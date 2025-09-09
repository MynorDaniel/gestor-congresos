/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.UsuarioBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.dominio.Usuario;
import com.mynor.gestor.congresos.app.modelo.fabricacionpura.RolSistema;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mynordma
 */
public class RegistroDeUsuario {

    public Usuario registrar(Usuario usuarioNuevo) throws UsuarioInvalidoException, AccesoDeDatosException {
        UsuarioBD usuarioBD = new UsuarioBD();
        
        // Verificar que el id no exista
        Map<String, String> filtros = new HashMap<>();
        filtros.put("id", usuarioNuevo.getId());
        
        Usuario[] coincidenciasID = usuarioBD.leer(filtros);
        if(coincidenciasID.length > 0) throw new UsuarioInvalidoException("ID en uso");
        
        // Verificar que el correo no exista
        Map<String, String> filtros2 = new HashMap<>();
        filtros2.put("correo", usuarioNuevo.getCorreo());
        
        Usuario[] coincidenciasCorreo = usuarioBD.leer(filtros2);
        if(coincidenciasCorreo.length > 0) throw new UsuarioInvalidoException("Correo en uso");
        
        //Asignar permisos
        usuarioNuevo.setRol(RolSistema.PARTICIPANTE);
        usuarioNuevo.setActivado(true);
        
        // Registrar el usuario
        return usuarioBD.crear(usuarioNuevo);
    }
    
}
