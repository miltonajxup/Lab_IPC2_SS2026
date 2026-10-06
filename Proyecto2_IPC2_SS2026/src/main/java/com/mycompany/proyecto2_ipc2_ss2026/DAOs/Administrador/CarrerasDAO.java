/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.CarreraDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.CarreraRequest;
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
public class CarrerasDAO {
    
    private final String EXISTE_CARRERA = "SELECT * FROM carrera WHERE nombre = ? AND nivel_id = ?";
    private final String CREAR_CARRERA = "INSERT INTO carrera (codigo, nombre, grado_id) VALUES (?,?,?)";
    private final String GET_CARRERAS = "SELECT * FROM carrera";
    private final String GET_CARRERA_POR_ID = "SELECT * FROM carreara WHERE codigo = ?";
    private final String MODIFICAR_ESTADO_CARRERA = "UPDATE carrera SET estado = ? WHERE codigo = ?";
    
    public boolean existeCarrera(String nombre, int nivelId) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(EXISTE_CARRERA);
            ps.setString(1, nombre);
            ps.setInt(2, nivelId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al revisar si existe una carrera " + e.getMessage());
        }
    }
    
    public void crearCarrera(CarreraRequest request) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(CREAR_CARRERA);
            ps.setString(1, request.getCodigo());
            ps.setString(2, request.getNombre());
            ps.setInt(3, request.getGradoId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar una carrera " + e.getMessage());
        }
    }
    
    public List<CarreraDB> getCarreras() throws AccesoALaDataException {
        List<CarreraDB> carreras = new ArrayList<>();
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_CARRERAS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                carreras.add(armarCarrera(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar las carreras " + e.getMessage());
        }
        return carreras;
    }
    
    public CarreraDB getCarreraPorId(String codigo) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_CARRERA_POR_ID);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return armarCarrera(rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar una carrera por id: " + e.getMessage());
        }
        return null;
    }
    
    public CarreraDB modificarEstadoCarrera(boolean estado, String codigo) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(MODIFICAR_ESTADO_CARRERA);
            ps.setBoolean(1, estado);
            ps.setString(2, codigo);
            ps.executeUpdate();
            
            return getCarreraPorId(codigo);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar el estado de una carrera " + e.getMessage());
        }
    }
    
    private CarreraDB armarCarrera(ResultSet rs) throws SQLException {
        return new CarreraDB(
                rs.getString("codigo"), 
                rs.getString("nombre"), 
                rs.getBoolean("estado"), 
                rs.getInt("grado_id"));
    }
    
}
