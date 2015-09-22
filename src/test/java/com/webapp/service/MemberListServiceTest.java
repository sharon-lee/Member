package com.webapp.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.webapp.model.Member;
import com.webapp.model.MemberList;

public class MemberListServiceTest {
	
	static Log log = LogFactory.getLog(MemberListServiceTest.class);

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
		ctx.getEnvironment().setActiveProfiles("mysql", "spring"); //MemberDao.java, SpringMemberDao.java
		//ctx.getEnvironment().setActiveProfiles("mysql", "mybatis"); //beans_biz.xml, MybatisMemberDao.java, MemberMapper.xml

		ctx.load("spring/beans_oracle.xml", "spring/beans_mysql.xml");
		ctx.refresh();
		
		//Transaction은 Service에서 시작
		MemberListService service = ctx.getBean(MemberListService.class);
		List<Member> list = service.getListAll();

		log.info("size = " + list.size());
		
		MemberList page = service.getList(3); //pageNo이 over되면 lastPage, -이면 firstPage를 보이도록
		
		log.info("totalItem = " + page.getTotalItem());
		log.info("pageNo = " + page.getpageNo());
		log.info("firstItem = " + page.getFirstItem()); //currentPageNo의 firstItem no.
		log.info("lastItem = " + page.getLastItem());
		
		for (Member m : page.getMembers()) {
			System.out.println(m.getId() + "   " + m.getEmail());
		}
	}

}
