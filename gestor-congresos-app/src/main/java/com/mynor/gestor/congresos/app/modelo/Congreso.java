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
public class Congreso {
    
    private String nombre;
    private String creador;
    private double precio;
    private boolean convocando;
    private LocalDate fecha;
    private LocalDate fechaFin;
    private String descripcion;
    private boolean activado;
    private int instalacionId;
    private Participacion[] comite;

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Participacion[] getComite() {
        return comite;
    }

    public void setComite(Participacion[] comite) {
        this.comite = comite;
    }

    public int getInstalacionId() {
        return instalacionId;
    }

    public void setInstalacionId(int instalacionId) {
        this.instalacionId = instalacionId;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCreador() {
        return creador;
    }

    public void setCreador(String creador) {
        this.creador = creador;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isConvocando() {
        return convocando;
    }

    public void setConvocando(boolean convocando) {
        this.convocando = convocando;
    }

    public LocalDate getFechaInicio() {
        return fecha;
    }

    public void setFechaInicio(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }
    
    
}
