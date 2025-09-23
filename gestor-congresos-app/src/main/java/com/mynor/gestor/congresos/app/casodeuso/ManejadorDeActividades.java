/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.ActividadBD;
import com.mynor.gestor.congresos.app.basededatos.EncargadoActividadBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.ActividadInvalidaException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Actividad;
import com.mynor.gestor.congresos.app.modelo.Asistencia;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.EstadoActividad;
import com.mynor.gestor.congresos.app.modelo.FiltrosActividad;
import com.mynor.gestor.congresos.app.modelo.FiltrosAsistencia;
import com.mynor.gestor.congresos.app.modelo.FiltrosCongreso;
import com.mynor.gestor.congresos.app.modelo.FiltrosUsuarioActividad;
import com.mynor.gestor.congresos.app.modelo.Participacion;
import com.mynor.gestor.congresos.app.modelo.Reservacion;
import com.mynor.gestor.congresos.app.modelo.Salon;
import com.mynor.gestor.congresos.app.modelo.TipoActividad;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author mynordma
 */
public class ManejadorDeActividades extends Manejador {
    
    private final ActividadBD actividadBD;

    public ManejadorDeActividades() {
        this.actividadBD = new ActividadBD();
    }

    public void crear(Actividad actividad) throws AccesoDeDatosException, ActividadInvalidaException {        
        //Validar duplicidad
        if(existeActividad(actividad)) throw new ActividadInvalidaException("Ya existe una actividad con el mismo nombre en el congreso");
        
        //Validar logica de horas
        if(!horasLogicas(actividad)) throw new ActividadInvalidaException("Verifica las horas");
        
        //Validar que el dia sea en el intervalo del congreso
        ManejadorDeCongresos manejadorCongresos = new ManejadorDeCongresos();
        Congreso congreso = manejadorCongresos.obtenerCongreso(actividad.getCongresoNombre());
        if(!diaValido(actividad, congreso)) throw new ActividadInvalidaException("Día fuera del intervalo de fechas del congreso");
        
        //Verificar que el salon pertenezca a la instalacion
        if(!salonPerteneceAInstalacion(actividad, congreso)) throw new ActividadInvalidaException("El salón no pertenece a la instalación del congreso");
        
        //Validar que el salon no este ocupado
        if(salonOcupado(actividad)) throw new ActividadInvalidaException("Salón ocupado");
        
        //Validar que el autor este inscrito al congreso
        
        //Asignar estado
        if(congreso.getCreador().equals(actividad.getAutorId())){
            actividad.setEstado(EstadoActividad.APROBADA);
        }else{
            actividad.setEstado(EstadoActividad.PENDIENTE);
        }
        
        actividadBD.crear(actividad);
    }

    private boolean existeActividad(Actividad actividad) throws AccesoDeDatosException {
        FiltrosActividad filtros = new FiltrosActividad();
        filtros.setCongresoNombre(actividad.getCongresoNombre());
        filtros.setNombre(actividad.getNombre());
        Actividad[] coincidencias = actividadBD.leer(filtros);
        return coincidencias.length > 0;
    }

    private boolean horasLogicas(Actividad actividad) {
        return !actividad.getHoraInicio().isAfter(actividad.getHoraFin());
    }

    private boolean diaValido(Actividad actividad, Congreso congreso) throws AccesoDeDatosException {
        return (actividad.getDia().isEqual(congreso.getFechaInicio()) || actividad.getDia().isAfter(congreso.getFechaInicio())) &&
               (actividad.getDia().isEqual(congreso.getFechaFin())    || actividad.getDia().isBefore(congreso.getFechaFin()));
    }

    private boolean salonPerteneceAInstalacion(Actividad actividad, Congreso congreso) throws AccesoDeDatosException {
        ManejadorDeInstalaciones manejadorInstalaciones = new ManejadorDeInstalaciones();
        int instalacionIdCongreso = congreso.getInstalacionId();
        Salon[] salonesValidos = manejadorInstalaciones.obtenerSalones(instalacionIdCongreso);
        String salonActividadNombre = actividad.getSalonNombre();
        
        for (Salon salonValido : salonesValidos) {
            if(salonValido.getNombre().equals(salonActividadNombre)){
                return true;
            }
        }
        return false;
    }

