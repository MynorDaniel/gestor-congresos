/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.modelo;

import java.time.LocalDate;

/**
 *
 * @author mynordma
 */
public class FiltrosAsistencia extends Filtros {
    
    private String actividadNombre;
    private String congresoNombre;
    private String salonNombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    public String getActividadNombre() {
        return actividadNombre;
    }

    public void setActividadNombre(String actividadNombre) {
        this.actividadNombre = actividadNombre;
    }

    public String getCongresoNombre() {
        return congresoNombre;
    }

    public void setCongresoNombre(String congresoNombre) {
        this.congresoNombre = congresoNombre;
    }

    public String getSalonNombre() {
        return salonNombre;
    }

    public void setSalonNombre(String salonNombre) {
        this.salonNombre = salonNombre;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    
    
}
