/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.FiltrosInvalidosException;
import com.mynor.gestor.congresos.app.modelo.FiltrosActividad;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mynordma
 */
public class FiltrosActividadParametros extends Validador implements EntidadParseador<FiltrosActividad> {
    
    private final String nombre;
    private final String congreso;

    public FiltrosActividadParametros(HttpServletRequest request) {
        nombre = request.getParameter("nombre");
        congreso = request.getParameter("congreso");
    }

    @Override
    public FiltrosActividad toEntidad() throws FiltrosInvalidosException {
        FiltrosActividad filtros = new FiltrosActividad();
        
        if(!StringUtils.isBlank(nombre)){
            filtros.setNombre(nombre);
        }
        
        if(!StringUtils.isBlank(congreso)){
            filtros.setCongresoNombre(congreso);
        }
        
        return filtros;
    }
    
}
