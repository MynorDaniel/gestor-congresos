/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.CredencialesLogin;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mynordma
 */
public class CredencialesLoginParametros extends Validador implements EntidadParseador<CredencialesLogin> {
    
    private final String correo;
    private final String clave;

    public CredencialesLoginParametros(HttpServletRequest request) {
        clave = request.getParameter("clave");
        correo = request.getParameter("correo");
    }

    @Override
    public CredencialesLogin toEntidad() throws UsuarioInvalidoException {
        if(!correoValido(correo)) throw new UsuarioInvalidoException("Correo inválido");
        if(StringUtils.isBlank(clave)) throw new UsuarioInvalidoException("Clave inválida");
        
        CredencialesLogin credenciales = new CredencialesLogin();
        credenciales.setCorreo(correo);
        credenciales.setClave(clave);
        
        return credenciales;
    }
}
