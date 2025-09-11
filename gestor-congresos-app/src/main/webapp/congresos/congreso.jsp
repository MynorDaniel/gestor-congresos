<%@page import="com.mynor.gestor.congresos.app.modelo.dominio.Congreso"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    Congreso[] congresos = (Congreso[]) request.getAttribute("congresosAtributo");
    Congreso congreso = (congresos != null && congresos.length > 0) ? congresos[0] : null;
    request.setAttribute("primerCongreso", congreso);
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Congreso</title>
    <jsp:include page="/includes/resources.jsp"/>
</head>
<body class="container-fluid p-0">

    <jsp:include page="/includes/header.jsp"/>
    <jsp:include page="/includes/error.jsp"/>
    <div class="container mt-4">

       

        <c:if test="${not empty primerCongreso}">
            <div class="card shadow-sm p-3 mb-4">
                <h2 class="card-title">${primerCongreso.nombre}</h2>
                <p><strong>Precio:</strong> $${primerCongreso.precio}</p>
                <p><strong>Convocando:</strong> 
                    <c:choose>
                        <c:when test="${primerCongreso.convocando}">Sí</c:when>
                        <c:otherwise>No</c:otherwise>
                    </c:choose>
                </p>
                <p><strong>Inicio:</strong> ${primerCongreso.fechaInicio}</p>
                <p><strong>Fin:</strong> <c:out value="${primerCongreso.fechaFin != null ? primerCongreso.fechaFin : '-'}"/></p>
                <p><strong>Descripción:</strong> <c:out value="${primerCongreso.descripcion != null ? primerCongreso.descripcion : '-'}"/></p>
                
            </div>
        </c:if>

        <c:if test="${empty primerCongreso}">
            <p>No hay congresos disponibles.</p>
        </c:if>

    </div>

</body>
</html>
