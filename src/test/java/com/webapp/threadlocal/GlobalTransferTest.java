package com.webapp.threadlocal;

class Result2 { //결과를 담는 객체
	int sum;
	
}
class Calculator2 {
	
	public Calculator2() {
		//GlobalVariable.sum = 0;
	}
	
	void summarize(int start, int end) {
		//계산값을 전역변수에 저장
		for (int i = start; i < end; i++) {
			//GlobalVariable.sum += i;
			//result.sum += i;
			GlobalVariable.result.get().sum += i;
			/*
			try {
				Thread.sleep((int)(Math.random()*1000));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			*/
		}
	}
	
	void multiply(int mul) {
		//GlobalVariable.sum *= mul;
		//result.sum *= mul;
		GlobalVariable.result.get().sum *= mul;
	}
	
}

class MyThread2 extends Thread {
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		GlobalVariable.result.set(new Result2());
		
		// Single Thread환경에서는 공유자원을 순서대로 접근하면 NO Problem
		Calculator2 c = new Calculator2();		
		c.summarize(1, 11);
		
		try {
			Thread.sleep((int)(Math.random()*1000));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		c.multiply(10);		
		//System.out.println("sum = " + GlobalVariable.sum);
		System.out.println("sum = " + GlobalVariable.result.get().sum);		
	}
}

public class GlobalTransferTest {

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new MyThread2().start();
		}
	}
	
	

}
