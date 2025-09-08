/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author mynordma
 */
public class BaseDeDatos {
    
    protected String getSelect(String tabla, Map<String, String> filtros){
        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM ");
        query.append(tabla);
        query.append(" WHERE 1=1 ");
        
        for (String filtro : filtros.keySet()) {
            query.append("AND ");
            query.append(filtro);
            query.append(" = ? ");
        }
        
        return query.toString();
    }
    
    protected int obtenerLongitudDeResultSet(ResultSet rs) throws SQLException{
        if (rs.last()) {
            int longitud = rs.getRow();
            rs.beforeFirst();
            return longitud;
        }else{
            return 0;
        }
    }
    
    protected void asignarValoresAPreparedStatement(PreparedStatement ps, Map<String, String> filtros) throws SQLException {
        int i = 1;
        for (String filtroValor : filtros.values()) {
            ps.setString(i, filtroValor);
            i++;
        }
    }
}