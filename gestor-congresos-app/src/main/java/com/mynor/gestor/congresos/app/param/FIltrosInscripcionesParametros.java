/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.FiltrosInvalidosException;
import com.mynor.gestor.congresos.app.modelo.FiltrosInscripcion;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mynordma
 */
public class FIltrosInscripcionesParametros extends Validador implements EntidadParseador<FiltrosInscripcion> {
    
    private final String usuarioId;

    public FIltrosInscripcionesParametros(HttpServletRequest request) {
        usuarioId = request.getParameter("usuario");
    }

    @Override
    public FiltrosInscripcion toEntidad() throws FiltrosInvalidosException {
        FiltrosInscripcion filtros = new FiltrosInscripcion();
        
        if(StringUtils.isBlank(usuarioId)) return filtros;
        
        if(!longitudValida(usuarioId, 30)) throw new FiltrosInvalidosException("Usuario incorrecto");
        
        filtros.setUsuarioId(usuarioId);
        
        return filtros;
    }
    
}
