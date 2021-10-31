<%@page import="login.Login"%>
<%@page import="java.util.HashMap"%>
<%@page import="login.LoginDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"
	type="text/javascript"></script>

<html>
<head>
<meta charset="UTF-8">
<title>detail</title>
<style type="text/css">
#detailBox {
	height: auto;
	width: 500px;
	border: 1px black solid;
}

#backButton {
	float: right;
}

#commentSection {
	height: auto;
	width: 500px;
	border: 2px black solid;
}

#commentBox {
	height: auto 50px;
	width: 500px;
	margin: 10px auto;
}

#individualComment {
	border-bottom: 1px black solid;
}

a {
	float: right;
}
</style>
<script type="text/javascript">
	function detail(fno, code){
	if(code == 'a'){
		alert("종아요를 눌렀습니다.");
		location.href="./fantasyLike?fno="+fno;
		
	}else if(code == 'm'){
		if(confirm("수정하시겠습니까?")){
			location.href="./fantasyModify?fno="+fno;			
		}else{
			alert("수정 취소");
		}
	}else if(code =='d'){
		if (confirm("삭제하시겠습니까?")) {
			alert("삭제되었습니다");
			location.href="./fantasyDelete?fno="+fno;			
		}else{
			alert("삭제 취소");
			}
	}
}	
	$(document).ready(function(){
		//수정하기 기능
		$(".modifyInput").click(function(){
			//var fcontent = $(this).children().first().text();
			//var fcno = $(this).children().last().text();
			//변경
			var fccontent = $(this).children(".fcontent").text();
			var fno = $(this).children(".fno").text();
			var fcno = $(this).children(".fcno").text();
			$(this).parent().html(
					"<form action='./fantasyModifyComment' method='post'>"
					+"<textarea name='fccontent'>"+fccontent+"</textarea>"
					+"<input type='hidden' name='fcno' value='"+fcno+"'>"
					+"<input type='hidden' name='fno' value='"+fno+"'>"
					+"<button>수정하기</button>"
					+"</form>"
					+"<div class='clear1'>수정취소</div>");
					//content변경 + 댓글번호
		$(".clear1").click(function(){
			//alert(htmlB);
			$(this).parent().html(
					'<div class="modifyInput">수정하기'
			 		+'<div class="fccontent">'+fccontent+'</div>'
			 		+'<div class="fno" style="display: none;">'+fno+'</div>'
			 		+'<div class="fcno" style="display: none;">'+fcno+'</div>'
				 	+'</div>');
		});
		});
});
</script>
</head>
<body>
	<h1>detail</h1>
	<div id="main">
		<div id="detailBox">
			<c:set value="1" var="adminGrade" />
			<table>
				<tr>
					<th>제목:</th>
					<c:if test="${admin.id eq sessionScope.id }">
					11111111111111
						<button onclick="return detail(${d.fno}, 'd');">삭제하기</button>
					</c:if>
					<td>${d.ftitle }<c:if test="${sessionScope.id eq d.id }">
							<button onclick="detail(${d.fno}, 'a')">좋아요 : ${d.flike }</button>
							<c:choose>
								<c:when test="${sessionScope.id eq d.id }">
									<button onclick="return detail(${d.fno}, 'm');">수정하기</button>
									<button onclick="return detail(${d.fno}, 'd');">삭제하기</button>
									<c:if test="${sessionScope.id eq adminGrade}">
										<button onclick="return detail(${d.fno}, 'd');">삭제하기</button>
									</c:if>
								</c:when>
								<c:otherwise>
									<small>(본인 id로 수정 및 삭제할 수 있습니다.)</small>
								</c:otherwise>
							</c:choose>
						</c:if>
					</td>
				</tr>
				<tr>
					<th>글쓴이:</th>
					<td>${d.no}</td>
				</tr>
				<tr>
					<th>조회수:</th>
					<td>${d.fcount}</td>
				</tr>
				<tr>
					<th>날짜:</th>
					<td>${d.fdate }</td>
				</tr>
				<tr>
					<th>내용:</th>
					<td>${d.fcontent}</td>
				</tr>
				<tr>
					<th>uploaded file: <c:if test="${d.fthumbnail ne null }">
							<img alt="pic" src="./upload/${d.fthumbnail}" width="100%">
						</c:if>
					</th>
				</tr>
			</table>
		</div>
		<button id="backButton" onclick="location.href='fantasy'">back</button>
		<br>
	</div>
	<div id="commentSection">
		comment section: ${d.commentcount }개의 댓글이 있습니다.
		<div id="commentBox">
			<c:if test="${d.commentcount > 0 }">
				<c:forEach items="${commentList }" var="c">
					<div id=individualComment>
						${c.id} : ${c.fccontent }
						<c:if test="${sessionScope.id eq c.id }">
							<form action="./fantasyCommentDelete" method="post">
								<button>delete</button>
								<input type="hidden" name="fcno" value="${c.fcno }"> <input
									type="hidden" name="fno" value="${c.fno }"> <input
									type="hidden" name="fccontent" value="${c.fccontent }">
							</form>
						</c:if>
						<div class="modifyInput">
							<c:if test="${sessionScope.id eq c.id }">
								<button type="submit">modify</button>
							</c:if>
							<div class="fccontent"></div>
							<div class="fno" style="display: none;">${c.fno }</div>
							<div class="fcno" style="display: none;">${c.fcno }</div>
							<div class="id" style="display: none;">${c.id }</div>
						</div>
					</div>
				</c:forEach>
			</c:if>
		</div>
		<div id=InsertComment>
			<form action="./comment" method="post">
				<textarea name="comment" id=commentContent>
	</textarea>
				<input type="hidden" name="fno" value="${d.fno }">
				<button type="submit">댓글 쓰기</button>
			</form>
		</div>
	</div>
</body>
</html>