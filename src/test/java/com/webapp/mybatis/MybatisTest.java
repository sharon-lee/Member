package com.webapp.mybatis;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.util.ResourceUtils;

import com.webapp.model.Member;

public class MybatisTest {

	public static void main(String[] args) throws IOException {
		// Mybatis - Factory 개념 SQL Session Factory(Framework)
		
		SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder(); //설정파일을 갖고 session factory를 만듦
		
		//InputStream inputStream = Resources.getResourceAsStream("mybatis/mybatis_config.xml"); //default로는 Classpath에 있는것을 가져옴.
		//InputStream inputStream = Resources.getUrlAsStream("file:mybatis_xxx.xml"); //filesystem상
		//FileInputStream inputStream = new FileInputStream("mybatis_xxx.xml");
		//InputStream inputStream = MybatisTest.class.getClassLoader().getResourceAsStream("mybatis/mybatis_config.xml");
		//File file = ResourceUtils.getFile("classpath:mybatis/mybatis_config.xml"); //Spring Resource
		File file = ResourceUtils.getFile("file:mybatis_xxx.xml"); //InpurStream or Reader를 원하는 대로 사용
		FileInputStream inputStream = new FileInputStream(file);
		
		SqlSessionFactory factory = builder.build(inputStream); //설정파일을 읽어서 inputStream으로 주입(Injection)
		
		//JdbcConnection/Session을 공급 - Builder Pattern
		
		SqlSession session = factory.openSession();
		
		//Query - session.select (insert, delete, update) 
		///Member/src/main/java/com/webapp/dao/MemberMapper.xml에 기술되어 있는 것이 수행
		//List<Member> list = session.selectList("com.webapp.dao.xxx.selectAll");
		List<Member> list = session.selectList("com.webapp.dao.MemberDao.selectAll");
		//List<Member> list = session.selectOne("com.webapp.dao.xxx.selectAll"); //Error case
		
		for (Member m : list) {
			System.out.println(m.getId() + "   " + m.getEmail());
		}
		
		// SQL MAP
		//Member m = session.selectOne("com.webapp.dao.xxx.selectById", 1002);
		//Mapper Interface는 MyBatis 3.0부터 도입
		Member m = session.selectOne("com.webapp.dao.MemberDao.selectById", 1002); //String ID, 파라미터로 Map이나 객체를 넘겨줘야
		System.out.println(m.getId() + "   " + m.getEmail());
	}

}
