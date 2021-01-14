<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 상세</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<style>
	table {
		border-collapse:collapse;
		margin:40px auto;
	}
	table tr th {
		font-weight:700;
	}
	table tr td, table tr th {
		border:1px solid #818181;
		width:200px;
		text-align:center;
	}
</style>
<body id="body">
<p>제목 : ${board.b_title }</p>
<p>내용 : ${board.b_content }</p>
<p>날짜 : ${board.b_date }</p>
<p>작성자 : ${board.b_writer }</p>
<a href="board-edit.do?b_idx=${board.b_idx }"><input type="button" value="수정하기"></a>
<a href="board-delete.do?b_idx=${board.b_idx }"><input type="button" value="삭제하기"></a>
<a href="board-add.do?b_idx=${board.b_idx }"><input type="button" value="답글쓰기"></a>
<a href="board.do"><input type="button" value="돌아가기"></a>
<br><br>
<input type="hidden" name="b_idx" value="${board.b_idx }">
<input type="text" name="content" id="cont"> <input type="button" value="댓글달기" id="btn">

<div id="replyList">
	<table>
		<tr>
			<th>ID</th>
			<th>내용</th>
			<th>날짜</th>
			<th>수정</th>
			<th>삭제</th>
		</tr>
		<c:forEach items="${list}" var="item">
			<tr>
				<td>${item.r_writer}</td>
				<td>${item.r_content}</td>
				<td>${item.r_date}</td>
				<td><input type="button" class="btn-edit" data-e_r_idx="${item.r_idx }"></td>
				<td><input type="button" class="btn-del" data-d_r_idx="${item.r_idx }"></td>
			</tr>
		</c:forEach>
	</table>
</div>

<script>
$(document).on('click', '#btn', function () {
	let b_idx = $('input[name="b_idx"]').val();
	let content = $('input[name="content"]').val();	
	console.log(b_idx);
	console.log(content);
	
	$.ajax({
		  method: "POST",
		  url: "/webTest/board-reply.do",
		  data: { b_idx: b_idx, content: content },
		  dateType: "html"
		})
		.done(function(data) {
	 		console.log("ok");
	 		$("#replyList").html(data);	
	 		$("#cont").val('');
		});
});

$(document).on('click', '.btn-edit', function() {
	let r_idx = $(this).attr('data-e_r_idx');
	let checkBtn = $(this);	
	let tr = checkBtn.parent().parent();
	let td = tr.children();
	console.log(r_idx);
	console.log(td.eq(1).text());
	
	$.ajax({
		method: "POST",
		url: "/webTest/board-reply-edit.do",
		data: {r_idx: r_idx},
		dateType: "html"
	})
	.done(function(data) {
		console.log(data);
		if(data == 'false') {
			console.log("false");
			$("#body").html('<h1>권한이 없습니다.</h1>');
			setTimeout(function() {
				window.location.href = "user-main.do";
			}, 2000);
		} else {
			td.eq(1).html(data);			
		}
	});
});

$(document).on('click', '#btn-edit-comp', function() {
	let content = $('input[name="edit-content"]').val();
	let r_idx = $('input[name="r_idx"]').val();
	let b_idx = $('input[name="b_idx"]').val();
	console.log(content);
	
	$.ajax({
		method: "POST",
		url: "/webTest/board-reply-edit-process.do",
		data: {r_idx:r_idx, content:content, b_idx:b_idx},
		dateType: "html"
	})
	.done(function(data) {
		console.log("ok");
		$("#replyList").html(data);	
	});
});

$(document).on('click', '.btn-del', function () {
	let r_idx = $(this).attr('data-d_r_idx');	
	console.log(r_idx);
	
	$.ajax({
		  method: "POST",
		  url: "/webTest/board-reply-delete.do",
		  data: { r_idx: r_idx },
		  dateType: "html"
		})
		.done(function(data) {
			console.log(data);
			if(data == 'false') {
				console.log("false");
				$("#body").html('<h1>권한이 없습니다.</h1>');
				setTimeout(function() {
					window.location.href = "user-main.do";
				}, 2000);
			} else {
		 		$("#replyList").html(data);			
			}
		});
});
</script>
</body>
</html>