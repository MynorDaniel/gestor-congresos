/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeCongresos;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.CongresoInvalidoException;
import com.mynor.gestor.congresos.app.excepcion.FiltrosInvalidosException;
import com.mynor.gestor.congresos.app.modelo.dominio.Congreso;
import com.mynor.gestor.congresos.app.param.CongresoParametros;
import com.mynor.gestor.congresos.app.param.FIltrosCongresosParametros;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * @author mynordma
 */
@WebServlet(name = "CongresoControlador", urlPatterns = {"/congresos"})
public class CongresoControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            FIltrosCongresosParametros filtrosParam = new FIltrosCongresosParametros();
            filtrosParam.asignarValoresDesdeRequest(request);
            Map<String, String> filtros = filtrosParam.toFiltrosCongresos();

            ManejadorDeCongresos manejador = new ManejadorDeCongresos();
            Congreso[] congresos = manejador.obtenerCongresos(filtros);
            
            request.setAttribute("congresosAtributo", congresos);
            
            if(filtros.containsKey("institucion") || filtros.containsKey("fecha_inicio")){
                //pagina reportes
            }else if(filtros.containsKey("nombre")){
                //pagina congreso
                request.getRequestDispatcher("congresos/congreso.jsp").forward(request, response);
            }else if(filtros.containsKey("creador")){
                //pagina mis congresos
                request.getRequestDispatcher("congresos/mis-congresos.jsp").forward(request, response);
            }else{
                //home
                request.getRequestDispatcher("home/home.jsp").forward(request, response);
            }
            
        } catch (FiltrosInvalidosException | AccesoDeDatosException e) {
            request.setAttribute("errorAtributo", e.getMessage());
            request.getRequestDispatcher("home/home.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            CongresoParametros congresoParam = new CongresoParametros();
            congresoParam.asignarValoresDesdeRequest(request);
            Congreso congresoNuevo = congresoParam.toCongreso();
                        
            ManejadorDeCongresos manejador = new ManejadorDeCongresos();
            Congreso congresoCreado = manejador.crearCongreso(congresoNuevo);
            
            request.setAttribute("infoAtributo", "Congreso " + congresoCreado.getNombre() + " registrado con éxito");
            request.getRequestDispatcher("congresos/crear-congreso.jsp").forward(request, response);
            
        } catch (CongresoInvalidoException | AccesoDeDatosException ex) {
            request.setAttribute("errorAtributo", ex.getMessage());
            request.getRequestDispatcher("congresos/crear-congreso.jsp").forward(request, response);
        }
    }

}