<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="./resources/css/table.css"/>
<title>회원 목록</title>
</head>
<body>
	<h3>현재페이지 : ${paging.page}</h3>

	<table>
		<caption>회원 목록</caption>
		<tr>
			<td colspan="4" align="right">

				<form action="mSearch" method="GET">
					<!-- 검색 카테고리 -->
					<select name="category">
						<option value="MID">아이디</option>
						<option value="MNAME">이름</option>
						<option value="MPHONE">연락처</option>
					</select>

					<!-- 검색어 -->
					<input type="text" name="keyword" />

					<!-- 검색버튼 -->
					<input type="submit" value="검색" />

				</form> 
				
				<select id="limit">
					<option value="5">회원명수</option>
					<option value="5">5명씩</option>
					<option value="10">10명씩</option>
					<option value="20">20명씩</option>
				</select>
			</td>
		</tr>

		<tr>
			<th>아이디</th>
			<th>이름</th>
			<th>성별</th>
			<th>생년월일</th>
		</tr>

		<c:forEach var="list" items="${memberList}">
			<tr>
				<td><a href="mView?mId=${list.MId}">${list.MId}</a></td>
				<td><a href="mView?mId=${list.MId}">${list.MName}</a></td>
				<td>${list.MGender}</td>
				<td>${list.MBirth}</td>
			</tr>
		</c:forEach>

		<tr>
			<!-- 페이징 처리 -->
			<th colspan="4">
				<!-- 이전 --> 
				<c:if test="${paging.page <= 1}">[이전]</c:if> 
				<c:if test="${paging.page > 1}">
					<a href="mList?page=${paging.page-1}&limit=${paging.limit}">[이전]</a>
				</c:if> 
				
				<!-- 페이지 번호 --> 
				<c:forEach var="i" begin="${paging.startPage}" end="${paging.endPage}">
					<c:if test="${paging.page == i}">${i}</c:if>
					<c:if test="${paging.page != i}">
						<a href="mList?page=${i}&limit=${paging.limit}">${i}</a>
					</c:if>
				</c:forEach> 
				
				<!-- 다음 --> 
				<c:if test="${paging.page >= paging.maxPage}">[다음]</c:if>
				<c:if test="${paging.page < paging.maxPage}">
					<a href="mList?page=${paging.page+1}&limit=${paging.limit}">[다음]</a>
				</c:if>
			</th>
		</tr>
	</table>
</body>

<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script>
	$("#limit").change(()=>{
		let limit = $('#limit').val();
		location.href=`mList?limit=` + limit;
	});
</script>


</html>













