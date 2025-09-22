<%-- 
    Document   : crear-actividad
    Created on : Sep 18, 2025, 6:54:19 PM
    Author     : mynordma
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Crear Actividad</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/header.jsp"/>
        <jsp:include page="/includes/info.jsp"/>
        <jsp:include page="/includes/error.jsp"/>
        
        <div class="container mt-5">

            <div class="card mb-4">
                <div class="card-body">
                    <h3 class="card-title">${congresoAtributo.nombre}</h3>
                    <p class="card-text">
                        <strong>Fecha inicio:</strong> ${congresoAtributo.fechaInicio} <br>
                        <strong>Fecha fin:</strong> ${congresoAtributo.fechaFin}
                    </p>
                </div>
            </div>

            <h4>Crear Actividad</h4>
            <form action="${pageContext.servletContext.contextPath}/actividades" method="post" class="mt-4">

                <input type="hidden" name="esTrabajo" class="form-control" 
                           value="${esTrabajo != null ? esTrabajo : null}">
                
                <div class="mb-3">
                    <label class="form-label">Nombre</label>
                    <input type="text" name="nombre" class="form-control" 
                           value="${param.nombre}" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Congreso</label>
                    <input type="text" name="congreso" class="form-control" 
                           value="${param.congreso != null ? param.congreso : congresoAtributo.nombre}" readonly>
                </div>

                <div class="mb-3">
                    <label class="form-label">Instalación</label>
                    <input type="text" name="instalacion" class="form-control" 
                           value="${param.instalacion != null ? param.instalacion : congresoAtributo.instalacionId}" readonly>
                </div>

                <div class="mb-3">
                    <label class="form-label">Salón</label>
                    <select name="salon" class="form-select" required>
                        <c:forEach var="salon" items="${salonesAtributo}">
                            <option value="${salon.nombre}" 
                                    <c:if test="${param.salon == salon.nombre}">selected</c:if>>
                                ${salon.nombre}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="mb-3">
                    <label class="form-label">Descripción</label>
                    <textarea name="descripcion" class="form-control" rows="3">${param.descripcion}</textarea>
                </div>

                <div class="mb-3">
                    <label class="form-label">Tipo</label>
                    <select name="tipo" id="tipoSelect" class="form-select" required>
                        <option value="PONENCIA" ${param.tipo == 'PONENCIA' ? 'selected' : ''}>Ponencia</option>
                        <option value="TALLER" ${param.tipo == 'TALLER' ? 'selected' : ''}>Taller</option>
                    </select>
                </div>

                <div class="mb-3" id="cupoDiv" style="display: ${param.tipo == 'TALLER' ? 'block' : 'none'};">
                    <label class="form-label">Cupo</label>
                    <input type="number" name="cupo" class="form-control" min="1" 
                           value="${param.cupo}">
                </div>

                <div class="mb-3">
                    <label class="form-label">Hora inicio</label>
                    <input type="time" name="hora_inicio" class="form-control" 
                           value="${param.hora_inicio}" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Hora fin</label>
                    <input type="time" name="hora_fin" class="form-control" 
                           value="${param.hora_fin}" required>
                </div>

                <div class="mb-3">
                    <label class="form-label">Día</label>
                    <input type="date" name="dia" class="form-control" 
                           value="${param.dia}" required>
                </div>
                
                <div class="mb-3">
                    <label for="encargadosSelect" class="form-label">Seleccionar Encargados</label>
                    <select id="encargadosSelect" class="form-select" multiple>
                        <c:forEach var="p" items="${posiblesEncargadosAtributo}">
                            <option value="${p.usuarioId}">
                                ${p.usuario.nombre} - ${p.rol}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div id="encargadosSeleccionados" class="mt-3 mb-4">
                    <h6>Encargados seleccionados:</h6>
                    <ul class="list-group" id="encargadosLista"></ul>
                </div>

                <input type="hidden" name="encargadosUsuarios" id="encargadosUsuarios">


                <div class="d-grid mb-3">
                    <button type="submit" class="btn btn-primary">Crear Actividad</button>
                </div>
            </form>

        </div>
    </body>
    
    
    <script>
        const tipoSelect = document.getElementById("tipoSelect");
        const cupoDiv = document.getElementById("cupoDiv");

        function toggleCupo() {
            if (tipoSelect.value === "TALLER") {
                cupoDiv.style.display = "block";
                cupoDiv.querySelector("input").setAttribute("required", "required");
            } else {
                cupoDiv.style.display = "none";
                cupoDiv.querySelector("input").removeAttribute("required");
            }
        }

        toggleCupo();
        tipoSelect.addEventListener("change", toggleCupo);
        
        document.getElementById("encargadosSelect").addEventListener("change", function () {
        const seleccionados = Array.from(this.selectedOptions);
        const lista = document.getElementById("encargadosLista");
        const hiddenInput = document.getElementById("encargadosUsuarios");

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

</html>
