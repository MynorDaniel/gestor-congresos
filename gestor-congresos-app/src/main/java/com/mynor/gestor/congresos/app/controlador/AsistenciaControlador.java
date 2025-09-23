/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeActividades;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Asistencia;
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
@WebServlet(name = "AsistenciaControlador", urlPatterns = {"/asistencias"})
public class AsistenciaControlador extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = request.getParameter("usuario");
        String actividad = request.getParameter("actividad");
        String congreso = request.getParameter("congreso");
        try {
            
            
            ManejadorDeActividades ma = new ManejadorDeActividades();
            Asistencia a = new Asistencia();
            a.setActividadCongresoNombre(congreso);
            a.setActividadNombre(actividad);
            a.setUsuario(usuario);
            
            ma.crearAsistencia(a);
            response.sendRedirect("congresos?nombre=" + congreso);
            
            
        } catch (AccesoDeDatosException e) {
            System.out.println(e.getMessage());
            request.setAttribute("errorAtributo", e.getMessage());
            request.getRequestDispatcher("congresos/congreso.jsp").forward(request, response);
        }
    }
}
