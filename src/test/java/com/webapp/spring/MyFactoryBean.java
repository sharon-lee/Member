package com.webapp.spring;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.FactoryBean;

public class MyFactoryBean implements FactoryBean<SqlSessionFactory> {

	//FactoryBean<SqlSessionFactory>은 SqlSessionFactoryBuilder와 같은 역할
	static SqlSessionFactory factory;
	
	@Override
	public SqlSessionFactory getObject() throws Exception {
		// TODO Auto-generated method stub
		if (factory == null) {
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			factory = builder.build(Resources.getResourceAsStream("mybatis/mybatis_config.xml"), "oracle");
		}
		
		return factory;
	}

	@Override
	public Class<?> getObjectType() {
		// TODO Auto-generated method stub
		return SqlSessionFactory.class;
	}

	@Override
	public boolean isSingleton() {
		// TODO Auto-generated method stub
		return true;
	}

}
