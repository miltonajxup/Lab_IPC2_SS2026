/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.BusDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.Bus;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author milton
 */
public class BusDAO {
    
    private final String AGREGAR_BUS = "INSERT INTO bus (numero_placa, marca, modelo, fecha_fabricacion, capacidad_pasajeros, kilometraje, sucursal_base, sucursal_actual) VALUES (?,?,?,?,?,?,?,?)";
    private final String MODIFICAR_BUS = "UPDATE bus SET marca = ?, modelo = ?, fecha_fabricacion = ?, capacidad_pasajeros = ?, sucursal_base = ? WHERE numero_placa = ?";
    private final String MODIFICAR_KILOMETRAJE_BUS = "UPDATE bus SET kilometraje = ? WHERE numero_placa = ?";
    private final String MODIFICAR_ESTADO_BUS = "UPDATE bus SET estado_operativo = ? WHERE numero_placa = ?";
    private final String ACTUALIZAR_SUCURSAL_BUS = "UPDATE bus SET sucursal_actual = ? WHERE numero_placa = ?";
    private final String GET_BUS_POR_ID = "SELECT * FROM bus WHERE numero_placa = ?";
    private final String GET_BUSES_SUCURSAL = "SELECT * FROM bus WHERE sucursal_base = ?";
    private final String GET_BUSES_SUCURSAL_ACTUAL = "SELECT * FROM bus WHERE sucursal_actual = ?";
    private final String TODOS_LOS_BUSES = "SELECT * FROM bus";
    
    public void agregarBus(Bus request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement insert = connection.prepareStatement(AGREGAR_BUS);
            insert.setString(1, request.getNumeroPlaca());
            
            insert.setString(2, request.getMarca());
            insert.setString(3, request.getModelo());
            Date fecha = Date.valueOf(request.getFechaFabricacion());
            insert.setDate(4, fecha);
            insert.setInt(5, request.getCapacidadPasajeros());
            insert.setInt(6, request.getKilometraje());
            insert.setString(7, request.getSucursalBase());
            insert.setString(8, request.getSucursalBase());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar un bus " + e.getMessage());
        }
    }
    
    public void modificarBus(Bus request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement update = connection.prepareStatement(MODIFICAR_BUS);
            
            update.setString(1, request.getMarca());
            update.setString(2, request.getModelo());
            Date fecha = Date.valueOf(request.getFechaFabricacion());
            update.setDate(3, fecha);
            update.setInt(4, request.getCapacidadPasajeros());
            update.setString(5, request.getSucursalBase());
            update.setString(6, request.getNumeroPlaca());
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar un bus " + e.getMessage());
        }
    }
    
    public void modificarKilometraje(int kilometrajeActual, String numeroPlaca) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement update = connection.prepareStatement(MODIFICAR_KILOMETRAJE_BUS);
            update.setInt(1, kilometrajeActual);
            update.setString(2, numeroPlaca);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar el kilometraje de bus " + e.getMessage());
        }
    }
    
    public void modificarEstadoBus(boolean estado, String numeroPlaca) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement update = connection.prepareStatement(MODIFICAR_ESTADO_BUS);
            update.setBoolean(1, estado);
            update.setString(2, numeroPlaca);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al actualizar estado de autobus " + e.getMessage());
        }
    }
    
    public void actualizarSucursalBus(Bus request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement update = connection.prepareStatement(ACTUALIZAR_SUCURSAL_BUS);
            update.setString(1, request.getSucursalActual());
            update.setString(2, request.getNumeroPlaca());
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al actualizar la sucursal de un bus " + e.getMessage());
        }
    }
    
    public boolean existeBus(String numeroPlaca) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_BUS_POR_ID);
            select.setString(1, numeroPlaca);
            ResultSet rs = select.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar bus por id " + e.getMessage());
        }
    }
    
    public BusDB getBusId(String numeroPlaca) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_BUS_POR_ID);
            select.setString(1, numeroPlaca);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                return armarBus(rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar bus por id " + e.getMessage());
        }
        return null;
    }
    
    public List<BusDB> getBusSucursal(String sucursal) throws AccesoALaDataException {
        List<BusDB> buses = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_BUSES_SUCURSAL);
            select.setString(1, sucursal);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                buses.add(armarBus(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar buses de sucursal " + e.getMessage());
        }
        return buses;
    }
    
    public List<BusDB> getBusSucursalActual(String sucursalActual) throws AccesoALaDataException {
        List<BusDB> buses = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_BUSES_SUCURSAL_ACTUAL);
            select.setString(1, sucursalActual);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                buses.add(armarBus(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar buses de sucursal " + e.getMessage());
        }
        return buses;
    }
    
    public List<BusDB> getTodosLosBuses() throws AccesoALaDataException {
        List<BusDB> buses = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(TODOS_LOS_BUSES);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                buses.add(armarBus(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al traer todos los buses " + e.getMessage());
        }
        return buses;
    }
    
    private BusDB armarBus(ResultSet rs) throws SQLException {
        return new BusDB(
                rs.getString("numero_placa"), 
                rs.getBytes("foto"), 
                rs.getString("marca"), 
                rs.getString("modelo"), 
                rs.getString("fecha_fabricacion"), 
                rs.getInt("capacidad_pasajeros"), 
                rs.getInt("kilometraje"), 
                rs.getBoolean("estado_operativo"), 
                rs.getString("sucursal_base"), 
                rs.getString("sucursal_actual"));
    }
    
}
