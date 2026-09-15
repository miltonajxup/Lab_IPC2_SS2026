/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.BusDB;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ViajePublicoDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.Asiento;
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
    
    //SELECT viap.* FROM viaje AS via LEFT JOIN viaje_ejecucion AS viaej ON via.id = viaej.viaje_id JOIN viaje_publico AS viap ON via.id = viap.id_viaje JOIN horario_ruta AS hor ON viap.horario = hor.id JOIN ruta AS rut ON hor.ruta = rut.id
    private final String AGREGAR_COMPRA_BOLETO = "INSERT INTO boleto_viaje (usuario, viaje, asiento) VALUES (?,?,?)";
    private final String ACTUALIZAR_SALDO_CLIENTE = "UPDATE usuario SET credito_disponible = ? WHERE dpi = ?";
    private final String BUSCAR_VIAJES_ORIGEN_DESTINO = 
            """
            SELECT 
            viap.*, via.chofer, via.bus, 
            rut.sucursal_origen, rut.sucursal_destino, 
            hor.hora_salida, hor.hora_aprox_llegada 
            FROM viaje AS via LEFT JOIN viaje_ejecucion AS viaej ON via.id = viaej.viaje_id 
            JOIN viaje_publico AS viap ON via.id = viap.id_viaje 
            JOIN horario_ruta AS hor ON viap.horario = hor.id 
            JOIN ruta AS rut ON hor.ruta = rut.id 
            WHERE rut.id = ? AND viaej.hora_salida IS NULL""";
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
    private final String GET_ASIENTOS_BUS = "SELECT * FROM boleto_viaje WHERE viaje = ?";
    
    private final BusDAO busdao;

    public ViajePublicoDAO() {
        busdao = new BusDAO();
    }
    
    public void agregarCompraBoleto(String dpiUsuario, int idViaje, int numeroAsiento, double saldoActual) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement insert = connection.prepareStatement(AGREGAR_COMPRA_BOLETO);
            insert.setString(1, dpiUsuario);
            insert.setInt(2, idViaje);
            insert.setInt(3, numeroAsiento);
            insert.executeUpdate();
            
            actualizarSaldoCliente(connection, saldoActual, dpiUsuario);
            
            connection.commit();
        } catch (SQLException | AccesoALaDataException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new AccesoALaDataException("Error al hacer el rollback de compra de boleto " + ex.getMessage());
            }
            throw new AccesoALaDataException("Error al agregar una compra de boleto " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new AccesoALaDataException("Error al devolver el autocomit de compra boleto" + ex.getMessage());
            }
        }
    }
    
    private void actualizarSaldoCliente(Connection connection, double saldoActual, String dpi) throws AccesoALaDataException {
        try {
            PreparedStatement ps = connection.prepareStatement(ACTUALIZAR_SALDO_CLIENTE);
            ps.setDouble(1, saldoActual);
            ps.setString(2, dpi);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al actualizar el saldo de un usuario despues de compra de un boleto " + e.getMessage());
        }
    }
    
    public List<Asiento> getAsientosBus(String numeroPlaca, int idViaje) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            BusDB bus = busdao.getBusId(numeroPlaca);
            if (bus != null) {
                List<Asiento> asientos = armarAsientos(bus.getCapacidadPasajeros());
                PreparedStatement psAsientos = connection.prepareStatement(GET_ASIENTOS_BUS);
                psAsientos.setInt(1, idViaje);
                ResultSet rsAsientos = psAsientos.executeQuery();
                while (rsAsientos.next()) {
                    for (Asiento asiento : asientos) {
                        if (asiento.getNumAsiento() == rsAsientos.getInt("asiento")) {
                            asiento.setOcupado(true);
                        }
                    }
                }
                return asientos;
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los asientos de un bus " + e.getMessage());
        }
        return null;
    }
    
    public List<ViajePublicoDB> getViajesPublicosOrigenDestino(String idRuta) throws AccesoALaDataException {
        List<ViajePublicoDB> viajes = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(BUSCAR_VIAJES_ORIGEN_DESTINO);
            select.setString(1, idRuta);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                viajes.add(armarViajePublico(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar viajes publicos por origen y destino " + e.getMessage());
        }
        return viajes;
    }
    
    private List<ViajePublicoDB> getViajesPublicosRuta(int ruta, String fecha) throws AccesoALaDataException {
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
                rs.getString("sucursal_origen"), 
                rs.getString("sucursal_destino"), 
                rs.getString("hora_salida"), 
                rs.getString("hora_aprox_llegada"));
    }
    
    private List<Asiento> armarAsientos(int capacidad) {
        List<Asiento> asientos = new ArrayList<>();
        for (int i = 0; i < capacidad; i++) {
            asientos.add(new Asiento(i + 1, false));
        }
        return asientos;
    }
    
}
