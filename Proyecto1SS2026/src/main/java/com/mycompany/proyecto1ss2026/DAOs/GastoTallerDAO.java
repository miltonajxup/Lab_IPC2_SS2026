/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.Request.GastoTallerRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author milton
 */
public class GastoTallerDAO {
    
    private final String AGREGAR_REGISTRO_GASTO = "INSERT INTO gasto_taller(monto_mano_obra, monto_repuestos, fecha_mantenimiento, bus) VALUES (?,?,?,?)";
    
    public void agregarRegistroGasto(GastoTallerRequest request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement insert = connection.prepareStatement(AGREGAR_REGISTRO_GASTO);
            insert.setDouble(1, request.getMontoManoObra());
            insert.setDouble(2, request.getMontoRepuestos());
            insert.setString(3, request.getFechaMantenimiento());
            insert.setString(4, request.getBus());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar un registro de gasto de taller " + e.getMessage());
        }
    }
    
}
