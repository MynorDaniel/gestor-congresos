/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.dominio.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mynordma
 */
public class UsuarioParametros implements RequestParseador {
    
    private String id;
    private String clave;
    private String nombre;
    private String numero;
    private String correo;

    @Override
    public void asignarValoresDesdeRequest(HttpServletRequest request) {
        clave    = request.getParameter("clave");
        correo   = request.getParameter("correo");
        id = request.getParameter("id");
        nombre   = request.getParameter("nombre");
        numero = request.getParameter("numero");
    }

    public Usuario toUsuario() throws UsuarioInvalidoException {
        if(!correoValido(correo)) throw new UsuarioInvalidoException("Correo inválido");
        if(!claveValida(clave)) throw new UsuarioInvalidoException("La clave debe tener una longitud menor o igual a 100");
        if(!IDValido(id)) throw new UsuarioInvalidoException("El id debe tener una longitud menor o igual a 30");
        if(!nombreValido(nombre)) throw new UsuarioInvalidoException("El nombre debe tener una longitud menor o igual a 200");
        if(!numeroValido(numero)) throw new UsuarioInvalidoException("Verifica que el numero sea correcto o que tenga una longitud menor o igual a 30");
        
        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setClave(clave);
        usuario.setId(id);
        usuario.setNombre(nombre);
        usuario.setNumero(numero);
        
        return usuario;
    }
    
    private boolean IDValido(String id){
        return !StringUtils.isBlank(id)
                && id.trim().length() <= 30;
    }
    
    private boolean nombreValido(String nombre){
        return !StringUtils.isBlank(nombre)
                && nombre.trim().length() <= 200;
    }
    
    private boolean numeroValido(String numero){
        return !StringUtils.isBlank(numero)
                && numero.matches("^\\d+$")
                && numero.trim().length() <= 30;
    }
    
    private boolean claveValida(String clave){
        return !StringUtils.isBlank(clave)
                && clave.trim().length() <= 100;
    }
    
    private boolean correoValido(String correo){
        return !StringUtils.isBlank(correo)
                && correo.trim().length() <= 320
                && correo.matches(".+@.+\\..+");
    }
    
    
}
