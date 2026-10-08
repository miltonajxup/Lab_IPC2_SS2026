/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author milton
 */
public class CicloEscolar {
    
    private final String GET_AÑO_ESCOLAR_ACTIVO = "SELECT anio FROM ciclo_escolar WHERE estado = TRUE";
    
    public int getAñoActivo() throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_AÑO_ESCOLAR_ACTIVO);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("anio");
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar el año del ciclo escolar activo: " + e.getMessage());
        }
        return 0;
    }
    
}
