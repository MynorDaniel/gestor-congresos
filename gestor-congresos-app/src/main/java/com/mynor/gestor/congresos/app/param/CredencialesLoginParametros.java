/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.fabricacionpura.CredencialesLogin;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mynordma
 */
public class CredencialesLoginParametros implements RequestParseador {
    
    private String correo;
    private String clave;

    @Override
    public void asignarValoresDesdeRequest(HttpServletRequest request) {
        clave    = request.getParameter("clave");
        correo   = request.getParameter("correo");
    }

    public CredencialesLogin toCredencialesLogin() throws UsuarioInvalidoException {
        if(!correoValido(correo)) throw new UsuarioInvalidoException("Correo inválido");
        if(!claveValida(clave)) throw new UsuarioInvalidoException("La clave debe tener una longitud menor o igual a 100");
        
        CredencialesLogin credenciales = new CredencialesLogin();
        credenciales.setCorreo(correo);
        credenciales.setClave(clave);
        
        return credenciales;
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
