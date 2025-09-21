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
import com.mynor.gestor.congresos.app.excepcion.FiltrosInvalidosException;
import com.mynor.gestor.congresos.app.excepcion.InscripcionInvalidaException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Actividad;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.FiltrosInscripcion;
import com.mynor.gestor.congresos.app.modelo.Inscripcion;
import com.mynor.gestor.congresos.app.modelo.Instalacion;
import com.mynor.gestor.congresos.app.modelo.Participacion;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import com.mynor.gestor.congresos.app.param.FIltrosInscripcionesParametros;
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
@WebServlet(name = "InscripcionControlador", urlPatterns = {"/inscripciones"})
public class InscripcionControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            FIltrosInscripcionesParametros filtrosParam = new FIltrosInscripcionesParametros(request);
            FiltrosInscripcion filtros = filtrosParam.toEntidad();
            
            ManejadorDeInscripciones manejador = new ManejadorDeInscripciones();
            Inscripcion[] inscripciones = manejador.obtenerInscripcionesPropias(filtros, (Usuario) request.getSession().getAttribute("usuarioSession"));
            
            request.setAttribute("inscripcionesAtributo", inscripciones);
            request.getRequestDispatcher("inscripciones/inscripciones.jsp").forward(request, response);
        } catch (AccesoDeDatosException | FiltrosInvalidosException | UsuarioInvalidoException e) {
            request.setAttribute("errorAtrbuto", e.getMessage());
            request.getRequestDispatcher("home/home.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            InscripcionParametros inscripcionParam = new InscripcionParametros(request);
            Inscripcion inscripcion = inscripcionParam.toEntidad();
            
            Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
            if(usuarioActual == null || !inscripcion.getUsuarioId().equals(usuarioActual.getId())) throw new UsuarioInvalidoException("Sesión inválida");
            
            ManejadorDeInscripciones manejador = new ManejadorDeInscripciones();
            manejador.crear(inscripcion);
            
            ManejadorDeCongresos manejadorCongresos = new ManejadorDeCongresos();
            Congreso congreso = manejadorCongresos.obtenerCongreso(inscripcion.getCongresoNombre());
            
            ManejadorDeActividades manejadorActividades = new ManejadorDeActividades();
            Actividad[] actividades = manejadorActividades.obtenerPorCongreso(congreso.getNombre());
            request.setAttribute("actividadesAtributo", actividades);
            
            request.setAttribute("congreso", congreso);
            request.setAttribute("infoAtributo", "Inscripción realizada exitosamente");
            
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
            
        } catch (AccesoDeDatosException | InscripcionInvalidaException | UsuarioInvalidoException e) {
            try {
                System.out.println(e.getMessage());
                ManejadorDeCongresos manejadorCongresos = new ManejadorDeCongresos();
                Congreso congreso = manejadorCongresos.obtenerCongreso(request.getParameter("congreso"));
                
                ManejadorDeActividades manejadorActividades = new ManejadorDeActividades();
                Actividad[] actividades = manejadorActividades.obtenerPorCongreso(congreso.getNombre());
                request.setAttribute("actividadesAtributo", actividades);
                
                request.setAttribute("congreso", congreso);
                request.setAttribute("errorAtributo", e.getMessage());
                
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
            } catch (AccesoDeDatosException ex) {
                request.setAttribute("errorAtributo", e.getMessage());
                request.getRequestDispatcher("home/home.jsp").forward(request, response);
            }
        }
    }

}
