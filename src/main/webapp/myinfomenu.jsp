<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>작성한 글 조회</title>
<style type="text/css">
#info {
	float: right;
}
</style>
</head>
<body>
<h1 style="text-align: center;">작성한 글 조회</h1><hr>
	<!-- <form action="./myInfoIdentify" method="post"> -->
	<button onclick="location.href='actionInfoIdentify'">Action게시판</button>
	<button onclick="location.href='dailyInfoIdentify'">Daily게시판</button>
	<button onclick="location.href='dramaInfoIdentify'">Drama 게시판</button>
	<button onclick="location.href='fantasyInfoIdentify'">Fantasy
		게시판</button>
	<button onclick="location.href='romanceInfoIdentify'">Romance
		게시판</button>
	<button onclick="location.href='thrillerInfoIdentify'">Thriller
		게시판</button>
	<div id="info">
		<button onclick="location.href='myCommentMenu.jsp'">내가 작성한 댓글 조회</button>
		<button onclick="location.href='myinfo.jsp'">수정,탈퇴</button>
		<button onclick="location.href='index.jsp'">메인메뉴로 돌아가기</button>
	</div>
	
	<!-- 가드라인 -->
<h1 style="text-align: center;">계정 정보 수정, 탈퇴</h1><hr>
	<button onclick="location.href='./myInfo'">회원정보 수정하기</button>
	<button onclick="location.href='./myAccountDelete.jsp'">탈퇴</button>
	<button onclick="location.href='index.jsp'">메인메뉴로 돌아가기</button>
	<button onclick="location.href='myinfomenu.jsp'">내가 쓴 글 모아보기</button>
	<hr>
</body>
</html>