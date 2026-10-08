/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.UsuarioRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author milton
 */
public class UsuarioDAO {
    
    private final String AGREGAR_USUARIO = "INSERT INTO usuario (dpi, nombre, rol) VALUES (?,?,?)";
    private final String ACTUALIZAR_CONSTRASEÑA = "UPDATE usuario SET contrasenia = ?  WHERE dpi = ?";
    private final String GET_USUARIO_POR_ID = "SELECT * FROM usuario WHERE dpi = ?";
    private final String GET_USUARIO_POR_NOMBRE = "SELECT * FROM usuario WHERE nombre = ?";
    
    public void agregarUsuario(Connection connection, UsuarioRequest request) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(AGREGAR_USUARIO);
        ps.setString(1, request.getDpi());
        ps.setString(2, request.getNombre());
        ps.setString(3, request.getRol().name());
        ps.executeUpdate();
    }
    
    public void modificarContraseña(UsuarioRequest usuario) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(ACTUALIZAR_CONSTRASEÑA);
            ps.setString(1, usuario.getContrasenia());
            ps.setString(2, usuario.getDpi());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al actualizar la contraseña: " + e.getMessage());
        }
    }
    
    public boolean existeUsuarioPorDpi(String dpiUsuario) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_USUARIO_POR_ID);
            ps.setString(1, dpiUsuario);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar a un usuario por dpi " + e.getMessage());
        }
    }
    
    public boolean existeUsuarioPorNombre(String nombreUsuario) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_USUARIO_POR_NOMBRE);
            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar a un usuario por nombre " + e.getMessage());
        }
    }
    
}
