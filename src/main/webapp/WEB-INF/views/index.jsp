<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원제 게시판</title>
</head>
<body>
	<h2>회원제 게시판 프로젝트</h2>

	<!-- 관리자 -->
	<!-- 회원목록, 게시글 목록, 로그아웃 -->
	<!-- 회원(로그인o) -->
	<!-- 내정보, 내가 쓴글, 게시글 작성, 게시글 목록, 로그아웃, 아이디, 프로필 -->
	<!-- 회원(로그인x) -->
	<!-- 회원가입, 로그인, 게시글 목록 -->

	<c:choose>
		<c:when test="${loginId eq 'admin'}">
			<button id="mlist">회원목록</button>
			<button id="logout">로그아웃</button>
		</c:when>
		<c:when test="${loginId == null}">
			<button id="join">회원가입</button>
			<button id="login">로그인</button>
		</c:when>

		<c:otherwise>
			<h3>로그인 아이디 : ${loginId}</h3>
			<button id="myInfo">내 정보</button>
			<button id="myWrite">내가 쓴글</button>
			<button id="write">게시글 작성</button>
			<button id="logout">로그아웃</button>


		</c:otherwise>
	</c:choose>
	<button id="bList">게시글 목록</button>



	<p>로그인 아이디 : ${loginId}</p>
	<img src="./resources/profile/${loginProfile}" width="200px" />



</body>

<script src="https://code.jquery.com/jquery-3.7.1.js"
	integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4="
	crossorigin="anonymous"></script>
<script>
	$('#join').click(()=>{
		location.href = "joinForm";
	});
	
	$('#login').click(()=>{
		location.href = "loginForm";
	});
	
	$('#mlist').click(()=>{
		location.href = "mList";
	});
	
	$('#logout').click(()=>{
		location.href = "mLogout";
	});
	$('#write').click(()=>{
		location.href = "writeForm";
	});
	
	$('#bList').click(()=>{
		location.href = "bList";
	})
	
	
</script>

</html>







