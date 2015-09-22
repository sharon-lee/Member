<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>animation.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf"%> 
<%-- framework이 초기화하면서 default CSS를 바꿈 --%>
<style type="text/css">
 	.box {
 		width: 100px;
 		height: 100px;
 		background-color: orange;
 		display: inline-block; /* box default display=block */
 		vertical-align: bottom; /* top */
 	}
 	
 	.border {
 		border: 10px solid red;
 	}
 	
 	.padding {
 		padding: 10px;
 	}
 	
 	.boxsizing {
 		box-sizing: border-box; /* CSS3 default: content-box */
 	}
 	
 	.box1_animation {
 		animation: box1 10s infinite;
 	}
 	
 	.box2_animation {
 		animation: box2 10s infinite;
 	}
 	
 	
 	/* keyframe전환 % %시간*/
 	@keyframes box1 {
 		0% { 
 			width: 50px;
 			transform: scale(0.5, 0.5) rotateY(90deg); /* 0.5배수 */
 			background-color: purple;
 		}
 		50% {
 			width: 100px;
 			transform: scale(1, 1) rotateY(180deg); /* 1배수 */
 			background-color: yellow;
 		}
 		100% {
 			width: 200px;
 			transform: scale(2, 2) rotateY(360deg); /* 2배수 */
 			background-color: #77ff77;
 		}
 	}
 	
 	@keyframes box2 {
 		0% { 
 			width: 200px;
 			transform: scale(2, 2) rotate(90deg); /* 0.5배수 */
 			background-color: #77ff77;
 		}
 		50% {
 			width: 100px;
 			transform: scale(1, 1) rotate(180deg); /* 1배수 */
 			background-color: violet;
 		}
 		100% {
 			width: 200px;
 			transform: scale(2, 2) rotate(360deg); /* 2배수 */
 			background-color: purple;
 		}
 	}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$('div').eq(0).on('mouseover', function() {
			/*  alert('mouseover'); */
			$(this).addClass('box1_animation');
		});
		$('div').eq(0).on('mouseout', function() {
			/* alert('mouseout'); */
			$(this).removeClass('box1_animation');
		});
	});
	$(document).ready(function() {
		$('div').eq(1).on('mouseout', function() {
			$(this).addClass('box2_animation');
		});
		$('div').eq(1).on('mouseover', function() {
			$(this).addClass('box2_animation');
		});
	});

</script>
</head>
<body>
<h1> CSS3 animation</h1>

<div class="box border padding">box1</div> <!-- box sizing 속성 = box width + padding + border -->
<div class="box">box2</div>
<div class="box border padding boxsizing">box3</div> <!-- framework에서는 boxsizing으로 바꿈 -->

</body>
</html>