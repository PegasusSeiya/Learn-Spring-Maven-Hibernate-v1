<%@ attribute name="task" required="true" type="fr.todooz.domain.Task"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<c:set var="contextPath"
	value="${pageContext.servletContext.contextPath}" scope="page" />
<tr>
	<td>${fn:escapeXml(task.title)}</td>
	<td>
		<p>
			<fmt:formatDate value="${task.date}" pattern="dd/MM/yyyy" />
		</p>
	</td>
	<td>
		<p>
			<c:out value="${task.text}" />
		</p>
	</td>
	<td><c:forEach var="tag" items="${task.tagArray}">
			<code>
				<c:out value="${tag}" />
			</code>
		</c:forEach>
	</td>
	<td>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
			<span class="lead">
				<a href="${contextPath}/edit/${task.id}" title="Edit">
					<span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
				</a>
			</span>
			<span class="lead">
				<a href="${contextPath}/edit/${task.id}/delete" title="Delete" name="taskDelete">
					<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
				</a>
			</span>
		</sec:authorize>
		
	</td>
</tr>