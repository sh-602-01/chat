window.onload = function() {
    connect();
}

let stompClient = null;
let roomId = document.getElementById("room_id").value;
let userId = document.getElementById("user_id").value;

// 비회원이면 guest 생성
if (!userId || userId === "null") {
    userId = localStorage.getItem("guestId");

    if (!userId) {
        userId = "guest_" + Math.random().toString(36).substring(2, 8);
        localStorage.setItem("guestId", userId);
    }
}
console.log("현재 userId:", userId);
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
        console.log("✅ 연결 성공:", frame);
        // WebSocket 구독
        stompClient.subscribe("/topic/messages/" + roomId, function(message){
            // 🔥 내가 보낸 메시지는 무시
            if (data.sender_name === userId || data.senderId === userId) {
                return;
            }
            showMessage(data);
        });
        // 기존 메시지 불러오기
        loadPreviousMessages();
    });
}

function sendMsg() {
    const chat = document.getElementById("chat");
    const messageInput = document.getElementById("chatInput");
    const message = messageInput.value;

    if (!message) return;

    showMessage({
        senderId: userId,
        message: message
    });

    stompClient.send("/app/sendMessage/" + roomId, {}, JSON.stringify({
        senderId: userId,        // 👉 통일
        senderName: userId,      // 👉 필요하면 나중에 닉네임으로 변경
        message: message
    }));

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


function showMessage(message){
    //console.log(message)
    console.log("🔥 화면 출력 들어옴:", message);
    const chat = document.getElementById("chat");
    const bubble = document.createElement("div");

    // JSON 데이터의 실제 키값(snake_case)에 맞게 수정
    const msgSenderId   = message.sender_id || message.senderId;
    const msgContent    = message.final_msg || message.message;
    //const msgSenderName = message.sender_name || message.senderName || "알 수 없는 사용자";

    if (msgSenderId == userId) {   // == 사용 (문자열/숫자 대응)
        bubble.className = "bubble sent";
        bubble.innerText = "(나) " + msgContent;
    } else {
        bubble.className = "bubble received";
        bubble.innerText = msgContent;
    }

    chat.appendChild(bubble);
    chat.scrollTop = chat.scrollHeight; // 새 메시지 오면 스크롤 하단으로
}
