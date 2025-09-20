/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import com.mynor.gestor.congresos.app.seguridad.Seguridad;
import jakarta.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mynordma
 */
public class UsuarioParametros extends Validador implements EntidadParseador<Usuario> {
    
    private final String id;
    private final String clave;
    private final String nombre;
    private final String numero;
    private final String correo;
    private final String institucionStr;

    public UsuarioParametros(HttpServletRequest request) {
        clave    = request.getParameter("clave");
        correo   = request.getParameter("correo");
        id = request.getParameter("id");
        nombre   = request.getParameter("nombre");
        numero = request.getParameter("numero");
        institucionStr = request.getParameter("institucion");
    }

    @Override
    public Usuario toEntidad() throws UsuarioInvalidoException {
        if(!correoValido(correo)) throw new UsuarioInvalidoException("Correo inválido");
        if(StringUtils.isBlank(clave)) throw new UsuarioInvalidoException("Clave inválida");
        if(!longitudValida(id, 30)) throw new UsuarioInvalidoException("El id debe tener una longitud menor o igual a 30");
        if(!longitudValida(nombre, 200)) throw new UsuarioInvalidoException("El nombre debe tener una longitud menor o igual a 200");
        if(!numeroTelefonicoValido(numero)) throw new UsuarioInvalidoException("Verifica que el numero sea correcto o que tenga una longitud menor o igual a 30");
        if(!esEnteroPositivo(institucionStr)) throw new UsuarioInvalidoException("Institución inválida");
        
        Seguridad seguridad = new Seguridad();
        
        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        try {
            usuario.setClave(seguridad.hashear(clave));
        } catch (NoSuchAlgorithmException ex) {
            System.out.println(ex.getMessage());
            throw new UsuarioInvalidoException("Error al guardar la clave");
        }
        usuario.setId(id);
        usuario.setNombre(nombre);
        usuario.setNumero(numero);
        usuario.setInstitucion(Integer.parseInt(institucionStr));
        
        return usuario;
    }
    
    private boolean numeroTelefonicoValido(String numero){
        return !StringUtils.isBlank(numero)
                && numero.matches("^\\d+$")
                && numero.trim().length() <= 30;
    }
    
}
