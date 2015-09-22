package com.webapp.idgenerator;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Map;

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
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.webapp.mapper.IdGeneratorMapper;
import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;


public class GeneratorTableTest {

	static Log log = LogFactory.getLog(GeneratorTableTest.class);
		
	static GenericXmlApplicationContext ctx; //factory를 하나만 만들도록 static으로 선언
	
	public static void main(String[] args) throws SQLException {
		/*
		// Spring Jdbc Programming, factory를 만들어야
		ctx = new GenericXmlApplicationContext(); //default 생성자
		//ctx.getEnvironment().setActiveProfiles("oracle"); //load하기 전에 beans profile="oracle"을 활성화
		ctx.getEnvironment().setActiveProfiles("mysql");
		ctx.load("classpath:spring/beans.xml"); //default는 명시하지 않아도 classpath
		ctx.refresh();
		*/		
		
		ctx = new GenericXmlApplicationContext(); //default 생성자
		//ctx.getEnvironment().setActiveProfiles("oracle"); //load하기 전에 beans profile="oracle"을 활성화
		//ctx.getEnvironment().setActiveProfiles("oracle", "mybatis");
		//org.springframework.beans.factory.NoUniqueBeanDefinitionException
		ctx.getEnvironment().setActiveProfiles("mysql");
		ctx.load("spring/beans_oracle.xml", "spring/beans_mysql.xml"); //default는 명시하지 않아도 classpath
		ctx.refresh();
		
		//Connection Pool은 10개까지 제공되므로 10이하는 중복발급이 안되나 10초과되면 중복 발급
		//Spring에서 Transaction처리하고 JdbcTemplate과 협업해야
		for (int i = 0; i < 1; i++) {
			Runnable r = new Runnable() {
				
				@Override
				public void run() {
					try {
						//jdb4c();
						//jdbcTemplate(); //Transaction처리 필요
						//SqlSession();
						SqlSessionTemplate();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
					/*catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					
				}

			};
			Thread t = new Thread(r);
			t.start();
		}
		//jdbc();
		//jdbcTemplate();
		
	}
	
	
	// Service layer에서 Transaction 시작
	static void SqlSessionTemplate() throws InterruptedException {
		// Spring Transaction 핵심 - Transaction Manager Interface
		PlatformTransactionManager tm = ctx.getBean(PlatformTransactionManager.class);//beans_biz.xml에 설정한 Transaction Manager를 가져옴
		
		DefaultTransactionDefinition td = new DefaultTransactionDefinition();
		td.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
		td.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
		
		TransactionStatus status = tm.getTransaction(td);
		
		//multi-thread환경에서는 전역변수(static)는 모두 Access가능하므로 parameter전달로 사용불가
		try {
			memberInsert();  // dao insert
			//memberUpdate();  // dao update
			
			tm.commit(status);			
		} catch (BadSqlGrammarException e) { //Spring Exception
			log.info("BadSqlGrammarException...", e);
		} catch (Exception e) {
			tm.rollback(status);
			e.printStackTrace();
		}
	}
	
	static void memberUpdate() throws InterruptedException {
		
		Member member = new Member();
		/*
		 * Member Mapper id=27300
		 */
		member.setId(28000); //org.springframework.jdbc.BadSqlGrammarException:
		member.setPassword("xxxx1234");
		member.setName("yyyy");
		MemberMapper memberMapper = ctx.getBean(MemberMapper.class);
		
		memberMapper.update(member);
		
		//log.info("seqno = " + seqno);
	}
	
	// dao, ThreadLocal을 써서 Transaction정보를 Thread의 Local변수에 저장, 전달
	static void memberInsert() throws InterruptedException {
		
		Member member = ctx.getBean(Member.class);
		/*
		 * 채번, Member Mapper
		 */
		IdGeneratorMapper idGenMapper = ctx.getBean(IdGeneratorMapper.class);
		MemberMapper memberMapper = ctx.getBean("memberMapper", MemberMapper.class);
		
		//SqlSessionTemplate template = ctx.getBean(SqlSessionTemplate.class);
		
		Map<String, Object> idGen = idGenMapper.selectByName("memberId");
		int nextval = ((BigDecimal)idGen.get("nextval")).intValue();
		int incval = ((BigDecimal)idGen.get("incval")).intValue();
		int seqno = nextval + incval;
		
		Thread.sleep((int)(Math.random()*1000)); //채번 동시 발급 재현
		
		idGen.put("nextval", seqno);		
		idGenMapper.update(idGen);
		
		member.setId(seqno);
		member.setEmail(member.getEmail() + seqno); //Integrity Violation방지용
		
		memberMapper.insertGenerator(member);
		//memberMapper.insert(member);
		
		log.info("seqno = " + seqno);

	}


	
	static void SqlSession() throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis_config.xml");
		//SqlSessionFactory factory = builder.build(inputStream, "mysql");
		SqlSessionFactory factory = builder.build(inputStream, "oracle");
		
		//Connection
		SqlSession session = factory.openSession(false); //CTRL+SHIFT+O
		
		IdGeneratorMapper idGeneratorMapper = session.getMapper(IdGeneratorMapper.class);
		MemberMapper memberMapper = session.getMapper(MemberMapper.class);
		
		Map<String, Object> idGen = idGeneratorMapper.selectByName("memberId");
		int nextval = ((BigDecimal)idGen.get("nextval")).intValue();
		int incval = ((BigDecimal)idGen.get("incval")).intValue();		
		int seqno = nextval + incval;
		
		Thread.sleep((int)(Math.random()*3000));
		
		idGen.put("nextval", seqno);
		idGeneratorMapper.update(idGen);
		
		Member member = new Member();
		member.setId(seqno);
		member.setEmail("xxx@gen.com" + seqno);
		member.setPassword("1234");
		member.setName("홍길동");
		member.setRegdate(new Date());
		
		memberMapper.insertGenerator(member);
		
		log.info("idGeneratorMapper:::seqno = " + seqno);
		
		session.commit();
		session.close();
	}
	
	static void jdbcTemplate() throws SQLException, InterruptedException {
		// Jdbc Programming, JdbcTemplate을 사용
		/* main()에서 수행되고 있어
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(); //default 생성자
		ctx.getEnvironment().setActiveProfiles("oracle"); //load하기 전에 beans profile="oracle"을 활성화
		ctx.load("classpath:spring/beans.xml"); //default는 명시하지 않아도 classpath
		ctx.refresh();
		*/
		//JdbcTemplate은 자동으로 Connection을 만들어줌. template은 Spring에서 제공, Transaction처리해야
		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);
		/*
		 * 1. select member_id_seq.nextval from dual;(알아서 lock검) 채번/번호발급한 후에 2.insert
		 */ 
		//String sql = "select member_id_seq.nextval from dual";
		//int seq = template.queryForObject(sql, Integer.class);
		//log.info("jdbcTemplate():seq = " + seq);
		//채번 발급 로직
		String sql = "select name, nextval, incval " +
				 	" from id_generator "			 +
				 	"where name = 'memberId' " 	 	 + 
				 	" for update ";

		//Spring의 PlatformTransactionManager의 Traansaction처리는 일반화 되어 있어 Mybatis에서도 공통적으로 사용
		//beanx.xml설정파일에서 처리-선언적 Transaction관리
		//DataSourceTransactionManager tm = new DataSourceTransactionManager();
		/* Bean 설정에서 가져오도록 Change
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(ctx.getBean(DataSource.class));
		*/
		PlatformTransactionManager tm = ctx.getBean(PlatformTransactionManager.class);
		
		//Transaction 정의 정보를 넘겨줘야 - DefaultTransactionDefinition
		//TransactionDefinition definition = new DefaultTransactionDefinition();
		DefaultTransactionDefinition definition = new DefaultTransactionDefinition();
		definition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		definition.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);//Multi-Thread수행시에 Transaction간의 독립성 (순서대로 DB내용 까지지 않음)
		//Oracle default는 ISOLATION_READ_COMMITTED
		definition.setReadOnly(false); //true이면 속도가 빨라지나 update는 수행할수 없고 select만 가능
		//definition.setTimeout(10);
		/*
		 * private int propagationBehavior = PROPAGATION_REQUIRED; Connection이 반드시 있어야
	private int isolationLevel = ISOLATION_DEFAULT;
	private int timeout = TIMEOUT_DEFAULT;
	private boolean readOnly = false; true이면 select만을 위해 최적화
		 */
		TransactionStatus status = tm.getTransaction(definition);
		
		//template은 Transactionstatus를 알고있기 때문에 이미 있는 Connection을 가져와서 사용후 Commit, Rollback을 함
		Map<String, Object> gen = template.queryForMap(sql);
		
		Thread.sleep((int)(Math.random()*3000)); //test용
		
		//MAP에서는 int는 BigDecimal로 나오므로  .intValue()로 
		String name = (String) gen.get("name");
		int nextval = ((BigDecimal) gen.get("nextval")).intValue();
		int incval = ((BigDecimal) gen.get("incval")).intValue();
				
		/*
		log.info("name = " + name);
		log.info("nextval = " + nextval);
		log.info("incval = " + incval);
		*/
		String update = "update id_generator " 	+
				" set nextval = ? "		+
				" where name = 'memberId'";
		
		template.update(update, nextval + incval);
		
		//id를 발급받고 member table에 id를 추가
		String insert = "insert into member2 " 			+
				"(id, email, password, name, regdate) " +
				"values " 	 							+
				"(?, ?, 'yyy', 'xxx', '2015/08/11') ";
		
		template.update(insert, nextval + incval, "xxx@" + nextval); //같은 Transaction
		
		tm.commit(status); //template.queryForMap와 template.update는 하나의 Transaction으로 묶임 
		
		
		log.info("name = " + name + ", nextval = " + (nextval+incval) + ", incval = " + incval);
		
		
		//queryForMap수행후 Connection을 자동으로 Close하므로 update시에는 같은 Connection이 아니므로 하나의 Transaction이 아니므로 Lock이 안걸림(중복발번됨)
		//Transaction관리가 필요 - 중복발급 방지를 위해 template.queryForMap, update모두 하나의 Connection을 공유해야
		/*
		 * String insert = "insert into member " 		+
						"(id, email, password, name, regdate) " +
						"values " 	 				+
						"(?, ?, 'yyy', 'xxx', '2015/08/11') ";
		*/
		//template.update(insert, seq, "xxx@" +seq);
		/*
		sql = "select * from member";
		template.query(sql, rowMapper)
		*/	
	}
	
	static void jdbc() throws SQLException {
		
		try {
			Thread.sleep((int)(Math.random()*3000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//DriverManagerDataSource ds = ctx.getBean(DriverManagerDataSource.class); //Connectionpool은 BasicDataSource
		//BasicDataSource ds = ctx.getBean(BasicDataSource.class);
		DataSource ds = ctx.getBean(DataSource.class);
		//의존적이지 않게 DataSource자료형으로 만들어야 - 의존도를 낮게 
		
		log.info(((BasicDataSource)ds).getUrl()); //.가 ()보다 우선순위가 높아 Error
		
		Connection con = ds.getConnection();
		con.setAutoCommit(false);
		
		/*
		 * 1. select member_id_seq.nextval from dual;(알아서 lock검) 채번/번호발급한 후에 2.insert
		 */ 
		Statement stmt = con.createStatement();
		
		//for update는 lock 시간은 최소화해야
		String sql = "select name, nextval, incval " +
					 " from id_generator "			 +
					 "where name = 'memberId' " 	 + 
					 " for update ";
		ResultSet rs = stmt.executeQuery(sql);
		rs.next();
		String name = rs.getString("name");		
		int nextval = rs.getInt("nextval");
		int incval = rs.getInt("incval");
		int seq = nextval + incval;
		
		log.info("seq = " + seq);
		
		/*
		 * 채번 Dao(unique number id_generator)
		 * 2. insert into member 
(id, email, password, name, regdate)
values
(1004, 'xxx1@xxx.com', 'yyy1', 'zz''z', '2015/08/11');
		 */
		String insert = "insert into member2 " 					+
						"(id, email, password, name, regdate) " +
						"values " 	 							+
						"(?, ?, 'yyy', 'xxx', '2015/08/11') ";
		PreparedStatement pstmt = con.prepareStatement(insert);
		pstmt.setInt(1, seq);
		pstmt.setString(2, "xxx@xxx.com" + seq);
		pstmt.executeUpdate();
		
		String update = "update id_generator " 	+
						" set nextval = ? "		+
						" where name = 'memberId'";
		pstmt = con.prepareStatement(update);
		pstmt.setInt(1, seq);
		pstmt.executeUpdate();
		
		/*
		 * create table member (
	id			int not null,
	email 		varchar(64) not null,
	password	varchar(64) not null,
	name		varchar(12) not null,
	regdate		timestamp not null)
		 */
		
		/*
		 * String sql = "select * from member";
		pstmt = con.prepareStatement(sql);
		rs = pstmt.executeQuery();
		rs.next();
		Member mem = (Member) rs.getObject(1);
		log.info("member = " + mem.getId() + "   " + mem.getEmail() + "   " + mem.getPassword() + "   " + mem.getName() + "   " + mem.getRegdate());
		*/
		con.commit();
		con.close();
		
		
	}

}
