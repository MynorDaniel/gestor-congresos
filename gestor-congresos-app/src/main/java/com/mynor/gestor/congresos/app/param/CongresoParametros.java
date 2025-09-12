/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.modelo.dominio.Congreso;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author mynordma
 */
public class CongresoParametros extends Validador implements RequestParseador, EntidadParseador<Congreso> {
    
    private String nombre;
    private String precioStr;
    private String convocandoStr;
    private String fechaInicioStr;
    private String fechaFinStr;
    private String descripcion;
    private String instalacionIdStr;

    @Override
    public void asignarValoresDesdeRequest(HttpServletRequest request) {
        nombre = request.getParameter("nombre");
        precioStr = request.getParameter("precio");
        convocandoStr = request.getParameter("convocando");
        fechaInicioStr = request.getParameter("fecha_inicio");
        fechaFinStr = request.getParameter("fecha_fin"); // opcional
        descripcion = request.getParameter("descripcion");
        instalacionIdStr = request.getParameter("instalacion");
    }

    @Override
    public Congreso toEntidad() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
