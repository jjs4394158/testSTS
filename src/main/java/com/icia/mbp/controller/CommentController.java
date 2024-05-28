package com.icia.mbp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.icia.mbp.dto.BoardDTO;
import com.icia.mbp.dto.CommentDTO;
import com.icia.mbp.service.CommentService;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService csvc;
	
	// cList : 댓글 목록
	@RequestMapping(value="cList", method=RequestMethod.POST)
	public @ResponseBody List<CommentDTO> cList(@RequestParam("cbNum") int cbNum){
		System.out.println("[1] cbnum : " + cbNum);
		List<CommentDTO> list = csvc.cList(cbNum);
		System.out.println("[5] cbnum : " + list);
		return list;
	}
	// cWrite : 댓글입력
	@RequestMapping(value="cWrite", method=RequestMethod.POST)
	public @ResponseBody List<CommentDTO> cWrite(@ModelAttribute CommentDTO comment){
		System.out.println("[1] cbNum : " + comment);
		List<CommentDTO> list = csvc.cWrite(comment);
		System.out.println("[5] list : " + list);
		return list;
	}
	
	// cModify : 댓글 수정
	@RequestMapping(value="cModify", method=RequestMethod.POST)
	public @ResponseBody List<CommentDTO> cModify(@ModelAttribute CommentDTO comment){
		System.out.println("[1] cbNum : " + comment);
		List<CommentDTO> list = csvc.cModify(comment);
		System.out.println("[5] list : " + list);
		return list;
	}
	
	// cDelete : 댓글 삭제
	@RequestMapping(value = "/cDelete", method = RequestMethod.POST)
	public @ResponseBody List<CommentDTO> cDelete(@ModelAttribute CommentDTO comment){
		System.out.println("\n게시글 삭제 메소드");
		System.out.println("[1] cbNum : " + comment);
		List<CommentDTO> list = csvc.cDelete(comment);
		System.out.println("[5] list : " + list);
		return list;
	}
	
	
	
	
}




