/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Pago;
import java.sql.*;

/**
 *
 * @author mynordma
 */
public class PagoBD extends BaseDeDatos {
    
    public Pago[] leer() throws AccesoDeDatosException {
        String sql = "SELECT usuario, congreso, monto, fecha, comision_cobrada FROM pago";

        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {

            ResultSet rs = ps.executeQuery();

            Pago[] pagos = new Pago[obtenerLongitudDeResultSet(rs)];
            int i = 0;
            while (rs.next()) {
                Pago p = new Pago();
                p.setMonto(rs.getDouble("monto"));
                p.setFecha(rs.getDate("fecha").toLocalDate());
                p.setComisionCobrada(rs.getDouble("comision_cobrada"));
                pagos[i++] = p;
            }

            return pagos;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error al leer pagos");
        }
    }

    public Pago[] leerPorCongreso(String congresoNombre) throws AccesoDeDatosException {
        String sql = "SELECT usuario, congreso, monto, fecha, comision_cobrada FROM pago WHERE congreso = ?";

        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql,
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {

            ps.setString(1, congresoNombre);
            ResultSet rs = ps.executeQuery();

            Pago[] pagos = new Pago[obtenerLongitudDeResultSet(rs)];
            int i = 0;
            while (rs.next()) {
                Pago p = new Pago();
                p.setMonto(rs.getDouble("monto"));
                p.setFecha(rs.getDate("fecha").toLocalDate());
                p.setComisionCobrada(rs.getDouble("comision_cobrada"));
                pagos[i++] = p;
            }

            return pagos;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error al leer pagos por congreso");
        }
    }
}
