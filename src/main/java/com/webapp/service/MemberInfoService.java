package com.webapp.service;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.dao.MemberDao;
import com.webapp.exception.MemberNotFoundException;
import com.webapp.model.Member;

public class MemberInfoService {
	
	MemberDao memberDao; //Service는 Dao하고만 통신. Controller하고도 통신
	
	public void setMemberDao(MemberDao dao) {
		this.memberDao = dao;
	}
	
	//MybatisMemberDao, SpringMemberDao readOnly=true이면 최적화
	@Transactional(readOnly=true)
	public Member getMember(int id) {
		
		Member m = null;
		try {
			m = memberDao.selectById(id);
		} catch (EmptyResultDataAccessException e) {//Dao입장에서 DB Table에 없다는 0 Error Exception=>Biz에 맞게 변환처리해야
			//EmptyResultDataAccessException:이 여기까지 올라오니 Error 처리해야
			throw new MemberNotFoundException(e); //Biz Exception-Error를 Biz에 맞게 Translate(Controller는 이 translated Exception을 처리해야)
			
		}
		return m;
	}
	
	
	@Transactional(readOnly=true)
	public Member getMember(String email) {
		Member m = null;
		try {
			m = memberDao.selectByEmail(email);
		} catch (EmptyResultDataAccessException e) {//Dao입장에서 DB Table에 없다는 0 Error Exception=>Biz에 맞게 변환처리해야
			//EmptyResultDataAccessException:이 여기까지 올라오니 Error 처리해야
			throw new MemberNotFoundException(e); //Biz Exception-Error를 Biz에 맞게 Translate(Controller는 이 translated Exception을 처리해야)
			
		}
		return m;
		/*
		Member m = memberDao.selectByEmail(email);
		return m;
		*/
	}
	
}
