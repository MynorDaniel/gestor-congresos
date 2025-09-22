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
import com.mynor.gestor.congresos.app.excepcion.ActividadInvalidaException;
import com.mynor.gestor.congresos.app.excepcion.FiltrosInvalidosException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Actividad;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.FiltrosActividad;
import com.mynor.gestor.congresos.app.modelo.Inscripcion;
import com.mynor.gestor.congresos.app.modelo.Instalacion;
import com.mynor.gestor.congresos.app.modelo.Participacion;
import com.mynor.gestor.congresos.app.modelo.Salon;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import com.mynor.gestor.congresos.app.param.ActividadParametros;
import com.mynor.gestor.congresos.app.param.FiltrosActividadParametros;
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
@WebServlet(name = "ActividadControlador", urlPatterns = {"/actividades"})
public class ActividadControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            FiltrosActividadParametros filtrosParam = new FiltrosActividadParametros(request);
            FiltrosActividad filtros = filtrosParam.toEntidad();
            
            ManejadorDeActividades manejador = new ManejadorDeActividades();
            Actividad[] actividades = manejador.obtener(filtros);
            
            Actividad actividad = actividades[0];
            
            ManejadorDeParticipaciones mp = new ManejadorDeParticipaciones();
            Participacion[] comite = mp.obtenerComite(actividad.getCongresoNombre());
         
            request.setAttribute("comiteAtributo", comite);
            request.setAttribute("actividadAtributo", actividad);
            request.getRequestDispatcher("actividades/actividad.jsp").forward(request, response);
            
        } catch (AccesoDeDatosException | FiltrosInvalidosException e) {
            request.setAttribute("errorAtributo", e.getMessage());
            request.getRequestDispatcher("actividades/actividad.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
            //Parsear parametros
            ActividadParametros actividadParam = new ActividadParametros(request);
            Actividad actividad = actividadParam.toEntidad();
            
            //Obtener congreso
            ManejadorDeCongresos manejadorCongresos = new ManejadorDeCongresos();
            Congreso congreso = manejadorCongresos.obtenerCongreso(actividad.getCongresoNombre());
            
            //Autorizacion
            String esTrabajo = request.getParameter("esTrabajo");
            if(StringUtils.isBlank(esTrabajo) || !esTrabajo.equals("true")){
                if(!congreso.getCreador().equals(((Usuario) request.getSession().getAttribute("usuarioSession")).getId())) throw new UsuarioInvalidoException("No puedes modificar este congreso");
            }
            
            //Crear actividad
            ManejadorDeActividades manejadorActividades = new ManejadorDeActividades();
            manejadorActividades.crear(actividad);
            if(StringUtils.isBlank(esTrabajo) || !esTrabajo.equals("true")){
                request.setAttribute("infoAtributo", "Actividad creada exitosamente");
            }else{
                request.setAttribute("infoAtributo", "Actividad pendiente de aprobación");
            }
            
            //Obtener actividades
            Actividad[] actividades = manejadorActividades.obtenerPorCongreso(congreso.getNombre());
            request.setAttribute("actividadesAtributo", actividades);
            
            //Obtener instalacion del congreso
            ManejadorDeInstalaciones manejadorInstalaciones = new ManejadorDeInstalaciones();
            Instalacion instalacion = manejadorInstalaciones.obtenerPorCongreso(congreso.getNombre());
            request.setAttribute("instalacionAtributo", instalacion);

            //Obtener comite cientifico
            ManejadorDeParticipaciones manejadorParticipaciones = new ManejadorDeParticipaciones();
            Participacion[] comite = manejadorParticipaciones.obtenerComite(congreso.getNombre());
            request.setAttribute("comiteAtributo", comite);

            request.setAttribute("congreso", congreso);
            
            //Obtener participantes inscritos
            ManejadorDeInscripciones manejadorInscripciones = new ManejadorDeInscripciones();
            Inscripcion[] inscripciones = manejadorInscripciones.obtenerPorCongreso(congreso.getNombre());
            request.setAttribute("inscripcionesAtributo", inscripciones);
            
            request.getRequestDispatcher("congresos/congreso.jsp").forward(request, response);
            
        } catch (AccesoDeDatosException | ActividadInvalidaException | UsuarioInvalidoException e) {
            try {
                request.setAttribute("errorAtributo", e.getMessage());
                String congresoNombre = request.getParameter("congreso");
                ManejadorDeCongresos manejadorCongresos = new ManejadorDeCongresos();
                Congreso congreso = manejadorCongresos.obtenerCongreso(congresoNombre);
                
                ManejadorDeInstalaciones manejadorInstalaciones = new ManejadorDeInstalaciones();
                Salon[] salones = manejadorInstalaciones.obtenerSalones(congreso.getInstalacionId());

                request.setAttribute("congresoAtributo", congreso);
                request.setAttribute("salonesAtributo", salones);
                
                ManejadorDeParticipaciones mp = new ManejadorDeParticipaciones();
                Participacion[] posiblesEncargados = mp.obtenerPosiblesEncargados(congreso);
                request.setAttribute("posiblesEncargadosAtributo", posiblesEncargados);
                
                String esTrabajo = request.getParameter("esTrabajo");
                if(!StringUtils.isBlank(esTrabajo) && esTrabajo.equals("true")){
                    request.setAttribute("esTrabajo", esTrabajo);
                }

                request.getRequestDispatcher("actividades/crear-actividad.jsp").forward(request, response);

            } catch (AccesoDeDatosException ex) {
                request.setAttribute("errorAtributo", ex.getMessage());
                request.getRequestDispatcher("actividades/crear-actividad.jsp").forward(request, response);
            }
        }
    }
}