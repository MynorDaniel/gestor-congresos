/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.creador;

import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author mynordma
 */
public class MapeadorDeUsuarios {

    public Usuario mapear(HttpServletRequest request) throws UsuarioInvalidoException {
        
        //String id    = request.getParameter("id");
        String clave    = request.getParameter("clave");
        //String nombre   = request.getParameter("nombre");
        //String numero   = request.getParameter("numero");
        //String activado = request.getParameter("activado");
        String correo   = request.getParameter("correo");
        
        if(!correoValido(correo)) throw new UsuarioInvalidoException("Correo inválido");
        if(!claveValida(clave)) throw new UsuarioInvalidoException("La clave debe tener una longitud menor o igual a 100");
        
        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setClave(clave);
        
        return usuario;
    }
    
    private boolean IDValido(String id){
        return id != null 
                && !id.trim().isEmpty()
                && id.trim().length() <= 30;
    }
    
    private boolean claveValida(String clave){
        return clave != null 
                && !clave.trim().isEmpty()
                && clave.trim().length() <= 100;
    }
    
    private boolean correoValido(String correo){
        return correo != null 
                && !correo.trim().isEmpty()
                && correo.trim().length() <= 320
                && correo.matches(".+@.+\\..+");
    }
    
}