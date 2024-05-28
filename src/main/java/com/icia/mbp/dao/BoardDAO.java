package com.icia.mbp.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.icia.mbp.dto.BoardDTO;
import com.icia.mbp.dto.PageDTO;
import com.icia.mbp.dto.SearchDTO;

@Repository
public class BoardDAO {

	@Autowired
	private SqlSessionTemplate sql;

	public int bWrite(BoardDTO board) {
		System.out.println("[3] service → dao : " + board);
		return sql.insert("Board.bWrite", board);
	}

	public BoardDTO bView(int bNum) {
		System.out.println("[3] service → dao : " + bNum);
		return sql.selectOne("Board.bView", bNum);
	}

	public int bCount(int bNum) {
		System.out.println("[3] service → dao : " + bNum);
		return sql.update("Board.bCount", bNum);
	}

	public int bModify(BoardDTO board) {
		System.out.println("[3] service → dao : " + board);
		return sql.update("Board.bModify", board);
	}

	public int bDelete(int bNum) {
		System.out.println("[3] service → dao : " + bNum);
		return sql.delete("Board.bDelete", bNum);
	}

	public int bCtn() {
		System.out.println("[3] service → dao : ");
		return sql.selectOne("Board.bCtn");
	}

	public List<BoardDTO> bList(PageDTO paging) {
		System.out.println("[3] service → dao : " + paging);
		return sql.selectList("Board.bList", paging);
	}

	public List<BoardDTO> bSearch(SearchDTO search) {
		System.out.println("[3] service → dao : " + search);
		return sql.selectList("Board.bSearch", search);
	}

}
