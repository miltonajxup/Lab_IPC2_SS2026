/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.practica1ss2026;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author milton
 */
public class DBConnection {
    
    private static final String IP = "localhost";
    private static final String PUERTO = "3306";
    private static final String SCHEMA = "Practica1_SS2026";
    private static final String USER_NAME = "Milton";
    private static final String PASSWORD = "1234";
    private static final String URL = "jdbc:mysql://" + IP + ":" + PUERTO + "/" + SCHEMA;
    
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error al conectarse a la base de datos " + e.getMessage());
        }
        return null;
    }
    
}
