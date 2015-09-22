package com.webapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;

import com.webapp.dao.MemberDao;
import com.webapp.exception.IdPasswordNotMatchException;
import com.webapp.model.AuthInfo;
import com.webapp.model.Member;
import com.webapp.util.Password;

public class AuthService {

	//DB 조회 - Error없으면 멤버인증정보 Return
	//DB MemberDao를 써야 - Injection 

	MemberDao dao;
	
	public void setMemberDao(MemberDao dao) {
		this.dao = dao;
	}
	
	public AuthInfo authenticate(String email, String password) {

		AuthInfo info = new AuthInfo();		
		/*
		info.setName("홍길동");
		info.setEmail(email);
		*/
		try {
			Member m = dao.selectByEmail(email);
			//암호화
			if (!m.getPassword().equals(Password.encode(password))) {
				throw new IdPasswordNotMatchException();
			}
			info.setEmail(m.getEmail());
			info.setName(m.getName());
		} catch (EmptyResultDataAccessException e) {
			throw new IdPasswordNotMatchException(e);
		}
		
		return info;
	}
}
