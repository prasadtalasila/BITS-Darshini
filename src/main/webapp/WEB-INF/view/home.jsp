<%-- 
    Document   : home
    Created on : 30 Sep, 2015, 1:46:00 AM
    Author     : amit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Spring Loaded! Dispatcher Servlet working!</h1>
        <c:forEach items="${paths}" var="path">
        <tr>      
            <td>${path}</td> 
        </tr>
    </c:forEach>
</body>
</html>
