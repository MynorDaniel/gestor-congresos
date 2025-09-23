/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.ActividadInvalidaException;
import com.mynor.gestor.congresos.app.modelo.Actividad;
import com.mynor.gestor.congresos.app.modelo.EstadoActividad;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mynordma
 */
public class ActualizacionActividadParametros extends Validador implements EntidadParseador<Actividad> {

    private final String nombre;
    private final String congreso;
    private final String cupo;
    private final String estado;
    private final String descripcion;

    public ActualizacionActividadParametros(HttpServletRequest request){
        this.nombre = request.getParameter("nombre");
        this.congreso = request.getParameter("congreso");
        this.cupo = request.getParameter("cupo");
        this.estado = request.getParameter("estado");
        this.descripcion = request.getParameter("descripcion");
    }
    
    @Override
    public Actividad toEntidad() throws ActividadInvalidaException {
        //Si los campos no son blank asignarlos a la actividad
        try {
            Actividad actividad = new Actividad();
            
            if(!StringUtils.isBlank(nombre) && !StringUtils.isBlank(congreso)){
                actividad.setNombre(nombre);
                actividad.setCongresoNombre(congreso);
            }else{
                throw new ActividadInvalidaException("Nombre incorrecto");
            }
            
            if(!StringUtils.isBlank(cupo)){
                actividad.setCupo(Integer.parseInt(cupo));
            }
            
            if(!StringUtils.isBlank(estado)){
                actividad.setEstado(EstadoActividad.valueOf(estado.toUpperCase()));
            }
            
            if(!StringUtils.isBlank(descripcion)){
                actividad.setDescripcion(descripcion);
            }
            
            return actividad;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw new ActividadInvalidaException("Valores inválidos");
        }
    }
    
}
