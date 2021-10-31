<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>${dto.dtitle} - WebtoonReview</title>
<script
src="https://code.jquery.com/jquery-3.6.0.min.js"
integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
crossorigin="anonymous"></script>
<script src='./js/boardDetail.js'></script>
<script>
	function deleteConfirm(dno, pageNo){
		if(confirm("해당 글을 삭제하겠습니까?")){
			location.href="./dramaBoardDelete?dno=" + dno + "&pageNo=" + pageNo;
		}
	}
	
	function deleteComment(dcno, dno, pageNo){
		if(confirm("해당 댓글을 삭제하겠습니까?")){
			location.href="./commentDelete?dno=" + dno + "&dcno=" + dcno + "&pageNo=" + pageNo;
		}
	}

	function checkCommentWrite() {
	var dccontent = document.getElementById("commentWriteForm");
	if (dccontent.value == "" || dccontent.value.length < 10) {
		alert("내용을 10글자 이상 적어주세요.");
		dccontent.focus();
		return false;
		}	
	}
	
	function checkCommentModify() {
		var dccontent = document.getElementById("dccontent");
		if (dccontent.value == "" || dccontent.value.length < 10) {
			alert("내용을 10글자 이상 적어주세요.");
			dccontent.focus();
			return false;
			}	
		}
	
	function modifyBox(dcno) {
		var boxOpened = document.getElementById("modifybox"+dcno+"_Opened");
		if(boxOpened.style.display == 'contents'){
			boxOpened.style.display = 'none';
		} else{
			boxOpened.style.display = 'contents';
		}
	}
	
	function cantLike() {
		alert("추천 기능은 로그인 후 이용 가능합니다.");
	}
	
	function cantLikeError() {
		alert("추천 정보를 불러오지 못했습니다.")
	}
	
	function toLikeUp(dno) {
	if(confirm("추천하시겠습니까?")){
	$.ajax({
		type: 'GET',
		dataType:'TEXT',
		data:'dno='+dno,
		url: './dramaLike',
		async : false,
		success: function(rData, textStatus, xhr){
			var checkResult = rData;
			if(checkResult == 1) {
				
				alert('추천하였습니다.');
				var dlike = $('#likeValue').children('.valueText').text();
				dlike = parseInt(dlike) + 1;
				$('#likeValue').children('.valueText').text(dlike);
				$("#likeLogo").html('<img src="./img/like.png" height="35px" alt="comment" onclick="toDeleteLike(${dto.dno})">');
			} else if (checkResult <= 0){
				alert('추천에 실패하였습니다.');
			}
		},
		error:function(xhr, status, e){
			alert("문제 발생 : " + e)
		}
	 })
	}
}
	
	function toDeleteLike(dno) {
		if(confirm("추천을 취소하시겠습니까?")){
			$.ajax({
				type: 'GET',
				dataType:'TEXT',
				data:'dno='+dno,
				url: './dramaLikeDelete',
				async : false,
				success: function(rData, textStatus, xhr){
					var checkResult = rData;
					if(checkResult == 1) {
						alert('추천을 취소하였습니다.');
						var dlike = $('#likeValue').children('.valueText').text();
						dlike = parseInt(dlike) - 1;
						$('#likeValue').children('.valueText').text(dlike);
						//$("#likeLogo").children('img').attr("src", "./img/unlike.png");
						$("#likeLogo").html('<img src="./img/unlike.png" height="35px" alt="comment" onclick="toLikeUp(${dto.dno})">');
					} else if (checkResult <= 0){
						alert('추천 취소에 실패하였습니다.');
					}
				},
				error:function(xhr, status, e){
					alert("문제 발생 : " + e)
				}
			 })
			}
	}
	
</script>
<style>
  @import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Limelight&family=Londrina+Solid&display=swap');
