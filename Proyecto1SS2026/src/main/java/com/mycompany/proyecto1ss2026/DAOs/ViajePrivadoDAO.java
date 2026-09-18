/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Constantes.EstadoViajePrivado;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ViajePrivadoDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.PropuestaViaje;
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
public class ViajePrivadoDAO {
    
    
    private final String AGREGAR_PROPUESTA = 
            """
            INSERT INTO propuesta_viaje_privado (
                cantidad_pasajeros, 
                origen, 
                destino, 
                distancia_aproximada, 
                hora_salida, 
                hora_aprox_llegada, 
                fecha_salida, 
                costo, 
                usuario_solicitante) VALUES (?,?,?,?,?,?,?,?,?)""";
    private final String BUSCAR_PROPUESTAS_ID = "SELECT * FROM propuesta_viaje_privado WHERE id = ?";
    private final String BUSCAR_PROPUESTAS_NO_ACEPTADAS = "SELECT * FROM propuesta_viaje_privado WHERE estado = FALSE";
    private final String ELIMINAR_PROPUESTA = "DELETE FROM propuesta_viaje_privado WHERE id = ?";
    private final String BUSCAR_VIAJES_PRIVADOS_EN_REVISION = 
            """
            SELECT viapriv.*, via.chofer, via.bus 
            FROM viaje_privado AS viapriv 
            JOIN viaje AS via ON viapriv.id_viaje = via.id 
            WHERE estado_viaje = EN_REVISION""";
    private final String BUSCAR_VIAJE_PRIVADO_USUARIO = 
            """
            SELECT viapriv.*, via.chofer, via.bus 
            FROM viaje_privado AS viapriv 
            JOIN viaje AS via ON viapriv.id_viaje = via.id 
            WHERE usuario = ?""";
    
    private final String POSIBLES_VIAJES_PRIVADOS_ELIMINAR = 
            """
            SELECT viapriv.*, via.chofer, via.bus 
            FROM viaje_privado AS viapriv 
            JOIN viaje AS via ON viapriv.id_viaje = via.id 
            WHERE estado_viaje != ?""";
    
    public void agregarPropuestaViaje(PropuestaViaje propuesta) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement insert = connection.prepareStatement(AGREGAR_PROPUESTA);
            insert.setInt(1, propuesta.getCantidadPasajeros());
            insert.setString(2, propuesta.getOrigen());
            insert.setString(3, propuesta.getDestino());
            insert.setInt(4, propuesta.getDistanciaAProximada());
            insert.setString(5, propuesta.getHoraSalida());
            insert.setString(6, propuesta.getHoraLlegada());
            insert.setString(7, propuesta.getFechaSalida());
            insert.setDouble(8, propuesta.getCosto());
            insert.setString(9, propuesta.getUsuarioSolicitante());
            
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar una propuesta de viaje privado " + e.getMessage());
        }
    }
    
    public PropuestaViaje buscarPropuestaPorId(String idViaje) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(BUSCAR_PROPUESTAS_ID);
            ps.setString(1, idViaje);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return armarPropuesta(rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar una propuesta por id " + e.getMessage());
        }
        return null;
    }
    
    public List<PropuestaViaje> getPropuestas() throws AccesoALaDataException {
        List<PropuestaViaje> propuestas = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(BUSCAR_PROPUESTAS_NO_ACEPTADAS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                propuestas.add(armarPropuesta(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al cargar todas las prouestas " + e.getMessage());
        }
        return propuestas;
    }
    
    public PropuestaViaje getPropuestaPorId(String id) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(BUSCAR_PROPUESTAS_NO_ACEPTADAS);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return armarPropuesta(rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al cargar una propuesta por id " + e.getMessage());
        }
        return null;
    }
    
    public List<ViajePrivadoDB> getViajesPrivadosRevision() throws AccesoALaDataException {
        List<ViajePrivadoDB> viajes = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(BUSCAR_VIAJES_PRIVADOS_EN_REVISION);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                viajes.add(armarViajePrivado(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar viajes privados EN_REVISION " + e.getMessage());
        }
        return viajes;
    }
    
    public List<ViajePrivadoDB> getViajesPrivadosUsuarios(String dpiUsuario) throws AccesoALaDataException {
        List<ViajePrivadoDB> viajes = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(BUSCAR_VIAJE_PRIVADO_USUARIO);
            select.setString(1, dpiUsuario);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                viajes.add(armarViajePrivado(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar viajes privados de Usuario " + e.getMessage());
        }
        return viajes;
    }
    
    public List<ViajePrivadoDB> getPosiblesViajesPrivadosEliminar() throws AccesoALaDataException {
        List<ViajePrivadoDB> viajes = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(POSIBLES_VIAJES_PRIVADOS_ELIMINAR);
            select.setString(1, EstadoViajePrivado.PAGADO.name());
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                viajes.add(armarViajePrivado(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al traer lso viajes privados que se pueden eliminar " + e.getMessage());
        }
        return viajes;
    }
    
    private ViajePrivadoDB armarViajePrivado(ResultSet rs) throws SQLException {
        return new ViajePrivadoDB(
                rs.getInt("id_viaje"), 
                rs.getString("chofer"), 
                rs.getString("bus"), 
                rs.getInt("cantidad_pasajeros"), 
                rs.getString("origen"), 
                rs.getString("destino"), 
                rs.getInt("distancia_aproximada"), 
                rs.getString("hora_salida"), 
                rs.getString("hora_aprox_llegada"), 
                rs.getString("fecha_salida"), 
                rs.getString("fecha_llegada"), 
                rs.getDouble("costo"), 
                rs.getString("usuario_solicitante"), 
                EstadoViajePrivado.valueOf(rs.getString("estado_viaje")));
    }
    
    private PropuestaViaje armarPropuesta(ResultSet rs) throws SQLException {
        return new PropuestaViaje (
                rs.getInt("id"), 
                rs.getInt("cantidad_pasajeros"), 
                rs.getString("origen"), 
                rs.getString("destino"), 
                rs.getInt("distancia_aproximada"), 
                rs.getString("hora_salida"), 
                rs.getString("hora_aprox_llegada"), 
                rs.getString("fecha_salida"), 
                rs.getDouble("costo"), 
                rs.getString("usuario_solicitante"), 
                rs.getBoolean("estado"));
    }
    
}
