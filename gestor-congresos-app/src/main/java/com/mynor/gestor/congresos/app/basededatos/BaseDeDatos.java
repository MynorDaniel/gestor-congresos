/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 *
 * @author mynordma
 * @param <T>
 */
public abstract class BaseDeDatos <T> {
    
    public abstract T crear(T entidad) throws AccesoDeDatosException;
    public abstract T[] leer(Map<String, String> filtros) throws AccesoDeDatosException;
    public abstract T actualizar(T entidad) throws AccesoDeDatosException;
    public abstract T eliminar(T entidad) throws AccesoDeDatosException;
    
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
    
    protected String getInsert(String tabla, String[] valores){ // INSERT INTO tabla VALUES(?, ?, ?, ?)
        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        query.append(tabla);
        query.append(" VALUES(");
        
        for (String valor : valores) {
            System.out.println(valor);
            query.append("?, ");
        }

        query.setLength(query.length() - 2);
        query.append(")");
        
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
    
    protected void asignarValoresAPreparedStatement(PreparedStatement ps, String[] valores) throws SQLException {
        int i = 1;
        for (String valor : valores) {
            ps.setString(i, valor);
            i++;
        }
    }
    
}