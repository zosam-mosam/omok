<%@page import="org.json.JSONObject"%>
<%@page import="java.util.ArrayList"%>
<%@page import="lombok.Data"%>
<%@page import="java.util.List"%>
<%@page import="omok.MemberVO"%>
<%@page import="omok.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link href="createID.css?alert" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.js"></script>
<title>회원가입 페이지</title>

<script>
      function checkId() {
        var id = $("#id").val(); //id값이 "id"인 입력란의 값을 저장
        console.log(id);
        if(id!==""){
        	$.ajax({
            	url:'/omokGame/idCheck.do',
            	type:'post',
            	data:{id:id},
            	success:function(check){
            		console.log($.trim(check));
            		if($.trim(check)==="false"){//값이 넘어올때 공백도 같이 넘어오기 때문에 트리밍 해줘야함
            			$(".id_already").css("display", "inline-block");
            			$(".id_ok").css("display", "none");
            		}
            		else{
            			$(".id_ok").css("display", "inline-block");
            			$(".id_already").css("display", "none");
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
        <h2>회원가입</h2>
        <div class="loginInfo">
          <form method="post" id="login-form" action="createID.do">
            <input
              type="text"
              placeholder="아이디를 입력해주세요"
              name="userId"
              id="id"
              onchange="checkId()"
              style="margin: 0"
            />
            <span class="id_ok">사용 가능한 아이디입니다.</span>
            <span class="id_already">중복된 아이디 입니다</span>
            <br />
            <input
              type="password"
              placeholder="비밀번호를 입력해주세요"
              name="userPwd"
              style="margin-top: 16px"
            /><br />
              <input
              type="text"
              placeholder="게임에서 사용 할 닉네임"
              name="nickname"
            /><br />
            <input type="submit" value="가입하기 "/>
            <input type="text" hidden="hidden" />
            <input
              type="button"
              value="돌아가기"
              onclick="location.href='start.jsp'"
            />
          </form>
        </div>
      </div>
    </div>
  </body>
</html>