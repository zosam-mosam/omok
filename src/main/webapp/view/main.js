updateBoard();



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
