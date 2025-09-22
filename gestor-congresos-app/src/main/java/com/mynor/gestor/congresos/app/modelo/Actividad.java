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
public class Actividad {
    
    private String nombre;
    private String congresoNombre;
    private String salonNombre;
    private int instalacionId;
    private int cupo;
    private EstadoActividad estado;
    private String descripcion;
    private TipoActividad tipo;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private LocalDate dia;
    private String autorId;
    private EncargadoActividad[] encargados;

    public EncargadoActividad[] getEncargados() {
        return encargados;
    }

    public void setEncargados(EncargadoActividad[] encargados) {
        this.encargados = encargados;
    }

    public String getAutorId() {
        return autorId;
    }

    public void setAutorId(String autorId) {
        this.autorId = autorId;
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

    public String getSalonNombre() {
        return salonNombre;
    }

    public void setSalonNombre(String salonNombre) {
        this.salonNombre = salonNombre;
    }

    public int getInstalacionId() {
        return instalacionId;
    }

    public void setInstalacionId(int instalacionId) {
        this.instalacionId = instalacionId;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public EstadoActividad getEstado() {
        return estado;
    }

    public void setEstado(EstadoActividad estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoActividad getTipo() {
        return tipo;
    }

    public void setTipo(TipoActividad tipo) {
        this.tipo = tipo;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }
}
