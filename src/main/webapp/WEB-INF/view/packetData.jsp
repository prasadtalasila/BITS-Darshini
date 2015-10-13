<%-- 
    Document   : packetCount
    Created on : 6 Oct, 2015, 2:26:36 AM
    Author     : amit
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Pcap File Data</title>
    </head>
    <body>
        <h1>Pcap File Packet List</h1>
        <h2>${packetCount}</h2>
        <c:forEach items="${packetList}" var="packet">
        <tr>
            <td>${packet.packetId}</td>
        </tr>
    </c:forEach>
</body>
</html>
