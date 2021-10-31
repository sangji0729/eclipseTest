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
    <title>WebtoonReview - ������ ������</title>
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
        <h3>[ ������ ��� ]</h3>
    </div>
    <div id="adminIdPrint">
        ${sessionScope.id} ��
    </div>
    <hr class="styleHR4">
  </div>
      <div id="adminSideMenuContainer">
          <div id="adminSideMenu">
              <ul class="item_AdminSideMenu">
                  <li><a href="./adminMember"><img src="./img/memberControl.png">ȸ�� ����</a></li>
                  <li><a href="./adminPost"><img src="./img/articleControl.png">�Խñ� ����</a></li>
                  <li class="active"><a href="./admin"><img src="./img/log.png">�α� ����</a></li>
                  <li><a href="./adminPick"><img src="./img/article2.png">Editor's Pick</a></li>
                  <li><a href="./index"><img src="./img/exit.png">������</a></li>
              </ul>
          </div>
      </div>
</nav>
<div id="adminMain">
    <div id="adminMainContainer">
        <div id="adminMainContent">
			 <p class="dataSum">��ü �� �� : ${totalCount } �� / ������ : ${page }</p>
			 <div class="adminSearchBox">
				<form action="adminLogSearch" method="post">
                <select name="searchname" class="searchColumn">
                    <option value="id">id</option>
                    <option value="ip">ip</option>
                    <option value="target">target</option>
                </select> <input type="text" id="searchWord" name="search">
                <button class="search" type="submit">�˻�</button>
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
									<!-- �̵��� ���������� ����ó�� -->
									<c:set var="pageName" value="adminLog" scope="request" />
									<c:set var="PAGENUMBER" value="10" scope="request" />
									<c:import url="paging.jsp" />
								</div>
							</c:when>
							<c:otherwise>
								<h2>����� ���� �����ϴ�.</h2>
								<a href="./adminLog"> ���ư��� </a>
							</c:otherwise>
						</c:choose>
					</div>
				</div>
			</div>
		</div>

	<script type="text/javascript">
		function select() {
			//value ���� �������� �ʹٸ�?
			//alert("!");
			var ida = document.getElementById("ida").value;
			var ip = document.getElementById("ip").value;
			var target = document.getElementById("target").value;
			//�� ���� ���� Ȯ�εȴٸ� ������ ������ �ش� ip�͸� �޵��� �մϴ�.
			//location.href='admin?ip='+ip + '&page=' + ${page};
			location.href = 'adminLog?id=' + ida + '&ip=' + ip + '&target='
					+ target;
		}
	</script>
</body>
</html>