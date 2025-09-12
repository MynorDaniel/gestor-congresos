<%-- 
    Document   : mis-congresos
    Created on : Sep 11, 2025, 3:57:42 PM
    Author     : mynordma
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Mis congresos</title>
        <jsp:include page="/includes/resources.jsp" />
    </head>
    <body>
        <jsp:include page="/includes/header.jsp"/>
        <jsp:include page="/includes/error.jsp"/>
        
        <div class="mb-4 d-flex justify-content-center mt-3">
            <a href="${pageContext.servletContext.contextPath}/crear-congreso" class="btn btn-success">
                Crear Congreso +
            </a>
        </div>

        
        <jsp:include page="/congresos/listado-congresos.jsp"/>
    </body>
</html>
