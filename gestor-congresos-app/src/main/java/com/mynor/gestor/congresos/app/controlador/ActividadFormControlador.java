/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeCongresos;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeInstalaciones;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.Salon;
import com.mynor.gestor.congresos.app.modelo.Usuario;
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
@WebServlet(name = "ActividadFormControlador", urlPatterns = {"/crear-actividad-form"})
public class ActividadFormControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String congresoNombre = request.getParameter("congresoNombre");
            ManejadorDeCongresos manejadorCongresos = new ManejadorDeCongresos();
            Congreso congreso = manejadorCongresos.obtenerCongreso(congresoNombre);
            
            if(!congreso.getCreador().equals(((Usuario) request.getSession().getAttribute("usuarioSession")).getId())) throw new UsuarioInvalidoException("No puedes modificar este congreso");
            
            ManejadorDeInstalaciones manejadorInstalaciones = new ManejadorDeInstalaciones();
            Salon[] salones = manejadorInstalaciones.obtenerSalones(congreso.getInstalacionId());
            
            request.setAttribute("congresoAtributo", congreso);
            request.setAttribute("salonesAtributo", salones);
            
            request.getRequestDispatcher("actividades/crear-actividad.jsp").forward(request, response);
            
        } catch (AccesoDeDatosException | UsuarioInvalidoException e) {
            request.setAttribute("errorAtributo", e.getMessage());
            request.getRequestDispatcher("actividades/crear-actividad.jsp").forward(request, response);
        }
    }
}
