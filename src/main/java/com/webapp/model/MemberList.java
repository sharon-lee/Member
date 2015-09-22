package com.webapp.model;

import java.util.List;

import com.webapp.util.Pagination;

public class MemberList extends Pagination { //객체지향: Inheritance(상속성) 구현
	List<Member> members;
	
	public MemberList() {
		// default 생성자
		super();
	}
	
	public MemberList(int itemsPerPage, int pagesPerGroup) {
		// 사용자 생성자
		super(itemsPerPage, pagesPerGroup);
	}

	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}
	
	
}
