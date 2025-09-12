<%-- 
    Document   : crear-congreso
    Created on : Sep 11, 2025, 4:00:31 PM
    Author     : mynordma
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Crear Congreso</title>
    <jsp:include page="/includes/resources.jsp" />
</head>
<body class="bg-light">
    <jsp:include page="/includes/header.jsp"/>
    <jsp:include page="/includes/error.jsp"/>

    <div class="container mt-5">
        <h2 class="mb-4 text-center">Crear Congreso</h2>


        <form action="${pageContext.servletContext.contextPath}/congresos" method="post">

            <!-- Nombre -->
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="nombre" name="nombre" required
                       value="${not empty requestScope.errorAtributo ? param.nombre : ''}">
            </div>

            <!-- Precio -->
            <div class="mb-3">
                <label for="precio" class="form-label">Precio</label>
                <input type="number" step="0.01" class="form-control" id="precio" name="precio" required
                       value="${not empty requestScope.errorAtributo ? param.precio : ''}">
            </div>

            <!-- Convocando -->
            <div class="mb-3">
                <label class="form-label">Convocando</label>
                <select class="form-select" name="convocando" required>
                    <option value="true" ${param.convocando == 'true' ? 'selected' : ''}>Sí</option>
                    <option value="false" ${param.convocando == 'false' ? 'selected' : ''}>No</option>
                </select>
            </div>

            <!-- Fecha inicio -->
            <div class="mb-3">
                <label for="fecha_inicio" class="form-label">Fecha de inicio</label>
                <input type="date" class="form-control" id="fecha_inicio" name="fecha_inicio" required
                       value="${not empty requestScope.errorAtributo ? param.fecha_inicio : ''}">
            </div>

            <!-- Fecha fin (opcional) -->
            <div class="mb-3">
                <label for="fecha_fin" class="form-label">Fecha de fin</label>
                <input type="date" class="form-control" id="fecha_fin" name="fecha_fin"
                       value="${not empty requestScope.errorAtributo ? param.fecha_fin : ''}">
            </div>

            <!-- Descripción -->
            <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción</label>
                <textarea class="form-control" id="descripcion" name="descripcion" rows="3">${not empty requestScope.errorAtributo ? param.descripcion : ''}</textarea>
            </div>

            <!-- Instalación -->
            <div class="mb-3">
                <label for="instalacion" class="form-label">Instalación</label>
                <select class="form-select" id="instalacion" name="instalacion" required>
                    <c:forEach var="inst" items="${requestScope.instalacionesAtributo}">
                        <option value="${inst.id}" ${param.instalacion == inst.id ? 'selected' : ''}>
                            ${inst.nombre} (${inst.ubicacion})
                        </option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit" class="btn btn-primary w-100">Crear Congreso</button>
        </form>
    </div>

</body>
</html>

