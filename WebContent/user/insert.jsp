<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 추가</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<body>
<h2> 회원가입 </h2>
<form action="user-insert-process.do" name="user" method="post">
	<p> 아이디 : <input type="text" name="id"> <input type="button" value="아이디 중복 검사" id="btn-id"></p>
	<p> 비밀번호 : <input type="password" name="password"></p>
	<p> 이름 : <input type="text" name="name"></p>
	<p> 연락처 : <input type="text" maxlength="4" size="4" name="tel1"> -
				<input type="text" maxlength="4" size="4" name="tel2"> -
				<input type="text" maxlength="4" size="4" name="tel3"></p>
	<p> 나이 : <input type="text" name="age"></p>
	<p> <input type="submit" value="가입하기" id="btn-sub" disabled="disabled"> <a href="user-list.do"><input type="button" value="돌아가기"></a></p>
</form>

<script>
$(document).on('click', '#btn-id', function () {
	let u_id = $('input[name="id"]').val();
	console.log(u_id);
	
	$.ajax({
	  method: "POST",
	  url: "/webTest/user-dup.do",
	  data: { u_id: u_id },
	  dataType: "text"
	})
	.done(function( data ) {
 		console.log(data);
		if(data == 'true') {
			$(function() {
				alert("중복된 아이디입니다.");
				$("#btn-sub").prop("disabled", true);
			});
		}	
		else {
			$(function() {
				alert("중복된 아이디가 아닙니다.");
			    $("#btn-sub").prop("disabled", false);
			});
		}
	});
});
</script>
</body>
</html>