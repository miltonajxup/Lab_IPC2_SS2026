/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Constantes.EstadoViajePrivado;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ViajeDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.ViajePrivado;
import com.mycompany.proyecto1ss2026.Modelos.Request.ViajePublicoRequest;
import java.sql.Connection;
import java.sql.Date;
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
    private final String ACEPTAR_PROPUESTA = "UPDATE propuesta_viaje_privado SET estado = TRUE WHERE id = ?";
    private final String AGREGAR_VIAJE_PRIVADO = 
            """
            INSERT INTO viaje_privado (
                id_viaje, cantidad_pasajeros, 
                origen, destino, 
                distancia_aproximada, hora_salida, 
                hora_aprox_llegada, fecha_salida, 
                costo, usuario_solicitante, 
                sucursal_receptor) VALUES (?,?,?,?,?,?,?,?,?,?,?)""";
    private final String MODIFCAR_ESTADO_VIAJE_PRIVADO = "UPDATE viaje_privado SET estado_viaje = ? WHERE id_viaje = ?";
    private final String ELIMINAR_VIAJE = "DELETE FROM viaje WHERE id = ?";
    private final String BUSCAR_VIAJE_POR_ID = "SELECT * FROM viaje WHERE id = ?";
    private final String BUSCAR_VIAJE_POR_CHOFER = "SELECT * FROM viaje WHERE chofer = ?";
    private final String GET_VIAJES_SIN_TERMINIAR = 
            """
            SELECT via.*, viaej.hora_salida, rut.sucursal_destino 
            FROM viaje_ejecucion AS viaej RIGHT JOIN viaje AS via ON viaej.viaje_id = via.id 
            JOIN viaje_publico AS viap ON via.id = viap.id_viaje 
            JOIN horario_ruta AS hor ON viap.horario = hor.id 
            JOIN ruta AS rut ON hor.ruta = rut.id 
            WHERE viaej.hora_salida IS NULL OR viaej.hora_llegada IS NULL""";
    private final String GET_VIAJES_SIN_TERMINIAR_SUCURSAL_ORIGEN = 
            """
            SELECT via.*, viaej.hora_salida, rut.sucursal_destino 
            FROM viaje AS via LEFT JOIN viaje_ejecucion AS viaej ON via.id = viaej.viaje_id 
            JOIN viaje_publico AS viap ON via.id = viap.id_viaje 
            JOIN horario_ruta AS hor ON viap.horario = hor.id 
            JOIN ruta AS rut ON hor.ruta = rut.id 
            WHERE (viaej.hora_salida IS NULL OR viaej.hora_llegada IS NULL) AND rut.sucursal_origen = ?""";
    private final String GET_VIAJES_PRIVADOS_SIN_TERMINAR_SUCURSAL = 
            """
            SELECT via.*, viaej.hora_salida 
            FROM viaje AS via LEFT JOIN viaje_ejecucion AS viaej ON via.id = viaej.viaje_id 
            JOIN viaje_privado AS vpriv ON via.id = vpriv.id_viaje 
            WHERE (viaej.hora_salida IS NULL OR viaej.hora_llegada IS NULL) AND via.id = vpriv.id_viaje AND vpriv.sucursal_receptor = ?""";
    private final String GET_VIAJES_SIN_TERMINIAR_CHOFER = 
            """
            SELECT via.*, viaej.hora_salida, rut.sucursal_destino 
            FROM viaje AS via LEFT JOIN viaje_ejecucion AS viaej ON via.id = viaej.viaje_id 
            JOIN viaje_publico AS viap ON via.id = viap.id_viaje 
            JOIN horario_ruta AS hor ON viap.horario = hor.id 
            JOIN ruta AS rut ON hor.ruta = rut.id 
            WHERE (viaej.hora_salida IS NULL OR viaej.hora_llegada IS NULL) AND via.chofer = ?""";
    private final String GET_VIAJES_PRIVADOS_SIN_TERMINAR_CHOFER = 
            """
            SELECT via.*, viaej.hora_salida 
            FROM viaje AS via LEFT JOIN viaje_ejecucion AS viaej ON via.id = viaej.viaje_id 
            JOIN viaje_privado AS vpriv ON via.id = vpriv.id_viaje 
            WHERE (viaej.hora_salida IS NULL OR viaej.hora_llegada IS NULL) AND via.id = vpriv.id_viaje AND via.chofer = ?""";
    private final String GET_VIAJES_SIN_TERMINIAR_ID = 
            """
            SELECT via.*, viaej.hora_salida, rut.sucursal_destino 
            FROM viaje_ejecucion AS viaej 
            RIGHT JOIN viaje AS via ON viaej.viaje_id = via.id 
            JOIN viaje_publico AS viap ON via.id = viap.id_viaje 
            JOIN horario_ruta AS hor ON viap.horario = hor.id 
            JOIN ruta AS rut ON hor.ruta = rut.id 
            WHERE (viaej.hora_salida IS NULL OR viaej.hora_llegada IS NULL) AND via.id = ?""";
    private final String GET_VIAJE_PRIVADO_SIN_TERMINAR = 
            """
            SELECT via.*, viaej.hora_salida 
            FROM viaje AS via LEFT JOIN viaje_ejecucion AS viaej ON via.id = viaej.viaje_id 
            JOIN viaje_privado AS vpriv ON via.id = vpriv.id_viaje 
            WHERE (viaej.hora_salida IS NULL OR viaej.hora_llegada IS NULL) AND via.id = vpriv.id_viaje AND via.id = ?""";
    private final String GET_VIAJE_PUBLICO = "SELECT * FROM viaje_publico WHERE id_viaje = ?";
    private final String GET_PRECIO_VIAJE = 
            """
            SELECT rut.precio_boleto 
            FROM viaje AS via JOIN viaje_publico AS viap ON via.id = viap.id_viaje 
            JOIN horario_ruta AS hor ON viap.horario = hor.id 
            JOIN ruta AS rut ON hor.ruta = rut.id WHERE via.id = ?""";
    
    public void agregarViajePublico(ViajePublicoRequest request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            connection.setAutoCommit(false);
            int idViaje = crearViaje(connection, request.getChofer(), request.getBus());
            PreparedStatement insert = connection.prepareStatement(AGREGAR_VIAJE_PUBLICO);
            insert.setInt(1, idViaje);
            Date date = Date.valueOf(request.getFechaSalida());
            insert.setDate(2, date);
            insert.setString(3, request.getHorario());
            insert.executeUpdate();
            
            connection.commit();
        } catch (SQLException | AccesoALaDataException e) {
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
    
    public void agregarViajePrivado(ViajePrivado request, int idPropuesta) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            connection.setAutoCommit(false);
            
            PreparedStatement updatePropuesta = connection.prepareStatement(ACEPTAR_PROPUESTA);
            updatePropuesta.setInt(1, idPropuesta);
            updatePropuesta.executeUpdate();
            
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
            insert.setDouble(9, request.getCosto());
            insert.setString(10, request.getUsuarioSolicitante());
            insert.setString(11, request.getSucursal());
            insert.executeUpdate();
            
            connection.commit();
        } catch (SQLException | AccesoALaDataException e) {
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
    
    private int crearViaje(Connection connection, String chofer, String bus) throws SQLException, AccesoALaDataException {
        PreparedStatement crearViaje = connection.prepareStatement(GENERAR_VIAJE);
        crearViaje.setString(1, chofer);
        crearViaje.setString(2, bus);
        crearViaje.executeUpdate();
        PreparedStatement idViaje = connection.prepareStatement(GET_ULTIMO_VIAJE);
        ResultSet rs = idViaje.executeQuery();
        if (rs.next()) {
            return rs.getInt("id");
        }
        throw new AccesoALaDataException("Error al acceder al intentar acceder al ultimo id de viaje generado");
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
    
    public ViajeDB getViajePorId(String idViaje) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(BUSCAR_VIAJE_POR_ID);
            select.setString(1, idViaje);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                return armarViaje(rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar viaje por id " + e.getMessage());
        }
        return null;
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
    
    public List<ViajeDB> getViajesSinTerminar() throws AccesoALaDataException {
        List<ViajeDB> viajes = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(GET_VIAJES_SIN_TERMINIAR);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                viajes.add(armarViajeYEstado(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los viajes sin terminar " + e.getMessage());
        }
        return viajes;
    }
    
    public List<ViajeDB> getViajesSinTerminarSucursalOrigen(String sucursalOrigen) throws AccesoALaDataException {
        List<ViajeDB> viajes = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(GET_VIAJES_SIN_TERMINIAR_SUCURSAL_ORIGEN);
            select.setString(1, sucursalOrigen);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                viajes.add(armarViajeYEstado(rs));
            }
            
            PreparedStatement ps2 = connection.prepareStatement(GET_VIAJES_PRIVADOS_SIN_TERMINAR_SUCURSAL);
            ps2.setString(1, sucursalOrigen);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                viajes.add(armarViajeSinDestino(rs2));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los viajes sin terminar de la sucursal " + sucursalOrigen + ": " + e.getMessage());
        }
        return viajes;
    }
    
    public List<ViajeDB> getViajesSinTerminarChofer(String numeroLicencia) throws AccesoALaDataException {
        List<ViajeDB> viajes = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(GET_VIAJES_SIN_TERMINIAR_CHOFER);
            select.setString(1, numeroLicencia);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                viajes.add(armarViajeYEstado(rs));
            }
            
            PreparedStatement ps2 = connection.prepareStatement(GET_VIAJES_PRIVADOS_SIN_TERMINAR_CHOFER);
            ps2.setString(1, numeroLicencia);
            ResultSet rs2 = ps2.executeQuery();
            while (rs2.next()) {
                viajes.add(armarViajeSinDestino(rs2));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los viajes sin terminar del chofer " + numeroLicencia + ": " + e.getMessage());
        }
        return viajes;
    }
    
    public ViajeDB getViajesSinTerminarId(String idViaje) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(GET_VIAJES_SIN_TERMINIAR_ID);
            select.setString(1, idViaje);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                return armarViajeYEstado(rs);
            }
            PreparedStatement ps2 = connection.prepareStatement(GET_VIAJE_PRIVADO_SIN_TERMINAR);
            ps2.setString(1, idViaje);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                return armarViajeSinDestino(rs2);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar un viaje sin terminar: " + e.getMessage());
        }
        return null;
    }
    
    public boolean esViajePublico(int idViaje) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(GET_VIAJE_PUBLICO);
            select.setInt(1, idViaje);
            ResultSet rs = select.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar un viaje sin terminar: " + e.getMessage());
        }
    }
    
    public double getPrecioViaje(int idViaje) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(GET_PRECIO_VIAJE);
            select.setInt(1, idViaje);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                return rs.getDouble("precio_boleto");
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar un viaje sin terminar: " + e.getMessage());
        }
        return 0;
    }
    
    public ViajeDB armarViaje(ResultSet rs) throws SQLException {
        return new ViajeDB(rs.getInt("id"), rs.getString("chofer"), rs.getString("bus"));
    }
    
    private ViajeDB armarViajeSinDestino(ResultSet rs) throws SQLException {
        boolean estado = false;
        String horaSalida = rs.getString("hora_salida");
        if (horaSalida != null) {
            estado = true;
        }
        return new ViajeDB(rs.getInt("id"), rs.getString("chofer"), rs.getString("bus"), estado);
    }
    
    private ViajeDB armarViajeYEstado(ResultSet rs) throws SQLException {
        boolean estado = false;
        String horaSalida = rs.getString("hora_salida");
        if (horaSalida != null) {
            estado = true;
        }
        return new ViajeDB(rs.getInt("id"), rs.getString("chofer"), rs.getString("bus"), estado, rs.getString("sucursal_destino"));
    }
     
}
