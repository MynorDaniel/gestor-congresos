/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.UsuarioServicio;
import com.mynor.gestor.congresos.app.creador.MapeadorDeUsuarios;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Usuario;
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
@WebServlet(name = "LoginControlador", urlPatterns = {"/LoginControlador"})
public class LoginControlador extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        MapeadorDeUsuarios mapeador = new MapeadorDeUsuarios();
        UsuarioServicio usuarioServicio = new UsuarioServicio();
        
        try {
            Usuario usuario = mapeador.mapear(request);
            
            usuarioServicio.loguear(usuario);
            
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuarioSession", usuario);
            
            response.sendRedirect("home/home.jsp");
        } catch (UsuarioInvalidoException ex) {
            request.setAttribute("errorAtributo", ex.getMessage());
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

}
