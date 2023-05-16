<%@ page import="omok.MemberVO"%>
<%@ page import="omok.RoomVO"%>
<%@ page import="omok.Websocket"%>
<%@ page import="java.util.Map" %>
<%@ page import="javax.websocket.Session" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<link href="waiting.css?alert" rel="stylesheet">
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Waiting Room</title>
</head>
<body>
<script>
	// setTimeout('location.reload()', 50000);
</script>
<%
Websocket ws = new Websocket();
Map<Integer, RoomVO> ss = ws.getSessionUsers2();
int[] a = new int[10];
int sum = 0;
for(int i=1; i<5; ++i) {
	if(ss.get(i) != null) {
		a[i] = ss.get(i).getPlayerCount();
		sum += a[i];
	}
}
MemberVO vo = (MemberVO)session.getAttribute("loginInfo");
System.out.println(vo); // 로그인 정보 객체
if (vo == null) {
%>
<script>
	alert("로그인해 주세요");
	location.href= "start.jsp";
	<%-- location.href= "boardlist.jsp?page=" + <%=spage %>; --%>
</script>
<%	
} else {
	if (!ss.isEmpty())
		System.out.println("현재 게임 접속자 수 : " + sum);
	else
		System.out.println("Room UserSession is Empty");%>
<div id=wrap>
	<p><%=vo.getNickname() %>님 안녕하세요</p>
<input type="button" value="로그아웃" onclick="location.href='start.jsp'">
<ul>
	<li><a href="play.jsp?roomId=1">Room 1 <%=a[1] %> / 2</a></li>
	<li><a href="play.jsp?roomId=2">Room 2 <%=a[2] %> / 2</a></li>
	<li><a href="play.jsp?roomId=3">Room 3 <%=a[3] %> / 2</a></li>
	<li><a href="play.jsp?roomId=4">Room 4 <%=a[4] %> / 2</a></li>
	<li><a href="play.jsp?roomId=5">Room 5 <%=a[5] %> / 2</a></li>
</ul>
</div>
<%
	}
%>
</body>
</html>