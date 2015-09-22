<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="var" required="true"  rtexprvalue="false"%>
<%@ attribute name="begin" required="true" type="java.lang.Integer"%>
<%@ attribute name="end" required="true" type="java.lang.Integer"%>
<%@ variable name-from-attribute="var" 
			 alias="forvar" 
			 variable-class="java.lang.Integer" 
			 scope="NESTED"
			 %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!-- for (int i = 0; i < array.length; i++) { -->
		
<!-- 	}	 -->
    	
<c:set var="forvar" value="${begin}"/>
${begin}<br>
${end}<br>
<%
//while (end - begin < 0) {
	begin++;
	out.println(end - begin);
	out.println(var=var+1);
%>
<jsp:doBody/>
<%

//}
%>
<%-- 	<jsp:doBody/> --%>
var=${var}<br>
var=${forvar}<br>
${var} = ${forvar }
<%
{}
%>

<%-- <c:forEach var="i" begin="0" end="10"> --%>
<%-- <c:forEach items="${pageScope.names}" --%>
<%--                var="currentName" --%>
<%--                varStatus="status" --%>
<%--                begin="0" --%>
<%--                end="3" --%>
<%--                step="2" --%>
<%--     > --%>