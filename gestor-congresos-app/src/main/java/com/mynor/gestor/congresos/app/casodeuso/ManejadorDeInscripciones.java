/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.CarteraBD;
import com.mynor.gestor.congresos.app.basededatos.CongresoBD;
import com.mynor.gestor.congresos.app.basededatos.InscripcionBD;
import com.mynor.gestor.congresos.app.basededatos.UsuarioBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.InscripcionInvalidaException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Cartera;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.FiltrosCongreso;
import com.mynor.gestor.congresos.app.modelo.FiltrosInscripcion;
import com.mynor.gestor.congresos.app.modelo.FiltrosUsuario;
import com.mynor.gestor.congresos.app.modelo.Inscripcion;
import com.mynor.gestor.congresos.app.modelo.Pago;
import com.mynor.gestor.congresos.app.modelo.Usuario;

/**
 *
 * @author mynordma
 */
public class ManejadorDeInscripciones extends Manejador {

    public Inscripcion[] obtenerInscripciones(FiltrosInscripcion filtros, Usuario usuarioActual) throws UsuarioInvalidoException, AccesoDeDatosException {
        //Verificar que el usuario actual coincida con el filtro
        if(filtros.getUsuarioId() != null){
            if(!filtros.getUsuarioId().equals(usuarioActual.getId())) throw new UsuarioInvalidoException("No puedes ver estas inscripciones");
        }
        
        InscripcionBD inscripcionBD = new InscripcionBD();
        return inscripcionBD.leer(filtros);
    }

    public boolean existeInscripcion(Inscripcion inscripcion) throws AccesoDeDatosException {
        FiltrosInscripcion filtros = new FiltrosInscripcion();
        filtros.setUsuarioId(inscripcion.getUsuarioId());
        filtros.setCongresoNombre(inscripcion.getCongresoNombre());
        
        InscripcionBD inscripcionBD = new InscripcionBD();
        Inscripcion[] coincidencias = inscripcionBD.leer(filtros);
        
        return coincidencias.length > 0;
    }

    public void crear(Inscripcion inscripcion) throws AccesoDeDatosException, InscripcionInvalidaException {
        //Verificar saldo suficiente
        CarteraBD carteraBD = new CarteraBD();
        
        Cartera cartera = carteraBD.leer(inscripcion.getUsuarioId());
                
        CongresoBD congresoBD = new CongresoBD();
        FiltrosCongreso filtrosCongreso = new FiltrosCongreso();
        filtrosCongreso.setNombre(inscripcion.getCongresoNombre());
        
        Congreso congreso = congresoBD.leer(filtrosCongreso)[0];
        
        if(cartera.getSaldo() < congreso.getPrecio()) throw new InscripcionInvalidaException("Saldo insuficiente");
        
        //Verificar que la fecha de inscripcion sea antes de la fecha de fin del congreso
        if(!inscripcion.getPago().getFecha().isBefore(congreso.getFechaFin())) throw new InscripcionInvalidaException("El congreso ya terminó");
        
        //Crear la inscripcion
        
        
        
    }
    
}