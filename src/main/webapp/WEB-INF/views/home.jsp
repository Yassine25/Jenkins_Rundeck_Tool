<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@page session="true"%>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Google Chart - Servlet 3</title>
<script src="<c:url value="/resources/scripts/jsapi.js" />"></script>
<script src="<c:url value="/resources/scripts/script.js" />"></script>
<link href="<c:url value="/resources/scripts/bootstrap.min.css" /> rel="stylesheet">
<link href="<c:url value="/resources/scripts/bootstrap-theme.css" />" rel="stylesheet">
<link href="<c:url value="/resources/scripts/bootstrap.css" />" rel="stylesheet">
<link href="<c:url value="/resources/scripts/bootstrap-theme.min.css" />" rel="stylesheet">
<link href="<c:url value="/resources/scripts/style.css" />" rel="stylesheet">
<script src="http://code.jquery.com/jquery-latest.min.js" type="text/javascript"></script>

<script type="text/javascript">

google.load("visualization", "1.1", {packages:["bar"]});
google.setOnLoadCallback(drawChart);
google.setOnLoadCallback(barChart);
google.setOnLoadCallback(deploymentContinuousChart);
google.setOnLoadCallback(deploymentReleaseChart);
    
    function drawChart() {
    	var data = google.visualization.arrayToDataTable(
        		[['', ''],
                <c:forEach items="${releaseReleases}" var="entry">
                ['${entry.key}', ${entry.value}],
                </c:forEach>]);
    	
    	var options = {
                width: 650,
                height:500,
                colors: ['orange'],
                is3D : true,
                bars:'horizontal',
                
                series: {
                  0: { axis: 'distance' },
                  1: { axis: 'brightness' },
                  
                },
                axes: {
                  x: {
                    distance: {label: 'minutes'},
                    brightness: {side: 'top', label: 'apparent magnitude'},
                    
                  }
                }
              };
        var chart = new google.charts.Bar(document.getElementById('releaseReleases_div'));
        chart.draw(data, options);
    }
    
    function barChart() {
    	var data = google.visualization.arrayToDataTable(
        		[['', ''],
                <c:forEach items="${releaseContinuous}" var="entry">
                ['${entry.key}', ${entry.value}],
                </c:forEach>]);
        
        var options = {
                width: 650,
                height:500,
                bars:'horizontal',
                colors: ['orange','black'],
                
                series: {
                  0: { axis: 'distance' },
                  1: { axis: 'brightness' }
                },
                axes: {
                  x: {
                    distance: {label: 'minutes'},
                    brightness: {side: 'top', label: 'apparent magnitude'}
                  }
                }
                
              };
        var chart = new google.charts.Bar(document.getElementById('releaseContinuous_div'));
        chart.draw(data, options);
    }
    
    function deploymentContinuousChart() {
    	var data = google.visualization.arrayToDataTable(
        		[['', ''],
                <c:forEach items="${deploymentReleases}" var="entry">
                ['${entry.key}', ${entry.value}],
                </c:forEach>]);
        
        var options = {
                width: 650,
                height:500,
                bars:'horizontal',
                colors: ['orange','black'],
                
                series: {
                  0: { axis: 'distance' },
                  1: { axis: 'brightness' }
                },
                axes: {
                  x: {
                    distance: {label: 'minutes'},
                    brightness: {side: 'top', label: 'apparent magnitude'}
                  }
                }
                
              };
        var chart = new google.charts.Bar(document.getElementById('deploymentReleases_div'));
        chart.draw(data, options);
    }
    
    function deploymentReleaseChart() {
    	var data = google.visualization.arrayToDataTable(
        		[['', ''],
                <c:forEach items="${deploymentContinuous}" var="entry">
                ['${entry.key}', ${entry.value}],
                </c:forEach>]);
        
        var options = {
                width: 650,
                height:500,
                bars:'horizontal',
                colors: ['orange','black'],
                
                series: {
                  0: { axis: 'distance' },
                  1: { axis: 'brightness' }
                },
                axes: {
                  x: {
                    distance: {label: 'minutes'},
                    brightness: {side: 'top', label: 'apparent magnitude'}
                  }
                }
                
              };
        var chart = new google.charts.Bar(document.getElementById('deploymentContinuous_div'));
        chart.draw(data, options);
    }
</script>
<script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>

</head>

<body class="home">

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
					<li class="active"><a href="<c:url value="/" />">HOME</a></li>
					<li><a href="<c:url value="/customers" />">CUSTOMERS</a></li>
					<li><a href="<c:url value="/jobs" />">JOBS</a></li>
					
					<li><a href="javascript:formSubmit()"> LOGOUT</a></li>
				</ul>
			</div>
		</div>
	</div>

    <div class="block" id=div_statisticsChart>

		<div id="deploymentChart" class="statisticsChart chart">
			<h2>deployments</h2>


			<div class="chart fliep">
				<div id="deploymentContinuous_div"></div>
				<h3>continuous deployment</h3>
			</div>

			<div class="chart">
				<div id="deploymentReleases_div"></div>
				<h3>release deployment</h3>
			</div>
		</div>
	</div>
    


	<div class="block" id=div_statisticsChart> 
		<div id="releaseChart" class="statisticsChart chart">

			<h2>releases</h2>
			<div class="chart fliep">
				<div id="releaseContinuous_div"></div>
				<h3>continuous build</h3>
			</div>
			<div class="chart">

				<div id="releaseReleases_div"></div>
				<h3>release build</h3>

			</div>

		</div>
	</div>
	



</body>
</html>