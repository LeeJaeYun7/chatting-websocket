const jwtToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMDc0NTk2ODg1NDE5NTA1ODE0IiwiaWF0IjoxNzQ0MDQ1NDg1LCJleHAiOjE3NDQwNDkwODV9.h0iw3DFRQoXQNOJbVJ1Z8sXxPSjW2Xiz82tUARk1me3VZauweendbUyssNcv4L6qzx4VBfqdPAWYrQBmJYVoqQ"
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8081/gs-guide-websocket',
    connectHeaders: {
       Authorization: 'Bearer ' + jwtToken// JWT 토큰을 헤더에 추가
    }
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);

    stompClient.subscribe('/topic/chatMessage', (response) => {
         console.log('Response body: ', response.body);
    });

    stompClient.subscribe('/user/topic/chatMessage', (response) => {
         console.log('Response body: ', response.body);
    });
};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    stompClient.activate();
}

function disconnect() {
    stompClient.deactivate();
    setConnected(false);
    console.log("Disconnected");
}

function sendChatMessage() {
     const messageData = {
         roomId: $("#roomId").val(),
         content: $("#chatMessage").val(),
         roomType: 'oneOnOne'
     };

     console.log("Sending message:", messageData);  // 메시지 데이터 확인

     stompClient.publish({
            destination: "/api/v1/chatMessage",  // 서버에서 처리하는 엔드포인트
            body: JSON.stringify(messageData)  // JSON 형식으로 메시지 전송
     });
}

function showToken(token) {
    $("#tokens").append("<tr><td>Token: " + token + "</td></tr>");
}

function showRank(rank) {
    $("#ranks").append("<tr><td>waitingRank: " + rank + "</td></tr>");
}

$(function () {
    $("form").on('submit', (e) => e.preventDefault());
    $("#connect").click(() => connect());
    $("#disconnect").click(() => disconnect());
    $("#send").click(() => sendUuid());
    $("#sendChatMessage").click( () => {
        console.log("sendChatMessage button clicked");
        sendChatMessage()
    });
});