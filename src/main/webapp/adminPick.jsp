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
<meta charset="UTF-8">
    <title>WebtoonReview - 관리자 페이지</title>
<head>
<script src="https://code.jquery.com/jquery-3.6.0.js"
        integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossOrigin="anonymous"></script>
<script>
    function exampleShow(num) {
        $("#exampleIndex").attr("src", "./img/exampleEditorPick"+num+".png");
    }

    $(document).ready(function() {
        $("select[name='searchName']").on('change', function () {
            let select = $(this).val();
            switch(select) {
                case 'a' :
                    $(this).closest("div").next().children("span").html('글 주소의 <b>ano=""</b> 안에 있는 번호를 입력해주세요.<br><b>ano="');
                    break;
                case 'da' :
                    $(this).closest("div").next().children("span").html('글 주소의 <b>dano=""</b> 안에 있는 번호를 입력해주세요.<br><b>dano="');
                    break;
                case 'd' :
                    $(this).closest("div").next().children("span").html('글 주소의 <b>dno=""</b> 안에 있는 번호를 입력해주세요.<br><b>dno="');
                    break;
                case 'f' :
                    $(this).closest("div").next().children("span").html('글 주소의 <b>fno=""</b> 안에 있는 번호를 입력해주세요.<br><b>fno="');
                    break;
                case 'r' :
                    $(this).closest("div").next().children("span").html('글 주소의 <b>rno=""</b> 안에 있는 번호를 입력해주세요.<br><b>rno="');
                    break;
                case 't' :
                    $(this).closest("div").next().children("span").html('글 주소의 <b>tno=""</b> 안에 있는 번호를 입력해주세요.<br><b>tno="');
                    break;
            }
        });
    });
</script>

<style>
    @import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');
</style>
<style>
    #adminEditorPickContainer {
        margin: 30px auto 83px auto;
    }

    #exampleEditorPick {
    text-align: center;
    margin-bottom: 20px;
    }

    #exampleIndex {
        width: 500px;
    }

    .articleSetting {
        display: table;
        margin: auto;
        width: 53%;
        vertical-align: middle;
        border-bottom: 1px #04bfad solid;
    }

    .tableHead{
        display: table-header-group;
        background: #04bfad;
        font-weight: 600;
        height: 40px;
        /* width: 377px; */
        /* margin: 0 023px; */
        text-align: center;
    }

    .row {
        display: table-row;
        cursor: pointer;
        border-bottom: 6px #FFFFFF solid;
        /* box-sizing: border-box; */
    }

.articleSetting.row {}

    .cell1{
        display: table-cell;
        width: 10%;
        height: 60px;
        text-align: center;
    }

    .cell2{
        display: table-cell;
        width: 15%;
        text-align: center;
    }

    .cell3{
        display: table-cell;
        text-align: center;
        /* text-align: end; */
    }

    .cell2>select {
        width: 73px;
    }
    .noticeArticleNum {

    }

    .articleNum {
        width: 60px;
        background: #ffffff99;
        border: none;
        border-bottom: 3px #04bfad solid;
        border-radius: 4px;
        color: #003857;
    }

    .settingButtonSpace {
        margin-top: 15px;
        text-align: center;
    }
    
    #editorPickSetting {
        width: 170px;
        height: 50px;
        background: #04bfad;
        color: white;
        border: none;
        border-radius: 15px;
        font-size: 18px;
    }

    #editorPickSetting:hover {
        background: #003857;
    }
    
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
        <h3>[ 관리자 모드 ]</h3>
    </div>
    <div id="adminIdPrint">
        admin 님
    </div>
    <hr class="styleHR4">
  </div>
      <div id="adminSideMenuContainer">
          <div id="adminSideMenu">
              <ul class="item_AdminSideMenu">
                  <li><a href="#"><img src="./img/memberControl.png">회원 관리</a></li>
                  <li><a href="#"><img src="./img/articleControl.png">게시글 관리</a></li>
                  <li><a href="#"><img src="./img/log.png">로그 관리</a></li>
                  <li class="active"><a href="#"><img src="./img/article2.png">Editor's Pick</a></li>
                  <li><a href="#"><img src="./img/exit.png">나가기</a></li>
              </ul>
          </div>
      </div>
