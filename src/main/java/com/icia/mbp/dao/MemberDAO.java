package com.icia.mbp.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.icia.mbp.dto.MemberDTO;
import com.icia.mbp.dto.PageDTO;
import com.icia.mbp.dto.SearchDTO;

@Repository
public class MemberDAO {
	
	@Autowired
	private SqlSessionTemplate sql;

	public String idCheck(String mId) {
		System.out.println("[3] service → dao : " + mId);
		return sql.selectOne("Member.idCheck", mId);
	}

	public void mJoin(MemberDTO member) {
		System.out.println("[3] service → dao : " + member);
		sql.insert("Member.mJoin", member);
	}

	public MemberDTO mView(String mId) {
		System.out.println("[3] service → dao : " + mId);
		return sql.selectOne("Member.mView", mId);
	}

	public int mCount() {
		System.out.println("[3] service → dao : ");
		return sql.selectOne("Member.mCount");
	}

	public List<MemberDTO> mList(PageDTO paging) {
		System.out.println("[3] service → dao : " + paging);
		return sql.selectList("Member.mList", paging);
	}

	public List<MemberDTO> mSearch(SearchDTO search) {
		System.out.println("[3] service → dao : " + search);
		return sql.selectList("Member.mSearch", search);
	}

	public void mModify(MemberDTO member) {
		System.out.println("[3] service → dao : " + member);
		sql.update("Member.mModify", member);
	}

	public void mDelete(String mId) {
		System.out.println("[3] service → dao : " + mId);
		sql.delete("Member.mDelete", mId);
	}
	
	
	
	
	

}
