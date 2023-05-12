<%@page import="omok.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="omok.MemberVO"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
MemberVO vo = (MemberVO)session.getAttribute("loginInfo");
if (vo == null) {
	out.println("미로그인");
} else {
%>
<%=vo.getNickname() %>님 안녕하세요<br>
<input type="button" value="로그아웃" onclick="location.href='start.jsp'">
<ul>
<li><a href="tempplay.jsp">Room 2</a></li>
<li><a href="tempplaytest.jsp">Room 3</a></li>
</ul>
<%
}
%>
</body>
</html>