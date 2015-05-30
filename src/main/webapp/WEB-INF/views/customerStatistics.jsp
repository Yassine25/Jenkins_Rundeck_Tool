<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>


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

<script type="text/javascript">

google.load("visualization", "1.1", {packages:["bar"]});
google.setOnLoadCallback(drawChart);
google.setOnLoadCallback(releaseChart);
google.setOnLoadCallback(deploymentReleaseChart);
google.setOnLoadCallback(deploymentContinuousChart);
    
    
    function drawChart() {
    	var data = google.visualization.arrayToDataTable(
        		[['', 'minutes'],
                <c:forEach items="${releaseReleases}" var="entry">
                ['${entry.key}', ${entry.value}],
                </c:forEach>]);
    	
    	var options = {
                width: 323,
                height:400,
                colors: ['orange'],
                is3D : true,
                bars: 'vertical',
                
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
        var chart = new google.charts.Bar(document.getElementById('chart_div'));
        chart.draw(data, options);
    }
    
    function releaseChart() {
    	var data = google.visualization.arrayToDataTable(
        		[['', 'minutes'],
                <c:forEach items="${releaseContinuous}" var="entry">
                ['${entry.key}', ${entry.value}],
                </c:forEach>]);
    	
    	var options = {
                width: 323,
                height:400,
                colors: ['orange'],
                is3D : true,
                bars: 'vertical',
                
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
        var chart = new google.charts.Bar(document.getElementById('release_div'));
        chart.draw(data, options);
    }
    
    function deploymentReleaseChart() {
    	var data = google.visualization.arrayToDataTable(
        		[['', 'minutes'],
                <c:forEach items="${deploymentRelease}" var="entry">
                ['${entry.key}', ${entry.value}],
                </c:forEach>]);
    	
    	var options = {
                width: 323,
                height:400,
                colors: ['orange'],
                is3D : true,
                bars: 'vertical',
                
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
        var chart = new google.charts.Bar(document.getElementById('deploymentReleaseChart'));
        chart.draw(data, options);
    }
    
    function deploymentContinuousChart() {
    	var data = google.visualization.arrayToDataTable(
        		[['', 'minutes'],
                <c:forEach items="${deploymentContinuous}" var="entry">
                ['${entry.key}', ${entry.value}],
                </c:forEach>]);
    	
    	var options = {
                width: 323,
                height:400,
                colors: ['orange'],
                is3D : true,
                bars: 'vertical',
                
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
        var chart = new google.charts.Bar(document.getElementById('deploymentContinuousChart'));
        chart.draw(data, options);
    }
    </script>
    
    <script>
		function formSubmit() {
			document.getElementById("logoutForm").submit();
		}
	</script>
	
</head>

<body>

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
					<li><a href="<c:url value="/jobs" />">JOBS</a></li>
				
					<li><a href="<c:url value="javascript:formSubmit()" />">LOG OUT</a></li>
				</ul>
			</div>
		</div>
	</div>
	
	<div id ="labelCustomer">
	<h2>${customer}</h2>
	<a href="<c:url value="/customers"/>"><h3>CUSTOMERS</h3></a>
	</div>
	<div class="block" id=div_statisticsChart>


		<div id="releaseChart" class="statisticsChart chart">
			<h2>releases</h2>
			<div class="chart">
				<div id="release_div"></div>
				<h3>continuous build</h3>
			</div>
			<div class="chart">

				<div id="chart_div"></div>
				<h3>release build</h3>

			</div>

		</div>
		<div id="deploymentChart" class="statisticsChart chart">
			<h2>deployments</h2>


			<div class="chart">
				<div id="deploymentContinuousChart"></div>
				<h3>continuous build</h3>
			</div>

			<div class="chart">
				<div id="deploymentReleaseChart"></div>
				<h3>release build</h3>
			</div>

		</div>
	</div>


	<div id="customerStatisticsPanel">
		<div id="datepicker">
			<table>
				<form:form method="post" action="customerStatistics"
					modelAttribute="dateyears">
					<tr>
						<td>

							<div>
								<form:select path="yearModel.yearItem">
									<form:options items="${years}" itemValue="yearItem"
										itemLabel="yearItem" />
								</form:select>
							</div>
						</td>

						<td>
							<div>
								<form:select path="monthModel.key">
									<form:options items="${months}" itemValue="key"
										itemLabel="month" />
								</form:select>
							</div>
						</td>
						<td>
							<div>
								<input type="submit" id="button" name="addOptions"
									class="btn btn-info" value="SEARCH" />
							</div>
						</td>
					</tr>


				</form:form>
			</table>
			
			<div id="total">
			<h2 class="total">Het aantal Deployments is: ${countDeployments}</h2>
			<h2 class="total">Het aantal Releases is: ${countReleases}</h2>
			    <hr>
			<h2 class="total">Het totaal is: ${total}</h2>
			
			<div class="labelTotal"></div>
		</div>
		</div>


		<div id="table_content">
			<div id=table_continuous>
				<table class="table table-bordered">
					<h3>releases</h3>
					<tr>
						<th>Duration</th>
						<th>Date</th>
						<th>Time</th>
					</tr>

					<c:forEach items="${releasesByMonth}" var="entry">
						<tr>
							<td>${entry.duration}</td>
							<td>${entry.date}</td>
							<td>${entry.time}</td>
						</tr>
					</c:forEach>
				</table>
			</div>


			<div id=table_release>
				<h3>deployments</h3>
				<table class="table table-bordered">
					<tr>
						<th>Duration</th>
						<th>Date</th>
						<th>Time</th>
					</tr>

					<c:forEach items="${deploymentsByMonth}" var="entry">
						<tr>
							<td>${entry.duration}</td>
							<td>${entry.date}</td>
							<td>${entry.time}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>






</body>
</html>