/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.CongresoBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.dominio.Congreso;
import java.util.Map;

/**
 *
 * @author mynordma
 */
public class ManejadorDeCongresos {

    public Congreso[] obtenerCongresos(Map<String, String> filtros) throws AccesoDeDatosException {
        CongresoBD congresoBD = new CongresoBD();
        return congresoBD.leer(filtros);
    }
    
}
