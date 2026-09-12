/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Constantes.EstadoViajePrivado;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ViajeDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.ViajePrivadoRequest;
import com.mycompany.proyecto1ss2026.Modelos.Request.ViajePublicoRequest;
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
public class ViajeDAO {
    
    private final String GENERAR_VIAJE = "INSERT INTO viaje (chofer, bus) VALUES (?,?)";
    private final String GET_ULTIMO_VIAJE = "SELECT id FROM viaje ORDER BY id DESC LIMIT 1";
    private final String AGREGAR_VIAJE_PUBLICO = "INSERT INTO viaje_publico (id_viaje, fecha_salida, horario) VALUES (?,?,?)";
    private final String AGREGAR_VIAJE_PRIVADO = "INSERT INTO viaje_publico (id_viaje, cantidad_pasajeros, origen, destino, distancia_aproximada, "
            + "hora_salida, hora_aprox_llegada, fecha_salida, fecha_llegada, costo, usuario_solicitante) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private final String MODIFCAR_ESTADO_VIAJE_PRIVADO = "UPDATE viaje_privado SET estado_viaje = ? WHERE id_viaje = ?";
    private final String ELIMINAR_VIAJE = "DELETE FROM viaje WHERE id = ?";
    private final String BUSCAR_VIAJE_POR_CHOFER = "SELECT * FROM viaje WHERE chofer = ?";
    private final String GET_VIAJES_SIN_TERMINIAR = "SELECT * FROM viaje_ejecucion AS viaej RIGHT JOIN viaje AS via ON viaej.viaje_id = via.id WHERE hora_salida IS NULL";
    
    public void agregarViajePublico(ViajePublicoRequest request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            connection.setAutoCommit(false);
            int idViaje = crearViaje(connection, request.getChofer(), request.getBus());
            PreparedStatement insert = connection.prepareStatement(AGREGAR_VIAJE_PUBLICO);
            insert.setInt(1, idViaje);
            insert.setString(2, request.getFechaSalida());
            insert.setInt(3, request.getHorario());
            insert.executeUpdate();
            
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new AccesoALaDataException("Error al hacer rollback en viaje pubico " + ex.getMessage());
            }
            throw new AccesoALaDataException("Error al agregar un viaje publico " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new AccesoALaDataException("Error al devolver el auto commit " + ex.getMessage());
            }
        }
    }
    
    public void agregarViajePrivado(ViajePrivadoRequest request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement insert = connection.prepareStatement(AGREGAR_VIAJE_PRIVADO);
            int idViaje = crearViaje(connection, request.getChofer(), request.getBus());
            insert.setInt(1, idViaje);
            insert.setInt(2, request.getCantidadPasajeros());
            insert.setString(3, request.getOrigen());
            insert.setString(4, request.getDestino());
            insert.setInt(5, request.getDistanciaAProximada());
            insert.setString(6, request.getHoraSalida());
            insert.setString(7, request.getHoraLlegada());
            insert.setString(8, request.getFechaSalida());
            insert.setString(9, request.getFechaLlegada());
            insert.setDouble(10, request.getCosto());
            insert.setString(11, request.getUsuarioSolicitante());
            insert.executeUpdate();
            
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new AccesoALaDataException("Error al hacer rollback en viaje privado " + ex.getMessage());
            }
            throw new AccesoALaDataException("Error al agregar un viaje privado " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new AccesoALaDataException("Error al devolver el auto commit de viaje privado " + e.getMessage());
            }
        }
    }
    
    private int crearViaje(Connection connection, String chofer, String bus) throws SQLException {
        PreparedStatement crearViaje = connection.prepareStatement(GENERAR_VIAJE);
        crearViaje.setString(1, chofer);
        crearViaje.setString(2, bus);
        crearViaje.executeUpdate();
        PreparedStatement idViaje = connection.prepareStatement(GET_ULTIMO_VIAJE);
        ResultSet rs = idViaje.executeQuery();
        return rs.getInt("id");
    }
    
    public void modificarEstadoViaje(EstadoViajePrivado estado, int idViaje) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement update = connection.prepareStatement(MODIFCAR_ESTADO_VIAJE_PRIVADO);
            update.setString(1, estado.name());
            update.setInt(2, idViaje);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar el estado de un viaje " + e.getMessage());
        }
    }
    
    public void eliminarViaje(int idViaje) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement delete = connection.prepareStatement(ELIMINAR_VIAJE);
            delete.setInt(1, idViaje);
            delete.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al eliminar un viaje " + e.getMessage());   
        }
    }
    
    public ViajeDB getViajePorChofer(String chofer) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(BUSCAR_VIAJE_POR_CHOFER);
            select.setString(1, chofer);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                return armarViaje(rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar viaje por chofer " + e.getMessage());
        }
        return null;
    }
    
    public List<ViajeDB> getViajesSinTerminra() throws AccesoALaDataException {
        List<ViajeDB> viajes = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(GET_VIAJES_SIN_TERMINIAR);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                viajes.add(armarViaje(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los viajes sin terminar " + e.getMessage());
        }
        return viajes;
    }
    
    public ViajeDB armarViaje(ResultSet rs) throws SQLException {
        return new ViajeDB(rs.getInt("id"), rs.getString("chofer"), rs.getString("bus"));
    }
     
}
