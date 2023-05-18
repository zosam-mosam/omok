const black_btn = document.getElementById("r-btn1");
const white_btn = document.getElementById("r-btn2");

let mine=-1; //흑:1, 백:2
let myColor="#b8b8b8"; //"#....."
let myBtn=null; //black_btn 또는 white_btn

function amIuser(sBlack, sWhite){
  if (sBlack === user_id.value || sWhite=== user_id.value) return true;
  else return false;
}

function selectStone(message) {
  //사용자에게 표시해주기
  setReadyButton(message.black, message.white);
  //내 돌 설정.
  setMyStone(message);
}

function setReadyButton(sBlack, sWhite){
  //본인이 게임하는 경우 button 둘 다 disabled
  if (amIuser(sBlack, sWhite)) {
    black_btn.disabled = true;
    white_btn.disabled = true;
  }
  //흑이 준비가 된 경우 disabled
  if (sBlack != null) {
    black_btn.innerText = sBlack + " ready";
    black_btn.disabled = true;
  }
  //백이 준비가 된 경우 disabled
  if (sWhite != null) {
    white_btn.innerText = sWhite + " ready";
    white_btn.disabled = true;
  }
}

function setMyStone(message){
  mine = message.black === user_id.value ? 1 : 2;
  myColor = mine === 1 ? blackColor : whiteColor;
  myBtn = mine === 1 ? black_btn : white_btn;

}

//게임시작
function start(turnCount){
  console.log("게임시작~");
  console.log("start "+turnCount);
  //시작하면 mousemove 이벤트 추가
  board.addEventListener("mousemove", mouseMove);
  //누구차례?
  whoseTurn(turnCount);

}

function whoseTurn(turnCount){
  console.log("whose "+turnCount)
  //내차례?
  turn = (turnCount%2==1)? 1: 2;
  //내차례면 돌 놓을 수 있음
  if(turn==mine){
    board.removeEventListener("mousedown", function(event){
      mouseDown(event, turnCount);
    });
  }else{ // 아니면 못 놓음
    board.removeEventListener("mousedown", function(event){
      mouseDown(event, turnCount);
    });
  }
  setTurnButton(turn);
}

function setTurnButton(turn){
    if(turn==1){
      black_btn.style.boarder = "3px solid "+blackColor;
      black_btn.style.color = "#ffffff";
      black_btn.style.backgroundColor = blackColor; 
      white_btn.style.boarder = "3px solid "+"#b8b8b8";
      white_btn.style.color = "#ffffff";
      white_btn.style.backgroundColor = "#b8b8b8"; 
    }else{
      white_btn.style.boarder = "3px solid "+whiteColor;
      white_btn.style.color = "#ffffff";
      white_btn.style.backgroundColor = whiteColor; 
      black_btn.style.boarder = "3px solid "+"#b8b8b8";
      black_btn.style.color = "#ffffff";
      black_btn.style.backgroundColor = "#b8b8b8"; 
    }
    
}
const mouseMove = (e) => {
  var mousePos = getMousePos(board, e);
  drawNotClicked(mousePos.x, mousePos.y);
};

function mouseDown(event, turnCount){
  var mousePos = getMousePos(board, event);
  isClicked(mousePos.x, mousePos.y, turnCount);
}
// const mouseDown = (e) => {
//   var mousePos = getMousePos(board, e);
//   isClicked(mousePos.x, mousePos.y, turnCount);
// };

function putStone(message){
  console.log(message.finish);
  console.log("put "+message.turnCount);
  setBoard(message.board);
  whoseTurn(message.turnCount);

}