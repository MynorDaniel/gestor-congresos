/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.InstalacionBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Instalacion;
import com.mynor.gestor.congresos.app.modelo.Salon;
import com.mynor.gestor.congresos.app.modelo.Usuario;

/**
 *
 * @author mynordma
 */
public class ManejadorDeInstalaciones extends Manejador {

    public void crear(Instalacion instalacion, Usuario creador) throws AccesoDeDatosException, UsuarioInvalidoException {
        //Verificar que el rol sea administrador de congresos
        if(!super.esAdminDeCongresos(creador.getId())) throw new UsuarioInvalidoException("No tienes autorización para crear una instalacion");
        
        //Validar duplicidad
        
        //Crear la instalacion
        InstalacionBD instalacionBD = new InstalacionBD();
        instalacionBD.crear(instalacion);
    }

    public Instalacion[] obtenerInstalaciones(Usuario usuario) throws AccesoDeDatosException, UsuarioInvalidoException {
        //Verificar que el rol sea admin congresos
        if(!super.esAdminDeCongresos(usuario.getId())) throw new UsuarioInvalidoException("No tienes autorización");
        
        //Verificar que no exista
        
        InstalacionBD instalacionBD = new InstalacionBD();
        return instalacionBD.leer();
    }
    
    public Salon[] obtenerSalones(int instalacionId) throws AccesoDeDatosException {
        InstalacionBD instalacionBD = new InstalacionBD();
        return instalacionBD.leerSalones(instalacionId);
    }
}
