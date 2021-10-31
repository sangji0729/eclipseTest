<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>	    

<c:if test="{${sessionScope.id eq null}">
<c:redirect url="./index" />
</c:if>
<c:if test="{${sessionScope.myInfoPass eq null}">
<c:redirect url="./index" />
</c:if>   
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>MyInfo - WebtoonReview</title>
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Limelight&family=Londrina+Solid&display=swap');
  </style>
  <link rel="stylesheet" href="./css/base.css">
  <link rel="stylesheet" href="./css/myInfo.css">
  <style>
    #leaveConfirmContent {
      border: 5px #ff4a4a dashed;
      width: 495px;
      padding: 30px;
      padding-bottom: 55px;
    }

    #leaveCofirmContent h3 {

    }

    #leaveCofirmContent span {

    }

    #accountDeleteSpace {
      margin-top: 30px;
      text-align: right;
      width: 565px;
    }
    
    #accountDelete {
      width: 170px;
      height: 50px;
      background: #04bfad;
      color: white;
      border: none;
      border-radius: 55px;
      font-size: 18px;
    }

    #accountDelete:hover {
      background: #d20606;
    }

  </style>
</head>
<body>
<div id="headerSpace">
</div>

<div id="myInfo">
  <div id="myInfoSideMenuContainer">
    <div id="myInfoSideMenu">
      <ul class="item_myInfoSideMenu">
		<li><a href="./myInfo"><img src="./img/info_id.png">³» Á¤º¸</a></li>
        <li><a href="./actionInfoIdentify"><img src="./img/info_article.png">ÀÛ¼ºÇÑ ±Û</a></li>
        <li><a href="./actionInfoCommentIdentify"><img src="./img/info_comment.png" height="50px">ÀÛ¼ºÇÑ ´ñ±Û</a></li>
        <li><a href="./actionInfoLikeIdentify"><img src="./img/info_like.png">ÃßÃµÇÑ ±Û</a></li>
        <li class="active"><a href="#"><img src="./img/info_close.png">Å»ÅðÇÏ±â</a></li>
      </ul>
    </div>
  </div>
</div>

  <div id="myInfoMainContainer">
    <div id="myInfoMain">
      <div id="myInfoMainContent_title">
        <img src="./img/info_close2.png" class="myInfoItemImg"><h1>Å»ÅðÇÏ±â</h1>
        <hr class="styleHR6">
      </div>

            <div id="leaveConfirmContent">
              <h3>Á¤¸» Å»ÅðÇÏ½Ã°Ú½À´Ï±î?</h3>
              <span>Å»ÅðÇÑ °èÁ¤Àº º¹±¸ÇÒ ¼ö ¾ø½À´Ï´Ù.</span>
            </div>

            <div id="accountDeleteSpace">
              <button id="accountDelete" onclick="location.href='./deleteAccount'">Å»ÅðÇÏ±â</button>
            </div>
    </div>
  </div>

<div class="marginSpace"></div>
<div id="footerSpace"></div>
</body>
</html>