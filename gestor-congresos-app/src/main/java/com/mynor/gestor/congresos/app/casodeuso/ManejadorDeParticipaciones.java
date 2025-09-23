/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.ParticipacionBD;
import com.mynor.gestor.congresos.app.basededatos.UsuarioBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.ParticipacionInvalidaException;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.FiltrosUsuario;
import com.mynor.gestor.congresos.app.modelo.Participacion;
import com.mynor.gestor.congresos.app.modelo.Rol;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mynordma
 */
public class ManejadorDeParticipaciones extends Manejador {
    
    private final ParticipacionBD participacionBD;

    public ManejadorDeParticipaciones() {
        this.participacionBD = new ParticipacionBD();
    }

    public Participacion[] obtenerComite(String nombreCongreso) throws AccesoDeDatosException {
        Participacion[] comite = participacionBD.leerPorCongresoYRol(nombreCongreso, Rol.COMITE);
        
        ManejadorDeUsuarios manejadorUsuarios = new ManejadorDeUsuarios();
        for (Participacion p : comite) {
            Usuario usuario = manejadorUsuarios.obtenerPorId(p.getUsuarioId());
            p.setUsuario(usuario);
        }
        
        return comite;
    }

    public void crear(Participacion p) throws AccesoDeDatosException, ParticipacionInvalidaException {
        //verificar duplicidad
        if(existeParticipacion(p)) throw new ParticipacionInvalidaException("Ya existe esta participacion");
        
        participacionBD.crear(p);
    }

    private boolean existeParticipacion(Participacion p) throws AccesoDeDatosException {
        Participacion[] participaciones = participacionBD.leerPorCongresoYRol(p.getCongresoNombre(), p.getRol());
        
        for (Participacion participacion : participaciones) {
            if(participacion.getUsuarioId().equals(p.getUsuarioId())){
                return true;
            }
        }
        return false;
    }

    public Participacion[] obtenerPosiblesEncargados(Congreso congreso) throws AccesoDeDatosException {
        Participacion[] participaciones = participacionBD.leerPorCongresoYRol(congreso.getNombre(), Rol.PONENTE_INVITADO);
        UsuarioBD uBD = new UsuarioBD();
        FiltrosUsuario filtros = new FiltrosUsuario();
        
        for (Participacion p : participaciones) {
            filtros.setId(p.getUsuarioId());
            p.setUsuario(uBD.leer(filtros)[0]);
            
        }
        return participaciones;
    }
    
    public Participacion[] obtenerPorCreadorDeCongreso(String id, String rol) throws AccesoDeDatosException, IllegalArgumentException {
        ManejadorDeCongresos mc = new ManejadorDeCongresos();
        Congreso[] congresos = mc.obtenerCongresos(id);
        
        List<Participacion> participaciones = new LinkedList<>();
        
        for (Congreso congreso : congresos) {
            if(!StringUtils.isBlank(rol)){
                Participacion[] participacionesCongreso = participacionBD.leerPorCongresoYRol(congreso.getNombre(), Rol.valueOf(rol));
                participaciones.addAll(Arrays.asList(participacionesCongreso));
            }else{
                Participacion[] participacionesCongreso = participacionBD.leerPorCongreso(congreso.getNombre());
                participaciones.addAll(Arrays.asList(participacionesCongreso));
            }
            
        }
        
        UsuarioBD usuarioBD = new UsuarioBD();
        for (Participacion p : participaciones) {
            FiltrosUsuario filtros = new FiltrosUsuario();
            filtros.setId(p.getUsuarioId());
            p.setUsuario(usuarioBD.leer(filtros)[0]);
        }
        
        return participaciones.toArray(Participacion[]::new);
    }
    
}
