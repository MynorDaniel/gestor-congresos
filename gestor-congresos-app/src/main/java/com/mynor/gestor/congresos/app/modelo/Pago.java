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
public class Pago {
    
    private double monto;
    private LocalDate fecha;
    private double comisionCobrada;

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public double getComisionCobrada() {
        return comisionCobrada;
    }

    public void setComisionCobrada(double comisionCobrada) {
        this.comisionCobrada = comisionCobrada;
    }
}
