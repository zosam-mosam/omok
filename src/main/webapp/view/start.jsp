<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="./start.css?alrt" rel="stylesheet" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
    <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
    <title>시작페이지</title>
    <script>
      $(window).on("load", function () {
        setTimeout(function () {
          $("#startButton").css("display", "flex");
        }, 1000);
        $("#startButton").on("click", function () {
          $("#load").fadeOut(1000);
        }); 
        
      });
   	  function login() {
     		var id=$("#id").val();
     		var pwd=$("#pwd").val();
     		console.log(id,pwd);
     		if(id!==""||pwd!==""){
     			$.ajax({
               	url:'/omok/start.do',
               	type:'post',
               	data:{id:id,
               		pwd:pwd},
               	success:function(check){
               		console.log($.trim(check));
               		if($.trim(check)==="false"){//값이 넘어올때 공백도 같이 넘어오기 때문에 트리밍 해줘야함
               			alert("아이디 또는 비밀번호가 일치하지 않습니다.");
               		}
               		else{
               			location.replace("/omok/view/waiting.jsp")
               		}
               	}
               });
           }
           else{//공백이 아닌 값이 들어올때만 실행       	
      			$(".id_already").css("display", "none");
      			$(".id_ok").css("display", "none");
           }
   	  }
 
    </script>
    <div id="load">
      <div id="loadingImgWrap">
        <img src="./img/1200x1200.gif" alt="loading" id="loadingImg" onsubmit="false"/>
      </div>
      <div id="startImgWrap">
        <img
          src="./img/NicePng_start-button-png_1108890.gif"
          id="startButton"
        />
      </div>
    </div>
  </head>
  <body>
    <div class="startPageWrap">
      <div class="header">
        <img src="./img/roc1.png" alt="로고" class="headerLogo" />
      </div>
      <div class="container">
        <h2>로그인</h2>
        <div class="loginInfo">
          <form method="post" id="login-form" action="start.do">
            <input
              type="text"
              placeholder="아이디를 입력해주세요"
              name="userId"
              id="id"
            /><br />
            <input
              type="password"
              placeholder="비밀번호를 입력해주세요"
              name="userPwd"
              id="pwd"
            /><br />
            <input type="button" value="로그인" onclick="login()" id="lo"/>
            <input
              type="button"
              value="회원가입"
              onclick="location.href='createID.jsp'"
            />
          </form>
        </div>
      </div>
    </div>
  </body>
</html>