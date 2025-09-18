/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Congreso;
import com.mynor.gestor.congresos.app.modelo.FiltrosInscripcion;
import com.mynor.gestor.congresos.app.modelo.Inscripcion;
import java.sql.*;

/**
 *
 * @author mynordma
 */
public class InscripcionBD extends BaseDeDatos {

    public Inscripcion[] leer(FiltrosInscripcion filtros) throws AccesoDeDatosException {
        StringBuilder sql = new StringBuilder("SELECT * FROM inscripcion WHERE 1=1");

        if (filtros.getUsuarioId() != null) {
            sql.append(" AND usuario = ?");
        }
        
        if (filtros.getCongresoNombre()!= null) {
            sql.append(" AND congreso = ?");
        }
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(
                sql.toString(),
                ResultSet.TYPE_SCROLL_INSENSITIVE,
                ResultSet.CONCUR_READ_ONLY)) {
            
            int i = 1;
            if (filtros.getUsuarioId() != null) {
                ps.setString(i, filtros.getUsuarioId());
                i++;
            }
            
            if (filtros.getCongresoNombre()!= null) {
                ps.setString(i, filtros.getCongresoNombre());
                i++;
            }
            
            System.out.println(sql.toString());
            ResultSet rs = ps.executeQuery();

            Inscripcion[] inscripciones = new Inscripcion[obtenerLongitudDeResultSet(rs)];
            int j = 0;
            while (rs.next()) {
                Inscripcion inscripcion = new Inscripcion();
                
                inscripcion.setUsuarioId(rs.getString("usuario"));
                inscripcion.setCongresoNombre(rs.getString("congreso"));

                inscripciones[j] = inscripcion;
                j++;
            }

            return inscripciones;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
    }

    public void crear(Inscripcion inscripcion, Congreso congreso) throws AccesoDeDatosException {
        String sqlInscripcion = "INSERT INTO inscripcion(usuario, congreso) "
                   + "VALUES (?, ?)";
        String sqlPago = "INSERT INTO pago(usuario, congreso, monto, fecha, comision_cobrada) "
                + "VALUES(?, ?, ?, ?, ?)";
        String sqlCarteraResta = "UPDATE cartera SET saldo = saldo - ? WHERE usuario = ?";
        String sqlCarteraAdminCongreso = "UPDATE cartera SET saldo = saldo + (? - (? * ?)) WHERE usuario = ?";
        String sqlCarteraAdminSistema = "UPDATE cartera SET saldo = saldo + (? * ?) WHERE usuario = ?";

        Connection conn = ConexionBD.getInstance().getConnection();

        try (PreparedStatement ps1 = conn.prepareStatement(sqlInscripcion);
             PreparedStatement ps2 = conn.prepareStatement(sqlPago);
             PreparedStatement psCarteraParticipante = conn.prepareStatement(sqlCarteraResta);
             PreparedStatement psCarteraAdminCongreso = conn.prepareStatement(sqlCarteraAdminCongreso);
             PreparedStatement psCarteraAdminSistema = conn.prepareStatement(sqlCarteraAdminSistema)) {

            conn.setAutoCommit(false);

            ps1.setString(1, inscripcion.getUsuarioId());
            ps1.setString(2, inscripcion.getCongresoNombre());

            ps2.setString(1, inscripcion.getUsuarioId());
            ps2.setString(2, inscripcion.getCongresoNombre());
            ps2.setDouble(3, inscripcion.getPago().getMonto());
            ps2.setDate(4, Date.valueOf(inscripcion.getPago().getFecha()));
            ps2.setDouble(5, inscripcion.getPago().getComisionCobrada());
            
            psCarteraParticipante.setDouble(1, inscripcion.getPago().getMonto());
            psCarteraParticipante.setString(2, inscripcion.getUsuarioId());
            
            psCarteraAdminCongreso.setDouble(1, inscripcion.getPago().getMonto());
            psCarteraAdminCongreso.setDouble(2, inscripcion.getPago().getMonto());
            psCarteraAdminCongreso.setDouble(3, inscripcion.getPago().getComisionCobrada());
            psCarteraAdminCongreso.setString(4, congreso.getCreador());
            
            psCarteraAdminSistema.setDouble(1, inscripcion.getPago().getMonto());
            psCarteraAdminSistema.setDouble(2, inscripcion.getPago().getComisionCobrada());
            psCarteraAdminSistema.setString(3, "0");

            int filasAfectadas1 = ps1.executeUpdate();
            int filasAfectadas2 = ps2.executeUpdate();
            int filasAfectadas3 = psCarteraParticipante.executeUpdate();
            int filasAfectadas4 = psCarteraAdminCongreso.executeUpdate();
            int filasAfectadas5 = psCarteraAdminSistema.executeUpdate();

            if (filasAfectadas1 < 1 || filasAfectadas2 < 1 || filasAfectadas3 < 1 || filasAfectadas4 < 1 || filasAfectadas5 < 1) {
                conn.rollback();
                throw new AccesoDeDatosException("Error en el servidor");
            }

            conn.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            try {
                conn.rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
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
    
    public double obtenerComision() throws AccesoDeDatosException {
        String sql = "SELECT valor FROM configuracion_pago WHERE tipo = 'comision'";
        
        Connection conn = ConexionBD.getInstance().getConnection();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                double comision = rs.getDouble("valor");
                return comision;
            }else{
                throw new AccesoDeDatosException("Error en el servidor");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new AccesoDeDatosException("Error en el servidor");
        }
    }
    
}
