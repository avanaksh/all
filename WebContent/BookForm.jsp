<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CABIN Books Store Application</title>
</head>
<body>
 <center>
        <h1>Books Management</h1>
        <h2>
            <a href="<%=request.getContextPath()%>/new">Add New Book</a>
            &nbsp;&nbsp;&nbsp;
            <a href="<%=request.getContextPath()%>/list">List All Books</a>
              <a href="<%=request.getContextPath()%>/upload">Upload Data</a>   
 <a href="Download.jsp">Download</a>   
        </h2>
    </center>
    <div align="center">
        <c:if test="${book != null}">
            <form action="update" method="post">
        </c:if>
        <c:if test="${book == null}">
            <form action="insert" method="post">
        </c:if>
        <table border="1" cellpadding="5">
            <caption>
                <h2>
                    <c:if test="${book != null}">
                        Edit Book
                    </c:if>
                    <c:if test="${book == null}">
                        Add New Book
                    </c:if>
                </h2>
            </caption>
                <c:if test="${book != null}">
				<c:out value="${book.id}" />
                  <%--   <input type="hidden" name="id" value="<c:out value='${book.id}' />" /> --%>
                </c:if>           
            <tr>
                <th>Title: </th>
                <td><c:out value="${book.title}" />
                   <%--  <input type="text" name="title" size="45"
                            value="<c:out value='${book.title}' />"
                        /> --%>
                </td>
            </tr>
            <tr>
                <th>Author: </th>
                <td>
<c:out value="${book.author}" />
                   <%--  <input type="text" name="author" size="45"
                            value="<c:out value='${book.author}' />"
                    /> --%>
                </td>
            </tr>
            <tr>
                <th>Price: </th>
                <td>
<c:out value="${book.price}" />
                    <%-- <input type="text" name="price" size="5"
                            value="<c:out value='${book.price}' />"
                    /> --%>
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <input type="submit" value="Save" />
                </td>
            </tr>
        </table>
        </form>
    </div>   
</body>
</html>