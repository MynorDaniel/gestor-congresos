/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.dominio.Instalacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 *
 * @author mynordma
 */
public class InstalacionBD extends BaseDeDatos<Instalacion> {

    @Override
    public Instalacion crear(Instalacion instalacion) throws AccesoDeDatosException {
        String sql = getInsert("instalacion", instalacion.getValores());

        Connection conn = ConexionBD.getInstance().getConnection();  
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            asignarValoresAPreparedStatement(ps, instalacion.getValores());

            int filasAfectadas = ps.executeUpdate();
            if (filasAfectadas < 1) {
                throw new AccesoDeDatosException("Error en el servidor");
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    instalacion.setId(idGenerado);
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }

        return instalacion;
    }


    @Override
    public Instalacion[] leer(Map<String, String> filtros) throws AccesoDeDatosException {
        String sql = getSelect("instalacion", filtros);
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
            
            asignarValoresAPreparedStatement(ps, filtros.values().toArray(String[]::new));
            ResultSet rs = ps.executeQuery();
            System.out.println(sql);
            
            Instalacion[] instalaciones = new Instalacion[obtenerLongitudDeResultSet(rs)];
            
            int j = 0;
            while(rs.next()){
                Instalacion instalacion = new Instalacion();
                instalacion.setId(rs.getInt("id"));
                instalacion.setNombre(rs.getString("nombre"));
                instalacion.setUbicacion(rs.getString("ubicacion"));
                instalaciones[j] = instalacion;
                j++;
            }
            
            return instalaciones;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
    }

    @Override
    public Instalacion actualizar(Instalacion instalacion) throws AccesoDeDatosException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Instalacion eliminar(Instalacion instalacion) throws AccesoDeDatosException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
