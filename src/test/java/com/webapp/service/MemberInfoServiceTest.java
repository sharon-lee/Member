package com.webapp.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.webapp.exception.MemberNotFoundException;
import com.webapp.model.Member;
import com.webapp.model.MemberList;

public class MemberInfoServiceTest {
	
	static Log log = LogFactory.getLog(MemberInfoServiceTest.class);

	public static void main(String[] args) {
		// Spring 구동 - Service Layer에서 Biz logic구현할 때 dao사용
		// Spring Bean 설정 - MybatisMemberDao(MemberMapper Injection), SpringMemberDao(JdbcTemplate Injection)
		/*
		BeanFactory factory = new ClassPathXmlApplicationContext("spring/beans.xml");		
		MemberDao dao = factory.getBean(MemberDao.class);// org.springframework.beans.factory.NoUniqueBeanDefinitionException
		*/
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		//ctx.getEnvironment().setActiveProfiles("oracle");
		//ctx.getEnvironment().setActiveProfiles("oracle", "spring"); //MemberDao.java, SpringMemberDao.java
		//ctx.getEnvironment().setActiveProfiles("oracle", "mybatis"); //beans_biz.xml, MybatisMemberDao.java, MemberMapper.xml
		//ctx.getEnvironment().setActiveProfiles("mysql", "spring"); //MemberDao.java, SpringMemberDao.java
		ctx.getEnvironment().setActiveProfiles("mysql", "mybatis"); //beans_biz.xml, MybatisMemberDao.java, MemberMapper.xml

		ctx.load("spring/beans_oracle.xml", "spring/beans_mysql.xml");
		ctx.refresh();
		
		//Transaction은 Service에서 시작
		MemberInfoService service = ctx.getBean(MemberInfoService.class);
		
		try {
			//Member m = service.getMember(0); //Mybatis의 경우 없으면 java.lang.NullPointerException 
			//Spring은 org.springframework.dao.EmptyResultDataAccessException(:Incorrect result size: expected 1, actual 0)을 Throw
			//Member가 없는 경우 Spring, Mybatis 모두 일관되게 처리해야. 이경우 Spring에 맞춰서 처리
		
			Member m = service.getMember("xxx@xxx");
			log.info("id = " + m.getId());
			log.info("email = " + m.getEmail());
		// Exception이 생기면 Transaction은 Rollback
		} catch (MemberNotFoundException e) {
			log.info("### ERROR ### 로그인 ID가 없습니다", e); 
			//Error처리하고 JSP에 Forward해서 Presentation Layer에서 Error Display하도록 
		}
//Service에서는 Transaction처리하고, Exception변환처리: Biz 예외처리가 필요할때는 DB Error를 Biz Exception으로 Throw 변환 처리 
		
	}

}
