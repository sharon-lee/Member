package com.webapp.threadlocal;

class Result { //결과를 담는 객체
	int sum;
	
}
class Calculator {
	
	public Calculator() {
		//GlobalVariable.sum = 0;
	}
	
	void summarize(Result result, int start, int end) {
		//계산값을 전역변수에 저장
		for (int i = start; i < end; i++) {
			//GlobalVariable.sum += i;
			result.sum += i;
		}
	}
	
	void multiply(Result result, int mul) {
		//GlobalVariable.sum *= mul;
		result.sum *= mul;
	}
	
}

class MyThread extends Thread {
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		Result r = new Result();
		
		// Single Thread환경에서는 공유자원을 순서대로 접근하면 NO Problem
		Calculator c = new Calculator();		
		c.summarize(r,1, 11);
		
		try {
			Thread.sleep((int)(Math.random()*1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c.multiply(r,10);		
		//System.out.println("sum = " + GlobalVariable.sum);
		System.out.println("sum = " + r.sum);
				
	}
}

public class ParameterTransferTest {

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			new MyThread().start();
		}
	}
	
	

}
