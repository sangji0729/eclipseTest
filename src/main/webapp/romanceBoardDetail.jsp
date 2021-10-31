<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>${list.rtitle }-WebtoonReview</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<script src='./js/boardDetail.js'></script>
<style>
@import
	url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Limelight&family=Londrina+Solid&display=swap')
	;
</style>
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/boardDetail.css">
<script type="text/javascript">
	function deleteConfirm(rno) {
		if (confirm("해당 글을 삭제하겠습니까?")) {
			location.href = "./romanceBoardDelete?rno=" + rno;
		}
	}

	function deleteComment(rcno, rno) {
		if (confirm("해당 댓글을 삭제하겠습니까?")) {
			location.href = "./romanceCommentDelete?rcno=" + rcno + "&rno="
					+ rno;
		}
	}

	function checkCommentWrite() {
		var rccontent = document.getElementById("commentWriteForm");
		if (rccontent.value == "" || rccontent.value.length < 10) {
			alert("내용을 10글자 이상 적어주세요.");
			rccontent.focus();
			return false;
		}
	}

	function checkCommentModify() {
		var rccontent = document.getElementById("rccontent");
		if (rccontent.value == "" || rccontent.value.length < 10) {
			alert("내용을 10글자 이상 적어주세요.");
			rccontent.focus();
			return false;
		}
	}

	function modifyBox(rcno) {
		var boxOpened = document.getElementById("modifybox" + rcno + "_Opened");

		if (boxOpened.style.display == 'contents') {
			boxOpened.style.display = 'none';

		} else {
			boxOpened.style.display = 'contents';
		}
	}

	function cantLike() {
		alert("추천 기능은 로그인 후 이용 가능합니다.");
	}

	function cantLikeError() {
		alert("추천 정보를 불러오지 못했습니다.")
	}

	function toLikeUp(rno) {
		if (confirm("추천하시겠습니까?")) {
			$
					.ajax({
						type : 'GET',
						dataType : 'TEXT',
						data : 'rno=' + rno,
						url : './romanceBoardLike',
						async : false,
						success : function(rData, textStatus, xhr) {
							var checkResult = rData;

							if (checkResult == 1) {
								alert('추천하였습니다.');
								var rlike = $('#likeValue').children(
										'.valueText').text();
								rlike = parseInt(rlike) + 1;
								$('#likeValue').children('.valueText').text(
										rlike);
								$("#likeLogo")
										.html(
												'<img src="./img/like.png" height="35px" alt="comment" onclick="toDeleteLike(${list.rno})">');

							} else if (checkResult <= 0) {
								alert('추천에 실패하였습니다.');
							}
						},

						error : function(xhr, status, e) {
							alert("문제 발생 : " + e)
						}
					})
		}
	}

	function toDeleteLike(rno) {
		if (confirm("추천을 취소하시겠습니까?")) {
			$
					.ajax({
						type : 'GET',
						dataType : 'TEXT',
						data : 'rno=' + rno,
						url : './romanceLikeDelete',
						async : false,
						success : function(rData, textStatus, xhr) {
							var checkResult = rData;

							if (checkResult == 1) {
								alert('추천을 취소하였습니다.');
								var rlike = $('#likeValue').children(
										'.valueText').text();
								rlike = parseInt(rlike) - 1;
								$('#likeValue').children('.valueText').text(
										rlike);
								$("#likeLogo")
										.html(
												'<img src="./img/unlike.png" height="35px" alt="comment" onclick="toLikeUp(${list.rno})">');
							} else if (checkResult <= 0) {
								alert('추천 취소에 실패하였습니다.');
							}
						},

						error : function(xhr, status, e) {
							alert("문제 발생 : " + e)
						}
					})
		}
	}
