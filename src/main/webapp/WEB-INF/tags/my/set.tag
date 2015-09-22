<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag body-content="empty"%>
<%@ attribute name="var" required="true" rtexprvalue="false"%>
<%@ attribute name="value" required="true" type="java.lang.Object"%>
<%@ variable name-from-attribute="var" 
			 alias="setvar" 
			 variable-class="java.lang.Object" 
			 scope="AT_END"
			 %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <c:set var="setvar" value="${value}"/> --%>
			 
<!-- type="java.lang.Boolean"은 안주면 default로 String -->
<!-- tag영역에서는 pageContext의 직접적인 접근 불가: variable로 pageContext의 EL변수 접근 -->
<!-- rtexprvalue="false"이면 EL변수를 쓸수없음 -->

<%
	jspContext.setAttribute("setvar", value);
	

%>