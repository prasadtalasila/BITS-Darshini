<%-- 
    Document   : packetStats
    Created on : 03 Feb, 2016, 9:58:02 PM
    Author     : crygnus
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
        <h1>Welcome to the Pcap file Stats Page</h1>
        <p>
        This page shows you the main statistics of the pcap file you have read and are about to analyze.
        </p>
        <p> Note that I am only a temporary page and Mr. Sukanto Guha will butify me. Where is he btw?? </p>
        <h2>Mean Timestamp : ${meanTimeStamp}</h2>
        <h2>Standard Deviation of Timestamps : ${standardDeviation}</h2>
    </body>
</html>
