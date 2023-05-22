const modal = document.getElementById("modal");
const winner = document.getElementById("winner");

function modalOn(id) {
  console.log("modal");
  modal.style.display = "flex";
  winner.innerText = id + winner.innerText;
  console.log(modal.style.display);
}

function isModalOn() {
  return modal.style.display === "flex";
}

function modalOff() {
  modal.style.display = "none";
}
