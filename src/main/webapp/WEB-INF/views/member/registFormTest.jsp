<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- WEB Browser가 rendering할 때 css style sheet을 참조. 읽어내려가면서 DOM객체 작성. property는 마지막것으로 override -->
<!-- 적용순서:tag->각tag의 style  -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>registForm.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf"%>
<style type="text/css">
	/* .class    */
	.fade {
		opacity: 0.7;	
		border-radius: 10px;
	}
	
	form {
		width: 400px;
		border-top: 1px double red;
		border-bottom: 1px double red;
	}
	/* CSS Block - Property ending with ;semicolon   */
	
	h1 {
		text-align: center;
		text-shadow: 5px 5px 1px grey;
	}
	
	/* #member => #id   */
	#member {
		margin: auto auto;
		
	}
	
	.none {
		display: none;
	}
	
	.block {
		display: block;
	}

</style>

</head>
<body>
<button id="xxx1">display=none</button>
<button id="xxx2">display=block</button>
<script type="text/javascript">
	$('#xxx1').click(function() {
		$('h1').addClass('none')
			   .removeClass('block');
	});
	$('#xxx2').click(function() {
		$('h1').addClass('block')
			   .removeClass('none');
	});

</script>

<h1><spring:message code="member.regist.title"/></h1>

<form:form cssClass="fade" commandName="member" action="regist" method="post">
<!-- Email -->
	<div class="form-group">
		<label for="email"><spring:message code="member.regist.email"/></label>
		<form:input path="email" cssClass="form-control"/>
		<form:errors path="email"/>
	</div>
<!-- Password -->
	<div class="form-group">
		<label for="password"><spring:message code="member.regist.password"/></label>
		<form:password path="password" cssClass="form-control"/>
		<form:errors path="password"/>
	</div>
<!-- Name -->
	<div class="form-group">
		<label for="name"><spring:message code="member.regist.name"/></label>
		<form:input path="name" cssClass="form-control"/>
		<form:errors path="name"/>
	</div>
<input type="submit" value="회원가입"/>
</form:form>

</body>
</html>