/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeActividades;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeCongresos;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeInscripciones;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeInstalaciones;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeParticipaciones;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.InscripcionInvalidaException;
import com.mynor.gestor.congresos.app.modelo.Actividad;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.Inscripcion;
import com.mynor.gestor.congresos.app.modelo.Instalacion;
import com.mynor.gestor.congresos.app.modelo.Participacion;
import com.mynor.gestor.congresos.app.param.InscripcionParametros;
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
@WebServlet(name = "ConfirmarPagoControlador", urlPatterns = {"/confirmar-pago"})
public class ConfirmarPagoControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            InscripcionParametros inscripcionParam = new InscripcionParametros(request);
            Inscripcion inscripcion = inscripcionParam.toEntidad();
            
            ManejadorDeInscripciones manejador = new ManejadorDeInscripciones();
            
            ManejadorDeCongresos manejadorCongresos = new ManejadorDeCongresos();
            Congreso congreso = manejadorCongresos.obtenerCongreso(inscripcion.getCongresoNombre());
            request.setAttribute("congreso", congreso);
            
            if(manejador.existeInscripcion(inscripcion)){
                ManejadorDeActividades manejadorActividades = new ManejadorDeActividades();
                Actividad[] actividades = manejadorActividades.obtenerPorCongreso(congreso.getNombre());
                request.setAttribute("actividadesAtributo", actividades);
                System.out.println("existe");
                request.setAttribute("infoAtributo", "Ya estas inscrito a este congreso");
                
                ManejadorDeInstalaciones manejadorInstalaciones = new ManejadorDeInstalaciones();
                Instalacion instalacion = manejadorInstalaciones.obtenerPorCongreso(congreso.getNombre());
                request.setAttribute("instalacionAtributo", instalacion);
                
                ManejadorDeParticipaciones manejadorParticipaciones = new ManejadorDeParticipaciones();
                Participacion[] comite = manejadorParticipaciones.obtenerComite(congreso.getNombre());
                request.setAttribute("comiteAtributo", comite);
                
                ManejadorDeInscripciones manejadorInscripciones = new ManejadorDeInscripciones();
                Inscripcion[] inscripciones = manejadorInscripciones.obtenerPorCongreso(congreso.getNombre());
                request.setAttribute("inscripcionesAtributo", inscripciones);
                
                request.getRequestDispatcher("congresos/congreso.jsp").forward(request, response);
            }else{
                System.out.println("no existe");
                request.getRequestDispatcher("inscripciones/confirmar-pago.jsp").forward(request, response);
            }
        } catch (AccesoDeDatosException | InscripcionInvalidaException e) {
            try {
                ManejadorDeActividades manejadorActividades = new ManejadorDeActividades();
                Actividad[] actividades = manejadorActividades.obtenerPorCongreso(request.getParameter("congreso"));
                request.setAttribute("actividadesAtributo", actividades);
                System.out.println(e.getMessage());
                request.setAttribute("errorAtributo", e.getMessage());
                
                ManejadorDeInstalaciones manejadorInstalaciones = new ManejadorDeInstalaciones();
                Instalacion instalacion = manejadorInstalaciones.obtenerPorCongreso(request.getParameter("congreso"));
                request.setAttribute("instalacionAtributo", instalacion);
                
                ManejadorDeParticipaciones manejadorParticipaciones = new ManejadorDeParticipaciones();
                Participacion[] comite = manejadorParticipaciones.obtenerComite(request.getParameter("congreso"));
                request.setAttribute("comiteAtributo", comite);
                
                ManejadorDeInscripciones manejadorInscripciones = new ManejadorDeInscripciones();
                Inscripcion[] inscripciones = manejadorInscripciones.obtenerPorCongreso(request.getParameter("congreso"));
                request.setAttribute("inscripcionesAtributo", inscripciones);
                
                request.getRequestDispatcher("congresos/congreso.jsp").forward(request, response);
            } catch (AccesoDeDatosException ex) {
                System.out.println(e.getMessage());
                request.setAttribute("errorAtributo", ex.getMessage());
                request.getRequestDispatcher("congresos/congreso.jsp").forward(request, response);
            }
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
