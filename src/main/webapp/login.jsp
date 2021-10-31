<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>	
<!DOCTYPE html>
 <c:if test="{${sessionScope.id ne null }">
<c:redirect url="./index" />
</c:if>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<script src='./js/login.js'></script>
<style>
@import
	url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Limelight&family=Londrina+Solid&display=swap')
	;
</style>
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/login.css">
</head>
<body>
	<div id="headerSpace"></div>
	<div id="loginMain">
		<div id="leftContainer">
			<div class="bigTextBox">
				<p class="bigText">Welcome</p>
			</div>
			<div class="smallTextBox">
				<p class="smallText">지금, 웹툰을 보는 눈이 풍요로워집니다.</p>
			</div>
		</div>
		<div id="loginBoxContainer">
		<c:if test="${requestScope.nomember ne null}">
				${requestScope.nomember}
				</c:if>
			<div id="loginBox">
					<div id="inputIDBox">
					<div id="IDlogoSpace">
						<img src="./img/id.png" height="35px" alt="ID">
					</div>
					<form action="./login" method="post" style="display: contents;">
						<input type="text" id="inputID" name="id" required="required"
							placeholder="아이디를 입력하세요.">
				</div>
				<div id="inputPWBox">
					<div id="PWlogoSpace">
						<img src="./img/pw.png" height="35px" alt="Password">
					</div>
					<input type="password" id="inputPW" name="pw" required="required"
						placeholder="비밀번호를 입력하세요.">
				</div>
				<div id="loginOption">
					<label for="autoLogin"> <input type="checkbox"
						id="autoLogin" name="autoLogin">로그인 상태 유지 <span
						class="checkMark"></span>
					</label>
					<div class="findMyInfo">
						<a href="./findID.jsp">ID</a>/<a href="./findPW.jsp">비밀번호</a> 찾기
					</div>
				</div>
				<div id="toCreateIDBox">
					<span>아직 계정이 없으신가요?</span>&nbsp;&nbsp;<a href="./join.jsp" class="toCreateID">계정 만들기</a>
				</div>
				<button type="submit" id="loginSubmit">SIGN IN</button>
				</form>
			</div>
		</div>
	</div>
	<div class="marginSpace"></div>
	<div id="footerSpace"></div>
</body>
</html>