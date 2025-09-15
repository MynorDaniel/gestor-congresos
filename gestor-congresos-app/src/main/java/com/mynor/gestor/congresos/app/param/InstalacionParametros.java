/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.InstalacionInvalidaException;
import com.mynor.gestor.congresos.app.modelo.Instalacion;
import com.mynor.gestor.congresos.app.modelo.Salon;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mynordma
 */
public class InstalacionParametros extends Validador implements EntidadParseador<Instalacion> {
    
    private final String nombre;
    private final String ubicacion;
    private final String[] salones;

    public InstalacionParametros(HttpServletRequest request){
        nombre = request.getParameter("nombre");
        ubicacion = request.getParameter("ubicacion");
        salones = request.getParameterValues("salones[]");
    }

    @Override
    public Instalacion toEntidad() throws InstalacionInvalidaException {
        if(!longitudValida(nombre, 200)) throw new InstalacionInvalidaException("El nombre debe tener una longitud menor o igual a 200");
        if(StringUtils.isBlank(ubicacion)) throw new InstalacionInvalidaException("La ubicacion no puede estar vacía");
        if(salones == null) throw new InstalacionInvalidaException("Ingresa al menos un salón");
        
        for (String salon : salones) {
            if(!longitudValida(salon, 200)) throw new InstalacionInvalidaException("El nombre del salón debe tener una longitud menor o igual a 200");
        }
        
        Instalacion instalacion = new Instalacion();
        instalacion.setNombre(nombre);
        instalacion.setUbicacion(ubicacion);
        
        Salon[] salonesInstalacion = new Salon[salones.length];
        
        for (int i = 0; i < salones.length; i++) {
            Salon salonInstalacion = new Salon();
            salonInstalacion.setNombre(salones[i]);
            salonesInstalacion[i] = salonInstalacion;
        }
        
        instalacion.setSalones(salonesInstalacion);
        
        return instalacion;
    }
    
}
