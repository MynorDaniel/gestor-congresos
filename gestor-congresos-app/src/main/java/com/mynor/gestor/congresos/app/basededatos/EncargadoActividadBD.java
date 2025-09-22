/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.EncargadoActividad;
import com.mynor.gestor.congresos.app.modelo.FiltrosUsuarioActividad;
import java.sql.*;

/**
 *
 * @author mynordma
 */
public class EncargadoActividadBD extends BaseDeDatos {

    public EncargadoActividad[] leer(FiltrosUsuarioActividad filtros) throws AccesoDeDatosException {
        StringBuilder sql = new StringBuilder("SELECT * FROM encargado_actividad WHERE 1=1");

        if (filtros.getActividadCongreso()!= null) {
            sql.append(" AND actividad_congreso = ?");
        }
        if (filtros.getActividadNombre()!= null) {
            sql.append(" AND actividad_nombre = ?");
        }
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
                sql.toString(),
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
            
            int i = 1;
            if (filtros.getActividadCongreso()!= null) {
                ps.setString(i, filtros.getActividadCongreso());
                i++;
            }
            if (filtros.getActividadNombre()!= null) {
                ps.setString(i, filtros.getActividadNombre());
                i++;
            }

            ResultSet rs = ps.executeQuery();

            EncargadoActividad[] encargados = new EncargadoActividad[obtenerLongitudDeResultSet(rs)];
            
            int j = 0;
            while(rs.next()){
                EncargadoActividad encargado = new EncargadoActividad();
                
                encargado.setActividadCongresoNombre(rs.getString("actividad_congreso"));
                encargado.setActividadNombre(rs.getString("actividad_nombre"));
                encargado.setUsuario(rs.getString("usuario"));

                encargados[j] = encargado;
                j++;
            }

            return encargados;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
    }
    
}
