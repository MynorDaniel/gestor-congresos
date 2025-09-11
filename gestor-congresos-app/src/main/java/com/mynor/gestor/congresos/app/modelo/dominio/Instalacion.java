/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.modelo.dominio;

/**
 *
 * @author mynordma
 */
public class Instalacion {
    
    private String nombre;
    private String ubicacion;
    private Salon[] salones;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Salon[] getSalones() {
        return salones;
    }

    public void setSalones(Salon[] salones) {
        this.salones = salones;
    }
    
}
