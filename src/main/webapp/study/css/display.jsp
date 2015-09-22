<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>display.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf"%>
<style type="text/css">
	span, div {
		border: 2px solid red;
		width: 100px;
		height: 100px;
/* 		margin: 50px 20px; */
	}

</style>
</head>
<body>
<h1>display: none | inline | block | inline-block</h1>
<!-- width height: display: display 속성에 따라 적용되는 CSS -->
<h2>width height </h2>
<span>inline1</span>
<!-- <span>inline-block</span> -->
<span style="display: inline-block; vertical-align: bottom">inline-block</span>
<span>inline3</span>
<div>block1</div>
<div>block2</div>
<!-- <div style="display: none;">block-none</div> -->
<div>block3</div>

</body>
</html>