package com.webapp.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import static org.apache.ibatis.jdbc.SqlBuilder.*;

import org.springframework.jdbc.JdbcUpdateAffectedIncorrectNumberOfRowsException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.util.Assert;

import com.webapp.model.Member;
import com.webapp.util.Password;

public class SpringMemberDao implements MemberDao {
	
	static Log log = LogFactory.getLog(SpringMemberDao.class);
		
	final static String oracle = "Oracle"; //DB구분
	final static String mysql = "MySQL";
	
	//채번테이블 구분은 Flag로 상태 저장하고 있어야
	boolean useGeneratorTable; //default: false 
	
	//값 설정 - setter
	public void setUseGeneratorTable(boolean use) {
		this.useGeneratorTable = use;
		log.info("useGeneratorTable = " + use);
	}
	
	String DB_VENDOR;
	
	//Data Source, JDBCTemplate 정의
	JdbcTemplate template; // = new JdbcTemplate(dataSource); Spring 생성자를 통한 방법
	
	//설정자로 주입 - template도 singleton
	public void setTemplate(JdbcTemplate template) throws SQLException {
		this.template = template;
		
		//DB 구분해서 Query를 선택해서 구문 수행하도록 
		Connection con=null;
		try {
			//Connection을 가져와야
			con = template.getDataSource().getConnection();
			DB_VENDOR = con.getMetaData().getDatabaseProductName();
			log.info("DB_VENDOR = " + DB_VENDOR);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			log.error("DB_VENDOR error", e);
			//e.printStackTrace();
			throw e;
		} finally {			
				try {
					//Connection close
					if(con != null)
						con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}


	//Anonymous Inner Type
	RowMapper<Member> memberMapper = new RowMapper<Member>() {

		@Override
		public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
			// Mybatis의 memberResultMap처럼 수동으로 Mapping
			Member m = new Member();
			m.setId(rs.getInt("id"));
			m.setEmail(rs.getString("email"));
			m.setPassword(rs.getString("password"));
			m.setName(rs.getString("name"));
			m.setRegdate(rs.getDate("regdate"));
			return m;
		}
	};
	
	//Spring 설정으로 JdbcTemplate, Data source, Bean 설정을 해야 - Class Level로 
	@Override
	public List<Member> selectAll() {
		// 횡단적(분산)으로 분포된 관심(기능,관점)사 Aspect 시작/끝Log기능을 객체지향으로 구현이 안되기 때문에 
		//AOP(Aspect Oriented Programming관점/Biz기능지향프로그램)가 하나의 Class에만 기술하게 만들어줌 
		// 횡단적 관심(기능,관점)사의 분리 Cross-cutting concern ===> AOP (Proxy 사용)
		/*
		log.info("########################");
		log.info("selectAll() start...    ");
		log.info("########################");
		*/
		 
		/*
		List<Member> list = template.query(SELECT_ALL, memberMapper);

		log.info("########################");
		log.info("selectAll() end...    ");
		log.info("####### #################");

		return list;
		*/
		//BeanPropertyRowMapper()는 id_generator를 ==> idGenerator로 Mapping처리해줌
		//Weaving - 실행시에 Run-time시에 원래 있던 메소드 자리에 다시 삽입시키는 기능  
		/*return template.query(SELECT_ALL, 
							  new BeanPropertyRowMapper<Member>(Member.class));*/ //rowMapper도 Interface
		return template.query(SELECT_ALL, memberMapper);
	}

	
	@Override
	public List<Member> select(Map<String, Object> index) {
		// 횡단적(분산)으로 분포된 관심(기능,관점)사 Aspect 시작/끝Log기능을 객체지향으로 구현이 안되기 때문에 
		//AOP(Aspect Oriented Programming관점/Biz기능지향프로그램)가 하나의 Class에만 기술하게 만들어줌 
		// 횡단적 관심(기능,관점)사의 분리 Cross-cutting concern ===> AOP
		/*
		log.info("##############################################");
		log.info("select(Map<String, Object> index) start...    ");
		log.info("##############################################");
		*/		
		// 실질적인 처리 구현
		List<Member> list = new ArrayList<Member>();
		
		//DB에 따라 query 선택 
		switch (DB_VENDOR) {
		case oracle:
			int firstItem = (int) index.get("firstItem");
			int lastItem = (int) index.get("lastItem");
			//list = template.query(PAGING_ORACLE, new BeanPropertyRowMapper<Member>(Member.class), firstItem, lastItem);
			list = template.query(PAGING_ORACLE, memberMapper, firstItem, lastItem);
			//new BeanPropertyRowMapper<Member>(Member.class) == Mybatis의 memberResultMap
			break;
		case mysql:
			int offset = (int) index.get("offset");
			int count = (int) index.get("count");
			//list = template.query(PAGING_MYSQL, new BeanPropertyRowMapper<Member>(Member.class), offset, count);
			list = template.query(PAGING_MYSQL, memberMapper, offset, count);
			break;
		default:
			break;
		}
		/*
		log.info("##############################################");
		log.info("select(Map<String, Object> index) end...    ");
		log.info("##############################################");
		*/
		
		return list;
	}

	@Override
	public List<Member> select(int firstItem, int lastItem) {
		//Map으로 Parameter 변환 처리 
		Map<String, Object> index = new HashMap<String, Object>();
		index.put("firstItem", firstItem);
		index.put("lastItem", lastItem);
		
		int offset = firstItem - 1;
		int count = lastItem - firstItem + 1;
		index.put("offset", offset);
		index.put("count", count);
		
		//Forward to List<Member> select(Map<String, Object> index)
		return this.select(index);
	}


	//MemberDao, MemberMapper와 합쳐짐
	@Override
	public Member selectById(int id) {
		// TODO Auto-generated method stub
		Member m = template.queryForObject(SELECT_BY_ID, memberMapper, id);
		//Member가 없으면 org.springframework.dao.EmptyResultDataAccessException: Incorrect result size: expected 1, actual 0

		return m;

	}

	@Override
	public Member selectByEmail(String email) {
		// TODO Auto-generated method stub
		Member m = template.queryForObject(SELECT_BY_EMAIL, memberMapper, email);
		//Member가 없으면 org.springframework.dao.EmptyResultDataAccessException: Incorrect result size: expected 1, actual 0
		return m;
	}
	
	
	@Override
	public int selectTotalCount() {
		// Spring JdbcTemplate
		/*
		log.info("########################");
		log.info("selectTotalCount() start...    ");
		log.info("####### #################");
		*/
		/*
		int rtn =  template.queryForObject("select count(*) from member", Integer.class);

		log.info("########################");
		log.info("selectTotalCount() end...    ");
		log.info("####### #################");

		return rtn;
		*/
		return template.queryForObject("select count(*) from member", Integer.class);
	}
	

	@Override
	public int insert(Member member) {
		int rtn = 0;
		
		if (useGeneratorTable) { //useGeneratorTable은 beans_biz의 springMemberDao에서 설정		
			log.info("INSERT_BY IdGeneratorTable QUERY");
			
			//id generator table
			Map<String, Object> idGen = template.queryForMap(SELECT_GEN_MEMBER_ID, "memberId");
			int nextval = ((BigDecimal)idGen.get("nextval")).intValue();
			int incval = ((BigDecimal)idGen.get("incval")).intValue();
			int seq = nextval + incval;
			template.update(UPDATE_ID_GENERATOR_WITH_NEXTVAL, seq, "memberId");
			member.setId(seq);

			rtn = template.update(INSERT_BY_GENERATOR_TABLE, 
								member.getId(), 
								member.getEmail(),
								Password.encode(member.getPassword()),
								member.getName(),
								member.getRegdate());
		} else { //DB의 기술 사용. Oracle Sequence, MySQL Auto-increment
			switch (DB_VENDOR) {
			case oracle:
				log.info("INSERT_BY Oracle Sequence QUERY");//Proxy로 해결 불가능
				//Sequence query
				int seq = template.queryForObject("select member_id_seq.nextval from dual", Integer.class); //Type만 필요. Mapper도 불필요

				member.setId(seq);

				rtn = template.update(INSERT_BY_SEQUENCE, 
									member.getId(), 
									member.getEmail(),
									Password.encode(member.getPassword()),
									member.getName(),
									member.getRegdate());
				break;
			case mysql:
				log.info("INSERT_BY MySQL AutoIncrement QUERY");
				
				KeyHolder generatedKeyHolder = new GeneratedKeyHolder();
				
				final Member m = member;//createPreparedStatement에서 사용위해 상수화
				
				rtn = template.update(new PreparedStatementCreator() {
					
					//별도의 무명 객체에서는 호출함수의 Local변수를 직접 참조할수 없다-Final로 선언해야
					@Override
					public PreparedStatement createPreparedStatement(Connection con)
							throws SQLException {
						// TODO Auto-generated method stub
						PreparedStatement pstmt = con.prepareStatement(INSERT_BY_AUTOINCREMENT, Statement.RETURN_GENERATED_KEYS);
						pstmt.setString(1, m.getEmail());
						pstmt.setString(2, Password.encode(m.getPassword()));
						pstmt.setString(3, m.getName());
						pstmt.setTimestamp(4, new Timestamp(m.getRegdate().getTime()));
						//Timestamp는 Data로 변환처리해야
						return pstmt; //template이 generatedKeyHolder을 읽어서 pstmt를 실행하도록 return
					}
				}, generatedKeyHolder);
				member.setId(generatedKeyHolder.getKey().intValue());
				
				break;

			default:
				break;
			}
			
		}
		
		return rtn;
	}

	//insertGenerator-실질적인 로직처리는 insert()에서
	@Override
	public int insertGenerator(Member member) {
		// 호출하면 Exception Throw하도록. Validation을 해서 값이 아니면 Exception을 Throw
		Assert.isTrue(false, "insertGenerator()대신 insert()를 사용하세요");
		return 0;
	}

/*	@Override
	public int update(Member member) {
		// TODO Auto-generated method stub
		BEGIN(); //ThreadLocal을 초기화 
		UPDATE("member");
		if (member.getEmail() != null)
		   SET("email = ?");
		if (member.getPassword() != null)
		   SET("password = ?");
		if (member.getName() != null)
		   SET("name = ?");
		if (member.getRegdate() != null)
		   SET("regdate = ?");
		 WHERE("id = ?");
		//WHERE("email = ?"); //id와 email이 AND 조건일 때 둘다 넣어야. 조건 처리할수도
				
		//조건에 따라 placeholder가 바뀌므로 수동으로 PreparedStatementSetter를 써서 Setting해줘야(Parameter가 유동적으로 변경)
		//PreparedStatementCreator도 사용 가능
		String sql = SQL();
		
		log.info("\n" + sql);
		 
		int rtn = template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// Placeholder 넣을지 판단하고 setting
				
			}
		});
		//rtn이 1이 아니면 Exception 처리
		 * 
		 * return rtn;
		
		return 0;
	}
*/
	
	@Override
	public int update(Member member) {
		// TODO Auto-generated method stub
		BEGIN(); //ThreadLocal을 초기화 
		UPDATE("member");
		if (member.getPassword() != null)
		   SET("password = ?");
		if (member.getName() != null)
		   SET("name = ?");
		WHERE("id = ?");
		WHERE("email = ?"); //id와 email이 AND 조건일 때 둘다 넣어야. 조건 처리할수도
				
		//조건에 따라 placeholder가 바뀌므로 수동으로 PreparedStatementSetter를 써서 Setting해줘야(Parameter가 유동적으로 변경)
		//PreparedStatementCreator도 사용 가능
		String sql = SQL();
		
		log.info("\n" + sql);

		final Member m = member;
		int rtn = template.update(sql, new PreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				// Placeholder 넣을지 판단하고 setting
				int index = 0;
				//이름이 있는 Template을 쓰면 ?Placeholder 순서와 상관 없음
				if (m.getPassword() != null)
					ps.setString(++index, Password.encode(m.getPassword()));
				if (m.getName() != null)
					ps.setString(++index, m.getName());
				ps.setInt(++index, m.getId());
				ps.setString(++index, m.getEmail());

			}
		});
		//rtn Error 처리 - 이 1(정상Return)이 아니면(비정상Return) Exception 처리(Commit하지말고 Rollback처리해야)		
		if (rtn != 1) { //Error - Throw Exception
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException("Invalid update", 1, rtn);
			
		}
		
		return rtn;
	}

	@Override
	public int deleteById(int id) {
		// Exception 처리		
		int rtn = template.update(DELETE_BY_ID, id);
		if (rtn != 1) 
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(id + "", 1, rtn);
		
		return rtn;
	}

	@Override
	public int deleteByEmail(String email) {
		// Exception 처리
		int rtn = template.update(DELETE_BY_EMAIL, email);
		if (rtn != 1) 
			throw new JdbcUpdateAffectedIncorrectNumberOfRowsException(email, 1, rtn);
		
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
		
	}


}
