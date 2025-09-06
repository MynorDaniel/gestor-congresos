/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.modelo.dominio.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author mynordma
 */
public class UsuarioBD {
    
    public Usuario crear(Usuario usuario){
        return usuario;
    }
    
    public Usuario[] leer(Map<String, String> filtros){
        QuerySQL querySQL = new QuerySQL();
        String sql = querySQL.getSelect("usuario", filtros);
        
        Connection conn = ConexionBD.getInstance().getConnection(); 
        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
            
            int i = 1;
            for (String filtroValor : filtros.values()) {
                ps.setString(i, filtroValor);
                i++;
            }
            
            ResultSet rs = ps.executeQuery();
            
            Usuario[] usuarios = new Usuario[obtenerLongitudDeResultSet(rs)];
            
            int j = 0;
            while(rs.next()){
                Usuario usuario = new Usuario();
                
                usuario.setId(rs.getString("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setActivado(rs.getBoolean("activado"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setAdmin(rs.getBoolean("esAdmin"));
                //usuario.setFoto(foto);

                usuarios[j] = usuario;
                j++;
            }
            
            return usuarios;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new Usuario[0];
        }
    }
    
    public Usuario actualizar(Usuario usuario){
        return usuario;
    }
    
    public Usuario eliminar(Usuario usuario){
        return usuario;
    }
    
    private int obtenerLongitudDeResultSet(ResultSet rs) throws SQLException{
        if (rs.last()) {
            int longitud = rs.getRow();
            rs.beforeFirst();
            return longitud;
        }else{
            return 0;
        }
    }
}
