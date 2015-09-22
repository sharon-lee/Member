package com.webapp.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.webapp.exception.AlreadyExistingMemberException;
import com.webapp.exception.MemberNotFoundException;
import com.webapp.model.Member;
import com.webapp.model.MemberList;

public class MemberRegisterServiceTest {
	
	static Log log = LogFactory.getLog(MemberRegisterServiceTest.class);

	//Controller Logic Test
	public static void main(String[] args) {
		// Spring 구동 - Service Layer에서 Biz logic구현할 때 dao사용
		// Spring Bean 설정 - MybatisMemberDao(MemberMapper Injection), SpringMemberDao(JdbcTemplate Injection)
		/*
		BeanFactory factory = new ClassPathXmlApplicationContext("spring/beans.xml");		
		MemberDao dao = factory.getBean(MemberDao.class);// org.springframework.beans.factory.NoUniqueBeanDefinitionException
		*/
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		//ctx.getEnvironment().setActiveProfiles("oracle");
		ctx.getEnvironment().setActiveProfiles("oracle", "spring"); //MemberDao.java, SpringMemberDao.java
		//ctx.getEnvironment().setActiveProfiles("oracle", "mybatis"); //beans_biz.xml, MybatisMemberDao.java, MemberMapper.xml
		//ctx.getEnvironment().setActiveProfiles("mysql", "spring"); //MemberDao.java, SpringMemberDao.java
		//ctx.getEnvironment().setActiveProfiles("mysql", "mybatis"); //beans_biz.xml, MybatisMemberDao.java, MemberMapper.xml

		ctx.load("spring/beans_oracle.xml", "spring/beans_mysql.xml");
		ctx.refresh();
		
		//Transaction은 Service에서 시작
		MemberRegisterService service = ctx.getBean(MemberRegisterService.class);
		
		MemberInfoService infoService = ctx.getBean(MemberInfoService.class);
		
		Member m = ctx.getBean(Member.class);
		log.info("id = " + m.getId());
		m.setEmail(m.getEmail() + "XXX");

		
		//Presentation Layer 역할 - Error Display
		try {
			service.register(m); 
			log.info("id = " + m.getId());
		} catch (AlreadyExistingMemberException e) {
		//2번째 register는 중복 Error (beans_biz.xml setting:채번테이블) Duplicate entry 'xxx@oracle.com'
		//org.springframework.dao.DuplicateKeyException, com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException
			//Mybatis도 Mybatis-Spring모듈이 기존의 Spring Exception으로 변환 처리해줌 
			log.info("### ERROR ### 멤버가 존재합니다", e);

		}
		
		//Member info = infoService.
	}

}
