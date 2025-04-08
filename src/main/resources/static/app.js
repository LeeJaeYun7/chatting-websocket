const jwtToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMDc0NTk2ODg1NDE5NTA1ODE0IiwiaWF0IjoxNzQ0MTM1NDczLCJleHAiOjE3NDQxMzkwNzN9.PSkzbtK1hCzPIiZV7Nvrc3yc9QTyR_k3AsFBVfzB5OcCsPQgCPinFwORZUOgk4D5lx1BrAKqBmneYdNDoC25dg"
const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8081/gs-guide-websocket',
    connectHeaders: {
       Authorization: 'Bearer ' + jwtToken// JWT 토큰을 헤더에 추가
    }
});

stompClient.onConnect = (frame) => {
    setConnected(true);
    console.log('Connected: ' + frame);

    stompClient.subscribe('/topic/chatMessage/oneOnOne', (response) => {
         console.log('Response body: ', response.body);
    });

    stompClient.subscribe('/user/topic/chatMessage/oneOnOne', (response) => {
         console.log('Response body: ', response.body);
    });

    stompClient.subscribe('/topic/chatMessage/group', (response) => {
             console.log('Response body: ', response.body);
    });

    stompClient.subscribe('/user/topic/chatMessage/group', (response) => {
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

function sendOneOnOneChatMessage() {
     const messageData = {
         roomId: $("#oneOnOneRoomId").val(),
         content: $("#oneOnOneChatMessage").val()
     };

     console.log("Sending message:", messageData);  // 메시지 데이터 확인

     stompClient.publish({
            destination: "/api/v1/chatMessage/oneOnOne",  // 서버에서 처리하는 엔드포인트
            body: JSON.stringify(messageData)  // JSON 형식으로 메시지 전송
     });
}


function sendGroupChatMessage() {
     const messageData = {
         roomId: $("#groupRoomId").val(),
         content: $("#groupChatMessage").val()
     };

     console.log("Sending message:", messageData);  // 메시지 데이터 확인

     stompClient.publish({
            destination: "/api/v1/chatMessage/group",  // 서버에서 처리하는 엔드포인트
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
    $("#sendOneOnOneChatMessage").click( () => {
        console.log("sendOneOnOneChatMessage button clicked");
        sendOneOnOneChatMessage()
    });

    $("#sendGroupChatMessage").click( () => {
        console.log("sendGroupChatMessage button clicked");
        sendOneOnOneChatMessage()
    });
});