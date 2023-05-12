<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="start.css?alert" rel="stylesheet">
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
    <script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<title>시작화면</title>
<script>
      $(window).on("load", function () {
        setTimeout(function () {
          $("#startButton").css("display", "flex");
        }, 3000);
        $("#startButton").on("click", function () {
          $("#load").fadeOut(1000);
        });
      });
    </script>
<div id="load">
      <div id="loadingImgWrap">
        <img src="img/1200x1200.gif" alt="loading" id="loadingImg" />
      </div>
      <div id="startImgWrap">
        <img
          src="img/NicePng_start-button-png_1108890.gif"
          id="startButton"
        />
      </div>
    </div>
</head>
<body>
    <div class="startPageWrap">
      <div class="header">
        <img
          src="img/돌맹이.png"
          alt="로고"
          class="headerLogo"
        />
      </div>
      <div class="container">
        <h2>로그인</h2>
        <div class="loginInfo">
          <form
            method="post"
            id="login-form"
            action="start.do"
          >
            <input
              type="text"
              placeholder="아이디를 입력해주세요"
              name="userId"
            /><br />
            <input
              type="password"
              placeholder="비밀번호를 입력해주세요"
              name="userPwd"
            /><br />
            <input type="submit" value="로그인" />
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