/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Actividad;
import com.mynor.gestor.congresos.app.modelo.Asistencia;
import com.mynor.gestor.congresos.app.modelo.EncargadoActividad;
import com.mynor.gestor.congresos.app.modelo.EstadoActividad;
import com.mynor.gestor.congresos.app.modelo.FiltrosActividad;
import com.mynor.gestor.congresos.app.modelo.FiltrosAsistencia;
import com.mynor.gestor.congresos.app.modelo.Reservacion;
import com.mynor.gestor.congresos.app.modelo.TipoActividad;
import java.sql.*;

/**
 *
 * @author mynordma
 */
public class ActividadBD extends BaseDeDatos {

    public Actividad[] leer(FiltrosActividad filtros) throws AccesoDeDatosException {
        StringBuilder sql = new StringBuilder("SELECT * FROM actividad WHERE 1=1");

        if (filtros.getNombre() != null) {
            sql.append(" AND nombre = ?");
        }
        if (filtros.getCongresoNombre() != null) {
            sql.append(" AND congreso = ?");
        }
        if (filtros.getSalonNombre()!= null) {
            sql.append(" AND salon = ?");
        }
        
        if (filtros.getDia()!= null) {
            sql.append(" AND dia = ?");
        }
        
        if(filtros.getHoraInicial() != null && filtros.getHoraFinal()!= null){
            sql.append(" AND hora_inicio < ? AND hora_fin > ?");
        }
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
                sql.toString(),
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
            
            int i = 1;
            if (filtros.getNombre() != null) {
                ps.setString(i, filtros.getNombre());
                i++;
            }
            if (filtros.getCongresoNombre() != null) {
                ps.setString(i, filtros.getCongresoNombre());
                i++;
            }
            if (filtros.getSalonNombre()!= null) {
                ps.setString(i, filtros.getSalonNombre());
                i++;
            }

            if (filtros.getDia()!= null) {
                ps.setDate(i, Date.valueOf(filtros.getDia()));
                i++;
            }

            if(filtros.getHoraInicial() != null && filtros.getHoraFinal()!= null){
                ps.setTime(i, Time.valueOf(filtros.getHoraFinal()));
                i++;
                
                ps.setTime(i, Time.valueOf(filtros.getHoraInicial()));
                i++;
            }

            ResultSet rs = ps.executeQuery();

            Actividad[] actividades = new Actividad[obtenerLongitudDeResultSet(rs)];
            int j = 0;
            while (rs.next()) {
                Actividad actividad = new Actividad();
                actividad.setNombre(rs.getString("nombre"));
                actividad.setCongresoNombre(rs.getString("congreso"));
                actividad.setSalonNombre(rs.getString("salon"));
                actividad.setCupo(rs.getInt("cupo"));
                actividad.setEstado(EstadoActividad.valueOf(rs.getString("estado")));
                actividad.setDescripcion(rs.getString("descripcion"));
                actividad.setTipo(TipoActividad.valueOf(rs.getString("tipo")));
                actividad.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
                actividad.setHoraFin(rs.getTime("hora_fin").toLocalTime());
                actividad.setDia(rs.getDate("dia").toLocalDate());
                actividad.setAutorId(rs.getString("autor"));

                actividades[j] = actividad;
                j++;
            }

            return actividades;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
    }

