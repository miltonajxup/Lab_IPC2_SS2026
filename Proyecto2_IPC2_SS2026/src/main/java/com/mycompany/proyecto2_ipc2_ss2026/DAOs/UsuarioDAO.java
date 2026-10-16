/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.RolUsuario;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.UsuarioCompleto;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.UsuarioDB;
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
    private final String GET_USUARIO_POR_ID = "SELECT * FROM usuario WHERE dpi = ?";
    private final String GET_USUARIO_POR_NOMBRE = "SELECT * FROM usuario WHERE nombre = ?";
    
    public void agregarUsuario(Connection connection, UsuarioRequest request) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(AGREGAR_USUARIO);
        ps.setString(1, request.getDpi());
        ps.setString(2, request.getNombre());
        ps.setString(3, request.getRol().name());
        ps.executeUpdate();
    }
    
    public boolean existeUsuarioPorDpi(String dpiUsuario) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_USUARIO_POR_ID)) {
            ps.setString(1, dpiUsuario);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar a un usuario por dpi " + e.getMessage());
        }
    }
    
    public boolean existeUsuarioPorNombre(String nombreUsuario) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_USUARIO_POR_NOMBRE)) {
            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar a un usuario por nombre " + e.getMessage());
        }
    }
    
    public UsuarioDB usuarioPorId(String idUsuario) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_USUARIO_POR_ID)) {
            ps.setString(1, idUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return armarUsuario(rs);
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar un usuario por dpi: " + e.getMessage());
        }
        return null;
    }
    
    public UsuarioDB usuarioPorNombre(String nombre) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_USUARIO_POR_NOMBRE)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return armarUsuario(rs);
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar un usuario por nombre: " + e.getMessage());
        }
        return null;
    }
    
    private UsuarioDB armarUsuario(ResultSet rs) throws SQLException {
        return new UsuarioDB( 
                rs.getString("dpi"), 
                rs.getString("nombre"), 
                RolUsuario.valueOf(rs.getString("rol")));
    }
    
    public UsuarioCompleto usuarioCompletoPorNombre(String nombre) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_USUARIO_POR_NOMBRE)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new UsuarioCompleto(
                            rs.getString("dpi"), 
                            rs.getString("nombre"), 
                            RolUsuario.valueOf(rs.getString("rol")), 
                            rs.getString("contrasenia"));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar un usuario completo: " + e.getMessage());
        }
        return null;
    }
    
}
