package com.webapp.aspect;

import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;

//Weaver가 Annotation @Aspect을 보고 Weaving시켜줌
@Aspect
@Order(0) //낮은게 먼저 적용 수행. Proxy 적용은 나중에 순서 제어. 
public class LoggingAspect {
	//LoggAspect를 Bean등록하고 Weaver가 @Aspect Annotation을 보고 인식해서 Pointcut(execution())을 찾아서 Proxy를 끼워넣어 수행
	//모든 Signature가 다 들어가야 *(Returntype) *..(모든Package)*MemberDao.*(Class.모든Method) (..)모든Parameter
	//@Pointcut("execution(public * *..*MemberDao.*(..))")
	@Pointcut("execution(public * com.webapp.dao.*MemberDao.*(..)) || " +
			  "execution(public * com.webapp.service.*Service.*(..))")
	void pointcut() { //Pointcut:Weaving되는 대상을 Filtering
		
	}
	
	@Pointcut("execution(public * com.webapp.service.*Service.*(..))")
	void service() { //Pointcut:Weaving되는 대상을 Filtering
		
	}
	
	@Before("pointcut()")
	void before(JoinPoint jp) { //BeforeAdvice, JoinPoint: before()가 수행될때의 Method
		//Log log = LogFactory.getLog("Aspect"); //Target Class를 Parameter로 넘겨줘야
		Log log = LogFactory.getLog(jp.getTarget().getClass());
		//log.info("before advice...");
		//log.info("### before " + jp.getSignature().toLongString());
		//log.info("### before " + jp.getSignature().toShortString());
		String msg = "### before " + jp.getSignature().getName() + "(" + Arrays.toString(jp.getArgs()) + ")" + " START";
		log.info(msg);
	}
	
	@After("pointcut()")
	void after(JoinPoint jp) {
		Log log = LogFactory.getLog(jp.getTarget().getClass());
		//log.info("after advice..."); log는 trace 목적
		//log.info("### after " + jp.getSignature().toLongString());
		//log.info("### after " + jp.getSignature().toShortString());
		//log.info("### after " + jp.getSignature().getName() + "() END");
		String msg = "### after " + jp.getSignature().getName() + "(" + Arrays.toString(jp.getArgs()) + ")" + " END";
		log.info(msg);
		
	}
	
	
}
