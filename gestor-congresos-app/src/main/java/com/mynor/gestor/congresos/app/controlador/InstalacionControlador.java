/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeInstalaciones;
import com.mynor.gestor.congresos.app.excepcion.*;
import com.mynor.gestor.congresos.app.modelo.Instalacion;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import com.mynor.gestor.congresos.app.param.InstalacionParametros;
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
@WebServlet(name = "InstalacionControlador", urlPatterns = {"/instalaciones"})
public class InstalacionControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            InstalacionParametros instalacionParam = new InstalacionParametros(request);
            Instalacion instalacion = instalacionParam.toEntidad();

            ManejadorDeInstalaciones manejador = new ManejadorDeInstalaciones();
            manejador.crear(instalacion, (Usuario) request.getSession().getAttribute("usuarioSession"));
            
            request.setAttribute("infoAtributo", "Instalación creada exitosamente");
            request.getRequestDispatcher("instalaciones/crear-instalacion.jsp").forward(request, response);
            
        } catch (InstalacionInvalidaException | AccesoDeDatosException | UsuarioInvalidoException e) {
            request.setAttribute("errorAtributo", e.getMessage());
            request.getRequestDispatcher("instalaciones/crear-instalacion.jsp").forward(request, response);
        }
        
        

    }

}
