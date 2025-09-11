/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.dominio.Usuario;
import com.mynor.gestor.congresos.app.modelo.fabricacionpura.RolSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author mynordma
 */
public class UsuarioBD extends BaseDeDatos <Usuario> {
    
    @Override
    public Usuario crear(Usuario usuario) throws AccesoDeDatosException {
        String sql = getInsert("usuario", usuario.getValores());
        
        Connection conn = ConexionBD.getInstance().getConnection();  
        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
            
            asignarValoresAPreparedStatement(ps, usuario.getValores());
            int filasAfectadas = ps.executeUpdate();
                    
            if(filasAfectadas < 1) throw new AccesoDeDatosException("Error en el servidor");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
        
        return usuario;
    }
    
    @Override
    public Usuario[] leer(Map<String, String> filtros) throws AccesoDeDatosException {
        String sql = getSelect("usuario", filtros);
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
            
            asignarValoresAPreparedStatement(ps, filtros.values().toArray(String[]::new));
            ResultSet rs = ps.executeQuery();
            
            Usuario[] usuarios = new Usuario[obtenerLongitudDeResultSet(rs)];
            
            int j = 0;
            while(rs.next()){
                Usuario usuario = new Usuario();
                
                usuario.setId(rs.getString("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setActivado(rs.getBoolean("activado"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setRol(RolSistema.valueOf(rs.getString("rol_sistema")));
                //usuario.setFoto(foto);

                usuarios[j] = usuario;
                j++;
            }
            
            return usuarios;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
    }
    
    @Override
    public Usuario actualizar(Usuario usuario) throws AccesoDeDatosException {
        return usuario;
    }
    
    @Override
    public Usuario eliminar(Usuario usuario) throws AccesoDeDatosException {
        return usuario;
    }
    
}
