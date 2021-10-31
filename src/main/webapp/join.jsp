<%@page import="join.JoinDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Join</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4="
	crossorigin="anonymous"></script>
<script src='./js/join.js'></script>
<style>
@import
	url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Limelight&family=Londrina+Solid&display=swap')
	;
</style>
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/join.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<script type="text/javascript">
$(document).ready(function(){
    $(function(){
        $("#joinSubmitSubmit").prop("disabled", true);
    });

    $("#id, #name, #pw1, #pw2, #email").blur(function() { check(); } );

    function check() {
        var id = $("#id").val();
        var name = $("#name").val();
        var pw1 = $("#pw1").val();
        var pw2 = $("#pw2").val();
        var email = $("#email").val();
        var idOk = 0;

        if (id == "" || id.length < 5) {
            $("#joinSubmitSubmit").prop("disabled", true); //비활성화
            $("#checkID").text("아이디는 5글자 이상으로 사용해주세요.");
            $("#checkID").css("color", "#da2244");
            $("#checkIDLogo").html("<img src='./img/no.png' height='25px' alt='checkLogo'>");
            //$("#id").attr('style','border:3px solid #da2244');
            idOk = 0;
        }
        $.ajax({
            type: 'POST',
            dataType: 'TEXT',
            data: 'id=' + id,
            url: './idCheck',
            async: false,
            success: function (rData, textStatus, xhr) {
                var checkResult = rData;
                //	alert("성공 : " + checkResult);
                if (checkResult == 1) {
                    $("#joinSubmitSubmit").prop("disabled", true);
                    $("#checkID").text('이미 등록된 아이디입니다.');
                    $("#checkID").css("color", "#da2244");
                    $("#checkIDLogo").html("<img src='./img/no.png' height='25px' alt='checkLogo'>");
                    // $("#id").attr('style','border:3px solid #da2244');
                    idOk = 0;
                } else if (checkResult == 0 && id.length > 4) {
                    $("#checkID").css("color", "#0aca4a");
                    // $("#id").attr('style','border:3px solid #0aca4a');
                    idOk = 1;
                    $("#checkID").text('사용 가능한 ID입니다.');
                    $("#checkIDLogo").html("<img src='./img/yes.png' height='25px' alt='checkLogo'>");
                }
            },
            error: function (xhr, status, e) {
                alert("문제 발생 : " + e)
            }

        });


        if (name == "" || name.length < 2) {
            $("#joinSubmit").prop("disabled", true); //비활성화
            $("#checkName").text("이름은 두 글자 이상으로 입력해주세요.");
            $("#checkName").css("color", "#da2244");
            $("#checkNameLogo").html("<img src='./img/no.png' height='25px' alt='checkLogo'>");
            //$("#name").attr('style','border:3px solid #da2244');
        } else if (name.length > 2) {
            $("#checkName").text('사용 가능한 이름입니다.');
            $("#checkName").css("color", "#0aca4a");
            // $("#name").attr('style','border:3px solid #0aca4a');
            $("#checkNameLogo").html("<img src='./img/yes.png' height='25px' alt='checkLogo'>");
        }

        if (pw1 == "" || pw1.length < 5 || pw2.length < 5) {
            $("#joinSubmit").prop("disabled", true); //비활성화
            $("#checkPw").text("비밀번호는 5자리 이상의 문자를 사용해주세요.");
            $("#checkPw").css("color", "#da2244");
            $("#checkPwLogo").html("<img src='./img/no.png' height='25px' alt='checkLogo'>");
            //$("#pw1").attr('style','border:3px solid #da2244');
        }
        if (pw1 != pw2) {
            $("#joinSubmit").prop("disabled", true); //비활성화
            $("#checkPw").text("비밀번호가 일치하지 않습니다.");
            $("#checkPw").css("color", "#da2244");
            $("#checkPwLogo").html("<img src='./img/no.png' height='25px' alt='checkLogo'>");
        }

        if (pw1.length > 5 && pw1 == pw2) {
            $("#checkPw").text('사용 가능한 비밀번호입니다.');
            $("#checkPw").css("color", "#0aca4a");
            $("#checkPwLogo").html("<img src='./img/yes.png' height='25px' alt='checkLogo'>");
        }

        if (email == "" || email.length < 5) {
            $("#joinSubmit").prop("disabled", true); //비활성화
            $("#checkEmail").text("이메일 주소를 입력해주세요.");
            $("#checkEmail").css("color", "#da2244");
            $("#checkEmailLogo").html("<img src='./img/no.png' height='25px' alt='checkLogo'>");
            //$("#email").attr('style','border:3px solid #da2244');
        }
        $.ajax({
            type: 'POST',
            dataType: 'TEXT',
            data: 'email=' + email,
            url: './emCheck',
            async: false,
            success: function (rData, textStatus, xhr) {
                var checkResult = rData;
                if (checkResult == 1) {
                    $("#joinSubmit").prop("disabled", true); //비활성화
                    $("#checkEmail").text('이미 등록된 이메일입니다.');
                    $("#checkEmail").css("color", "#da2244");
                    $("#checkEmailLogo").html("<img src='./img/no.png' height='25px' alt='checkLogo'>");
                    //$("#email").attr('style','border:3px solid #da2244');
                } else if (checkResult == 0 && email.length > 5) {
                    $("#checkEmail").text('사용 가능한 이메일입니다.');
                    $("#checkEmail").css("color", "#0aca4a");
                    $("#checkEmailLogo").html("<img src='./img/yes.png' height='25px' alt='checkLogo'>");
                    // $("#email").attr('style','border:3px solid #0aca4a');
                }
                if (checkResult == 0 && email.length > 5
                    && id.length > 5 && idOk == 1
                    && name.length > 2
                    && pw1.length > 5 && pw1 == pw2) {
                    $("#joinSubmit").prop("disabled", false); //활성화
                }
            },
            error: function (xhr, status, e) {
                alert("문제 발생 : " + e)
            }
        });
    }
});
</script>
<body>
<div id="headerSpace">
</div>
<div id="joinMain">
    <div id="leftContainer">
        <div class="bigTextBox">
            <p class="bigText">Welcome</p>
        </div>
        <div class="smallTextBox">
            <p class="smallText">지금, 웹툰을 보는 눈이 풍요로워집니다.</p>
        </div>
    </div>
    <div id="joinBoxContainer">
        <h1>Create Account</h1>
        <div id="joinBox">
        <form action="./join" method="post">
            <div class="inputInfoBox">
                <input type="text" class="inputInfo" id="id" name="id" placeholder="아이디를 입력하세요." required="required">
                <div id="checkIDLogo" class="checkLogoSpace"><!--  <img src=".\img\no.png" height="25px" alt="checkLogo">--></div>
            </div>
            <div id="checkID" class="checkText">
            </div>
            <div class="inputInfoBox">
                <input type="text" class="inputInfo" id="name" name="name" placeholder="이름을 입력하세요." required="required">
                <div id="checkNameLogo" class="checkLogoSpace"></div>
            </div>
            <div id="checkName" class="checkText">

            </div>
            <div class="inputInfoBox">
                <input type="text" class="inputInfo" id="email" name="email" placeholder="이메일 주소를 입력하세요." required="required">
                <div id="checkEmailLogo" class="checkLogoSpace"></div>
            </div>
            <div id="checkEmail" class="checkText">

            </div>
            <div class="inputInfoBox">
                <input type="password" class="inputInfo" id="pw" name="pw" placeholder="비밀번호를 입력하세요." required="required">
            </div>
            <div class="inputInfoBox">
                <input type="password" class="inputInfo" id="pw2" name="pw2" placeholder="비밀번호를 한 번 더 입력하세요." required="required">
                <div id="checkPwLogo" class="checkLogoSpace"><!-- <img src=".\img\yes.png" height="25px" alt="checkLogo"> --></div>
            </div>
            <div id="checkPw" class="checkText">
            </div>
            <button type="submit">SIGN UP</button>
          </form>
        </div>
    </div>
</div>
<div class="marginSpace"></div>
<div id="footerSpace"></div>
</body>
</html>