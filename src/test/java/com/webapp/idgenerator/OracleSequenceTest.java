package com.webapp.idgenerator;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.webapp.dao.MemberDao;
import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;


public class OracleSequenceTest {

	static Log log = LogFactory.getLog(OracleSequenceTest.class);
		
	public static void main(String[] args) throws SQLException, IOException {
		//jdbc();
		//jdbcTemplate(); //Spring 사용
		//SqlSession(); //SqlSession()은 Mybatis만 사용
		SqlSessionTemplate(); //실무!!! Spring하고 Mybatis가 합쳐지면 SqlSession에서 SqlSessionTemplate()로 사용
	}
	
	static void SqlSessionTemplate() throws IOException, SQLException {
		// Spring은 Bean설정해야
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("oracle", "mybatis");
		//ctx.getEnvironment().setActiveProfiles("mysql");
		ctx.load("spring/beans_oracle.xml", "spring/beans_mysql.xml");
		ctx.refresh();
		
		DataSource ds = (DataSource) ctx.getBean("dataSource");
		
		Connection con = ds.getConnection();
		
		con.getMetaData().getDatabaseProductName(); //Meta:Schema, Table의 정보, XML의 실 Data를 기술하는 Tag정보등
		
		log.info("dbProductName = " + con.getMetaData().getDatabaseProductName());
		log.info("dbProductVersion = " + con.getMetaData().getDatabaseProductVersion());
		log.info("dbMajorVersion = " + con.getMetaData().getDatabaseMajorVersion());
		log.info("dbMinorVersion = " + con.getMetaData().getDatabaseMinorVersion());
		
		//beans_oracle.xml 설정파일에서 SqlSessionTemplate 처리, Mapper만 호출
		MemberMapper mapper = ctx.getBean(MemberMapper.class);
		Member member = ctx.getBean(Member.class);
		
		/* Member Bean 설정 - Singleton이 아님
		Member member = new Member();
		member.setEmail("yyy@oracle.com" + (int)(Math.random()*1000000));
		member.setName("xxx");
		member.setPassword("1234");
		member.setRegdate(new Date());
		*/

		member.setEmail(member.getEmail() + (int)(Math.random()*1000000));
		
		log.info(member.getRegdate());
		
		mapper.insert(member); //commit됨: 여러개의 Statement를 수행하려면 Spring의 Transaction을 써야
		
		log.info("id = " + member.getId());
	}

	static void SqlSession() throws IOException {
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		//Mybatis에서 준비된 Resource를 사용
		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis_config.xml");
		SqlSessionFactory factory = builder.build(inputStream, "oracle");
		//SqlSessionFactory factory = builder.build(inputStream, "mysql");
		
		//Connection을 Autocommit을 false로 가져온것과 같은 효과 
		SqlSession session = factory.openSession(false);
		
		//CRUD - 내부적으로 Jdbc를 사용. Mybatis는 Query를  XML로 작성해서 수행
		/*
		 * ibatis CRUD ==> 매퍼 파일 (xml) 
		 */
		String statement=null; //단점: statement String 오타 문제
		List<Member> list = session.selectList("com.webapp.mapper.MemberMapper.selectAll"); //Parameter Binding
		log.info("member size = " + list.size());
		

		Member member = session.selectOne("com.webapp.mapper.MemberMapper.selectById", 1002); //XML에도 "com.webapp.dao.MemberDao.select"로 기술. 실행시 Error / 오타 문제 감지
		if (member != null)
			log.info("id = " + member.getId());
		
		member = new Member();
		member.setEmail("xxx@xxx.com" + (int)(Math.random()*1000));
		member.setName("홍길동");
		member.setPassword("yyy");
		member.setRegdate(new Date());
		
		session.insert("com.webapp.mapper.MemberMapper.insert", member);
		/*
		session.delete("com.webapp.mapper.MemberMapper.delete", null);
		session.update("com.webapp.mapper.MemberMapper.update", null);
		*/
		/*
		 * Mapper Interface CRUD ==> Mapper Interface + 매퍼 파일(xml) - Mybatis에서 추가된 기능 Mapper를 CRUD에 사용
		 * Mapper XML에 있는 sql statement를 수행
		 * /Member/src/main/java/com/webapp/dao/MemberMapper.xml를 참조 (Mapper를 Dao로 간주-Dao처럼 하나의 Table당 Mapper정의해야)
		 * namespace.select id(method)로 지칭/호출
		 */

		MemberMapper mapper = session.getMapper(MemberMapper.class);
		list = mapper.selectAll(); //MemberMapper Interface의 method 호출
		log.info("membermapper size = " + list.size());
		
		member = mapper.selectById(1000);
		if (member != null)
			log.info("member id = " + member.getId());
		
		member = new Member();
		member.setEmail("yyy@mapper.com" + (int)(Math.random()*1000));
		member.setName("홍길동");
		member.setPassword("yyy");
		member.setRegdate(new Date());
		mapper.insert(member);
		/*		
		dao.deleteByIdWithEmail(100, null);
		dao.update(null);
		*/
		session.commit();
		/*
		session.rollback();
		*/
	}
	
