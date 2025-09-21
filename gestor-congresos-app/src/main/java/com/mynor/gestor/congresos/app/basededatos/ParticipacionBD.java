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
    
}
