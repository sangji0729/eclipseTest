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
                  <li><a href="./adminPost"><img src="./img/articleControl.png">게시글 관리</a></li>
                  <li class="active"><a href="./admin"><img src="./img/log.png">로그 관리</a></li>
                  <li><a href="./adminPick"><img src="./img/article2.png">Editor's Pick</a></li>
                  <li><a href="./index"><img src="./img/exit.png">나가기</a></li>
              </ul>
          </div>
      </div>
</nav>
<div id="adminMain">
    <div id="adminMainContainer">
        <div id="adminMainContent">
			 <p class="dataSum">전체 글 수 : ${totalCount } 개 / 페이지 : ${page }</p>
			 <div class="adminSearchBox">
				<form action="adminLogSearch" method="post">
                <select name="searchname" class="searchColumn">
                    <option value="id">id</option>
                    <option value="ip">ip</option>
                    <option value="target">target</option>
                </select> <input type="text" id="searchWord" name="search">
                <button class="search" type="submit">검색</button>
            </form>
            </div>
						<c:choose>
							<c:when test="${fn:length(list) > 0 }">
								<table style="width: 100%">
									<tr>
										<th>no</th>
										<th><select onchange="select()" id="ida">
												<option value="">id</option>
												<c:forEach items="${idList }" var="i">
													<c:if test="${ida eq i }">
														<option value="${i }" selected="selected">${i }</option>
													</c:if>
													<c:if test="${id ne i }">
														<option value="${i }">${i }</option>
													</c:if>
												</c:forEach>
										</select></th>
										<th><select onchange="select()" id="ip">
												<option value="">ip</option>
												<c:forEach items="${ipList }" var="i">
													<c:if test="${ip eq i }">
														<option value="${i }" selected="selected">${i }</option>
													</c:if>
													<c:if test="${ip ne i }">
														<option value="${i }">${i }</option>
													</c:if>
												</c:forEach>
										</select></th>
										<th>date</th>
										<th><select onchange="select()" id="target">
												<option value="">target</option>
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
											<td id="no">${l.log_no }</td>
											<td id="id">${l.log_id }</td>
											<td id="ip">${l.log_ip }</td>
											<td id="date">${l.log_date }</td>
											<td id="target">${l.log_target }</td>
											<td id="etc"><c:choose>
													<c:when test="${fn:length(l.log_etc) >9 }">
														<c:out value="${fn:substring(l.log_etc, 0, 10) }..." />
													</c:when>
													<c:otherwise>
														<c:out value="${fn:substring(l.log_etc, 0, 10) }" />
													</c:otherwise>
												</c:choose>
										</tr>
									</c:forEach>
								</table>
								<div id="paging" class="fixed">
									<!-- 이동할 페이지명을 변수처리 -->
									<c:set var="pageName" value="adminLog" scope="request" />
									<c:set var="PAGENUMBER" value="10" scope="request" />
									<c:import url="paging.jsp" />
								</div>
							</c:when>
							<c:otherwise>
								<h2>출력할 글이 없습니다.</h2>
								<a href="./adminLog"> 돌아가기 </a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>

	<script type="text/javascript">
		function select() {
			//value 값을 가져오고 싶다면?
			//alert("!");
			var ida = document.getElementById("ida").value;
			var ip = document.getElementById("ip").value;
			var target = document.getElementById("target").value;
			//값 오는 것이 확인된다면 서블릿을 보내서 해당 ip것만 받도록 합니다.
			//location.href='admin?ip='+ip + '&page=' + ${page};
			location.href = 'adminLog?id=' + ida + '&ip=' + ip + '&target='
					+ target;
		}
	</script>
</body>
</html>