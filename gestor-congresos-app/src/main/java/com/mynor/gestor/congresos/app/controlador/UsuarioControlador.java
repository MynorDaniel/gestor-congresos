/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeInstituciones;
import com.mynor.gestor.congresos.app.casodeuso.RegistroDeUsuario;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Institucion;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import com.mynor.gestor.congresos.app.param.UsuarioParametros;
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
@WebServlet(name = "UsuarioControlador", urlPatterns = {"/usuarios"})
public class UsuarioControlador extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            UsuarioParametros usuarioParam = new UsuarioParametros(request);
            Usuario usuarioNuevo = usuarioParam.toEntidad();
                        
            RegistroDeUsuario registro = new RegistroDeUsuario();
            Usuario usuarioRegistrado = registro.registrar(usuarioNuevo);
            
            request.setAttribute("infoAtributo", "Usuario " + usuarioRegistrado.getNombre() + " registrado con éxito");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            
        } catch (UsuarioInvalidoException | AccesoDeDatosException ex) {
            System.out.println(ex.getMessage());
            request.setAttribute("errorAtributo", ex.getMessage());
            try {
                ManejadorDeInstituciones manejadorInstituciones = new ManejadorDeInstituciones();
                Institucion[] instituciones = manejadorInstituciones.obtenerTodas();
                request.setAttribute("institucionesAtributo", instituciones);
                request.getRequestDispatcher("usuario/registroUsuario.jsp").forward(request, response);

            } catch (AccesoDeDatosException e) {
                request.setAttribute("errorAtributo", e.getMessage());
                request.getRequestDispatcher("usuario/registroUsuario.jsp").forward(request, response);
            }
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

}