</nav>
<div id="adminMain">
    <div id="adminMainContainer">
            <div id="adminMainContent_title">
                <h1>Editor's Pick 설정</h1>
                <hr class="styleHR5">
            </div>
            <div id="adminMainContent">
            <p>인덱스 페이지의 Editor's Pick 코너에 노출할 글을 설정합니다.</p>
                <div id="adminEditorPickContainer">
                    <div id="exampleEditorPick">
                        <img src="./img/exampleEditorPick.png" id="exampleIndex">
                    </div>
                    <div class="articleSetting">
                       <div class="articleSetting tableHead">
                           <div class="articleSetting cell1">노출 순서</div>
                           <div class="articleSetting cell2">장르</div>
                           <div class="articleSetting cell3">글 번호</div>
                       </div>
                        <div class="articleSetting row" onclick="exampleShow(1)">
                            <div class="articleSetting cell1">1</div>
                            <div class="articleSetting cell2">
                                <select name="searchName">
                                    <option value="a">액션</option>
                                    <option value="da">일상</option>
                                    <option value="d">드라마</option>
                                    <option value="f">판타지</option>
                                    <option value="r">로맨스</option>
                                    <option value="t">스릴러</option>
                                </select>
                            </div>
                            <div class="articleSetting cell3">
                                <!--입력칸에 기존에 선택한 거 불러와짐-->
                                <!--처음 장르는 액션으로 선택되어 있음-->
                                <span class="noticeArticleNum">글 주소의 <b>ano=""</b> 안에 있는 번호를 입력해주세요.
                                <br>
                                <b>ano="</span><input type="number" class="articleNum" name="articleNum1">"</b>
                            </div>
                        </div>
                        <div class="articleSetting row" onclick="exampleShow(2)">
                            <div class="articleSetting cell1">2</div>
                            <div class="articleSetting cell2">
                                <select name="searchName">
                                    <option value="a">액션</option>
                                    <option value="da">일상</option>
                                    <option value="d">드라마</option>
                                    <option value="f">판타지</option>
                                    <option value="r">로맨스</option>
                                    <option value="t">스릴러</option>
                                </select>
                            </div>
                            <div class="articleSetting cell3">
                                <!--입력칸에 기존에 선택한 거 불러와짐-->
                                <!--처음 장르는 액션으로 선택되어 있음-->
                                <span class="noticeArticleNum">글 주소의 <b>ano=""</b> 안에 있는 번호를 입력해주세요.
                                <br>
                                <b>ano="</span><input type="number" class="articleNum" name="articleNum2">"</b>
                            </div>
                        </div>
                        <div class="articleSetting row" onclick="exampleShow(3)">
                            <div class="articleSetting cell1">3</div>
                            <div class="articleSetting cell2">
                                <select name="searchName">
                                    <option value="a">액션</option>
                                    <option value="da">일상</option>
                                    <option value="d">드라마</option>
                                    <option value="f">판타지</option>
                                    <option value="r">로맨스</option>
                                    <option value="t">스릴러</option>
                                </select>
                            </div>
                            <div class="articleSetting cell3">
                                <!--입력칸에 기존에 선택한 거 불러와짐-->
                                <!--처음 장르는 액션으로 선택되어 있음-->
                                <span class="noticeArticleNum">글 주소의 <b>ano=""</b> 안에 있는 번호를 입력해주세요.
                                <br>
                                <b>ano="</span><input type="number" class="articleNum" name="articleNum3">"</b>
                            </div>
                        </div>
                        <div class="articleSetting row" onclick="exampleShow(4)">
                            <div class="articleSetting cell1">4</div>
                            <div class="articleSetting cell2">
                                <select name="searchName">
                                    <option value="a">액션</option>
                                    <option value="da">일상</option>
                                    <option value="d">드라마</option>
                                    <option value="f">판타지</option>
                                    <option value="r">로맨스</option>
                                    <option value="t">스릴러</option>
                                </select>
                            </div>
                            <div class="articleSetting cell3">
                                <!--입력칸에 기존에 선택한 거 불러와짐-->
                                <!--처음 장르는 액션으로 선택되어 있음-->
                                <span class="noticeArticleNum">글 주소의 <b>ano=""</b> 안에 있는 번호를 입력해주세요.
                                <br>
                                <b>ano="</span><input type="number" class="articleNum" name="articleNum4">"</b>
                            </div>
                        </div>
                    </div>
                    <div class="settingButtonSpace">
                    <button type="submit" id="editorPickSetting">적용</button>
                    </div>
                    </div>
                </div>
            </div>
    </div>
</div>
</body>
</html>