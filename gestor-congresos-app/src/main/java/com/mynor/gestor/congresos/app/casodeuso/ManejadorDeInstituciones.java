/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.InstitucionBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Institucion;

/**
 *
 * @author mynordma
 */
public class ManejadorDeInstituciones extends Manejador {

    public Institucion[] obtenerTodas() throws AccesoDeDatosException {
        InstitucionBD institucionBD = new InstitucionBD();
        return institucionBD.leer();
    }
    
}
