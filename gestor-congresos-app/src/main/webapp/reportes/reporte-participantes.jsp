<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte Participantes</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/header.jsp"/>
        <jsp:include page="/includes/error.jsp"/>
        <jsp:include page="/includes/info.jsp"/>

        <div class="container mt-4">

            <h2 class="mb-4">Reporte de Participaciones</h2>

            <form action="${pageContext.servletContext.contextPath}/reportes-admin-congreso" method="get" class="row g-3 mb-5">
                <input type="hidden" name="reporte" value="participaciones">
                <div class="col-md-4">
                    <label for="rol" class="form-label">Rol</label>
                    <select id="rol" name="rol" class="form-select" required>
                        <option value="" disabled selected>Selecciona un rol</option>
                        <option value="ASISTENTE">ASISTENTE</option>
                        <option value="TALLERISTA">TALLERISTA</option>
                        <option value="PONENTE">PONENTE</option>
                        <option value="PONENTE_INVITADO">PONENTE_INVITADO</option>
                        <option value="COMITE">COMITE</option>
                    </select>
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Filtrar</button>
                </div>
            </form>

            <div class="mb-5 row row-cols-1 row-cols-md-3 g-4">
                <c:forEach var="p" items="${participacionesAtributo}">
                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <div class="card-body">
                                <h5 class="card-title">${p.usuario.nombre}</h5>
                                <h6 class="card-subtitle mb-2 text-muted">ID: ${p.usuario.id}</h6>
                                <p class="card-text">
                                    <strong>Correo:</strong> ${p.usuario.correo}<br>
                                    <strong>Teléfono:</strong> ${p.usuario.numero}<br>
                                    <strong>Rol:</strong> ${p.rol}
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

        </div>
    </body>
</html>

