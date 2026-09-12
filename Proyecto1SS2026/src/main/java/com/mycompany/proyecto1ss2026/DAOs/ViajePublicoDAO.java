/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ViajePublicoDB;
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
public class ViajePublicoDAO {
    
    private final String BUSCAR_VIAJES_RUTA = """
                                              SELECT viapub.*, via.chofer, via.bus, 
                                              rut.sucursal_origen AS origen, rut.sucursal_destino AS destino, 
                                              hora.hora_salida, hora.hora_aprox_llegada 
                                              FROM viaje AS via 
                                              JOIN viaje_publico AS viapub ON via.id = viapub.viaje_id 
                                              JOIN horario_ruta AS hora ON viapub.horario = hora.id 
                                              JOIN ruta AS rut ON hora.ruta = rut.id 
                                              WHERE rut.ruta = ? AND via.fecha_salida > ?""";
    private final String POSIBLES_VIAJES_PUBLICOS_ELIMINAR = 
                                    """
                                    SELECT viapub.*, via.chofer, via.bus, 
                                    rut.sucursal_origen AS origen, rut.sucursal_destino AS destino, 
                                    hora.hora_salida, hora.hora_aprox_llegada 
                                    FROM viaje AS via 
                                    JOIN viaje_publico AS viapub ON via.id = viapub.viaje_id 
                                    JOIN horario_ruta AS hora ON viapub.horario = hora.id 
                                    JOIN ruta AS rut ON hora.ruta = rut.id 
                                    JOIN viaje_ejecucion AS viaej ON viapub.viaje_id = viaej.viaje_id 
                                    WHERE viaej.hora_salida IS NULL AND viaej.hora_llegada IS NULL""";
    private final String VIAJES_SIN_COMPLETAR = 
            """
            SELECT * FROM viaje_ejecucion AS viaej JOIN LEFT """;
    
    public List<ViajePublicoDB> getViajesPublicosRuta(int ruta, String fecha) throws AccesoALaDataException {
        List<ViajePublicoDB> viajes = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(BUSCAR_VIAJES_RUTA);
            select.setInt(1, ruta);
            select.setString(2, fecha);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                viajes.add(armarViajePublico(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los viajes de la ruta " + ruta + " " + e.getMessage());
        }
        return viajes;
    }
    
    public List<ViajePublicoDB> getPosiblesViajesPublicosEliminar() throws AccesoALaDataException {
        List<ViajePublicoDB> viajes = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(POSIBLES_VIAJES_PUBLICOS_ELIMINAR);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                viajes.add(armarViajePublico(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al traer los viajes publicos que se pueden eliminar " + e.getMessage());
        }
        return viajes;
    }
    
    private ViajePublicoDB armarViajePublico(ResultSet rs) throws SQLException {
        return new ViajePublicoDB(
                rs.getInt("id_viaje"), 
                rs.getString("chofer"), 
                rs.getString("bus"), 
                rs.getString("fecha_salida"), 
                rs.getInt("horario"), 
                rs.getString("origen"), 
                rs.getString("destino"), 
                rs.getString("hora_salida"), 
                rs.getString("hora_aprox_llegada"));
    }
    
}
