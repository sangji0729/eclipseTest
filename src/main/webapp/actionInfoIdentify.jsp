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
  <link rel="stylesheet" href="./css/myArticle.css">
</head>
<body>
 <c:set var="pageName" value="actionInfoIdentify" scope="request" />
								<c:set var="PAGENUMBER" value="5" scope="request"></c:set>
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
        <li class="active"><a href="./actionInfoIdentify"><img src="./img/info_article.png">작성한 글</a></li>
        <li><a href="./actionInfoCommentIdentify"><img src="./img/info_comment.png" height="50px">작성한 댓글</a></li>
        <li><a href="./actionInfoLikeIdentify"><img src="./img/info_like.png">추천한 글</a></li>
        <li><a href="./myAccountDelete.jsp"><img src="./img/info_close.png">탈퇴하기</a></li>
      </ul>
    </div>
  </div>
  <div id="myInfoMainContainer">
    <div id="myInfoMain">
      <div id="myInfoMainContent_title">
        <img src="./img/myinfoItem.png" class="myInfoItemImg"><h1>작성한 글</h1>
        <hr class="styleHR6">
      </div>
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
		<button onclick="location.href='actionInfoCommentIdentify.jsp'">내가 작성한 댓글 조회</button>
<button onclick="location.href='myInfo'">수정,탈퇴</button>
		<button onclick="location.href='index'">메인메뉴로 돌아가기</button>
	</div>
      
      
      <div id="myArticleListContainer"></div>
               <c:choose>
          		<c:when test="${fn:length(list) > 0 }">
            <table id="myArticleList">
              <tbody>
              <tr>
                <td class="tableListhead">No.</td>
                <td class="tableListhead" colspan="9">Article</td>
              </tr>
              <c:forEach items="${list }" var="l" >
              <tr>
                <td class="articleNumber"  rowspan="3">
                 ${l.ano }
                </td>
                <td class="articleThumbnail" rowspan="3">
                  			<c:choose>
							<c:when test="${l.athumbnail ne null}">
                            <img class="thumbnail" src="./thumbnail/${l.athumbnail}" alt="thumbnail">
                            </c:when>
							<c:otherwise>
							<img class="thumbnail" src="./thumbnail/noimage.png">
							</c:otherwise>
							</c:choose>
                </td>
                <td class="articleTitle" colspan="9">
                  <a href="actionBoardDetail?ano=${l.ano }">${l.atitle }</a>
                </td>
              </tr>
              <tr>
                <td class="articleContent" colspan="9">
                 <c:set var="content" value="${l.acontent } "/>
                  	<c:if test="${fn:length(content) <= 30 }">
                  		<c:out value="${content }"/>
                  	</c:if>
                  	<c:if test="${fn:length(content) > 30 }">
                  		<c:out value="${fn:substring(content, 0, 15) }"/>
                  	</c:if>
                </td>
              </tr>
              <tr>
                <td class="articleCount_Left">
                  <img class="articleInfoLogo" src="./img/read.png" alt="comment" width="25px">
                </td>
                <td class="articleCount_Right">
                  <span class="articleCountText"> 조회수 </span>&nbsp;<span class="valueText">${l.acount }</span>
                </td>
                <td class="articleComment_Left">
                  <img class="articleInfoLogo" src="./img/comment.png" alt="comment" width="24px">
                </td>
                <td class="articleComment_Right">
                  <span class="articleCommentText"> 댓글 </span>&nbsp;<span class="valueText">${l.commentcount }</span>
                </td>
                <td class="articleLike_Left">
                  <img class="articleInfoLogo" src="./img/unlike.png" alt="unlike" width="25px">
                </td>
                <td class="articleLike_Right">
                  <span class="articleLikeText"> 추천 </span>&nbsp;<span class="valueText">${l.alike }</span>
                </td>
                <td class="articleDate">
                  ${l.adate }
                </td>
                <td class="articleAuthor">
                  <span class="articleAuthorText"> ${l.name }  </span>
                </td>
              </tr>
              </c:forEach>
                    <!--임시 셀-->
                    <!--임시 셀 끝-->
              <tr>
                <td id="boardPage" colspan="9">
                  <div id="pageValueContainer">
                            <c:choose>
                            <c:when test="${startPage == 0}">
                     1
                     </c:when>
                     <c:otherwise>
                     <c:if test="${page > 5}">
                            <a href="./actionInfoIdentify?page=1" class="pageArrow">
                                <img class="leftArrow" src="./img/doublenext.png" alt="toStartPage" width="25px">
                                <img class="leftArrow" src="./img/doublenext2.png" alt="toStartPage" width="25px">
                            </a>
                            </c:if>
                     <c:if test="${startPage > 5}">
                            <a href="./actionInfoIdentify?page=${startPage - 5}" class="pageArrow">
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
                                   <a href="./actionInfoIdentify?page=${i}" class="pageValue">${i}</a>
                                  </c:otherwise>
                               </c:choose>
                            </c:forEach>
                            <c:if test="${totalPage - page > 5}">
                            <a href="./actionInfoIdentify?page=${startPage + 5}" class="pageArrow">
                                <img src="./img/next.png" alt="toNextPage" width="25px">
                                <img src="./img/next2.png" alt="toNextPage" width="25px">
                            </a>
                            </c:if>
                            <c:choose>
                     <c:when test="${totalPage - endPage == 0}">
                     &nbsp;
                     </c:when>
                     <c:when test="${totalPage - page >= totalPage-(totalPage / 5) * 5}">
                            <a href="./actionInfoIdentify?page=${totalPage }" class="pageArrow">
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
					<td class="noArticle" colspan="10">작성한 글이 없습니다.</td>
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