<%-- 
    Document   : index
    Created on : Sep 5, 2025
    Author     : mynordma
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
        <jsp:include page="/includes/resources.jsp" />
    </head>
        <body class="d-flex flex-column align-items-center justify-content-center vh-100 bg-light">

        <jsp:include page="/includes/error.jsp"/>

        <div class="card shadow-lg p-4 mt-3" style="width: 22rem;">
            <h2 class="text-center mb-4">Iniciar Sesión</h2>
            <form action="LoginControlador" method="post">
                <div class="mb-3">
                    <label for="correo" class="form-label">Correo electrónico</label>
                    <input type="email" class="form-control" id="correo" name="correo" required>
                </div>

                <div class="mb-3">
                    <label for="clave" class="form-label">Contraseña</label>
                    <input type="password" class="form-control" id="clave" name="clave" required>
                </div>

                <button type="submit" class="btn btn-primary w-100">Ingresar</button>
            </form>
        </div>

    </body>

</html>

