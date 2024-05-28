package com.icia.mbp.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.icia.mbp.dto.CommentDTO;

@Repository
public class CommentDAO {
	
	@Autowired
	private SqlSessionTemplate sql;

	public List<CommentDTO> cList(int cbNum) {
		System.out.println("[3] cbNum : " + cbNum);
		return sql.selectList("Comment.cList", cbNum);
	}

	public void cWrite(CommentDTO comment) {
		sql.insert("Comment.cWrite", comment);
	}

	public void cModify(CommentDTO comment) {
		sql.update("Comment.cModify", comment);
		
	}

	public void cDelete(CommentDTO comment) {
		sql.delete("Comment.cDelete", comment);
		
	}


}







