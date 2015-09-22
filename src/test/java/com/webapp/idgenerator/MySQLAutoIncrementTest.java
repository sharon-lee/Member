package com.webapp.idgenerator;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;


public class MySQLAutoIncrementTest {

	static Log log = LogFactory.getLog(MySQLAutoIncrementTest.class);
		
	public static void main(String[] args) throws SQLException, IOException {
		//jdbc();
		//jdbcTemplate();
		//SqlSession();
		SqlSessionTemplate();
	}
	
	static void SqlSessionTemplate() throws IOException, SQLException {
		// Spring은 Bean설정해야
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		//ctx.getEnvironment().setActiveProfiles("oracle");
		ctx.getEnvironment().setActiveProfiles("mysql");
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
		
		//databaseId="oracle"
		//Spring FactoryBean은 mybatis_config.xml DB관련 <environments>와 <databaseIdProvider>를 무시하므로 
		//Spring에서 dataSource설정하듯이 beans.xml에서 설정해야
		mapper.insert(member); //commit됨: 여러개의 Statement를 수행하려면 Spring의 Transaction을 써야
		
		log.info("id = " + member.getId());
	}

	static void SqlSession() throws IOException {
		// TODO Auto-generated method stub
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis_config.xml");
		SqlSessionFactory factory = builder.build(inputStream, "mysql");
		//SqlSessionFactory factory = builder.build(inputStream, "oracle");
		
		//Connection
		SqlSession session = factory.openSession(false); //CTRL+SHIFT+O
		
		//CRUD - 입력값을 DB에 저장전에 Bean Validation을 해야(Business Logic처리)
		Member parameter = new Member();
		parameter.setEmail("xxx@mysql.com" + (int)(Math.random()*1000)); 
		parameter.setName("홍길동");
		parameter.setPassword("1234");
		parameter.setRegdate(new Date());
		
		session.insert("com.webapp.mapper.MemberMapper.insert", parameter);
		//Spring은 DB에 상관없이 같은 Error 출력
		log.info("MySQLAutoIncrementTest:::id = " + parameter.getId());
		
		parameter = new Member();
		parameter.setEmail("xxx@mysql.com" + (int)(Math.random()*1000)); 
		parameter.setName("홍길동");
		parameter.setPassword("1234");
		parameter.setRegdate(new Date());
		MemberMapper mapper = session.getMapper(MemberMapper.class);
		
		mapper.insert(parameter);
		log.info("MySQLAutoIncrementTest:MemberMapper:::id = " + parameter.getId());
		
		//반드시 Commit을 해줘야 DB에 저장
		session.commit();
	}

	static void jdbcTemplate() throws SQLException {
		// Jdbc Programming, JdbcTemplate을 사용
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(); //default 생성자
		ctx.getEnvironment().setActiveProfiles("mysql"); //load하기 전에 beans profile="oracle"을 활성화
		ctx.load("classpath:spring/beans.xml"); //default는 명시하지 않아도 classpath
		ctx.refresh();
		
		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);

		final String insert = "insert into member " +
				"(email, password, name, regdate) " +
				"values " 	 						+
				"(?, 'yyy', 'xxx', '2015/08/11') ";
		
		KeyHolder keyholder = new GeneratedKeyHolder();
		
		//insert수행항후 발급받은 id를 다시 keyholder로 읽어옴
		template.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				//PreparedStatement를 return해야
				PreparedStatement pstmt = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, (int)(Math.random()*10000000));
				
				return pstmt;
			}
		}, keyholder);
		
		log.info("key = " + keyholder.getKey().intValue());
				
	}
	
	static void jdbc() throws SQLException {
		// Jdbc Programming, factory를 만들어야
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(); //default 생성자
		ctx.getEnvironment().setActiveProfiles("mysql"); //load하기 전에 beans profile="oracle"을 활성화
		ctx.load("classpath:spring/beans.xml"); //default는 명시하지 않아도 classpath
		ctx.refresh();
		
		//DriverManagerDataSource ds = ctx.getBean(DriverManagerDataSource.class);
		BasicDataSource ds = ctx.getBean(BasicDataSource.class);
		log.info(ds.getUrl());
		
		Connection con = ds.getConnection();
		
		/*
		 * id는 개발자가 만드는게 아니므로 지운다
		 * 2. insert into member 
(id, email, password, name, regdate)
values
(1004, 'xxx1@xxx.com', 'yyy1', 'zz''z', '2015/08/11');
		 */
		String insert = "insert into member " 				+
						"(email, password, name, regdate) " +
						"values " 	 						+
						"(?, 'yyy', 'xxx', '2015/08/11') ";
		PreparedStatement pstmt = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS); //MySQL은 autoincrement지원하므로 Statement.RETURN_GENERATED_KEYS추가
		//PreparedStatement pstmt = con.prepareStatement(insert); //Oracle은 autoincrement지원하지 않으므로 Statement.RETURN_GENERATED_KEYS사용불가
		
		pstmt.setString(1, "" + (int)(Math.random()*10000));
		pstmt.executeUpdate();
		
		//insert수행항후 발급받은 id를 다시 읽어옴
		ResultSet rs = pstmt.getGeneratedKeys();
		rs.next();
		int seq = rs.getInt(1);
		log.info("seq = " + seq);

		/*
		 * create table member (
	id			int not null,
	email 		varchar(64) not null,
	password	varchar(64) not null,
	name		varchar(12) not null,
	regdate		timestamp not null)
	
	select * from member;
		 */
		/*
		rs = stmt.executeQuery("select * from member");
		rs.next();
		Member mem = (Member) rs.getObject(1);
		log.info("member = " + mem.getId() + "   " + mem.getEmail() + "   " + mem.getPassword() + "   " + mem.getName() + "   " + mem.getRegdate());
		*/
		
		con.close();
		
	}

}
