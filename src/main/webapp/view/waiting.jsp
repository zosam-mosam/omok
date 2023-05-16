<%@page import="omok.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="omok.MemberVO"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="waiting.css?alert" rel="stylesheet" />
    <title>Document</title>
  </head>
  <body>
    <script
      src="https://kit.fontawesome.com/755a2d278e.js"
      crossorigin="anonymous"
    ></script>
    <div id="container">
      <div
        class="header"
        style="line-height: 200px; text-align: center; color: #fff"
      >
        <%
		MemberVO vo = (MemberVO)session.getAttribute("loginInfo");
		if (vo == null) {
			out.println("미로그인");
		} else {
		%>
		<%=vo.getNickname() %>님 안녕하세요 !!<br>
		<%
		}
		%>
      </div>
      <div class="mainWrap">
        <div class="roomContainer">
          <div id="roomHeader">
            <div id="roomList"><p>▩ 방 목록</p></div>
            <div id="roomLine">
              <input
                type="button"
                value="로그아웃"
                onclick="location.href='start.jsp'"
                id="logout"
              />
            </div>
          </div>
          <div class="roomWrap">
            <ul>
              <li>
                <a href="tempplay.jsp"
                  ><i class="fa-sharp fa-solid fa-play"></i>&nbsp;1번방
                </a>
              </li>
              <li>
                <a href="tempplaytest.jsp"
                  ><i class="fa-sharp fa-solid fa-play"></i>&nbsp2번방</a
                >
              </li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>

