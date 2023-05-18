//보드생성
updateBoard();

//메시지
webSocket.onopen = () => sendMessage(JSON.stringify({'type': 0}));

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