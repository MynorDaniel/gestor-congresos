/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import com.mynor.gestor.congresos.app.modelo.CredencialesLogin;
import com.mynor.gestor.congresos.app.modelo.FiltrosUsuario;
import com.mynor.gestor.congresos.app.modelo.RolSistema;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mynordma
 */
public class UsuarioBD extends BaseDeDatos {
    
    public Usuario crear(Usuario usuario) throws AccesoDeDatosException {
        String sql = "INSERT INTO usuario(id, clave, nombre, numero, activado, correo, rol_sistema) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = ConexionBD.getInstance().getConnection();  
        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
            
            ps.setString(1, usuario.getId());
            ps.setString(2, usuario.getClave());
            ps.setString(3, usuario.getNombre());
            ps.setString(4, usuario.getNumero());
            ps.setBoolean(5, usuario.isActivado());
            ps.setString(6, usuario.getCorreo());
            ps.setString(7, usuario.getRol().name());
            
            int filasAfectadas = ps.executeUpdate();
                    
            if(filasAfectadas < 1) throw new AccesoDeDatosException("Error en el servidor");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
        
        return usuario;
    }
    
//    public Usuario[] leer(Map<String, String> filtros) throws AccesoDeDatosException {
//        String sql = getSelect("usuario", filtros);
//        
//        Connection conn = ConexionBD.getInstance().getConnection();
//        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
//            
//            asignarValoresAPreparedStatement(ps, filtros.values().toArray(String[]::new));
//            ResultSet rs = ps.executeQuery();
//            
//            Usuario[] usuarios = new Usuario[obtenerLongitudDeResultSet(rs)];
//            
//            int j = 0;
//            while(rs.next()){
//                Usuario usuario = new Usuario();
//                
//                usuario.setId(rs.getString("id"));
//                usuario.setNombre(rs.getString("nombre"));
//                usuario.setActivado(rs.getBoolean("activado"));
//                usuario.setCorreo(rs.getString("correo"));
//                usuario.setRol(RolSistema.valueOf(rs.getString("rol_sistema")));
//                //usuario.setFoto(foto);
//
//                usuarios[j] = usuario;
//                j++;
//            }
//            
//            return usuarios;
//            
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            throw new AccesoDeDatosException("Error en el servidor");
//        }
//    }

    public Usuario existe(CredencialesLogin credenciales) throws AccesoDeDatosException, UsuarioInvalidoException {
        String sql = "SELECT * FROM usuario WHERE correo = ? AND clave = ? AND activado = 1";
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try(PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)){
            
            ps.setString(1, credenciales.getCorreo());
            ps.setString(2, credenciales.getClave());
            
            ResultSet rs = ps.executeQuery();
            
            Usuario usuario = new Usuario();
            
            if(rs.next()){
                usuario.setId(rs.getString("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setActivado(rs.getBoolean("activado"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setRol(RolSistema.valueOf(rs.getString("rol_sistema")));
                //usuario.setFoto(foto);
            }else{
                throw new UsuarioInvalidoException("Usuario no existe");
            }
            
            return usuario;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
    }

    public RolSistema obtenerRolDeUsuario(String creador) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Usuario[] leer(FiltrosUsuario filtroId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
