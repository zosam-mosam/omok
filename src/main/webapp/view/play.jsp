<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Test ING...</title>
<link rel="stylesheet" href="layout.css"/>
<link rel="stylesheet" href="button.css"/>
<link rel="stylesheet" href="chatting.css"/>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
</head>
<body>
    <div class="wrapper">
        <div class="header"></div>
        <div class="wrap">
	        <div class="left">
	        	<div class="area_btn">
		        	<div class="area_btn1">
	        			<button onclick="btn_ready(1)" class="w-btn w-btn-black" type="button">user1 ready</button>
	        		</div>
	        		<br>
		        	<div class="area_btn2">
		        		<button onclick="btn_ready(2)" class="w-btn w-btn-white" type="button">user2 ready</button>
		        	</div>
	        	</div>
	        </div>
	        <div class="middle">
	            <div class="board">
	                <canvas id="board" width="700" height="700"></canvas>
	            </div>
	        </div>
	        <div class="right">
	        	<div class="chatting">
	        	 <form>
				    <textarea id="messageTextArea" rows="20" cols="40"></textarea>
				    <input id="user" type="text" placeholder="사용자 이름" value="">
				    <input id="textMessage" type="text" placeholder="보낼 메시지 입력">
				    <input onclick="sendMessage()" value="Send" type="button">
				    <input onclick="disconnect()" value="Disconnect" type="button">
				  </form>
	        	</div>
	        	</div>
	     </div>
    </div>
</body>
<script type="text/javascript" src="communication.js"></script>
<script type="text/javascript" src="board.js"></script>
<script type="text/javascript" src="main.js"></script>
</html>