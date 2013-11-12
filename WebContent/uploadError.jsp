<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>Upload file error</title>
<%
	response.setHeader("Refresh","1;URL=index.jsp");
%>
</head>
<body>
Oops, it seems like the file you uploaded is not valid! Jump back to home page in a few seconds...
</body>
</html>