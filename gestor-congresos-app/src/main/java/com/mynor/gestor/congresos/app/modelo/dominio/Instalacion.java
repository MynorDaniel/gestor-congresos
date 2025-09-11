/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.modelo.dominio;

/**
 *
 * @author mynordma
 */
public class Instalacion extends Entidad {
    
    private int id;
    private String nombre;
    private String ubicacion;
    private Salon[] salones;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
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

    @Override
    public String[] getValores() {
        return new String[] {
            "NULL",
            nombre,
            ubicacion
        };
    }
    
}
