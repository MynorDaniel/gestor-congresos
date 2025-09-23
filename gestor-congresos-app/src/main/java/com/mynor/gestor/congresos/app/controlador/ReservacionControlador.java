/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeActividades;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeParticipaciones;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Actividad;
import com.mynor.gestor.congresos.app.modelo.FiltrosActividad;
import com.mynor.gestor.congresos.app.modelo.Participacion;
import com.mynor.gestor.congresos.app.modelo.Reservacion;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author mynordma
 */
@WebServlet(name = "ReservacionControlador", urlPatterns = {"/reservaciones"})
public class ReservacionControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String actividad = request.getParameter("actividad");
        String congreso = request.getParameter("congreso");
        String usuarioId = ((Usuario) request.getSession().getAttribute("usuarioSession")).getId();
        
        try {
            
            
            Reservacion reservacion = new Reservacion();
            reservacion.setActividadCongresoNombre(congreso);
            reservacion.setActividadNombre(actividad);
            reservacion.setUsuario(usuarioId);
            
            ManejadorDeActividades ma = new ManejadorDeActividades();
            ma.crearReservacion(reservacion);
            
            response.sendRedirect("actividades?congreso=" + congreso + "&nombre=" + actividad);
            
        } catch (AccesoDeDatosException e) {
            try {
                request.setAttribute("errorAtributo", e.getMessage());
                ManejadorDeActividades manejador = new ManejadorDeActividades();
                FiltrosActividad filtros = new FiltrosActividad();
                filtros.setCongresoNombre(congreso);
                filtros.setNombre(actividad);
                
                Actividad[] actividades = manejador.obtener(filtros);
                
                Actividad actividad2 = actividades[0];
                
                ManejadorDeParticipaciones mp = new ManejadorDeParticipaciones();
                Participacion[] comite = mp.obtenerComite(actividad2.getCongresoNombre());
                request.setAttribute("actividadAtributo", actividad2);
                request.setAttribute("reservacionesAtributo", manejador.obtenerReservaciones(actividad2));
                request.setAttribute("comiteAtributo", comite);
                request.setAttribute("actividadAtributo", actividad2);
                request.getRequestDispatcher("actividades/actividad.jsp").forward(request, response);
            } catch (AccesoDeDatosException ex) {
                request.setAttribute("errorAtributo", ex.getMessage());
                request.getRequestDispatcher("actividades/actividad.jsp").forward(request, response);
            }
        }
    }

}
