<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/widget" prefix="widget"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
	

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Todooz</title>
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
	<div class="container">
		<widget:navbar />
		<c:if test="${not empty flashMessage}">
			<div class="alert alert-success">${flashMessage}</div>
		</c:if>
		<div class="row">
			<div class="col-lg-7 col-lg-offset-1">
				<legend>All tasks</legend>
				<c:forEach var="task" items="${tasks}">
					<widget:task task="${task}" />
				</c:forEach>
			</div>
			<div class="col-lg-3">
				<div class="panel panel-default">
					<div class="panel-heading">
						Bienvenue
					</div>
					<div class="panel-body">
						<sec:authorize access="hasRole('ROLE_USER')">
							<!-- For login user -->
							<c:url value="/j_spring_security_logout" var="logoutUrl" />
							<form action="${logoutUrl}" method="post" id="logoutForm">
								<!-- TODO: Reactiver le controle csrf 
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								-->
							</form>
							<script>
								function formSubmit() {
									document.getElementById("logoutForm")
											.submit();
								}
							</script>

							<c:if test="${pageContext.request.userPrincipal.name != null}">
								<h5>
									User : ${pageContext.request.userPrincipal.name} | <a href="javascript:formSubmit()"> Logout</a>
								</h5>
							</c:if>


						</sec:authorize>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading">Quick links</div>
					<div class="panel-body">
						<ul>
							<li><a href="${contextPath}/deadLine/today">Today's</a></li>
							<li><a href="${contextPath}/deadLine/tomorrow">Tomorrow's</a></li>
						</ul>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading">Tags</div>
					<div class="panel-body">
						<c:forEach var="tag" items="${tagCloud.tags}">
							<a href="${contextPath}/tag/${tag}" style="font-size: 14px"><c:out
									value="${tag}" /></a>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script src="${contextPath}/jquery/jquery-2.1.4.js"></script>
	<script src="${contextPath}/bootstrap-3.3.4-dist/js/bootstrap.min.js"></script>
</body>
</html>