<%@ attribute name="task" required="true" type="fr.todooz.domain.Task"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="rootPath" value="/J2EE-Spring-Maven-1.0" scope="page"/>
<div>
	<p>
		<fmt:formatDate value="${task.date}" pattern="dd MM yyyy" />
	</p>
	<span class="lead"><a href="<c:url value="${rootPath}/edit/${task.id}"/>">${fn:escapeXml(task.title)}</a></span>
	<c:forEach var="tag" items="${task.tagArray}">
		<code>
			<c:out value="${tag}" />
		</code>
	</c:forEach>

	<p>
		<c:out value="${task.text}" />
	</p>
	<a href="<c:url value="${rootPath}/edit/${task.id}/delete"/>"><button class="btn btn-danger"></button></a>
</div>