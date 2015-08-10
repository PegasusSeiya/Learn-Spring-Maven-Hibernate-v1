<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="contextPath" value="${pageContext.servletContext.contextPath}" scope="page"/>

<div class="navbar navbar-default">
    <div class="navbar-header">
        <a class="navbar-brand" href="${contextPath}/">Todooz</a>
    </div>
    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <form class="navbar-form navbar-left" role="search" action="${contextPath}/search">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Search" name="query">
            </div>
        </form>
        <a href="${contextPath}/add" class="btn btn-default navbar-btn navbar-right">
            <span class="glyphicon glyphicon-plus"></span>
        </a>
    </div>
</div>