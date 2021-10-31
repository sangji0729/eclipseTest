<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>	    
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>MyInfo - WebtoonReview</title>
  <script type="text/javascript">
  $(document).ready(function(){
  	$(function(){
  	$("#UpdateInfoSubmit").prop("disabled", true);
  	});

  	$("#name, #pw1, #pw2, #email").blur(function() { check(); } );
  	function check() {
  		var name = $("#name").val();
  		var pw1 = $("#pw1").val();
  		var pw2 = $("#pw2").val();
  		var email = $("#email").val();
  		
  		
  		if(name == "" || name.length < 2){
  			$("#UpdateInfo").prop("disabled", true); //비활성화
  			$("#checkName").text("이름은 두 글자 이상으로 입력해주세요.");
  			$("#checkName").css("color", "#da2244");
  			$("#name").attr('style','background: rgba(218, 34, 68, 0.65)');
  		} else if (name.length > 2){
  			 $("#checkName").text('사용 가능한 이름입니다.');
  			 $("#checkName").css("color", "#0aca4a");
  			// $("#name").attr('style','border:3px solid #0aca4a');
  		}
  	
  		if (pw1 == "" || pw1.length < 5 || pw2.length < 5) {
  			$("#UpdateInfo").prop("disabled", true); //비활성화
  			$("#checkPw").text("비밀번호는 5자리 이상의 문자를 사용해주세요.");
  			$("#checkPw").css("color", "#da2244");
  			$("#pw1").attr('style','background: rgba(218, 34, 68, 0.65)');
  		}	
  		if (pw1 != pw2) {
  			$("#UpdateInfo").prop("disabled", true); //비활성화
  			$("#checkPw").text("비밀번호가 일치하지 않습니다.");
  			$("#checkPw").css("color", "#da2244");
  			$("#pw2").attr('style','background: rgba(218, 34, 68, 0.65)');
  		}

  		if (pw1.length > 5 && pw1 == pw2){
  			 $("#checkPw").text('사용 가능한 비밀번호입니다.');
  			 $("#checkPw").css("color", "#0aca4a");
  		}

  		if(email == "" || email.length < 5){
  			$("#UpdateInfo").prop("disabled", true); //비활성화
  			$("#checkEmail").text("이메일 주소를 입력해주세요.");
  			$("#checkEmail").css("color", "#da2244");
  			$("#email").attr('style','background: rgba(218, 34, 68, 0.65)');
  			}
  		$.ajax({
  			type: 'POST',
  			dataType:'TEXT',
  			data : 'email='+email, 
  			url: './emCheck',
  			async : false,
  			success: function(rData, textStatus, xhr){
  				var checkResult = rData;
  				if(checkResult == 1) {
  					 $("#UpdateInfo").prop("disabled", true); //비활성화
  					 $("#checkEmail").text('이미 등록된 이메일입니다.');
  					 $("#checkEmail").css("color", "#da2244");
  					 $("#email").attr('style','background: rgba(218, 34, 68, 0.65)');
  				} else if (checkResult == 0 && email.length > 5){
  					 $("#checkEmail").text('사용 가능한 이메일입니다.');
  					 $("#checkEmail").css("color", "#0aca4a");
  					// $("#email").attr('style','border:3px solid #0aca4a'); 
  				}
  				if (  checkResult == 0 && email.length > 5
  						&& name.length > 2
  						&& pw1.length > 5 && pw1 == pw2) {
  					$("#UpdateInfo").prop("disabled", false); //활성화
  				}
  			},
  			error:function(xhr, status, e){
  				alert("문제 발생 : " + e)
            }
        });
    }
});
  </script>
  <style>
    @import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Limelight&family=Londrina+Solid&display=swap');
  </style>
  <link rel="stylesheet" href="./css/base.css">
  <link rel="stylesheet" href="./css/myInfo.css">
  <style>
    #infoTable {
      display: table;
      /* border-bottom: 1px #000000 solid; */
      /* height: 62px; */
      /* border: 1px #04bfad solid; */
    }

    .infoTableRow {
      display: table-row;
      /* border: 1px #000000 solid; */
    }

    .infoTableCell {
      display: table-cell;
      /* border-bottom: 1px #000000 solid; */
    }

    .infoTableCell.cell1 {
      background: #04BFAD;
      color: white;
      height: 78px;
      vertical-align: middle;
      text-align: center;
      width: 121px;
      font-weight: bolder;
    }

    .infoTableCell.cell2 {
      border-bottom: 1px #ffffff solid;
      width: 480px;
      background: #eaeaea;
      vertical-align: middle;
      padding-left: 15px;
    }

    .checkText{
      text-align: left;
      /* margin: 0 0 0 15px; */
      color: #da2244;
      font-weight: 600;
      font-size: 11pt;
    }

    .inputInfo {
      height: 25px;
      width: 80%;
      /* margin-left: 14px; */
      background: transparent;
      border: none;
      font-size: 13pt;
      color: white;
      border-bottom: 2px #04bfad solid;
    }

    .inputInfo::placeholder {
      color: rgba(0, 33, 77, 0.32);
    }

    #UpdateInfoSpace {
      margin-top: 30px;
      text-align: right;
      width: 616px;
    }
    
    #UpdateInfo {
      width: 170px;
      height: 55px;
      background: #04bfad;
      color: white;
      border: none;
      border-radius: 15px;
      font-size: 18px;
    }

    #UpdateInfo:hover {
      background: #003857;
    }
  </style>
