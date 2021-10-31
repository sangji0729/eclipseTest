<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${boardList eq null }">
	<!-- 서블릿을 지나와야 boardList가 생성됨 -->
	<!-- boardList가 없다면 다시 서블릿으로 이동 -->
	<c:redirect url="./fantasy" />
</c:if>
<c:if test="${empty requestScope.searchWord}">
	<c:redirect url="./fantasy" />
</c:if>
<c:if test="${param.searchWord eq ''} || ${requestScope.searchWord eq ''}">
	<c:redirect url="./fantasy" />
</c:if>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>장르 - WebtoonReview</title>
<script
  src="https://code.jquery.com/jquery-3.6.0.min.js"
  integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
  crossorigin="anonymous"></script>
<script src='./js/boardList.js'></script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Limelight&family=Londrina+Solid&display=swap');
</style>
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/boardList.css">

</head>
<body>
	<c:set var="pageNo"
		value="${empty requestScope.pageNo? '1':requestScope.pageNo }" />
	<c:set var="pageNo" value="${empty param.pageNo? '1':param.pageNo }" />
	<c:set var="searchWord"
		value="${empty requestScope.searchWord? param.searchWord:requestScope.searchWord}"
		scope="request" />
	<c:set var="searchColumn"
		value="${empty requestScope.searchColumn? param.searchColumn:requestScope.searchColumn}"
		scope="request" />
