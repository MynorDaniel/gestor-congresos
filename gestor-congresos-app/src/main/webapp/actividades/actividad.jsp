<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Actividad</title>
    <jsp:include page="/includes/resources.jsp"/>
</head>
<body>
    <jsp:include page="/includes/header.jsp"/>
    <jsp:include page="/includes/info.jsp"/>
    <jsp:include page="/includes/error.jsp"/>

    <div class="container mt-5">

        <div class="card mb-4 bg-gradient bg-dark text-white">
            <div class="card-body">
                <h3 class="card-title">${actividadAtributo.nombre}</h3>
                <p><strong>Congreso:</strong> ${actividadAtributo.congresoNombre}</p>
                <p><strong>Salón:</strong> ${actividadAtributo.salonNombre}</p>
                <p><strong>Instalación ID:</strong> ${actividadAtributo.instalacionId}</p>
                <p><strong>Cupo:</strong> ${actividadAtributo.cupo}</p>
                <p><strong>Estado:</strong> ${actividadAtributo.estado}</p>
                <p><strong>Tipo:</strong> ${actividadAtributo.tipo}</p>
                <p><strong>Día:</strong> ${actividadAtributo.dia}</p>
                <p><strong>Hora inicio:</strong> ${actividadAtributo.horaInicio}</p>
                <p><strong>Hora fin:</strong> ${actividadAtributo.horaFin}</p>
                <p><strong>Autor ID:</strong> ${actividadAtributo.autorId}</p>
                <p><strong>Descripción:</strong> ${actividadAtributo.descripcion}</p>

                <!-- Reservar cupo --> 
                <c:if test="${actividadAtributo.tipo == 'TALLER'}">
                    <c:set var="yaReservado" value="false"/>
                    <c:forEach var="r" items="${reservacionesAtributo}">
                        <c:if test="${r.usuario == sessionScope.usuario.id}">
                            <c:set var="yaReservado" value="true"/>
                        </c:if>
                    </c:forEach>

                    <c:if test="${not yaReservado}">
                        <form action="${pageContext.servletContext.contextPath}/reservaciones" method="post" class="mt-3">
                            <input type="hidden" name="actividad" value="${actividadAtributo.nombre}">
                            <button type="submit" class="btn btn-success">Reservar cupo</button>
                        </form>
                    </c:if>
                </c:if>
                
                <!-- Aprobar trabajo --> 
                <c:if test="${actividadAtributo.estado == 'PENDIENTE'}">

                    <c:set var="enComite" value="false"/>
                    <c:forEach var="participacion" items="${comiteAtributo}">
                        <c:if test="${participacion.usuarioId eq sessionScope.usuarioSession.id}">
                            <c:set var="enComite" value="true"/>
                        </c:if>
                    </c:forEach>

                    <c:if test="${enComite}">
                        <form action="${pageContext.servletContext.contextPath}/aprobar-trabajo" method="post" class="mt-3">
                            <!-- Datos ocultos para identificar la actividad -->
                            <input type="hidden" name="actividad" value="${actividadAtributo.nombre}">
                            <input type="hidden" name="congreso" value="${actividadAtributo.congresoNombre}">

                            <button type="submit" class="btn btn-warning">Aprobar trabajo</button>
                        </form>
                    </c:if>
                </c:if>

            </div>
                
            


        </div>

        <!-- Tabla de Reservaciones -->
        <h4>Reservaciones</h4>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>Usuario ID</th>
                    <th>Actividad</th>
                    <th>Congreso</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="r" items="${reservacionesAtributo}">
                    <tr>
                        <td>${r.usuario}</td>
                        <td>${r.actividad_nombre}</td>
                        <td>${r.actividad_congreso}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

    </div>
</body>
</html>
