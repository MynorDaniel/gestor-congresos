<%-- 
    Document   : confirmar-pago
    Created on : Sep 17, 2025, 3:21:10 PM
    Author     : mynordma
--%>

<%@page import="com.mynor.gestor.congresos.app.modelo.Congreso"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalles del Congreso</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/header.jsp"/>
        <jsp:include page="/includes/error.jsp"/>
        <jsp:include page="/includes/info.jsp"/>
        
        <div class="container mt-5">
            <h1 class="mb-4">Confirmar Pago</h1>

            <%
                Congreso congreso = (Congreso) request.getAttribute("congreso");
                if (congreso != null) {
            %>
            
            <table class="table table-bordered">
                <tr>
                    <th>Nombre</th>
                    <td><%= congreso.getNombre() %></td>
                </tr>
                <tr>
                    <th>Creador</th>
                    <td><%= congreso.getCreador() %></td>
                </tr>
                <tr>
                    <th>Precio</th>
                    <td>Q <%= congreso.getPrecio() %></td>
                </tr>
                <tr>
                    <th>Convocando</th>
                    <td><%= congreso.isConvocando() ? "Sí" : "No" %></td>
                </tr>
                <tr>
                    <th>Fecha de Inicio</th>
                    <td><%= congreso.getFechaInicio() %></td>
                </tr>
                <tr>
                    <th>Fecha de Fin</th>
                    <td><%= congreso.getFechaFin() %></td>
                </tr>
                <tr>
                    <th>Descripción</th>
                    <td><%= congreso.getDescripcion() %></td>
                </tr>
            </table>

            <div class="mt-4 text-center">
                <form action="${pageContext.servletContext.contextPath}/inscripciones" method="post">
                    <input type="hidden" name="congreso" value="<%= congreso.getNombre() %>">
                    <input type="hidden" name="usuario" value="${sessionScope.usuarioSession.id}">
                    <label for="fecha" class="form-label">Fecha de pago</label>
                    <input type="date" class="form-control" name="fecha" required>
                    <button type="submit" class="btn btn-success btn-lg mt-3">
                        Confirmar pago
                    </button>
                </form>
            </div>

            <% } else { %>
                <div class="alert alert-warning">
                    No se encontró información del congreso.
                </div>
            <% } %>
        </div>
    </body>
</html>