/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author milton
 */
public class LoginDAO {
    
    private final String ACTUALIZAR_CONSTRASEÑA = "UPDATE usuario SET contrasenia = ?  WHERE dpi = ?";
    private final String AGREGAR_GUARADO_CONTRASEÑA = "INSERT INTO contrasenias (usuario_dpi, contrasenia) VALUES (?,?)";
    private final String GET_CONTRASEÑAS_ANTERIORES = "SELECT * FROM contrasenias WHERE usuario_dpi = ?";
    
    public void modificarContraseña(String contraseña, String idUsuario) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection()) {
            
            connection.setAutoCommit(false);
            try {
                
                try (PreparedStatement psCambio = connection.prepareStatement(ACTUALIZAR_CONSTRASEÑA);
                        PreparedStatement psContra = connection.prepareStatement(AGREGAR_GUARADO_CONTRASEÑA)) {
                    psCambio.setString(1, contraseña);
                    psCambio.setString(2, idUsuario);
                    psCambio.executeUpdate();
                    
                    psContra.setString(1, idUsuario);
                    psContra.setString(2, contraseña);
                    psContra.executeUpdate();
                }
                
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new AccesoALaDataException("Error al hacer un rollback al modificar una contraseña: " + ex.getMessage() + " \nOrigen: " + e.getMessage());
                }
                throw new AccesoALaDataException("Error al actualizar la contraseña: " + e.getMessage());
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new AccesoALaDataException("Error al devolver el auto commit al modificar una contraseña: " + e.getMessage());
                }
            }
            
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error de conexion al modificar la contraseña: " + e.getMessage());
        }
        
    }
    
    public List<String> getContraseñasGuardas(String idUsuario) throws AccesoALaDataException {
        List<String> contraseñas = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_CONTRASEÑAS_ANTERIORES)) {
            ps.setString(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    contraseñas.add(rs.getString("contrasenia"));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar las contraseñas anteriores: " + e.getMessage());
        }
        return contraseñas;
    }
    
}
