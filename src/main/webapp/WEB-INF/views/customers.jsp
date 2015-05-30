<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@page session="true"%>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Google Chart - Servlet 3</title>
<script src="<c:url value="/resources/scripts/jsapi.js" />"></script>
<link href="<c:url value="/resources/scripts/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/scripts/bootstrap-theme.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/scripts/bootstrap.css" />"
	rel="stylesheet">
<link
	href="<c:url value="/resources/scripts/bootstrap-theme.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/scripts/style.css" />"
	rel="stylesheet">
<body>

<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
	
	<c:url value="/j_spring_security_logout" var="logoutUrl" />
	<form action="${logoutUrl}" method="post" id="logoutForm">
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	

	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target=".navbar-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
				    <li><a href="<c:url value="/home" />">HOME</a></li>
					<li class="active"><a href="<c:url value="/customers" />">CUSTOMERS</a></li>
					<li><a href="<c:url value="/jobs" />">JOBS</a></li>
					
					<li><a href="<c:url value="javascript:formSubmit()"/>">LOG OUT</a></li>
				</ul>
			</div>
		</div>
	</div>



	<div id = "pageCustomers" class="block">
		<h3>CUSTOMERS</h3>
		<ul class="client-list">


			<c:forEach items="${customers}" var="entry">

				<li><a
					href="<c:url value="/ShowCustomerStatistics?id=${entry}"/>">${entry}</a></li>

			</c:forEach>
		</ul>
	</div>






</body>
</html>