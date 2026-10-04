/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author milton
 */
public class DBConnectionSingleton {
    
    private final String IP = "localhost";
    private final String PUERTO = "3306";
    private final String SCHEMA = "Proyecto2_SS2026";
    private final String USER = "Milton";
    private final String PASSWORD = "1234";
    private final String URL = "jdbc:mysql://" + IP + ":" + PUERTO + "/" + SCHEMA;
    
    private static DBConnectionSingleton instancia;
    
    private Connection connection;
    
    private DBConnectionSingleton() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Ocurrio un error al conectarse con la base de datos " + e.getMessage());
        }
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    public static DBConnectionSingleton getInstancia() {
        if (instancia == null) {
            instancia = new DBConnectionSingleton();
        }
        return instancia;
    }
    
}
