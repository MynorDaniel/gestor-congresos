<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Inscripciones</title>
    <jsp:include page="/includes/resources.jsp" />
</head>
<body class="bg-light">
    <jsp:include page="/includes/header.jsp"/>
    <jsp:include page="/includes/error.jsp"/>
    <div class="container mt-4">
        <h2 class="mb-4 text-center">Mis inscripciones</h2>

        <c:if test="${empty inscripcionesAtributo}">
            <div class="alert alert-info text-center">
                No hay inscripciones registradas.
            </div>
        </c:if>

        <c:if test="${not empty inscripcionesAtributo}">
            <table class="table table-bordered table-hover text-center align-middle">
                <thead class="table-dark">
                    <tr>
                        <th>Congreso</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="inscripcion" items="${inscripcionesAtributo}">
                        <tr>
                            <td>${inscripcion.congresoNombre}</td>
                            <td>
                                <a href="${pageContext.servletContext.contextPath}/congresos?nombre=${inscripcion.congresoNombre}">Ver congreso</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>

</body>
</html>
