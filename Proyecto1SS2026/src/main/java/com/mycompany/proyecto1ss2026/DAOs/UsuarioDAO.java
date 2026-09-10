/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Constantes.RolUsuario;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.UsuarioRequest;
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
public class UsuarioDAO {
    
    private final String AGREGAR_USUARIO = "INSERT INTO usuario (dpi, nombre, nit, telefono, direccion, credito_disponible, rol) VALUES (?,?,?,?,?,?,?)";
    private final String CREAR_ADMIN_SUCURSAL = "INSERT INTO admin_sucursal (dpi, sucursal) VALUES (?,?)";
    private final String EDITAR_DATOS_USUARIO = "UPDATE usuario SET nombre = ?, telefono = ?, direccion = ? WHERE dpi = ?";
    private final String EDITAR_SUCURSAL = "UPDATE admin_sucursal SET sucursal = ?";
    private final String AGREGAR_CREDITOS = "UPDATE usuario SET credito_disponible = ? WHERE dpi = ?";
    private final String MODIFICAR_ESTADO = "UPDATE usuario SET estado = ? WHERE dpi = ?";
    private final String EXISTE_USUARIO = "SELECT * FROM usuario WHERE dpi = ?";
    private final String GET_USUARIOS = "SELECT * FROM usuario WHERE rol = ?";
    private final String GET_ADMINS_SUCURSALES = "SELECT usu.*, adm.sucursal FROM usuario AS usu JOIN admin_sucursal AS adm ON usu.dpi = adm.dpi WHERE adm.sucursal = ?";
    private final String GET_USUARIO_POR_ID = "SELECT * FROM usuario WHERE id = ?";
    
    public void agregarUsuario(UsuarioRequest request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement insert = connection.prepareStatement(AGREGAR_USUARIO);
            insert.setString(1, request.getDpi());
            insert.setString(2, request.getNombre());
            insert.setString(3, request.getNit());
            insert.setString(4, request.getTelefono());
            insert.setString(5, request.getDireccion());
            insert.setDouble(6, request.getCreditoDisponible());
            insert.setString(7, request.getRol().name());
            insert.executeUpdate();
            if (request.getRol() == RolUsuario.ADMINISTRADOR_SUCURSAL) {
                crearAdminSucursal(connection, request);
            }
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new AccesoALaDataException("Error al hacer rollback en agregar usuario " + ex.getMessage());
            }
            throw new AccesoALaDataException("Error al agregar un usuario " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new AccesoALaDataException("Error al devolver el auto commit de usuario " + e.getMessage());
            }
        }
    }
    
    public void crearAdminSucursal(Connection connection, UsuarioRequest request) throws SQLException {
        PreparedStatement insert = connection.prepareStatement(CREAR_ADMIN_SUCURSAL);
        insert.setString(1, request.getDpi());
        insert.setString(2, request.getSucursal());
        insert.executeUpdate();
    }
    
    public void editarUsuario(UsuarioRequest request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement update = connection.prepareStatement(EDITAR_DATOS_USUARIO);
            update.setString(1, request.getNombre());
            update.setString(2, request.getTelefono());
            update.setString(3, request.getDpi());
            update.executeUpdate();
            if (request.getRol() == RolUsuario.ADMINISTRADOR_SUCURSAL) {
                editarSucursal(connection, request);
            }
            
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new AccesoALaDataException("Error al hacer rolback al editar un usuario " + ex.getMessage());
            }
            throw new AccesoALaDataException("Error al editar un usuario " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new AccesoALaDataException("Error al devolver el autocommit en editar usuario " + e.getMessage());
            }
        }
    }
    
    private void editarSucursal(Connection connection, UsuarioRequest request) throws SQLException {
        PreparedStatement update = connection.prepareStatement(EDITAR_SUCURSAL);
        update.setString(1, request.getSucursal());
        update.setString(2, request.getDpi());
        update.executeUpdate();
    }
    
    public void agregarCreditos(double creditosActuales, String dpi) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement update = connection.prepareStatement(AGREGAR_CREDITOS);
            update.setDouble(1, creditosActuales);
            update.setString(2, dpi);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar creditos al usuario " + dpi + " " + e.getMessage());
        }
    }
    
    public void modificarEstado(boolean estado, String dpi) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement update = connection.prepareStatement(MODIFICAR_ESTADO);
            update.setBoolean(1, estado);
            update.setString(2, dpi);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar el estado de " + dpi + " " + e.getMessage());
        }
    }
    
    public boolean existeDpiUsuario(String dpiUsuario) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(EXISTE_USUARIO);
            select.setString(1, dpiUsuario);
            ResultSet rs = select.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al traer todos los clientes " + e.getMessage());
        }
    }
    
    public List<UsuarioDB> getClientes() throws AccesoALaDataException {
        List<UsuarioDB> usuarios = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_USUARIOS);
            select.setString(1, RolUsuario.CLIENTE.name());
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                usuarios.add(armarUsuario(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al traer todos los clientes " + e.getMessage());
        }
        return usuarios;
    }
    
    public List<UsuarioDB> getAdministradoresSucursal(String codigoSucursal) throws AccesoALaDataException {
        List<UsuarioDB> usuarios = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_ADMINS_SUCURSALES);
            select.setString(1, codigoSucursal);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                UsuarioDB usuario = armarUsuario(rs);
                usuario.setSucursal(codigoSucursal);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al traer todos los administradores de la sucursal " + codigoSucursal + " " + e.getMessage());
        }
        return usuarios;
    }
    
    public List<UsuarioDB> getAdiministradores() throws AccesoALaDataException {
        List<UsuarioDB> usuarios = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_USUARIOS);
            select.setString(1, RolUsuario.ADMINISTRADOR.name());
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                usuarios.add(armarUsuario(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al traer todos los usuarios " + e.getMessage());
        }
        return usuarios;
    }
    
    public UsuarioDB getUsuarioPorDpi(String dpi) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_USUARIO_POR_ID);
            select.setString(1, dpi);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                return armarUsuario(rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al traer usuario por dpi " + e.getMessage());
        }
        return null;
    }
    
    private UsuarioDB armarUsuario(ResultSet rs) throws SQLException {
        return new UsuarioDB(rs.getString("dpi"), 
                rs.getString("nombre"), 
                rs.getString("nit"), 
                rs.getString("telefono"), 
                rs.getString("direccion"), 
                rs.getDouble("credito_disponible"), 
                rs.getBoolean("estado"));
    }
    
}
