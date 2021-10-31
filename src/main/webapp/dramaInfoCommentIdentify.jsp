<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
  <link rel="stylesheet" href="./css/myComment.css">
</head>
<body>
<c:set var="pageName" value="dramaInfoCommentIdentify" scope="request" />
								<c:set var="PAGENUMBER" value="10" scope="request"></c:set>
								<fmt:parseNumber integerOnly="true" var="totalPage"
	value="${totalCount / PAGENUMBER }" scope="request" />
<c:if test="${totalCount % PAGENUMBER ne 0 }">
	<c:set var="totalPage" value="${totalPage + 1 }" scope="request" />
</c:if>
<c:if test="${page % PAGENUMBER ne 0 }">
	<fmt:parseNumber integerOnly="true" var="startPage"
		value="${page / PAGENUMBER }" scope="request" />
	<c:set value="${startPage * PAGENUMBER + 1 }" var="startPage"
		scope="request" />
</c:if>
<c:if test="${page % PAGENUMBER eq 0 }">
	<c:set value="${page - (PAGENUMBER - 1) }" var="startPage"
		scope="request" />
</c:if>

<c:set value="${startPage + 4 }" var="endPage" scope="request" />
<c:if test="${startPage + 4 gt totalPage}">
	<c:set var="endPage" value="${totalPage }" scope="request" />
</c:if>

<div id="headerSpace">
</div>

<div id="myInfo">
  <div id="myInfoSideMenuContainer">
    <div id="myInfoSideMenu">
      <ul class="item_myInfoSideMenu">
        <li><a href="./myInfo"><img src="./img/info_id.png">내 정보</a></li>
        <li><a href="./actionInfoIdentify"><img src="./img/info_article.png">작성한 글</a></li>
        <li class="active"><a href="./actionInfoCommentIdentify"><img src="./img/info_comment.png" height="50px">작성한 댓글</a></li>
        <li><a href="./actionInfoLikeIdentify"><img src="./img/info_like.png">추천한 글</a></li>
        <li><a href="./myAccountDelete.jsp"><img src="./img/info_close.png">탈퇴하기</a></li>
      </ul>
    </div>
  </div>
  <div id="myInfoMainContainer">
    <div id="myInfoMain">
      <div id="myInfoMainContent_title">
        <img src="./img/myinfoItem.png" class="myInfoItemImg"><h1>작성한 댓글</h1>
        <hr class="styleHR6">
      </div>
      <button onclick="location.href='actionInfoCommentIdentify'">Action게시판</button>
<button onclick="location.href='dailyInfoCommentIdentify'">Daily게시판</button>
<button onclick="location.href='dramaInfoCommentIdentify'">Drama 게시판</button>	
<button onclick="location.href='fantasyInfoCommentIdentify'">Fantasy 게시판</button>	
<button onclick="location.href='romanceInfoCommentIdentify'">Romance 게시판</button>	
<button onclick="location.href='thrillerInfoCommentIdentify'">Thriller 게시판</button>	
<div id="info">
<button onclick="location.href='actionInfoIdentify'">내가 작성한 글 조회</button>
<button onclick="location.href='myInfo'">수정,탈퇴</button>
<button onclick="location.href='index'">메인메뉴로 돌아가기</button>
      <div id="myCommentListContainer"></div>
           <c:choose>
          <c:when test="${fn:length(list) > 0 }">
            <table id="myCommentList">
              <tbody>
              <tr>
                <td class="tableListhead no">No.</td>
                <td class="tableListhead">Comment</td>
              </tr>
          <c:forEach items="${list }" var="l">
              <tr>
                <td class="commentNumber" rowspan="3">
                댓글 번호 : ${l.dcno }
                </td>
                <td class="articleTitle">
                  <a href="dramaBoardDetail?dno=${l.dno }">글 번호 : ${l.dno }</a>
                </td>
              </tr>
              <tr>
                <td class="commentContent">
                  <c:set var="content" value="${l.dccontent } "/>
                  <c:if test="${fn:length(content) <= 30 }">
                  		<c:out value="${content }"/>
                  	</c:if>
                  	<c:if test="${fn:length(content) > 30 }">
                  		<c:out value="${fn:substring(content, 0, 21) }"/>....
                  	</c:if>
                </td>
              </tr>
               <tr>
                <td class="commentDate">
                   ${l.dcdate }
                </td>
              </tr>
              </c:forEach>
                    <!--임시 셀-->
                    <!--임시 셀 끝-->
             <tr>
                        <td id="boardPage" colspan="2">
                            <div id="pageValueContainer">
                            <c:choose>
                            <c:when test="${startPage == 0}">
                     1
                     </c:when>
                     <c:otherwise>
                     <c:if test="${page > 5}">
                            <a href="./dramaInfoCommentIdentify?page=1" class="pageArrow">
                                <img class="leftArrow" src="./img/doublenext.png" alt="toStartPage" width="25px">
                                <img class="leftArrow" src="./img/doublenext2.png" alt="toStartPage" width="25px">
                            </a>
                            </c:if>
                     <c:if test="${startPage > 5}">
                            <a href="./dramaInfoCommentIdentify?page=${startPage - 5}" class="pageArrow">
                                <img class="leftArrow" src="./img/next.png" alt="toPreviousPage" width="25px">
                                <img class="leftArrow" src="./img/next2.png" alt="toPreviousPage" width="25px">
                            </a>
                            </c:if>
                            <c:forEach var="i" begin="${startPage}" end="${endPage}">
                               <c:choose>
                                   <c:when test="${page == i}">
                                   <a class="pageValue"><b>${i}</b></a>
                                  <c:set var="i" value="${i + 1}" />
                                  </c:when>
                                  <c:otherwise>
                                   <a href="./dramaInfoCommentIdentify?page=${i}" class="pageValue">${i}</a>
                                  </c:otherwise>
                               </c:choose>
                            </c:forEach>
                            <c:if test="${totalPage - page > 5}">
                            <a href="./dramaInfoCommentIdentify?page=${startPage + 5}" class="pageArrow">
                                <img src="./img/next.png" alt="toNextPage" width="25px">
                                <img src="./img/next2.png" alt="toNextPage" width="25px">
                            </a>
                            </c:if>
                            <c:choose>
                     <c:when test="${totalPage - endPage == 0}">
                     &nbsp;
                     </c:when>
                     <c:when test="${totalPage - page >= totalPage-(totalPage / 5) * 5}">
                            <a href="./dramaInfoCommentIdentify?page=${totalPage }" class="pageArrow">
                                <img src="./img/doublenext.png" alt="toEndPage" width="25px">
                                <img src="./img/doublenext2.png" alt="toEndPage" width="25px">
                            </a>
                            </c:when>
                     </c:choose>
                  </c:otherwise>
                  </c:choose>
                            </div>
                        </td>
                    </tr>
                    </tbody>
            </table>
            </c:when>
              <c:when test="${fn:length(list) == 0}">
                    <tr>
					<td class="noArticle" colspan="2">작성한 댓글이 없습니다.</td>
					</tr>
				</c:when>
                </c:choose>
    </div>
    </div>
</div>

<div class="marginSpace"></div>
<div id="footerSpace"></div>
</body>
</html>