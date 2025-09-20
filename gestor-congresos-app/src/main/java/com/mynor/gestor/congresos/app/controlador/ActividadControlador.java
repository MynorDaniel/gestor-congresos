/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeActividades;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeCongresos;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeInstalaciones;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.ActividadInvalidaException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Actividad;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.Salon;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import com.mynor.gestor.congresos.app.param.ActividadParametros;
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
@WebServlet(name = "ActividadControlador", urlPatterns = {"/actividades"})
public class ActividadControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            ActividadParametros actividadParam = new ActividadParametros(request);
            Actividad actividad = actividadParam.toEntidad();
            
            ManejadorDeCongresos manejadorCongresos = new ManejadorDeCongresos();
            Congreso congreso = manejadorCongresos.obtenerCongreso(actividad.getCongresoNombre());
            
            Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
            if(usuarioActual == null || !congreso.getCreador().equals(usuarioActual.getId())) throw new UsuarioInvalidoException("No puedes modificar este congreso");
            
            ManejadorDeActividades manejadorActividades = new ManejadorDeActividades();
            manejadorActividades.crear(actividad);
            
            Actividad[] actividades = manejadorActividades.obtenerPorCongreso(congreso.getNombre());
            request.setAttribute("actividadesAtributo", actividades);
            
            request.setAttribute("congreso", congreso);
            request.setAttribute("infoAtributo", "Actividad creada exitosamente");
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

                request.getRequestDispatcher("actividades/crear-actividad.jsp").forward(request, response);

            } catch (AccesoDeDatosException ex) {
                request.setAttribute("errorAtributo", ex.getMessage());
                request.getRequestDispatcher("actividades/crear-actividad.jsp").forward(request, response);
            }
        }
    }
}