package com.webapp.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.webapp.model.Member;

public class LogProxyMemberDao implements MemberDao {

	static Log log;// = LogFactory.getLog(LogProxyMemberDao.class);
	// realdao에 기능을 추가 - Decorate 기능
	MemberDao real;
	
	public LogProxyMemberDao(MemberDao real) {
		// Constructor
		this.real = real;
		log = LogFactory.getLog(real.getClass());
	}
	
	@Override
	public List<Member> selectAll() {
		//Weaving - 실행시에 Run-time시에 원래 있던 메소드 자리에 다시 삽입시키는 기능  
		//삽입시키는 개체 - Weaver		
		log.info("########################");
		log.info("selectAll() start...    ");
		log.info("########################");
		// 1Before Advice
		List<Member> list = real.selectAll(); // Pointcut - Advice를 삽입할 수 있는 위치 - 객체지향 필드,메소드 - JointPoint (조인포인트) 
		//Spring은 필드 조인을 불허
		// Advice 호출시점에 부가적인 기능이 추가되는 메소드들의 모임 - JointPoint (조인포인트) 
		//After Returning
		log.info("########################");
		log.info("selectAll() end...    ");
		log.info("####### #################");
		// 2After Advice - Error에 상관없이 반드시 수행되는 메소드
		// 3After Returning Advice - Error없이 정상적으로 Return될때
		// 4After Throwing - Error발생시 Exception에 추가하는 기능
		
		// 5AroundAdvice - Before + After 합친 전체 모든Advice들을 포함 (5가지 Advice)		
		// Pointcut (Weaving되는 JointPoint들의 모임)		
		// Aspect Class = Pointcut + Advice(Before/After)들
		
		return list;
	}

	@Override
	public List<Member> select(Map<String, Object> index) {
		// TODO Auto-generated method stub
		log.info("##############################################");
		log.info("select(Map<String, Object> index) start...    ");
		log.info("##############################################");
		
		List<Member> list = real.select(index);
		 
		log.info("##############################################");
		log.info("select(Map<String, Object> index) end...    ");
		log.info("##############################################");
		return list;
	}
	
	
	@Override
	public List<Member> select(int firstItem, int lastItem) {
		// TODO Auto-generated method stub
		log.info("##############################################");
		log.info("select(int firstItem, int lastItem) start...    ");
		log.info("##############################################");
		
		List<Member> list = real.select(firstItem, lastItem);
		
		log.info("##############################################");
		log.info("select(int firstItem, int lastItem) end...    ");
		log.info("##############################################");
		
		return list;
	}


	@Override
	public Member selectById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Member selectByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectTotalCount() {
		// TODO Auto-generated method stub
		int rtn=0;
		try {
			//BeforeAdvice
			log.info("########################");
			log.info("selectTotalCount() start...BeforeAdvice    ");
			log.info("####### #################");
			
			rtn =  real.selectTotalCount(); // JointPoint - Pointcut
			
			//AfterReturning
			log.info("########################");
			log.info("selectTotalCount() end...AfterReturning    ");
			log.info("####### #################");
			
		} catch(Throwable t) {
			// AfterThrowing
			log.info("########################");
			log.info("selectTotalCount() end...AfterThrowing    ");
			log.info("####### #################");
		} finally {
			//AfterAdvice
			log.info("########################");
			log.info("selectTotalCount() end...AfterAdvice    ");
			log.info("####### #################");
		}
		// AroundAdvice - Pointcut들의 4가지 Advice를 삽입할수 있는 다 포함하는 Method (Transaction구현 방법)
		// 1Before Advice
		// 2After Advice - Error에 상관없이 반드시 수행되는 메소드
		// 3After Returning Advice - Error없이 정상적으로 Return될때
		// 4After Throwing - Error발생시 Exception에 추가하는 기능
		
		// 5AroundAdvice - Before + After 합친 전체 모든Advice들을 포함 (5가지 Advice)		
		// Pointcut (Weaving되는 JointPoint들의 모임)		
		// Aspect Class = Pointcut + Advice(Before/After)들
	
		return rtn;
	}

	@Override
	public int insert(Member member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insertGenerator(Member member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Member member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteByEmail(String email) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public Member selectByEmailWithPassword(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteByIdWithEmail(int id, String email) {
		// TODO Auto-generated method stub
		
	}

}
