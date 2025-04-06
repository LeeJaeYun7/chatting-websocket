const jwtToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkYTA5ZGI0Mi04YWE0LTRlNjMtYmIyZC1lYzZkMzZkNmEwMjciLCJpYXQiOjE3NDM5NTE5NDksImV4cCI6MTc0Mzk1NTU0OX0.pJCuyx_L1KeV3Z-M0_mYc7BIvIg3-7QSQ5SyOsTKqrx7Xz-47IA4Rq7zIcRwne458rgHNVDk0DUpFEb5cNiW5A"

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
         roomUuid: $("#roomId").val(),
         receiverUuid: 'b7f8937b-6fa6-427d-a236-aea67af259f4',
         content: $("#chatMessage").val(),
         jwtToken: jwtToken
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