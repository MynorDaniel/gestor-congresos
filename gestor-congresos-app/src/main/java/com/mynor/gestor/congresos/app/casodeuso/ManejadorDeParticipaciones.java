/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.ParticipacionBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Participacion;
import com.mynor.gestor.congresos.app.modelo.Rol;
import com.mynor.gestor.congresos.app.modelo.Usuario;

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
    
}
