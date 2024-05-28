<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="./resources/css/table.css" />
<title>회원가입</title>

</head>

<body>
	<form action="mJoin" method="post" enctype="multipart/form-data">
		<table>
			<caption>회원가입 페이지</caption>

			<tr>
				<th>항목</th>
				<th>내용</th>
			</tr>

			<tr>
				<th>아이디</th>
				<td><input type="text" name="mId" id="mId" />
					<p id="check1"></p></td>
			</tr>

			<tr>
				<th>비밀번호</th>
				<td><input type="password" name="mPw" id="mPw" />
					<p id="check2"></p></td>
			</tr>

			<tr>
				<th>비밀번호 확인</th>
				<td><input type="password" id="checkPw" />
					<p id="check3"></p></td>
			</tr>
			
			<!-- 비밀번호와 확인의 입력 값이 일치할 경우 "비밀번호 일치"(파란색) -->
			<!-- 일치하지 않을 경우 "비밀번호 불일치"(빨간색) -->

			<tr>
				<th>이름</th>
				<td><input type="text" name="mName" /></td>
			</tr>

			<tr>
				<th>생년월일</th>
				<td><input type="date" name="mBirth" /></td>
			</tr>

			<tr>
				<th>성별</th>
				<td>
					남자 <input type="radio" name="mGender" value="남자" /> 
					여자 <input type="radio" name="mGender" value="여자" />
				</td>
			</tr>

			<tr>
				<th>이메일</th>
				<td><input type="email" name="mEmail" /></td>
			</tr>

			<tr>
				<th>연락처</th>
				<td><input type="text" name="mPhone" /></td>
			</tr>

			<tr>
				<th>주소</th>
				<td><input type="text" name="addr1" id="sample6_postcode" placeholder="우편번호">
					<input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br> 
					<input type="text" name="addr2" id="sample6_address" placeholder="주소"><br> 
					<input type="text" name="addr3" id="sample6_detailAddress" placeholder="상세주소">
				</td>
			</tr>

			<tr>
				<th>프로필사진</th>
				<td><input type="file" name="mProfile" id="mProfile" /> <br/>
				<img id="preview" width="150px" /></td>
			</tr>

			<tr>
				<th colspan="2">
					<input type="submit" value="가입" /> 
					<input type="reset" value="초기화" />
				</th>
			</tr>

		</table>
	</form>
</body>


