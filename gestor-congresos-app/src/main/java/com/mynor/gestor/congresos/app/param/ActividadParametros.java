/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.ActividadInvalidaException;
import com.mynor.gestor.congresos.app.modelo.Actividad;
import com.mynor.gestor.congresos.app.modelo.TipoActividad;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;

/**
 *
 * @author mynordma
 */
public class ActividadParametros extends Validador implements EntidadParseador<Actividad> {
    
    private final String nombre;
    private final String congreso;
    private final String instalacionId;
    private final String salon;
    private String cupoStr;
    private final String descripcion;
    private final String tipo;
    private final String horaInicioStr;
    private final String horaFinStr;
    private final String diaStr;

    public ActividadParametros(HttpServletRequest request) {
        nombre = request.getParameter("nombre");
        congreso = request.getParameter("congreso");
        instalacionId = request.getParameter("instalacion");
        salon = request.getParameter("salon");
        cupoStr = request.getParameter("cupo");
        descripcion = request.getParameter("descripcion");
        tipo = request.getParameter("tipo");
        horaInicioStr = request.getParameter("hora_inicio");
        horaFinStr = request.getParameter("hora_fin");
        diaStr = request.getParameter("dia");
    }

    @Override
    public Actividad toEntidad() throws ActividadInvalidaException {
        if(!longitudValida(nombre, 200)) throw new ActividadInvalidaException("La longitud del nombre debe ser entre 1 y 200");
        if(!longitudValida(congreso, 200)) throw new ActividadInvalidaException("La longitud del congreso debe ser entre 1 y 200");
        if(!esEnteroPositivo(instalacionId)) throw new ActividadInvalidaException("Instalación inválida");
        if(!longitudValida(salon, 200)) throw new ActividadInvalidaException("La longitud del salon debe ser entre 1 y 200");
        if(!parsearTipo(tipo).isPresent()) throw new ActividadInvalidaException("Tipo inválido");
        if(tipo.equals("TALLER")){
            if(!esEnteroPositivo(cupoStr)) throw new ActividadInvalidaException("Cupo inválido");
        }else{
            cupoStr = "0";
        }
        if(!horaValida(horaInicioStr)) throw new ActividadInvalidaException("Hora inicial inválida");
        if(!horaValida(horaFinStr)) throw new ActividadInvalidaException("Hora final inválida");
        if(!fechaValida(diaStr)) throw new ActividadInvalidaException("Día inválido");
        
        Actividad actividad = new Actividad();
        
        actividad.setNombre(nombre);
        actividad.setCongresoNombre(congreso);
        actividad.setInstalacionId(Integer.parseInt(instalacionId));
        actividad.setSalonNombre(salon);
        actividad.setCupo(Integer.parseInt(cupoStr));
        actividad.setDescripcion(descripcion);
        actividad.setTipo(TipoActividad.valueOf(tipo));
        actividad.setHoraInicio(LocalTime.parse(horaInicioStr));
        actividad.setHoraFin(LocalTime.parse(horaFinStr));
        actividad.setDia(LocalDate.parse(diaStr));
        
        return actividad;
    }
    
    private Optional<TipoActividad> parsearTipo(String tipoStr){
        return Arrays.stream(TipoActividad.values()).filter(tipoActividad -> tipoActividad.name().equals(tipoStr)).findFirst();
    }
    
}