/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeCongresos;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.RolSistema;
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
@WebServlet(name = "ConvocatoriaServlet", urlPatterns = {"/convocatoria"})
public class ConvocatoriaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String convocandoStr = request.getParameter("convocando");
            Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");
            
            if(usuarioActual == null || usuarioActual.getRol() != RolSistema.ADMIN_CONGRESOS) throw new UsuarioInvalidoException("Sin permiso");
            
            ManejadorDeCongresos mc = new ManejadorDeCongresos();
            Congreso congreso = mc.obtenerCongreso(request.getParameter("congreso"));
            
            if(!congreso.getCreador().equals(usuarioActual.getId())) throw new UsuarioInvalidoException("Sin permiso");
            
            congreso.setConvocando(!Boolean.parseBoolean(convocandoStr));
            
            mc.actualizarConvocatoria(congreso);
            
            response.sendRedirect("congresos?nombre=" + congreso.getNombre());
            
        } catch (AccesoDeDatosException | UsuarioInvalidoException e) {
            response.sendRedirect("congresos?nombre=" + request.getParameter("congreso"));
        }
    }

}
