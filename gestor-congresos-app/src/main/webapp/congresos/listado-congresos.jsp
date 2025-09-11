<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="container-fluid mt-4">
    <div class="row row-cols-1 row-cols-md-3 g-4 justify-content-start">
        <c:choose>
            <c:when test="${not empty congresosAtributo}">
                <c:forEach var="c" items="${congresosAtributo}">
                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <div class="card-body">
                                <h5 class="card-title">${c.nombre}</h5>
                                <p class="card-text">
                                    <strong>Precio:</strong> $${c.precio}<br>
                                    <strong>Convocando:</strong> 
                                    <c:choose>
                                        <c:when test="${c.convocando}">Sí</c:when>
                                        <c:otherwise>No</c:otherwise>
                                    </c:choose><br>
                                    <strong>Inicio:</strong> ${c.fechaInicio}<br>
                                    <strong>Fin:</strong> <c:out value="${c.fechaFin != null ? c.fechaFin : '-'}"/><br>
                                    <strong>Descripción:</strong> <c:out value="${c.descripcion != null ? c.descripcion : '-'}"/>
                                </p>
                            </div>
                            <div class="card-footer d-flex justify-content-between">
                                <a href="${pageContext.servletContext.contextPath}/congresos?nombre=${c.nombre}" class="btn btn-primary">Ver</a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="col-12">
                    <p class="text-center">No hay congresos disponibles.</p>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</div>
