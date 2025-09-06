/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.casodeuso;

import com.mynor.gestor.congresos.app.excepcion.UsuarioInvalidoException;
import com.mynor.gestor.congresos.app.modelo.dominio.Usuario;
import com.mynor.gestor.congresos.app.modelo.fabricacionpura.CredencialesLogin;

/**
 *
 * @author mynordma
 */
public class Login {

    public Usuario loguear(CredencialesLogin credenciales) throws UsuarioInvalidoException {
        // lanzar exception si el usuario no coincide en la base de datos
        return new Usuario();
    }
    
}