	static void jdbcTemplate() throws SQLException {
		// Jdbc Programming, JdbcTemplate을 사용
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(); //default 생성자
		ctx.getEnvironment().setActiveProfiles("oracle"); //load하기 전에 beans profile="oracle"을 활성화
		ctx.load("classpath:spring/beans.xml"); //default는 명시하지 않아도 classpath
		ctx.refresh();
		
		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);
		/*
		 * 1. select member_id_seq.nextval from dual;(알아서 lock검) 채번/번호발급한 후에 2.insert
		 */ 
		String sql = "select member_id_seq.nextval from dual";
		int seq = template.queryForObject(sql, Integer.class);
		log.info("jdbcTemplate():seq = " + seq);
		
		String insert = "insert into member " 		+
						"(id, email, password, name, regdate) " +
						"values " 	 				+
						"(?, ?, 'yyy', 'xxx', '2015/08/11') ";
		template.update(insert, seq, "xxx@" +seq);

		sql = "select * from member";
		/*
		List<Member> list = template.query(sql, new RowMapper<Member>() {
			@Override
			public Member mapRow(ResultSet rs, int rowNum) throws SQLException {
				// TODO Auto-generated method stub
				return null;
			}
		});
		*/
		List<Member> list = template.query(sql, new BeanPropertyRowMapper<Member>(Member.class));
		
		for (Member m : list) {
			log.info("jdbcTemplate():member = " + m.getId() + "   " + m.getEmail());
		}

		/* 정석
		return template.query("select * from dept", new RowMapper<Dept>() {

			@Override
			public Dept mapRow(ResultSet rs, int rowNum) throws SQLException {
				// 실질적인 Mapping 작업
				return null;
			}
		}); //Parameter가 없는 경우
		*/
		
	}
	
	static void jdbc() throws SQLException {
		// Jdbc Programming, factory를 만들어야
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(); //default 생성자
		ctx.getEnvironment().setActiveProfiles("oracle"); //load하기 전에 beans profile="oracle"을 활성화
		ctx.load("classpath:spring/beans.xml"); //default는 명시하지 않아도 classpath
		ctx.refresh();
		
		//DriverManagerDataSource ds = ctx.getBean(DriverManagerDataSource.class);
		BasicDataSource ds = ctx.getBean(BasicDataSource.class);
		log.info(ds.getUrl());
		
		Connection con = ds.getConnection();
		
		/*
		 * 1. select member_id_seq.nextval from dual;(알아서 lock검) 채번/번호발급한 후에 2.insert
		 */ 
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery("select member_id_seq.nextval from dual");
		rs.next();
		int seq = rs.getInt(1);
		log.info("seq = " + seq);
		
		/*
		 * 2. insert into member 
(id, email, password, name, regdate)
values
(1004, 'xxx1@xxx.com', 'yyy1', 'zz''z', '2015/08/11');
		 */
		String insert = "insert into member " 		+
						"(id, email, password, name, regdate) " +
						"values " 	 				+
						"(?, ?, 'yyy', 'xxx', '2015/08/11') ";
		PreparedStatement pstmt = con.prepareStatement(insert);
		pstmt.setInt(1, seq);
		pstmt.setString(2, "xxx@xxx.com" + seq);
		pstmt.executeUpdate();
		/*
		 * create table member (
	id			int not null,
	email 		varchar(64) not null,
	password	varchar(64) not null,
	name		varchar(12) not null,
	regdate		timestamp not null)
		 */
		String sql = "select * from member";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		rs.next();
		Member mem = (Member) rs.getObject(1);
		log.info("member = " + mem.getId() + "   " + mem.getEmail() + "   " + mem.getPassword() + "   " + mem.getName() + "   " + mem.getRegdate());
		
		
		con.close();
		
		
	}

}
