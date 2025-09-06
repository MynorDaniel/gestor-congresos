/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import java.util.Map;

/**
 *
 * @author mynordma
 */
public class QuerySQL {

    public String getSelect(String tabla, Map<String, String> filtros) {
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
    
}
