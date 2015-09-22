<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boxmodel.jsp</title>
<%-- <%@ include file="/WEB-INF/views/common.jspf"%> framework이 초기화하면서 default CSS를 바꿈 --%>
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
</style>
</head>
<body>
<h1>boxmodel</h1>

<div class="box border padding">box1</div> <!-- box sizing 속성 = box width + padding + border -->
<div class="box">box2</div>
<div class="box border padding boxsizing">box3</div> <!-- framework에서는 boxsizing으로 바꿈 -->

</body>
</html>