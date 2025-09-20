/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.modelo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author mynordma
 */
public class FiltrosActividad extends Filtros {
    
    private String nombre;
    private String congresoNombre;
    private String salonNombre;
    private LocalTime horaInicial;
    private LocalTime horaFinal;
    private LocalDate dia;

    public String getSalonNombre() {
        return salonNombre;
    }

    public void setSalonNombre(String salonNombre) {
        this.salonNombre = salonNombre;
    }

    public LocalTime getHoraInicial() {
        return horaInicial;
    }

    public void setHoraInicial(LocalTime horaInicial) {
        this.horaInicial = horaInicial;
    }

    public LocalTime getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(LocalTime horaFinal) {
        this.horaFinal = horaFinal;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCongresoNombre() {
        return congresoNombre;
    }

    public void setCongresoNombre(String congresoNombre) {
        this.congresoNombre = congresoNombre;
    }
}
