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

    public Congreso crear(Congreso congreso) throws AccesoDeDatosException {
        String sql = "INSERT INTO congreso(nombre, creador, precio, convocando, fecha, fecha_fin, descripcion, activado, instalacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = ConexionBD.getInstance().getConnection();  
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setString(1, congreso.getNombre());
            ps.setString(2, congreso.getCreador());
            ps.setDouble(3, congreso.getPrecio());
            ps.setBoolean(4, congreso.isConvocando());
            ps.setDate(5, Date.valueOf(congreso.getFechaInicio()));
            ps.setDate(6, Date.valueOf(congreso.getFechaFin()));
            ps.setString(7, congreso.getDescripcion());
            ps.setBoolean(8, congreso.isActivado());
            ps.setInt(9, congreso.getInstalacionId());
            
            int filasAfectadas = ps.executeUpdate();
                    
            if(filasAfectadas < 1) throw new AccesoDeDatosException("Error en el servidor");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
        
        return congreso;
    }
    
//  SELECT * FROM congreso c JOIN usuario u ON c.creador = u.id JOIN afiliacion a ON u.id = a.usuario WHERE institucion = CUNOC AND fecha_inicio BETWEEN '2024-02-02' AND '2026-02-02'
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
        
        if(filtros.getFechaInicio() != null && filtros.getFechaFin() != null){
            sql.append(" AND fecha BETWEEN ? AND ?");
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
                i++;
            }
            
            if(filtros.getFechaInicio() != null && filtros.getFechaFin() != null){
                ps.setDate(i, Date.valueOf(filtros.getFechaInicio()));
                i++;
                
                ps.setDate(i, Date.valueOf(filtros.getFechaFin()));
                i++;
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
                congreso.setInstalacionId(rs.getInt("instalacion"));

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
