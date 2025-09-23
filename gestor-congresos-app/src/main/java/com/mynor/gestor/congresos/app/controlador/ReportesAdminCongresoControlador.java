/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeActividades;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeCongresos;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeInstalaciones;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeParticipaciones;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.EntidadInvalidaException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Asistencia;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.FiltrosAsistencia;
import com.mynor.gestor.congresos.app.modelo.FiltrosCongreso;
import com.mynor.gestor.congresos.app.modelo.Participacion;
import com.mynor.gestor.congresos.app.modelo.RolSistema;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import com.mynor.gestor.congresos.app.param.FiltrosAsistenciaParametros;
import com.mynor.gestor.congresos.app.param.FiltrosCongresoParametros;
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
            
                //Autorizacion
                Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
                if(usuarioActual == null || usuarioActual.getRol() != RolSistema.ADMIN_CONGRESOS) throw new UsuarioInvalidoException("Sin permisos");
                
                String rol = request.getParameter("rol");

                ManejadorDeParticipaciones mp = new ManejadorDeParticipaciones();
                Participacion[] participaciones = mp.obtenerPorCreadorDeCongreso(usuarioActual.getId(), rol);
                
                request.setAttribute("participacionesAtributo", participaciones);
                request.getRequestDispatcher("reportes/reporte-participantes.jsp").forward(request, response);
                
            } catch (AccesoDeDatosException | UsuarioInvalidoException | IllegalArgumentException e) {
                request.setAttribute("errorAtributo", e.getMessage());
                request.getRequestDispatcher("reportes/reporte-participantes.jsp").forward(request, response);
            }
        }else if(!StringUtils.isBlank(reporte) && reporte.equals("asistencias")){
            
            try {
                //Autorizacion
                Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
                if(usuarioActual == null || usuarioActual.getRol() != RolSistema.ADMIN_CONGRESOS) throw new UsuarioInvalidoException("Sin permisos");
                
                FiltrosAsistenciaParametros filtrosAsistenciaParam = new FiltrosAsistenciaParametros(request);
                FiltrosAsistencia filtros = filtrosAsistenciaParam.toEntidad();
                
                ManejadorDeActividades ma = new ManejadorDeActividades();
                Asistencia[] asistencias = ma.obtenerAsistencias(filtros, usuarioActual);
                
                ManejadorDeInstalaciones mi = new ManejadorDeInstalaciones();
                
                ManejadorDeCongresos mc = new ManejadorDeCongresos();
                Congreso[] congresos = mc.obtenerCongresos(usuarioActual.getId());
                
                request.setAttribute("actividadesAtributo", ma.obtenerPorCongresos(congresos));
                request.setAttribute("salonesAtributo", mi.obtenerPorCongresos(congresos));
                request.setAttribute("asistenciasAtributo", asistencias);
                request.getRequestDispatcher("reportes/reporte-asistencias.jsp").forward(request, response);
                
            } catch (AccesoDeDatosException | EntidadInvalidaException e) {
                
                try {
                    //Autorizacion
                    Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
                    if(usuarioActual == null || usuarioActual.getRol() != RolSistema.ADMIN_CONGRESOS) throw new UsuarioInvalidoException("Sin permisos");
                    
                    request.setAttribute("errorAtributo", e.getMessage());
                    
                    ManejadorDeActividades ma = new ManejadorDeActividades();
                    
                    ManejadorDeInstalaciones mi = new ManejadorDeInstalaciones();
                    
                    ManejadorDeCongresos mc = new ManejadorDeCongresos();
                    Congreso[] congresos = mc.obtenerCongresos(usuarioActual.getId());
                    
                    request.setAttribute("actividadesAtributo", ma.obtenerPorCongresos(congresos));
                    request.setAttribute("salonesAtributo", mi.obtenerPorCongresos(congresos));
                    
                    request.getRequestDispatcher("reportes/reporte-asistencias.jsp").forward(request, response);
                } catch (UsuarioInvalidoException | AccesoDeDatosException ex) {
                    request.setAttribute("errorAtributo", ex.getMessage());
                    request.getRequestDispatcher("reportes/reporte-asistencias.jsp").forward(request, response);
                }
            }
            
        }else if(!StringUtils.isBlank(reporte) && reporte.equals("reservaciones")){
            
            try {
                
                //Autorizacion
                Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
                if(usuarioActual == null || usuarioActual.getRol() != RolSistema.ADMIN_CONGRESOS) throw new UsuarioInvalidoException("Sin permisos");

                String actividadNombre = request.getParameter("actividad");
               
                ManejadorDeCongresos mc = new ManejadorDeCongresos();
                Congreso[] congresos = mc.obtenerCongresos(usuarioActual.getId());
                
                ManejadorDeActividades ma = new ManejadorDeActividades();
                request.setAttribute("actividadesAtributo", ma.obtenerPorCongresos(congresos));
                
                request.setAttribute("reservasAtributo", ma.obtenerReservacionesPorCongresos(congresos, actividadNombre));
                request.getRequestDispatcher("reportes/reporte-reservas.jsp").forward(request, response);
                
            } catch (AccesoDeDatosException | UsuarioInvalidoException e) {
                try {
                    //Autorizacion
                    Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
                    if(usuarioActual == null || usuarioActual.getRol() != RolSistema.ADMIN_CONGRESOS) throw new UsuarioInvalidoException("Sin permisos");
                                        
                    ManejadorDeCongresos mc = new ManejadorDeCongresos();
                    Congreso[] congresos = mc.obtenerCongresos(usuarioActual.getId());
                    
                    ManejadorDeActividades ma = new ManejadorDeActividades();
                    request.setAttribute("actividadesAtributo", ma.obtenerPorCongresos(congresos));
                    
                    request.setAttribute("errorAtributo", e.getMessage());
                    request.getRequestDispatcher("reportes/reporte-reservas.jsp").forward(request, response);
                } catch (UsuarioInvalidoException | AccesoDeDatosException ex) {
                    request.setAttribute("errorAtributo", ex.getMessage());
                    request.getRequestDispatcher("reportes/reporte-reservas.jsp").forward(request, response);
                }
            }
            
        }else if(!StringUtils.isBlank(reporte) && reporte.equals("ganancias")){
            try {
                //Autorizacion
                Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
                if(usuarioActual == null || usuarioActual.getRol() != RolSistema.ADMIN_CONGRESOS) throw new UsuarioInvalidoException("Sin permisos");


                FiltrosCongresoParametros filtrosParam = new FiltrosCongresoParametros(request);
                FiltrosCongreso filtros = filtrosParam.toEntidad();

                ManejadorDeCongresos mc = new ManejadorDeCongresos();
                Congreso[] congresos = mc.obtenerCongresos(filtros);

                if(!StringUtils.isBlank(reporte) && reporte.equals("ganancias")){
                    for (Congreso congreso : congresos) {
                        congreso.setPagos(mc.obtenerPagos(congreso.getNombre()));
                    }

                    request.setAttribute("reporte", reporte);
                    request.setAttribute("esAdminCongresos", true);
                }

                request.setAttribute("congresosAtributo", mc.ordenarPorGanancias(congresos));
                request.getRequestDispatcher("reportes/reporte-congresos.jsp").forward(request, response);
            } catch (AccesoDeDatosException | EntidadInvalidaException e) {
                try {
                request.setAttribute("errorAtributo", e.getMessage());
                
                //Autorizacion
                Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
                if(usuarioActual == null || usuarioActual.getRol() != RolSistema.ADMIN_CONGRESOS) throw new UsuarioInvalidoException("Sin permisos");
                
                ManejadorDeCongresos mc = new ManejadorDeCongresos();
                Congreso[] congresos = mc.obtenerCongresos();
                if(!StringUtils.isBlank(reporte) && reporte.equals("ganancias")){
                    for (Congreso congreso : congresos) {
                        congreso.setPagos(mc.obtenerPagos(congreso.getNombre()));
                    }

                    request.setAttribute("reporte", reporte);
                    request.setAttribute("esAdminCongresos", true);
                }

                request.setAttribute("congresosAtributo", mc.ordenarPorGanancias(congresos));
                
                request.getRequestDispatcher("reportes/reporte-congresos.jsp").forward(request, response);
            } catch (AccesoDeDatosException | UsuarioInvalidoException ex) {
                request.setAttribute("errorAtributo", ex.getMessage());
                request.getRequestDispatcher("reportes/reporte-congresos.jsp").forward(request, response);
            }
            }
        }
    }

}