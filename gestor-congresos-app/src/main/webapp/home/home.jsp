<%-- 
    Document   : home
    Created on : Sep 5, 2025, 2:28:32 PM
    Author     : mynordma
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Gestor de Congresos</title>
        <jsp:include page="/includes/resources.jsp" />
    </head>
    <body>
        
        <jsp:include page="/includes/header.jsp"/>
        <jsp:include page="/includes/error.jsp"/>
        <jsp:include page="/congresos/listado-congresos.jsp"/>
        
    </body>
</html>