var roomId = new URL(window.location.href).searchParams.get("roomId");
const webSocket = new WebSocket("ws://localhost:8090/omok/websocket/" + roomId);

const user_id=document.getElementById("user");
const messageTextArea = document.getElementById("messageTextArea");

let start=0;

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
	if(received.type == 1) selectedStone(received);
	if(received.type == 2) putStone(received); // 소영 test

	if(received.type == 3) {
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
	message.room=0;
	message.id = user_id.value;
	message.type = 1;
	message.stone = stone;
	sendMessage(JSON.stringify(message));
}


//선택된 돌 disabled
function selectedStone(message){
	//console.log(message);
	const ready_btn=document.getElementById("r-btn"+message.stone);
	ready_btn.innerText = message.settedUser+" "+ready_btn.innerText;
	ready_btn.disabled=true;
	
	if(message.ready != 0) addEvent();
}
//박소영 test
function putStone(message){
	console.log(message);
	turnCount = message.turnCount;
	isClickedd(message.xPos, message.yPos);
}

function addEvent(){
	
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