<%-- 
    Document   : packetCount
    Created on : 6 Oct, 2015, 2:26:36 AM
    Author     : amit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Packet List in Pcap File</title>
    </head>
    <body>
        <h1>Create Session</h1>
        <br>
        <br>
        <div class="row">
            <div class="col-md-4">
                <form accept-charset="UTF-8" role="form" method="GET" action="<c:url value="/session/new"/>">
                    <button type="submit" class="btn btn-success btn-lg">Create New Session</button>
                </form>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4">
                <form accept-charset="UTF-8" role="form" method="GET" action="<c:url value="/session/default"/>">
                    <button type="submit" class="btn btn-success btn-lg">Create Default Session</button>
                </form>
            </div>
        </div>
    </body>
</html>
