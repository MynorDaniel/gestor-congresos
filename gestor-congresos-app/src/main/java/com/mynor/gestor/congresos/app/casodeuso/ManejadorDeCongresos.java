/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.CongresoBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.CongresoInvalidoException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.FiltrosCongreso;
import java.time.LocalDate;

/**
 *
 * @author mynordma
 */
public class ManejadorDeCongresos extends Manejador {

    public Congreso crearCongreso(Congreso congresoNuevo) throws CongresoInvalidoException, UsuarioInvalidoException, AccesoDeDatosException {
        //Verificar el rol
        if(!esAdminDeCongresos(congresoNuevo.getCreador())) throw new UsuarioInvalidoException("No puedes crear congresos");
        
        //Verificar fechas logicas
        if(!fechasLogicas(congresoNuevo.getFechaInicio(), congresoNuevo.getFechaFin())) throw new UsuarioInvalidoException("La fecha inicial debe ser anterior a la fecha final");
        
        //Verificar que la instalacion no este ocupada
        if(instalacionOcupada(congresoNuevo)) throw new UsuarioInvalidoException("La instalación esta siendo ocupada");
        
        //Crear el congreso
        CongresoBD congresoBD = new CongresoBD();
        congresoBD.crear(congresoNuevo);
        
        return congresoNuevo;
    }

    private boolean fechasLogicas(LocalDate fechaInicio, LocalDate fechaFin) {
        return !fechaInicio.isAfter(fechaFin);
    }

    private boolean instalacionOcupada(Congreso congresoNuevo) throws AccesoDeDatosException {
        CongresoBD congresoBD = new CongresoBD();
        FiltrosCongreso filtros = new FiltrosCongreso();
        filtros.setInstalacion(congresoNuevo.getInstalacionId());
        
        Congreso[] coincidencias = congresoBD.leer(filtros);
        
        return coincidencias.length > 0;
    }

    public Congreso obtenerCongreso(String nombre) throws AccesoDeDatosException {
        CongresoBD congresoBD = new CongresoBD();
        FiltrosCongreso filtros = new FiltrosCongreso();
        filtros.setNombre(nombre);
        return congresoBD.leer(filtros)[0];
    }

    public Congreso[] obtenerCongresos(String creador) throws AccesoDeDatosException {
        CongresoBD congresoBD = new CongresoBD();
        FiltrosCongreso filtros = new FiltrosCongreso();
        filtros.setCreador(creador);
        return congresoBD.leer(filtros);
    }

    public Congreso[] obtenerCongresos() throws AccesoDeDatosException {
        CongresoBD congresoBD = new CongresoBD();
        return congresoBD.leer(new FiltrosCongreso());
    }
    
}