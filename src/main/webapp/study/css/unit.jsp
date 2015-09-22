<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>unit.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf"%>
<style type="text/css">
	.box {
		margin: 20pt;
		background-color: red;
		height: 40pt;
		display: block;
	}
	.p {
		font-size: 10pt;
		
	}
</style>
</head>
<body>
<h1>unit </h1>
<h2>
% : 															<br>
px : pixel	상대단위 dpi(density per inch)에따라 달라짐			<br>
inch : 1 inch = 2.54cm = 25.4mm									<br>
cm : 1 cm = 10 mm												<br>
mm :															<br>
pt : 1 point = 1 / 72 inch										<br>
pc : 1 pc = 12pt (pica)											<br>
em : 1 em = 현재 폰트크기의 1배수(x2)							<br>
ex : 1 ex = 현재 폰트의 1/2배수(x0.5)							<br>


</h2>
<div class="box" style="width: 1in">box1 1in</div>
<div class="box" style="width: 2.54cm">box2 2.54cm</div>
<div class="box" style="width: 25.4mm">box3 25.4mm</div>
<div class="box" style="width: 72pt">box4 72pt</div>
<div class="box" style="width: 2em">box5 2em</div>
<div class="box" style="width: 2ex">box6 2ex</div>
<div class="box" style="width: 96px">box7 100px</div>

<p>Hello Units DEFAULT</p>
<p style="font-size: 1em;">Hello Units 1em</p>
<p style="font-size: 2em;">Hello Units 2em</p>
<p style="font-size: 1ex;">Hello Units 1ex</p>
<p style="font-size: 2ex;">Hello Units 2ex</p>

</body>
</html>