package com.webapp.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.dao.MemberDao;
import com.webapp.exception.AlreadyExistingMemberException;
import com.webapp.exception.MemberModifyEmptyException;
import com.webapp.exception.MemberUnRegisterEmptyException;
import com.webapp.model.Member;

public class MemberUnRegisterService {
	
	MemberDao memberDao;
	
	//Bean에 등록, Injection해야
	public void setMemberDao(MemberDao dao) {
		this.memberDao = dao;
	}
	
	//Overloading
	@Transactional
	public void unregister(int id) { //User로부터 입력받아야. Member 삭제 (delete from DB)
		try {
			memberDao.deleteById(id); //biz관련 Exception만 처리- Error 처리. DB자체 문제는 Throw. 

		} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException e) { //DB Exception Error 변환 처리
			throw new MemberUnRegisterEmptyException(e);
			
		}
	}
	
	//Overloading
		@Transactional
		public void unregister(String email) { //User로부터 입력받아야. Member 삭제 (delete from DB)
			try {
				memberDao.deleteByEmail(email); //biz관련 Exception만 처리- Error 처리. DB자체 문제는 Throw. 

			} catch (JdbcUpdateAffectedIncorrectNumberOfRowsException e) { //DB Exception Error 변환 처리
				throw new MemberUnRegisterEmptyException(e);
				
			}
		}
	

}
