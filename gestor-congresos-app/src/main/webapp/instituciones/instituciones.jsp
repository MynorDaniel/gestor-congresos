<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instituciones</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/header.jsp"/>
        <jsp:include page="/includes/error.jsp"/>
        <jsp:include page="/includes/info.jsp"/>
        <div class="container mt-4">
            

            
            <h2 class="mb-4">Agregar institucion</h2>
            <form action="${pageContext.servletContext.contextPath}/instituciones" method="post" class="mb-4">
                <div class="input-group">
                    <input type="text" name="nombre" class="form-control" placeholder="Nombre de la institución" required>
                    <button type="submit" class="btn btn-success">Agregar</button>
                </div>
            </form>

            <h2 class="mb-4">Instituciones</h2>
            <table class="table table-bordered table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="inst" items="${institucionesAtributo}">
                        <tr>
                            <td>${inst.id}</td>
                            <td>${inst.nombre}</td>
                            <td>
                                <form action="${pageContext.servletContext.contextPath}/instituciones" method="post" style="display:inline;">
                                    <input type="hidden" name="id" value="${inst.id}">
                                    <input type="hidden" name="metodo" value="delete">
                                    <button type="submit" class="btn btn-danger btn-sm">Eliminar</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

    </body>
</html>