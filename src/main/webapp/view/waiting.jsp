<%@page import="java.util.List"%>
<%@ page import="omok.MemberVO"%>
<%@ page import="omok.RoomVO"%>
<%@ page import="omok.Websocket"%>
<%@ page import="java.util.Map" %>
<%@ page import="javax.websocket.Session" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="waiting.css?asd" rel="stylesheet">
<%MemberVO vo = (MemberVO)session.getAttribute("loginInfo"); %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link href="waiting.css?alert" rel="stylesheet" />
    <title>Document</title>
    <%
		int[] a = new int[10];
		Websocket ws = new Websocket();
		Map<Integer, RoomVO> ss = ws.getSessionUsers2();
		int sum = 0;
		for(int i=1; i<=5; ++i) {
			if(ss.get(i) != null) {
				a[i] = ss.get(i).getPlayerCount();
				sum += a[i];
				pageContext.setAttribute("a",a);

			}
		}
		if (vo == null) {
		%>
<script>
	alert("로그인해 주세요");
	location.href= "start.jsp";
	<%-- location.href= "boardlist.jsp?page=" + <%=spage %>; --%>
</script>
		<%	
		} else {
			if (!ss.isEmpty()) System.out.println("현재 게임 접속자 수 : " + sum);
			else System.out.println("Room UserSession is Empty");
			}%>
    <script type="text/javascript">
    		function onclick(roomNo,num){
    			if(num<2 || num === undefined){
    				location.href="/omok/view/play.jsp?roomId="+roomNo;
    			}
    			else{
    				alert(num);
    			}
    		}
    </script>	
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
				<li><a href="javascript:onclick(1,${a[1]})"><i class="fa-sharp fa-solid fa-play"></i>&nbsp;Room 1 <%=a[1] %> / 2</a></li>
				<li><a href="javascript:onclick(2,${a[2]})"><i class="fa-sharp fa-solid fa-play"></i>&nbsp;Room 2 <%=a[2] %> / 2</a></li>
				<li><a href="javascript:onclick(3,${a[3]})"><i class="fa-sharp fa-solid fa-play"></i>&nbsp;Room 3 <%=a[3] %> / 2</a></li>
				<li><a href="javascript:onclick(4,${a[4]})"><i class="fa-sharp fa-solid fa-play"></i>&nbsp;Room 4 <%=a[4] %> / 2</a></li>
				<li><a href="javascript:onclick(5,${a[5]})"><i class="fa-sharp fa-solid fa-play"></i>&nbsp;Room 5 <%=a[5] %> / 2</a></li>
			</ul>
          </div>
        </div>
      </div>
    </div>
</body>
</html>