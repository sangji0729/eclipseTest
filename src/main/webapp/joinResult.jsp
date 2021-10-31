<%@page import="util.Util"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>WebToonReview</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<script src='./js/join.js'></script>
<style>
@import
	url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Limelight&family=Londrina+Solid&display=swap')
	;
</style>
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/join.css">
<link rel="stylesheet" href="./css/login.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
	<div id="loginMain">
		<div id="loginBoxContainer">
			<div id="loginBox">
				<h1 style="text-align: center;">Join Result</h1>
				<%
				if (request.getAttribute("result").equals(1)) {
				%>
				<h2 style="text-align: center;"><%=request.getAttribute("id")%>,
					가입이 완료되었습니다. <br>
					<button type="submit" id="joinSubmit"
						onclick="location.href='./login.jsp'">Sign In</button>
				</h2>
				<%
				} else {
				%>
				<h2 style="text-align: center;"><%=request.getAttribute("id")%>,
					error!
				</h2>
				<h3 style="text-align: center;">가입에 실패했습니다. 다시 시도해주세요.</h3>
				<%
				}
				%>
			</div>
		</div>
	</div>
</body>
</html>