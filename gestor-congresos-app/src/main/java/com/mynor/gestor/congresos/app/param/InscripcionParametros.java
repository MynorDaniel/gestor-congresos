/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.InscripcionInvalidaException;
import com.mynor.gestor.congresos.app.modelo.Inscripcion;
import com.mynor.gestor.congresos.app.modelo.Pago;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;

/**
 *
 * @author mynordma
 */
public class InscripcionParametros extends Validador implements EntidadParseador<Inscripcion> {

    private final String usuarioId;
    private final String congresoNombre;
    private final String fecha;
    
    public InscripcionParametros(HttpServletRequest request) {
        usuarioId = request.getParameter("usuario");
        congresoNombre = request.getParameter("congreso");
        fecha = request.getParameter("fecha");
    }

    @Override
    public Inscripcion toEntidad() throws InscripcionInvalidaException {
        if(!longitudValida(usuarioId, 30)) throw new InscripcionInvalidaException("Usuario incorrecto");
        if(!longitudValida(congresoNombre, 200)) throw new InscripcionInvalidaException("Congreso incorrecto");
        if(!fechaValida(fecha)) throw new InscripcionInvalidaException("Fecha incorrecta");
        
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setUsuarioId(usuarioId);
        inscripcion.setCongresoNombre(congresoNombre);
        
        Pago pago = new Pago();
        pago.setFecha(LocalDate.parse(fecha));
        
        inscripcion.setPago(pago);
        
        return inscripcion;
    }
    
}
