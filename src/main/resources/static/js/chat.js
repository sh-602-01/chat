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
        stompClient.subscribe("/topic/messages/" + roomId, function(message) {
            // 1. 서버에서 받은 JSON 문자열을 객체로 변환
            const data = JSON.parse(message.body);

            console.log("받은 메시지 데이터:", data);

            // 2. 내가 보낸 메시지인지 확인 (중복 출력 방지)
            // 서버에서 보내주는 필드명(senderId 또는 sender_id 등)을 정확히 확인하세요.
            const msgSenderId = data.senderId || data.sender_id;

            if (msgSenderId == userId) {
                return;
            }

            // 3. 화면에 표시
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
        senderName: "나",
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

function showMessage(message) {
    console.log("🔥 화면 출력 데이터:", message);
    const chat = document.getElementById("chat");
    const bubble = document.createElement("div");

    // 데이터 키값 추출 (서버 필드명에 맞춰 유연하게 대응)
    const msgSenderId = message.senderId || message.sender_id;
    const msgContent = message.message || message.final_msg;
    const msgSenderName = message.senderName || message.sender_name || "익명";

    // 1. 이름을 담을 span 생성
    const nameSpan = document.createElement("span");
    nameSpan.className = "user-name";

    // 2. 메시지 내용을 담을 p 생성
    const contentP = document.createElement("p");
    contentP.className = "user-msg";
    contentP.innerText = msgContent;

    if (msgSenderId == userId) {
        bubble.className = "bubble sent";
        nameSpan.innerText = "(나)";
    } else {
        bubble.className = "bubble received";
        nameSpan.innerText = msgSenderName;
    }

    // bubble 안에 span과 p를 순서대로 추가
    bubble.appendChild(nameSpan);
    bubble.appendChild(contentP);

    chat.appendChild(bubble);
    chat.scrollTop = chat.scrollHeight; // 스크롤 하단 이동
}
