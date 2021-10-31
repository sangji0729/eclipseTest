%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
if (request.getParameter("error") != null) {
%>
<script>
	alert("Already registered ID. Try again.");
</script>
<%
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Info Modify</title>
<style type="text/css">
body {
	margin: 0;
	padding: 0;
}

table {
	width: 400px;
	height: 400px;
	margin: 0 auto;
	border-collapse: collapse;
}

table input {
	width: 200px;
	height: 30px;
	margin: 5px;
	border: 0px;
	padding: 5px;
}

table button {
	width: 140px;
	height: 50px;
	border: 0px;
}

table td {
	background-color: #FFF0F5;
}

table tr {
	border: 1px white solid;
	text-align: center;
}

#itext {
	width: 150px;
}

#tt {
	text-align: center;
}

#jsubb {
	width: 430px;
	padding: 10px;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<script type="text/javascript">
	function isSame() {
		if (document.getElementById('pw1').value != ''
				&& document.getElementById('pw2').value != '') {
			if (document.getElementById('pw1').value == document
					.getElementById('pw2').value) {
				document.getElementById('same').innerHTML = 'Password is match';
				document.getElementById('same').style.color = 'blue';
			} else {
				document.getElementById('same').innerHTML = 'Password is not match';
				document.getElementById('same').style.color = 'red';
			}
		}
	}

	function join() {
		var id = document.getElementById("id");
		var name = document.getElementById("name");
		var pw1 = document.getElementById("pw1");
		var pw2 = document.getElementById("pw2");
		var email = document.getElementById("email");

		if (id.value == "" || id.value.length < 3) {
			alert("Enter a longer id");
			id.focus();
			id.style.backgroundColor = '#F5B7B1';
			return false;
		}

		if (name.value == "" || name.value.length < 3) {
			alert("Enter a longer name");
			name.focus();
			name.style.backgroundColor = '#F5B7B1';
			return false;
		}

		if (pw1.value == "" || pw1.value.length < 4) {
			alert("Enter the correct password");
			pw1.focus();
			return false;
		}

		if (pw2.value == "" || pw2.value.length < 4) {
			alert("Enter the correct password");
			pw2.focus();
			return false;
		}

		if (pw1.value != pw2.value) {
			alert("Enter the same password");
			pw1.value = "";
			pw2.value = "";
			pw1.style.backgroundColor = '#F5B7B1';
			pw2.style.backgroundColor = '#F5B7B1';
			pw1.focus();
			return false;
		}

		if (email.value == "") {
			alert("Enter the email");
			email.style.backgroundColor = '#F5B7B1';
			email.focus();
			return false;
		}
	}

	$(function() {
		$("#join_join").prop("disabled", true);
		$("#resultText").css("color", "red");
	});

	function checkID() {
		var id = $("#id").val();

		if (id == "" || id.length < 4) {
			alert("Enter a longer id");
			$("#id").focus();
			return false;
		}

		$.ajax({
			type : 'post',
			dataType : 'text',
			data : 'id=' + id,
			url : './idCheck',
			success : function(rData, textStatus, xhr) {

				if (rData == 1) {
					alert(id + " is already registered");
					$("#join_join").prop("disabled", true);
					$("#resultText").css("color", "red");
					$("#resultText").text(id + " is already registered.");

				} else {
					alert(id + " is be able to join");
					$("#join_join").prop("disabled", false);
					$("#resultText").css("color", "blue");
					$("#resultText").text(id + " is be able to join");
				}
			},
			error : function(xhr, status, e) {
				alert("error : " + e);
			}
		});
	}
</script>
<body>
	<h1 id="tt">회원정보 수정</h1>
	<div>
		<form action="./myInfoModify" method="post" onsubmit="return join()">
			<table>
				<tr>
					<td class="itext">ID</td>
					<td><input type="text" name="id" id="id"
						placeholder="Enter the ID" onchange="checkID()">
				</tr>
				<tr>
					<td>Name</td>
					<td><input type="text" name="name" id="name"
						placeholder="Enter the name"></td>
				</tr>
				<tr>
					<td>Password</td>
					<td><input type="password" id="pw1" name="pw1"
						placeholder="Enter the password"><br> <input
						type="password" id="pw2" name="pw2" placeholder="Password check"
						onchange="isSame()"> <br> <span id="same"></span></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><input type="email" name="email" id="email"
						placeholder="Enter the email"></td>
				</tr>
				<tr>
					<td colspan="2">
						<button type="submit">수정하기</button>
						<button type="reset">Reset</button>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>