<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>position.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf"%>
<style type="text/css">
	.box {
		margin: 10px;
		width: 100px;
		height: 100px;
		background-color: rgb(10, 200, 100);
		transition-duration: 1s;
	}
	
	.moveright {
		top: 200px;
		left: 200px;		
	}
	
	.absolute {
		position: absolute;
	}
	
	.fixed {
		position: fixed;
	}
	
	.relative {
		position: relative;
	}
</style>
<script type="text/javascript">
$(document).ready(function() { /* 문서를 DOM으로 만든이후 호출되는 ready handler */
	$('.box').click(function() { /* DOM에서 box class로 만든 element만 find해서 click event를 달아 */
		//alert("box clicked... count = " + $('.box').size());
		$(this).css('left', '300px'); /* click event가 걸린 객체가 this로 표현 */
	});
});


</script>
</head>
<body>
<!-- position : element의 위치 결정 모든 element는 static 문서의 위치(부모의 원점)에 따라 정해진 위치,default는 static -->
<h1>position : static | relative | absolute | fixed </h1>
<!-- left right top bottom : position 속성에 따라 적용되는 CSS의 의미 달라짐 -->
<!-- responsive반응형 web : mobile기기 지원을 위해 화면의 width를 check해서 CSS display방식 결정 -->
<h2>left(x) right top(y) bottom </h2>

<div class="box moveright absolute">box1 absolute</div> <!-- Document기준으로 위치 결정 -->
<div class="box moveright fixed">box2 fixed</div> <!-- 화면기준으로 절대위치 결정 -->
<div class="box moveright">box3</div>
<div class="box moveright">box4</div>
<div class="box moveright relative">box4 relative</div> <!-- 원래 있던 static위치를 원점으로 상대적 위치로 결정 -->

<div class="w3-row">
	<div class="w3-col w3-half w3-green w3-padding">
		<img alt="" src="http://www.placehold.it/200x300" width="100%">
	</div>
	<div class="w3-col w3-half w3-blue w3-padding">
		<img alt="" src="http://www.placehold.it/200x400" width="100%">
	</div>
</div>
</body>
</html>