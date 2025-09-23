/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.AfiliacionBD;
import com.mynor.gestor.congresos.app.basededatos.InstitucionBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Afiliacion;
import com.mynor.gestor.congresos.app.modelo.Institucion;

/**
 *
 * @author mynordma
 */
public class ManejadorDeInstituciones extends Manejador {
    
    private final InstitucionBD institucionBD;
    
    public ManejadorDeInstituciones(){
        institucionBD = new InstitucionBD();
    }

    public Institucion[] obtenerTodas() throws AccesoDeDatosException {
        return institucionBD.leer();
    }

    public Institucion[] obtenerPorUsuario(String id) throws AccesoDeDatosException {
        AfiliacionBD afiliacionBD = new AfiliacionBD();
        Afiliacion[] afiliaciones = afiliacionBD.leerPorUsuario(id);
        
        Institucion[] instituciones = new Institucion[afiliaciones.length];
        for (int i = 0; i < afiliaciones.length; i++) {
            instituciones[i] = institucionBD.leerPorId(afiliaciones[i].getInstitucion().getId());
            
        }
        return instituciones;
    }

    public void crear(Institucion institucion) throws AccesoDeDatosException {
        institucionBD.crear(institucion);
    }

    public void borrar(int id) throws AccesoDeDatosException {
        Institucion institucion = new Institucion();
        institucion.setId(id);
        institucionBD.borrar(institucion);
    }
    
}
