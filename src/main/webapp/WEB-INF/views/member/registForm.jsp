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
	form {
		width: 400px;
		border-top: 1px double red;
		border-bottom: 1px double red;
	}
	/* CSS Block - Property ending with ;semicolon   */
	
	.center { /* h1 tag아니라 class로 해야 */
		text-align: center;
		text-shadow: 5px 5px 1px grey;
	}
	
	/* #member => #id   */
	#member {
		margin: auto auto;
		
	}
	
	label[for^=gender], label[for^=hobby] {
		padding-left: 5px;
		padding-right: 10px;
		color: blue;	
	}
/* selector표현식 for^=gender ==== for*=gender,    for$=gender1  */
</style>

<!-- WEB Browser는 위에서 차례로 읽어내려가며 객체를 만들기 때문에 form이 만들어진후에 script를 작성해야 동작 가능 -->
<!-- 이전에 놓으려면 ReadyHandler Event를 만들어야 -->
<script type="text/javascript">
//ReadyHandler 준비Handler, Method Chain 방식
//jQuery함수의 CSS Selector함수를 사용
//Animation은 비동기식이므로 완료함수를 추가해야 
$(document).ready(function() { 
	$('form').slideToggle().slideToggle(1000)
			 .fadeOut(500).fadeIn(1000, function() {
				 $('label[for^=gender]').css('background-color', 'lightgreen')
				 						.fadeOut(1000).fadeIn(2000);
			});
	
	$('label[for^=gender]').css('backgroundColor', 'orange');
	$('label[for^=gender]').css('background-color', 'lightgreen');
});

</script>

</head>
<body>

<h1 class="center"><a href="regist"><spring:message code="member.regist.title"/></a></h1>

<form:form commandName="member" action="regist" method="post">
<!-- Global Error Display -->
<form:errors element="div"/>

<!-- Email -->
	<div class="form-group">
		<label for="email"><spring:message code="member.regist.email"/></label>
		<form:input path="email" cssClass="form-control"/>
		<form:errors path="email"/>
	</div>
<!-- Password -->
	<div class="form-group">
		<label for="password"><spring:message code="member.regist.password"/></label>
<%-- 		<form:password path="password" cssClass="form-control"/> --%>
<form:input path="password" cssClass="form-control"/>
		<form:errors path="password"/>
	</div>
<!-- Name -->
	<div class="form-group">
		<label for="name"><spring:message code="member.regist.name"/></label>
		<form:input path="name" cssClass="form-control"/>
		<form:errors path="name"/>
	</div>
<!-- Gender -->
	<div class="form-group">
		<div for="gender1" style="display: block;"><spring:message code="member.regist.gender"/></div>
		<form:radiobuttons items="${gender}" path="gender"/>
		<form:errors path="gender" />
	</div>
<!-- Gender -->
	<div class="form-group">
	<div><spring:message code="member.regist.hobby"/></div>
		<form:checkboxes items="${hobby}" path="hobby" itemLabel="label" itemValue="code"/>
		<form:errors path="hobby"/>
	</div>
<!-- Comment -->
	<div class="form_group">
		<div><spring:message code="member.regist.comment"/></div>
		<form:textarea path="comment" cssClass="form-control" rows="10"/>	
		<form:errors path="comment"/>
	</div>
<!-- Email Reception true/false -->
	<div class="form_group">
		<label for="reception1"><spring:message code="member.regist.reception"/></label>
		<form:checkbox path="reception"/>
		<form:errors path="reception"/>
	</div>	
<input type="submit" value="회원가입"/>
</form:form>



</body>
</html>