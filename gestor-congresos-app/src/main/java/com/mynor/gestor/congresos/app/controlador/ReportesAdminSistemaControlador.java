/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeCongresos;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeInstituciones;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.EntidadInvalidaException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.FiltrosCongreso;
import com.mynor.gestor.congresos.app.modelo.Institucion;
import com.mynor.gestor.congresos.app.modelo.RolSistema;
import com.mynor.gestor.congresos.app.modelo.Usuario;
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
@WebServlet(name = "ReportesAdminSistemaControlador", urlPatterns = {"/reportes-sistema"})
public class ReportesAdminSistemaControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String reporte = request.getParameter("reporte");
        try {
            //Autorizacion
            Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
            if(usuarioActual == null || usuarioActual.getRol() != RolSistema.ADMIN_SISTEMA) throw new UsuarioInvalidoException("Sin permisos");
            
            
            FiltrosCongresoParametros filtrosParam = new FiltrosCongresoParametros(request);
            FiltrosCongreso filtros = filtrosParam.toEntidad();
            
            ManejadorDeCongresos mc = new ManejadorDeCongresos();
            Congreso[] congresos = mc.obtenerCongresos(filtros);
            
            ManejadorDeInstituciones mi = new ManejadorDeInstituciones();
            Institucion[] instituciones = mi.obtenerTodas();
            
            if(!StringUtils.isBlank(reporte) && reporte.equals("ganancias")){
                for (Congreso congreso : congresos) {
                    congreso.setPagos(mc.obtenerPagos(congreso.getNombre()));
                }
                
                request.setAttribute("reporte", reporte);
            }
            
            request.setAttribute("institucionesAtributo", instituciones);
            request.setAttribute("congresosAtributo", mc.ordenarPorGanancias(congresos));
            request.getRequestDispatcher("reportes/reporte-congresos.jsp").forward(request, response);
            
        } catch (AccesoDeDatosException | EntidadInvalidaException e) {
            try {
                request.setAttribute("errorAtributo", e.getMessage());
                
                //Autorizacion
                Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
                if(usuarioActual == null || usuarioActual.getRol() != RolSistema.ADMIN_SISTEMA) throw new UsuarioInvalidoException("Sin permisos");
                
                ManejadorDeCongresos mc = new ManejadorDeCongresos();
                Congreso[] congresos = mc.obtenerCongresos();
                ManejadorDeInstituciones mi = new ManejadorDeInstituciones();
                Institucion[] instituciones = mi.obtenerTodas();
                
                if(!StringUtils.isBlank(reporte) && reporte.equals("ganancias")){
                    for (Congreso congreso : congresos) {
                        congreso.setPagos(mc.obtenerPagos(congreso.getNombre()));
                    }

                    request.setAttribute("reporte", reporte);
                }

                request.setAttribute("institucionesAtributo", instituciones);
                request.setAttribute("congresosAtributo", mc.ordenarPorGanancias(congresos));
                
                request.getRequestDispatcher("reportes/reporte-congresos.jsp").forward(request, response);
            } catch (AccesoDeDatosException | UsuarioInvalidoException ex) {
                request.setAttribute("errorAtributo", ex.getMessage());
                request.getRequestDispatcher("reportes/reporte-congresos.jsp").forward(request, response);
            }
            
        }
    }
    
    
}
