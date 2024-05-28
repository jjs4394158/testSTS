package com.icia.mbp.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.icia.mbp.dao.BoardDAO;
import com.icia.mbp.dto.BoardDTO;
import com.icia.mbp.dto.PageDTO;
import com.icia.mbp.dto.SearchDTO;

@Service
public class BoardService {

	private ModelAndView mav;

	// DAO 연결
	@Autowired
	private BoardDAO bdao;

	// HttpServletRequest 객체 생성
	@Autowired
	private HttpServletRequest request;

	public ModelAndView bWrite(BoardDTO board) {
		System.out.println("[2]controller → service : " + board);
		mav = new ModelAndView();
		MultipartFile bFile = board.getBFile();

		if (!bFile.getOriginalFilename().isEmpty()) {

			UUID uuid = UUID.randomUUID();

			String bFileName = uuid.toString().substring(0, 8) + "_" + bFile.getOriginalFilename();
			board.setBFileName(bFileName);
			String savePath = request.getServletContext().getRealPath("src/main/webapp/resources/profile/")
					.replace(".metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\", "");
			try {
				bFile.transferTo(new File(savePath + bFileName));
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}

		} else {
			board.setBFileName("default.jpg");
		}

		int result = bdao.bWrite(board);
		System.out.println("[4]dao → service : " + result);

		if (result > 0) {
			mav.setViewName("index");
		} else {
			mav.setViewName("board/write");
		}

		return mav;
	}

	

	public ModelAndView bView(int bNum) {
		System.out.println("[2]controller → service : " + bNum);

		mav = new ModelAndView();

		int result = bdao.bCount(bNum);

		if (result > 0) {
			BoardDTO board = bdao.bView(bNum);
			System.out.println("[4]dao → service : " + board);

			mav.addObject("view", board);
			mav.setViewName("board/view");
		} else {
			mav.setViewName("index");
		}

		return mav;
	}

	public ModelAndView modifyForm(int bNum) {
		System.out.println("[2]controller → service : " + bNum);

		mav = new ModelAndView();

		BoardDTO board = bdao.bView(bNum);
		System.out.println("[4]dao → service : " + board);

		mav.addObject("modify", board);
		mav.setViewName("board/modify");

		return mav;
	}

	public ModelAndView bModify(BoardDTO board) {
		System.out.println("[2]controller → service : " + board);
		mav = new ModelAndView();

		// 삭제할 파일 이름
		String delFileName = board.getBFileName();

		MultipartFile bFile = board.getBFile();

		if (!bFile.getOriginalFilename().isEmpty()) {
			// 첨부된 파일이 있는 경우에 대한 처리
			UUID uuid = UUID.randomUUID();
			String bFileName = uuid.toString().substring(0, 8) + "_" + bFile.getOriginalFilename();
			board.setBFileName(bFileName);

			String savePath = request.getServletContext().getRealPath("src/main/webapp/resources/profile/")
					.replace(".metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\", "");

			try {
				bFile.transferTo(new File(savePath + bFileName));

				// 저장경로(savePath)에 있는 파일(delFileName을 delFile에 담는다.
				File delFile = new File(savePath + delFileName);

				// 삭제하고자 하는 파일이 존재한다면
				if (delFile.exists()) {
					// 해당파일을 삭제한다.
					delFile.delete();
				}

			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}

		} else {
			board.setBFileName("default.jpg");
		}

		int result = bdao.bModify(board);
		System.out.println("[4]dao → service : " + result);

		if (result > 0) {
			mav.setViewName("redirect:/bView?bNum=" + board.getBNum());
		} else {
			mav.setViewName("redirect:/bList");
		}

		return mav;
	}

	public ModelAndView bDelete(BoardDTO board) {
		System.out.println("[2]controller → service : " + board);
		mav = new ModelAndView();

		int result = bdao.bDelete(board.getBNum());
		System.out.println("[4]dao → service : " + result);

		if (result > 0) {

			String savePath = request.getServletContext().getRealPath("src/main/webapp/resources/fileUpload/")
					.replace(".metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\", "");

			File delFile = new File(savePath + board.getBFileName());

			if (delFile.exists()) {
				delFile.delete();
			}

			mav.setViewName("redirect:/bList");
		} else {
			mav.setViewName("redirect:/bView?bNum=" + board.getBNum());
		}

		return mav;
	}

	public ModelAndView bList(int page, int limit) {
		System.out.println("[2]controller → service => page : " + page + ", limit : " + limit);
		mav = new ModelAndView();

		// (1) 한 화면에 보여줄 페이지 번호 갯수
		int block = 5;

		// (2) 전체 게시글 갯수 : db에서 조 회 (67개)
		int count = bdao.bCtn();
		System.out.println("게시글 갯수 : " + count);

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

		// 페이지 객체 생성
		PageDTO paging = new PageDTO();

		// DB로 보낼 정보
		paging.setStartRow(startRow);
		paging.setEndRow(endRow);

		// JSP로 보낼 정보
		paging.setPage(page);
		paging.setMaxPage(maxPage);
		paging.setStartPage(startPage);
		paging.setEndPage(endPage);
		paging.setLimit(limit);

		System.out.println("paging 확인 : " + paging);

		// 페이징 목록
		List<BoardDTO> pagingList = bdao.bList(paging);
		System.out.println("[4]dao → service : " + pagingList);

		mav.setViewName("board/list");
		mav.addObject("boardList", pagingList);
		mav.addObject("paging", paging);

		return mav;
	}

	public ModelAndView bSearch(SearchDTO search) {
		System.out.println("[2]controller → service : " + search);
		mav = new ModelAndView();

		List<BoardDTO> searchList = bdao.bSearch(search);

		mav.addObject("boardList", searchList);
		mav.setViewName("board/list");

		return mav;
	}

}
