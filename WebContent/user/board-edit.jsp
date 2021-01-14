<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글수정</title>
</head>
<body>
<h2> 글수정 </h2>
<form action="board-edit-process.do" name="board" method="post">
	<input type="hidden" name="edit_idx" value="${board.b_idx }">
	<input type="hidden" name="u_idx" value="${board.u_idx }">
	<p> 제목 : <input type="text" name="edit_title" value=${board.b_title }></p>
	<p> 내용 : <textarea style="resize:vertical;" name="edit_content" cols="40" rows="8">${board.b_content }</textarea></p>
	<p> <input type="submit" value="수정"> <a href="board.do"><input type="button" value="돌아가기"></a></p>
</form>
</body>
</html>