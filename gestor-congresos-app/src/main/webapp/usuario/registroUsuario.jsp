<%-- 
    Document   : registroUsuario
    Created on : Sep 5, 2025
    Author     : mynordma
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Registro de Usuario</title>
        <jsp:include page="/includes/resources.jsp" />
    </head>
    <body class="bg-light">
        
        <jsp:include page="/includes/header.jsp"/>
        <jsp:include page="/includes/error.jsp"/>
        <jsp:include page="/includes/info.jsp"/>

        <div class="container d-flex align-items-center justify-content-center vh-100">
            <div class="card shadow-lg p-4" style="width: 28rem;">
                
                <c:if test="${not empty rolRegistroAtributo and rolRegistroAtributo == 'ponente-invitado'}">
                    <h2 class="text-center mb-4">Elige o registra un Ponente Invitado</h2>
                    
                    
                    <form action="${pageContext.servletContext.contextPath}/participaciones" method="post">
                        <div class="mb-3">
                            <select class="form-select" id="usuario" name="usuarioExistenteId" required>
                                <c:forEach var="usuario" items="${usuariosAtributo}">
                                    <option value="${usuario.id}">${usuario.nombre} (${usuario.id})</option>
                                </c:forEach>
                            </select>
                        </div>
                        <input type="hidden" class="form-control" id="congreso" name="congreso" value="<%= request.getParameter("congreso") != null ? request.getParameter("congreso") : "" %>">
                        <input type="hidden" class="form-control" id="rol" name="rol" value="<%= request.getParameter("rol") != null ? request.getParameter("rol") : "" %>">
                        <button type="submit" class="btn btn-primary mb-5">Aceptar</button>
                    </form>
                    
                    
                    <form action="${pageContext.servletContext.contextPath}/participaciones" method="post">
                    <input type="hidden" class="form-control" id="congreso" name="congreso" value="<%= request.getParameter("congreso") != null ? request.getParameter("congreso") : "" %>">
                    <input type="hidden" class="form-control" id="rol" name="rol" value="<%= request.getParameter("rol") != null ? request.getParameter("rol") : "" %>">

                </c:if>

                <c:if test="${empty rolRegistroAtributo or rolRegistroAtributo != 'ponente-invitado'}">
                    <h2 class="text-center mb-4">Registro de Participante</h2>
                    <form action="${pageContext.servletContext.contextPath}/usuarios" method="post">
                </c:if>
                    
                    <div class="mb-3">
                        <label for="id" class="form-label">Número de Identificación</label>
                        <input type="text" class="form-control" id="id" name="id" required value="<%= request.getParameter("id") != null ? request.getParameter("id") : "" %>">
                    </div>

                    <div class="mb-3">
                        <label for="correo" class="form-label">Correo Electrónico</label>
                        <input type="email" class="form-control" id="correo" name="correo" required value="<%= request.getParameter("correo") != null ? request.getParameter("correo") : "" %>">
                    </div>

                    <div class="mb-3">
                        <label for="clave" class="form-label">Clave</label>
                        <input type="password" class="form-control" id="clave" name="clave" required value="<%= request.getParameter("clave") != null ? request.getParameter("clave") : "" %>">
                    </div>

                    <div class="mb-3">
                        <label for="nombre" class="form-label">Nombre Completo</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" required value="<%= request.getParameter("nombre") != null ? request.getParameter("nombre") : "" %>">
                    </div>

                    <div class="mb-3">
                        <label for="numero" class="form-label">Número de Teléfono</label>
                        <input type="tel" class="form-control" id="numero" name="numero" required value="<%= request.getParameter("numero") != null ? request.getParameter("numero") : "" %>">
                    </div>
                    
                    <div class="mb-3">
                        <label for="institucion" class="form-label">Institución</label>
                        <select class="form-select" id="institucion" name="institucion" required>
                            <c:forEach var="inst" items="${institucionesAtributo}">
                                <option value="${inst.id}" 
                                    <c:if test="${param.institucion == inst.id}">selected</c:if>>
                                    ${inst.nombre}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <button type="submit" class="btn btn-primary w-100">Registrar</button>
                </form>
            </div>
        </div>
    </body>
</html>

