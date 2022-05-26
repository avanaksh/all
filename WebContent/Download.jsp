<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.FileInputStream"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Download</title>
</head>
<body>
<H1>Download File</H1>
<%
//response.setContentType("text/html");
String filename="image.png";
String filepath="E:\\";

response.setContentType("APPLICATION/OCTET-STREAM");
response.setHeader("Content-Disposition", "attachment; filename=\""+filename+"\"");

FileInputStream fis=new FileInputStream(filepath+filename);

int i=-1;
while((i=fis.read())!=-1){
	out.write(i);
}
fis.close();
out.close();
%>
</body>
</html>