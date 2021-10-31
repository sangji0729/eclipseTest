<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<html>
<head>
  <meta charset="UTF-8">
  <title>MyInfo - WebtoonReview</title>
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Limelight&family=Londrina+Solid&display=swap');
  </style>
  <link rel="stylesheet" href="./css/base.css">
  <link rel="stylesheet" href="./css/myInfo.css">
  <style>
    #infoTable {
      display: table;
      /* border-bottom: 1px #000000 solid; */
      /* height: 62px; */
      /* border: 1px #04bfad solid; */
    }

    .infoTableRow {
      display: table-row;
      /* border: 1px #000000 solid; */
    }

    .infoTableCell {
      display: table-cell;
      /* border-bottom: 1px #000000 solid; */
    }

    .infoTableCell.cell1 {
      background: #04BFAD;
      color: white;
      height: 64px;
      vertical-align: middle;
      text-align: center;
      width: 121px;
      font-weight: bolder;
    }

    .infoTableCell.cell2 {
      border-bottom: 1px #ffffff solid;
      width: 500px;
      background: #eaeaea;
      vertical-align: middle;
      padding-left: 15px;
    }

    #toUpdateInfoSpace {
      margin-top: 30px;
      text-align: right;
      width: 636px;
    }
    
    #toUpdateInfo {
      width: 170px;
      height: 50px;
      background: #04bfad;
      color: white;
      border: none;
      border-radius: 55px;
      font-size: 18px;
    }

    #toUpdateInfo:hover {
      background: #003857;
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
        <li class="active"><a href="./myInfo"><img src="./img/info_id.png">내 정보</a></li>
        <li><a href="./actionInfoIdentify"><img src="./img/info_article.png">작성한 글</a></li>
        <li><a href="./actionInfoCommentIdentify"><img src="./img/info_comment.png" height="50px">작성한 댓글</a></li>
        <li><a href="./actionInfoLikeIdentify"><img src="./img/info_like.png">추천한 글</a></li>
        <li><a href="./myAccountDelete.jsp"><img src="./img/info_close.png">탈퇴하기</a></li>
      </ul>
    </div>
  </div>
  <div id="myInfoMainContainer">
    <div id="myInfoMain">
      <div id="myInfoMainContent_title">
        <img src="./img/myinfoItem.png" class="myInfoItemImg"><h1>나의 정보</h1>
        <hr class="styleHR6">
      </div>
      		 <form action="./transForMyInfo" method="post" style="display: contents;">
            <div id="infoTable">
              <div class="infoTableRow">
                <div class="infoTableCell cell1">아이디</div>
                  <div class="infoTableCell cell2">${my.id }</div>
              </div>
              <div class="infoTableRow">
                <div class="infoTableCell cell1">이름</div>
                <div class="infoTableCell cell2">${my.name }</div>
              </div>
              <div class="infoTableRow">
                <div class="infoTableCell cell1">이메일 주소</div>
                <div class="infoTableCell cell2">${my.email }</div>
              </div>
              </div>
              <input type="hidden" name="myInfoPass" value="myInfoPass">
            <div id="toUpdateInfoSpace">
              <button id="toUpdateInfo">수정하기</button>
            </div>
            </form>
    </div>
    </div>
</div>

<div class="marginSpace"></div>
<div id="footerSpace"></div>
</body>
</html>