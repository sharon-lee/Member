<%@tag import="java.io.File"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag body-content="empty"%>
<%@ attribute name="var" required="true" rtexprvalue="false"%>
<%@ attribute name="path" required="true"%>
<!-- EL변수는 variable로  -->
<%@ variable alias="filevar" 
			 name-from-attribute="var" 
			 variable-class="java.io.File[]" 
			 scope="AT_END" 
%>

<%
	File[] files = new File(path).listFiles();
	jspContext.setAttribute("filevar", files);
%>