/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.Request.ConclusionViaje;
import com.mycompany.proyecto1ss2026.Modelos.Request.DepreciacionBus;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author milton
 */
public class EjecucionViajeDAO {
    
    private final String AGREGAR_VIAJE_EJECUCION = "INSERT INTO viaje_ejecucion (kilometraje_salida, viaje_id) VALUES (?,?)";
    private final String MODIFICAR_VIAJE_EJECUCION = "UPDATE viaje_ejecucion SET hora_llegada = CURRENT_TIME, kilometraje_llegada = ?, gasto_combustible = ? WHERE viaje_id = ?";
    private final String ACTUALIZAR_KILOMETROS_BUS = "UPDATE bus SET kilometraje = ?, sucursal_actual = ? WHERE numero_placa = ?";
    private final String ACTUALIZAR_SALDO_CHOFER = "UPDATE chofer SET saldo_disponible = ?, sucursal_actual = ? WHERE numero_de_licencia = ?";
    private final String AGREGAR_REGISTRO_DEPRECIACION = "INSERT INTO depreciacion_bus (fecha_registro, kilometros_recorridos, bus, depreciacion_id, monto_depreciado) VALUES (?,?,?,?,?)";
    
    public void agregarViajeEjecucion(int kilometrajeSalida, int idViaje) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(AGREGAR_VIAJE_EJECUCION);
            select.setInt(1, kilometrajeSalida);
            select.setInt(2, idViaje);
            select.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al crear un registro de viaje ejcucion " + e.getMessage());
        }
    }
    
    public void modificarViajeEjecucion(ConclusionViaje conclusion, DepreciacionBus depreciacion) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement updateViaje = connection.prepareStatement(MODIFICAR_VIAJE_EJECUCION);
            updateViaje.setInt(1, conclusion.getKilometrajeLlegada());
            updateViaje.setDouble(2, conclusion.getGastoCombustible());
            updateViaje.setString(3, conclusion.getIdViaje());
            updateViaje.executeUpdate();
            
            actualizarBus(connection, conclusion.getKilometrajeLlegada(), conclusion.getDestino(), conclusion.getNumeroPlaca());
            
            actualizarChofer(connection, conclusion.getSaldoChofer(), conclusion.getDestino(), conclusion.getNumeroLicencia());
            
            agregarRegistroDepreciacion(depreciacion, connection);
            
            connection.commit();
        } catch (SQLException | AccesoALaDataException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new AccesoALaDataException("Error al hacer Roll Back de modificacion de Ejecucion " + ex.getMessage());
            }
            throw new AccesoALaDataException("Error al modificar el registro de viaje ejcucion del viaje " + conclusion.getIdViaje() + ": \n" + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new AccesoALaDataException("Error al regresar el auto commit de la actualizacion de la ejecucion del viaje " + ex.getMessage());
            }
        }
        
    }
    
    private void actualizarBus(Connection connection, int kilometrajeLlegada, String sucursalActual, String numeroPlaca) throws AccesoALaDataException {
        try {
            PreparedStatement updateBus = connection.prepareStatement(ACTUALIZAR_KILOMETROS_BUS);
            updateBus.setInt(1, kilometrajeLlegada);
            updateBus.setString(2, sucursalActual);
            updateBus.setString(3, numeroPlaca);
            updateBus.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error actualizar el saldo del chofer " + e.getMessage());
        }
    }
    
    private void actualizarChofer(Connection connection, double saldoChofer, String sucursalActual, String numeroLicencia) throws AccesoALaDataException {
        try {
            PreparedStatement updateChofer = connection.prepareStatement(ACTUALIZAR_SALDO_CHOFER);
            updateChofer.setDouble(1, saldoChofer);
            updateChofer.setString(2, sucursalActual);
            updateChofer.setString(3, numeroLicencia);
            updateChofer.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error actualizar el saldo del chofer " + e.getMessage());
        }
    }
    
    private void agregarRegistroDepreciacion(DepreciacionBus depreciacion, Connection connection) throws AccesoALaDataException {
        try {
            PreparedStatement insert = connection.prepareStatement(AGREGAR_REGISTRO_DEPRECIACION);
            Date date = Date.valueOf(depreciacion.getFechaRegistro());
            insert.setDate(1, date);
            insert.setInt(2, depreciacion.getKilometrosRecorridos());
            insert.setString(3, depreciacion.getBus());
            insert.setInt(4, depreciacion.getDepreciacionId());
            insert.setDouble(5, depreciacion.getMontoDepreciado());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar registro depreciacion bus " + e.getMessage());
        }
    }
    
}
