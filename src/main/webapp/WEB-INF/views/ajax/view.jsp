<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>view.jsp</title>
<%@ include file="/WEB-INF/views/common.jspf"%>
<style type="text/css">
	@keyframes rank {
		from {
			transform : rotateX(180deg);
		}
		to {
			transform : rotateX(0deg);
		}
	}
	.rank {
		animation: rank 1s;
	}

</style>
<script type="text/javascript">
/* jQuery */
$(document).ready(function() {
	$('h1').click(function() {
		$.getJSON('member', function(data) {
			console.log(data);
			$('#memberlist').html('');
			
			$.each(data, function(index, m) {
				console.log(m.name + " " + m.email);
				//$('#memberlist').append('<li class="lank">' + m.name + '</li>');
				$('<li class="rank">' + m.name + '</li>').css('animation-delay', index + 's')
														 .appendTo('#memberlist');
			});
		});
	});
	
	$('#memberlist').dblclick(function() {
		$(this).html('');
	});
});

</script>
</head>
<body>
<h1>view</h1>

<ol id="memberlist">

</ol>

</body>
</html>