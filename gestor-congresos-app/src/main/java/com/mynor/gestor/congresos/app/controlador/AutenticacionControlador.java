/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.Login;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.dominio.Usuario;
import com.mynor.gestor.congresos.app.modelo.fabricacionpura.CredencialesLogin;
import com.mynor.gestor.congresos.app.param.CredencialesLoginParametros;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author mynordma
 */
@WebServlet(name = "AutenticacionControlador", urlPatterns = {"/AutenticacionControlador"})
public class AutenticacionControlador extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            CredencialesLoginParametros credencialesParam = new CredencialesLoginParametros();
            credencialesParam.asignarValoresDesdeRequest(request);
            CredencialesLogin credenciales = credencialesParam.toCredencialesLogin();
            
            Login login = new Login();
            Usuario usuarioLoguedo = login.loguear(credenciales);
            
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuarioSession", usuarioLoguedo);
            
            response.sendRedirect("home/home.jsp");
            
        } catch (UsuarioInvalidoException | AccesoDeDatosException ex) {
            request.setAttribute("errorAtributo", ex.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        
    }

}
