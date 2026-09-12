/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.RutaDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.RutaRequest;
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
public class RutaDAO {
    
    private final String AGREGAR_RUTA = "INSERT INTO ruta (distancia_aproximada, precio_boleto, sucursal_registro, sucursal_origen, sucursal_destino) VALUES (?,?,?,?,?)";
    private final String MODIFICAR_RUTA = "UPDATE ruta SET distancia_aproximada = ? AND precio_boleto = ? WHERE id = ? ";
    private final String DESHABILITAR_RUTA = "UPDATE ruta SET ruta_habilitada = FALSE WHERE id = ?";
    private final String EXISTE_RUTA_ID = "SELECT * FROM ruta WHERE id = ? AND ruta_habilitada = TRUE";
    private final String EXISTE_RUTA = "SELECT * FROM ruta WHERE sucursal_origen = ? AND sucursal_destino = ? AND ruta_habilitada = TRUE";
    private final String GET_RUTAS_SUCURSAL_ORIGEN = "SELECT * FROM ruta WHERE ruta_habilitada = TRUE AND sucursal_origen = ?";
    private final String GET_RUTAS_SUCURSAL_DESTINO = "SELECT * FROM ruta WHERE ruta_habilitada = TRUE AND sucursal_destino = ?";
    private final String GET_RUTA_POR_ID = "SELECT * FROM ruta WHERE ruta_habilitada = TRUE AND id = ?"; //ver si se puede mostrar
    
    public void agregarRuta(RutaRequest request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement insert = connection.prepareStatement(AGREGAR_RUTA);
            insert.setInt(1, request.getDistanciaAproximada());
            insert.setDouble(2, request.getPrecioBoleto());
            insert.setString(3, request.getSucursalRegistro());
            insert.setString(4, request.getSucursalOrigen());
            insert.setString(5, request.getSucursalDestino());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar una ruta " + e.getMessage());
        }
    }
    
    public void modificarRuta(RutaRequest request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement update = connection.prepareStatement(MODIFICAR_RUTA);
            update.setInt(1, request.getDistanciaAproximada());
            update.setDouble(1, request.getPrecioBoleto());
            update.setString(1, request.getId());
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar una ruta " + e.getMessage());
        }
    }
    
    public void deshabilitarRuta(String idRuta) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement update = connection.prepareStatement(DESHABILITAR_RUTA);
            update.setString(1, idRuta);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al deshabilitar una ruta " + e.getMessage());
        }
    }
    
    public boolean existeRuta(String idRuta) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(EXISTE_RUTA_ID);
            select.setString(1, idRuta);
            ResultSet rs = select.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al revisar si una ruta existe " + e.getMessage());
        }
    }
    
    public boolean existeRuta(String sucursalOrigen, String sucursalDestino) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(EXISTE_RUTA);
            select.setString(1, sucursalOrigen);
            select.setString(2, sucursalDestino);
            ResultSet rs = select.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al revisar si una ruta existe " + e.getMessage());
        }
    } 
    
    public List<RutaDB> getRutasSucursalOrigen(String sucursalOrigen) throws AccesoALaDataException {
        List<RutaDB> rutas = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_RUTAS_SUCURSAL_ORIGEN);
            select.setString(1, sucursalOrigen);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                rutas.add(armarRuta(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al obtener las rutas con origen en la sucursal " + sucursalOrigen + e.getMessage());
        }
        return rutas;
    }
    
    public List<RutaDB> getRutasSucursalDestino(String sucursalDestino) throws AccesoALaDataException {
        List<RutaDB> rutas = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_RUTAS_SUCURSAL_DESTINO);
            select.setString(1, sucursalDestino);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                rutas.add(armarRuta(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al obtener las rutas con destino en la sucursal " + sucursalDestino + e.getMessage());
        }
        return rutas;
    }
    
    public RutaDB getRutasPorId(String idRuta) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_RUTA_POR_ID);
            select.setString(1, idRuta);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                return armarRuta(rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al obtener una ruta por su id " + e.getMessage());
        }
        return null;
    }
    
    private RutaDB armarRuta(ResultSet rs) throws SQLException {
        return new RutaDB(
                rs.getInt("id"), 
                rs.getInt("distancia_aproximada"), 
                rs.getDouble("precio_boleto"), 
                rs.getString("sucursal_origen"), 
                rs.getString("sucursal_destino"), 
                rs.getBoolean("ruta_habilitada"));
    }
    
}
