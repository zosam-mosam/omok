const board = document.getElementById("board");
const ctx = board.getContext("2d");

let width = board.clientWidth;
let height = board.clientHeight;
let turn = 1; // 1 흑 2 백

const out = -1;
const size = 19;
const blank = width / 50;
const interval = (width - 2 * blank) / (size - 1);
const radius = interval / 2 - 2;

const line = "#fff";
const blackColor = "#E06D7A";
const whiteColor = "#5EB89F";


//카운트, 381되면 무승부,
//보드 resize

//보드판 초기화
var boardArray = new Array(size);
for (var i = 0; i < size; i++) {
  boardArray[i] = new Array(size);
  for (j = 0; j < size; j++) {
    boardArray[i][j] = 0;
  }
}

//돌 그리는 함수
function drawStone(color, posX, posY, radius) {
  ctx.beginPath();
  ctx.strokeStyle = color;
  ctx.fillStyle = color;
  ctx.arc(posX, posY, radius, 0, 2 * Math.PI);
  ctx.fill();
  ctx.stroke();
}

//보드 그리는 함수
function updateBoard() {
  // 배경
  ctx.fillStyle = "#000000";
  ctx.fillRect(0, 0, width, height);

  // 선 그리기
  ctx.strokeStyle = line;
  ctx.fillStyle = line;
  for (i = 0; i < size; i++) {
    // 수직선 그리기
    ctx.beginPath();
    ctx.moveTo(blank + i * interval, blank);
    ctx.lineTo(blank + i * interval, height - blank);
    ctx.stroke();

    // 수평선 그리기
    ctx.beginPath();
    ctx.moveTo(blank, blank + i * interval);
    ctx.lineTo(height - blank, blank + i * interval);
    ctx.stroke();
  }

  // 점그리기
  var circleRadius = width / 200;
  for (i = 0; i < 3; i++) {
    for (j = 0; j < 3; j++) {
      drawStone(
        line,
        blank + 3 * interval + i * 6 * interval,
        blank + 3 * interval + j * 6 * interval,
        circleRadius
      );
    }
  }

  // 놓은 돌 그리기
  for (i = 0; i < size; i++) {
    for (j = 0; j < size; j++) {
      if (boardArray[i][j] == 1) {
        drawStone(
          blackColor,
          blank + i * interval,
          blank + j * interval,
          radius
        );
      } else if (boardArray[i][j] == 2) {
        drawStone(
          whiteColor,
          blank + i * interval,
          blank + j * interval,
          radius
        );
      }
    }
  }
}

/* 마우스이벤트 */
//마우스 좌표를 구함
function getMousePos(canvas, evt) {
  var rect = canvas.getBoundingClientRect();
  return {
    x: evt.clientX - rect.left,
    y: evt.clientY - rect.top,
  };
}
//마우스좌표의 보드좌표를 구함
function getMouseRoundPos(xPos, yPos) {
  var x = (xPos - blank) / interval;
  var resultX = Math.round(x);
  var y = (yPos - blank) / interval;
  var resultY = Math.round(y);

  return {
    x: resultX,
    y: resultY,
  };
}
//돌이 놓이지 않은 곳이면 놓을수 있다는 표시를 해줌
function drawNotClicked(xPos, yPos) {
  resultPos = getMouseRoundPos(xPos, yPos);

  if (
    resultPos.x > out &&
    resultPos.x < size &&
    resultPos.y > out &&
    resultPos.y < size &&
    boardArray[resultPos.x][resultPos.y] == 0
  ) {
    updateBoard();
    ctx.beginPath();
    ctx.globalAlpha = 0.8;
    if (turn < 2) {
      now = blackColor;
    } else {
      now = whiteColor;
    }
    drawStone(
      now,
      blank + resultPos.x * interval,
      blank + resultPos.y * interval,
      radius
    );
    ctx.globalAlpha = 1;
  }
}
//돌 놓기
function isClicked(xPos, yPos) {
  resultPos = getMouseRoundPos(xPos, yPos);
  if (
    resultPos.x > out &&
    resultPos.x < size &&
    resultPos.y > out &&
    resultPos.y < size &&
    boardArray[resultPos.x][resultPos.y] == 0
  ) {
    boardArray[resultPos.x][resultPos.y] = turn;
    //checkOmok(turn, resultPos.x, resultPos.y);
    turn = 3 - turn; //차례 변경
  }
  updateBoard();
}


