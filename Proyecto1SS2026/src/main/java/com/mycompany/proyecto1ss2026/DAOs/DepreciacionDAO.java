/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.DepreciacionDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.DepreciacionBusRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author milton
 */
public class DepreciacionDAO {
    
    private final String AGREGAR_MONTO_DEPRECIACION = "INSERT INTO depreciacion (monto_depreciacion) VALUES (?)";
    private final String GET_ULTIMO_MONTO = "SELECT * FROM depreciacion ORDER BY id DESC LIMIT 1";
    private final String AGREGAR_REGISTRO_DEPRECIACION = "INSERT INTO depreciacion_bus (fecha_registro, kilometros_recorridos, bus, depreciacion_id, monto_depreciado) VALUES (?,?,?,?,?)";
    
    public void agregarMontoDepreciacion(double monto) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement insert = connection.prepareStatement(AGREGAR_MONTO_DEPRECIACION);
            insert.setDouble(1, monto);
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar un registro de monto depreciacion" + e.getMessage());
        }
    }
    
    public DepreciacionDB ultimoValorDepreciacion(Connection connection) throws AccesoALaDataException {
        try {
            PreparedStatement monto = connection.prepareStatement(GET_ULTIMO_MONTO);
            ResultSet rs = monto.executeQuery();
            if (rs.next()) {
                return new DepreciacionDB(rs.getInt("id"), rs.getDouble("monto_depreciacion"));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al obtener monto de depreciacion " + e.getMessage());
        }
        return null;
    }
    
    public void agregarRegistroDepreciacion(DepreciacionBusRequest request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement insert = connection.prepareStatement(AGREGAR_REGISTRO_DEPRECIACION);
            insert.setString(1, request.getFechaRegistro());
            insert.setInt(2, request.getKilometrosRecorridos());
            insert.setString(3, request.getBus());
            insert.setInt(4, request.getDepreciacionId());
            insert.setDouble(5, request.getMontoDepreciado());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar registro depreciacion bus " + e.getMessage());
        }
    }
    
}
