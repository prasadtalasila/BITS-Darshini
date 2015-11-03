<%-- 
    Document   : viewStored
    Created on : 4 Nov, 2015, 12:28:44 AM
    Author     : amit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp"/>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Stored Packets</title>
    </head>
    <body>
        <h1>Stored Packets Analysis</h1>
        <h2>Total Packets: ${listSize}</h2>
        <br>
        <br>
        <br>
        <div class="row">
            <div class="col-md-2"></div>
            <div class="col-md-8">
                <h4>Packet Attributes</h4>
                <table class="table table-striped table-bordered">
                    <tr>
                        <th>Packet ID</th>
                        <th>Source MAC</th>
                        <th>Destination MAC</th>
                        <th>Source IP</th>
                        <th>Destination IP</th>
                        <th>Header Length</th>
                        <th>Packet Length</th>
                    </tr>
                    <tbody>
                        <c:forEach items="${packetList}" var="packet">
                            <tr>
                                <td><c:out value="${packet.linkAnalyzerEntity.source}"/></td>
                                <td><c:out value="${packet.linkAnalyzerEntity.source}"/></td>
                                <td><c:out value="${packet.linkAnalyzerEntity.destination}"/></td>
                                <td><c:out value="${packet.networkAnalyzerEntity.source}"/></td>
                                <td><c:out value="${packet.networkAnalyzerEntity.destination}"/></td>
                                <td><c:out value="${packet.networkAnalyzerEntity.headerLength}"/></td>
                                <td><c:out value="${packet.networkAnalyzerEntity.packetLength}"/></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>
