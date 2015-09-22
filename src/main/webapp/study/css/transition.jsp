<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>transition.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf"%>
<style type="text/css">
	.box {
		border: 2px solid red;
		background-color: rgb(0,255,255);
		padding: 20px;
		width: 100px;
		height: 100px;
		transition: transform 1s, width 1s, height 1s, background-color 2s;
	}

	.box:hover {
		width: 400px;
		height: 400px;
		background-color: rgb(0, 0, 255);
		transform: rotate(180deg);
	}
</style>
</head>
<body>
<h1 class="box">transition : property 1s</h1>

<div class="box">
box
</div>


</body>
</html>