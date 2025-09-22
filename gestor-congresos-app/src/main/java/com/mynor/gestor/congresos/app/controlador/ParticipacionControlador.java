/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mynor.gestor.congresos.app.controlador;

import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeCongresos;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeInstituciones;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeParticipaciones;
import com.mynor.gestor.congresos.app.casodeuso.ManejadorDeUsuarios;
import com.mynor.gestor.congresos.app.casodeuso.RegistroDeUsuario;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.ParticipacionInvalidaException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.Institucion;
import com.mynor.gestor.congresos.app.modelo.Participacion;
import com.mynor.gestor.congresos.app.modelo.Rol;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import com.mynor.gestor.congresos.app.param.UsuarioParametros;
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
@WebServlet(name = "ParticipacionControlador", urlPatterns = {"/participaciones"})
public class ParticipacionControlador extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        try {
            
            String usuarioExistenteId = request.getParameter("usuarioExistenteId");
            
            if(!StringUtils.isBlank(usuarioExistenteId)){
                String congresoNombre = request.getParameter("congreso");
                ManejadorDeCongresos mc = new ManejadorDeCongresos();
                Congreso congreso = mc.obtenerCongreso(congresoNombre);
                
                Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");

                if(usuarioActual == null ||usuarioActual.getId() == null || !usuarioActual.getId().equals(congreso.getCreador())) throw new UsuarioInvalidoException("Sin permisos para modificar este congereso");

                ManejadorDeParticipaciones manejadorParticipaciones = new ManejadorDeParticipaciones();
                Participacion p = new Participacion();
                p.setCongresoNombre(congreso.getNombre());
                p.setRol(Rol.PONENTE_INVITADO);
                p.setUsuarioId(usuarioExistenteId);
                manejadorParticipaciones.crear(p);

                String rol = request.getParameter("rol");
                System.out.println("Rol: " + rol);
                if(rol != null){
                    request.setAttribute("rolRegistroAtributo", rol);
                    
                    //Obtener usuarios
                    ManejadorDeUsuarios manejadorUsuarios = new ManejadorDeUsuarios();
                    Usuario[] usuarios = manejadorUsuarios.obtenerTodos();
                    request.setAttribute("usuariosAtributo", usuarios);
                }

                ManejadorDeInstituciones manejadorInstituciones = new ManejadorDeInstituciones();
                Institucion[] instituciones = manejadorInstituciones.obtenerTodas();
                request.setAttribute("institucionesAtributo", instituciones);

                request.setAttribute("infoAtributo", "Usuario " + usuarioExistenteId + " registrado con éxito");
                request.getRequestDispatcher("usuario/registroUsuario.jsp").forward(request, response);
            }else{
            
                UsuarioParametros usuarioParam = new UsuarioParametros(request);
                Usuario usuarioNuevo = usuarioParam.toEntidad();

                RegistroDeUsuario registro = new RegistroDeUsuario();
                Usuario usuarioRegistrado = registro.registrar(usuarioNuevo);

                String congresoNombre = request.getParameter("congreso");
                ManejadorDeCongresos mc = new ManejadorDeCongresos();
                Congreso congreso = mc.obtenerCongreso(congresoNombre);

                System.out.println("Nombre:");
                System.out.println(congreso.getNombre());

                Usuario usuarioActual = (Usuario) request.getSession().getAttribute("usuarioSession");

                if(usuarioActual == null ||usuarioActual.getId() == null || !usuarioActual.getId().equals(congreso.getCreador())) throw new UsuarioInvalidoException("Sin permisos para modificar este congereso");

                ManejadorDeParticipaciones manejadorParticipaciones = new ManejadorDeParticipaciones();
                Participacion p = new Participacion();
                p.setCongresoNombre(congreso.getNombre());
                p.setRol(Rol.PONENTE_INVITADO);
                p.setUsuario(usuarioRegistrado);
                p.setUsuarioId(usuarioRegistrado.getId());
                manejadorParticipaciones.crear(p);

                String rol = request.getParameter("rol");
                System.out.println("Rol: " + rol);
                if(rol != null){
                    request.setAttribute("rolRegistroAtributo", rol);
                    
                    //Obtener usuarios
                    ManejadorDeUsuarios manejadorUsuarios = new ManejadorDeUsuarios();
                    Usuario[] usuarios = manejadorUsuarios.obtenerTodos();
                    request.setAttribute("usuariosAtributo", usuarios);
                }

                ManejadorDeInstituciones manejadorInstituciones = new ManejadorDeInstituciones();
                Institucion[] instituciones = manejadorInstituciones.obtenerTodas();
                request.setAttribute("institucionesAtributo", instituciones);

                request.setAttribute("infoAtributo", "Usuario " + usuarioRegistrado.getNombre() + " registrado con éxito");
                request.getRequestDispatcher("usuario/registroUsuario.jsp").forward(request, response);
            }
        } catch (UsuarioInvalidoException | AccesoDeDatosException | ParticipacionInvalidaException ex) {
            System.out.println(ex.getMessage());
            request.setAttribute("errorAtributo", ex.getMessage());
            String rol = request.getParameter("rol");
            try {
                if(rol != null){
                    request.setAttribute("rolRegistroAtributo", rol);
                    //Obtener usuarios
                    ManejadorDeUsuarios manejadorUsuarios = new ManejadorDeUsuarios();
                    Usuario[] usuarios = manejadorUsuarios.obtenerTodos();
                    request.setAttribute("usuariosAtributo", usuarios);
                }
                
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
}
