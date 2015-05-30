<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@page session="true"%>

<html>
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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
</head>

<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>



<body class="jobsPage">

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
					<li><a href="<c:url value="/customers" />">CUSTOMERS</a></li>
					<li class="active"><a href="<c:url value="/jobs" />">JOBS</a></li>
					
					<li><a href="javascript:formSubmit()">LOGOUT</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div id="listboxes">
		<div id="listboxCustomer">
			<div class="h2_Customer">
				<h2>Add Customer</h2>
			</div>
			<div class=classListboxCustomer>

				<form:form action="AddCustomer" method="post">
					<div class = "listboxCustomerDivs">NAME CUSTOMER</div>
                     
					<div class = "listboxCustomerDivs">
						<input type="text" name="nameCustomer" />
					</div>

					<div>
						<input type="submit" name="button" class="btn btn-info"
							value="ADD CUSTOMER">
					</div>
				</form:form>
			</div>
		</div>




		<div id=listboxItems>
			<div class="h2_jobs">
				<h2>Add Jobs to customer</h2>
			</div>
			<div class=classListboxItems>

				<form:form method="post" action="AddJobsToCustomer"
					modelAttribute="multimodel">
					<div class="listboxCustomers">
						<h3>select customer</h3>
						<form:select path="customers">
							<form:options items="${customers}" />
						</form:select>
					</div>

					<div class="listboxDeployments">
						<h3>select deployments</h3>
						<form:select path="deployments" class="form-control"
							multiple="true" size="10">
							<form:options items="${deployments}" />
						</form:select>
					</div>
					<form:errors path="${error}" />

					<div class="listboxReleases">
						<h3>select releases</h3>
						<form:select path="options" class="form-control" multiple="true"
							size="10">
							<form:options items="${options}" />
						</form:select>
					</div>
					<form:errors path="${error}" />

					<div class="button">
						<input type="submit" name="button" class="btn btn-info"
							value="ADD JOBS">
					</div>
				</form:form>
			</div>
		</div>
	</div>

</body>
</html>