<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>작성한 댓글 조회</title>
<style type="text/css">
#info{
	float: right;
}
</style>
</head>
<body>
<h1 style="text-align: center;">작성한 댓글 조회</h1><hr>
<!-- <form action="./myInfoIdentify" method="post"> -->
<button onclick="location.href='actionInfoCommentIdentify'">Action게시판</button>
<button onclick="location.href='dailyInfoCommentIdentify'">Daily게시판</button>
<button onclick="location.href='dramaInfoCommentIdentify'">Drama 게시판</button>	
<button onclick="location.href='fantasyInfoCommentIdentify'">Fantasy 게시판</button>	
<button onclick="location.href='romanceInfoCommentIdentify'">Romance 게시판</button>	
<button onclick="location.href='thrillerInfoCommentIdentify'">Thriller 게시판</button>	
<div id="info">
<button onclick="location.href='myinfomenu.jsp'">내가 작성한 글 조회</button>
<button onclick="location.href='myinfo.jsp'">수정,탈퇴</button>
<button onclick="location.href='index.jsp'">메인메뉴로 돌아가기</button>
</div>
<hr>
</body>
</html>