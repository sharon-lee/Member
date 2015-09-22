<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag body-content="empty"%>
<%@ attribute name="test" required="true" type="java.lang.Boolean"%>
<%@ attribute name="cls"%>
<%-- <%@ attribute name="cls" type="java.lang.String" rtexprvalue="true"%> --%>
<%
	if (test) {
%>
<!-- 	<button class="btn btn-default">my:login Log Out</button> -->
		<button class="${cls}">my:login Log Out</button>
<%
	} else {
%>
<!-- 	<button class="btn btn-primary">my:login Log In</button> -->
		<button class="${cls} btn-primary">my:login Log In</button>
<%
	}
%>
