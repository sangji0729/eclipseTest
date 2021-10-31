<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>thrillerDetail</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"
	integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
	crossorigin="anonymous"></script>
<script src='./js/boardDetail.js'></script>
<style>
  @import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Limelight&family=Londrina+Solid&display=swap');
</style>
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/boardDetail.css">
<script type="text/javascript">
function deleteConfirm(tno){
	if(confirm("해당 글을 삭제하겠습니까?")){
		location.href="./thrillerBoardDelete?tno="+tno;
	}
}

function deleteComment(tcno, tno){
	if(confirm("해당 댓글을 삭제하겠습니까?")){
		location.href="./thrillerCommentDelete?tcno="+tcno + "&tno=" + tno;
	}
}

function checkCommentWrite() {
	   var dccontent = document.getElementById("commentWriteForm");
	   if (tccontent.value == "" || tccontent.value.length < 10) {
	      alert("내용을 10글자 이상 적어주세요.");
	      tccontent.focus();
	      return false;
	      }   
	   }
function checkCommentModify() {
    var dccontent = document.getElementById("tccontent");
    if (tccontent.value == "" || tccontent.value.length < 10) {
       alert("내용을 10글자 이상 적어주세요.");
       dccontent.focus();
       return false;
       }   
    }
function modifyBox(tcno) {
	var boxOpened = document.getElementById("modifybox"+tcno+"_Opened");
	if(boxOpened.style.display == 'contents'){
		boxOpened.style.display = 'none';
	} else{
		boxOpened.style.display = 'contents';
	}
}

	function deleteConfirm(tno){
		if(confirm("해당 글을 삭제하겠습니까?")){
			location.href="./thrillerBoardDelete?tno="+tno;
		}
	}
	function cantLike() {
		alert("추천 기능은 로그인 후 이용 가능합니다.");
	}
	
	function cantLikeError() {
		alert("추천 정보를 불러오지 못했습니다.")
	}
	
	function toLikeUp(tno) {
	if(confirm("추천하시겠습니까?")){
	$.ajax({
		type: 'GET',
		dataType:'TEXT',
		data:'tno='+tno,
		url: './thrillerLike',
		async : false,
		success: function(rData, textStatus, xhr){
			var checkResult = rData;
			if(checkResult == 1) {
				
				alert('추천하였습니다.');
				var tlike = $('#likeValue').children('.valueText').text();
				tlike = parseInt(tlike) + 1;
				$('#likeValue').children('.valueText').text(tlike);
				$("#likeLogo").html('<img src="./img/like.png" height="35px" alt="comment" onclick="toDeleteLike(${dto.tno})">');
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
	
	function toDeleteLike(tno) {
		if(confirm("추천을 취소하시겠습니까?")){
			$.ajax({
				type: 'GET',
				dataType:'TEXT',
				data:'tno='+tno,
				url: './thrillerLikeDelete',
				async : false,
				success: function(rData, textStatus, xhr){
					var checkResult = rData;
					if(checkResult == 1) {
						alert('추천을 취소하였습니다.');
						var tlike = $('#likeValue').children('.valueText').text();
						tlike = parseInt(alike) - 1;
						$('#likeValue').children('.valueText').text(tlike);
						$("#likeLogo").html('<img src="./img/unlike.png" height="35px" alt="comment" onclick="toLikeUp(${dto.tno})">');
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
    <div id="headerSpace"></div>
    <div id="detailMain">
    <div id="backgroundThumbnail" >
    </div>

    <div id="articleContainer"> <!--그라디언트 적용하기-->
                <div id="articleInner1">
                <div id="articleGenre">
                    <span>스릴러</span>
                </div>
    <div id="articleTitle">
                    <span>${dto.ttitle}</span>
                </div>
                <div id="articleInfo">
                    <div id="articleAuthor"><span>${dto.name}(${dto.id})</span></div>
                    <div id="articleDate"><span>${dto.tdate}</span></div>
                </div>
                </div>
                <div id="articleInner2">
                <div id="articleContent">
                    ${dto.tcontent} <br> <c:if test="${dto.tfilename ne null }">
								<img alt="img" src="./upload/${dto.tfilename }" >
							</c:if>
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
                            <img src="./img/unlike.png" height="35px" alt="comment">
                            </c:when>
                            <c:when test="${canLike == 0}">
                            <img src="./img/unlike.png" height="35px" alt="comment" onclick="toLikeUp(${dto.tno})">
                        </c:when>
                        <c:when test="${canLike == 1}">
                        	<img src="./img/like.png" height="35px" alt="comment" onclick="toDeleteLike(${dto.tno})">
                        </c:when>
                        <c:otherwise>
                        	 <img src="./img/unlike.png" height="35px" alt="comment" onclick="cantLikeError()">
                        </c:otherwise>   
                        </c:choose>
                        </div>
                    <div id="likeValue">
                            <span class="articleLikeText"> 추천 </span>&nbsp;<span class="valueText">${dto.tlike }</span>
                        </div>
                    </div>
                    <div id="articleModify">
                    	<c:if test="${sessionScope.id eq dto.id}">
                        <span class="modifyText">
                        <a href="./thrillerUpdate?tno=${dto.tno}">수정</a>
                        &nbsp;&nbsp;&nbsp;
                        <a onclick="deleteConfirm(${dto.tno});">삭제</a></span>
                        </c:if>
					<c:if test="${sessionScope.grade eq 1 }">
							<button onclick="location.href='./adminPost'">글관리 페이지로돌아가기</button>
								<form action="./thrillerBoardDelete" method="get">
									<button type="submit">관리자 권한 삭제</button>
									<input type="hidden" name="tno" value="${dto.tno }">
								</form>
						</c:if></div></div></div>

<div class="marginBar">
                <hr class="styleHR3">
            </div>
				<div id="commentContainer">
                <div id="commentListInner">
                    <table id="commentList">
                    <c:forEach items="${commentList }" var="i">
					<!-- 여기에 댓글 찍기 -->
					 <tbody>
                        <tr>
                        <td class="commentAuthorAndDate">
                                <span class="commentAuthorText">${i.name }((<small>${i.id }</small>))</span>
                                &nbsp;&nbsp;&nbsp;
                                <span class="dateText">${i.tcdate }</span>
                            </td>
                           <td class="commentModify"> 
                            <c:if test="${i.id eq sessionScope.id}">
                                <span class="modifyText">
                             <a onclick="modifyBox(${i.tcno})">수정</a>
										&nbsp;&nbsp;
                                <a onclick="deleteComment(${i.tcno }, ${i.tno })">삭제</a>
                                </span>
									</c:if>
									</td>
									</tr>
								<tr>
                            <td class="commentContent" colspan="4" rowspan="1">
                                ${i.tccontent}
                            </td>
                        </tr>
								<tr id="modifybox${i.tcno}_Opened" class="modifybox" style="display: none;">
                            <td colspan="2" rowspan="1">
                            <form action='./thrillerCommentUpdate' method='post' onsubmit='return checkCommentModify()'>
                            <textarea name='tccontent' id='tccontent'>${i.tccontent}</textarea>
                            <input type="hidden" name="tcno" value="${i.tcno}">
                            <input type="hidden" name="tno" value="${i.tno}">
                            <input type="hidden" name="page" value="${page}">
							<div class="commentModifyButtonBox">
							<button id="commentModifySubmit">수정하기</button>
							</div>
							</form>
							</td>
                        </tr>
                        </c:forEach>
							</tbody>	
							</table></div>
					
					<div id="commentWriteInner">
                <c:if test="${sessionScope.id ne null}">
                <form action="./thrillerCommentWrite" method="post" onsubmit='return checkCommentWrite()'>
										<textarea id="commentWriteForm" name="tccontent"></textarea>
                    <input type="hidden" name="tno" value="${dto.tno}">
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