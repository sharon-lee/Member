package com.webapp.spring;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SpringContainerTest {

	static Log log = LogFactory.getLog(SpringContainerTest.class);
	static BeanFactory factory;
	SimpleDateFormat format;
	
	//설정자를 통한 주입
	public void setFormat(SimpleDateFormat format) {
		this.format = format;
	}
	
	public void print() throws InterruptedException {
		for (int i = 0; i < 5; i++) {
			Date curr = factory.getBean(Date.class);//Singleton으로 new를 한번만 하므로 같은 시각을 Print.현재시간을 Print하기 위해서는 Prototype으로 바꿔야
			//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");
			log.info("Current Time = " + format.format(curr.getTime()));
			
			Thread.sleep(1000);
			
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		// Container = Factory
		//ApplicationContext factory = new GenericXmlApplicationContext("file:beans.xml"); // file system상의 XML 설정 파일을 Argument로 넘겨줘야
		//xml설정(file system상 또는 Classpath상 또는 src/test/resources )대로 new하고 조립하고 lifecycle상의 초기화해서 factory를 만듦
		//default는 class path에서 파일을 읽어옴
		
		//Factory기능이 정의되어 있는 Low level Interface
		//BeanFactory factory = new GenericXmlApplicationContext("file:beans.xml");
		factory = new FileSystemXmlApplicationContext("beans.xml");
		//factory = new ClassPathXmlApplicationContext("beans.xml");
		
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");//생성자를 통한 주입(Code level Injection)
		//SimpleDateFormat format = (SimpleDateFormat) factory.getBean("format");
		
		factory.getBean(SpringContainerTest.class).print(); //설정자에 의한 print()
		
	}

}
