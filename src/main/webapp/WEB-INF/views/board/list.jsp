<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
table, td, th {
	border: 1px solid black;
	border-collapse: collapse;
}

td, th {
	padding: 10px;
}
</style>
<title>페이징 목록</title>
</head>
<body>
	<h3>현재페이지 : ${paging.page}</h3>

	<table>
		<caption>페이징 목록</caption>
		<tr>
			<td colspan="4" align="right">

				<form action="bSearch" method="GET">
					<!-- 검색 카테고리 -->
					<select name="category">
						<option value="BTITLE">제목</option>
						<option value="BCONTENTS">내용</option>
						<option value="BWRITER">작성자</option>
					</select>

					<!-- 검색어 -->
					<input type="text" name="keyword" />

					<!-- 검색버튼 -->
					<input type="submit" value="검색" />

				</form> 
				
				<select id="limit">
					<option value="5">게시글수</option>
					<option value="5">5개씩</option>
					<option value="10">10개씩</option>
					<option value="20">20개씩</option>
				</select>
			</td>
		</tr>

		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>작성일</th>
		</tr>

		<c:forEach var="list" items="${boardList}">
			<tr>
				<td>${list.BNum}</td>
				<td><a href="bView?bNum=${list.BNum}">${list.BTitle}</a></td>
				<td>${list.BWriter}</td>
				<td>${list.BDate}</td>
			</tr>
		</c:forEach>

		<tr>
			<!-- 페이징 처리 -->
			<th colspan="4">
				<!-- 이전 --> <c:if test="${paging.page <= 1}">[이전]</c:if> <c:if
					test="${paging.page > 1}">
					<a href="pList?page=${paging.page-1}&limit=${paging.limit}">[이전]</a>
				</c:if> <!-- 페이지 번호 --> <c:forEach var="i" begin="${paging.startPage}"
					end="${paging.endPage}">
					<c:if test="${paging.page == i}">${i}</c:if>
					<c:if test="${paging.page != i}">
						<a href="pList?page=${i}&limit=${paging.limit}">${i}</a>
					</c:if>
				</c:forEach> <!-- 다음 --> <c:if test="${paging.page >= paging.maxPage}">[다음]</c:if>
				<c:if test="${paging.page < paging.maxPage}">
					<a href="pList?page=${paging.page+1}&limit=${paging.limit}">[다음]</a>
				</c:if>
			</th>
		</tr>
	</table>
</body>

<script src="https://code.jquery.com/jquery-3.7.1.js"
	integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
	crossorigin="anonymous"></script>
<script>
	$("#limit").change(()=>{
		let limit = $('#limit').val();
		location.href=`pList?limit=` + limit;
	});




</script>


</html>













