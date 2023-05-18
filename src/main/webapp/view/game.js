function selectedStone(message) {
  const black_btn = document.getElementById("r-btn1");
  const white_btn = document.getElementById("r-btn2");

  if (message.black === user_id.value || message.white === user_id.value) {
    black_btn.disabled = true;
    white_btn.disabled = true;
    //내 돌 찾기.
    mine = message.black === user_id.value ? 1 : 2;
    stone = mine === 1 ? blackColor : whiteColor;
    my_btn = mine === 1 ? black_btn : white_btn;

    if (message.black != null && message.white != null)
      board.addEventListener("mousedown", mouseDown);
  }
  if (message.black != null) {
    black_btn.innerText = message.black + " ready";
    black_btn.disabled = true;
  }
  if (message.white != null) {
    white_btn.innerText = message.white + " ready";
    white_btn.disabled = true;
  }
  //게임시작
  if (message.black != null && message.white != null) {
    board.addEventListener("mousemove", mouseMove);
    //내차례
    if (message.turn == mine) {
      board.addEventListener("mousedown", mouseDown);
      my_btn.style.border = "3px solid " + stone;
      my_btn.style.backgroundColor = white;
    } //상대차례
    else {
      board.removeEventListener("mousedown", mouseDown);
    }
  }
}

const mouseMove = (e) => {
  var mousePos = getMousePos(board, e);
  drawNotClicked(mousePos.x, mousePos.y);
};

const mouseDown = (e) => {
  var mousePos = getMousePos(board, e);
  isClicked(mousePos.x, mousePos.y);
};
