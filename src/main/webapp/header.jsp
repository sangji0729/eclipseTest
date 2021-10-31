<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>	
<head>
<script
  src="https://code.jquery.com/jquery-3.6.0.min.js"
  integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
  crossorigin="anonymous"></script>
<script src='./js/header.js'></script>

<style>
 @import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Limelight&family=Londrina+Solid&display=swap');
</style>
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/header.css">

</head>
<body>

<nav id="sideMenu">
    <div id="loginStatusContainer">
    	<c:choose>
    	<c:when test="${sessionScope.id eq null}">
    	<!-- 로그인 안했을 시 -->
    	 <div id="loginStatus"><p>로그인해주세요.</p></div>
        <div id="loginMenu">
         <a href="./login">Sign Up</a>
        </div>
        </c:when>
        <c:when test="${sessionScope.id ne null}">
    	<!-- 로그인 했을 시 -->
        <div id="loginStatus"><p>${sessionScope.id} 님</p></div>
        <div id="loginMenu">
            <a onclick="openMyInfoLogin()">MyInfo</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="./logout">Sign Out</a>
        </div>
        </c:when>
        </c:choose>
    </div>
    <div class="marginBar">
        <hr class="styleHR">
    </div>
    <div id="sideMenuContainer">
        <ul>
            <li><a href="./index">홈으로</a></li>
            <c:if test="${sessionScope.grade ne null}">
            <li><a href="./adminIndex">관리자 모드</a></li>
            </c:if>
            <li class='sideMenuItem'><a>액션</a>
                <ul>
                    <li class="sideMenuItem"><a href="./actionBoard">리뷰 보기</a></li>
                    <li class="sideMenuItem"><a href="./actionBoardWrite">리뷰 작성</a></li>
                </ul>
            </li>
            <li class='sideMenuItem'><a>일상</a>
                <ul>
                    <li class="sideMenuItem"><a href="./daily">리뷰 보기</a></li>
                    <li class="sideMenuItem"><a href="./dailyWrite">리뷰 작성</a></li>
                </ul>
            </li>
            <li class='sideMenuItem'><a>드라마</a>
                <ul>
                    <li class="sideMenuItem"><a href="./drama">리뷰 보기</a></li>
                    <li class="sideMenuItem"><a href="./dramaWrite">리뷰 작성</a></li>
                </ul>
            </li>
            <li class='sideMenuItem'><a>판타지</a>
                <ul>
                    <li class="sideMenuItem"><a href="./fantasy">리뷰 보기</a></li>
                    <li class="sideMenuItem"><a href="./fantasyWrite">리뷰 작성</a></li>
                </ul>
            </li>
            <li class='sideMenuItem'><a>로맨스</a>
                <ul>
                    <li class="sideMenuItem"><a href="./romanceBoard">리뷰 보기</a></li>
                    <li class="sideMenuItem"><a href="./romanceBoardWrite">리뷰 작성</a></li>
                </ul>
            </li>
            <li class='sideMenuItem'><a>스릴러</a>
                <ul>
                    <li class="sideMenuItem"><a href="./thriller">리뷰 보기</a></li>
                    <li class="sideMenuItem"><a href="./thrillerWrite">리뷰 작성</a></li>
                </ul>
            </li>
        </ul>
    </div>
</nav>

<div id="header">
    <div id="title">
        <a href="./index"><img src="./img/title1.png" alt="title"></a>
    </div>
    <div id="mainMenuContainer">
        <div class="mainMenuInner">
            <div id="mainMenuSpace">
               <div class="mainMenu"><a href="./actionBoard">액션</a></div>
                <div class="mainMenu"><a href="./daily">일상</a></div>
                <div class="mainMenu"><a href="./drama">드라마</a></div>
                <div class="mainMenu"><a href="./fantasy">판타지</a></div>
                <div class="mainMenu"><a href="./romanceBoard">로맨스</a></div>
                <div class="mainMenu"><a href="./thriller">스릴러</a></div>
            </div>
        </div>
    </div>
</div>
<div id="header2">
    <div id="title2">
        <a href="./index"><img src="./img/title3.png" alt="title"></a>
    </div>
    <div id="mainMenuContainer2">
        <div class="mainMenuInner2">
            <div id="mainMenuSpace2">
               <div class="mainMenu"><a href="./actionBoard">액션</a></div>
                <div class="mainMenu"><a href="./daily">일상</a></div>
                <div class="mainMenu"><a href="./drama">드라마</a></div>
                <div class="mainMenu"><a href="./fantasy">판타지</a></div>
                <div class="mainMenu"><a href="./romanceBoard">로맨스</a></div>
                <div class="mainMenu"><a href="./thriller">스릴러</a></div>
            </div>
        </div>
    </div>
</div>
<div id="myInfoEntrance">
    <div id="myInfoEntranceContainer">
        <div id="myInfoEntranceBox">
            <div class="XButtonSpace">
                <a onclick="openMyInfoLogin()"><div class="Button_X"></div></a>
            </div>
            <h1>My info</h1>
            <h4>개인 정보 보호를 위해 비밀번호를 확인합니다.</h4>
            <form action="./loginForMyInfo" method="post">
            <div id="inputPWBox">
                <div id="PWlogoSpace"><img src="./img/pw.png" height="35px" alt="Password"></div>
                <input type="password" id="inputPW" name="pw" placeholder="비밀번호를 입력하세요." required="required">
            </div>
            <button type="submit" id="myInfoEnterSubmit">Enter</button>
        	</form>
        </div>
    </div>
</div>
</div>
<div id="sideMenuSpace">
    <div id="sideMenuButton"></div>
    <div id="sideMenuButton_X"></div>
</div>
</body>