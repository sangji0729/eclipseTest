<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:if test="${boardList eq null }">
	<!-- 서블릿을 지나와야 boardList가 생성됨 -->
	<!-- boardList가 없다면 다시 서블릿으로 이동 -->
	<c:redirect url="./drama" />
</c:if>
<c:if test="${empty requestScope.searchWord}">
	<c:redirect url="./drama" />
</c:if>
<c:if test="${param.searchWord eq ''} || ${requestScope.searchWord eq ''}">
	<c:redirect url="./drama" />
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
            <span class="boardListTitleText"> 드라마 </span>
        </div>
        <div id="mainTopContainer">
            <div id="listPopular">
                <div class="article top">
                    <div class="thumbnailBox">
                        <img class="thumbnail" src="./img/test.jpg" alt="thumbnail">
                    </div>
                    <div class="contentBox">
                        <span style="font-size:25px;"><b>제목</b></span>
                        <br>
                        어린 왕자의 별에는 여느 별에나 그렇듯이 좋은 풀과 나쁜 풀이 있었다. 따라서 좋은 풀의 좋은 씨와 나쁜 풀의 나쁜 씨가 있었다. 그러나 씨앗들은 보이지 않는다. 씨앗들은 땅속에 숨어 잠을 자고 있다가 그 중 하나에게 문득 깨어나고 싶은 생각이 든다.
                    </div>
                </div>
                <div class="topArticle top">
                    <div class="thumbnailBox">
                        <img class="thumbnail" src="./img/test.jpg" alt="thumbnail">
                    </div>
                    <div class="contentBox">
                        <span style="font-size:35px;"><b>제목</b></span>
                        <br>
                        그러면 그 씨앗은 기지개를 켜고, 태양을 향해 처음엔 머뭇거리면서 그 아름답고 연약한 새싹을 내민다. 무나 장미나무의 어린 싹이면 마음껏 자라도록 내버려두어도 괜찮다. 그러나 나쁜 식물의 싹이면 그걸 알아차리자마자 뽑아 버려야 한다. 그런데 어린 왕자의 별에는 무서운 씨가 있었으니......
                    </div>
                </div>
                <div class="article top">
                    <div class="thumbnailBox">
                        <img class="thumbnail" src="./img/test.jpg" alt="thumbnail">
                    </div>
                    <div class="contentBox">
                        <span style="font-size:25px;"><b>제목</b></span>
                        <br>
                        그것은 바오밥나무의 씨였다. 그 별의 흙엔 바오밥나무의 씨가 들끓었다. 그런데 바오밥나무는 너무 늦게 손을 쓰면 그땐 정말 처치할 수 없게 된다. 나무가 온 별을 다 차지하고, 그 뿌리로 별 깊숙이 구멍을 뚫는다.
                    </div>
                </div>
            </div>
            <div class="marginBar">
                <hr class="styleHR2">
            </div>
            <div id="searchBarBox">
                <div id="searchBar">
                	<form action="./dramaSearch" method="post">
                    <select name="searchColumn" id="searchColumn">
                  	 <option value="dtitle"
							<c:out value="${searchColumn == 'dtitle'? 'selected':''}"/>>제목</option>
						<option value="dcontent"
							<c:out value="${searchColumn == 'dcontent'? 'selected':''}"/>>내용</option>
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
            <div id="searchBarBox">
                <div id="searchBar">
                	<form action="./dramaSearch" method="post" class="formSearch">
                    <select name="searchColumn" id="searchColumn">
                   <option value="dtitle"
							<c:out value="${requestScope.searchColumn == 'dtitle'? 'selected':''}"/>>제목</option>
						<option value="dcontent"
							<c:out value="${requestScope.searchColumn == 'dcontent'? 'selected':''}"/>>내용</option>
						<option value="name"
							<c:out value="${requestScope.searchColumn == 'name'? 'selected':''}"/>>이름</option>
						<option value="id"
							<c:out value="${requestScope.searchColumn == 'id'? 'selected':''}"/>>아이디</option>
					</select>
					<c:choose>
						<c:when test="${empty requestScope.searchWord}">
                    		<input type="text" id="searchWord" name="searchWord">
                    	</c:when>
                    	<c:otherwise>
							<input type="text" id="searchWord" name="searchWord" value="${requestScope.searchWord}">
						</c:otherwise>
					</c:choose>
                    <button type="submit" id="searchSubmit"><img src="./img/search.png" height="50px" alt="search"></button>
                	</form>
                </div>
            </div>
        </div>
        <div id="boardListContainer">
            <table id="boardList">
                <tbody>
            <c:choose>
			<c:when test="${fn:length(boardList.list) > 0}">
			<c:forEach items="${boardList.list }" var="list">
                    <tr>
                        <td class="articleThumbnail" rowspan="3">
                        	<c:choose>
							<c:when test="${list.dthumbnail ne null}">
                            <img class="thumbnail" src="./thumbnail/${list.dthumbnail}" alt="thumbnail">
                            </c:when>
							<c:otherwise>
							<img class="thumbnail" src="./thumbnail/noimage.png">
							</c:otherwise>
							</c:choose>
                        </td>
                        <td class="articleTitle" colspan="8">
                           <c:choose>
							<c:when test="${list.dtitle.length() > 45}">
							<a href="./dramaBoardDetail?dno=${list.dno}&pageNo=${pageNo}">${fn:substring(list.dtitle, 0, 45)}…</a>
							</c:when>
							<c:otherwise>
							<a href="./dramaBoardDetail?dno=${list.dno}&pageNo=${pageNo}">${list.dtitle}</a>
							</c:otherwise>
							</c:choose>
                        </td>
                    </tr>
                    <tr>
                        <td class="articleContent" colspan="8">
                            <c:set var='dcontent' value='${list.dcontent.replaceAll("\\\<.*?\\\>","")}' />
								<c:choose>
								<c:when test="${dcontent.length() > 220}">${fn:substring(dcontent, 0, 220)}…</c:when>
								<c:otherwise>${dcontent}</c:otherwise>
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
                        <img class="articleInfoLogo" width="25px"
                        <c:if test="${likeList ne null}">
                         	<c:forEach var="like" items="${likeList}">
	  								<c:if test="${like.dno eq list.dno}">
		  								 src="./img/like.png" alt="like" 
	  								</c:if>
  							</c:forEach>
  						</c:if>
  						src="./img/unlike.png" alt="unlike">
                        </td>
                        <td class="articleLike_Right">
                             <span class="articleLikeText"> 추천 </span>&nbsp;<span class="valueText">${list.dlike }</span>
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
                            <button id="WriteButton" onclick="location.href='./dramaWrite.jsp'">리뷰 작성</button>
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
                            <a href="./dramaSearch?searchWord=${searchWord}&searchColumn=${searchColumn}&pageNo=1" class="pageArrow">
                                <img class="leftArrow" src="./img/doublenext.png" alt="toStartPage" width="45px">
                                <img class="leftArrow" src="./img/doublenext2.png" alt="toStartPage" width="45px">
                            </a>
                            </c:if>
							<c:if test="${boardList.startPage > 5}">
                            <a href="./dramaSearch?searchWord=${searchWord}&searchColumn=${searchColumn}&pageNo=${boardList.startPage - 5}" class="pageArrow">
                                <img class="leftArrow" src="./img/next.png" alt="toPreviousPage" width="45px">
                                <img class="leftArrow" src="./img/next2.png" alt="toPreviousPage" width="45px">
                            </a>
                            </c:if>
                            <c:forEach var="i" begin="${boardList.startPage}" end="${boardList.endPage}">
                            <a href="./dramaSearch?searchWord=${searchWord}&searchColumn=${searchColumn}&pageNo=${i}" class="pageValue">${i}</a>
                            </c:forEach>
                            <c:if test="${boardList.totalPages - pageNo > 5}">
                            <a href="./dramaSearch?searchWord=${searchWord}&searchColumn=${searchColumn}&pageNo=${boardList.startPage + 5}" class="pageArrow">
                                <img src="./img/next.png" alt="toNextPage" width="45px">
                                <img src="./img/next2.png" alt="toNextPage" width="45px">
                            </a>
                            </c:if>
                            <c:choose>
							<c:when test="${boardList.totalPages - boardList.endPage == 0}">
							&nbsp;
							</c:when>
							<c:when test="${boardList.totalPages-pageNo >= boardList.totalPages-(boardList.totalPages%5)*5}">
                            <a href="./dramaSearch?searchWord=${searchWord}&searchColumn=${searchColumn}&pageNo=${boardList.totalPages}" class="pageArrow">
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