</script>
</head>
<body>
	<c:set value="${list }" var="l" />
	<div id="headerSpace"></div>
	<div id="detailMain">
		<div id="backgroundThumbnail"></div>
		<div id="articleContainer">
			<div id="articleInner1">
				<div id="articleGenre">
					<span>로맨스</span>
				</div>
				<div id="articleTitle">
					<span>${l.rtitle }</span>
				</div>
				<div id="articleInfo">
					<div id="articleAuthor">
						<span>${l.name }</span>
					</div>
					<div id="articleDate">
						<span>${l.rdate }</span>
					</div>
				</div>
			</div>
			<div id="articleInner2">
				<div id="articleContent">
					${l.rcontent } <br>
					<c:if test="${l.rfilename ne null }">
						<img alt="img" src="./upload/${l.rfilename }">
					</c:if>
				</div>
				<div id="articlePopularity">
					<div id="commentValueBox">
						<div id="commentLogo">
							<img src="./img/comment.png" height="35px" alt="comment">
						</div>
						<div id="commentValue">
							<span class="articleCommentText"> 댓글 </span>&nbsp;<span
								class="valueText">${l.commentcount }</span>
						</div>
					</div>
					<div id="likeValueBox">
						<div id="likeLogo">
							<c:choose>
								<c:when test="${sessionScope.id eq null}">
									<img src="./img/unlike.png" height="35px" alt="comment"
										onclick="cantLike()">
								</c:when>
								<c:when test="${canLike == 0}">
									<img src="./img/unlike.png" height="35px" alt="comment"
										onclick="toLikeUp(${list.rno})">
								</c:when>
								<c:when test="${canLike == 1}">
									<img src="./img/like.png" height="35px" alt="comment"
										onclick="toDeleteLike(${list.rno})">
								</c:when>
								<c:otherwise>
									<img src="./img/unlike.png" height="35px" alt="comment"
										onclick="cantLikeError()">
								</c:otherwise>
							</c:choose>
						</div>
						<div id="likeValue">
							<span class="articleLikeText"> 추천 </span>&nbsp;<span
								class="valueText">${l.rlike}</span>
						</div>
					</div>
					<div id="articleModify">
						<c:if test="${sessionScope.id eq l.id }">
							<span class="modifyText"> <a
								href="./romanceBoardModify?rno=${l.rno}">수정</a>
								&nbsp;&nbsp;&nbsp; <a onclick="deleteConfirm(${l.rno});">삭제</a>
							</span>
						</c:if>
						<c:if test="${sessionScope.grade eq 1 }">
							<button onclick="location.href='./adminPost'">글관리
								페이지로돌아가기</button>
							<form action="./romanceBoardDelete" method="get">
								<button type="submit">관리자 권한 삭제</button>
								<input type="hidden" name="rno" value="${l.rno }">
							</form>
						</c:if>
					</div>
				</div>
			</div>
			<div class="marginBar">
				<hr class="styleHR3">
			</div>
			<div id="commentContainer">
				<div id="commentListInner">
					<table id="commentList">
						<c:forEach items="${commentList}" var="i">
							<tbody>
								<tr>
									<td class="commentAuthorAndDate"><span
										class="commentAuthorText">${i.name }(${i.id})</span>
										&nbsp;&nbsp;&nbsp; <span class="dateText">${i.rcdate}</span></td>
									<td class="commentModify"><c:if
											test="${i.id eq sessionScope.id}">
											<span class="modifyText"> <a
												onclick="modifyBox(${i.rcno})">수정</a> &nbsp;&nbsp; <a
												onclick="deleteComment(${i.rcno }, ${i.rno })">삭제</a>
											</span>
										</c:if></td>
								</tr>
								<tr>
									<td class="commentContent" colspan="4" rowspan="1">
										${i.rccontent}</td>
								</tr>
								<tr id="modifybox${i.rcno}_Opened" class="modifybox"
									style="display: none;">
									<td colspan="2" rowspan="1">
										<form action='./romanceCommentModify' method='post'
											onsubmit='return checkCommentModify()'>
											<textarea name='rccontent' id='rccontent'>${i.rccontent}</textarea>
											<input type="hidden" name="rcno" value="${i.rcno}"> <input
												type="hidden" name="rno" value="${i.rno}"> <input
												type="hidden" name="page" value="${page}">
											<div class="commentModifyButtonBox">
												<button id="commentModifySubmit">수정하기</button>
											</div>
										</form>
									</td>
								</tr>
						</c:forEach>
						</tbody>
					</table>
				</div>
				<div id="commentWriteInner">
					<c:if test="${sessionScope.id ne null}">
						<form action="./romanceCommentWrite" method="post"
							onsubmit='return checkCommentWrite()'>
							<textarea id="commentWriteForm" name="rccontent"></textarea>
							<input type="hidden" name="rno" value="${list.rno}"> <input
								type="hidden" name="page" value="${page}">
							<button id="commentWriteSubmit">댓글 작성</button>
						</form>
					</c:if>
				</div>
			</div>
			<div class="marginSpace"></div>
			<div id="footerSpace"></div>
		</div>
	</div>
</body>
</html>