    private boolean salonOcupado(Actividad actividad) throws AccesoDeDatosException {
        FiltrosActividad filtros = new FiltrosActividad();
        filtros.setCongresoNombre(actividad.getCongresoNombre());
        filtros.setSalonNombre(actividad.getSalonNombre());
        filtros.setDia(actividad.getDia());
        filtros.setHoraInicial(actividad.getHoraInicio());
        filtros.setHoraFinal(actividad.getHoraFin());
        
        Actividad[] coincidencias = actividadBD.leer(filtros);
        
        return coincidencias.length > 0;
    }

    public Actividad[] obtenerPorCongreso(String nombreCongreso) throws AccesoDeDatosException {
        FiltrosActividad filtros = new FiltrosActividad();
        filtros.setCongresoNombre(nombreCongreso);
        return actividadBD.leer(filtros);
    }

    public Actividad[] obtener(FiltrosActividad filtros) throws AccesoDeDatosException {
        Actividad[] actividades = actividadBD.leer(filtros);
        
        EncargadoActividadBD encargadoBD = new EncargadoActividadBD();
        FiltrosUsuarioActividad filtrosUsuarioActividad = new FiltrosUsuarioActividad();
        filtrosUsuarioActividad.setActividadCongreso(filtros.getCongresoNombre());
        filtrosUsuarioActividad.setActividadNombre(filtros.getNombre());
        
        for (Actividad a : actividades) {
            a.setEncargados(encargadoBD.leer(filtrosUsuarioActividad));
        }
        return actividades;
    }

    public Actividad actualizar(Actividad actualizaciones, Usuario usuarioActual) throws AccesoDeDatosException, UsuarioInvalidoException {
        //Obtener la actividad y sobreescribir los cambios
        
        FiltrosActividad filtros = new FiltrosActividad();
        filtros.setNombre(actualizaciones.getNombre());
        filtros.setCongresoNombre(actualizaciones.getCongresoNombre());
        Actividad[] coincidencias = actividadBD.leer(filtros);
        
        if(coincidencias.length < 1) throw new AccesoDeDatosException("No se pudo obtener la actividad");
        Actividad actividad = coincidencias[0];
        
        //Autorizacion
        ManejadorDeCongresos mc = new ManejadorDeCongresos();
        Congreso congreso = mc.obtenerCongreso(actividad.getCongresoNombre());
        if(!usuarioActual.getId().equals(congreso.getCreador())){
            ManejadorDeParticipaciones mp = new  ManejadorDeParticipaciones();
            Participacion[] comiteMiembros = mp.obtenerComite(congreso.getNombre());
            boolean esDelComite = false;
            for (Participacion comiteMiembro : comiteMiembros) {
                if(comiteMiembro.getUsuarioId().equals(usuarioActual.getId())) esDelComite = true;
            }
            
            if(!esDelComite) throw new UsuarioInvalidoException("No perteneces al comité científico");
        }
        
        if(actualizaciones.getCupo() != 0){
            actividad.setCupo(actualizaciones.getCupo());
        }
        
        if(actualizaciones.getEstado()!= null){
            actividad.setEstado(actualizaciones.getEstado());
        }
        
        if(actualizaciones.getDescripcion()!= null){
            actividad.setDescripcion(actualizaciones.getDescripcion());
        }
        
        actividadBD.actualizar(actividad);
        
        return actividad;
    }

    public Asistencia[] obtenerAsistencias(FiltrosAsistencia filtros, Usuario creador) throws AccesoDeDatosException {
        FiltrosCongreso filtrosC = new FiltrosCongreso();
        
        
        if(filtros.getFechaInicio() != null && filtros.getFechaFin() != null){
            if(!fechasLogicas(filtros.getFechaInicio(), filtros.getFechaFin())) throw new AccesoDeDatosException("Verifica que la fecha final sea despues de la inicial");
            filtrosC.setFechaInicio(filtros.getFechaInicio());
            filtrosC.setFechaFin(filtros.getFechaFin());
            
        }
        filtrosC.setCreador(creador.getId());
        ManejadorDeCongresos mc = new ManejadorDeCongresos();
        Congreso[] congresos = mc.obtenerCongresos(filtrosC);
        
        
        
        List<Actividad> actividades = new LinkedList<>();
        
        for (Congreso congreso : congresos) {
            FiltrosActividad filtrosA = new FiltrosActividad();
            filtrosA.setCongresoNombre(congreso.getNombre());
            
            if(filtros.getSalonNombre() != null){
                filtrosA.setSalonNombre(filtros.getSalonNombre());
            }
            
            actividades.addAll(Arrays.asList(actividadBD.leer(filtrosA)));
        }
        
        if(filtros.getActividadNombre() != null){
            for (Actividad a : actividades) {
                System.out.println("Act: " + a.getNombre());
                if(a.getNombre().equals(filtros.getActividadNombre())){
                    return actividadBD.leerAsistenciasPorActividad(a);
                }
            }
            
            return new Asistencia[0];
        }
        
        List<Asistencia> asistencias = new LinkedList<>();
        for (Actividad a : actividades) {
            asistencias.addAll(Arrays.asList(actividadBD.leerAsistenciasPorActividad(a)));
        }
        
        return asistencias.toArray(Asistencia[]::new);
    }

