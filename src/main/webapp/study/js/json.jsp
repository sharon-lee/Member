<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>json.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf"%>
<style type="text/css"> /* CSS 표기방법 */
	p {
/* 		position : relative; */
/* 		transition : left 1s;*/ /* left값이 변경될때 1초만 적용 */
 		transition : transform 1s; 
/* 		transition : background-color 1s; */
	}
</style>

<script type="text/javascript">
	//XML
	//JSON (Javascript Object Notation) - Javascript를 데이타전송시에 사용(XML). 
	//Javascript Literal - 객체 표기 방법 (Member Property)
	/* 
	var member = {
		email : "xxx@webapp.com",
		password : "1234",
		name : "홍길동",
		gender : "female",
		hobby : ["programming", "reading"],
		comment : "열공하세요", 
		reception : true
	};
	 */
	//event는 jQuery로 anonymous function()은 집합으로 동작
	$(document).ready(function() {
		$('button').on("click", function() {
			//click시에 Server에 있는 json을 가져와서 Display
			//jQuery Ajax (Asynchronous JavaScript and XML)
			$.getJSON("member.json", function(member) { // $. 전역함수 
				console.log(member);
				var message = 	"email = " + member.email + "<br>   " +
								"name = " + member.name + "<br>   " +
								"password = " + member.password + "<br>   " +
								"gender = " + member.gender + "<br>   " +
								"hobby = " + member.hobby + "<br>   " +
								"reception = " + member.reception;
				//$('p').text(message); jQuery(특징)는 항상 집합으로 동작. text와 html의 동작 차이, 기존것은 Override
				//$('p').html(message);
				//$('p').append(message);
				$('p').prepend(message);
				
				//jQuery에서 배열 집합에 대한 처리 - 전역함수 $.each == 향상된 for문솨 비슷
				$.each(member.hobby, function(index, value) {
					console.log("hobby[" + index + "] = " + member.hobby[index]);//Array index		
					console.log("hobby[" + index + "] = " + value);
				});
				
				for (var i = 0; i < member.hobby.length; i++) {
					console.log("hobby[" + i + "] = " + member.hobby[i]);
				}
			});
		});	
		
		$('p').on("click", function() {
// 			$(this).css("left", "100px"); //우측으로 이동
 			$(this).css("transform", "translate(50px,50px)");
// 			$(this).css("background-color", "red");
		});
	});
	
</script>
</head>
<body>
<h1>json.jsp</h1>
<button>member print1</button>
<button>member print2</button>
<p>
	print1		
</p>
<p>
	print2		
</p>


</body>
</html>