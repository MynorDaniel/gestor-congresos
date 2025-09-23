/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.modelo;

/**
 *
 * @author mynordma
 */
public class Reservacion {
    
    private String usuario;
    private String actividadNombre;
    private String actividadCongresoNombre;
    private Usuario usuarioEntidad;
    private Actividad actividad;

    public Usuario getUsuarioEntidad() {
        return usuarioEntidad;
    }

    public void setUsuarioEntidad(Usuario usuarioEntidad) {
        this.usuarioEntidad = usuarioEntidad;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getActividadNombre() {
        return actividadNombre;
    }

    public void setActividadNombre(String actividadNombre) {
        this.actividadNombre = actividadNombre;
    }

    public String getActividadCongresoNombre() {
        return actividadCongresoNombre;
    }

    public void setActividadCongresoNombre(String actividadCongresoNombre) {
        this.actividadCongresoNombre = actividadCongresoNombre;
    }
}
