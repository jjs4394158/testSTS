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
<title>게시글 상세보기</title>
</head>
<body>
	<table>
		<caption>게시글 상세보기</caption>
		<tr>
			<th>번호</th>
			<td>${view.BNum}</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${view.BWriter}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${view.BTitle}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>${view.BContents}</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${view.BDate}</td>
		</tr>
		<tr>
			<th>조회수</th>
			<td>${view.BHit}</td>
		</tr>
		<tr>
			<th>첨부파일</th>
			<td><img src="./resources/profile/${view.BFileName}"
				width="200px" /></td>
		</tr>
		<tr>
			<th colspan="2">
				<button id="modify">수정</button>
				<button id="delete">삭제</button>
			</th>
		</tr>

	</table>

	<!-- 댓글 작성 공간 -->
	<c:if test="${loginId ne null}">
		<div id="cmtWrite">
			<textarea rows="3" cols="30" id="cContents"></textarea>
			<button id="writeBtn">댓글 입력</button>
		</div>
	</c:if>

	<br />

	<!-- 댓글 목록 공간 -->
	<div id="cmtArea"></div>




</body>

<script src="https://code.jquery.com/jquery-3.7.1.js"
	integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
	crossorigin="anonymous"></script>
<script>
	$("#modify").click(()=>{
		location.href="modifyForm?bNum=${view.BNum}";
	});
	
	$("#delete").click(()=>{
		location.href="bDelete?bNum=${view.BNum}&bFileName=${view.BFileName}";
	});
	
	
	// ajax로 댓글 목록 가져오기
	$.ajax({
		type : "POST",
		url : "cList",
		data : {
			"cbNum" : ${view.BNum}
		},
		dataType : "json",
		success : function(list){
			//console.log(list);
			
			cmtList(list);
		}, 
		error : function(){
			alert('댓글 불러오기 실패!');
		}
	});
	
	function cmtList(list){
		let output = "";
		
		output += "<table>";
		output += "<tr>";
		output += "<th>작성자</th>";
		output += "<th>내용</th>";
		output += "<th>작성일</th>";
		output += "<th>수정</th>";
		output += "<th>삭제</th>";
		output += "</tr>";
		
		for(let i in list){
			output += "<tr>";	
			output += "<td>"+ list[i].cwriter +"</td>";
			output += "<td>"+ list[i].ccontents +"</td>";
			output += "<td>"+ list[i].cdate +"</td>";
			
			if('${loginId}' == list[i].cwriter || '${loginId}' == 'admin'){
				output += "<td><button class='cmodify' value='"+ list[i].cnum +"'>수정</button></td>";
				output += "<td><button class='cdelete' value='"+ list[i].cnum +"'>삭제</button></td>";	
			}else{
				output += "<td></td>";
				output += "<td></td>";
			}
			
			
			output += "</tr>";		
		}
		output += "</table>";
		$('#cmtArea').html(output);
		
		

		$('.cmodify').click((e)=>{
			if(confirm('댓글을 수정하시겠습니까?')){
				$.ajax({
					type : "POST",
					url : "cModify",
					data : {
						"cNum" : e.target.value,
						"cbNum" : ${view.BNum},
						"cContents" : $('#cContents').val()
					},
					dataType : "json",
					success : function(list){
						cmtlist(list);
						$('#cContents').val("");		
					},
					error : function(){
						alert('댓글 수정 실패');
					}
				});
				
			}else{
				alert('취소했습니다.');
			}
			
		});
		$('.cdelete').click((e)=>{
			
			if(confirm('댓글을 삭제하시겠습니까?')){
			$.ajax({
				type : "POST",
				url : "cDelete",
				data : {
					"cNum" : e.target.value,
					"cbNum" : ${view.BNum},
				},
				dataType : "json",
				success : function(list){
					cmtlist(list);
				},
				error : function(){
					alert('댓글 삭제 실패');
				}
			});
			}else{
				arlert('취소했습니다.');
			}
			
			
		});
	}
	
	$('#writeBtn').click(()=>{
		
		$.ajax({
			type : "POST",
			url : "cWrite",
			data : {
				"cbNum" : ${view.BNum},
				"cWriter" : '${loginId}',
				"cContents" : $('#cContents').val()
			},
			dataType : "json",
			success : function(list){
				cmtList(list);
				$('#cContents').val("");	// 댓글창 초기화
			},
			error : function(){
				alert('댓글 작성 실패!');
			}
			
		});
		
		
	});
	
	
	
	
	
	
</script>






</html>