<div id="headerSpace">
</div>
    <div id="boardMain">

        <div id="boardListTitle">
            <span class="boardListTitleText"> 판타지 </span>
        </div>
        <div id="mainTopContainer">
            <div id="listPopular">
               <c:forEach items="${list2 }" var="l2" >
                <div class="article top">
                    <div class="thumbnailBox">
                       <c:choose>
							<c:when test="${l2.fthumbnail ne null}">
                            <img class="thumbnail" src="./thumbnail/${l2.fthumbnail}" alt="thumbnail">
                            </c:when>
							<c:otherwise>
							<img class="thumbnail" src="./thumbnail/noimage.png">
							</c:otherwise>
							</c:choose>
                    </div>
                    <div class="contentBox">
                        <span style="font-size:25px;"><a href="./fantasyBoardDetail?fno=${l2.fno}&pageNo=${pageNo}">${l2.ftitle}</a></span>
                        							
                        
                        <br>
                      <c:set var="content" value="${l2.fcontent } "/>
                  	<c:if test="${fn:length(content) <= 30 }">
                  		<c:out value="${content }"/>
                  	</c:if>
                  	<c:if test="${fn:length(content) > 30 }">
                  		<c:out value="${fn:substring(content, 0, 15) }"/>
                  	</c:if>
                    </div>
                </div>
               </c:forEach>
            </div>
            <div class="marginBar">
                <hr class="styleHR2">
            </div>
            <div id="searchBarBox">
                <div id="searchBar">
                	<form action="./fantasySearch" method="post">
                    <select name="searchColumn" id="searchColumn">
                  	 <option value="ftitle"
							<c:out value="${searchColumn == 'ftitle'? 'selected':''}"/>>제목</option>
						<option value="fcontent"
							<c:out value="${searchColumn == 'fcontent'? 'selected':''}"/>>내용</option>
						<option value="name"
							<c:out value="${searchColumn == 'name'? 'selected':''}"/>>이름</option>
						<option value="id"
							<c:out value="${searchColumn == 'id'? 'selected':''}"/>>아이디</option>
					</select>
							<input type="text" id="searchWord" name="searchWord" value="${searchWord}">
                    <button type="submit" id="searchSubmit"><img src="./img/search.png" height="50px" alt="search"></button>
                	</form>
                </div>
            </div>
        </div>
        <div id="boardListContainer">
            <table id="boardList">
                <tbody>
                <tr>
                <td colspan="9">
                검색결과 ${requestScope.hitcount}건&nbsp;&nbsp;
				<a href="./fantasy">돌아가기</a>
				</td>
            <c:choose>
			<c:when test="${fn:length(boardList.list) > 0}">
			<c:forEach items="${boardList.list }" var="list">
                    <tr>
                        <td class="articleThumbnail" rowspan="3">
                        	<c:choose>
							<c:when test="${list.fthumbnail ne null}">
                            <img class="thumbnail" src="./thumbnail/${list.fthumbnail}" alt="thumbnail">
                            </c:when>
							<c:otherwise>
							<img class="thumbnail" src="./thumbnail/noimage.png">
							</c:otherwise>
							</c:choose>
                        </td>
                        <td class="articleTitle" colspan="8">
                           <c:choose>
							<c:when test="${list.ftitle.length() > 45}">
							<a href="./fantasyBoardDetail?fno=${list.fno}&pageNo=${pageNo}">${fn:substring(list.ftitle, 0, 45)}…</a>
							</c:when>
							<c:otherwise>
							<a href="./fantasyBoardDetail?fno=${list.fno}&pageNo=${pageNo}">${list.ftitle}</a>
							</c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td class="articleContent" colspan="8">
                            <c:set var='fcontent' value='${list.fcontent.replaceAll("\\\<.*?\\\>","")}' />
								<c:choose>
								<c:when test="${fcontent.length() > 220}">${fn:substring(fcontent, 0, 220)}…</c:when>
								<c:otherwise>${fcontent}</c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td class="articleCount_Left">
                            <img class="articleInfoLogo" src="./img/read.png" alt="comment" width="25px">
                        </td>
                        <td class="articleCount_Right">
                            <span class="articleCountText"> 조회수 </span>&nbsp;<span class="valueText">${list.dcount}</span>
                        </td>
                        <td class="articleComment_Left">
                            <img class="articleInfoLogo" src="./img/comment.png" alt="comment" width="24px">
                        </td>
                        <td class="articleComment_Right">
                             <span class="articleCommentText"> 댓글 </span>&nbsp;<span class="valueText">${list.commentcount}</span>
                        </td>
                        <td class="articleLike_Left">
                            <img class="articleInfoLogo" src="./img/unlike.png" alt="unlike" width="25px">
                        </td>
                        <td class="articleLike_Right">
                             <span class="articleLikeText"> 추천 </span>&nbsp;<span class="valueText">1</span>
                        </td>
		                <td class="articleDate">
                            ${list.ddate}
                        </td>
                        <td class="articleAuthor">
                            <span class="articleAuthorText">${list.name}(${list.id })</span>
                        </td>
                    </tr>
                    </c:forEach>
					</c:when>
                    <c:when test="${fn:length(boardList.list) == 0}">
                    <tr>
					<td class="noArticle" colspan="9">게시글이 없습니다.</td>
					</tr>
				</c:when>
			</c:choose>
                    <tr>
                        <td id="toBoardWrite" colspan="9">
                            <button id="WriteButton" onclick="location.href='./fantasyWrite.jsp'">리뷰 작성</button>
                        </td>
                    </tr>
                    <tr>
             <td id="boardPage" colspan="9">
                            <div id="pageValueContainer">
                            <c:choose>
                            <c:when test="${boardList.startPage == 0}">
                     1
                     </c:when>
                     <c:otherwise>
                     <c:if test="${pageNo > 5}">
                            <a href="./fantasy?pageNo=1" class="pageArrow">
                                <img class="leftArrow" src="./img/doublenext.png" alt="toStartPage" width="45px">
                                <img class="leftArrow" src="./img/doublenext2.png" alt="toStartPage" width="45px">
                            </a>
                            </c:if>
                     <c:if test="${boardList.startPage > 5}">
                            <a href="./fantasy?pageNo=${boardList.startPage - 5}" class="pageArrow">
                                <img class="leftArrow" src="./img/next.png" alt="toPreviousPage" width="45px">
                                <img class="leftArrow" src="./img/next2.png" alt="toPreviousPage" width="45px">
                            </a>
                            </c:if>
                            <c:forEach var="i" begin="${boardList.startPage}" end="${boardList.endPage}">
                               <c:choose>
                                   <c:when test="${pageNo == i}">
                                   <a class="pageValue"><b>${i}</b></a>
                                  <c:set var="i" value="${i + 1}" />
                                  </c:when>
                                  <c:otherwise>
                                   <a href="./fantasy?pageNo=${i}" class="pageValue">${i}</a>
                                  </c:otherwise>
                               </c:choose>
                            </c:forEach>
                            <c:if test="${boardList.totalPages - pageNo > 5}">
                            <a href="./fantasy?pageNo=${boardList.startPage + 5}" class="pageArrow">
                                <img src="./img/next.png" alt="toNextPage" width="45px">
                                <img src="./img/next2.png" alt="toNextPage" width="45px">
                            </a>
                            </c:if>
                            <c:choose>
                     <c:when test="${boardList.totalPages - boardList.endPage == 0}">
                     &nbsp;
                     </c:when>
                     <c:when test="${boardList.totalPages-pageNo >= boardList.totalPages-(boardList.totalPages%5)*5}">
                            <a href="./fantasy?pageNo=${boardList.totalPages}" class="pageArrow">
                                <img src="./img/doublenext.png" alt="toEndPage" width="45px">
                                <img src="./img/doublenext2.png" alt="toEndPage" width="45px">
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
        </div>
    </div>
    <div class="marginSpace"></div>
    <div id="footerSpace">
    </div>
</body>
</html>