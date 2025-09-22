/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Participacion;
import com.mynor.gestor.congresos.app.modelo.Rol;
import java.sql.*;

/**
 *
 * @author mynordma
 */
public class ParticipacionBD extends BaseDeDatos {

    public Participacion[] leerPorCongresoYRol(String nombreCongreso, Rol rol) throws AccesoDeDatosException {
        String sql = "SELECT * FROM participacion WHERE rol = ? AND congreso = ?";
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            
            ps.setString(1, rol.name());
            ps.setString(2, nombreCongreso);
            
            ResultSet rs = ps.executeQuery();

            Participacion[] participaciones = new Participacion[obtenerLongitudDeResultSet(rs)];
            int j = 0;
            while (rs.next()) {
                Participacion participacion = new Participacion();
                participacion.setCongresoNombre(rs.getString("congreso"));
                participacion.setUsuarioId(rs.getString("usuario"));
                participacion.setRol(Rol.valueOf(rs.getString("rol")));

                participaciones[j] = participacion;
                j++;
            }

            return participaciones;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
    }
    
    public void crear(Participacion p) throws AccesoDeDatosException {
        String sql = "INSERT INTO participacion(usuario, congreso, rol) VALUES(?, ?, ?)";
        String sqlInscripcion = "INSERT INTO inscripcion(usuario, congreso) VALUES(?, ?)";

        Connection conn = ConexionBD.getInstance().getConnection();

        try {
            conn.setAutoCommit(false);

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, p.getUsuarioId());
                ps.setString(2, p.getCongresoNombre());
                ps.setString(3, p.getRol().name());

                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas < 1) {
                    throw new SQLException("Error en el servidor");
                }
            }

            try (PreparedStatement ps2 = conn.prepareStatement(sqlInscripcion)) {
                ps2.setString(1, p.getUsuarioId());
                ps2.setString(2, p.getCongresoNombre());

                int filasAfectadas = ps2.executeUpdate();
                if (filasAfectadas < 1) {
                    throw new SQLException("Error en el servidor");
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
    
}
