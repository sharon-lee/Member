<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="var" required="true"  rtexprvalue="false" type="java.lang.String"%>
<%@ attribute name="begin" required="true" rtexprvalue="true" type="java.lang.Integer"%>
<%@ attribute name="end" required="true" rtexprvalue="true" type="java.lang.Integer"%>
<%@ variable name-from-attribute="var" 
			 alias="avar" 
			 variable-class="java.lang.Integer" 
			 scope="NESTED"
%>

<%
for (int i=begin;i<=end;i++) {
	//out.println("i = " + i + "<br>");
	jspContext.setAttribute("avar", i);
	//jspContext.setAttribute("i", i);
%>
<jsp:doBody/>
<%
}
%>