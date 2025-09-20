/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Institucion;
import java.sql.*;

/**
 *
 * @author mynordma
 */
public class InstitucionBD extends BaseDeDatos {

    public Institucion[] leer() throws AccesoDeDatosException {
        String sql = "SELECT id, nombre FROM institucion";
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            
            ResultSet rs = ps.executeQuery();

            Institucion[] instituciones = new Institucion[obtenerLongitudDeResultSet(rs)];
            int j = 0;
            while (rs.next()) {
                Institucion institucion = new Institucion();
                institucion.setId(rs.getInt("id"));
                institucion.setNombre(rs.getString("nombre"));

                instituciones[j] = institucion;
                j++;
            }

            return instituciones;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
    }
    
}
