/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.FiltrosInscripcion;
import com.mynor.gestor.congresos.app.modelo.Inscripcion;
import java.sql.*;

/**
 *
 * @author mynordma
 */
public class InscripcionBD extends BaseDeDatos {

    public Inscripcion[] leer(FiltrosInscripcion filtros) throws AccesoDeDatosException {
        StringBuilder sql = new StringBuilder("SELECT * FROM inscripcion WHERE 1=1");

        if (filtros.getUsuarioId() != null) {
            sql.append(" AND usuario = ?");
        }
        
        if (filtros.getCongresoNombre()!= null) {
            sql.append(" AND congreso = ?");
        }
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
                sql.toString(),
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
            
            int i = 1;
            if (filtros.getUsuarioId() != null) {
                ps.setString(i, filtros.getUsuarioId());
                i++;
            }
            
            if (filtros.getCongresoNombre()!= null) {
                ps.setString(i, filtros.getCongresoNombre());
                i++;
            }
            
            System.out.println(sql.toString());
            ResultSet rs = ps.executeQuery();

            Inscripcion[] inscripciones = new Inscripcion[obtenerLongitudDeResultSet(rs)];
            int j = 0;
            while (rs.next()) {
                Inscripcion inscripcion = new Inscripcion();
                
                inscripcion.setUsuarioId(rs.getString("usuario"));
                inscripcion.setCongresoNombre(rs.getString("congreso"));

                inscripciones[j] = inscripcion;
                j++;
            }

            return inscripciones;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
    }
    
}
