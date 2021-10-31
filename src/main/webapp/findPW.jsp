

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>WebtoonReview</title>
<script
    src="https://code.jquery.com/jquery-3.6.0.min.js"
    integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
    crossorigin="anonymous"></script>
<script src='./js/join.js'></script>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Limelight&family=Londrina+Solid&display=swap');
    @import url('https://fonts.googleapis.com/css2?family=Lora:wght@400;500&family=Nobile:wght@700&display=swap');
</style>
    <style>
        #findBox h4 {
            margin-top: -5px;
        }
    </style>
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/find.css">
</head>
<body>
	<%
	String result = (String) request.getAttribute("result");
	%>
<div id="headerSpace">
</div>
<div id="findMain">
    <div id="leftContainer">
        <div class="bigTextBox">
            <p class="bigText">Welcome</p>
        </div>
        <div class="smallTextBox">
            <p class="smallText">지금, 웹툰을 보는 눈이 풍요로워집니다.</p>
        </div>
    </div>
    <div id="findBoxContainer">
   				 <%
				if (result == null) {
				%>
        <h1>Find Your Password</h1>
        <div id="findBox">
            <h4>비밀번호를 잊어버리셨나요?</h4>
            <h4>회원 정보에 등록되어있는 아이디를 입력해주세요.</h4>
            <form action="./findPW" method="post" onsubmit="return email()">
            <div class="inputInfoBox">
                <input type="text" class="inputInfo" id="id" name="id" placeholder="아이디를 입력하세요." required="required">
            </div>
            <button type="submit" id="findSubmit">SUBMIT</button>
            </form>
           <%
				} else if (result.equals("1")) {
				%>
				        <h1>Find Your ID</h1>
       			 <div id="findBox">
            <h4>해당하는 비밀번호는</h4>
            <span><%=request.getAttribute("pw")%>.</span>
            <h4>입니다.</h4>
					
				<button type="submit" id="joinSubmit"
					onclick="location.href='./login.jsp'">SIGN IN</button>
			 </div>
				<%
				} else if (result.equals("2")) {
				%>
				<h1>Error</h1>
				<h2>해당하는 계정이 없습니다.</h2>
				<a href="./findPW.jsp"></a><h3>Try again</h3></a> <!-- 링크 걸기 -->
				<%
				}
				%>
        </div>
    </div>
</div>
<div class="marginSpace"></div>
<div id="footerSpace"></div>
</body>
</html>



















<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Find PW</title>
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
				<%
				if (result == null) {
				%>
				<h1 style="text-align: center; padding-bottom: 5px;">비밀번호 찾기</h1>
				<form action="./findPW" method="post">
					<div class="inputInfoBox">
						<input type="text" name="id" id="id" class="inputInfo"
							required="required" placeholder="ID를 입력하세요.">
					</div>
					<button type="submit" id="joinSubmit">Find Password</button>
				</form>
				<%
				} else if (result.equals("1")) {
				%>
				<h1>
					Password is
					<%=request.getAttribute("pw")%>.
				</h1>
				<button type="submit" id="joinSubmit"
					onclick="location.href='./login.jsp'">Login</button>
				<%
				} else if (result.equals("2")) {
				%>
				<h1>Error</h1>
				<h2>No password registered as ID</h2>
				<h3>Try again</h3> <!-- 링크 걸기 -->
				<%
				}
				%>
			</div>
		</div>
	</div>
</body>
</html>