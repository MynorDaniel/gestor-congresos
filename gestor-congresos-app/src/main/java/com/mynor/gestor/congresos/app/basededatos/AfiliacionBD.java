/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Afiliacion;
import com.mynor.gestor.congresos.app.modelo.Institucion;
import java.sql.*;

/**
 *
 * @author mynordma
 */
public class AfiliacionBD extends BaseDeDatos {

    // Leer todas las afiliaciones
    public Afiliacion[] leer() throws AccesoDeDatosException {
        String sql = "SELECT usuario, institucion FROM afiliacion";

        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = ps.executeQuery();
            Afiliacion[] afiliaciones = new Afiliacion[obtenerLongitudDeResultSet(rs)];
            int i = 0;
            while (rs.next()) {
                Afiliacion a = new Afiliacion();
                a.setUsuarioId(rs.getString("usuario"));
                
                Institucion inst = new Institucion();
                inst.setId(rs.getInt("institucion"));
                a.setInstitucion(inst);
                
                afiliaciones[i++] = a;
            }
            return afiliaciones;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error al leer afiliaciones");
        }
    }

    public void crear(Afiliacion afiliacion) throws AccesoDeDatosException {
        String sql = "INSERT INTO afiliacion(usuario, institucion) VALUES(?, ?)";

        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, afiliacion.getUsuarioId());
            ps.setInt(2, afiliacion.getInstitucion().getId());

            int filas = ps.executeUpdate();
            if (filas < 1) throw new AccesoDeDatosException("No se pudo crear la afiliación");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error al crear afiliación");
        }
    }

    public void actualizar(Afiliacion afiliacion) throws AccesoDeDatosException {
        String sql = "UPDATE afiliacion SET institucion = ? WHERE usuario = ?";

        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, afiliacion.getInstitucion().getId());
            ps.setString(2, afiliacion.getUsuarioId());

            int filas = ps.executeUpdate();
            if (filas < 1) throw new AccesoDeDatosException("No se pudo actualizar la afiliación");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error al actualizar afiliación");
        }
    }

    public void borrar(Afiliacion afiliacion) throws AccesoDeDatosException {
        String sql = "DELETE FROM afiliacion WHERE usuario = ? AND institucion = ?";

        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, afiliacion.getUsuarioId());
            ps.setInt(2, afiliacion.getInstitucion().getId());

            int filas = ps.executeUpdate();
            if (filas < 1) throw new AccesoDeDatosException("No se pudo borrar la afiliación");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error al borrar afiliación");
        }
    }

    public Afiliacion[] leerPorUsuario(String usuarioId) throws AccesoDeDatosException {
        String sql = "SELECT usuario, institucion FROM afiliacion WHERE usuario = ?";

        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {

            ps.setString(1, usuarioId);
            ResultSet rs = ps.executeQuery();

            Afiliacion[] afiliaciones = new Afiliacion[obtenerLongitudDeResultSet(rs)];

            int i = 0;
            while (rs.next()) {
                Afiliacion a = new Afiliacion();
                a.setUsuarioId(rs.getString("usuario"));

                Institucion inst = new Institucion();
                inst.setId(rs.getInt("institucion"));
                a.setInstitucion(inst);

                afiliaciones[i++] = a;
            }

            return afiliaciones;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error al leer afiliaciones del usuario");
        }
    }

}