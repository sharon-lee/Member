package com.webapp.json;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.webapp.model.Member;

public class GsonTest {

	public static void main(String[] args) {
		// TODO Server에서 DB 읽어 Networking해서 String Data보낼때 사용-Serialization
		//Gson g = new Gson();
		Gson g = new GsonBuilder().setPrettyPrinting().create();
		
		//이 경우(toJson) Serialization에 사용 (/ Deserialization)
		/*
		 * Primitive
		 */
		String json = g.toJson("Hello");		
		System.out.println(json);
		
		/*
		 * Array
		 */
		json = g.toJson(new String[]{"Hello", "Java", "C++"});
		json = g.toJson(new Object[]{"Hello", 1234, "C++"});
		//JavaScript와의 연동을 위해 JSon 사용
		System.out.println(json);
		
		/*
		 * Object(Bean)
		 */
		Member m = new Member();
		m.setId(100);
		m.setEmail("xxx@webapp.com");
		m.setName("홍길동");
		m.setPassword("1234");
		m.setRegdate(new Date());
		
		json = g.toJson(m);
		System.out.println(json);
	}

}
