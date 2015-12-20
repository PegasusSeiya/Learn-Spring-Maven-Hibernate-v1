<html>
	<head>
		<title>Todooz' Backlog</title>
		<c:set var="contextPath"
			value="${pageContext.servletContext.contextPath}" scope="page" />
		
		
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<!-- Bootstrap -->
		<link href="${contextPath}/bootstrap-3.3.4-dist/css/bootstrap.min.css"
			rel="stylesheet" media="screen">
		
		<style type="text/css">
		body {
			margin: 5px 0 50px 0;
		}
		</style>
	</head>

	<body>
		<div class="col-md-offset-1 col-md-9">
			<div>
				<span><h1>Backlog</h1></span>
				<span><a href="${contextPath}/" style="font-size: 14px">Menu principal</a></span>
			</div>
		
			<table class="table table-hover ">
				<thead>
					<th class="col-md-1">Name</th>
				  	<th class="col-md-4">Description</th>
				  	<th class="col-md-1">Branch</th>
				 	<th class="col-md-1">Version</th>
				 	<th class="col-md-2">Introduction date</th>
			  </thead>
			  <tbody>
			  	<tr>
			  		<td>Frameset</td>
			  		<td>Main menu, table, panel, search navbar, create-update form</td>
			  		<td>master</td>
			  		<td>1.00</td>
			  		<td></td>
			  	</tr>
			  	<tr>
			  		<td>Core</td>
			  		<td>Create-update, delete, find</td>
			  		<td>master</td>
			  		<td>1.00</td>
			  		<td></td>
			  	</tr>
			  	<tr>
			  		<td>Core advanced</td>
			  		<td>Tag-cloud. Today & Tomorrow task's deadline filtering</td>
			  		<td>master</td>
			  		<td>1.00</td>
			  		<td></td>
			  	</tr>
			  </tbody>			  
			</table>
			
		</div>
	
	</body>
</html>