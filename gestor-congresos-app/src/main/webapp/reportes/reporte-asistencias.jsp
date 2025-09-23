<%-- 
    Document   : reporte-asistencias
    Created on : Sep 23, 2025, 6:20:30 AM
    Author     : mynordma
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte Asistencias</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/header.jsp"/>
        <jsp:include page="/includes/error.jsp"/>
        <jsp:include page="/includes/info.jsp"/>

        <div class="container mt-4">

            <h2 class="mb-4">Reporte de Asistencias</h2>

            <form action="${pageContext.servletContext.contextPath}/reportes-admin-congreso" method="get" class="row g-3 mb-5">
                <input type="hidden" name="reporte" value="asistencias">
                <c:set var="congresoNombre" value=""></c:set>

                <div class="col-md-4">
                    <label for="actividad" class="form-label">Actividad</label>
                    <select id="actividad" name="actividad" class="form-select">
                        <option value="" selected>Todas</option>
                        <c:forEach var="a" items="${actividadesAtributo}">
                            <option value="${a.nombre}">
                                ${a.nombre} (Congreso: ${a.congresoNombre})
                            </option>
                            <c:set var="congresoNombre" value="${a.congresoNombre}"></c:set>
                        </c:forEach>
                    </select>
                </div>

                
                <div class="col-md-4">
                    <label for="salon" class="form-label">Salón</label>
                    <select id="salon" name="salon" class="form-select">
                        <option value="" selected>Todos</option>
                        <c:forEach var="s" items="${salonesAtributo}">
                            <option value="${s.nombre}">${s.nombre} (Instalación ID: ${s.instalacion})</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-md-2">
                    <label for="fechaInicio" class="form-label">Fecha Inicio</label>
                    <input type="date" id="fechaInicio" name="fecha-inicio" class="form-control">
                </div>
                <div class="col-md-2">
                    <label for="fechaFin" class="form-label">Fecha Fin</label>
                    <input type="date" id="fechaFin" name="fecha-fin" class="form-control">
                </div>

                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Buscar</button>
                </div>
            </form>

                <div id="fuente">
            <div class="mb-5 row row-cols-1 row-cols-md-3 g-4">
                <c:forEach var="as" items="${asistenciasAtributo}">
                    <div class="col">
                        <div class="card h-100 shadow-sm">
                            <div class="card-body">
                                <h5 class="card-title">Usuario: ${as.usuario}</h5>
                                <p class="card-text">
                                    <strong>Actividad:</strong> ${as.actividadNombre}<br>
                                    <strong>Congreso:</strong> ${as.actividadCongresoNombre}
                                </p>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
                
                
            </div>
                <div class="mb-3 text-end">
                    <button id="exportBtn" type="button" class="btn btn-success">Exportar HTML</button>
                </div>
        </div>
    </body>
    
        <script>
            document.getElementById('exportBtn').addEventListener('click', function () {
                const exportNode = document.getElementById('fuente');
                if (!exportNode) {
                    alert('No hay contenido para exportar.');
                    return;
                }

                const head = `<!DOCTYPE html>
                            <html>
                            <head>
                            <meta charset="utf-8">
                            <title>Reporte Congresos - Ganancias</title>


                            <style>
                      body { 
                        font-family: Arial, sans-serif; 
                        margin: 20px; 
                        color:#222; 
                        background: #f8f9fa;
                      }
                      h2,h3 { 
                        margin: 0 0 15px 0; 
                        color: #333;
                      }
                      .card-container {
                        display: grid;
                        grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
                        gap: 20px;
                      }
                      .card {
                        background: #fff;
                        border-radius: 12px;
                        box-shadow: 0 4px 10px rgba(0,0,0,0.08);
                        padding: 20px;
                        border: 1px solid #000;
                      }
                      .card h4 {
                        margin-top: 0;
                        color: #0069d9;
                        margin-bottom: 10px;
                      }
                      .card .subtitle {
                        font-size: 0.9rem;
                        color: #777;
                        margin-bottom: 12px;
                      }
                      .card p {
                        margin: 5px 0;
                      }
                      .badge {
                        display: inline-block;
                        padding: 3px 8px;
                        font-size: 0.8rem;
                        border-radius: 8px;
                        background: #e9ecef;
                        margin-left: 5px;
                      }
                      .highlight {
                        font-weight: bold;
                        color: #28a745;
                      }
                    </style>

                            </head>
                            <body>
                            `;

                // Footer del documento exportado
                const footer = `
                    </body>
                    </html>`;

                // innerHTML ya contiene el HTML generado por JSTL/EL
                const content = exportNode.innerHTML;

                const fullHtml = head + content + footer;

                // Crear blob y forzar descarga
                const blob = new Blob([fullHtml], { type: 'text/html;charset=utf-8' });
                const url = URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = 'reporte-congresos.html';
                document.body.appendChild(a);
                a.click();
                // limpieza
                setTimeout(() => {
                    URL.revokeObjectURL(url);
                    a.remove();
                }, 1000);
            });
        </script>

</html>
