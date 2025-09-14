/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.CongresoBD;
import com.mynor.gestor.congresos.app.basededatos.UsuarioBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.CongresoInvalidoException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.dominio.Congreso;
import com.mynor.gestor.congresos.app.modelo.dominio.Usuario;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author mynordma
 */
public class ManejadorDeCongresos {

    public Congreso[] obtenerCongresos(Map<String, String> filtros) throws AccesoDeDatosException {
        CongresoBD congresoBD = new CongresoBD();
        return congresoBD.leer(filtros);
    }

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

    private boolean esAdminDeCongresos(String creador) throws AccesoDeDatosException {
        Map<String, String> filtros = new HashMap<>();
        filtros.put("rol_sistema", "ADMIN_CONGRESOS");
        filtros.put("id", creador);
        
        UsuarioBD usuarioBD = new UsuarioBD();
        Usuario[] coincidencias = usuarioBD.leer(filtros);
        
        return coincidencias.length > 0;
    }

    private boolean fechasLogicas(LocalDate fechaInicio, LocalDate fechaFin) {
        return !fechaInicio.isAfter(fechaFin);
    }

    private boolean instalacionOcupada(Congreso congreso) throws AccesoDeDatosException {
        Map<String, String> filtros = new HashMap<>();
        filtros.put("instalacion", congreso.getInstalacionId());
        filtros.put("fecha", congreso.getFechaInicio().toString() + "@" + congreso.getFechaFin().toString()); // @: separador
        
        CongresoBD congresoBD = new CongresoBD();
        Congreso[] coincidencias = congresoBD.leer(filtros);
        
        return coincidencias.length > 0;
    }
    
}