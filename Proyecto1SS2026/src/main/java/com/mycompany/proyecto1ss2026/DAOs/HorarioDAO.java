/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.HorarioDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.Horario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author milton
 */
public class HorarioDAO {
    
    private final String AGREGAR_HORARIO = "INSERT INTO horario_ruta (hora_salida, hora_aprox_llegada, ruta) VALUES (?,?,?)";
    private final String GET_HORARIO_ID = "SELECT * FROM horario_ruta WHERE id = ?";
    private final String GET_HORARIO_VALORES = "SELECT * FROM horario_ruta WHERE hora_salida = ? AND hora_aprox_llegada = ? AND ruta = ?"; // agregar que la ruta tambien este activa
    //SELECT hora.* FROM horario_ruta AS hora JOIN ruta AS rut ON rut.id = hora.ruta WHERE hora.hora_salida = ? AND hora.hora_aprox_llegada AND hora.ruta = ? AND ruta.ruta_habilitada = TRUE
    private final String GET_HORARIOS_RUTA = "SELECT * FROM horario_ruta WHERE ruta = ?";
    private final String GET_HORARIOS_SUCURSALES = "SELECT hor.* FROM horario_ruta AS hor JOIN ruta AS rut ON hor.ruta = rut.id WHERE rut.sucursal_origen = ? AND rut.sucursal_destino = ?";
    
    public void agregarHorario(Horario request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement insert = connection.prepareStatement(AGREGAR_HORARIO);
            Time timeSalida = Time.valueOf(request.getHoraSalida());
            insert.setTime(1, timeSalida);
            Time timeLlegada = Time.valueOf(request.getHoraLlegada());
            insert.setTime(2, timeLlegada);
            insert.setString(3, request.getRuta());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar un Horario " + e.getMessage());
        }
    }
    
    public boolean existeHorario(String ruta) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(GET_HORARIO_ID);
            select.setString(1, ruta);
            ResultSet rs = select.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los horarios de ruta " + e.getMessage());
        }
    }
    
    public boolean existeHorario(Horario request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_HORARIO_VALORES);
            Time timeSalida = Time.valueOf(request.getHoraSalida());
            select.setTime(1, timeSalida);
            Time timeLlegada = Time.valueOf(request.getHoraLlegada());
            select.setTime(2, timeLlegada);
            select.setString(3, request.getRuta());
            ResultSet rs = select.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar horario " + e.getMessage());
        }
    }
    
    public List<HorarioDB> getHorariosRuta(String ruta) throws AccesoALaDataException {
        List<HorarioDB> horarios = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_HORARIOS_RUTA);
            select.setString(1, ruta);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                horarios.add(armarHorario(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los horarios de ruta " + e.getMessage());
        }
        return horarios;
    }
    
    public List<HorarioDB> getHorariosSucursales(String sucursalOrigen, String sucursalDestino) throws AccesoALaDataException {
        List<HorarioDB> horarios = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_HORARIOS_SUCURSALES);
            select.setString(1, sucursalOrigen);
            select.setString(2, sucursalDestino);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                horarios.add(armarHorario(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los horarios por sucursales " + e.getMessage());
        }
        return horarios;
    }
    
    private HorarioDB armarHorario(ResultSet rs) throws SQLException {
        return new HorarioDB(
                rs.getInt("id"), 
                rs.getString("hora_salida"), 
                rs.getString("hora_aprox_llegada"), 
                rs.getInt("ruta"));
    }
    
}
