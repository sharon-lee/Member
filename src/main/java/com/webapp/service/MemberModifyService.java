package com.webapp.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.dao.MemberDao;
import com.webapp.exception.AlreadyExistingMemberException;
import com.webapp.exception.MemberModifyEmptyException;
import com.webapp.model.Member;

public class MemberModifyService {
	
	MemberDao memberDao;
	
	//Bean에 등록, Injection해야
	public void setMemberDao(MemberDao dao) {
		this.memberDao = dao;
	}
	
	@Transactional
	public void modify(Member member) { //User로부터 입력받아야. Member 등록(insert into DB)
		try {
			memberDao.update(member); //biz관련 Exception만 처리-Invalid Error 처리. DB자체 문제는 Throw. 
			
		} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException e) { //DB Exception Error 변환 처리
			throw new MemberModifyEmptyException(e);
			
		}
	}
	//Update는 잘못하면 DB의 일관성이 깨지므로 고려해야 할 사항이 많아

}
