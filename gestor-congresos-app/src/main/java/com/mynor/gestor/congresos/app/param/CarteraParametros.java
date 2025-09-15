/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.param;

import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.Cartera;
import com.mynor.gestor.congresos.app.modelo.Usuario;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author mynordma
 */
public class CarteraParametros extends Validador implements EntidadParseador<Cartera> {
    
    private final String usuario;
    private final String saldo;

    public CarteraParametros(HttpServletRequest request) {
        usuario = ((Usuario) request.getSession().getAttribute("usuarioSession")).getId();
        saldo = request.getParameter("monto");
    }

    @Override
    public Cartera toEntidad() throws UsuarioInvalidoException {
        if(!longitudValida(usuario, 30)) throw new UsuarioInvalidoException("Usuario inválido");
        if(!montoValido(saldo)) throw new UsuarioInvalidoException("Monto inválido");
        
        Cartera cartera = new Cartera();
        
        cartera.setUsuario(usuario);
        cartera.setSaldo(Double.parseDouble(saldo));
        
        return cartera;
    }
    
}
