/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.Request.Sucursal;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.SucursalDB;
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
public class SucursalDAO {
    
    private final String AGREGAR_SUCURSAL = "INSERT INTO sucursal(codigo_sucursal, nombre, ciudad) VALUES (?,?,?)";
    private final String EDITAR_SUCURSAL = "UPDATE sucursal set nombre = ?, ciudad = ? WHERE codigo_sucursal = ?";
    private final String BUSCAR_SUCURSAL_ID = "SELECT * FROM sucursal WHERE codigo_sucursal = ?";
    private final String BUSCAR_SUCURSAL_VALORES = "SELECT * FROM sucursal WHERE nombre = ? AND ciudad = ?";
    private final String BUSCAR_SUCURSAL_POR_ID = "SELECT * FROM sucursal WHERE codigo_sucursal = ?";
    private final String TODAS_SUCURSALES = "SELECT * FROM sucursal";
    
    public void agregarSucursal(Sucursal request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement insert = connection.prepareStatement(AGREGAR_SUCURSAL);
            insert.setString(1, request.getCodigo());
            insert.setString(2, request.getNombre());
            insert.setString(3, request.getCiudad());
            
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar una sucursal " + e.getMessage());
        }
    } 
    
    public void editarSucursal(Sucursal request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement update = connection.prepareStatement(EDITAR_SUCURSAL);
            update.setString(1, request.getNombre());
            update.setString(2, request.getCiudad());
            update.setString(3, request.getCodigo());
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al actualizar la sucursal " + request.getCodigo() + " " + e.getMessage());
        }
    }
    
    public boolean existeSucursal(String codigoSucursal) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select1 = connection.prepareStatement(BUSCAR_SUCURSAL_ID);
            select1.setString(1, codigoSucursal);
            ResultSet rs = select1.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar sucural por ID " + e.getMessage());
        }
    }
    
    public boolean existeSucursal(Sucursal request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select1 = connection.prepareStatement(BUSCAR_SUCURSAL_ID);
            select1.setString(1, request.getCodigo());
            ResultSet rs1 = select1.executeQuery();
            if (rs1.next()) {
                return true;
            }
            return existeSucursalAtributos(request);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar la sucursal " + request.getCodigo() + " " + e.getMessage());
        }
    }
    
    public boolean existeSucursalAtributos(Sucursal request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(BUSCAR_SUCURSAL_VALORES);
            select.setString(1, request.getNombre());
            select.setString(2, request.getCiudad());
            ResultSet rs = select.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar la sucursal " + request.getNombre() + " en la ciudad " + request.getCiudad());
        }
    }
    
    public SucursalDB buscarSucursal(String codigoSucursal) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(BUSCAR_SUCURSAL_POR_ID);
            select.setString(1, codigoSucursal);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                return armarSucursal(rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar la sucursal " + codigoSucursal + " " + e.getMessage());
        }
        return null;
    }
    
    public List<SucursalDB> todasLasSucursales() throws AccesoALaDataException {
        List<SucursalDB> sucursales = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(TODAS_SUCURSALES);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                sucursales.add(armarSucursal(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar todas las sucursales " + e.getMessage());
        }
        return sucursales;
    }
    
    private SucursalDB armarSucursal(ResultSet rs) throws SQLException {
        return new SucursalDB(
                rs.getString("codigo_sucursal"), 
                rs.getString("nombre"), 
                rs.getString("ciudad"));
    }
    
}
