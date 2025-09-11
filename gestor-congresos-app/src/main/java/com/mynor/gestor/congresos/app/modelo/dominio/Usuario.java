/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.modelo.dominio;

import com.mynor.gestor.congresos.app.modelo.fabricacionpura.RolSistema;

/**
 *
 * @author mynordma
 */
public class Usuario extends Entidad{
    
    private String id;
    private String clave;
    private String nombre;
    private String numero;
    private boolean activado;
    private String correo;
    private byte[] foto;
    private RolSistema rol;

    public RolSistema getRol() {
        return rol;
    }

    public void setRol(RolSistema rol) {
        this.rol = rol;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public boolean isActivado() {
        return activado;
    }

    public void setActivado(boolean activado) {
        this.activado = activado;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
    
    @Override
    public String[] getValores() {
        return new String[] {
            id,
            clave,
            nombre,
            numero,
            activado ? "1" : "0",
            "NULL", // foto
            correo,
            rol.name()
        };
    }
}
