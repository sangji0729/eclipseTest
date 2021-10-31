<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>${dto.ftitle} - WebtoonReview</title>
<script
src="https://code.jquery.com/jquery-3.6.0.min.js"
integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
crossorigin="anonymous"></script>
<script src='./js/boardDetail.js'></script>
<script>
	function deleteConfirm(fno, pageNo){
		if(confirm("해당 글을 삭제하겠습니까?")){
			location.href="./fantasyBoardDelete?fno=" + fno + "&pageNo=" + pageNo;
		}
	}
	
	function deleteComment(fcno, fno, pageNo){
		if(confirm("해당 댓글을 삭제하겠습니까?")){
			location.href="./fantasyCommentDelete?fno=" + fno + "&fcno=" + fcno + "&pageNo=" + pageNo;
		}
	}
	function checkCommentWrite() {
		   var fccontent = document.getElementById("commentWriteForm");
		   if (fccontent.value == "" || fccontent.value.length < 10) {
		      alert("내용을 10글자 이상 적어주세요.");
		      fccontent.focus();
		      return false;
		      }   
		   }
	function checkCommentModify() {
	      var fccontent = document.getElementById("fccontent");
	      if (fccontent.value == "" || fccontent.value.length < 10) {
	         alert("내용을 10글자 이상 적어주세요.");
	         fccontent.focus();
	         return false;
	         }   
	      }


	
	function modifyBox(fcno) {
		var boxOpened = document.getElementById("modifybox"+fcno+"_Opened");
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
	
	function toLikeUp(fno) {
		if(confirm("추천하시겠습니까?")){
		$.ajax({
			type: 'GET',
			dataType:'TEXT',
			data:'fno='+fno,
			url: './fantasyLike',
			async : false,
			success: function(rData, textStatus, xhr){
				var checkResult = rData;
				if(checkResult == 1) {
					
					alert('추천하였습니다.');
					var flike = $('#likeValue').children('.valueText').text();
					flike = parseInt(flike) + 1;
					$('#likeValue').children('.valueText').text(flike);
					$("#likeLogo").html('<img src="./img/like.png" height="35px" alt="comment" onclick="toDeleteLike(${dto.fno})">');
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
	
	function toDeleteLike(fno) {
		if(confirm("추천을 취소하시겠습니까?")){
			$.ajax({
				type: 'GET',
				dataType:'TEXT',
				data:'fno='+fno,
				url: './fantasyLikeDelete',
				async : false,
				success: function(rData, textStatus, xhr){
					var checkResult = rData;
					if(checkResult == 1) {
						alert('추천을 취소하였습니다.');
						var flike = $('#likeValue').children('.valueText').text();
						flike = parseInt(flike) - 1;
						$('#likeValue').children('.valueText').text(flike);
						//$("#likeLogo").children('img').attr("src", "./img/unlike.png");
						$("#likeLogo").html('<img src="./img/unlike.png" height="35px" alt="comment" onclick="toLikeUp(${dto.fno})">');
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
    <c:if test="${dto.fthumbnail ne null}">
    style="background-image: url('./thumbnail/${dto.fthumbnail}');"
    </c:if>
    >
    </div>
        <div id="articleContainer"> <!--그라디언트 적용하기-->
                <div id="articleInner1">
                <div id="articleGenre">
                    <span>판타지</span>
                </div>
                <div id="articleTitle">
                    <span>${dto.ftitle}</span>
                </div>
                <div id="articleInfo">
                    <div id="articleAuthor"><span>${dto.name}(${dto.id})</span></div>
                    <div id="articleDate"><span>${dto.fdate}</span></div>
                </div>
                </div>
                <div id="articleInner2">
                <div id="articleContent">
                    ${dto.fcontent}
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
                            <img src="./img/unlike.png" height="35px" alt="comment" onclick="toLikeUp(${dto.fno})">
                        </c:when>
                        <c:when test="${canLike == 1}">
                        	<img src="./img/like.png" height="35px" alt="comment" onclick="toDeleteLike(${dto.fno})">
                        </c:when>
                        <c:otherwise>
                        	 <img src="./img/unlike.png" height="35px" alt="comment" onclick="cantLikeError()">
                        </c:otherwise>   
                        </c:choose>
                        </div>
                        <div id="likeValue">
                            <span class="articleLikeText"> 추천 </span>&nbsp;<span class="valueText">${dto.flike}</span>
                        </div>
                    </div>
                    <div id="articleModify">
                    	<c:if test="${sessionScope.id eq dto.id}">
                        <span class="modifyText">
                        <a href='./fantasyModify?fno=${dto.fno}&pageNo=${pageNo}'>수정</a>
                        &nbsp;&nbsp;&nbsp;
                        <a onclick="deleteConfirm(${dto.fno},${pageNo});">삭제</a></span>
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
                                <span class="dateText">${i.fcdate}</span>
                            </td>
                            <td class="commentModify">
                            <c:if test="${i.id eq sessionScope.id}">
                                <span class="modifyText">
                                <a onclick="modifyBox(${i.fcno})">수정</a>
                                &nbsp;&nbsp;
                                <a onclick="deleteComment(${i.fcno}, ${i.fno}, ${pageNo})">삭제</a>
                                </span>
                            </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="commentContent" colspan="4" rowspan="1">
                                ${i.fccontent}
                            </td>
                        </tr>
                            <tr id="modifybox${i.fcno}_Opened" class="modifybox" style="display: none;">
                            <td colspan="2" rowspan="1">
                            <form action='./fantasyModifyComment' method='post' onsubmit='return checkCommentModify()'>
                            <textarea name='fccontent' id='fccontent' class='fccontent'>${i.fccontent}</textarea>
                            <input type="hidden" name="fcno" value="${i.fcno}">
                            <input type="hidden" name="fno" value="${i.fno}">
                            <input type="hidden" name="pageNo" value="${pageNo}">
							<div class="commentModifyButtonBox">
							<button id="commentModifySubmit">수정하기</button>
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
                <form action="./comment" method="post" onsubmit='return checkCommentWrite()'>
                    <textarea id="commentWriteForm" class="fccontent" name="fccontent"></textarea>
                    <input type="hidden" name="fno" value="${dto.fno}">
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