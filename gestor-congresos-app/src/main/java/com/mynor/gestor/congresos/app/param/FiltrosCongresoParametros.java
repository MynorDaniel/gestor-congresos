/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.EntidadInvalidaException;
import com.mynor.gestor.congresos.app.excepcion.InstitucionInvalidaException;
import com.mynor.gestor.congresos.app.modelo.FiltrosCongreso;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mynordma
 */
public class FiltrosCongresoParametros extends Validador implements EntidadParseador<FiltrosCongreso> {
    
    private final String institucion;
    private final String fechaInicio;
    private final String fechaFin;

    public FiltrosCongresoParametros(HttpServletRequest request) {
        institucion = request.getParameter("institucion");
        fechaInicio = request.getParameter("fecha-inicio");
        fechaFin = request.getParameter("fecha-fin");
    }

    @Override
    public FiltrosCongreso toEntidad() throws EntidadInvalidaException {
        FiltrosCongreso filtros = new FiltrosCongreso();
        
        if(!StringUtils.isBlank(institucion)){
            if(!esEnteroPositivo(institucion)) throw new InstitucionInvalidaException("Institución inválida");
            
            filtros.setInstitucion(Integer.parseInt(institucion));
        }
        
        if(!StringUtils.isBlank(fechaInicio) && !StringUtils.isBlank(fechaFin)){
            if(!fechaValida(fechaInicio)) throw new InstitucionInvalidaException("Fecha incorrecta");
            if(!fechaValida(fechaFin)) throw new InstitucionInvalidaException("Fecha incorrecta");
            
            filtros.setFechaInicio(LocalDate.parse(fechaInicio));
            filtros.setFechaFin(LocalDate.parse(fechaFin));
        }
        
        return filtros;
    }
    
}
