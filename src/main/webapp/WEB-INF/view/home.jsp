<%-- 
    Document   : home
    Created on : 30 Sep, 2015, 1:46:00 AM
    Author     : amit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Protocol Analyzer Home</title>
    </head>
    <body>
        <div class="row">
            <div class="col-md-1"></div>
            <div class="col-md-4">
                <h1 class="page-header">Protocol Analyzer</h1>
            </div>
            <div class="col-md-7"></div>
        </div>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <div class="row">
            <div class="col-md-4"></div>
            <div class="col-md-4">
                <div class="row">
                    <div class="col-md-4">
                        <form accept-charset="UTF-8" role="form" method="GET" action="<c:url value="/session"/>">
                            <button type="submit" class="btn btn-success btn-lg">Go to Session Page</button>
                        </form>
                    </div>
                    <div class="col-md-4"></div>
                    <div class="col-md-4">
                        <form accept-charset="UTF-8" role="form" method="GET" action="<c:url value="/stored_view"/>">
                            <button type="submit" class="btn btn-primary btn-lg">View Stored Packets</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
