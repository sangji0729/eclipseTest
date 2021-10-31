<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>thriller</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<script src='./js/boardList.js'></script>
<style>
@import
	url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Limelight&family=Londrina+Solid&display=swap')
	;
</style>
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/boardList.css">
</head>
<body>
	<c:set var="pageName" value="actionBoard" scope="request" />
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

	<div id="headerSpace"></div>
	<div id="boardMain">
		<div id="boardListTitle">
			<span class="boardListTitleText"> 스릴러/호러 </span>
		</div>
		<div id="mainTopContainer">
			<div id="listPopular">
			
			<c:forEach items="${list2 }" var="l2" >
				<div class="article top">
					<div class="thumbnailBox">
					 <c:choose>
							<c:when test="${l2.tthumbnail ne null}">
                            <img class="thumbnail" src="./thumbnail/${l2.tthumbnail}" alt="thumbnail">
                            </c:when>
                            <c:otherwise>
							<img class="thumbnail" src="./thumbnail/noimage.png">
							</c:otherwise>
							</c:choose>
					</div>
					<div class="contentBox">
						<span style="font-size: 25px;"><b>${l2.ttitle }</b></span> <br> 내용
					<c:set var="content" value="${l2.tcontent } "/>
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
					<form action="./thrillerSearch" method="post" id="searchBar" class="formSearch">
						<select name="searchColumn" id="searchColumn">
							<option value="ttitle"
								<c:out value="${requestScope.search == 'ttitle'? 'selected':''}"/>>제목</option>
							<option value="tcontent"
								<c:out value="${requestScope.search == 'tcontent'? 'selected':''}"/>>내용</option>
							<option value="name"
								<c:out value="${requestScope.search == 'name'? 'selected':''}"/>>이름</option>
							<option value="id"
								<c:out value="${requestScope.searchC == 'id'? 'selected':''}"/>>ID</option>
						</select>
						<c:choose>
							<c:when test="${empty requestScope.searchWord}">
								<input type="text" id="searchWord" name="searchWord">
							</c:when>
							<c:otherwise>
								<input type="text" id="searchWord" name="searchWord"
									value="${requestScope.searchWord}">
							</c:otherwise>
						</c:choose>
						<button type="submit" id="searchSubmit">
							<img src="./img/search.png" height="50px" alt="search">
						</button>
					</form>
				</div>
			</div>
		</div>
		<div id="boardListContainer">
			<table id="boardList">
				<tbody>
					<c:choose>
						<c:when test="${fn:length(list) > 0}">
							<c:forEach items="${list }" var="list">
								<tr>
									<td class="articleThumbnail" rowspan="3"><c:choose>
											<c:when test="${list.tthumbnail ne null}">
												<img class="thumbnail" src="./thumbnail/${list.tthumbnail}"
													alt="thumbnail">
											</c:when>
											<c:otherwise>
												<img class="thumbnail" src="./thumbnail/noimage.png">
											</c:otherwise>
										</c:choose></td>
									<td class="articleTitle" colspan="8"><c:choose>
											<c:when test="${list.ttitle.length() > 45}">
												<a href="./thrillerBoardDetail?tno=${list.tno}&page=${page}">${fn:substring(list.ttitle, 0, 45)}…</a>
											</c:when>
											<c:otherwise>
												<a href="./thrillerBoardDetail?tno=${list.tno}&page=${page}">${list.ttitle}</a>
											</c:otherwise>
										</c:choose></td>
								</tr>
								<tr>
									<td class="articleContent" colspan="8"><c:set
											var='rcontent'
											value='${list.tcontent.replaceAll("\\\<.*?\\\>","")}' /> <c:choose>
											<c:when test="${tcontent.length() > 220}">${fn:substring(tcontent, 0, 220)}…</c:when>
											<c:otherwise>${tcontent}</c:otherwise>
										</c:choose></td>
								</tr>
								<tr>
									<td class="articleCount_Left"><img class="articleInfoLogo"
										src="./img/read.png" alt="comment" width="25px"></td>
									<td class="articleCount_Right"><span
										class="articleCountText"> 조회수 </span>&nbsp;<span
										class="valueText">${list.tcount}</span></td>
									<td class="articleComment_Left"><img
										class="articleInfoLogo" src="./img/comment.png" alt="comment"
										width="24px"></td>
									<td class="articleComment_Right"><span
										class="articleCommentText"> 댓글 </span>&nbsp;<span
										class="valueText">${list.commentcount}</span></td>
									<td class="articleLike_Left"><img class="articleInfoLogo"
										src="./img/unlike.png" alt="unlike" width="25px"></td>
									<td class="articleLike_Right"><span
										class="articleLikeText"> 추천 </span>&nbsp;<span
										class="valueText">${list.tlike }</span></td>
									<td class="articleDate">${list.tdate}</td>
									<td class="articleAuthor"><span class="articleAuthorText">${list.name}(${list.id })</span>
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
							<button id="WriteButton"
								onclick="location.href='./thrillerWrite.jsp'">리뷰 작성</button>
						</td>
					</tr>
					<tr>
						<td id="boardPage" colspan="9">
							<div id="pageValueContainer">
								<c:choose>
									<c:when test="${startPage == 0}">
                     1
                     </c:when>
									<c:otherwise>
										<c:if test="${page > 5}">
											<a href="./thriller?page=1" class="pageArrow"> <img
												class="leftArrow" src="./img/doublenext.png"
												alt="toStartPage" width="45px"> <img class="leftArrow"
												src="./img/doublenext2.png" alt="toStartPage" width="45px">
											</a>
										</c:if>
										<c:if test="${startPage > 5}">
											<a href="./thriller?page=${startPage - 5}" class="pageArrow">
												<img class="leftArrow" src="./img/next.png"
												alt="toPreviousPage" width="45px"> <img
												class="leftArrow" src="./img/next2.png" alt="toPreviousPage"
												width="45px">
											</a>
										</c:if>
										<c:forEach var="i" begin="${startPage}" end="${endPage}">
											<c:choose>
												<c:when test="${page == i}">
													<a class="pageValue"><b>${i}</b></a>
													<c:set var="i" value="${i + 1}" />
												</c:when>
												<c:otherwise>
													<a href="./thriller?page=${i}" class="pageValue">${i}</a>
												</c:otherwise>
											</c:choose>
										</c:forEach>
										<c:if test="${totalPage - page > 5}">
											<a href="./thriller?page=${startPage + 5}" class="pageArrow">
												<img src="./img/next.png" alt="toNextPage" width="45px">
												<img src="./img/next2.png" alt="toNextPage" width="45px">
											</a>
										</c:if>
										<c:choose>
											<c:when test="${totalPage - endPage == 0}">
                     &nbsp;
                     </c:when>
											<c:when
												test="${totalPage - page >= totalPage-(totalPage / 5) * 5}">
												<a href="./thriller?page=${totalPage }" class="pageArrow">
													<img src="./img/doublenext.png" alt="toEndPage"
													width="45px"> <img src="./img/doublenext2.png"
													alt="toEndPage" width="45px">
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
	<div id="footerSpace"></div>
</body>
</html>