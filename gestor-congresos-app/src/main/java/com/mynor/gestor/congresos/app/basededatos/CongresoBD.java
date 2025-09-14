/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.dominio.Congreso;
import java.sql.*;
import java.util.Map;

/**
 *
 * @author mynordma
 */
public class CongresoBD extends BaseDeDatos <Congreso> {

    @Override
    public Congreso crear(Congreso entidad) throws AccesoDeDatosException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Congreso[] leer(Map<String, String> filtros) throws AccesoDeDatosException {
        String sql = (filtros.containsKey("institucion") || filtros.containsKey("fecha")) 
             ? getSelectCongresosConJoins(filtros)
             : getSelect("congreso", filtros);
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
            
            asignarValoresAPreparedStatement(ps, filtros.values().toArray(String[]::new));
            ResultSet rs = ps.executeQuery();
            
            Congreso[] congresos = new Congreso[obtenerLongitudDeResultSet(rs)];
            
            int j = 0;
            while(rs.next()){
                Congreso congreso = new Congreso();
                congreso.setNombre(rs.getString("nombre"));
                congreso.setCreador(rs.getString("creador"));
                congreso.setPrecio(rs.getDouble("precio"));
                congreso.setConvocando(rs.getBoolean("convocando"));
                congreso.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());

                java.sql.Date sqlFechaFin = rs.getDate("fecha_fin");
                if (sqlFechaFin != null) {
                    congreso.setFechaFin(sqlFechaFin.toLocalDate());
                }

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

    @Override
    public Congreso actualizar(Congreso entidad) throws AccesoDeDatosException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Congreso eliminar(Congreso entidad) throws AccesoDeDatosException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    private String getSelectCongresosConJoins(Map<String, String> filtros){// EJ: SELECT * FROM congreso c JOIN usuario u ON c.creador = u.id JOIN afiliacion a ON u.id = a.usuario WHERE institucion = CUNOC AND fecha_inicio BETWEEN '2024-02-02' AND '2026-02-02'
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM congreso c JOIN usuario u ON c.creador = u.id JOIN afiliacion a ON u.id = a.usuario WHERE 1=1 ");
        
        if(filtros.containsKey("institucion")){
            query.append("AND institucion = ? ");
        }
        
        if(filtros.containsKey("fecha_inicio")){
            query.append("AND fecha_inicio BETWEEN ? AND ?");
        }
        
        return query.toString();
    }
    
}
