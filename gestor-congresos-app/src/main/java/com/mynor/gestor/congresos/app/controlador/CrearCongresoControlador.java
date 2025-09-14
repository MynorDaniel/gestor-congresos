/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeInstalaciones;
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
            
            request.setAttribute("instalacionesAtributo", instalaciones);
            request.getRequestDispatcher("/congresos/crear-congreso.jsp").forward(request, response);
        } catch (AccesoDeDatosException | UsuarioInvalidoException ex) {
            request.setAttribute("errorAtributo", ex.getMessage());
            request.getRequestDispatcher("/congresos/crear-congreso.jsp").forward(request, response);
        }
    }

}
