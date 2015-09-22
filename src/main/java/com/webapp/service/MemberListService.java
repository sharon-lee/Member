package com.webapp.service;

import java.util.List;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.dao.MemberDao;
import com.webapp.model.Member;
import com.webapp.model.MemberList;

public class MemberListService {
	//Interface 기반, beans 설정
	MemberDao dao;
	
	//Mybatis, Spring모두 동작하도록
	public void setMemberDao(MemberDao dao) {
		this.dao = dao;
	}
	
	//Transaction Schema가 Proxy를 시작. Service전에 Proxy먼저 시작
	@Transactional
	public List<Member> getListAll() {
		
		return dao.selectAll();
		
	}
	
	//Transaction은 Service에서 시작
	PlatformTransactionManager tm;
	
	//Injection
	/*public void setTransactionManager(PlatformTransactionManager tm) {
		this.tm = tm;
	}
	*/
	
	
	//Pagination
	//@Transactional Annotation - Propagation.REQUIRED:Transaction이 있으면 재사용, 없으면 New, 
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED, 
				   readOnly=true, rollbackFor=Exception.class) //Annotation으로 Transaction 먼저 시작, Proxy 시작
	public MemberList getList(int pageNo) {
		//Transaction은 Service에서 시작
		MemberList paging = new MemberList();
		
		//getTransaction
		/*tm.getTransaction(definition); 
		*/
		int totalItem = dao.selectTotalCount(); //MemberMapper.java/.xml change required
		paging.setTotalItem(totalItem); //totalItem은 DB조회해서 get
		paging.setpageNo(pageNo);
		
		// Pagination 기능 구현
		int firstItem = paging.getFirstItem();
		int lastItem = paging.getLastItem();

		List<Member> members = dao.select(firstItem, lastItem);//parameter가 2개이상이면 MemberMapper에 설정불가(Dao에서 해야)
		
		paging.setMembers(members);
		
		
		
		return paging;
		
	}

}