    public Actividad[] obtenerPorCongresos(Congreso[] congresos) throws AccesoDeDatosException {
        List<Actividad> actividades = new LinkedList<>();
        for (Congreso congreso : congresos) {
            actividades.addAll(Arrays.asList(this.obtenerPorCongreso(congreso.getNombre())));
        }
        return actividades.toArray(Actividad[]::new);
    }

    public Reservacion[] obtenerReservaciones(Actividad actividad) throws AccesoDeDatosException {
        return actividadBD.leerReservaciones(actividad);
    }

    public void crearReservacion(Reservacion reservacion) throws AccesoDeDatosException {
        //Verificar cupo
        FiltrosActividad filtros = new FiltrosActividad();
        filtros.setNombre(reservacion.getActividadNombre());
        filtros.setCongresoNombre(reservacion.getActividadCongresoNombre());
        Actividad actividad = actividadBD.leer(filtros)[0];
        
        Reservacion[] reservaciones = actividadBD.leerReservaciones(actividad);
        
        if(reservaciones.length >= actividad.getCupo()) throw new AccesoDeDatosException("Sin cupo");
        
        actividadBD.crearReservacion(reservacion);
    }

    public Reservacion[] obtenerReservacionesPorCongresos(Congreso[] congresos, String actividadNombre) throws AccesoDeDatosException {
        List<Reservacion> reservas = new LinkedList<>();
        
        for (Congreso congreso : congresos) {
            
            FiltrosActividad filtros = new FiltrosActividad();
            filtros.setCongresoNombre(congreso.getNombre());
            
            if(actividadNombre != null){
                filtros.setNombre(actividadNombre);
            }
            
            Actividad[] actividades = actividadBD.leer(filtros);
            
            for (Actividad a : actividades) {
                reservas.addAll(Arrays.asList(actividadBD.leerReservaciones(a)));
                System.out.println("Act: " + a.getNombre());
            }
            
        }
        
        ManejadorDeUsuarios mu = new ManejadorDeUsuarios();
            
            for (Reservacion r : reservas) {
                System.out.println("r: " + r.getActividadNombre());
                r.setUsuarioEntidad(mu.obtenerPorId(r.getUsuario()));
                FiltrosActividad filtros2 = new FiltrosActividad();
                filtros2.setCongresoNombre(r.getActividadCongresoNombre());
                filtros2.setNombre(r.getActividadNombre());
                
                Actividad actividadDeReserva = this.obtener(filtros2)[0];
                
                r.setActividad(actividadDeReserva);
            }
        
        return reservas.toArray(Reservacion[]::new);
    }

    public void crearAsistencia(Asistencia a) throws AccesoDeDatosException {
        
        FiltrosActividad filtros = new FiltrosActividad();
        filtros.setCongresoNombre(a.getActividadCongresoNombre());
        filtros.setNombre(a.getActividadNombre());
        Actividad act = this.obtener(filtros)[0];
        
        if(act.getTipo() == TipoActividad.TALLER){
            Reservacion[] reservaciones = this.obtenerReservaciones(act);
            boolean tieneReservacion = false;
            for (Reservacion r : reservaciones) {
                if(r.getUsuario().equals(a.getUsuario())){
                    tieneReservacion = true;
                }
            }

            if(!tieneReservacion) throw new AccesoDeDatosException("Sin reservacion");
        }
        
        actividadBD.crearAsistencia(a);
    }
    
}
