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
							<option>category</option>
							<option disabled="disabled">---</option>
							<option value="action" onclick="location.href='adminActionPost'"
								id="Action">Action</option>
							<option value="daily" onclick="location.href='adminDailyPost'"
								id="Daily">Daily</option>
							<option value="drama" onclick="location.href='adminDramaPost'"
								id="Drama">Drama</option>
							<option value="fantasy" onclick="location.href='adminPost'"
								id="Fantasy">Fantasy</option>
							<option value="romance"
								onclick="location.href='adminRomancePost'" id="Romance">Romance</option>
							<option value="thriller"
								onclick="location.href='adminThrillerPost'" id="Thriller">Thriller</option>
						</select> <input type="text" id="search" name="search">
						<button type="submit" class="search">search</button>


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
									</tr>
									<c:forEach items="${list }" var="l">
										<tr>
											<td>${l.ano }</td>
											<td>${l.atable }</td>
											<td id="title">
												<!-- content 내용 줄여주는 것 --> <c:set var="string1"
													value="${l.atitle}" /> <c:set var="string2"
													value="${fn:substring(string1, 0, 9)}" /> ${string2 }
											<td id="content">
												<!-- content 내용 줄여주는 것 --> <c:set var="string1"
													value="${l.acontent }" /> <c:set var="string2"
													value="${fn:substring(string1, 0, 7)}" /> ${string2 }
											<td>${l.id }</td>
											<td>${l.afilename }</td>
											<td>${l.joindate }</td>
											<td>${l.acount }</td>
											<td></td>
										</tr>
									</c:forEach>
								</table>
								<div id="paging">
									<!-- 이동할 페이지명을 변수처리 -->
									<c:set var="pageName" value="adminActionPost" scope="request" />
									<c:set var="PAGENUMBER" value="40" scope="request" />
									<c:import url="paging.jsp" />
								</div>
							</c:when>
							<c:otherwise>
								<h2>출력할 데이터가 없습니다</h2>
							</c:otherwise>
						</c:choose>

					</div>
				</div>
			</div>
		</div>
	<script type="text/javascript">
		function select() {
		}
	</script>
</body>
</html>