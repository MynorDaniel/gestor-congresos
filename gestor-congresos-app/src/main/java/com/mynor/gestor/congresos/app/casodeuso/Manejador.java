/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.UsuarioBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.FiltrosUsuario;
import com.mynor.gestor.congresos.app.modelo.RolSistema;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import java.time.LocalDate;

/**
 *
 * @author mynordma
 */
public abstract class Manejador {
    
    public boolean esAdminDeCongresos(String creador) throws AccesoDeDatosException {
        UsuarioBD usuarioBD = new UsuarioBD();
        FiltrosUsuario filtros = new FiltrosUsuario();
        filtros.setId(creador);
        
        Usuario usuario = usuarioBD.leer(filtros)[0];
        
        return usuario.getRol() == RolSistema.ADMIN_CONGRESOS;
    }
    
    protected boolean fechasLogicas(LocalDate fechaInicio, LocalDate fechaFin) {
        return !fechaInicio.isAfter(fechaFin);
    }
}
