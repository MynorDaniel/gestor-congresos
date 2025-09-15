/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.basededatos.CarteraBD;
import com.mynor.gestor.congresos.app.excepcion.AccesoDeDatosException;
import com.mynor.gestor.congresos.app.modelo.Cartera;
import com.mynor.gestor.congresos.app.modelo.Usuario;

/**
 *
 * @author mynordma
 */
public class ManejadorDeCartera {
    
    public Cartera obtenerCartera(Usuario usuario) throws AccesoDeDatosException {
        CarteraBD carteraBD = new CarteraBD();
        return carteraBD.leer(usuario.getId());
    }

    public Cartera actualizar(Cartera carteraTemporal) throws AccesoDeDatosException {
        CarteraBD carteraBD = new CarteraBD();
        Cartera carteraReal = carteraBD.leer(carteraTemporal.getUsuario());
        carteraReal.setSaldo(carteraReal.getSaldo() + carteraTemporal.getSaldo());
        return carteraBD.actualizar(carteraReal);
    }
}
