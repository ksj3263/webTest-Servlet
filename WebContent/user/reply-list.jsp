<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table>
	<tr>
		<th>ID</th>
		<th>내용</th>
		<th>날짜</th>
		<th>수정</th>
		<th>삭제</th>
	</tr>
	<c:forEach items="${list}" var="item">
		<tr id="tr">
			<td>${item.r_writer}</td>
			<td>${item.r_content}</td>
			<td>${item.r_date}</td>
			<td><input type="button" class="btn-edit" data-e_r_idx="${item.r_idx }"></td>
			<td><input type="button" class="btn-del" data-d_r_idx="${item.r_idx }"></td>
		<tr>
	</c:forEach>
</table>