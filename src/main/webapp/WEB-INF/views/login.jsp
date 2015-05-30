<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="<c:url value="/resources/scripts/bootstrap.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/scripts/bootstrap-theme.css" />" rel="stylesheet">
<link href="<c:url value="/resources/scripts/bootstrap.css" />" rel="stylesheet">
<link href="<c:url value="/resources/scripts/bootstrap-theme.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/scripts/style.css" />" rel="stylesheet">
<%@page session="true"%>

<html>
<head>
<title>Login Page</title>

</head>
<body class = "bodyLogin" onload='document.loginForm.username.focus();'>
 
    <div id = "loginPage">
	<div id="login-box">
 
		<h2><span class="fontH2"></span>LOGIN</h2>
 
		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
 
		<form name='loginForm'
		  action="<c:url value='/j_spring_security_check' />" method='POST'>
          <fieldset>
		
				<p><label>USERNAME</label></p>
				<p><input class="inputLabel" type='text' name='username'></p>
			
				<p><label>PASSWORD</label></p>
				<p><input class="inputLabel" type='password' name='password' /></p>

				<p><input name="submit" class="btn btn-info"type="submit" 
				  value="LOGIN" /></p>
 
		  <input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
            
            </fieldset>
 
 
		</form>
	</div>
	</div>
 
</body>
</html>