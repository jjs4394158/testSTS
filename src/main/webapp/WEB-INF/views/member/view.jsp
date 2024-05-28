<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./resources/css/table.css"/>
<title>회원 상세보기</title>
</head>
<body>
	<table>
		<caption>회원정보 상세보기</caption>
		<tr>
			<th>아이디</th>
			<td>${view.MId}</td>
		</tr>
		
		<c:if test="${loginId eq view.MId || loginId eq 'admin'}">
		<tr>
			<th>비밀번호</th>
			<td>${view.MPw}</td>
		</tr>
		</c:if>
		
		
		<tr>
			<th>이름</th>
			<td>${view.MName}</td>
		</tr>
		<tr>
			<th>생년월일</th>
			<td>${view.MBirth}</td>
		</tr>
		<tr>
			<th>성별</th>
			<td>${view.MGender}</td>
		</tr>
		<tr>
			<th>이메일</th>
			<td>${view.MEmail}</td>
		</tr>
		<tr>
			<th>연락처</th>
			<td>${view.MPhone}</td>
		</tr>
		<tr>
			<th>주소</th>
			<td>${view.MAddr}</td>
		</tr>
		<tr>
			<th>프로필사진</th>
			<td><img src="./resources/profile/${view.MProfileName}" width="150px"/></td>
		</tr>
		
		<c:if test="${loginId eq view.MId || loginId eq 'admin'}">
		<tr>
			<th colspan="2">
				<button id="modify">수정</button>
				<button id="delete">삭제</button>
			</th>
		</tr>
		</c:if>
		
	</table>
	
</body>

<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script>
	$("#modify").click(()=>{
		location.href=`mModiForm?mId=` + "${view.MId}";
	});
	
	$("#delete").click(()=>{
		location.href="mDelete?mId=${view.MId}&mProfileName=${view.MProfileName}";
	});

</script>


</html>