<script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>

	// 아이디 중복체크
	let mId = $('#mId');
	let check1 = $('#check1');
	let checked1 = false;
	
	// 비밀번호 정규식
	let mPw = $('#mPw');
	let check2 = $('#check2');
	
	// 비밀번호 확인
	let checkPw = $('#checkPw');
	let check3 = $('#check3');
	
	// 사진 미리보기
	let mProfile = $('#mProfile');
	let preview = $('#preview');
	
	/*
		Ajax(Asynchoronous JavaScript And XML) : 비동기식 자바스크립트 XML
		- html만으로 어려운 다양한 작업을 웹페이지에 구현해 이용자가 웹페이지와 자유롭게
		  상호작용할 수 있도록 하는 기술
		- jsp 페이지 이동 없이 데이터베이스 정보를 가져올 수 있다.
		- ajax를 사용하려면 JSON에 대해 알아야 한다.
		- ajax를 사용하기 위해 반드시 jQuery가 필요하다.
		
		JSON(JavaScript Object Notation) : 자바스크립트에서 객체를 만들때 사용하는 표현식
		- {중괄호}를 사용해서 key와 value로 구분
		- key : 이름, value : 값
		
		ajax 예)
		
		$.ajax({
			type : 통신타입(GET과 POST중 선택),
			url : 요청 할 주소(controller에서 RequestMapping으로 받을 값(value)),
			data : 서버에 요청시 보낼 파라미터(매개변수) <- JSON으로 보내야한다.
			{
				"keyName1" : keyValue1,
				"keyName2" : keyValue2
			},
			dataType : 응답받을 데이터의 타입(text, json 등..),
			success : function(result){
				요청 및 응답에 성공했을 경우, result(결과값)을 가져온다.	
			},
			error : function(){
				요청 및 응답에 실패했을 경우
			}
			
		});
	
	*/
	
	// ajax를 사용해서 db에서 id 가입여부 확인
	mId.keyup(()=>{
		$.ajax({
			type : "POST",
			url : "idCheck",
			data : 
			{
				"mId" : mId.val()
						// mId : 아이디 입력창
						// mId.val() : 아이디 입력값
			},
			dataType : "text",
			success : function(result){
				if(result=="OK"){
					check1.html(mId.val()+"는 사용 가능한 아이디");
					check1.css("color", "blue");
					checked1 = true;
				} else {
					check1.html(result + "는 사용중인 아이디");
					check1.css("color", "red");	
				}
				
			},
			error : function(){
				alert('idCheck함수 통신 실패!');
			}
			
		});
	});
	
	
	mPw.keyup(()=>{
		let pwVal = mPw.val();	// 비밀번호 입력값
		
		let num = pwVal.search(/[0-9]/);	// 숫자 입력 여부 : 입력하면 -1이 아니다
		let eng = pwVal.search(/[a-z]/);	// 영문 입력 여부 : 입력하면 -1이 아니다
		let spe = pwVal.search(/[`~!@#$%^&*|\\\'\":;\/?]/);	// 특수문자 입력 여부
		let spc = pwVal.search(/\s/);		// 공백 입력 여부 : 입력하면 -1이 아니다
		
		console.log("num : " + num + ", eng : " + eng + ", spe : " + spe + ", spc : " + spc); 
		
		// (1) 공백x : "공백없이 입력"
		// (2) 비밀번호 8~16자리 : "비밀번호는 8자리에서 16자리로 입력"
		// (3) 숫자, 문자, 특수문자 조합 : "영문, 숫자, 특수문자를 혼합해서 입력"		
		check2.css("color", "red");
		
		// (4) 모든 조건을 만족하면 "사용가능한 비밀번호" / 글자색 : 파란색
		
		if(spc != -1){	// 공백을 입력했다면
			check2.html("공백없이 입력");
		} else if(pwVal.length < 8 || pwVal.length > 16){
			check2.html("비밀번호는 8자리에서 16자리로 입력");
		} else if(num == -1 || eng == -1 || spe == -1){	// 숫자, 문자, 특문 중에 하나라도 입력을 하지 않았다면
			check2.html("영문, 숫자, 특수문자를 혼합해서 입력");
		} else {
			check2.html("사용가능한 비밀번호");
			check2.css("color", "blue");
		}
		
	});
	
	// 비밀번호 확인
	checkPw.keyup(()=>{
		if(mPw.val() == checkPw.val()){
			check3.html("비밀번호 일치");
			check3.css("color", "blue");
		} else {
			check3.html("비밀번호 불일치");
			check3.css("color", "red");
		}
	});
	
	
	// 사진 미리보기
	mProfile.on("change", (event)=>{
		let file = event.target.files[0];
		
		let reader = new FileReader();
		reader.onload = (e) => {
			preview.attr("src", e.target.result);
		}
		
		reader.readAsDataURL(file);
	});
	
	
	
	
	
	

	// 다음 주소 API
    function sample6_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
                var addr = ''; // 주소 변수
                var extraAddr = ''; // 참고항목 변수

                //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
                if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                    addr = data.roadAddress;
                } else { // 사용자가 지번 주소를 선택했을 경우(J)
                    addr = data.jibunAddress;
                }

                // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                if(data.userSelectedType === 'R'){
                    // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                    // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                    if(data.bname !== '' && /[동|로|가]$/g.test(data.bname)){
                        extraAddr += data.bname;
                    }
                    // 건물명이 있고, 공동주택일 경우 추가한다.
                    if(data.buildingName !== '' && data.apartment === 'Y'){
                        extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                    }
                    
                    // 조합된 참고항목을 해당 필드에 넣는다.
                    
                
                }

                // 우편번호와 주소 정보를 해당 필드에 넣는다.
                document.getElementById('sample6_postcode').value = data.zonecode;
                document.getElementById("sample6_address").value = addr;
                // 커서를 상세주소 필드로 이동한다.
                document.getElementById("sample6_detailAddress").focus();
            }
        }).open();
    }
</script>




</html>



















