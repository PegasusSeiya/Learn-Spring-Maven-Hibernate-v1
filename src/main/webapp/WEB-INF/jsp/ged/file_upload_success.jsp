<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Spring MVC Multiple File Upload Results</title>
</head>
<body>
<h1>Spring Multiple File Upload results</h1>
    <p>Following files are uploaded successfully.</p>
    <ol>
        <c:forEach items="${files}" var="file">
            <li>${file}</li>
        </c:forEach>
    </ol>
    
    <p>Files already uploaded</p>
    <div id="folder">
    	<ul id="folderList">
    		
    	</ul>
    </div>
</body>

<script src="/jquery/jquery-2.1.4.min.js"></script>
<script>

	var urlListFileUploaded = "/fileManage/listFileUploaded";

	var listFileUploaded = function(url){
		
		$.get(url)
		.done(function(response){
			
			console.log(response);
			
			listFiles(response)
			
		})
		.fail(function(response, textStatus, jqXHR){
			
			console.log(response);
			console.log(textStatus);
			console.log(jqXHR);
			
		});
		
	};
	
	
	var listFiles = function(fileNames){
		
		var $folderList = $("#folderList");
		
		var urlDownLoad = "download?file=";
		
		fileNames.forEach(function(value, index){
			var $li 	= $("<li></li>");
			var $link 	= $("<a></a>")
							.attr("href", urlDownLoad+value)
							.text(value);
			$li.append($link);
			$folderList.append($li);	
		});
		
	};

	$(function() {
		
		listFileUploaded(urlListFileUploaded);
		
		
	    //add more file components if Add is clicked
	    /*
	    $('#addFile').click(function() {
	        var fileIndex = $('#fileTable tr').children().length - 1;
	        $('#fileTable').append(
	                '<tr><td>'+
	                '   <input type="file" name="files['+ fileIndex +']" />'+
	                '</td></tr>');
	    });
	    */
	     
	});
</script>
</html>