</head>
<body>
<div id="headerSpace">
</div>

<div id="myInfo">
  <div id="myInfoSideMenuContainer">
    <div id="myInfoSideMenu">
      <ul class="item_myInfoSideMenu">
        <li class="active"><a href="./myInfo"><img src="./img/info_id.png">내 정보</a></li>
        <li><a href="./actionInfoIdentify"><img src="./img/info_article.png">작성한 글</a></li>
        <li><a href="./actionInfoCommentIdentify"><img src="./img/info_comment.png" height="50px">작성한 댓글</a></li>
        <li><a href="./actionInfoLikeIdentify"><img src="./img/info_like.png">추천한 글</a></li>
        <li><a href="./myAccountDelete.jsp"><img src="./img/info_close.png">탈퇴하기</a></li>
      </ul>
    </div>
  </div>
  <div id="myInfoMainContainer">
    <div id="myInfoMain">
      <div id="myInfoMainContent_title">
        <img src="./img/myinfoItem.png" class="myInfoItemImg"><h1>나의 정보</h1>
        <hr class="styleHR6">
      </div>
      	<form action="./myInfoUpdate" method="post">
            <div id="infoTable">
              <div class="infoTableRow">
                <div class="infoTableCell cell1">아이디</div>
                  <div class="infoTableCell cell2">
                      ${my.id }
                  </div>
              </div>
              <div class="infoTableRow">
                <div class="infoTableCell cell1">이름</div>
                <div class="infoTableCell cell2">
                  <input type="text" name="name" id="name" class="inputInfo" value="${my.name }" placeholder="이름을 입력하세요.">
                </div>
                <div class="checkText" id="checkName"></div>
              </div>
              <div class="infoTableRow">
                <div class="infoTableCell cell1">비밀번호</div>
                <div class="infoTableCell cell2">
                  <input type="password" name="pw1" id="pw1" class="inputInfo" placeholder="비밀번호를 입력하세요.">
                </div>
              </div>
              <div class="infoTableRow">
                <div class="infoTableCell cell1">비밀번호 확인</div>
                <div class="infoTableCell cell2">
                  <input type="password" name="pw2" id="pw2" class="inputInfo" placeholder="비밀번호를 한 번 더 입력하세요.">
                  <div class="checkText" id="checkPw">안되는 비밀번호입니다~</div>
                </div>
              </div>
              <div class="infoTableRow">
                <div class="infoTableCell cell1">이메일 주소</div>
                <div class="infoTableCell cell2">
                  <input type="email" name="email" id="email" class="inputInfo" value="${my.email }" placeholder="이메일 주소를 입력하세요.">

                  <div class="checkText" id="checkEmail"></div>
                </div>
              </div>
              </div>
            <div id="UpdateInfoSpace">
              <button id="UpdateInfo">적용하기</button>
            </div>
            </form>
    </div>
    </div>
</div>
<div class="marginSpace"></div>
<div id="footerSpace"></div>
</body>
</html>