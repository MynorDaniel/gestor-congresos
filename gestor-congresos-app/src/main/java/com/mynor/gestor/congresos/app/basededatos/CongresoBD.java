/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.FiltrosCongreso;
import java.sql.*;

/**
 *
 * @author mynordma
 */
public class CongresoBD extends BaseDeDatos {

    public Congreso crear(Congreso entidad) throws AccesoDeDatosException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
//    private String getSelectCongresosConJoins(Map<String, String> filtros){// EJ: SELECT * FROM congreso c JOIN usuario u ON c.creador = u.id JOIN afiliacion a ON u.id = a.usuario WHERE institucion = CUNOC AND fecha_inicio BETWEEN '2024-02-02' AND '2026-02-02'
//        StringBuilder query = new StringBuilder();
//        query.append("SELECT * FROM congreso c JOIN usuario u ON c.creador = u.id JOIN afiliacion a ON u.id = a.usuario WHERE 1=1 ");
//        
//        if(filtros.containsKey("institucion")){
//            query.append("AND institucion = ? ");
//        }
//        
//        if(filtros.containsKey("fecha")){
//            query.append("AND fecha BETWEEN ? AND ?");
//        }
//        
//        return query.toString();
//    }

    public Congreso[] leer(FiltrosCongreso filtros) throws AccesoDeDatosException {
        StringBuilder sql = new StringBuilder("SELECT * FROM congreso WHERE 1=1");

        if (filtros.getNombre() != null) {
            sql.append(" AND nombre = ?");
        }
        if (filtros.getCreador() != null) {
            sql.append(" AND creador = ?");
        }
        if (filtros.getInstalacion() != null) {
            sql.append(" AND instalacion = ?");
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
            if (filtros.getCreador() != null) {
                ps.setString(i, filtros.getCreador());
                i++;
            }
            if (filtros.getInstalacion() != null) {
                ps.setInt(i, filtros.getInstalacion());
                //i++;
            }

            ResultSet rs = ps.executeQuery();

            Congreso[] congresos = new Congreso[obtenerLongitudDeResultSet(rs)];
            int j = 0;
            while (rs.next()) {
                Congreso congreso = new Congreso();
                congreso.setNombre(rs.getString("nombre"));
                congreso.setCreador(rs.getString("creador"));
                congreso.setPrecio(rs.getDouble("precio"));
                congreso.setConvocando(rs.getBoolean("convocando"));
                congreso.setFechaInicio(rs.getDate("fecha").toLocalDate());
                congreso.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
                congreso.setDescripcion(rs.getString("descripcion"));
                congreso.setActivado(rs.getBoolean("activado"));

                congresos[j] = congreso;
                j++;
            }

            return congresos;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
    }


    
}
