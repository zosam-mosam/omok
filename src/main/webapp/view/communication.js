const webSocket = new WebSocket("ws://localhost:8090/omok/websocket4"); //나중에 바꾸기 // 192.168.0.127

const user_id=document.getElementById("user");
const messageTextArea = document.getElementById("messageTextArea");

webSocket.onopen = function(message) {
	
	status.valueOf
    messageTextArea.value += "Server connect...\n";
  };
  
webSocket.onclose = function(message) {
     
    messageTextArea.value += "Server Disconnect...\n";
};
   
webSocket.onerror = function(message) {
      
   	messageTextArea.value += "error...\n";
};
   
webSocket.onmessage = function(message) {
      
    messageTextArea.value += message.data + "\n";
    
    let received = JSON.parse(message.data);
	if(received.type == 1) console.log("됬다");
    
};

/** 
{ 	
	"id" : user_id //사용자 id
	"type": 1	//ready
	"stone": 1 or 2 // "color"
}

*/

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
	console.log(stone)
		let message = {};
		message.id = user_id.value;
		message.type = 1;
		message.stone = stone;
		sendMessage(JSON.stringify(message));
}