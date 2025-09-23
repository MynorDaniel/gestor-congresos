/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.EntidadInvalidaException;
import com.mynor.gestor.congresos.app.modelo.FiltrosAsistencia;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mynordma
 */
public class FiltrosAsistenciaParametros extends Validador implements EntidadParseador<FiltrosAsistencia> {

    private final String actividadNombre;
    private final String congresoNombre;
    private final String salonNombre;
    private final String fechaInicio;
    private final String fechaFin;
    
    public FiltrosAsistenciaParametros(HttpServletRequest request) {
        actividadNombre = request.getParameter("actividad");
        congresoNombre = request.getParameter("congreso");
        salonNombre = request.getParameter("salon");
        fechaInicio = request.getParameter("fecha-inicio");
        fechaFin = request.getParameter("fecha-fin");
        
    }

    @Override
    public FiltrosAsistencia toEntidad() throws EntidadInvalidaException {
        FiltrosAsistencia filtros = new FiltrosAsistencia();
        
        if(!StringUtils.isBlank(actividadNombre)){
            filtros.setActividadNombre(actividadNombre);
        }
        
        if(!StringUtils.isBlank(congresoNombre)){
            filtros.setCongresoNombre(congresoNombre);
        }
        
        if(!StringUtils.isBlank(salonNombre)){
            filtros.setSalonNombre(salonNombre);
        }
        
        if(!StringUtils.isBlank(fechaInicio) && !StringUtils.isBlank(fechaFin)){
            if(!fechaValida(fechaInicio)) throw new EntidadInvalidaException("Fecha incorrecta");
            if(!fechaValida(fechaFin)) throw new EntidadInvalidaException("Fecha incorrecta");
            
            filtros.setFechaInicio(LocalDate.parse(fechaInicio));
            filtros.setFechaFin(LocalDate.parse(fechaFin));
        }
        
        return filtros;
    }
    
}
