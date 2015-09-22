<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>image.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf"%>
<style type="text/css">
	span, img {
		border: 2px solid red;
		height: 200px;
		width: 200px;
		margin: 20px;
		float: right;
		/* display: inline-block; */
	}

</style>
</head>
<body>
<h1>display: none | inline | block | inline-block</h1>

<span>display span inline</span>
<img style="vertical-align: bottom;" alt="100x100" src="http://www.placehold.it/100x100">
<p>
9월 09, 2015 9:24:03 오전 org.apache.tomcat.util.digester.SetPropertiesRule begin
경고: [SetPropertiesRule]{Server/Service/Engine/Host/Context} Setting property 'source' to 'org.eclipse.jst.jee.server:Member' did not find a matching property.
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.VersionLoggerListener log
정보: Server version:        Apache Tomcat/8.0.23
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.VersionLoggerListener log
정보: Server built:          May 19 2015 14:58:38 UTC
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.VersionLoggerListener log
정보: Server number:         8.0.23.0
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.VersionLoggerListener log
정보: OS Name:               Windows 7
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.VersionLoggerListener log
정보: OS Version:            6.1
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.VersionLoggerListener log
정보: Architecture:          x86
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.VersionLoggerListener log
정보: Java Home:             C:\Program Files\Java\jdk1.7.0_76\jre
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.VersionLoggerListener log
정보: JVM Version:           1.7.0_76-b13
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.VersionLoggerListener log
정보: JVM Vendor:            Oracle Corporation
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.VersionLoggerListener log
정보: CATALINA_BASE:         C:\03_Src\spring\.metadata\.plugins\org.eclipse.wst.server.core\tmp0
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.VersionLoggerListener log
정보: CATALINA_HOME:         C:\Program Files\Apache Software Foundation\Tomcat 8.0
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.VersionLoggerListener log
정보: Command line argument: -Dcatalina.base=C:\03_Src\spring\.metadata\.plugins\org.eclipse.wst.server.core\tmp0
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.VersionLoggerListener log
정보: Command line argument: -Dcatalina.home=C:\Program Files\Apache Software Foundation\Tomcat 8.0
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.VersionLoggerListener log
정보: Command line argument: -Dwtp.deploy=C:\03_Src\spring\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.VersionLoggerListener log
정보: Command line argument: -Djava.endorsed.dirs=C:\Program Files\Apache Software Foundation\Tomcat 8.0\endorsed
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.VersionLoggerListener log
정보: Command line argument: -Dfile.encoding=UTF-8
9월 09, 2015 9:24:03 오전 org.apache.catalina.core.AprLifecycleListener lifecycleEvent
정보: The APR based Apache Tomcat Native library which allows optimal performance in production environments was not found on the java.library.path: C:\Program Files\Java\jdk1.7.0_76\bin;C:\Windows\Sun\Java\bin;C:\Windows\system32;C:\Windows;C:/Program Files/Java/jdk1.7.0_76/bin/../jre/bin/client;C:/Program Files/Java/jdk1.7.0_76/bin/../jre/bin;C:/Program Files/Java/jdk1.7.0_76/bin/../jre/lib/i386;C:\oraclexe\app\oracle\product\11.2.0\server\bin;C:\Program Files\Java\jdk1.7.0_76\bin;C:\Program Files\Wizvera\Delfino;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;C:\Windows\System32\WindowsPowerShell\v1.0\;C:\Program Files\apache-maven-3.2.5\bin;C:\Program Files\Apache Software Foundation\Tomcat 8.0\bin;C:\Android\android-sdk\platform-tools;C:\Android\android-sdk\tools;C:\Program Files\MySQL\MySQL Server 5.6\bin;C:\Program Files\MySQL\MySQL Utilities 1.3.6\;C:\Program Files\nodejs\;C:\Users\Administrator\AppData\Roaming\npm;C:\Program Files\eclipse;;.
9월 09, 2015 9:24:03 오전 org.apache.coyote.AbstractProtocol init
정보: Initializing ProtocolHandler ["http-nio-8080"]
9월 09, 2015 9:24:03 오전 org.apache.tomcat.util.net.NioSelectorPool getSharedSelector
정보: Using a shared selector for servlet write/read
9월 09, 2015 9:24:03 오전 org.apache.coyote.AbstractProtocol init
정보: Initializing ProtocolHandler ["ajp-nio-8089"]
9월 09, 2015 9:24:03 오전 org.apache.tomcat.util.net.NioSelectorPool getSharedSelector
정보: Using a shared selector for servlet write/read
9월 09, 2015 9:24:03 오전 org.apache.catalina.startup.Catalina load
정보: Initialization processed in 784 ms
9월 09, 2015 9:24:03 오전 org.apache.catalina.core.StandardService startInternal
정보: Starting service Catalina
9월 09, 2015 9:24:03 오전 org.apache.catalina.core.StandardEngine startInternal
정보: Starting Servlet Engine: Apache Tomcat/8.0.23
9월 09, 2015 9:24:04 오전 org.apache.catalina.util.SessionIdGeneratorBase createSecureRandom
정보: Creation of SecureRandom instance for session ID generation using [SHA1PRNG] took [187] milliseconds.
9월 09, 2015 9:24:04 오전 org.apache.catalina.core.ApplicationContext log
정보: default: DefaultServlet.init:  input buffer size=2048, output buffer size=2048
9월 09, 2015 9:24:07 오전 org.apache.jasper.servlet.TldScanner scanJars
정보: At least one JAR was scanned for TLDs yet contained no TLDs. Enable debug logging for this logger for a complete list of JARs that were scanned but no TLDs were found in them. Skipping unneeded JARs during scanning can improve startup time and JSP compilation time.
9월 09, 2015 9:24:07 오전 org.apache.catalina.core.ApplicationContext log
정보: No Spring WebApplicationInitializer types detected on classpath
9월 09, 2015 9:24:07 오전 org.apache.catalina.core.ApplicationContext log
정보: Initializing Spring root WebApplicationContext
2015-09-09 09:24:07 INFO  org.springframework.web.context.ContextLoader:307 - Root WebApplicationContext: initialization started
2015-09-09 09:24:07 INFO  org.springframework.web.context.support.XmlWebApplicationContext:573 - Refreshing Root WebApplicationContext: startup date [Wed Sep 09 09:24:07 KST 2015]; root of context hierarchy
2015-09-09 09:24:07 INFO  org.springframework.beans.factory.xml.XmlBeanDefinitionReader:317 - Loading XML bean definitions from class path resource [spring/biz/beans_dao.xml]
2015-09-09 09:24:07 INFO  org.springframework.beans.factory.xml.XmlBeanDefinitionReader:317 - Loading XML bean definitions from class path resource [spring/biz/beans_oracle.xml]
2015-09-09 09:24:07 INFO  org.springframework.beans.factory.xml.XmlBeanDefinitionReader:317 - Loading XML bean definitions from class path resource [spring/biz/beans_mysql.xml]
2015-09-09 09:24:07 INFO  org.springframework.beans.factory.xml.XmlBeanDefinitionReader:317 - Loading XML bean definitions from class path resource [spring/biz/beans_service_spring.xml]
2015-09-09 09:24:07 INFO  org.springframework.beans.factory.xml.XmlBeanDefinitionReader:317 - Loading XML bean definitions from class path resource [spring/biz/beans_service_mybatis.xml]
2015-09-09 09:24:10 INFO  com.webapp.dao.SpringMemberDao:64 - DB_VENDOR = MySQL
2015-09-09 09:24:10 INFO  com.webapp.dao.SpringMemberDao:46 - useGeneratorTable = false
2015-09-09 09:24:10 INFO  com.webapp.dao.MybatisMemberDao:34 - useGeneratorTable = false
9월 09, 2015 9:24:10 오전 org.apache.catalina.core.ApplicationContext log
정보: default: DefaultServlet.init:  input buffer size=2048, output buffer size=2048
2015-09-09 09:24:10 INFO  org.springframework.web.context.ContextLoader:347 - Root WebApplicationContext: initialization completed in 3341 ms
2015-09-09 09:24:10 INFO  org.springframework.web.servlet.DispatcherServlet:488 - FrameworkServlet 'springDispatcherServlet': initialization started
9월 09, 2015 9:24:10 오전 org.apache.catalina.core.ApplicationContext log
정보: Initializing Spring FrameworkServlet 'springDispatcherServlet'
2015-09-09 09:24:10 INFO  org.springframework.web.context.support.XmlWebApplicationContext:573 - Refreshing WebApplicationContext for namespace 'springDispatcherServlet-servlet': startup date [Wed Sep 09 09:24:10 KST 2015]; parent: Root WebApplicationContext
2015-09-09 09:24:10 INFO  org.springframework.beans.factory.xml.XmlBeanDefinitionReader:317 - Loading XML bean definitions from class path resource [spring/mvc/beans_mvc.xml]
2015-09-09 09:24:10 INFO  org.springframework.beans.factory.xml.XmlBeanDefinitionReader:317 - Loading XML bean definitions from class path resource [spring/mvc/beans_controller.xml]
2015-09-09 09:24:11 INFO  org.springframework.web.servlet.handler.SimpleUrlHandlerMapping:341 - Mapped URL path [/**] onto handler 'org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler#0'
2015-09-09 09:24:11 INFO  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping:532 - Mapped "{[/locale/{language:[a-z]{2}}]}" onto public void com.webapp.controller.LocaleController.setLocale(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,java.io.PrintWriter,java.lang.String)
2015-09-09 09:24:11 INFO  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping:532 - Mapped "{[/member/regist],methods=[GET]}" onto public java.lang.String com.webapp.controller.MemberRegisterController.registForm()
2015-09-09 09:24:11 INFO  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping:532 - Mapped "{[/member/regist],methods=[POST]}" onto public java.lang.String com.webapp.controller.MemberRegisterController.regist(com.webapp.command.MemberCommand)
2015-09-09 09:24:11 INFO  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter:532 - Looking for @ControllerAdvice: WebApplicationContext for namespace 'springDispatcherServlet-servlet': startup date [Wed Sep 09 09:24:10 KST 2015]; parent: Root WebApplicationContext
2015-09-09 09:24:11 INFO  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter:532 - Looking for @ControllerAdvice: WebApplicationContext for namespace 'springDispatcherServlet-servlet': startup date [Wed Sep 09 09:24:10 KST 2015]; parent: Root WebApplicationContext
2015-09-09 09:24:11 INFO  org.springframework.web.servlet.handler.SimpleUrlHandlerMapping:341 - Mapped URL path [/home] onto handler of type [class org.springframework.web.servlet.mvc.ParameterizableViewController]
2015-09-09 09:24:11 INFO  org.springframework.web.servlet.handler.SimpleUrlHandlerMapping:341 - Mapped URL path [/img/**] onto handler 'org.springframework.web.servlet.resource.ResourceHttpRequestHandler#0'
2015-09-09 09:24:11 INFO  org.springframework.web.servlet.handler.SimpleUrlHandlerMapping:341 - Mapped URL path [/js/**] onto handler 'org.springframework.web.servlet.resource.ResourceHttpRequestHandler#1'
2015-09-09 09:24:11 INFO  org.springframework.web.servlet.handler.SimpleUrlHandlerMapping:341 - Mapped URL path [/css/**] onto handler 'org.springframework.web.servlet.resource.ResourceHttpRequestHandler#2'
2015-09-09 09:24:12 INFO  org.springframework.web.servlet.DispatcherServlet:507 - FrameworkServlet 'springDispatcherServlet': initialization completed in 1296 ms
9월 09, 2015 9:24:12 오전 org.apache.coyote.AbstractProtocol start
정보: Starting ProtocolHandler ["http-nio-8080"]
9월 09, 2015 9:24:12 오전 org.apache.coyote.AbstractProtocol start
정보: Starting ProtocolHandler ["ajp-nio-8089"]
9월 09, 2015 9:24:12 오전 org.apache.catalina.startup.Catalina start
정보: Server startup in 8436 ms
9월 09, 2015 9:24:12 오전 org.apache.catalina.core.ApplicationContext log
정보: default: DefaultServlet.serveResource:  Serving resource '/' headers and data
9월 09, 2015 9:45:05 오전 org.apache.catalina.core.ApplicationContext log
정보: default: DefaultServlet.serveResource:  Serving resource '/favicon.ico' headers and data
9월 09, 2015 9:45:06 오전 org.apache.catalina.core.ApplicationContext log
정보: default: DefaultServlet.serveResource:  Serving resource '/favicon.ico' headers and data

</p>

</body>
</html>