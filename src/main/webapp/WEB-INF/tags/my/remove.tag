<%@tag import="java.security.InvalidParameterException"%>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ tag body-content="empty"%>
<%@ attribute name="var" required="true" rtexprvalue="false"%>
<%@ attribute name="scope"%> <!-- scope 속성 부여 -->
<%@ variable name-from-attribute="var"  
 			 scope="AT_BEGIN" 
 			 alias="avar" 	 
 			 variable-class="java.lang.String" 
 %>
 <%-- scope="AT_END"이면 pageContext의 EL변수 remove 불가 --%>
<%-- avar: attribute var --%>
<!-- jspContext에서 pageContext는 접근 불가로 처리 불가 -->
<%
	//jspContext.removeAttribute("avar");
/* pageContext의 var를 remove하는 효과 == pageContext.removeAttribute(var); */
	//jspContext.setAttribute("avar", "xxxyyy");
	if(scope == null) {
		jspContext.removeAttribute("avar");
		request.removeAttribute(var);
		session.removeAttribute(var);
		application.removeAttribute(var);
	} else {
		/* pageContext에 복사되도록 처리 - avar가 지워지지 않도록 */

		jspContext.setAttribute("avar", jspContext.findAttribute("avar"));

		switch(scope) {
		case "page" :
			jspContext.removeAttribute("avar");
			break;
		case "request" :
			request.removeAttribute(var);
			break;
		case "session" :
			session.removeAttribute(var);
			break;
		case "application" :
			application.removeAttribute(var);
			break;
		default :
			throw new InvalidParameterException("scope attribute error...");
		}
	}

%>


