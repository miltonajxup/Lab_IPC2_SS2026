/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.DepreciacionDB;
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
    
    public DepreciacionDB ultimoValorDepreciacion() throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
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
    
}
