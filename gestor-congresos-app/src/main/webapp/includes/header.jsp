<%@ page import="com.mynor.gestor.congresos.app.modelo.dominio.Usuario" %>
<%@ page import="com.mynor.gestor.congresos.app.modelo.fabricacionpura.RolSistema" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        
        <%
            Usuario usuarioSession = (Usuario) session.getAttribute("usuarioSession");
            String contextPath = request.getContextPath();
            String homeLink = (usuarioSession == null) 
                              ? contextPath + "/index.jsp" 
                              : contextPath + "/home/home.jsp";
        %>

        <a class="navbar-brand" href="<%= homeLink %>">Gestor de Congresos</a>


        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto">
                <%
                    if (usuarioSession == null) {
                %>
                        <li class="nav-item">
                            <a class="btn btn-outline-light me-2" href="${pageContext.servletContext.contextPath}/usuario/registroUsuario.jsp">Registrarse</a>
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
                        <!-- Header Participante -->
                        <li class="nav-item"><a class="nav-link" href="#">Cartera digital</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">Mis inscripciones</a></li>
                        <li class="nav-item">
                            <a class="btn btn-danger" href="#">Cerrar sesión</a>
                        </li>
                <%
                        } else if (rol == RolSistema.ADMIN_CONGRESOS) {
                %>
                        <!-- Header Congresos -->
                        <li class="nav-item"><a class="nav-link" href="#">Cartera digital</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">Mis inscripciones</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">Mis congresos</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">Instalaciones</a></li>
                        <li class="nav-item">
                            <a class="btn btn-danger" href="#">Cerrar sesión</a>
                        </li>
                <%
                        } else if (rol == RolSistema.ADMIN_SISTEMA) {
                %>
                        <!-- Header Admin -->
                        <li class="nav-item"><a class="nav-link" href="#">Cartera digital</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">Mis inscripciones</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">Comisión</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">Instituciones</a></li>
                        <li class="nav-item"><a class="nav-link" href="#">Usuarios</a></li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="reportesDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                                Reportes
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="reportesDropdown">
                                <li><a class="dropdown-item" href="#">Reporte Congresos</a></li>
                                <li><a class="dropdown-item" href="#">Reporte Ganancias</a></li>
                            </ul>
                        </li>

                        <li class="nav-item">
                            <a class="btn btn-danger" href="#">Cerrar sesión</a>
                        </li>
                <%
                        }
                %>
                        <li class="nav-item">
                            <span class="navbar-text text-white ms-3">
                                Bienvenido, <%= usuarioSession.getNombre() %>
                            </span>
                        </li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</nav>
