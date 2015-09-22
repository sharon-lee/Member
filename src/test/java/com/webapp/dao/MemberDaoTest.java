package com.webapp.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.webapp.model.Member;

public class MemberDaoTest {
	
	static Log log = LogFactory.getLog(MemberDaoTest.class);

	public static void main(String[] args) {
		// Spring 구동 - Service Layer에서 Biz logic구현할 때 dao사용
		// Spring Bean 설정 - MybatisMemberDao(MemberMapper Injection), SpringMemberDao(JdbcTemplate Injection)
		/*
		BeanFactory factory = new ClassPathXmlApplicationContext("spring/beans.xml");		
		MemberDao dao = factory.getBean(MemberDao.class);// org.springframework.beans.factory.NoUniqueBeanDefinitionException
		*/
		
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
		ctx.getEnvironment().setActiveProfiles("oracle");
		//ctx.getEnvironment().setActiveProfiles("mysql");
		ctx.load("spring/beans_oracle.xml", "spring/beans_mysql.xml");
		ctx.refresh();
		
		MemberDao dao = ctx.getBean("mybatisMemberDao", MemberDao.class);
		//MemberDao dao = ctx.getBean("springMemberDao", MemberDao.class);
		
		List<Member> list = dao.selectAll();
		
		for (Member m : list) {
			System.out.println(m.getId() + "   " + m.getEmail());
		}
		
		log.info("totalItem = " + dao.selectTotalCount());
	}

}
