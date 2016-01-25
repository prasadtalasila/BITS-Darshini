<%-- 
    Document   : attachHook
    Created on : 23 Jan, 2016, 7:22:22 PM
    Author     : mihirkakrambe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>${addHookMsg}</h1>
        <div class="row">
            <div class="col-md-4">
                <form accept-charset="UTF-8" role="form" method="GET" action="<c:url value="/reader"/>">
                    <button type="submit" class="btn btn-success btn-lg">Back</button>
                </form>
            </div>
        </div>
    </body>
</html>
