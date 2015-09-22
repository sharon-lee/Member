package com.webapp.spring;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.webapp.mapper.MemberMapper;
import com.webapp.model.Member;

public class CreateBeanTest {

	static Log log = LogFactory.getLog(CreateBeanTest.class);

	public static void main(String[] args) throws Exception {
		/*
		 * Object(객체)를 생성 방법
		 * 
		 * 1. new 객체 생성
		 * 2. static method로 객체 생성 newInstance()- why to implement Singleton
		 * 3. FactoryBean으로 객체 생성 (Mybatis, Spring에서 주사용)
		 */
		//Calendar c = Calendar.getInstance();
		
		//BeanFactory 실행
		String resourceLocations = "com/webapp/spring/createbean.xml";
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(resourceLocations);
	
		// Spring의 Bean설정:생성자를 고려해야 (Spring은 Reflection을 사용)
		/*
		CityDao dao = ctx.getBean(CityDao.class);
		dao.print();

		CityDao dao2 = CityDao.newInstance();
		dao2.print();

		CityDao dao = new CityDao(); 
		log.info("dao = " + dao.toString());
		*/
		/*
		 * Error: org.springframework.beans.factory.NoSuchBeanDefinitionException: 
		 * No qualifying bean of type [com.webapp.model.Member] is defined
		 * Date f = ctx.getBean(Date.class);
		 * Member f = ctx.getBean(Member.class);
		 */
		//SqlSessionFactory는 beans.xml에 설정되어 있지 않아도 
		//객체생성할때 Sprint이 getObject()로 객체 생성 특별 취급
		/*
		SqlSessionFactory f = ctx.getBean(SqlSessionFactory.class);
		SqlSession session = f.openSession(false);
		*/
		/* Spring Transaction으로, Spring Exception으로 변환 처리
		 * SqlSessionTemplate은 Gluecode로 SqlSession을 Wrapping해서 구현한 Interface, Proxy처럼 SqlSession으로 변환 처리
		SqlSessionTemplate session = ctx.getBean(SqlSessionTemplate.class);
		 */
		/** Java doc 주석
		 * 
		 */
		//Spring의 SqlSession(NOT Mybatis)
		SqlSession session = ctx.getBean(SqlSession.class);
		
		MemberMapper mapper = session.getMapper(MemberMapper.class);
		
		List<Member> list = mapper.selectAll();
		log.info("size = " + list.size());
		
		/*
		MyFactoryBean fb = new MyFactoryBean(); //SqlSessionFactoryBuilder와 같은 역할
		SqlSessionFactory factory = fb.getObject();
		*/
	}

}
