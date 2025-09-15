<%-- 
    Document   : cartera
    Created on : Sep 15, 2025, 1:11:03 PM
    Author     : mynordma
--%>

<%@page import="com.mynor.gestor.congresos.app.modelo.Cartera"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.mynor.gestor.congresos.app.modelo.Cartera"%>
<%
    Cartera cartera = (Cartera) request.getAttribute("carteraAtributo");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Cartera</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body class="bg-light">
        
        <jsp:include page="/includes/header.jsp"/>
        <jsp:include page="/includes/error.jsp"/>
        <jsp:include page="/includes/info.jsp"/>
        
        <div class="container mt-5">
            
            <h2 class="mb-4 text-center">Cartera Digital</h2>

            <div class="card shadow-lg p-4 mb-4">
                <p><strong>Usuario:</strong> <%= cartera != null ? cartera.getUsuario() : "Desconocido" %></p>
                <p><strong>Saldo:</strong> Q <%= cartera != null ? cartera.getSaldo() : 0.0 %></p>
            </div>

            <h4 class="mb-3">Agregar saldo</h4>
            <form action="${pageContext.servletContext.contextPath}/cartera" method="post" class="card p-4 shadow-sm">
                <div class="mb-3">
                    <label for="monto" class="form-label">Monto a agregar</label>
                    <input type="number" step="0.01" min="0.01" class="form-control" id="monto" name="monto" required>
                </div>
                <button type="submit" class="btn btn-primary w-100">Agregar</button>
            </form>
        </div>
    </body>
</html>


