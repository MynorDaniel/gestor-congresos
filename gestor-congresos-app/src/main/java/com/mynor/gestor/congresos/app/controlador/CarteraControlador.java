/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeCartera;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Cartera;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import com.mynor.gestor.congresos.app.param.CarteraParametros;
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
@WebServlet(name = "CarteraControlador", urlPatterns = {"/cartera"})
public class CarteraControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            ManejadorDeCartera manejador = new ManejadorDeCartera();
            Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
            Cartera cartera = manejador.obtenerCartera(usuarioActual);
            
            request.setAttribute("carteraAtributo", cartera);
            request.getRequestDispatcher("usuario/cartera.jsp").forward(request, response);
        } catch (AccesoDeDatosException e) {
            System.out.println(e.getMessage());
            request.setAttribute("errorAtributo", e.getMessage());
            request.getRequestDispatcher("home/home.jsp").forward(request, response);
        }
        
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            CarteraParametros carteraParam = new CarteraParametros(request);
            Cartera carteraTemporal = carteraParam.toEntidad();

            ManejadorDeCartera manejador = new ManejadorDeCartera();
            manejador.actualizar(carteraTemporal);
            
            response.sendRedirect("cartera");
            
        } catch (AccesoDeDatosException | UsuarioInvalidoException e) {
            request.setAttribute("errorAtributo", e.getMessage());
            request.getRequestDispatcher("usuario/cartera.jsp").forward(request, response);
        }
        
    }
    
}
