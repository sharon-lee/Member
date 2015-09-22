package com.webapp.mapper;

import java.util.Map;

public interface IdGeneratorMapper {
	Map<String, Object> selectByName(String name); //ResultMapping(ReturnType)이 Map으로 되도록
	//int update(int nextval);  
	int update(Map<String, Object> idGen); //Placehold에 Parameter nextval, name이 Binding되어야, Bean(Best)또는 Map을 사용
	
}