</style>
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/boardDetail.css">
</head>
<body>
<c:set var="pageNo" value="${empty param.pageNo? '1':param.pageNo }" />
    <div id="headerSpace"></div>
    <div id="detailMain">
    <div id="backgroundThumbnail" 
    <c:if test="${dto.dthumbnail ne null}">
    style="background-image: url('./thumbnail/${dto.dthumbnail}');"
    </c:if>
    >
    </div>
        <div id="articleContainer"> <!--그라디언트 적용하기-->
                <div id="articleInner1">
                <div id="articleGenre">
                    <span>드라마</span>
                </div>
                <div id="articleTitle">
                    <span>${dto.dtitle}</span>
                </div>
                <div id="articleInfo">
                    <div id="articleAuthor"><span>${dto.name}(${dto.id})</span></div>
                    <div id="articleDate"><span>${dto.ddate}</span></div>
                </div>
                </div>
                <div id="articleInner2">
                <div id="articleContent">
                    ${dto.dcontent}
                </div>
                <div id="articlePopularity">
                    <div id="commentValueBox">
                        <div id="commentLogo">
                            <img src="./img/comment.png" height="35px" alt="comment">
                        </div>
                        <div id="commentValue">
                            <span class="articleCommentText"> 댓글 </span>&nbsp;<span class="valueText">${dto.commentcount}</span>
                        </div>
                    </div>
                    <div id="likeValueBox">
                        <div id="likeLogo">
                        <c:choose>
                        <c:when test="${sessionScope.id eq null}">
                        	<img src="./img/unlike.png" height="35px" alt="comment" onclick="cantLike()">
                        </c:when>
                        <c:when test="${canLike == 0}">
                            <img src="./img/unlike.png" height="35px" alt="comment" onclick="toLikeUp(${dto.dno})">
                        </c:when>
                        <c:when test="${canLike == 1}">
                        	<img src="./img/like.png" height="35px" alt="comment" onclick="toDeleteLike(${dto.dno})">
                        </c:when>
                        <c:otherwise>
                        	 <img src="./img/unlike.png" height="35px" alt="comment" onclick="cantLikeError()">
                        </c:otherwise>   
                        </c:choose>
                        </div>
                        <div id="likeValue">
                            <span class="articleLikeText"> 추천 </span>&nbsp;<span class="valueText">${dto.dlike}</span>
                        </div>
                    </div>
                    <div id="articleModify">
                    	<c:if test="${sessionScope.id eq dto.id}">
                        <span class="modifyText">
                        <a href='./dramaModify?dno=${dto.dno}&pageNo=${pageNo}'>수정</a>
                        &nbsp;&nbsp;&nbsp;
                        <a onclick="deleteConfirm(${dto.dno},${pageNo});">삭제</a></span>
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
                            <td class="commentAuthorAndDate">
                                <span class="commentAuthorText">${i.name }(${i.id})</span>
                                &nbsp;&nbsp;&nbsp;
                                <span class="dateText">${i.dcdate}</span>
                            </td>
                            <td class="commentModify">
                            <c:if test="${i.id eq sessionScope.id}">
                                <span class="modifyText">
                                <a onclick="modifyBox(${i.dcno})">수정</a>
                                &nbsp;&nbsp;
                                <a onclick="deleteComment(${i.dcno}, ${i.dno}, ${pageNo})">삭제</a>
                                </span>
                            </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="commentContent" colspan="4" rowspan="1">
                                ${i.dccontent}
                            </td>
                        </tr>
                            <tr id="modifybox${i.dcno}_Opened" class="modifybox" style="display: none;">
                            <td colspan="2" rowspan="1">
                            <form action='./commentModify' method='post' onsubmit='return checkCommentModify()'>
                            <textarea name='dccontent' id='dccontent'>${i.dccontent}</textarea>
                            <input type="hidden" name="dcno" value="${i.dcno}">
                            <input type="hidden" name="dno" value="${i.dno}">
                            <input type="hidden" name="pageNo" value="${pageNo}">
							<div class="commentModifyButtonBox">
							<button id="commentModifySubmit" >수정하기</button>
							</form>
							</div>
							</td>
                        </tr>
					</c:forEach>
                        </tbody>
                    </table>
                </div>
                <div id="commentWriteInner">
                <c:if test="${sessionScope.id ne null}">
                <form action="./dramaCommentInput" method="post" onsubmit='return checkCommentWrite()'>
                    <textarea id="commentWriteForm" name="dccontent"></textarea>
                    <input type="hidden" name="dno" value="${dto.dno}">
                    <input type="hidden" name="pageNo" value="${pageNo}">
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