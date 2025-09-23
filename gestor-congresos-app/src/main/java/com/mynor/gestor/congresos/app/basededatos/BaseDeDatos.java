/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author mynordma
 */
interface BaseDeDatos {
    
    default int obtenerLongitudDeResultSet(ResultSet rs) throws SQLException{
        if (rs.last()) {
            int longitud = rs.getRow();
            rs.beforeFirst();
            return longitud;
        }else{
            return 0;
        }
    }
    
}