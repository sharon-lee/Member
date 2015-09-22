<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>gridview.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf"%>
<style type="text/css">

</style>
</head>
<body>
<h1>gridview</h1>
<!-- 원리는 @media query - width의 %로 동작 -->
<section class="w3-row">
	<article class="w3-col m6 l4 s12 w3-red">article1</article>
	<article class="w3-col m6 l4 s6 w3-green">article2</article>
	<article class="w3-col m12 l4 s6 w3-blue">article3</article>
</section>
</body>
</html>