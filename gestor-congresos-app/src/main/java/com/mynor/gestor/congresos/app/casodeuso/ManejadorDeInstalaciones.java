/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.InstalacionBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.Instalacion;
import com.mynor.gestor.congresos.app.modelo.Salon;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mynordma
 */
public class ManejadorDeInstalaciones extends Manejador {
    
    private final InstalacionBD instalacionBD;

    public ManejadorDeInstalaciones() {
        this.instalacionBD = new InstalacionBD();
    }

    public void crear(Instalacion instalacion, Usuario creador) throws AccesoDeDatosException, UsuarioInvalidoException {
        //Verificar que el rol sea administrador de congresos
        if(!super.esAdminDeCongresos(creador.getId())) throw new UsuarioInvalidoException("No tienes autorización para crear una instalacion");
        
        //Crear la instalacion
        instalacionBD.crear(instalacion);
    }

    public Instalacion[] obtenerInstalaciones(Usuario usuario) throws AccesoDeDatosException, UsuarioInvalidoException {
        //Verificar que el rol sea admin congresos
        if(!super.esAdminDeCongresos(usuario.getId())) throw new UsuarioInvalidoException("No tienes autorización");
        
        //Verificar que no exista
        
        return instalacionBD.leer();
    }
    
    public Salon[] obtenerSalones(int instalacionId) throws AccesoDeDatosException {
        return instalacionBD.leerSalones(instalacionId);
    }

    public Instalacion obtenerPorCongreso(String nombreCongreso) throws AccesoDeDatosException {
        return instalacionBD.leer(nombreCongreso);
    }

    public Salon[] obtenerPorCongresos(Congreso[] congresos) throws AccesoDeDatosException {
        List<Salon> salones = new LinkedList<>();
        for (Congreso congreso : congresos) {
            salones.addAll(Arrays.asList(this.obtenerSalones(congreso.getInstalacionId())));
        }
        return salones.toArray(Salon[]::new);
    }
}
