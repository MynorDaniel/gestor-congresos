/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author mynordma
 */
public interface RequestParseador {
    
    public void asignarValoresDesdeRequest(HttpServletRequest request);
}