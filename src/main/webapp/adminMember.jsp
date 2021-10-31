<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>	
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WebtoonReview - 관리자 페이지</title>
 <c:if test="{${sessionScope.grade eq null }">
<c:redirect url="./index" />
</c:if>
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
<script type="text/javascript">
function changePW(no,pw){
	//alert(no + "암호 번경을 눌렀습니다.");
	var pw = prompt("변경할 암호를 입력하세요.",pw);
	//
	if(confirm("정말 수정하시겠습니까?")){
		//alert("입력한 암호는 : " + pw);
		location.href="adminChangePW?no="+no+"&pw="+pw;
		//나중에는 post전송으로도 보내보세요.
	}
}

function changeEmail(no,email){
	//alert(no + "암호 번경을 눌렀습니다.");
	var email = prompt("변경할 이메일을 입력하세요.",email);
	//
	if(confirm("정말 수정하시겠습니까?")){
		//alert("입력한 암호는 : " + pw);
		location.href="adminChangeEmail?no="+no+"&email="+email;
		//나중에는 post전송으로도 보내보세요.
	}
}

function changeGrade(no){
	var grade = document.getElementById("grade").value;
	//alert(no + "등급 변경을 눌렀습니다.");
	//alert(grade + "현재 회원님의 등급입니다.");
	location.href="changeGrade?no="+no+"&grade="+grade;
}

function member(no, code){
	if (code == 'm') {
		if (confirm("해당 계정을 수정하시겠습니까?")) {
			location.href="adminModify="+no;
		}
	} else if (code == 'd') {
		if (confirm("해당 계정을 삭제하시겠습니까?")) {
			location.href="adminDelete?no="+no;
		}
	}
}
	location.href="deleteUser="+no;
</script>
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
                  <li class="active"><a href="./adminMember"><img src="./img/memberControl.png">회원 관리</a></li>
                  <li><a href="./adminPost"><img src="./img/articleControl.png">게시글 관리</a></li>
                  <li><a href="./admin"><img src="./img/log.png">로그 관리</a></li>
                  <li><a href="./adminPick"><img src="./img/article2.png">Editor's Pick</a></li>
                  <li><a href="./index"><img src="./img/exit.png">나가기</a></li>
              </ul>
          </div>
      </div>
</nav>
<div id="adminMain">
    <div id="adminMainContainer">
        <div id="adminMainContent_title">
            <h1>멤버 관리</h1>
            <hr class="styleHR5">
        </div>
        <div id="adminMainContent">
						<p class="dataSum">전체 글 수 : ${totalCount } 개 / 페이지 : ${page }</p>
						<c:choose>
							<c:when test="${fn:length(list) > 0 }">
								<table style="width: 100%; font-size: smaller;">
									<tr>
										<th>no</th>
										<th>이름</th>
										<th>아이디</th>
										<th>비밀번호</th>
										<th>email</th>
										<th>join date</th>
										<th>grade</th>
										<th>삭제</th>
									</tr>
									<c:forEach items="${list }" var="l">
										<tr>
											<td>${l.no }</td>
											<td>${l.name }</td>
											<td>${l.id }</td>
											<td><span onclick="changePW(${l.no},'${l.pw }')">${l.pw }</span></td>
											<td><span onclick="changeEmail(${l.no},'${l.email }')">${l.email }</span></td>
											<td>${l.joindate }</td>
											<td><select onchange="changeGrade(${l.no })" id="grade" style="width: 50px;">
													<c:forEach begin="1" end="2" var="g">
														<c:if test="${g eq l.grade }">
															<option value="${g }" selected="selected">${g }</option>
														</c:if>
														<c:if test="${g ne l.grade }">
															<option value="${g }">${g }</option>
														</c:if>
													</c:forEach>
											</select></td>
											<td>
												<%-- <button onclick="member(${l.no}, 'm');">
										<img alt="modify" src="./img/update2.png" height="20px">
									</button> --%>
												<button onclick="member(${l.no}, 'd');" class="delete" >
												delete
												</button>
											</td>
										</tr>
									</c:forEach>

								</table>
								<div id="paging" class="fixed">
									<!-- 이동할 페이지명을 변수처리 -->
									<c:set var="pageName" value="adminMember" scope="request" />
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