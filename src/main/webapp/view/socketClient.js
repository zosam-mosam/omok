var roomId = new URL(window.location.href).searchParams.get("roomId");
const webSocket = new WebSocket("ws://192.168.0.127:8090/omok/websocket/" + roomId);

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
  console.log("받았다  : "+received.turnCount);
  if (received.type == 0) {
    selectStone(received);
    setBoard(received.board);
    //내가 유저면 게임을 이어갈 수 있도록?
  } else if (received.type == 1) {
	  selectStone(received);
    console.log("onM "+received.turnCount);
	  if(received.turnCount>=1 && amIuser(received.black, received.white)) start(received.turnCount);
  }
  else if (received.type == 2) {
    putStone(received);
    if(received.finish==1)win(received);
  }
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