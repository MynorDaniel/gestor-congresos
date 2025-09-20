/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.ActividadBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.ActividadInvalidaException;
import com.mynor.gestor.congresos.app.modelo.Actividad;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.EstadoActividad;
import com.mynor.gestor.congresos.app.modelo.FiltrosActividad;
import com.mynor.gestor.congresos.app.modelo.Salon;

/**
 *
 * @author mynordma
 */
public class ManejadorDeActividades extends Manejador {

    public void crear(Actividad actividad) throws AccesoDeDatosException, ActividadInvalidaException {        
        //Validar duplicidad
        if(existeActividad(actividad)) throw new ActividadInvalidaException("Ya existe una actividad con el mismo nombre en el congreso");
        
        //Validar logica de horas
        if(!horasLogicas(actividad)) throw new ActividadInvalidaException("Verifica las horas");
        
        //Validar que el dia sea en el intervalo del congreso
        ManejadorDeCongresos manejadorCongresos = new ManejadorDeCongresos();
        Congreso congreso = manejadorCongresos.obtenerCongreso(actividad.getCongresoNombre());
        if(!diaValido(actividad, congreso)) throw new ActividadInvalidaException("Día fuera del intervalo de fechas del congreso");
        
        //Verificar que el salon pertenezca a la instalacion
        if(!salonPerteneceAInstalacion(actividad, congreso)) throw new ActividadInvalidaException("El salón no pertenece a la instalación del congreso");
        
        //Validar que el salon no este ocupado
        if(salonOcupado(actividad)) throw new ActividadInvalidaException("Salón ocupado");
        
        //Crear actividad
        //Asignar estado
        actividad.setEstado(EstadoActividad.PENDIENTE);
        ActividadBD actividadBD = new ActividadBD();
        actividadBD.crear(actividad);
    }

    private boolean existeActividad(Actividad actividad) throws AccesoDeDatosException {
        ActividadBD actividadBD = new ActividadBD();
        FiltrosActividad filtros = new FiltrosActividad();
        filtros.setCongresoNombre(actividad.getCongresoNombre());
        filtros.setNombre(actividad.getNombre());
        Actividad[] coincidencias = actividadBD.leer(filtros);
        return coincidencias.length > 0;
    }

    private boolean horasLogicas(Actividad actividad) {
        return !actividad.getHoraInicio().isAfter(actividad.getHoraFin());
    }

    private boolean diaValido(Actividad actividad, Congreso congreso) throws AccesoDeDatosException {
        return (actividad.getDia().isEqual(congreso.getFechaInicio()) || actividad.getDia().isAfter(congreso.getFechaInicio())) &&
               (actividad.getDia().isEqual(congreso.getFechaFin())    || actividad.getDia().isBefore(congreso.getFechaFin()));
    }

    private boolean salonPerteneceAInstalacion(Actividad actividad, Congreso congreso) throws AccesoDeDatosException {
        ManejadorDeInstalaciones manejadorInstalaciones = new ManejadorDeInstalaciones();
        int instalacionIdCongreso = congreso.getInstalacionId();
        Salon[] salonesValidos = manejadorInstalaciones.obtenerSalones(instalacionIdCongreso);
        String salonActividadNombre = actividad.getSalonNombre();
        
        for (Salon salonValido : salonesValidos) {
            if(salonValido.getNombre().equals(salonActividadNombre)){
                return true;
            }
        }
        return false;
    }

    private boolean salonOcupado(Actividad actividad) throws AccesoDeDatosException {
        ActividadBD actividadBD = new ActividadBD();
        
        FiltrosActividad filtros = new FiltrosActividad();
        filtros.setCongresoNombre(actividad.getCongresoNombre());
        filtros.setSalonNombre(actividad.getSalonNombre());
        filtros.setDia(actividad.getDia());
        filtros.setHoraInicial(actividad.getHoraInicio());
        filtros.setHoraFinal(actividad.getHoraFin());
        
        Actividad[] coincidencias = actividadBD.leer(filtros);
        
        return coincidencias.length > 0;
    }

    public Actividad[] obtenerPorCongreso(String nombreCongreso) throws AccesoDeDatosException {
        ActividadBD actividadBD = new ActividadBD();
        FiltrosActividad filtros = new FiltrosActividad();
        filtros.setCongresoNombre(nombreCongreso);
        return actividadBD.leer(filtros);
    }
    
}
