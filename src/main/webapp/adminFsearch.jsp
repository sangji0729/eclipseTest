<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>	    

 <c:if test="{${sessionScope.grade eq null }">
<c:redirect url="./index" />
</c:if>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>WebtoonReview - 관리자 페이지</title>
</head>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
</style>
<link rel="stylesheet" href="./css/admin.css">
<body>
<div id="admin">
<nav id="adminSide">
  <div id="adminSideMenuTop">
    <div class="titleLogo">
        <img src="./img/title2.png" alt="title">
    </div>
    <div class="pageTitle">
        <h3>[ 관리자 모드 ]</h3>
    </div>
    <div id="adminIdPrint">
        ${sessionScope.id} 님
    </div>
    <hr class="styleHR4">
  </div>
      <div id="adminSideMenuContainer">
          <div id="adminSideMenu">
              <ul class="item_AdminSideMenu">
                  <li><a href="./adminMember"><img src="./img/memberControl.png">회원 관리</a></li>
                  <li class="active"><a href="./adminPost"><img src="./img/articleControl.png">게시글 관리</a></li>
                  <li><a href="./admin"><img src="./img/log.png">로그 관리</a></li>
                  <li><a href="./adminPick"><img src="./img/article2.png">Editor's Pick</a></li>
                  <li><a href="./index"><img src="./img/exit.png">나가기</a></li>
              </ul>
          </div>
      </div>
</nav>
<div id="adminMain">
    <div id="adminMainContainer">
        <div id="adminMainContent">



					<select id="select">

						<option value="Action"
							onclick="location.href='adminPost?category=Action'" id="Action">Action</option>
						<option value="Daily"
							onclick="location.href='adminPost?category=Daily'" id="Daily">Daily</option>
						<option value="Drama"
							onclick="location.href='adminPost?category=Drama'" id="Drama">Drama</option>
						<option value="Fantasy"
							onclick="location.href='adminPost?category=Fantasy'" id="Fantasy">Fantasy</option>
						<option value="Romance"
							onclick="location.href='adminPost?category=Romance'" id="Romance">Romance</option>
						<option value="Thriller"
							onclick="location.href='adminPost?category=Thriller'"
							id="Thriller">Thriller</option>
					</select>



					<c:choose>
						<c:when test="${fn:length(list) > 0 }">
							<table style="width: 100%; font-size: smaller;">
								<tr>
									<th>no</th>
									<th>section</th>
									<th>title</th>
									<th>content</th>
									<th>id</th>
									<th>filename</th>
									<th>date</th>
									<th>totalcount</th>
									<th>like</th>
									<th></th>
								</tr>
								<c:forEach items="${list }" var="l">
									<tr>
										<td>${l.no }</td>
										<td>${l.table }</td>
										<td id="title">
											<!-- title 내용 줄여주는 것 --> <c:set var="string1"
												value="${l.title}" /> <c:set var="string2"
												value="${fn:substring(string1, 0, 9)}" /> <c:choose>
												<c:when test="${fn:length(string1) >9 }">
													<a href="./${l.table }Detail?${l.viewName }no=${l.no }">${string2 }.....</a>
												</c:when>
												<c:otherwise>
													<a href="./${l.table }Detail?${l.viewName }no=${l.no }">${string2 }</a>
												</c:otherwise>
											</c:choose>
										</td>

										<td id="content">
											<!-- content 내용 줄여주는 것 --> <c:set var="string1"
												value="${l.content }" /> <c:set var="string2"
												value="${fn:substring(string1, 0, 7)}" /> ${string2 }
										</td>

										<td>${l.id }</td>
										<td id="content">
											<!-- thumbnail 이름 내용 줄여주는 것 --> <c:set var="string1"
												value="${l.fileName}" /> <c:set var="string2"
												value="${fn:substring(string1, 0, 7)}" /> <c:if
												test="${string1 ne null }">
										${string2 }~.file
    					 	</c:if>

										</td>
										<td>${l.joinDate }</td>
										<td>${l.count }</td>
										<td>${l.like}</td>


										<td><input type="hidden" id="user" name="user1"
											value="2" />
											<form action="${l.table}Delete?${l.viewName }no=${l.no}"
												method="post">
												<input type="hidden" id="user" name="lowertable"
													value="${l.table}" /> <input type="hidden" id="user"
													name="lowerTC" value="${l.viewName }" /> <input
													type="hidden" id="user" name="number" value="${l.no}" />

												<button class="delete" onclick=>delete</button>
											</form></td>
									</tr>
								</c:forEach>
							</table>
			-
					</c:when>
						<c:otherwise>
							<%-- listing out from the searchlist --%>

							<table style="width: 100%; font-size: smaller;">
								<tr>
									<th>no</th>
									<th>section</th>
									<th>title</th>
									<th>content</th>
									<th>id</th>
									<th>filename</th>
									<th>date</th>
									<th>totalcount</th>
									<th>like</th>
									<th></th>
								</tr>
								<c:forEach items="${slist }" var="l">
									<tr>
										<td>${l.no }</td>
										<td>${l.table }</td>
										<td id="title">
											<!-- title 내용 줄여주는 것 --> <c:set var="string1"
												value="${l.title}" /> <c:set var="string2"
												value="${fn:substring(string1, 0, 9)}" /> <c:choose>
												<c:when test="${fn:length(string1) >9 }">
													<a href="./${l.table }Detail?${l.viewName }no=${l.no }">${string2 }.....</a>
												</c:when>
												<c:otherwise>
													<a href="./${l.table }Detail?${l.viewName }no=${l.no }">${string2 }</a>
												</c:otherwise>
											</c:choose>
										</td>

										<td id="content">
											<!-- content 내용 줄여주는 것 --> <c:set var="string1"
												value="${l.content }" /> <c:set var="string2"
												value="${fn:substring(string1, 0, 7)}" /> ${string2 }
										</td>

										<td>${l.id }</td>
										<td id="content">
											<!-- thumbnail 이름 내용 줄여주는 것 --> <c:set var="string1"
												value="${l.fileName}" /> <c:set var="string2"
												value="${fn:substring(string1, 0, 7)}" /> <c:if
												test="${string1 ne null }">
										${string2 }~.file
    					 	</c:if>

										</td>
										<td>${l.joinDate }</td>
										<td>${l.count }</td>
										<td>${l.like}</td>
										<td>delete</td>
								</c:forEach>
							</table>
		--
						
					</c:otherwise>
					</c:choose>

				</div>
			</div>
		</div>
	</div>
</body>
</html>