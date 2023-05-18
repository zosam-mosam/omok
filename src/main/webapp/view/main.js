updateBoard();

//메시
webSocket.onopen = () => sendMessage(JSON.stringify({ type: 0 }));

function btn_ready(stone) {
  let message = {};
  message.id = user_id.value;
  message.type = 1;
  message.stone = stone;

  sendMessage(JSON.stringify(message));
}

//채팅
function sendChatMessage() {
  let message = {};
  message.type = 3;
  message.msg = messageBox.value;
  message.id = user_id.value;
  if (message.msg != "") sendMessage(JSON.stringify(message));

  messageBox.value = "";
}

function disconnect() {
  //disconnect 만들기
}

