package com.webapp.spring;


public class CityDao {
		
	static CityDao citydao;
	
	//Singleton구현을 위해 private로 선언
	private CityDao() {
		
	}
	
	public static CityDao newInstance() {
		if(citydao == null) {
			citydao = new CityDao();
		}
		return citydao;
	}
	
	void print() {
		System.out.println("CityDao...");
	}
	
}
