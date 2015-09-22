<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf"%>
<style type="text/css">

</style>
</head>
<body>
<h1>Login</h1>
<!-- Email, Password 입력받아야 - Error연동을 위해 Spring Custom Tag 써야 -->
<form:form commandName="login" action="login" method="post" cssClass="w3-container"> <!-- form:form은 Command객체를 읽어야, action="login"은 상대경로 -->
	
	<form:errors element="div"/>
	
	<!-- Email -->
	<div class="w3-group">
		<form:input path="email" cssClass="w3-input" required="required"/>
		<label for="email" class="w3-label">Email</label>
		<!-- <script type="text/javascript">
			$('#email').attr("required", "required");
		</script> -->
	</div>
	<!-- Password -->
	<div class="w3-group">
		<form:password path="password" cssClass="w3-input" required="required"/>
		<label for="password" class="w3-label">Password</label>
		<!-- <script type="text/javascript">
			$('#password').attr("required", "required");
		</script> -->
	</div>
	<!-- rememberID -->
	<div>
		<label for="rememberID" class="w3-checkbox">
		<c:choose>
			<c:when test="${login.rememberID eq true}">
				<input id="rememberID" name="rememberID"   type="checkbox" value="true" checked="checked"/>
			</c:when>
			<c:otherwise>
			<input id="rememberID" name="rememberID"   type="checkbox" value="true"/>
			</c:otherwise>
		</c:choose>		
		<span class="w3-checkmark"></span>
		RememberID
		</label>
	</div>
	<input class="w3-btn"  type="submit" value="Login"/>


</form:form>
</body>
</html>