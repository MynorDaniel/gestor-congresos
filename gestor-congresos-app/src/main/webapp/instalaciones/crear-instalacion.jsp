<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registrar Instalación</title>
    <jsp:include page="/includes/resources.jsp" />
</head>
<body class="bg-light">
    
    <jsp:include page="/includes/header.jsp"/>
    <jsp:include page="/includes/info.jsp"/>
    <jsp:include page="/includes/error.jsp"/>
    
    <div class="container mt-5 vh-100">
        
        <h2 class="mb-4">Registrar Instalación</h2>

        <form action="${pageContext.servletContext.contextPath}/instalaciones" method="post">
            
            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre de la instalación</label>
                <input type="text" class="form-control" id="nombre" name="nombre" required 
                       value="${not empty requestScope.errorAtributo ? param.nombre : ''}">
            </div>

            <div class="mb-3">
                <label for="ubicacion" class="form-label">Ubicación</label>
                <input type="text" class="form-control" id="ubicacion" name="ubicacion" required
                       value="${not empty requestScope.errorAtributo ? param.ubicacion : ''}">
            </div>

            <div id="salones-container" class="mb-3">
                <label class="form-label">Salones</label>

                <c:choose>
                    <c:when test="${not empty requestScope.errorAtributo and not empty paramValues['salones[]']}">
                        <c:forEach var="salon" items="${paramValues['salones[]']}">
                            <div class="input-group mb-2">
                                <input type="text" class="form-control" name="salones[]" 
                                       value="${salon}" placeholder="Nombre del salón" required>
                                <button type="button" class="btn btn-danger" onclick="eliminarSalon(this)">Eliminar</button>
                            </div>
                        </c:forEach>
                    </c:when>

                    <c:otherwise>
                        <div class="input-group mb-2">
                            <input type="text" class="form-control" name="salones[]" placeholder="Nombre del salón" required>
                            <button type="button" class="btn btn-danger" onclick="eliminarSalon(this)">Eliminar</button>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <button type="button" class="btn btn-secondary mb-3" onclick="agregarSalon()">+ Agregar salón</button>
            <button type="submit" class="btn btn-primary w-100">Registrar</button>
        </form>
        
    </div>

    <script>
        function agregarSalon() {
            const container = document.getElementById("salones-container");
            const div = document.createElement("div");
            div.className = "input-group mb-2";
            div.innerHTML = `
                <input type="text" class="form-control" name="salones[]" placeholder="Nombre del salón" required>
                <button type="button" class="btn btn-danger" onclick="eliminarSalon(this)">Eliminar</button>
            `;
            container.appendChild(div);
        }

        function eliminarSalon(boton) {
            boton.parentElement.remove();
        }
    </script>

</body>
</html>

