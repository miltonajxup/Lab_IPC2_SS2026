/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Connection;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author milton
 */
public class DBConnectionSingleton {
    
    private static final String JNDI_NAME = "java:comp/env/jdbc/Proyecto2DBPool";
    
    private static DBConnectionSingleton instancia;
    
    private DataSource dataSource;
    
    private DBConnectionSingleton() {
        try {
            dataSource = (DataSource) new InitialContext().lookup(JNDI_NAME);
        } catch (NamingException e) {
            throw new IllegalStateException(
                    "No se pudo resolver el DataSource JNDI: " + JNDI_NAME, e);
        }
    }   
    
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
    
    public static DBConnectionSingleton getInstancia() {
        if (instancia == null) {
            instancia = new DBConnectionSingleton();
        }
        return instancia;
    }
    
    /*
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
    */
    
}
