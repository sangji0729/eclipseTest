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


								<div  style="float: left;" onclick="location.href='adminPost?category=Action&page=1'" ><button>Action</button></div>
								<div  style="float: left;" onclick="location.href='adminPost?category=Daily&page=1'" ><button>Daily</button></div>
								<div  style="float: left;" onclick="location.href='adminPost?category=Drama&page=1'" ><button>Drama</button></div>
								<div  style="float: left;" onclick="location.href='adminPost?category=Fantasy&page=1'" ><button>Fantasy</button></div>
								<div  style="float: left;" onclick="location.href='adminPost?category=Romance&page=1'" ><button>Romance</button></div>
								<div  style="float: left;" onclick="location.href='adminPost?category=Thriller&page=1'" ><button>Thriller</button></div>

		
						
					<div class="adminSearchBox">
					<form action="./adminSearch" method="post">
					 <select name="tableName">
									<option value="Action">Action</option>
									<option value="Daily">Daily</option>
									<option value="Drama">Drama</option>
									<option value="Fantasy">Fantasy</option>
									<option value="Romance">Romance</option>
									<option value="Thriller">Thriller</option>
								</select> 
			<select name="searchColumn">
					
		
			<option value="title"
					<c:out value="${requestScope.searchColumn == 'title'? 'selected':''}"/>>제목</option>
				<option value="content"
					<c:out value="${requestScope.searchColumn == 'content'? 'selected':''}"/>>내용</option>
				<option value="name"
					<c:out value="${requestScope.searchColumn == 'name'? 'selected':''}"/>>이름</option>
				<option value="id"
					<c:out value="${requestScope.searchColumn == 'id'? 'selected':''}"/>>아이디</option>
		
		
			</select>
			
			<c:choose>
				<c:when test="${empty requestScope.searchInput}">
					<input type="text" id="search" name="searchWord">
				</c:when>
				<c:otherwise>
					<input type="text" id="search" name="searchWord"
						value="${requestScope.searchWord}">
				</c:otherwise>
			</c:choose>

			<button type="submit" class="search">검색</button>
		</form>
	</div>
				</div>		
				<c:choose>
							<c:when test="${fn:length(list) > 0 }">
								<table style="width: 100%; font-size: smaller;">
									<tr>
										<th>글</th>
										<th>장르</th>
										<th>제목</th>
										<th>내용</th>
										<th>id</th>
										<th>이름</th>
										<th>파일이름</th>
										<th>날짜</th>
										<th>조회수</th>
										<th>좋아요</th>
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
												
														<a href="./${l.table }BoardDetail?${l.viewName }no=${l.no }">${string2 }.....</a>
														
													</c:when>
													<c:otherwise>
														<a href="./${l.table }BoardDetail?${l.viewName }no=${l.no }">${string2 }</a>
													</c:otherwise>
												</c:choose>
											</td>

											<td id="content">
												<!-- content 내용 줄여주는 것 --> <c:set var="string1"
													value="${l.content }" /> <c:set var="string2"
													value="${fn:substring(string1, 0, 7)}" /> ${string2 }
											</td>

											<td>${l.id }</td>
											<td>${l.name }</td>
											<td id="content">
												<!-- thumbnail 이름 내용 줄여주는 것 --> <c:set var="string1"
													value="${l.fileName}" /> <c:set var="string2"
													value="${fn:substring(string1, 0, 20)}" /> <c:if
													test="${string1 ne null }">
										${string2 }
    					 	</c:if>

											</td>
											<td>${l.joinDate }</td>
											<td>${l.count }</td>
											<td>${l.like}</td>


											<td id=deleteButton><input type="hidden" id="user"
												name="user1" value="2" />
												<form action="${l.table}BoardDelete?${l.viewName }no=${l.no}" method="post">
													<input type="hidden" id="user" name="lowertable"
														value="${l.table}" /> <input type="hidden" id="user"
														name="lowerTC" value="${l.viewName }" /> <input
														type="hidden" id="user" name="number" value="${l.no}" />

													<button onclick=>delete</button>
												</form></td>
										</tr>
									</c:forEach>
								</table>
			<div id="paging">
		<c:set var="pageName" value="adminPost" scope="request" />
		<c:set var="PAGENUMBER" value="20" scope="request" />
		<c:import url="adminPostPaging.jsp" />
	</div>

							</c:when>
							<c:otherwise>
								<%-- listing out from the searchlist --%>
								<table style="width: 100%; font-size: smaller;">
									<tr>
										<th>글</th>
										<th>장르</th>
										<th>제목</th>
										<th>내용</th>
										<th>id</th>
										<th>이름</th>
										<th>파일이름</th>
										<th>날짜</th>
										<th>조회수</th>
										<th>좋아요</th>
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
											<td>${l.name }</td>
											<td id="content">
												<!-- thumbnail 이름 내용 줄여주는 것 --> <c:set var="string1"
													value="${l.fileName}" /> <c:set var="string2"
													value="${fn:substring(string1, 0, 7)}" /> <c:if
													test="${string1 ne null }">
										${string2 }
    					 	</c:if>

											</td>
											<td>${l.joinDate }</td>
											<td>${l.count }</td>
											<td>${l.like}</td>
							<td id=deleteButton><input type="hidden" id="user"
												name="user1" value="2" />
												<form action="${l.table}BoardDelete?${l.viewName }no=${l.no}" method="post">
													<input type="hidden" id="user" name="lowertable"
														value="${l.table}" /> <input type="hidden" id="user"
														name="lowerTC" value="${l.viewName }" /> <input
														type="hidden" id="user" name="number" value="${l.no}" />

													<button class="delete" onclick=>delete</button>
												</form></td>
									</c:forEach>
								</table>
									<div id="paging">
		<c:set var="pageName" value="adminPost" scope="request" />
		<c:set var="PAGENUMBER" value="20" scope="request" />
		<c:import url="adminPostPaging.jsp" />
	</div>
							</c:otherwise>
						</c:choose>

					</div>
				</div>
			</div>
</body>
</html>