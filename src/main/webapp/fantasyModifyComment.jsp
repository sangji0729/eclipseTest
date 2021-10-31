<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글수정</title>
<style type="text/css">
</style>
</head>
<body>
	<div id="modifyComment">
		<form action="./fantasyModifyComment" method="post">
			<textarea name="fccontent">${dto.fccontent}</textarea>
			<input type="hidden" name="fcno" value="${dto.fcno }"> <input
				type="hidden" name="fno" value="${dto.fno }">
			<button>수정하기</button>

		</form>

	</div>
</body>
</html>