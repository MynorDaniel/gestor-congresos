/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeInstalaciones;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeUsuarios;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Instalacion;
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
@WebServlet(name = "CrearCongresoControlador", urlPatterns = {"/crear-congreso"})
public class CrearCongresoControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            ManejadorDeInstalaciones manejador = new ManejadorDeInstalaciones();
            Instalacion[] instalaciones = manejador.obtenerInstalaciones((Usuario) request.getSession().getAttribute("usuarioSession"));
            
            Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
            if(!manejador.esAdminDeCongresos(usuarioActual.getId())) throw new UsuarioInvalidoException("Sin permisos para crear un congreso");
            
            ManejadorDeUsuarios manejadorUsuarios = new ManejadorDeUsuarios();
            Usuario[] usuarios = manejadorUsuarios.obtenerTodos();

            request.setAttribute("usuariosAtributo", usuarios);
            request.setAttribute("instalacionesAtributo", instalaciones);
            request.getRequestDispatcher("congresos/crear-congreso.jsp").forward(request, response);
        } catch (AccesoDeDatosException | UsuarioInvalidoException ex) {
            try {
                ManejadorDeUsuarios manejadorUsuarios = new ManejadorDeUsuarios();
                Usuario[] usuarios = manejadorUsuarios.obtenerTodos();
                
                request.setAttribute("usuariosAtributo", usuarios);
                
                request.setAttribute("errorAtributo", ex.getMessage());
                request.getRequestDispatcher("congresos/crear-congreso.jsp").forward(request, response);
            } catch (AccesoDeDatosException ex1) {
                request.setAttribute("errorAtributo", ex1.getMessage());
                request.getRequestDispatcher("congresos/crear-congreso.jsp").forward(request, response);
            }
        }
    }

}
