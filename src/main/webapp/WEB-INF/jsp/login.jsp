<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/widget" prefix="widget"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Todooz</title>
<c:set var="contextPath" value="${pageContext.servletContext.contextPath}" scope="page" />


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
	<div class="panel panel-default">
		<div class="panel-heading">Authentification</div>
		<div class="panel-body">
			<form class="form-horizontal" role="form" name='f'
				action='/j_spring_security_check' method='POST'>
				<div class="form-group">
					<label for="user"
						class="col-lg-2 col-md-2 col-sm-2 col-xs-3 control-label">User</label>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-6">
						<input type="text" class="form-control" name='j_username'
							id="user" placeholder="User">
					</div>
				</div>
				<div class="form-group">
					<label for="password"
						class="col-lg-2 col-md-2 col-sm-2 col-xs-3 control-label">Password</label>
					<div class="col-lg-4 col-md-4 col-sm-4 col-xs-6">
						<input type="password" class="form-control" name='j_password'
							id="password" placeholder="Password">
					</div>
				</div>
				<div class="form-group">
					<div
						class="col-lg-offset-2 col-md-offset-2 col-sm-offset-2 col-lg-10 col-xs-4">
						<button type="submit" class="btn btn-default">Login</button>
					</div>
				</div>
			</form>
		</div>
	</div>


</body>
</html>