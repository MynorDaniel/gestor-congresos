/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.dominio.Salon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author mynordma
 */
public class SalonBD extends BaseDeDatos<Salon>{

    @Override
    public Salon crear(Salon salon) throws AccesoDeDatosException {
        String sql = getInsert("salon", salon.getValores());
        
        Connection conn = ConexionBD.getInstance().getConnection();  
        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
            
            asignarValoresAPreparedStatement(ps, salon.getValores());
            int filasAfectadas = ps.executeUpdate();
                    
            if(filasAfectadas < 1) throw new AccesoDeDatosException("Error en el servidor");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
        
        return salon;
    }

    @Override
    public Salon[] leer(Map<String, String> filtros) throws AccesoDeDatosException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Salon actualizar(Salon salon) throws AccesoDeDatosException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Salon eliminar(Salon salon) throws AccesoDeDatosException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
