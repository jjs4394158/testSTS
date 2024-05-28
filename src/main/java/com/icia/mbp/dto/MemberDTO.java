package com.icia.mbp.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MemberDTO {

	private String mId;		
	private String mPw;
	private String mName;
	private String mBirth;
	private String mGender;
	private String mEmail;
	private String mPhone;
	private String mAddr;	// (우편번호)일반주소, 상세주소
	private String mProfileName;
	
	private String addr1; // 우편번호
	private String addr2; // 일반주소
	private String addr3; // 상세주소	
	private MultipartFile mProfile;	// 업로드 파일
	
}
