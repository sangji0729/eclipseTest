<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>${list.atitle } - WebtoonReview</title>
<script
src="https://code.jquery.com/jquery-3.6.0.min.js"
integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
crossorigin="anonymous"></script>
<script src='./js/boardDetail.js'></script>
<style>
  @import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Limelight&family=Londrina+Solid&display=swap');
</style>
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/boardDetail.css">
<script type="text/javascript">
	function deleteConfirm(ano){
		if(confirm("해당 글을 삭제하겠습니까?")){
			location.href="./actionBoardDelete?ano="+ano;
		}
	}
	//테스트
	
	function deleteComment(acno, ano){
		if(confirm("해당 댓글을 삭제하겠습니까?")){
			location.href="./actionCommentDelete?acno=" + acno + "&ano=" + ano;
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
		      var dccontent = document.getElementById("accontent");
		      if (dccontent.value == "" || dccontent.value.length < 10) {
		         alert("내용을 10글자 이상 적어주세요.");
		         dccontent.focus();
		         return false;
		         }   
		      }

	
	function modifyBox(acno) {
		var boxOpened = document.getElementById("modifybox"+acno+"_Opened");
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
	
	function toLikeUp(ano) {
	if(confirm("추천하시겠습니까?")){
	$.ajax({
		type: 'GET',
		dataType:'TEXT',
		data:'ano='+ano,
		url: './actionBoardLike',
		async : false,
		success: function(rData, textStatus, xhr){
			var checkResult = rData;
			if(checkResult == 1) {
				
				alert('추천하였습니다.');
				var alike = $('#likeValue').children('.valueText').text();
				alike = parseInt(alike) + 1;
				$('#likeValue').children('.valueText').text(alike);
				$("#likeLogo").html('<img src="./img/like.png" height="35px" alt="comment" onclick="toDeleteLike(${list.ano})">');
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
	
	function toDeleteLike(ano) {
		if(confirm("추천을 취소하시겠습니까?")){
			$.ajax({
				type: 'GET',
				dataType:'TEXT',
				data:'ano='+ano,
				url: './actionLikeDelete',
				async : false,
				success: function(rData, textStatus, xhr){
					var checkResult = rData;
					if(checkResult == 1) {
						alert('추천을 취소하였습니다.');
						var alike = $('#likeValue').children('.valueText').text();
						alike = parseInt(alike) - 1;
						$('#likeValue').children('.valueText').text(alike);
						//$("#likeLogo").children('img').attr("src", "./img/unlike.png");
						$("#likeLogo").html('<img src="./img/unlike.png" height="35px" alt="comment" onclick="toLikeUp(${list.ano})">');
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
</head>
<body>
<c:set value="${list }" var="l"/>
    <div id="headerSpace"></div>
    <div id="detailMain">
    <div id="backgroundThumbnail">
    </div>
        <div id="articleContainer"> <!--그라디언트 적용하기-->
                <div id="articleInner1">
                <div id="articleGenre">
                    <span>액션</span>
                </div>
                <div id="articleTitle">
                    <span>${l.atitle }</span>
                </div>
                <div id="articleInfo">
                    <div id="articleAuthor"><span>${l.name }</span></div>
                    <div id="articleDate"><span>${l.adate }</span></div>
                </div>
                </div>
                <div id="articleInner2">
                <div id="articleContent">
                   ${l.acontent } <br> <c:if
								test="${l.afilename ne null }">
								<img alt="img" src="./upload/${l.afilename }" >
							</c:if>
                </div>
                <div id="articlePopularity">
                    <div id="commentValueBox">
                        <div id="commentLogo">
                            <img src="./img/comment.png" height="35px" alt="comment">
                        </div>
                        <div id="commentValue">
                            <span class="articleCommentText"> 댓글 </span>&nbsp;<span class="valueText">${l.commentcount }</span>
                        </div>
                    </div>
                    <div id="likeValueBox">
                        <div id="likeLogo">
                        <c:choose>
                        <c:when test="${sessionScope.id eq null}">
                        	<img src="./img/unlike.png" height="35px" alt="comment" onclick="cantLike()">
                        </c:when>
                        <c:when test="${canLike == 0}">
                            <img src="./img/unlike.png" height="35px" alt="comment" onclick="toLikeUp(${list.ano})">
                        </c:when>
                        <c:when test="${canLike == 1}">
                        	<img src="./img/like.png" height="35px" alt="comment" onclick="toDeleteLike(${list.ano})">
                        </c:when>
                        <c:otherwise>
                        	 <img src="./img/unlike.png" height="35px" alt="comment" onclick="cantLikeError()">
                        </c:otherwise>   
                        </c:choose>
                        </div>
                        <div id="likeValue">
                            <span class="articleLikeText"> 추천 </span>&nbsp;<span class="valueText">${l.alike}</span>
                        </div>
                    </div>
                    <div id="articleModify">
                    <c:if test="${sessionScope.id eq l.id }">
                        <span class="modifyText">
                        	<a href="./actionBoardModify?ano=${l.ano}">수정</a>
                        	&nbsp;&nbsp;&nbsp; 
                        	<a onclick="deleteConfirm(${l.ano});"> 삭제</a>
                        </span>
                    </c:if>
                    <c:if test="${sessionScope.grade eq 1 }">
							<button onclick="location.href='./adminPost'">글관리 페이지로돌아가기</button>
								<form action="./actionBoardDelete" method="get">
									<button type="submit">관리자 권한 삭제</button>
									<input type="hidden" name="ano" value="${l.ano }">
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
                            <td class="commentAuthorAndDate">
                                <span class="commentAuthorText">${i.name }(${i.id})</span>
                                &nbsp;&nbsp;&nbsp;
                                <span class="dateText">${i.acdate}</span>
                            </td>
                            <td class="commentModify">
                             <c:if test="${i.id eq sessionScope.id}">
                                <span class="modifyText">
                                <a onclick="modifyBox(${i.acno})">수정</a>
                                &nbsp;&nbsp;
                                <a onclick="deleteComment(${i.acno }, ${i.ano })">삭제</a>
                                </span>
                            </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="commentContent" colspan="4" rowspan="1">
                                ${i.accontent}
                            </td>
                        </tr>
                        <tr id="modifybox${i.acno}_Opened" class="modifybox" style="display: none;">
                            <td colspan="2" rowspan="1">
                            <form action='./actionCommentModify' method='post' onsubmit='return checkCommentModify()'>
                            <textarea name='accontent' id='accontent'>${i.accontent}</textarea>
                            <input type="hidden" name="acno" value="${i.acno}">
                            <input type="hidden" name="ano" value="${i.ano}">
                            <input type="hidden" name="page" value="${page}">
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
                <form action="./actionCommentWrite" method="post" onsubmit='return checkCommentWrite()'>
                    <textarea id="commentWriteForm" name="accontent"></textarea>
                    <input type="hidden" name="ano" value="${list.ano}">
                    <input type="hidden" name="page" value="${page}">
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