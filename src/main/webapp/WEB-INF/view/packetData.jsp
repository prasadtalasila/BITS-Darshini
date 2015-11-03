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
        <h1>Pcap File Packet List</h1>
        <h2>Total Packets : ${packetCount}</h2>
        <br>
        <br>
        <div class="row">
            <div class="col-md-4">
                <form accept-charset="UTF-8" role="form" method="GET" action="<c:url value="/reader/analysis"/>">
                    <button type="submit" class="btn btn-success btn-lg">Analyze Packets</button>
                </form>
            </div>
            <!--            <div class="col-md-4">
                            <div class="list-group">
            <c:forEach items="${packetList}" var="packet">
                <h3 class="list-group-item-heading">Packet Id : <b>${packet.packetIdEntity.packetId}</b></h3>
                <br>
            </c:forEach>
        </div>
    </div>-->
        </div>
    </body>
</html>
