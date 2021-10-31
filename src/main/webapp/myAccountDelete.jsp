<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계정 탈퇴</title>
</head>
<body>
<script>
if(confirm("정말 탈퇴하시겠습니까?")){
	alert("계정 탈퇴 되었습니다.");
	location.href="myAccountDelete";
}else{
	alert("취소되었습니다.");
	location.href="myinfo.jsp";
}
</script>
</body>
</html>