/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.EntidadInvalidaException;

/**
 *
 * @author mynordma
 * @param <T>
 */
public interface EntidadParseador<T> {
    
    public T toEntidad() throws EntidadInvalidaException;
    
}
