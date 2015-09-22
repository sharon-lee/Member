package com.webapp.service;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.dao.MemberDao;
import com.webapp.exception.AlreadyExistingMemberException;
import com.webapp.model.Member;

public class MemberRegisterService {
	
	MemberDao memberDao;
	
	//Bean에 등록, Injection해야
	public void setMemberDao(MemberDao dao) {
		this.memberDao = dao;
	}
	
	@Transactional
	public void register(Member member) { //User로부터 입력받아야. Member 등록(insert into DB)
		try {
			memberDao.insert(member); //biz관련 Exception만 처리-중복 Error 처리. DB자체 문제는 Throw. 
			//insert시에는 returntype check하는게 무의미 - update, delete시에만 return type check
			//memberDao.insertGenerator(member); //java.lang.IllegalArgumentException:insertGenerator()대신 insert()를 사용하세요
		} catch (DuplicateKeyException e) { //DB Exception Error 변환 처리
			throw new AlreadyExistingMemberException(e);
			
		} /*catch (IllegalArgumentException e) {
			
		}*/
	}	
}
