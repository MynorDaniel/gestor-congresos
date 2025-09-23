<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reporte Congresos</title>
        <jsp:include page="/includes/resources.jsp"/>
    </head>
    <body>
        <jsp:include page="/includes/header.jsp"/>
        <jsp:include page="/includes/error.jsp"/>
        <jsp:include page="/includes/info.jsp"/>

        <div class="container mt-4">

            <h2 class="mb-4">Reporte</h2>

            <form action="<c:choose>
                              <c:when test='${esAdminCongresos}'>
                                  ${pageContext.servletContext.contextPath}/reportes-admin-congreso?reporte=ganancias
                              </c:when>
                              <c:otherwise>
                                  ${pageContext.servletContext.contextPath}/reportes-sistema
                              </c:otherwise>
                          </c:choose>" 
                  method="get" class="row g-3 mb-5">

                <input type="hidden" name="reporte" value="<c:out value='${param.reporte}'/>">

                <c:if test="${not esAdminCongresos}">
                    <div class="col-md-4">
                        <label for="institucion" class="form-label">Institución</label>
                        <select id="institucion" name="institucion" class="form-select" required>
                            <option value="" disabled selected>Selecciona una institución</option>
                            <c:forEach var="inst" items="${institucionesAtributo}">
                                <option value="${inst.id}">${inst.nombre} (${inst.id})</option>
                            </c:forEach>
                        </select>
                    </div>
                </c:if>

                <div class="col-md-4">
                    <label for="fechaInicio" class="form-label">Fecha Inicio</label>
                    <input type="date" id="fechaInicio" name="fecha-inicio" class="form-control">
                </div>
                <div class="col-md-4">
                    <label for="fechaFin" class="form-label">Fecha Fin</label>
                    <input type="date" id="fechaFin" name="fecha-fin" class="form-control">
                </div>
                <div class="col-12">
                    <button type="submit" class="btn btn-primary">Buscar</button>
                </div>
            </form>

            <div id="fuente">
            <c:if test="${reporte == 'ganancias'}">
                <c:set var="totalRecaudado" value="0"/>
                <c:set var="totalGanancia" value="0"/>
                
                <c:forEach var="c" items="${congresosAtributo}">
                    <c:set var="recaudadoTemp" value="0"/>
                    <c:set var="gananciaTemp" value="0"/>
                    <c:forEach var="p" items="${c.pagos}">
                        <c:set var="recaudadoTemp" value="${recaudadoTemp + p.monto}"/>
                        <c:choose>
                            <c:when test="${esAdminCongresos}">
                                <c:set var="gananciaTemp" value="${gananciaTemp + (p.monto - (p.monto * p.comisionCobrada))}"/>
                            </c:when>
                            <c:otherwise>
                                <c:set var="gananciaTemp" value="${gananciaTemp + (p.monto * p.comisionCobrada)}"/>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                    <c:set var="totalRecaudado" value="${totalRecaudado + recaudadoTemp}"/>
                    <c:set var="totalGanancia" value="${totalGanancia + gananciaTemp}"/>
                </c:forEach>

                
                <div class="alert alert-info mb-4">
                    <h5>Totales del reporte</h5>
                    <p>
                        <strong>Total recaudado:</strong> 
                        Q<fmt:formatNumber value="${totalRecaudado}" type="number" minFractionDigits="2" maxFractionDigits="2"/> <br>
                        <strong>Ganancia total:</strong> 
                        Q<fmt:formatNumber value="${totalGanancia}" type="number" minFractionDigits="2" maxFractionDigits="2"/>
                    </p>
                </div>
            </c:if>

            <div class="mb-5 row row-cols-1 row-cols-md-3 g-4">
                <c:forEach var="c" items="${congresosAtributo}">
                    
                    <!-- Vista normal -->
                    <c:if test="${empty reporte or reporte != 'ganancias'}">
                        <div class="col">
                            <div class="card h-100 shadow-sm">
                                <div class="card-body">
                                    <h5 class="card-title">${c.nombre}</h5>
                                    <h6 class="card-subtitle mb-2 text-muted">
                                        Institución (ID): ${c.institucionId}
                                    </h6>
                                    <p class="card-text">
                                        <strong>Creador (ID):</strong> ${c.creador}<br>
                                        <strong>Precio:</strong> 
                                            Q<fmt:formatNumber value="${c.precio}" type="number" minFractionDigits="2" maxFractionDigits="2"/><br>
                                        <strong>Convocando:</strong> 
                                            <c:choose>
                                                <c:when test="${c.convocando}">Sí</c:when>
                                                <c:otherwise>No</c:otherwise>
                                            </c:choose><br>
                                        <strong>Activado:</strong> 
                                            <c:choose>
                                                <c:when test="${c.activado}">Sí</c:when>
                                                <c:otherwise>No</c:otherwise>
                                            </c:choose><br>
                                        <strong>Fecha:</strong> ${c.fecha} - ${c.fechaFin}
                                    </p>
                                    <p class="card-text"><em>${c.descripcion}</em></p>
                                    <div class="card-footer d-flex justify-content-between">
                                        <a href="${pageContext.servletContext.contextPath}/congresos?nombre=${c.nombre}" class="btn btn-primary">Ver</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${reporte == 'ganancias'}">
                        <c:set var="recaudado" value="0"/>
                        <c:set var="ganancia" value="0"/>
                        <c:set var="comision" value="0"/>
                        <c:forEach var="p" items="${c.pagos}">
                            <c:set var="recaudado" value="${recaudado + p.monto}"/>
                            <c:set var="comision" value="${p.comisionCobrada}"/>
                            <c:choose>
                                <c:when test="${esAdminCongresos}">
                                    <c:set var="ganancia" value="${ganancia + (p.monto - (p.monto * p.comisionCobrada))}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="ganancia" value="${ganancia + (p.monto * p.comisionCobrada)}"/>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>


                        <div class="col">
                            <div class="card h-100 shadow-sm">
                                <div class="card-body">
                                    <h5 class="card-title">${c.nombre}</h5>
                                    
                                    <h6 class="card-subtitle mb-2 text-muted">
                                        Institución (ID): ${c.institucionId}
                                    </h6>
                                    <strong>Fecha:</strong> ${c.fecha} - ${c.fechaFin}
                                    <p class="card-text">
                                        <strong>Precio:</strong> 
                                            Q<fmt:formatNumber value="${c.precio}" type="number" minFractionDigits="2" maxFractionDigits="2"/><br>
                                        <strong>Recaudado:</strong> 
                                            Q<fmt:formatNumber value="${recaudado}" type="number" minFractionDigits="2" maxFractionDigits="2"/><br>
                                        <strong>Comisión cobrada:</strong> 
                                            <fmt:formatNumber value="${comision * 100}" type="number" minFractionDigits="2" maxFractionDigits="2"/>%<br>
                                        <strong>Ganancia:</strong> 
                                            Q<fmt:formatNumber value="${ganancia}" type="number" minFractionDigits="2" maxFractionDigits="2"/>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </c:if>

                </c:forEach>
            </div>
            </div>
                <div class="mb-3 text-end">
                    <button id="exportBtn" type="button" class="btn btn-success">Exportar HTML</button>
                </div>

        </div>
                
                
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

                const footer = `
                    </body>
                    </html>`;

                const content = exportNode.innerHTML;

                const fullHtml = head + content + footer;

                const blob = new Blob([fullHtml], { type: 'text/html;charset=utf-8' });
                const url = URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = 'reporte-congresos.html';
                document.body.appendChild(a);
                a.click();
                setTimeout(() => {
                    URL.revokeObjectURL(url);
                    a.remove();
                }, 1000);
            });
        </script>

    </body>
</html>
