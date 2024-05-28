<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
	table, td, th {
		border : 1px solid black;
		border-collapse : collapse;
	}
	
	td, th {
		padding : 10px;
	}
</style>
<title>게시글 수정</title>
</head>
<body>
	<form action="bModify" method="POST" enctype="multipart/form-data">
		<table>
			<tr>
				<th>항목 : ${modify.BNum}</th>
				<th>내용 : ${modify.BFileName}</th>
			</tr>
			<tr>
				<th>글제목</th>
				<td><input type="text" name="bTitle" placeholder="${modify.BTitle}"/></td>
			</tr>
			<tr>
				<th>글내용</th>
				<td><textarea rows="20" cols="40" name="bContents">${modify.BContents}</textarea></td>
			</tr>
			<tr>
				<th>첨부파일</th>
				<td><input type="file" name="bFile" /></td>
			</tr>
			<tr>
				<th colspan="2">
					<input type="hidden" name="bNum" value="${modify.BNum}" />
					<input type="hidden" name="bFileName" value="${modify.BFileName}" />
					<input type="hidden" name="bWriter" value="${loginId}" />
					<input type="submit" value="수정" />
					<input type="reset" value="다시작성" />
				</th>
			</tr>
		
		</table>
	
	</form>
</body>
</html>