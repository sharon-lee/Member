package com.webapp.threadlocal;

public class GlobalVariable {
	public static int sum; //전역변수
	public static ThreadLocal<Result2> result = new ThreadLocal<Result2>(); //ThreadLocal은 Thread마다 별도의 공간 사용
	
	
}
