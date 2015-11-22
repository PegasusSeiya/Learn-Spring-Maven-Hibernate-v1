<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring MVC Multiple File Upload</title>
</head>
<body>
	<h1>Spring Multiple File Upload Form</h1>
	<c:if test="${!empty errors}">
		<p>
			Errors when uploading files : <c:out value="${errors}"/>
		</p>
	</c:if>
 
<form:form method="post" action="/fileManage/upload"
        modelAttribute="uploadForm" enctype="multipart/form-data">
 
    <p>Select files to upload. Press Add button to add more file inputs.</p>
 
    <input id="addFile" type="button" value="Add file field" />
    <input id="removeFile" type="button" value="Remove file field" />
    <table id="fileTable">
        <tr>
            <td><input name="files[0]" type="file" /></td>
        </tr>
        <tr>
            <td><input name="files[1]" type="file" /></td>
        </tr>
    </table>
    <br/>
    <input type="submit" value="Upload" />
</form:form>
</body>

<script src="/jquery/jquery-2.1.4.min.js"></script>
<script>
	
	//add more file components if Add is clicked
	var addFileField = function(){
		
	    $('#addFile').on("click", function() {
	        var fileIndex = $('#fileTable tr').children().length - 1;
	        $('#fileTable').append(
	                '<tr><td>'+
	                '   <input type="file" name="files['+ fileIndex +']" />'+
	                '</td></tr>');
	    });
	};
	
	
	
	var removeFileField = function(){
		$('#removeFile').on("click", function() {
			$('#fileTable').find('tr').last().remove();
	    });
	};
	
	
	$(function() {
	    
		addFileField();
	    
		removeFileField();
	     
	});
</script>
</html>