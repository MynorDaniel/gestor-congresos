/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author mynordma
 */
public class Validador {
    
    protected boolean fechaValida(String fechaStr){
        try {
            LocalDate.parse(fechaStr);
            return true;
        } catch (DateTimeParseException | NullPointerException e) {
            return false;
        }
    }
    
    protected boolean montoValido(String montoStr) {
        try {
            double monto = Double.parseDouble(montoStr);
            return monto >= 0;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }
    
    protected boolean longitudValida(String valor, int longitudMaxima) {
        return !StringUtils.isBlank(valor)
                && valor.trim().length() <= longitudMaxima;
    }
    
    protected boolean esEnteroPositivo(String valor){
        try {
            int entero = Integer.parseInt(valor);
            return entero >= 0;
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
    }
    
    protected boolean correoValido(String correo){
        return !StringUtils.isBlank(correo)
                && correo.trim().length() <= 320
                && correo.matches(".+@.+\\..+");
    }
}
