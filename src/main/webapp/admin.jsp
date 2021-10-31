<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WebtoonReview - 관리자 페이지</title>
  <!-- 관리자 아니면 튕겨내기 -->
</head>
<style>
    @import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
</style>
<style>
.searchColumn {
    width: 100px;
}

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
        admin 님
    </div>
    <hr class="styleHR4">
  </div>
      <div id="adminSideMenuContainer">
          <div id="adminSideMenu">
              <ul class="item_AdminSideMenu">
                  <li><a href="#"><img src="./img/memberControl.png">회원 관리</a></li>
                  <li><a href="#"><img src="./img/articleControl.png">게시글 관리</a></li>
                  <li class="active"><img src="./img/log.png">로그 관리</li>
                  <li><a href="#"><img src="./img/article2.png">Editor's Pick</a></li>
                  <li><a href="#"><img src="./img/exit.png">나가기</a></li>
              </ul>
          </div>
      </div>
</nav>
<div id="adminMain">
    <div id="adminMainContainer">
        <div id="adminMainContent_title">
            <h1>로그 관리</h1>
            <hr class="styleHR5">
        </div>
        <div id="adminMainContent">
						<p class="dataSum">전체 글 수 : ${totalCount } 개 / 페이지 : ${page }</p>
						<div class="adminSearchBox">
						<form action="admin" method="post">
							<select name="searchname">
								<option value="ip">ip</option>
								<option value="target">target</option>
								<option value="etc">etc</option>
							</select> <input type="text" id="search" name="search">
							<button class="search" type="submit">search</button>
						</form>
						</div>
						<c:choose>
							<c:when test="${fn:length(list) > 0 }">
								<table style="width: 100%; font-size: smaller;">
									<tr>
										<th>no</th>
										<th><select onchange="select()" id="ip">
												<option value="">ip를 선택</option>
												<c:forEach items="${ipList }" var="i">
													<c:if test="${i eq ip }">
														<option value="${i }" selected="selected">${i }</option>
													</c:if>
													<c:if test="${i ne ip }">
														<option value="${i }">${i }</option>
													</c:if>
												</c:forEach>
										</select></th>
										<th>date</th>
										<th><select onchange="select()" id="target">
												<option value="">target을 선택</option>
												<c:forEach items="${targetList }" var="t">
													<c:if test="${target eq t }">
														<option value="${t }" selected="selected">${t }</option>
													</c:if>
													<c:if test="${target ne t }">
														<option value="${t }">${t }</option>
													</c:if>
												</c:forEach>
										</select></th>
										<th>etc</th>
									</tr>
									<c:forEach items="${list }" var="l">
										<tr>
											<td>${l.log_no }</td>
											<td>${l.log_ip }</td>
											<td>${l.log_date }</td>
											<td>${l.log_target }</td>
											<td><c:out value="${fn:substring(l.log_etc, 0, 40) }" />
											</td>
										</tr>
									</c:forEach>
								</table>
								<div id="paging">
									<!-- 이동할 페이지명을 변수처리 -->
									<c:set var="pageName" value="admin" scope="request" />
									<c:set var="PAGENUMBER" value="20" scope="request" />
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
			//value값을 가져오고 싶다면 ?
			//alert("!");
			var ip = document.getElementById("ip").value;
			var target = document.getElementById("target").value;
			//값 오는 것이 확인된다면 서블릿을 보내서 해당 ip것만 받도록 합니다.
			//location.href='admin?ip=' + ip + '&page=' + ${page };
			location.href = '"admin?ip=' + ip + '&target=' + target;
		}
	</script>
</body>
</html>