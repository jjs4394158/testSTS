package com.icia.mbp.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.icia.mbp.dao.MemberDAO;
import com.icia.mbp.dto.MemberDTO;
import com.icia.mbp.dto.PageDTO;
import com.icia.mbp.dto.SearchDTO;

@Service
public class MemberService {

	@Autowired
	private MemberDAO mdao;

	@Autowired
	private BCryptPasswordEncoder pwEnc;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpSession session;

	private ModelAndView mav;

	public String savePath() {
		return request.getServletContext().getRealPath("src/main/webapp/resources/profile/")
				.replace(".metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\", "");
	}

	public String idCheck(String mId) {
		System.out.println("[2]controller → service : " + mId);
		
		String result = null;

		String checkId = mdao.idCheck(mId);

		if (checkId == null) {
			result = "OK";
		} else {
			result = checkId;
		}
		System.out.println("[4]dao → service : " + result);
		return result;
	}

	public ModelAndView mJoin(MemberDTO member) {
		mav = new ModelAndView();

		// (1) 비밀번호 암호화
		// member.getMPw() : 우리가 입력한 비밀번호
		// pwEnc.encode(member.getMPw()) : 입력한 비밀번호 암호화
		// member.setMPw(pwEnc.encode(member.getMPw()))
		// : 암호화 된 비밀번호를 다시 member객체에 저장
		member.setMPw(pwEnc.encode(member.getMPw()));

		// db에서 똑같은 비밀번호가 같은지 다른지 확인..

		// (2) 주소 처리
		// (22223)인천 미추홀구 매소홀로488번길 6-32, 태승빌딩 4층
		String mAddr = "(" + member.getAddr1() + ")" + member.getAddr2() + ", " + member.getAddr3();
		member.setMAddr(mAddr);

		// (3) 파일 업로드 : profile 폴더

		MultipartFile mProfile = member.getMProfile();

		String savePath = savePath();

		if (!mProfile.isEmpty()) {
			String uuid = UUID.randomUUID().toString().substring(0, 8);
			String fileName = mProfile.getOriginalFilename();
			String mProfileName = uuid + "_" + fileName;
			member.setMProfileName(mProfileName);

			try {
				mProfile.transferTo(new File(savePath + mProfileName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		} else {
			member.setMProfileName("default.jpg");
		}

		////////////////////////////////////////////////////////////////////////////////

		try { // 회원가입 성공
			mdao.mJoin(member);
			System.out.println("[4]dao → service : 회원가입 성공");
			mav.setViewName("member/login");
		} catch (Exception e) { // 회원가입 실패
			System.out.println("[4]dao → service : 회원가입 실패");
			mav.setViewName("member/join");
		}

		return mav;
	}

	public ModelAndView mLogin(MemberDTO member) {
		mav = new ModelAndView();

		// db에서 입력한 mId에 대한 정보(mView)를 불러온다.
		MemberDTO login = mdao.mView(member.getMId());

		// login 객체의 비밀번호(암호화)와 login.getMPw()
		// 우리가 입력한 비밀번호와 match 시킨다. member.getMPw()
		if (pwEnc.matches(member.getMPw(), login.getMPw())) {
			// 로그인 성공
			mav.setViewName("index");

			session.setAttribute("loginId", login.getMId());
			session.setAttribute("loginProfile", login.getMProfileName());

		} else {
			// 로그인 실패
			mav.setViewName("member/login");
		}

		return mav;
	}

	public ModelAndView mList(int page, int limit) {
		System.out.println("[2]controller → service : " + page + limit);
		mav = new ModelAndView();

		// (1) 한페이지 보여질 페이지 갯수
		int block = 5;

		// (2) 데이터 갯수(회원 명수)
		int count = mdao.mCount();

		// (3) 최대페이지 : 14
		int maxPage = (int) Math.ceil((double) count / limit);

		// (4) 시작행 // 1 2 3 4 5 ...
		int startRow = (page - 1) * limit + 1; // 1 6 11 16 21 ..

		// (5) 끝나는행
		int endRow = page * limit; // 5 10 15 20 25

		// (6) 시작페이지
		int startPage = ((int) Math.ceil((double) page / block) - 1) * block + 1;

		// (7) 끝나는 페이지
		int endPage = startPage + block - 1;

		if (endPage >= maxPage) {
			endPage = maxPage;
		}

		// (8) 페이지 객체 생성
		PageDTO paging = new PageDTO();

		// (9) DB로 보낼 정보
		paging.setStartRow(startRow);
		paging.setEndRow(endRow);

		// (10) JSP로 보낼 정보
		paging.setPage(page);
		paging.setMaxPage(maxPage);
		paging.setStartPage(startPage);
		paging.setEndPage(endPage);
		paging.setLimit(limit);
		
		List<MemberDTO> memberList = mdao.mList(paging);
		System.out.println("[4]dao → service : " + memberList);		
		mav.addObject("memberList", memberList);
		mav.addObject("paging", paging);
		
		mav.setViewName("member/list");
		

		return mav;
	}

	public ModelAndView mSearch(SearchDTO search) {
		System.out.println("[2]controller → service : " + search);
		mav = new ModelAndView();
		
		List<MemberDTO> searchList = mdao.mSearch(search);
		System.out.println("[4]dao → service : " + search);		
		mav.addObject("memberList", searchList);
		
		mav.setViewName("member/list");
		
		return mav;
	}

	public ModelAndView mView(String mId) {
		System.out.println("[2]controller → service : " + mId);
		mav = new ModelAndView();
		
		MemberDTO member = mdao.mView(mId);
		System.out.println("[4]dao → service : " + member);		
		mav.addObject("view", member);
		mav.setViewName("member/view");
		
		return mav;
	}

	public ModelAndView mModiForm(String mId) {
		System.out.println("[2]controller → service : " + mId);
		mav = new ModelAndView();
		
		MemberDTO member = mdao.mView(mId);
		System.out.println("[4]dao → service : " + member);		
		mav.addObject("modify", member);
		mav.setViewName("member/modify");
		
		return mav;
	}

	public ModelAndView mModify(MemberDTO member) {
		System.out.println("[2]controller → service : " + member);
		mav = new ModelAndView();

		member.setMPw(pwEnc.encode(member.getMPw()));

		String mAddr = "(" + member.getAddr1() + ")" + member.getAddr2() + ", " + member.getAddr3();
		member.setMAddr(mAddr);

		MultipartFile mProfile = member.getMProfile();
		
		String savePath = savePath();

		if (!mProfile.isEmpty()) {
			String uuid = UUID.randomUUID().toString().substring(0, 8);
			String fileName = mProfile.getOriginalFilename();
			String mProfileName = uuid + "_" + fileName;
			System.out.println("[4]dao → service : " + member);		

			try {
				mProfile.transferTo(new File(savePath + mProfileName));
				
				// 삭제할 파일
				File delFile = new File(savePath + member.getMProfileName());
				
				if(delFile.exists()) {
					if(delFile.delete()) {
						System.out.println(member.getMProfileName()+" 파일 삭제 성공!");
					} else {
						System.out.println(member.getMProfileName()+" 파일 삭제 실패!");
					}
				}
				member.setMProfileName(mProfileName);
				
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		} else {
			member.setMProfileName("default.jpg");
		}

		////////////////////////////////////////////////////////////////////////////////

		try { 
			mdao.mModify(member);
			mav.setViewName("redirect:/mView?mId=" + member.getMId());
		} catch (Exception e) { 
			mav.setViewName("redirect:/mModiForm?mId=" + member.getMId());
		}

		return mav;
	}

	public ModelAndView mDelete(String mId, String mProfileName) {
		mav = new ModelAndView();
		
		try {
			mdao.mDelete(mId);
			mav.setViewName("redirect:/mList");
			
			// 삭제할 파일
			File delFile = new File(savePath() + mProfileName);
			
			if(delFile.exists()) {
				if(delFile.delete()) {
					System.out.println(mProfileName + " 파일 삭제 성공!");
				} else {
					System.out.println(mProfileName + " 파일 삭제 실패!");
				}
			}
			
			
			
		} catch(Exception e) {
			mav.setViewName("redirect:/mView?mId="+mId);
		}
		
		return mav;
	}
	
	
	
	
	
	
	

}











