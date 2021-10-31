<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
<link href = "./summernote/summernote.min.css" rel="stylesheet">
<script src="./summernote/summernote.min.js"></script>
<script>
        $(document).ready(function(){
            $('#dcontent').summernote({tabsize: 2, height: 420, maxHeight: 600, maxWidth:1350});

            $("#thumbnailFile").on('change',function(){
                var fileName = $("#thumbnailFile").val();
                $(".uploadName").val(fileName);
            });
        });
</script>
<script src='./js/boardWrite.js'></script>
<script type="text/javascript">
	function check() {
		var title = document.getElementById("title");
		var content = document.getElementById("content");

		if (title.value.length < 5 || title.value == "") {
			alert("제목을 5글자 이상 작성해주세요.");
			title.focus();
			return false;
		}
		if (content.value == "" || content.value.length < 5) {
			alert("내용을 5글자 이상 작성해주세요.");
			content.focus();
			return false;
		}
	}
</script>
<style>
  @import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&family=Gowun+Batang:wght@400;700&family=Limelight&family=Londrina+Solid&display=swap');
</style>
<link rel="stylesheet" href="./css/base.css">
<link rel="stylesheet" href="./css/boardWrite.css">
</head>

<body>
<div id="headerSpace">
</div>
<div id="writeFormContainer">
<form action="./thrillerWrite" method="post" onsubmit="return check();"
		enctype="multipart/form-data">
	<h1>리뷰 작성</h1>
	<hr class="styleHR">
        <div id="genreInfo">
        
            <span class="writeItem">장르</span><br><p class="genreText">스릴러</p>
        </div>
        	
        <div id="titleInput">
            <span class="writeItem">제목</span><br>
            <input type="text" name="title" id="dtitle">
        </div>
        <div id="thumbnailInput">
            <span class="writeItem">썸네일</span><br>
            <input class="uploadName">
            <input type="file" name="file" id="thumbnailFile" accept=".gif, .png, .jpg">
            <label for="thumbnailFile">찾아보기</label>
            <span class="tip">&nbsp;&nbsp;3:2 비율의 사진을 사용하시면 깨끗하게 출력됩니다. </span>
            </div>
        <div id="contentInput">
        <textarea name="content" id="dcontent" row="20" cols="30" resize=none;>
	    </textarea>
        </div>
        <button id="writeSubmit" type="submit">글쓰기</button>
    </form>
</div>
<div class="marginSpace"></div>
<div id="footerSpace"></div>
</body>
</html>