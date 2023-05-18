var roomId = new URL(window.location.href).searchParams.get("roomId");
const webSocket = new WebSocket("ws://localhost:8090/omok/websocket/" + roomId);


const user_id=document.getElementById("user");
const messageTextArea = document.getElementById("messageTextArea");
console.log(user_id);

webSocket.onopen = function(message) {
	let hello = {};
	hello.type = 3;
	hello.msg = "";
	hello.id = user_id.value;
	
	sendMessage(JSON.stringify(hello));
  };
  
webSocket.onclose = function(message) {
     
    messageTextArea.value += "Server Disconnect...\n";
};
   
webSocket.onerror = function(message) {
      
   	messageTextArea.value += "error...\n";
};
   
webSocket.onmessage = function(message) {
    
    let received = JSON.parse(message.data);
    console.log(received)
    if(received.type == 0){
		selectedStone(received);
		setBoard(received.board);
	}
	else if(received.type == 1) selectedStone(received);
	else if(received.type == 2) putStone(received); // 소영 test
	else if(received.type == 3) {
		if(received.msg === "")
			messageTextArea.value += received.id + "님이 입장하셨습니다." + '\n';
		else {
			messageTextArea.value += received.id;
			messageTextArea.value += " : ";
			messageTextArea.value += received.msg + "\n";
		}
	}
};

/** 
{ 	
	"room": roomNumber
	"id" : user_id //사용자 id
	"type": 1	//ready
	"stone": 1 or 2 // "color"
}

*/
function sendChatMessage () {
	let message = {};
	message.type = 3;
	message.msg = messageBox.value;
	message.id = user_id.value;
	if(message.msg != "")
		sendMessage(JSON.stringify(message));
	
	messageBox.value = "";
}

function sendMessage(message) {

    webSocket.send(message);
  }
  /*
$('#btn_ready').addEventListener(
	"click",
	function (evt) {
		console.log(evt)
		let message = {};
		message.id = user_id.value;
		message.type = 1;
		sendMessage(JSON.stringify(message));
	},
	false
);
*/

function btn_ready(stone){
	
	let message = {};
	message.id = user_id.value;
	message.type = 1;
	message.stone = stone;
	sendMessage(JSON.stringify(message));
}


//선택된 돌 disabled
function selectedStone(message){
	console.log(message);
	const black_btn=document.getElementById("r-btn1");
	const white_btn=document.getElementById("r-btn2");

	if(message.black===user_id.value || message.white===user_id.value){
		black_btn.disabled = true;
		white_btn.disabled = true;
		//내 돌 찾기.
		console.log(message.black);
		console.log(user_id.value);
		console.log(message.black===user_id.value);
		mine = (message.black===user_id.value)? 1:2;
		console.log(mine);
		stone = (mine===1)? blackColor: whiteColor;

		if(message.black!=null && message.white!=null) addEvent();
	}
	if(message.black!=null) {
		black_btn.innerText =  message.black+" ready";
		black_btn.disabled = true;
	}
	if (message.white!=null) {
		white_btn.innerText =  message.white+" ready";
		white_btn.disabled = true;
	}
	
	if(message.black!=null && message.white!=null){
		//게임시작
		console.log("시작");
	}

}
//박소영 test
function putStone(message){
	console.log(message);
	turnCount = message.turnCount;
	isClicked(message.xPos, message.yPos);
	updateBoard();
}

function addEvent(){
	console.log("이벤트");
	board.addEventListener(
	  "mousemove",
	  function (evt) {
	    var mousePos = getMousePos(board, evt);
	    drawNotClicked(mousePos.x, mousePos.y);
	  },
	  false
	);
	board.addEventListener(
	  "mousedown",
	  function (evt) {
	    var mousePos = getMousePos(board, evt);
	    isClicked(mousePos.x, mousePos.y);
	  },
	  false
	);

}