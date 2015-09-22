package com.webapp.mybatis;

//static Class.*로 표시하면 마치 내가 구현한 static method인것처럼 class명을 붙이지 않고 static method만으로 호출 가능
import static org.apache.ibatis.jdbc.SqlBuilder.*;

public class SqlBuilderTest {
	
	public static void main(String[] args) {
		//SqlBuilder는 static class/method. 처음에는 Begin을 호출
		/* JdbcTemplate에는 이런 Mybatis 기능이 없음
		 * update 	member
		      set 	password= 'qwer1234'
		    where	id 		= 1000;
		 */
		/*
		SqlBuilder.BEGIN(); 
		SqlBuilder.UPDATE("member");
		if (true) SqlBuilder.SET("email = ?");
		if (false) SqlBuilder.SET("password = ?");
		if (true) SqlBuilder.SET("name = ?");
		if (false) SqlBuilder.SET("regdate = ?");
		SqlBuilder.WHERE("id = ? ");
		*/
		//Dynamic SQL 구문 생성 방법 - Spring Jdbc에서 사용
		BEGIN(); 
		UPDATE("member");
		if (true) SET("email = ?");
		if (false) SET("password = ?");
		if (true) SET("name = ?");
		if (false) SET("regdate = ?");
		if (true) WHERE("id = ? ");
		if (true) WHERE("email = ? ");
		OR();
		if (true) WHERE("email = ? ");
		System.out.println(SQL());
		
		
	}

}
