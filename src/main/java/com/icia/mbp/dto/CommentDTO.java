package com.icia.mbp.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class CommentDTO {
	
	private int cNum;			// 댓글 번호(SEQ)
	private int cbNum;			// 게시글 번호(BOARDDTO)
	private String cWriter;		// 댓글 작성자(MEMBERDTO)
	private String cContents;	// 댓글 내용 : 작성
	private Date cDate;			// 댓글 날짜 : SYSDATE
	
}
