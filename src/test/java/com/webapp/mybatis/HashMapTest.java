package com.webapp.mybatis;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.webapp.mapper.IdGeneratorMapper;

public class HashMapTest {

	static Log log = LogFactory.getLog(HashMapTest.class);
	
	public static void main(String[] args) throws IOException {
		// Mybatis - Factory 개념 SQL Session Factory(Framework)		
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder(); //설정파일을 갖고 session factory를 만듦
		
		InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis_config.xml"); //default로는 Classpath에 있는것을 가져옴.

		//설정파일을 읽어서 inputStream으로 주입(Injection)
		SqlSessionFactory factory = builder.build(inputStream, "mysql"); //소문자 member field
		//SqlSessionFactory factory = builder.build(inputStream, "oracle"); //대문자 member field
		
		//JdbcConnection/Session을 공급 - Builder Pattern		
		SqlSession session = factory.openSession(false);
		
		//Map<String, Object> map = session.selectOne("com.webapp.mapper.IdGeneratorMapper.selectByName", "memberId");
		//SQL statement 구문을 parameter로 넘겨줘야		
		/*
		 * Oracle은 대문자 member field만 인식하므로 IdGeneratorMapper.xml에서 alias처리 
		 * log.info("ORACLE" + "name = " + map.get("NAME") +
				", nextval = " + map.get("NEXTVAL") +
				", incval = " + map.get("INCVAL")
				);
		 */
		/* Mapper 사용 */
		IdGeneratorMapper mapper = session.getMapper(IdGeneratorMapper.class);
		Map<String, Object> map = mapper.selectByName("memberId");

		String name = (String) map.get("name");
		int nextval = ((BigDecimal)map.get("nextval")).intValue();//DB상의 int는 BigDecimal로, Java는 int. 수동변환 필요
		int incval = ((BigDecimal) map.get("incval")).intValue();
		int seqno = nextval + incval;
		
		log.info("Alias" + "name = " + name +
				", nextval = " + nextval +
				", incval = " + incval +
				", seqno = " + seqno
				);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("nextval", seqno);
		param.put("name", "memberId");
		//session.update("com.webapp.mapper.IdGeneratorMapper.update", param);
		mapper.update(param);
		
		session.commit();
		session.close();
		
	}

}
