/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeActividades;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeCongresos;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeInstalaciones;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.CongresoInvalidoException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Actividad;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.Instalacion;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import com.mynor.gestor.congresos.app.param.CongresoParametros;
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
@WebServlet(name = "CongresoControlador", urlPatterns = {"/congresos"})
public class CongresoControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            String nombre = request.getParameter("nombre");
            String creador = request.getParameter("creador");

            ManejadorDeCongresos manejador = new ManejadorDeCongresos();
            ManejadorDeActividades manejadorActividades = new ManejadorDeActividades();
            
            if(!StringUtils.isBlank(nombre)){
                Congreso congreso = manejador.obtenerCongreso(nombre);
                request.setAttribute("congreso", congreso);
                Actividad[] actividades = manejadorActividades.obtenerPorCongreso(congreso.getNombre());
                request.setAttribute("actividadesAtributo", actividades);
                
                //pagina congreso
                request.getRequestDispatcher("congresos/congreso.jsp").forward(request, response);
            }else if(!StringUtils.isBlank(creador)){
                Congreso[] congresos = manejador.obtenerCongresos(creador);
                request.setAttribute("congresosAtributo", congresos);
                
                //pagina mis congresos
                request.getRequestDispatcher("congresos/mis-congresos.jsp").forward(request, response);
            }else{
                Congreso[] congresos = manejador.obtenerCongresos();
                request.setAttribute("congresosAtributo", congresos);
                
                //home
                request.getRequestDispatcher("home/home.jsp").forward(request, response);
            }
            
        } catch (AccesoDeDatosException e) {
            request.setAttribute("errorAtributo", e.getMessage());
            request.getRequestDispatcher("home/home.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            CongresoParametros congresoParam = new CongresoParametros(request);
            Congreso congresoNuevo = congresoParam.toEntidad();
                        
            ManejadorDeCongresos manejador = new ManejadorDeCongresos();
            Congreso congresoCreado = manejador.crearCongreso(congresoNuevo);
            
            request.setAttribute("infoAtributo", "Congreso " + congresoCreado.getNombre() + " registrado con éxito");

            try {
                ManejadorDeInstalaciones manejadorInstalaciones = new ManejadorDeInstalaciones();
                Instalacion[] instalaciones = manejadorInstalaciones.obtenerInstalaciones((Usuario) request.getSession().getAttribute("usuarioSession"));

                request.setAttribute("instalacionesAtributo", instalaciones);
                request.getRequestDispatcher("/congresos/crear-congreso.jsp").forward(request, response);
            } catch (AccesoDeDatosException | UsuarioInvalidoException ex2) {
                request.setAttribute("errorAtributo", ex2.getMessage());
                request.getRequestDispatcher("/congresos/crear-congreso.jsp").forward(request, response);
            }
            
        } catch (CongresoInvalidoException | UsuarioInvalidoException | AccesoDeDatosException ex) {
            request.setAttribute("errorAtributo", ex.getMessage());
            try {
                ManejadorDeInstalaciones manejador = new ManejadorDeInstalaciones();
                Instalacion[] instalaciones = manejador.obtenerInstalaciones((Usuario) request.getSession().getAttribute("usuarioSession"));

                request.setAttribute("instalacionesAtributo", instalaciones);
                request.getRequestDispatcher("/congresos/crear-congreso.jsp").forward(request, response);
            } catch (AccesoDeDatosException | UsuarioInvalidoException ex2) {
                request.setAttribute("errorAtributo", ex2.getMessage());
                request.getRequestDispatcher("/congresos/crear-congreso.jsp").forward(request, response);
            }
        }
    }

}