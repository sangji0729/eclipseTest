<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="en">
<meta charset="UTF-8">
<head>
<title>WebtoonReview</title>
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.css">
<script src="https://cdn.jsdelivr.net/bxslider/4.2.12/jquery.bxslider.min.js"></script>
<script src='./js/index.js'></script>
<style>
@import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Limelight&family=Londrina+Solid&display=swap');
</style>
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/index.css">
</head>
<body>
<div id="headerSpace">
</div>
<div id="main">
    <div id="mainContainer1">
    <div id="mostPopular_Top">
            <div class="aarticle">
                <div class="thumbnailBox">
                    <img class="thumbnail" src="./img/test.jpg" alt="thumbnail">
                </div>
                <div class="contentBox">
                    <span style="font-size:40px;" class="noticableTitle"><b><a href="./boardDetail.html">제목</a></b></span>
                    <br>
                    <span class="genreText">장르</span>&nbsp;<span style="background-color: #E5E5E5">&nbsp;</span>&nbsp;
                    <span class="noticeableContent">
                    "어린 왕자가 있었다는 증거는 그 애가 멋있었다는 것이고, 그 애가 웃었다는 것이고, 그 애가 양을 갖고 싶어했다는 것이다. 누군가가 양을 갖고 싶어한다면 그것은 그 사람이 살아있다는 증거다"라고 어른들에게 말한다면, 그들은 어깨를 으쓱하며 여러분들을 어린아이로 취급할 것이다!
                     </span>
                </div>
            </div>
    </div>
        <div id="editor_PickList">
            <div class="listTitleBox"><span class="listTitle1">Editor's Pick</span></div>
            <div id="articleContainer_EP">
            <div class="article slide-top">
                <div class="thumbnailBox">
                    <img class="thumbnail" src="./img/test.jpg" alt="thumbnail">
                </div>
                <div class="contentBox">
                    <span class="articleTitle_EP">제목</span>
                    <br>
                    <span class="genreText">장르</span>&nbsp;<span style="background-color: #E5E5E5">&nbsp;</span>&nbsp;
                    그러나, "그는 소행성 B612로부터 왔다"고 말하면 어른들은 곧 알아듣고, 질문 따위를 늘어놓아 여러분들을 귀찮게 하지 않을 것이다. 어른들은 이렇다. 그들을 탓해서는 안 된다. 어린이들은 어른들에게...
                </div>
            </div>
            <div class="article">
                <div class="thumbnailBox">
                    <img class="thumbnail" src="./img/test.jpg" alt="thumbnail">
                </div>
                <div class="contentBox">
                    <span class="articleTitle_EP">제목</span>
                    <br>
                    <span class="genreText">장르</span>&nbsp;<span style="background-color: #E5E5E5">&nbsp;</span>&nbsp;
                    그러나 내가 그렇게 이야기하지 못한 것은 다른 사람들이 내 책을 가볍게 읽는 것이 싫기 때문이다. 이제 그 추억을 이야기하려니 슬픔이 그렇게도 크다. 내 친구가 양을 가지고 떠난 지도 어언 육 년이 되었다.
                </div>
            </div>
            <div class="article">
                <div class="thumbnailBox">
                    <img class="thumbnail" src="./img/test.jpg" alt="thumbnail">
                </div>
                <div class="contentBox">
                    <span class="articleTitle_EP">제목</span>
                    <br>
                    <span class="genreText">장르</span>&nbsp;<span style="background-color: #E5E5E5">&nbsp;</span>&nbsp;
                    내가 여기에다 그 모습을 그리려고 애를 쓰는 것은 그 애를 잊어버리지 않기 위해서이다. 누구나 다 친구를 가졌던 것은 아니다. 그리고 나도 숫자밖에는 관심이 없는 어른들처럼 되어 버릴지 모른다.
                </div>
            </div>
            </div>
        </div>
    </div>
    <!-- 인덱스 작업 -->
    <div id="mainContainer2">
        <div id="mostPopular_List">
            <div class="listTitleBox"><span class="listTitle1">Most Popular</span></div>
            <div class="articleContainer">
           <c:forEach items="${bestLikeList }" var="bll">
            <div class="article">
                <div class="thumbnailBox">
                  <!--   <img class="thumbnail" src="./img/test.jpg" alt="thumbnail"> 기존에 있던 테스트용 썸네일 -->
                  <c:choose>
							<c:when test="${bll.athumbnail ne null}">
                            <img class="thumbnail" src="./thumbnail/${bll.athumbnail}" alt="thumbnail">
                            </c:when>
							<c:otherwise>
							<img class="thumbnail" src="./thumbnail/noimage.png">
							</c:otherwise>
							</c:choose>
                </div>
                <div class="contentBox">
                    <span class="genreText">${bll.atable }</span>&nbsp;
                    <br>
                    <p class="articleTitle_MP"><a href="./${bll.atable }BoardDetail?${bll.num }=${bll.ano}">${bll.atitle}</a></p>
                    <!-- 글자수 제한 -->
                  	<c:set var="content" value="${bll.acontent } "/>
                  	<c:if test="${fn:length(content) <= 30 }">
                  		<c:out value="${content }"/>
                  	</c:if>
                  	<c:if test="${fn:length(content) > 30 }">
                  		<c:out value="${fn:substring(content, 0, 15) }"/>
                  	</c:if>
                  
                				
                    <!-- ${fn:substring(bll.acontent, 0, 30)} substring사용시 화면 짤림 현상 발생-->
                  
                </div>
            </div>
            </c:forEach>     
            </div>
        </div>
        <div id="promotion">
            <ul class="bxslider">
                <li><a href="#"><img src="./img/promotion1.png" alt=""></a></li>
                <li><a href="#"><img src="./img/promotion2.png" alt=""></a></li>
            </ul>
            <div id="prev-btn">
            </div>
            <div id="next-btn">
            </div>
        </div>
    </div>
    <div id="mainContainer3">
        <div class="listTitleBox"><span class="listTitle1">Most Commented</span></div>
        <div id="mostCommentedList">
            <div id="commentedTop">
             <c:forEach items="${bestCommentList }" var="bcl">
            <div class="article">
                <div class="thumbnailBox">
                 <!--   <img class="thumbnail" src="./img/test.jpg" alt="thumbnail"> 기존에 있던 테스트용 썸네일 -->
                   <c:choose>
							<c:when test="${bcl.athumbnail ne null}">
                            <img class="thumbnail" src="./thumbnail/${bcl.athumbnail}" alt="thumbnail">
                            </c:when>
							<c:otherwise>
							<img class="thumbnail" src="./thumbnail/noimage.png">
							</c:otherwise>
							</c:choose>
                </div>
                <div class="contentBox">
                    <span class="articleTitle_MCT"><a href="./${bcl.atable }BoardDetail?${bcl.num}=${bcl.ano}">${bcl.atitle }</a></span>
                    <br>
                    <span class="genreText">${bcl.atable }</span>&nbsp;<span style="background-color: #E5E5E5">&nbsp;</span>&nbsp;
                    <!-- 글자수 제한 -->
                  <c:set var="content" value="${bcl.acontent } "/>
                  	<c:if test="${fn:length(content) <= 30 }">
                  		<c:out value="${content }"/>
                  	</c:if>
                  	<c:if test="${fn:length(content) > 30 }">
                  		<c:out value="${fn:substring(content, 0, 15) }"/>
                  	</c:if>
                </div>
            </div>
            </c:forEach>
            </div>
            <div id="commented2nd_3rd">
            <c:forEach items="${secondCommentList }" var="scl">
            <div class="article">
                <div class="thumbnailBox">
                <!--   <img class="thumbnail" src="./img/test.jpg" alt="thumbnail"> 기존에 있던 테스트용 썸네일 -->
                   <c:choose>
							<c:when test="${scl.athumbnail ne null}">
                            <img class="thumbnail" src="./thumbnail/${scl.athumbnail}" alt="thumbnail">
                            </c:when>
							<c:otherwise>
							<img class="thumbnail" src="./thumbnail/noimage.png">
							</c:otherwise>
							</c:choose>
                </div>
                <div class="contentBox">
                    <span class="articleTitle_MC23"><a href="./${scl.atable }BoardDetail?${scl.num}=${scl.ano}">${scl.atitle }</a></span>
                    <br>
                    <span class="genreText">${scl.atable }</span>&nbsp;<span style="background-color: #E5E5E5">&nbsp;</span>&nbsp;
                    <!-- 글자수 제한 -->
                   	<c:set var="content" value="${scl.acontent } "/>
                  	<c:if test="${fn:length(content) <= 30 }">
                  		<c:out value="${content }"/>
                  	</c:if>
                  	<c:if test="${fn:length(content) > 30 }">
                  		<c:out value="${fn:substring(content, 0, 15) }"/>
                  	</c:if>
                </div>
            </div>
            </c:forEach>
            </div>
        </div>
    </div>
    <div id="mainContainer4">
        <div id="mostReadList">
            <div class="listTitleBox"><span class="listTitle1">Most Read</span></div>
        	<c:forEach items="${bestReadList }" var="brl">
        	<c:set var="i" value="${i+1 }"/>
                <div class="article">
                    <div class="ranking">   
                    ${i }     
                    </div>
                    <div class="thumbnailBox">
                    <!--   <img class="thumbnail" src="./img/test.jpg" alt="thumbnail"> 기존에 있던 테스트용 썸네일 -->
                       <c:choose>
							<c:when test="${brl.athumbnail ne null}">
                            <img class="thumbnail" src="./thumbnail/${brl.athumbnail}" alt="thumbnail">
                            </c:when>
							<c:otherwise>
							<img class="thumbnail" src="./thumbnail/noimage.png">
							</c:otherwise>
							</c:choose>
                    </div>
                    <div class="contentBox">
                        <span class="genreText">${brl.atable }</span>&nbsp;
                        <p class="articleTitle_MR"><b><a href="./${brl.atable }BoardDetail?${brl.num}=${brl.ano}">${brl.atitle }</a></b></p>
                    </div>
            </div>
            </c:forEach>
        </div>
        <div id="newArticleList">
            <div id="newArticleListTitleBox">
                <img class="articleLogo" src="./img/article_resize.png" alt="article">
                <span style="color: #04bfad; font-size: 40pt"><b>[</b></span>
                <p class="listTitle2">New Article</p>
                <span style="color: #04bfad; font-size: 40pt"><b>]</b></span>
            </div>
            <div id="newArticleListBox">
            <c:forEach items="${bestDateList }" var="bdl">
                    <ul>
                    <li class="article">
                        <span class="genreText_NA">${bdl.atable }</span>&nbsp;
                        <span class="articleTitle_NA"><a href="./${bdl.atable }BoardDetail?${bdl.num }=${bdl.ano}">${bdl.atitle }&nbsp;&nbsp;&nbsp;</a></span>
                        <span class="dateText">${bdl.adate }</span>
                    </li>
                    </ul>
			</c:forEach>
            </div>
        </div>
    </div>
    <div class="marginSpace"></div>
</div>
<div id="footerSpace"></div>
</body>
</html>