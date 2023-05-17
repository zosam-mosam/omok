//보드생성
updateBoard();
	
//메시지
webSocket.onopen = () => sendMessage(JSON.stringify({'type': 0}));