    public void crear(Actividad actividad) throws AccesoDeDatosException {
        String sql = "INSERT INTO actividad(nombre, congreso, cupo, estado, descripcion, tipo, hora_inicio, hora_fin, salon, dia, autor) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sqlEncargo = "INSERT INTO encargado_actividad (usuario, actividad_nombre, actividad_congreso) "
                + "VALUES (?, ?, ?)";
        
        Connection conn = ConexionBD.getInstance().getConnection();
        
        try {
            conn.setAutoCommit(false);
            try(PreparedStatement ps = conn.prepareStatement(sql)){
            
                ps.setString(1, actividad.getNombre());
                ps.setString(2, actividad.getCongresoNombre());
                ps.setInt(3, actividad.getCupo());
                ps.setString(4, actividad.getEstado().name());
                ps.setString(5, actividad.getDescripcion());
                ps.setString(6, actividad.getTipo().name());
                ps.setTime(7, Time.valueOf(actividad.getHoraInicio()));
                ps.setTime(8, Time.valueOf(actividad.getHoraFin()));
                ps.setString(9, actividad.getSalonNombre());
                ps.setDate(10, Date.valueOf(actividad.getDia()));
                ps.setString(11, actividad.getAutorId());

                int filasAfectadas = ps.executeUpdate();

                if(filasAfectadas < 1) throw new SQLException("Error en el servidor");
            }
            
            try(PreparedStatement ps2 = conn.prepareStatement(sqlEncargo)){
                
                ps2.setString(1, actividad.getAutorId());
                ps2.setString(2, actividad.getNombre());
                ps2.setString(3, actividad.getCongresoNombre());
                
                int filasAfectadas = ps2.executeUpdate();

                if(filasAfectadas < 1) throw new SQLException("Error en el servidor");
            }
            
            try (PreparedStatement ps = conn.prepareStatement(sqlEncargo)) {
                for (EncargadoActividad e : actividad.getEncargados()) {
                    if(!actividad.getAutorId().equals(e.getUsuario())){
                        
                        ps.setString(1, e.getUsuario());
                        ps.setString(2, actividad.getNombre());
                        ps.setString(3, actividad.getCongresoNombre());

                        int filasAfectadas = ps.executeUpdate();
                        if (filasAfectadas < 1) {
                            conn.rollback();
                            throw new SQLException("Error en el servidor");
                        }
                    }
                    
                }
            }
            
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            throw new AccesoDeDatosException("Error en el servidor");
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        
        
    }
        
    public void actualizar(Actividad actividad) throws AccesoDeDatosException {
        StringBuilder sql = new StringBuilder("UPDATE actividad SET ");
        boolean coma = false;

        if (actividad.getCupo() > 0) {
            sql.append("cupo = ?");
            coma = true;
        }
        if (actividad.getEstado() != null) {
            if (coma) sql.append(", ");
            sql.append("estado = ?");
            coma = true;
        }
        if (actividad.getDescripcion() != null) {
            if (coma) sql.append(", ");
            sql.append("descripcion = ?");
            coma = true;
        }

        if (!coma) {
            return;
        }

        sql.append(" WHERE nombre = ? AND congreso = ?");

        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int i = 1;

            if (actividad.getCupo() > 0) {
                ps.setInt(i++, actividad.getCupo());
            }
            if (actividad.getEstado() != null) {
                ps.setString(i++, actividad.getEstado().name());
            }
            if (actividad.getDescripcion() != null) {
                ps.setString(i++, actividad.getDescripcion());
            }

            ps.setString(i++, actividad.getNombre());
            ps.setString(i, actividad.getCongresoNombre());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas < 1) {
                throw new AccesoDeDatosException("No se pudo actualizar la actividad");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
    }
    
    public Asistencia[] leerAsistenciasPorActividad(Actividad a) throws AccesoDeDatosException {
        String sql = "SELECT usuario, actividad_nombre, actividad_congreso "
                   + "FROM asistencia WHERE actividad_nombre = ? AND actividad_congreso = ?";

        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {

            ps.setString(1, a.getNombre());
            ps.setString(2, a.getCongresoNombre());

            ResultSet rs = ps.executeQuery();

            Asistencia[] asistencias = new Asistencia[obtenerLongitudDeResultSet(rs)];
            int i = 0;
            while (rs.next()) {
                Asistencia asistencia = new Asistencia();
                asistencia.setUsuario(rs.getString("usuario"));
                asistencia.setActividadNombre(rs.getString("actividad_nombre"));
                asistencia.setActividadCongresoNombre(rs.getString("actividad_congreso"));
                asistencias[i] = asistencia;
                i++;
            }

            return asistencias;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error al leer asistencias por actividad");
        }
    }

    public void crearReservacion(Reservacion reservacion) throws AccesoDeDatosException {
        String sql = "INSERT INTO reserva (usuario, actividad_nombre, actividad_congreso) VALUES (?, ?, ?)";

        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, reservacion.getUsuario());
            ps.setString(2, reservacion.getActividadNombre());
            ps.setString(3, reservacion.getActividadCongresoNombre());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new AccesoDeDatosException("Error al crear reservación");
        }
    }

    public Reservacion[] leerReservaciones(Actividad actividad) throws AccesoDeDatosException {
        String sql = "SELECT usuario, actividad_nombre, actividad_congreso FROM reserva " +
                     "WHERE actividad_nombre = ? AND actividad_congreso = ?";

        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {

            ps.setString(1, actividad.getNombre());
            ps.setString(2, actividad.getCongresoNombre());

            ResultSet rs = ps.executeQuery();

            Reservacion[] reservaciones = new Reservacion[obtenerLongitudDeResultSet(rs)];
            int i = 0;
            while (rs.next()) {
                Reservacion r = new Reservacion();
                r.setUsuario(rs.getString("usuario"));
                r.setActividadNombre(rs.getString("actividad_nombre"));
                r.setActividadCongresoNombre(rs.getString("actividad_congreso"));
                reservaciones[i++] = r;
            }

            return reservaciones;

        } catch (SQLException e) {
            throw new AccesoDeDatosException("Error al leer reservaciones");
        }
    }

    public void crearAsistencia(Asistencia a) throws AccesoDeDatosException {
        String sql = "INSERT INTO asistencia (usuario, actividad_nombre, actividad_congreso) VALUES (?, ?, ?)";

        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getUsuario());
            ps.setString(2, a.getActividadNombre());
            ps.setString(3, a.getActividadCongresoNombre());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new AccesoDeDatosException("Asistencia duplicada");
        }
    }


}
