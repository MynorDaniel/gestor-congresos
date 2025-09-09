/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.UsuarioBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.dominio.Usuario;
import com.mynor.gestor.congresos.app.modelo.fabricacionpura.CredencialesLogin;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mynordma
 */
public class Login {

    public Usuario loguear(CredencialesLogin credenciales) throws UsuarioInvalidoException, AccesoDeDatosException {
                
        Map<String, String> filtros = new HashMap<>();
        filtros.put("correo", credenciales.getCorreo());
        filtros.put("clave", credenciales.getClave());
        filtros.put("activado", "1");
        
        UsuarioBD usuarioBD = new UsuarioBD();
        Usuario[] coincidencias = usuarioBD.leer(filtros);
        
        if(coincidencias.length < 1) throw new UsuarioInvalidoException("Credenciales inválidas");
        
        return coincidencias[0];
    }
    
}
