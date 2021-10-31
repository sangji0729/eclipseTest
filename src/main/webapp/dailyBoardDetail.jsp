<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>${dto.datitle} - WebtoonReview</title>
<script
src="https://code.jquery.com/jquery-3.6.0.min.js"
integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
crossorigin="anonymous"></script>
<script src='./js/boardDetail.js'></script>

<script type="text/javascript">
	function deleteConfirm(dano, pageNo){
		if(confirm("해당 글을 삭제하겠습니까?")){
			location.href="./dailyBoardDelete?dano=" + dano + "&pageNo=" + pageNo;
		}
	}
	
	function deleteComment(dacno, dano, pageNo){
		if(confirm("해당 댓글을 삭제하겠습니까?")){
			location.href="./dailyCommentDelete?dano=" + dano + "&dacno=" + dacno + "&pageNo=" + pageNo;
		}
	}

	function checkCommentWrite() {
		   var daccontent = document.getElementById("commentWriteForm");
		   if (daccontent.value == "" || daccontent.value.length < 10) {
		      alert("내용을 10글자 이상 적어주세요.");
		      daccontent.focus();
		      return false;
		      }   
		   }
	
	 function checkCommentModify() {
	      var daccontent = document.getElementById("daccontent");
	      if (daccontent.value == "" || daccontent.value.length < 10) {
	         alert("내용을 10글자 이상 적어주세요.");
	         daccontent.focus();
	         return false;
	         }   
	      }
	
	function modifyBox(dacno) {
		var boxOpened = document.getElementById("modifybox"+dacno+"_Opened");
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
	
	function toLikeUp(dano) {
	if(confirm("추천하시겠습니까?")){
	$.ajax({
		type: 'GET',
		dataType:'TEXT',
		data:'dano='+dano,
		url: './dailyLike',
		async : false,
		success: function(rData, textStatus, xhr){
			var checkResult = rData;
			if(checkResult == 1) {
				
				alert('추천하였습니다.');
				var dalike = $('#likeValue').children('.valueText').text();
				dalike = parseInt(dalike) + 1;
				$('#likeValue').children('.valueText').text(dalike);
				$("#likeLogo").html('<img src="./img/like.png" height="35px" alt="comment" onclick="toDeleteLike(${dto.dano})">');
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
	
	function toDeleteLike(dano) {
		if(confirm("추천을 취소하시겠습니까?")){
			$.ajax({
				type: 'GET',
				dataType:'TEXT',
				data:'dano='+dano,
				url: './dailyLikeDelete',
				async : false,
				success: function(rData, textStatus, xhr){
					var checkResult = rData;
					if(checkResult == 1) {
						alert('추천을 취소하였습니다.');
						var dalike = $('#likeValue').children('.valueText').text();
						dalike = parseInt(dalike) - 1;
						$('#likeValue').children('.valueText').text(dalike);
						//$("#likeLogo").children('img').attr("src", "./img/unlike.png");
						$("#likeLogo").html('<img src="./img/unlike.png" height="35px" alt="comment" onclick="toLikeUp(${dto.dano})">');
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
    <c:if test="${dto.dathumbnail ne null}">
    style="background-image: url('./thumbnail/${dto.dathumbnail}');"
    </c:if>
    >
    </div>
        <div id="articleContainer"> <!--그라디언트 적용하기-->
                <div id="articleInner1">
                <div id="articleGenre">
                    <span>일상</span>
                </div>
                <div id="articleTitle">
                    <span>${dto.datitle}</span>
                </div>
                <div id="articleInfo">
                    <div id="articleAuthor"><span>${dto.name}(${dto.id})</span></div>
                    <div id="articleDate"><span>${dto.dadate}</span></div>
                </div>
                </div>
                <div id="articleInner2">
                <div id="articleContent">
                    ${dto.dacontent }
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
                            <img src="./img/unlike.png" height="35px" alt="comment" onclick="toLikeUp(${dto.dano})">
                        </c:when>
                        <c:when test="${canLike == 1}">
                        	<img src="./img/like.png" height="35px" alt="comment" onclick="toDeleteLike(${dto.dano})">
                        </c:when>
                        <c:otherwise>
                        	 <img src="./img/unlike.png" height="35px" alt="comment" onclick="cantLikeError()">
                        </c:otherwise>   
                        </c:choose>
                        </div>
                        <div id="likeValue">
                            <span class="articleLikeText"> 추천 </span>&nbsp;<span class="valueText">${dto.dalike}</span>
                        </div>
                    </div>
                    <div id="articleModify">
                    	<c:if test="${sessionScope.id eq dto.id }">
                        <span class="modifyText">
                        <a href='./dailyUpdate?dano=${dto.dano}&pageNo=${pageNo}'>수정</a>
                        &nbsp;&nbsp;&nbsp;
                        <a onclick="deleteConfirm(${dto.dano},${pageNo});">삭제</a></span>
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
                                <span class="dateText">${i.dacdate}</span>
                            </td>
                            <td class="commentModify">
                            <c:if test="${i.id eq sessionScope.id }">
                                <span class="modifyText">
                                <a onclick="modifyBox(${i.dacno})">수정</a>
                                &nbsp;&nbsp;
                                <a onclick="deleteComment(${i.dacno}, ${i.dano}, ${pageNo})">삭제</a>
                                </span>
                            </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="commentContent" colspan="4" rowspan="1">
                                ${i.daccontent }
                            </td>
                        </tr>
                            <tr id="modifybox${i.dacno}_Opened" class="modifybox" style="display: none;">
                            <td colspan="2" rowspan="1">
                            <form action='./dailyCommentModify' method='post' onsubmit='return checkCommentModify()'>
                            <textarea name='daccontent' id='daccontent'>${i.daccontent}</textarea>
                            <input type="hidden" name="dacno" value="${i.dacno}">
                            <input type="hidden" name="dano" value="${i.dano}">
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
                <form action="./dailyCommentInput" method="post" onsubmit='return checkCommentWrite()'>
                    <textarea id="commentWriteForm" name="content"></textarea>
                    <input type="hidden" name="dano" value="${dto.dano}">
                    <input type="hidden" name="pageNo" value="${pageNo}">
                    <button id="commentWriteSubmit">댓글 작성</button>
                    </form>
                </div>
            </div>
        <div class="marginSpace"></div>
        <div id="footerSpace"></div>
        </div>
    </div>
</body>
</html>