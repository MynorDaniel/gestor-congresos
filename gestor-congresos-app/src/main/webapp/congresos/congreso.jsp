<%@page import="java.time.LocalDate"%>
<%@page import="com.mynor.gestor.congresos.app.modelo.Congreso"%>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    <jsp:include page="/includes/info.jsp"/>
    <div class="container mt-4">

        <c:if test="${not empty congreso}">
            <div class="card shadow-sm p-3 mb-4 bg-dark bg-gradient text-white">
                <h2 class="card-title">${congreso.nombre}</h2>
                <p><strong>Precio:</strong> $${congreso.precio}</p>
                <p><strong>Convocatoria:</strong> 
                    <c:choose>
                        <c:when test="${congreso.convocando}">Aceptando trabajos</c:when>
                        <c:otherwise>Convocatoria de trabajos cerrada</c:otherwise>
                    </c:choose>
                </p>
                <p><strong>Inicio:</strong> ${congreso.fechaInicio}</p>
                <p><strong>Fin:</strong> <c:out value="${congreso.fechaFin}"/></p>
                <p><strong>Descripción:</strong> <c:out value="${congreso.descripcion != null ? congreso.descripcion : '-'}"/></p>
                <p><strong>Ubicación:</strong> <c:out value="${instalacionAtributo.ubicacion != null ? instalacionAtributo.ubicacion : '-'}"/></p>
                
                <c:set var="yaInscrito" value="false" />
                <c:forEach var="inscripcion" items="${inscripcionesAtributo}">
                    <c:if test="${inscripcion.usuario.id == sessionScope.usuarioSession.id}">
                        <c:set var="yaInscrito" value="true" />
                    </c:if>
                </c:forEach>

                <c:if test="${not yaInscrito}">
                    <a href="${pageContext.servletContext.contextPath}/confirmar-pago?congreso=${congreso.nombre}&usuario=${sessionScope.usuarioSession.id}&fecha=<%=LocalDate.now()%>" 
                       class="btn btn-secondary">Inscribirse</a>
                </c:if>

                <c:if test="${congreso.creador == sessionScope.usuarioSession.id}">
                    <a href="congresos/editar?nombre=${congreso.nombre}" class="btn btn-secondary mt-3">Editar</a>
                    <a href="${pageContext.servletContext.contextPath}/crear-actividad-form?congresoNombre=${congreso.nombre}" class="btn btn-secondary mt-3">Agregar actividad</a>
                </c:if>
                
                <h2 class="mt-3">Comité científico</h2>
                <table class="table table-striped table-bordered">
                    <thead class="table-dark">
                        <tr>
                            <th>ID Usuario</th>
                            <th>Nombre</th>
                            <th>Correo</th>
                            <th>Número</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="participacion" items="${comiteAtributo}">
                            <tr>
                                <td>${participacion.usuario.id}</td>
                                <td>${participacion.usuario.nombre}</td>
                                <td>${participacion.usuario.correo}</td>
                                <td>${participacion.usuario.numero}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
            </div>
                
            <div class="container mt-4">
                <h2>Actividades</h2>
                <table class="table table-striped table-bordered">
                    <thead class="table-dark">
                        <tr>
                            <th>Nombre</th>
                            <th>Salón</th>
                            <th>Cupo</th>
                            <th>Estado</th>
                            <th>Tipo</th>
                            <th>Hora inicio</th>
                            <th>Hora fin</th>
                            <th>Día</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="actividad" items="${actividadesAtributo}">
                            <tr>
                                <td>${actividad.nombre}</td>
                                <td>${actividad.salonNombre}</td>
                                <td>${actividad.cupo}</td>
                                <td>${actividad.estado}</td>
                                <td>${actividad.tipo}</td>
                                <td>${actividad.horaInicio}</td>
                                <td>${actividad.horaFin}</td>
                                <td>${actividad.dia}</td>
                                <td>
                                    <a href="#" 
                                       class="btn btn-sm btn-primary">Ver</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
                
            <div class="container my-4">
                <h2 class="mb-3">Listado de Inscripciones</h2>
                <table class="table table-bordered table-striped">
                    <thead class="table-dark">
                        <tr>
                            <th>ID Usuario</th>
                            <th>Nombre</th>
                            <th>Correo</th>
                            <th>Número</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="inscripcion" items="${inscripcionesAtributo}">
                            <tr>
                                <td>${inscripcion.usuario.id}</td>
                                <td>${inscripcion.usuario.nombre}</td>
                                <td>${inscripcion.usuario.correo}</td>
                                <td>${inscripcion.usuario.numero}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>


        </c:if>

        <c:if test="${empty congreso}">
            <p>No hay congresos disponibles.</p>
        </c:if>

    </div>

</body>
</html>
