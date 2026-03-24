

window.onload = function() {

    connect();
}

let stompClient = null;
let roomId = document.getElementById("room_id").value;
function loadPreviousMessages() {

    fetch("/api/chat/" + roomId)  // REST API 호출
        .then(response => response.json())

        .then(messages => {
            messages.reverse().forEach(msg => showMessage(msg));
        })
        .catch(error => console.error('데이터 로딩 실패:', error));
}


function connect() {
    let socket = new SockJS("/ws-chat");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame){
        //console.log('Connected: ' + frame);

        // 기존 메시지 불러오기
        loadPreviousMessages();

        // WebSocket 구독
        stompClient.subscribe("/topic/messages/" + roomId, function(message){
            //console.log("구독 경로: ", "/topic/messages/" + roomId)
            //console.log("서버에서 받은 데이터: ", message.body);
            showMessage(JSON.parse(message.body));
        });
    });
}

function sendMsg() {
    const chat = document.getElementById("chat");
    const senderId = document.getElementById("sender_id").value;
    const name = document.getElementById("name").value;
    const messageInput = document.getElementById("chatInput");
    const message = messageInput.value;
    if (!message) return;

    stompClient.send("/app/sendMessage/" + roomId, {}, JSON.stringify({
        senderId: senderId,    // 서버 DTO 필드명과 똑같이!
        senderName: name,      // 서버 DTO 필드명과 똑같이!
        message: message
    }));

    // const myBubble = document.createElement('div');
    // myBubble.className = 'bubble sent';
    // myBubble.innerText = val;
    // chat.appendChild(myBubble);

    chat.scrollTop = chat.scrollHeight;
    messageInput.value = "";
    document.getElementById("refineCard").style.display="none";
}
document.getElementById("chatInput").addEventListener("keyup", function(e) {
    if (e.key === "Enter") {
        e.preventDefault();
        sendMsg();
    }
});
// function sendMsg() {
//     const input = document.getElementById('chatInput');
//     const chat = document.getElementById('chat');
//     const val = input.value.trim();
//     if (!val) return;
//
//     const myBubble = document.createElement('div');
//     myBubble.className = 'bubble sent';
//     myBubble.innerText = val;
//     chat.appendChild(myBubble);
//     input.value = '';
//
//     chat.scrollTop = chat.scrollHeight;
// }

function showMessage(message){
    //console.log(message)
    const chat = document.getElementById("chat");
    const bubble = document.createElement("div");
    const myId = Number(document.getElementById("sender_id").value);

    // JSON 데이터의 실제 키값(snake_case)에 맞게 수정
    const msgSenderId   = message.sender_id || message.senderId;
    const msgContent    = message.final_msg || message.message;
    const msgSenderName = message.sender_name || message.senderName || "알 수 없는 사용자";

    if (msgSenderId === myId) {
        bubble.className = "bubble sent";
        bubble.innerText = "(나) " + msgContent;
    } else {
        bubble.className = "bubble received";
        bubble.innerText = msgContent;
    }

    chat.appendChild(bubble);
    chat.scrollTop = chat.scrollHeight; // 새 메시지 오면 스크롤 하단으로
}
