/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.UsuarioBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.FiltrosUsuario;
import com.mynor.gestor.congresos.app.modelo.Usuario;

/**
 *
 * @author mynordma
 */
public class ManejadorDeUsuarios extends Manejador {
    
    private final UsuarioBD usuarioBD;
    
    public ManejadorDeUsuarios(){
        this.usuarioBD = new UsuarioBD();
    }

    public Usuario[] obtenerTodos() throws AccesoDeDatosException {
        return usuarioBD.leer(new FiltrosUsuario());
    }

    public Usuario obtenerPorId(String usuarioId) throws AccesoDeDatosException {
        FiltrosUsuario filtros = new FiltrosUsuario();
        filtros.setId(usuarioId);
        
        return usuarioBD.leer(filtros)[0];
    }
    
}