<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib tagdir="/WEB-INF/tags/widget" prefix="widget"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<html>
<head>
<title>Todooz</title>
<c:set var="contextPath" value="${pageContext.request.contextPath}" scope="page"/>
<c:set var="rootPath" value="/J2EE-Spring-Maven-1.0" scope="page"/>

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
		<div class="row">
			<div class="col-lg-7 col-lg-offset-1">

				<legend>Edit</legend>

				<form:form class="form-horizontal" role="form" action="${rootPath}/edit"  method="post" commandName="task">
					<form:hidden path="id" />
					<div class="form-group">
						<label for="title" class="col-lg-2 control-label">Titre</label>
						<div class="col-lg-8">
							<form:input cssClass="form-control" path="title" id="title"
								placeholder="Titre" />
						</div>
					</div>
					<c:set var="error">
						<form:errors path="date" cssStyle="color:#B94A48"
							cssClass="help-block" />
					</c:set>
					<div class="form-group ${not empty error ? 'has-error' : ''}">
						<label for="date" class="col-lg-2 control-label">Date</label>
						<div class="col-lg-4">
							<form:input class="form-control" path="date" id="date"
								placeholder="dd/MM/yyyy"/>
							<form:errors path="date">
								<span style="color: #B94A48" class="help-block">Le format
									de la date n'est pas correct.</span>
							</form:errors>
						</div>
					</div>
					<div class="form-group">
						<label for="tags" class="col-lg-2 control-label">Tags</label>
						<div class="col-lg-6">
							<form:input class="form-control" id="tags" path="tags"
								placeholder="Tags"/>
						</div>
					</div>
					<div class="form-group">
						<label for="text" class="col-lg-2 control-label">Texte</label>
						<div class="col-lg-10">
							<form:textarea class="form-control" path="text" id="text"
								placeholder="Texte" rows="5"/>
						</div>
					</div>
					<div class="form-group">
						<div class="col-lg-offset-2 col-lg-10">
							<button type="submit" class="btn btn-primary">${empty task.id ? 'Add' : 'Update'}</button>
						</div>
					</div>
				</form:form>

			</div>
			<div class="col-lg-3">
				<div class="panel panel-default">
					<div class="panel-heading">Quick links</div>
					<div class="panel-body">
						<ul>
							<li><a href="${rootPath}/today">Today's</a></li>
							<li><a href="${rootPath}/tomorrow">Tomorrow's</a></li>
						</ul>
					</div>
				</div>

				<div class="panel panel-default">
					<div class="panel-heading">Tags</div>
					<div class="panel-body">
						<c:forEach var="tag" items="${tagCloud.tags}">
							<a href="${rootPath}/tag/<c:out value="${tag}"/>" style="font-size: 14px"><c:out value="${tag}" /></a>
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