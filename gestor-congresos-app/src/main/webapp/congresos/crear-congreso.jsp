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
    <jsp:include page="/includes/info.jsp"/>

    <div class="container mt-5">
        <h2 class="mb-4 text-center">Crear Congreso</h2>


        <form action="${pageContext.servletContext.contextPath}/congresos" method="post">

            <div class="mb-3">
                <label for="nombre" class="form-label">Nombre</label>
                <input type="text" class="form-control" id="nombre" name="nombre" required
                       value="${not empty requestScope.errorAtributo ? param.nombre : ''}">
            </div>

            <div class="mb-3">
                <label for="precio" class="form-label">Precio</label>
                <input type="number" step="0.01" class="form-control" id="precio" name="precio" required
                       value="${not empty requestScope.errorAtributo ? param.precio : ''}">
            </div>

            <div class="mb-3">
                <label class="form-label">Convocando</label>
                <select class="form-select" name="convocando" required>
                    <option value="true" ${param.convocando == 'true' ? 'selected' : ''}>Sí</option>
                    <option value="false" ${param.convocando == 'false' ? 'selected' : ''}>No</option>
                </select>
            </div>

            <div class="mb-3">
                <label for="fecha_inicio" class="form-label">Fecha de inicio</label>
                <input type="date" class="form-control" id="fecha_inicio" name="fecha" required
                       value="${not empty requestScope.errorAtributo ? param.fecha : ''}">
            </div>

            <div class="mb-3">
                <label for="fecha_fin" class="form-label">Fecha de fin</label>
                <input type="date" class="form-control" id="fecha_fin" name="fecha_fin"
                       value="${not empty requestScope.errorAtributo ? param.fecha_fin : ''}">
            </div>

            <div class="mb-3">
                <label for="descripcion" class="form-label">Descripción</label>
                <textarea class="form-control" id="descripcion" name="descripcion" rows="3">${not empty requestScope.errorAtributo ? param.descripcion : ''}</textarea>
            </div>

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
            
            <div class="mb-3">
                <label for="comiteSelect" class="form-label">Seleccionar miembros del Comité Científico</label>
                <select id="comiteSelect" class="form-select" multiple>
                    <c:forEach var="usuario" items="${usuariosAtributo}">
                        <option value="${usuario.id}">${usuario.nombre}(${usuario.id})</option>
                    </c:forEach>
                </select>
            </div>

            <div id="comiteSeleccionados" class="mt-3 mb-5">
                <h6>Miembros seleccionados:</h6>
                <ul class="list-group" id="comiteLista"></ul>
            </div>

            <input type="hidden" name="comiteUsuarios" id="comiteUsuarios">


            <button type="submit" class="btn btn-primary w-100 mb-5">Crear Congreso</button>
        </form>
    </div>
            
    <script>
        document.getElementById("comiteSelect").addEventListener("change", function () {
            const seleccionados = Array.from(this.selectedOptions);
            const lista = document.getElementById("comiteLista");
            const hiddenInput = document.getElementById("comiteUsuarios");

            lista.innerHTML = "";

            const values = seleccionados.map(opt => opt.value);

            seleccionados.forEach(opt => {
                const li = document.createElement("li");
                li.className = "list-group-item";
                li.textContent = opt.text;
                lista.appendChild(li);
            });

            hiddenInput.value = values.join(",");
        });
    </script>


</body>
</html>

