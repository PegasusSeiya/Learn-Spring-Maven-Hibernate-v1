<%@ tag language="java" pageEncoding="UTF-8"%>
<div class="navbar navbar-default">
    <div class="navbar-header">
        <a class="navbar-brand" href="/">Todooz</a>
    </div>
    <div class="collapse navbar-collapse navbar-ex1-collapse">
        <form class="navbar-form navbar-left" role="search" action="/search">
            <div class="form-group">
                <input type="text" class="form-control" placeholder="Search" name="query">
            </div>
        </form>
        <a href="/add" class="btn btn-default navbar-btn navbar-right">
            <span class="glyphicon glyphicon-plus"></span>
        </a>
    </div>
</div>