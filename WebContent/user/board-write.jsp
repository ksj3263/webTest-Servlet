<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기</title>
</head>
<body>
<h2> 글쓰기 </h2>
<form action="board-write-result.do" name="user" method="post">
	<p> 제목 : <input type="text" name="title"></p>
	<p> 내용 : <textarea style="resize:vertical;" name="content" cols="40" rows="8"></textarea></p>
	<p> <input type="submit" value="작성" id="btn-sub"> <a href="board.do"><input type="button" value="돌아가기"></a></p>
</form>
</body>
</html>