package com.webapp.mapper;

import java.util.List;
import java.util.Map;

import com.webapp.model.Member;

//MyBatis가 Mapper이름과 같으면 수행
public interface MemberMapper {
	
	List<Member> selectAll();
	List<Member> select(Map<String, Object> index);
	
	Member selectById(int id);
	Member selectByEmail(String email);
	int selectTotalCount();
	
	int insert(Member member); //DB에 의존적이지 않게 insert로 구현(databaseId)
	/*
	int insertOracle(Member member); //MyBatis Plugin기능- CTRL+MOUSE CLICK하면 그 함수로 Jump
	int insertMySQL(Member member);
	*/
	int insertGenerator(Member member); //채번테이블 사용
	
	int update(Member member);
	
	int deleteById(int id);
	int deleteByEmail(String email);
}
