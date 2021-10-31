<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div id="logo" onclick="menuClick('daily')">
	<img alt="logo" src="./img/logo.png">
</div>
<div id="loginbox">
	<c:choose>
		<c:when test="${sessionScope.id ne null }">		
		${sessionScope.id }님 
		<br>반갑습니다.
		<button onclick="location.href='./myinfomenu.jsp'">My Info</button>
			<button onclick="location.href='./logout'">Logout</button>
		</c:when>
		<c:otherwise>
			<button onclick="location.href='./login'">Login</button>
		</c:otherwise>
	</c:choose>
</div>
<c:if test="${sessionScope.grade eq 1 }">
	<div id="loginbox">
		<button onclick="location.href='./adminMember'">Admin</button>
	</div>
</c:if>
<script type="text/javascript" src="./js/menu.js"></script>
