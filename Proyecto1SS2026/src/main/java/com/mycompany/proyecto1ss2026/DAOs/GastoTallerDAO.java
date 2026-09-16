/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.Request.GastoTaller;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author milton
 */
public class GastoTallerDAO {
    
    private final String AGREGAR_REGISTRO_GASTO = "INSERT INTO gasto_taller(monto_mano_obra, monto_repuestos, fecha_mantenimiento, bus) VALUES (?,?,?,?)";
    
    public void agregarRegistroGasto(GastoTaller request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement insert = connection.prepareStatement(AGREGAR_REGISTRO_GASTO);
            insert.setDouble(1, request.getMontoManoObra());
            insert.setDouble(2, request.getMontoRepuestos());
            Date date = Date.valueOf(request.getFechaMantenimiento());
            insert.setDate(3, date);
            insert.setString(4, request.getBus());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar un registro de gasto de taller " + e.getMessage());
        }
    }
    
}
