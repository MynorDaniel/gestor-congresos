/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.InstalacionBD;
import com.mynor.gestor.congresos.app.basededatos.SalonBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.dominio.Instalacion;
import com.mynor.gestor.congresos.app.modelo.dominio.Salon;
import com.mynor.gestor.congresos.app.modelo.dominio.Usuario;
import com.mynor.gestor.congresos.app.modelo.fabricacionpura.RolSistema;
import java.util.Map;

/**
 *
 * @author mynordma
 */
public class ManejadorDeInstalaciones {

    public void crear(Instalacion instalacion, Usuario creador) throws AccesoDeDatosException, UsuarioInvalidoException {
        //Verificar que el rol sea administrador de congresos
        if(creador.getRol() != RolSistema.ADMIN_CONGRESOS) throw new UsuarioInvalidoException("No tienes autorización para crear una instalacion");
        
        //Crear la instalacion
        InstalacionBD instalacionBD = new InstalacionBD();
        instalacionBD.crear(instalacion);
        
        //Crear salones
        SalonBD salonBD = new SalonBD();
        for (Salon salon : instalacion.getSalones()) {
            salon.setInstalacion(instalacion.getId());
            salonBD.crear(salon);
        }
    }

    public Instalacion[] obtenerInstalaciones(Map<String, String> filtros) throws AccesoDeDatosException {
        //Verificar que el rol sea admin congresos
        
        InstalacionBD instalacionBD = new InstalacionBD();
        return instalacionBD.leer(filtros);
    }
    
}
