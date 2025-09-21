/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Instalacion;
import com.mynor.gestor.congresos.app.modelo.Salon;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author mynordma
 */
public class InstalacionBD extends BaseDeDatos {

    public Instalacion crear(Instalacion instalacion) throws AccesoDeDatosException {
        String sqlInstalacion = "INSERT INTO instalacion(nombre, ubicacion) VALUES(?, ?)";
        String sqlSalon = "INSERT INTO salon(nombre, instalacion) VALUES(?, ?)";

        Connection conn = ConexionBD.getInstance().getConnection();

        try {
            conn.setAutoCommit(false);

            // Insertar la instalacion
            try (PreparedStatement ps = conn.prepareStatement(sqlInstalacion, Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, instalacion.getNombre());
                ps.setString(2, instalacion.getUbicacion());

                int filasAfectadas = ps.executeUpdate();
                if (filasAfectadas < 1) {
                    throw new AccesoDeDatosException("No se pudo guardar la instalación");
                }

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        instalacion.setId(idGenerado);
                    } else {
                        throw new AccesoDeDatosException("Error en el servidor");
                    }
                }
            }

            // Insertar todos los salones
            try (PreparedStatement psSalon = conn.prepareStatement(sqlSalon)) {
                for (Salon salon : instalacion.getSalones()) {
                    psSalon.setString(1, salon.getNombre());
                    psSalon.setInt(2, instalacion.getId());
                    psSalon.addBatch();
                }
                psSalon.executeBatch();
            }

            conn.commit();

        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            throw new AccesoDeDatosException("Error en el servidor");
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return instalacion;
    }

    public Instalacion[] leer() throws AccesoDeDatosException {
        String sql = "SELECT * FROM instalacion";
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            
            ResultSet rs = ps.executeQuery();

            Instalacion[] instalaciones = new Instalacion[obtenerLongitudDeResultSet(rs)];
            int j = 0;
            while (rs.next()) {
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

    public Salon[] leerSalones(int instalacionId) throws AccesoDeDatosException {
        String sql = "SELECT nombre FROM salon WHERE instalacion = ?";
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            
            ps.setInt(1, instalacionId);
            
            ResultSet rs = ps.executeQuery();

            Salon[] salones = new Salon[obtenerLongitudDeResultSet(rs)];
            int j = 0;
            while (rs.next()) {
                Salon salon = new Salon();
                salon.setNombre(rs.getString("nombre"));
                salon.setInstalacion(instalacionId);

                salones[j] = salon;
                j++;
            }

            return salones;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
    }

    public Instalacion leer(String nombreCongreso) throws AccesoDeDatosException {
        String sql = "SELECT i.id, i.nombre, i.ubicacion FROM instalacion i JOIN congreso c ON i.id = c.instalacion WHERE c.nombre = ?";
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            
            ps.setString(1, nombreCongreso);
            
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Instalacion instalacion = new Instalacion();
                instalacion.setId(rs.getInt("id"));
                instalacion.setNombre(rs.getString("nombre"));
                instalacion.setUbicacion(rs.getString("ubicacion"));
                return instalacion;
            }else{
                throw new AccesoDeDatosException("Error en el servidor");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
    }
    
}
