/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Cartera;
import java.sql.*;

/**
 *
 * @author mynordma
 */
public class CarteraBD extends BaseDeDatos {
    
    public Cartera leer(String idUsuario) throws AccesoDeDatosException {
        String sql = "SELECT usuario, saldo FROM cartera WHERE usuario = ?";
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, idUsuario);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                Cartera cartera = new Cartera();
                
                cartera.setUsuario(rs.getString("usuario"));
                cartera.setSaldo(rs.getDouble("saldo"));
                
                return cartera;
            }else{
                throw new AccesoDeDatosException("Error en el servidor al consultar el saldo");
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
    }

    public Cartera actualizar(Cartera carteraReal) throws AccesoDeDatosException {
        String sql = "UPDATE cartera SET saldo = ? WHERE usuario = ?";
        
        Connection conn = ConexionBD.getInstance().getConnection();  
        try(PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setDouble(1, carteraReal.getSaldo());
            ps.setString(2, carteraReal.getUsuario());
            
            int filasAfectadas = ps.executeUpdate();
                    
            if(filasAfectadas < 1) throw new AccesoDeDatosException("Error en el servidor");
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
        
        return carteraReal;
    }
}
