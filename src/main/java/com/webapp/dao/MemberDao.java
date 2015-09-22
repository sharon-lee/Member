package com.webapp.dao;

import java.util.List;

import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;

//JDBC/JDBCTemplate기준 - Mybatis와 연계
public interface MemberDao extends MemberMapper {
	
	//CRUD - Interface Rule 구현체(Class)가 구현하도록
	/*상위에 정의되어 있는 것은 하위에서 정의 불필요
	List<Member> selectAll();
	Member selectById(int id);
	Member selectByEmail(String email);
	*/

	/*상위에 정의되어 있는 것은 하위에서 정의 불필요
	void insert(Member member); //error는 Exception throw 처리할 것
	void update(Member member); //password 변경 포함. member전체 또는 일부 update
	*/
	
	List<Member> select(int firstItem, int lastItem);
	
	//구현체 필요
	Member selectByEmailWithPassword(String email, String password);
	
	void deleteByIdWithEmail(int id, String email); //id와 email로 delete - primary key로 delete

	/* WEB Application은 Multi-Thread 환경이므로 반드시 for update해줘야
	 * IdGenerator DML
	 */
	static final String SELECT_GEN_MEMBER_ID =
						" select name as \"name\", 			" +
					    "  		 nextval as \"nextval\", 	" +
					    "  		 incval as \"incval\"		" +
					    "    from id_generator				" +
					    "   where name = ?   	 			" + //#{name}
					    "     for update					";

	static final String UPDATE_ID_GENERATOR_WITH_NEXTVAL =
						" update id_generator			" +
					    "     set nextval = ?			" + //#{nextval}
					    "   where name = ?				";  //#{name}
	/*
	 * Member DML
	 */
	static final String SELECT_MEMBER = 
					  	" select 	id, 			" +
			  			"			email, 			" +
			  			"			password, 		" +
			  			"			name, regdate 	" +
						"			from member		";
	static final String SELECT_ALL = SELECT_MEMBER;
	
	static final String SELECT_BY_ID = SELECT_MEMBER 	+
						"	where id = ?			";
	static final String SELECT_BY_EMAIL = SELECT_MEMBER +
						"	where email = ?			";
	
	static final String PAGING_ORACLE = 
						" select outer.rn,					" +
						"		 outer.id,					" +
						"		 outer.email,				" +
						"		 outer.password,			" +
						"		 outer.name,				" +
						"		 outer.regdate				" +
						"  from (select  rownum rn, inner.*	" +
						"		  from (select	m.*			" +
						"				  from	member m	" +
						"				 order  by m.id desc" +
						"				  ) inner 			" +
						"		  ) outer					" +
						"  where outer.rn >= ?   			" + //&gt> #{firstItem}
						"    and outer.rn <= ?    			";  //&lt< #{lastItem}
	static final String PAGING_MYSQL = 
						SELECT_MEMBER 					  +
						" order by id desc				" +
						" limit ?, ? 					"; 		//#{offset}, #{count}

	static final String INSERT_BY_GENERATOR_TABLE = 	// Oracle or MySQL using Generator Table(채번테이블-개발자선택Option처리)
						"insert into member	" +
						"(					" +
						"	id, 			" +
						"	email,			" +
						"	password,		" +
						"	name,			" +
						"	regdate			" +
						")					" +
						"values				" +
						"(					" +
						"	?,				" + //#{id}
						"	?,				" + //#{email}
						"	?,				" + //#{password}
						"	?,				" + //#{name}
						"	?				" + //#{regdate}
						")					";
	static final String INSERT_BY_SEQUENCE = INSERT_BY_GENERATOR_TABLE; // Oracle
	static final String INSERT_BY_AUTOINCREMENT = 						// MySQL
						"insert into member	" +
						"(					" +
						"	email,			" +
						"	password,		" +
						"	name,			" +
						"	regdate			" +
						")					" +
						"values				" +
						"(					" +
						"	?,				" + //#{email}
						"	?,				" + //#{password}
						"	?,				" + //#{name}
						"	?				" + //#{regdate}
						")					";
	//Insert시 DB구분(Oracle/MySQL) Key 중복발생시 Error처리. Email, Validation처리. Password암호화 처리
	
	//Delete from member where id/email = ?
	static final String DELETE =
						" delete from member ";
	static final String DELETE_BY_ID = 
						DELETE 				+
						" where id = ?		";//#{id}	
	static final String DELETE_BY_EMAIL = 
						DELETE 				+
						" where email = ?	";//#{email}	

}
