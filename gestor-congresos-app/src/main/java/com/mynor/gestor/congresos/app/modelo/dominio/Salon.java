/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.modelo.dominio;

/**
 *
 * @author mynordma
 */
public class Salon extends Entidad {
    
    private String nombre;
    private int instalacion;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getInstalacion() {
        return instalacion;
    }

    public void setInstalacion(int instalacion) {
        this.instalacion = instalacion;
    }

    @Override
    public String[] getValores() {
        return new String[]{
            nombre,
            String.valueOf(instalacion)
        };
    }
    
}
