/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.modelo;

/**
 *
 * @author mynordma
 */
public class FiltrosUsuarioActividad extends Filtros {
    
    private String usuario;
    private String actividadNombre;
    private String actividadCongreso;

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

    public String getActividadCongreso() {
        return actividadCongreso;
    }

    public void setActividadCongreso(String actividadCongreso) {
        this.actividadCongreso = actividadCongreso;
    }
    
}
