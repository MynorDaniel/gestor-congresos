/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mynor.gestor.congresos.app.basededatos;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author mynordma
 */
public class ConexionBD {
    
    private static ConexionBD instance;
    private Connection connection;
    private String url;
    private String usuario;
    private String clave;
    
    private ConexionBD(){
        obtenerCredenciales();
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, usuario, clave);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public Connection getConnection(){
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, usuario, clave);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
    
    public static ConexionBD getInstance(){
        if (instance == null) {
            instance = new ConexionBD();
        }
        return instance;
    }
    
    private void obtenerCredenciales(){
        Properties properties = new Properties();
        String rutaActual = System.getProperty("user.dir");
        String nombreArchivo = "config.properties";
        
        System.out.println("DEBUG AQUI.....");
        System.out.println(rutaActual + File.separator + nombreArchivo);
        
        try(FileReader fr = new FileReader(rutaActual + File.separator + nombreArchivo)){
            properties.load(fr);
            url = properties.getProperty("db.url");
            usuario = properties.getProperty("db.usuario");
            clave = properties.getProperty("db.clave");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}