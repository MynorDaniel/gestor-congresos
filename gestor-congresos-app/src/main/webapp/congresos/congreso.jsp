<%@page import="com.mynor.gestor.congresos.app.modelo.Congreso"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    Congreso congreso = (Congreso) request.getAttribute("congreso");
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

       

        <c:if test="${not empty congreso}">
            <div class="card shadow-sm p-3 mb-4">
                <h2 class="card-title">${congreso.nombre}</h2>
                <p><strong>Precio:</strong> $${congreso.precio}</p>
                <p><strong>Convocando:</strong> 
                    <c:choose>
                        <c:when test="${congreso.convocando}">Sí</c:when>
                        <c:otherwise>No</c:otherwise>
                    </c:choose>
                </p>
                <p><strong>Inicio:</strong> ${congreso.fechaInicio}</p>
                <p><strong>Fin:</strong> <c:out value="${congreso.fechaFin}"/></p>
                <p><strong>Descripción:</strong> <c:out value="${congreso.descripcion != null ? congreso.descripcion : '-'}"/></p>
                
            </div>
        </c:if>

        <c:if test="${empty congreso}">
            <p>No hay congresos disponibles.</p>
        </c:if>

    </div>

</body>
</html>
