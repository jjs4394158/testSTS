<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./resources/css/table.css" />
<title>게시글 작성</title>
</head>
<body>
	<form action="bWrite" method="post" enctype="multipart/form-data">
		<table>
			<caption>게시글 작성</caption>
			<tr>
				<th>제목</th>
				<td><input type="text" name="bTitle" /></td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea rows="20" cols="40" name="bContents"></textarea></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td><input type="file" name="bFile" /></td>
			</tr>
			<tr>
				<th colspan="2">
					<input type="hidden" name="bWriter" value="${loginId}" />
					<input type="submit" value="등록" />
				</th>
			</tr>
		</table>
	</form>
</body>

<c:if test="${loginId eq null}">
<script>
	alert('로그인 후 사용해주세요.');
	location.href="loginForm";
</script>
</c:if>





</html>