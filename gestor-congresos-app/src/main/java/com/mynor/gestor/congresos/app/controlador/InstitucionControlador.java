/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeInstituciones;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.InstitucionInvalidaException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Institucion;
import com.mynor.gestor.congresos.app.modelo.RolSistema;
import com.mynor.gestor.congresos.app.modelo.Usuario;
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
@WebServlet(name = "InstitucionControlador", urlPatterns = {"/instituciones"})
public class InstitucionControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            //Autorizacion
            Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
            if(usuarioActual == null || usuarioActual.getRol() != RolSistema.ADMIN_SISTEMA) throw new UsuarioInvalidoException("Sin permisos");
            
            ManejadorDeInstituciones manejadorInst = new ManejadorDeInstituciones();
            Institucion[] instituciones = manejadorInst.obtenerTodas();
            
            request.setAttribute("institucionesAtributo", instituciones);
            request.getRequestDispatcher("instituciones/instituciones.jsp").forward(request, response);
        } catch (AccesoDeDatosException | UsuarioInvalidoException e) {
            request.setAttribute("errorAtributo", e.getMessage());
            request.getRequestDispatcher("instituciones/instituciones.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if(request.getParameter("metodo") != null && request.getParameter("metodo").equals("delete")){
            doDelete(request, response);
        }else{
            try {
                //Autorizacion
                Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
                if(usuarioActual == null || usuarioActual.getRol() != RolSistema.ADMIN_SISTEMA) throw new UsuarioInvalidoException("Sin permisos");

                String nombre = request.getParameter("nombre");
                if(StringUtils.isBlank(nombre)) throw new InstitucionInvalidaException("Nombre incorrecto");

                Institucion institucion = new Institucion();
                institucion.setNombre(nombre);

                ManejadorDeInstituciones mi = new ManejadorDeInstituciones();
                mi.crear(institucion);

                response.sendRedirect("instituciones");
            } catch (AccesoDeDatosException | InstitucionInvalidaException | UsuarioInvalidoException e) {
                try {
                    request.setAttribute("errorAtributo", e.getMessage());
                    ManejadorDeInstituciones manejadorInst = new ManejadorDeInstituciones();
                    Institucion[] instituciones = manejadorInst.obtenerTodas();

                    request.setAttribute("institucionesAtributo", instituciones);
                    request.getRequestDispatcher("instituciones/instituciones.jsp").forward(request, response);
                } catch (AccesoDeDatosException ex) {
                    request.setAttribute("errorAtributo", e.getMessage());
                    request.getRequestDispatcher("instituciones/instituciones.jsp").forward(request, response);
                }
            }
        }
        
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            //Autorizacion
            Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
            if(usuarioActual == null || usuarioActual.getRol() != RolSistema.ADMIN_SISTEMA) throw new UsuarioInvalidoException("Sin permisos");

            String id = request.getParameter("id");
            if(StringUtils.isBlank(id)) throw new InstitucionInvalidaException("Institución inválida");
            
            ManejadorDeInstituciones manejadorInst = new ManejadorDeInstituciones();
            manejadorInst.borrar(Integer.parseInt(id));
            
            response.sendRedirect("instituciones");
        } catch (AccesoDeDatosException | InstitucionInvalidaException | UsuarioInvalidoException | NumberFormatException e) {
            try {
                    request.setAttribute("errorAtributo", e.getMessage());
                    ManejadorDeInstituciones manejadorInst = new ManejadorDeInstituciones();
                    Institucion[] instituciones = manejadorInst.obtenerTodas();

                    request.setAttribute("institucionesAtributo", instituciones);
                    request.getRequestDispatcher("instituciones/instituciones.jsp").forward(request, response);
                } catch (AccesoDeDatosException ex) {
                    request.setAttribute("errorAtributo", e.getMessage());
                    request.getRequestDispatcher("instituciones/instituciones.jsp").forward(request, response);
                }
        }
    }
    
    

}
