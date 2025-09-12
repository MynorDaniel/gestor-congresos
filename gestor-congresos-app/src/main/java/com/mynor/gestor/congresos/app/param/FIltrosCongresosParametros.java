
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.FiltrosInvalidosException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author mynordma
 */
public class FIltrosCongresosParametros implements RequestParseador {
    
    private String institucion;
    private String fechaInicial;
    private String fechaFinal;
    private String nombre;
    private String creador;

    @Override
    public void asignarValoresDesdeRequest(HttpServletRequest request) {
        institucion    = request.getParameter("institucion");
        fechaInicial   = request.getParameter("fechaInicial");
        fechaFinal = request.getParameter("fechaFinal");
        nombre = request.getParameter("nombre");
        creador = request.getParameter("creador");
    }

    public Map<String, String> toFiltrosCongresos() throws FiltrosInvalidosException {
        if(!institucionValida(institucion)) throw new FiltrosInvalidosException("Institucion inválida");
        if(!fechaInicialValida(fechaInicial)) throw new FiltrosInvalidosException("FIltro inválioa");
        if(!fechaFinalValida(fechaFinal)) throw new FiltrosInvalidosException("Filtro inválido"); // modificar
        if(!nombreValido(nombre)) throw new FiltrosInvalidosException("Filtro inválido"); // modificar
        if(!creadorValido(creador)) throw new FiltrosInvalidosException("Filtro inválido"); // modificar
        
        Map<String, String> filtros = new LinkedHashMap<>();
        
        if(institucion != null) filtros.put("institucion", institucion);
        if(fechaInicial != null) filtros.put("fecha_inicio", fechaInicial);
        if(fechaFinal != null) filtros.put("fecha_fin", fechaFinal);
        if(nombre != null) filtros.put("nombre", nombre);
        if(creador != null) filtros.put("creador", creador);
        
        return filtros;
    }
    
    private boolean institucionValida(String institucion){
        return true;
    }
    
    private boolean fechaInicialValida(String fecha){
        return true;
    }
    
    private boolean fechaFinalValida(String fecha){
        return true;
    }
    
    private boolean nombreValido(String nombre){
        return true;
    }
    
    private boolean creadorValido(String creador){
        return true;
    }
    
}
