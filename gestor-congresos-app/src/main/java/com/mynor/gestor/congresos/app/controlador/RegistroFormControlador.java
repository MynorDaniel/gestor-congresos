/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeInstituciones;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeUsuarios;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Institucion;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author mynordma
 */
@WebServlet(name = "RegistroFormControlador", urlPatterns = {"/registro"})
public class RegistroFormControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
            String rol = request.getParameter("rol");
            if(rol != null){
                request.setAttribute("rolRegistroAtributo", rol);
                
                //Obtener usuarios
                ManejadorDeUsuarios manejadorUsuarios = new ManejadorDeUsuarios();
                Usuario[] usuarios = manejadorUsuarios.obtenerTodos();
                request.setAttribute("usuariosAtributo", usuarios);
            }
            
            ManejadorDeInstituciones manejadorInstituciones = new ManejadorDeInstituciones();
            Institucion[] instituciones = manejadorInstituciones.obtenerTodas();
            request.setAttribute("institucionesAtributo", instituciones);
            request.getRequestDispatcher("usuario/registroUsuario.jsp").forward(request, response);
            
        } catch (AccesoDeDatosException e) {
            request.setAttribute("errorAtributo", e.getMessage());
            request.getRequestDispatcher("usuario/registroUsuario.jsp").forward(request, response);
        }
    }
    
}