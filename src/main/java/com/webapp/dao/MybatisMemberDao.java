package com.webapp.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.util.Assert;

import com.webapp.mapper.IdGeneratorMapper;
import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;
import com.webapp.util.Password;

public class MybatisMemberDao implements MemberDao {

	static Log log = LogFactory.getLog(MybatisMemberDao.class);
	
	//Gateway역할 - MemberMapper를 가져와서(Injection-선언적, Annotation(Auto-wire)) Return. MybatisMemberDao는 SpringBean으로 들어가야
	MemberMapper memberMapper;
	IdGeneratorMapper idGeneratorMapper; 
	
	
	//채번테이블 구분은 Flag로 상태 저장하고 있어야
	boolean useGeneratorTable; //default: false 
		
	//값 설정 - setter
	public void setUseGeneratorTable(boolean use) {
		this.useGeneratorTable = use;
		log.info("useGeneratorTable = " + use);
	}
		
		
	public void setMemberMapper(MemberMapper mapper) {
		memberMapper = mapper;
	}
	
	//Injection받아야 - Setter
	public void setIdGeneratorMapper(IdGeneratorMapper mapper) {
		idGeneratorMapper = mapper;
	}
	
	@Override
	public List<Member> selectAll() {
		// SqlSessionTemplate, Mapper
		// 횡단적(분산)으로 분포된 관심(기능,관점)사 Aspect 시작/끝Log기능을 객체지향으로 구현이 안되기 때문에 
		//AOP(Aspect Oriented Programming관점/Biz기능지향프로그램)가 하나의 Class에만 기술하게 만들어줌 
		// 횡단적 관심(기능,관점)사의 분리 Cross-cutting concern ===> AOP (Proxy 사용)
		/*
		log.info("########################");
		log.info("MybatisMemberDao:::selectAll() start...    ");
		log.info("####### #################");
		
		 List<Member> list =  memberMapper.selectAll();

		log.info("########################");
		log.info("selectAll() end...    ");
		log.info("####### #################");
		
		return list;
		*/
		//Weaving - 실행시에 Run-time시에 원래 있던 메소드 자리에 다시 삽입시키는 기능  
		return memberMapper.selectAll();
	}

	
	@Override
	public List<Member> select(Map<String, Object> index) { 
		// TODO Auto-generated method stub
		/*
		log.info("########################");
		log.info("select(Map<String, Object> index) start...    ");
		log.info("####### #################");
		*/

		List<Member> list =  memberMapper.select(index);
		/*
		log.info("########################");
		log.info("select(Map<String, Object> index) end...    ");
		log.info("####### #################");
		*/
		
		return list;

		//return memberMapper.select(index);
	}

	@Override
	public List<Member> select(int firstItem, int lastItem) { //Proxy:parameter를 map으로 변환처리
		//Map으로 Parameter 변환 처리 
		Map<String, Object> index = new HashMap<String, Object>();
		index.put("firstItem", firstItem);
		index.put("lastItem", lastItem);
		
		int offset = firstItem - 1;
		int count = lastItem - firstItem + 1; 
		index.put("offset", offset);
		index.put("count", count);
		
		return this.select(index);
		
	}


	@Override
	public Member selectById(int id) {

		//return memberMapper.selectById(id);
		Member m = memberMapper.selectById(id);
		if (m == null)
			throw new EmptyResultDataAccessException(id + "", 1);
		return m;
	}

	@Override
	public Member selectByEmail(String email) {
		
		//return memberMapper.selectByEmail(email);
		Member m = memberMapper.selectByEmail(email);
		if (m == null)
			throw new EmptyResultDataAccessException(email, 1);
		return m;
	}

	void xxx() {
		throw new RuntimeException();
	}
	
	@Override
	public int selectTotalCount() {
		// TODO Auto-generated method stub
		/*
		log.info("########################");
		log.info("selectTotalCount() start...    ");
		log.info("####### #################");
		
		int rtn = memberMapper.selectTotalCount();
		
		log.info("########################");
		log.info("selectTotalCount() end...    ");
		log.info("####### #################");
		
		return rtn;
		*/
		//xxx();
		return memberMapper.selectTotalCount();
	}

	
	
	@Override
	public int insert(Member member) {
		// TODO Auto-generated method stub
		int rtn=0;
		member.setPassword(Password.encode(member.getPassword()));
		
		if (useGeneratorTable) {
			//채번테이블에서 먼저 id를 발급받는 Logic이 있어야Injection
			Map<String, Object> idGen = idGeneratorMapper.selectByName("memberId");
			int nextval = ((BigDecimal)idGen.get("nextval")).intValue();
			int incval = ((BigDecimal)idGen.get("incval")).intValue();
			int seq = nextval + incval;
			idGen.put("nextval", seq);
			idGeneratorMapper.update(idGen);
			
			member.setId(seq);
			
			rtn = memberMapper.insertGenerator(member);
		} else {
			rtn = memberMapper.insert(member);
		}
		//insert 실패면 Exception
		return rtn;
	}

	@Override
	public int insertGenerator(Member member) {
		// TODO Auto-generated method stub
		Assert.isTrue(false, "deprecated...");
		//return memberMapper.insertGenerator(member);
		return 0;
	}

	@Override
	public int update(Member member) {
		// TODO Auto-generated method stub
		int rtn = memberMapper.update(member);
		
		if (rtn != 1) { //Error - Throw Exception
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException("Invalid update", 1, rtn);
			
		}
		
		return rtn;
		
	}

	@Override
	public int deleteById(int id) {
		// TODO Auto-generated method stub
		int rtn = memberMapper.deleteById(id);
		
		if (rtn != 1) 
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(id + "", 1, rtn);
		
		return rtn;
	}

	@Override
	public int deleteByEmail(String email) {
		// TODO Auto-generated method stub
		int rtn = memberMapper.deleteByEmail(email);
		
		if (rtn != 1) { //Error - Throw Exception
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(email, 1, rtn);
			
		}
		
		return rtn;
	}

	@Override
	public Member selectByEmailWithPassword(String email, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteByIdWithEmail(int id, String email) {
		// TODO Auto-generated method stub
		
	} //Interface를 Mybatis로 구현해야


}
