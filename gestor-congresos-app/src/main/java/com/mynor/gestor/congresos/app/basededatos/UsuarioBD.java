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
        String sqlCartera = "INSERT INTO cartera(usuario) VALUES(?)";
        String sqlAfiliacion = "INSERT INTO afiliacion (usuario, institucion) VALUES(?, ?)";

        Connection conn = ConexionBD.getInstance().getConnection();

        try (PreparedStatement ps = conn.prepareStatement(sql);
             PreparedStatement ps2 = conn.prepareStatement(sqlCartera);
             PreparedStatement ps3 = conn.prepareStatement(sqlAfiliacion)) {

            conn.setAutoCommit(false);

            ps.setString(1, usuario.getId());
            ps.setString(2, usuario.getClave());
            ps.setString(3, usuario.getNombre());
            ps.setString(4, usuario.getNumero());
            ps.setBoolean(5, usuario.isActivado());
            ps.setString(6, usuario.getCorreo());
            ps.setString(7, usuario.getRol().name());

            ps2.setString(1, usuario.getId());
            
            ps3.setString(1, usuario.getId());
            ps3.setInt(2, usuario.getInstitucion());

            int filasAfectadas = ps.executeUpdate();
            int filasAfectadas2 = ps2.executeUpdate();
            int filasAfectadas3 = ps3.executeUpdate();

            if (filasAfectadas < 1 || filasAfectadas2 < 1 || filasAfectadas3 < 1) {
                conn.rollback();
                throw new SQLException("Error en el servidor");
            }

            conn.commit();

            return usuario;

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                throw new AccesoDeDatosException("Error en el servidor");
            }
        }
    }


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

    public Usuario[] leer(FiltrosUsuario filtros) throws AccesoDeDatosException {
        StringBuilder sql = new StringBuilder("SELECT * FROM usuario WHERE 1=1");

        if (filtros.getId()!= null) {
            sql.append(" AND id = ?");
        }
        if (filtros.getCorreo() != null) {
            sql.append(" AND correo = ?");
        }
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
                sql.toString(),
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
            
            int i = 1;
            if (filtros.getId() != null) {
                ps.setString(i, filtros.getId());
                i++;
            }
            if (filtros.getCorreo() != null) {
                ps.setString(i, filtros.getCorreo());
                i++;
            }

            ResultSet rs = ps.executeQuery();

            Usuario[] usuarios = new Usuario[obtenerLongitudDeResultSet(rs)];
            
            int j = 0;
            while(rs.next()){
                Usuario usuario = new Usuario();
                
                usuario.setId(rs.getString("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setNumero(rs.getString("numero"));
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
    
}
