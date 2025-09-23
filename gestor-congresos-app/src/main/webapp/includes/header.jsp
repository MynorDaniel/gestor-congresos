<%@ page import="com.mynor.gestor.congresos.app.modelo.Usuario" %>
<%@ page import="com.mynor.gestor.congresos.app.modelo.RolSistema" %>

<script>
    window.onpageshow = function(event) {
        if (event.persisted) {
            window.location.reload();
        }
    };
</script>


<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        
        <%
            Usuario usuarioSession = (Usuario) session.getAttribute("usuarioSession");
            String contextPath = request.getContextPath();
            String homeLink = (usuarioSession == null) 
                              ? contextPath + "/index.jsp" 
                              : contextPath + "/congresos";
        %>

        <a class="navbar-brand" href="<%= homeLink %>">Gestor de Congresos</a>


        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto">
                <%
                    if (usuarioSession == null) {
                %>
                        <li class="nav-item">
                            <a class="btn btn-outline-light me-2" href="${pageContext.servletContext.contextPath}/registro">Registrarse</a>
                            <a class="btn btn-primary" href="${pageContext.servletContext.contextPath}/index.jsp">Iniciar Sesión</a>
                        </li>
                <%
                    } else {
                        RolSistema rol = usuarioSession.getRol();
                %>
                        <li class="nav-item"><a class="nav-link" href="#">Perfil</a></li>
                <%
                        if (rol == RolSistema.PARTICIPANTE) {
                %>
                       
                <%
                        } else if (rol == RolSistema.ADMIN_CONGRESOS) {
                %>
                        <!-- Header Congresos -->
                        <li class="nav-item">
                            <a class="nav-link" 
                               href="${pageContext.servletContext.contextPath}/congresos?creador=${usuarioSession.id}">
                               Mis congresos
                            </a>
                        </li>

                        <li class="nav-item"><a class="nav-link" href="${pageContext.servletContext.contextPath}/instalaciones/crear-instalacion.jsp">Instalaciones</a></li>
                      
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="reportesDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Reportes
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="reportesDropdown">
                                <li><a class="dropdown-item" href="${pageContext.servletContext.contextPath}/reportes-admin-congreso?reporte=participaciones">Reporte de participantes</a></li>
                                <li><a class="dropdown-item" href="${pageContext.servletContext.contextPath}/reportes-admin-congreso?reporte=asistencias">Reporte de asistencias</a></li>
                                <li><a class="dropdown-item" href="${pageContext.servletContext.contextPath}/reportes-admin-congreso?reporte=reservaciones">Reporte de reservas a talleres</a></li>
                                <li><a class="dropdown-item" href="${pageContext.servletContext.contextPath}/reportes-admin-congreso?reporte=ganancias">Reporte de ganancias</a></li>

                            </ul>
                        </li>
                        
                <%
                        } else if (rol == RolSistema.ADMIN_SISTEMA) {
                %>
                        <!-- Header Admin -->
                        
                        <li class="nav-item"><a class="nav-link" href="#">Comisión</a></li>
                        <li class="nav-item"><a class="nav-link" href="${pageContext.servletContext.contextPath}/instituciones">Instituciones</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">Usuarios</a></li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="reportesDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Reportes
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="reportesDropdown">
                                <li><a class="dropdown-item" href="${pageContext.servletContext.contextPath}/reportes-sistema?reporte=congresos">Reporte Congresos</a></li>
                                <li><a class="dropdown-item" href="${pageContext.servletContext.contextPath}/reportes-sistema?reporte=ganancias">Reporte Ganancias</a></li>
                            </ul>
                        </li>

                        

                <%
                        }
                %>
                        <li class="nav-item"><a class="nav-link" href="${pageContext.servletContext.contextPath}/inscripciones?usuario=${sessionScope.usuarioSession.id}">Mis inscripciones</a></li>
                
                        <li class="nav-item"><a class="nav-link" href="${pageContext.servletContext.contextPath}/cartera">Cartera digital</a></li>
                        
                        <li class="nav-item">
                            <form action="${pageContext.servletContext.contextPath}/cerrar-sesion" method="post" class="d-inline">
                                <button type="submit" class="btn btn-danger">Cerrar sesión</button>
                            </form>
                        </li>
                <%
                    }
                %>
                
                
            </ul>
        </div>
    </div>
</nav>
