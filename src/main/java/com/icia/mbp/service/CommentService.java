package com.icia.mbp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.icia.mbp.dao.CommentDAO;
import com.icia.mbp.dto.CommentDTO;

@Service
public class CommentService {

	@Autowired
	private CommentDAO cdao;

	public List<CommentDTO> cList(int cbNum) {
		System.out.println("[2] cbNum : " + cbNum);
		List<CommentDTO> list = cdao.cList(cbNum);
		System.out.println("[4] list : " + list);
		return list;
	}

	public List<CommentDTO> cWrite(CommentDTO comment) {
		// 댓글 작성
		try {
			cdao.cWrite(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 댓글 작성 후 목록 가져오기
		List<CommentDTO> list = cdao.cList(comment.getCbNum());
		return list;
	}

	public List<CommentDTO> cModify(CommentDTO comment) {
		// 댓글 수정
		try {
			cdao.cModify(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 댓글 작성 후 목록 가져오기
		List<CommentDTO> list = cdao.cList(comment.getCbNum());
		return list;
	}

	public List<CommentDTO> cDelete(CommentDTO comment) {
		// 댓글 삭제
		try {
			cdao.cDelete(comment);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 댓글 삭제 후 목록 가져오기
		List<CommentDTO> list = cdao.cList(comment.getCbNum());
		return list;
	}

}
