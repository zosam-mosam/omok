var roomId = new URL(window.location.href).searchParams.get("roomId");
const webSocket = new WebSocket("ws://localhost:90/omok/websocket/" + roomId);

const user_id = document.getElementById("user");
const messageTextArea = document.getElementById("messageTextArea");
console.log(user_id.value);

webSocket.onopen = function (message) {
  messageTextArea.value += "Server connect...\n";
};

webSocket.onclose = function (message) {
  messageTextArea.value += "Server Disconnect...\n";
};

webSocket.onerror = function (message) {
  messageTextArea.value += "error...\n";
};

webSocket.onmessage = function (message) {
  let received = JSON.parse(message.data);
  console.log(received);
  if (received.type == 0) {
    selectedStone(received);
    setBoard(received.board);
  } else if (received.type == 1) selectedStone(received);
  else if (received.type == 2) putStone(received); // 소영 test
  else if (received.type == 3) {
    if (received.msg === "")
      messageTextArea.value += received.id + "님이 입장하셨습니다." + "\n";
    else {
      messageTextArea.value += received.id;
      messageTextArea.value += " : ";
      messageTextArea.value += received.msg + "\n";
    }
  }
};

function sendMessage(message) {
  webSocket.send(message);
}
