/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.UsuarioBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.RolSistema;

/**
 *
 * @author mynordma
 */
public class Manejador {
    
    protected boolean esAdminDeCongresos(String creador) throws AccesoDeDatosException {
        UsuarioBD usuarioBD = new UsuarioBD();
        RolSistema rol = usuarioBD.obtenerRolDeUsuario(creador);
        
        return rol == RolSistema.ADMIN_CONGRESOS;
    }
}
