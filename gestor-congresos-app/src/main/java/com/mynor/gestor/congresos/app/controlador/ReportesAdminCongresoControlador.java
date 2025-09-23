/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeParticipaciones;
import com.mynor.gestor.congresos.app.modelo.Participacion;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mynordma
 */
@WebServlet(name = "ReportesAdminCongresoControlador", urlPatterns = {"/reportes-admin-congreso"})
public class ReportesAdminCongresoControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reporte = request.getParameter("reporte");
        
        if(!StringUtils.isBlank(reporte) && reporte.equals("participaciones")){
            try {
                ManejadorDeParticipaciones mp = new ManejadorDeParticipaciones();
                Participacion[] participaciones = mp.obtenerTodas();
            } catch (Exception e) {
            }
        }else if(!StringUtils.isBlank(reporte) && reporte.equals("asistencias")){
            
        }else if(!StringUtils.isBlank(reporte) && reporte.equals("reservaciones")){
            
        }else if(!StringUtils.isBlank(reporte) && reporte.equals("ganancias")){
            
        }
    }

}
