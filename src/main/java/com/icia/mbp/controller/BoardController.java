package com.icia.mbp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.icia.mbp.dto.BoardDTO;
import com.icia.mbp.dto.SearchDTO;
import com.icia.mbp.service.BoardService;

@Controller
public class BoardController {

	private ModelAndView mav = new ModelAndView();

	@Autowired
	private BoardService bsvc;

	// writeForm : 게시글작성 페이지로 이동
	@RequestMapping(value = "/writeForm", method = RequestMethod.GET)
	public String writeForm() {
		return "board/write";
	}

	// bWrite : 게시글작성 메소드
	@RequestMapping(value = "/bWrite", method = RequestMethod.POST)
	public ModelAndView bWrite(@ModelAttribute BoardDTO board) {
		System.out.println("\n게시글 작성 메소드");
		System.out.println("[1]jsp → controller : " + board);
		mav = bsvc.bWrite(board);
		System.out.println("[5]service → controller : " + mav);
		return mav;
	}

	// bView?bNum=게시글번호 : 게시글 상세보기
	@RequestMapping(value = "/bView", method = RequestMethod.GET)
	public ModelAndView bView(@RequestParam("bNum") int bNum) {
		System.out.println("\n게시글 상세보기 메소드");
		System.out.println("[1]jsp → controller : " + bNum);
		mav = bsvc.bView(bNum);
		System.out.println("[5]service → controller : " + mav);
		return mav;
	}

	// modifyForm?bNum=게시글 번호
	@RequestMapping(value = "/modifyForm", method = RequestMethod.GET)
	public ModelAndView modifyForm(@RequestParam("bNum") int bNum) {
		System.out.println("\n게시글 게시글 번호 메소드");
		System.out.println("[1]jsp → controller : " + bNum);
		mav = bsvc.modifyForm(bNum);
		System.out.println("[5]service → controller : " + mav);
		return mav;
	}

	// bModify : 게시글수정
	@RequestMapping(value = "/bModify", method = RequestMethod.POST)
	public ModelAndView bModify(@ModelAttribute BoardDTO board) {
		System.out.println("\n게시글 게시글 수정 메소드");
		System.out.println("[1]jsp → controller : " + board);
		mav = bsvc.bModify(board);
		System.out.println("[5]service → controller : " + mav);
		return mav;
	}

	// bDelete : 게시글삭제
	@RequestMapping(value = "/bDelete", method = RequestMethod.GET)
	public ModelAndView bDelete(@ModelAttribute BoardDTO board) {
		System.out.println("\n게시글 삭제 메소드");
		System.out.println("[1]jsp → controller : " + board);
		mav = bsvc.bDelete(board);
		System.out.println("[5]service → controller : " + mav);
		return mav;
	}

	// pList : 페이징 처리
	// pList?page=2
	@RequestMapping(value = "/bList", method = RequestMethod.GET)
	public ModelAndView bList(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "limit", required = false, defaultValue = "5") int limit) {
		System.out.println("\n페이징 처리 메소드");
		System.out.println("[1]jsp → controller : " + page + limit);
		mav = bsvc.bList(page, limit);
		System.out.println("[5]service → controller : " + mav);
		return mav;
	}

	// bSearch : 게시글 검색
	@RequestMapping(value = "/bSearch", method = RequestMethod.GET)
	public ModelAndView bSearch(@ModelAttribute SearchDTO search) {
		System.out.println("\n게시글 검색 메소드");
		System.out.println("[1]jsp → controller : " + search);
		mav = bsvc.bSearch(search);
		System.out.println("[5]service → controller : " + mav);
		return mav;
	}

}