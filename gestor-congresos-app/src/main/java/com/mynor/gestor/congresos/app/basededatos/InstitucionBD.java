/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Institucion;
import java.sql.*;

/**
 *
 * @author mynordma
 */
public class InstitucionBD extends BaseDeDatos {

    public Institucion[] leer() throws AccesoDeDatosException {
        String sql = "SELECT id, nombre FROM institucion";
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            
            ResultSet rs = ps.executeQuery();

            Institucion[] instituciones = new Institucion[obtenerLongitudDeResultSet(rs)];
            int j = 0;
            while (rs.next()) {
                Institucion institucion = new Institucion();
                institucion.setId(rs.getInt("id"));
                institucion.setNombre(rs.getString("nombre"));

                instituciones[j] = institucion;
                j++;
            }

            return instituciones;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
    }
    
    public Institucion crear(Institucion institucion) throws AccesoDeDatosException {
        String sql = "INSERT INTO institucion(nombre) VALUES(?)";

        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, institucion.getNombre());
            int filas = ps.executeUpdate();
            if (filas < 1) throw new AccesoDeDatosException("No se pudo crear la institución");

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    institucion.setId(rs.getInt(1));
                }
            }

            return institucion;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error al crear institución");
        }
    }

    public void actualizar(Institucion institucion) throws AccesoDeDatosException {
        String sql = "UPDATE institucion SET nombre = ? WHERE id = ?";

        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, institucion.getNombre());
            ps.setInt(2, institucion.getId());

            int filas = ps.executeUpdate();
            if (filas < 1) throw new AccesoDeDatosException("No se pudo actualizar la institución");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error al actualizar institución");
        }
    }

    public void borrar(Institucion institucion) throws AccesoDeDatosException {
        String sql = "DELETE FROM institucion WHERE id = ?";

        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, institucion.getId());

            int filas = ps.executeUpdate();
            if (filas < 1) throw new AccesoDeDatosException("No se pudo borrar la institución");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error al borrar institución");
        }
    }

    public Institucion leerPorId(int id) throws AccesoDeDatosException {
        String sql = "SELECT id, nombre FROM institucion WHERE id = ?";

        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Institucion institucion = new Institucion();
                institucion.setId(rs.getInt("id"));
                institucion.setNombre(rs.getString("nombre"));
                return institucion;
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error al leer institución por ID");
        }
    }
    
}
