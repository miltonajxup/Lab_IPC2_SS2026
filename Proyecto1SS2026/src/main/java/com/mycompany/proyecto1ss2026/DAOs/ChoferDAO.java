/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Constantes.TipoLicencia;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ChoferDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.ChoferRequest;
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
public class ChoferDAO {
    
    private final String AGREGAR_CHOFER = "INSERT INTO chofer(numero_de_licencia, nombre, tipo_de_licencia, "
            + "fecha_vencimiento, numero_telefono, salario_por_viaje, sucursal_base, sucursal_actual) "
            + "VALUES (?,?,?,?,?,?,?,?)";
    private final String EDITAR_INFO_CHOFER = "UPDATE chofer SET nombre = ?, tipo_de_licencia = ? "
            + "fecha_vencimiento = ?, numero_telefono = ?, salario_por_viaje = ?, sucursal_base = ? WHERE numero_de_licencia = ?";
    private final String CAMBIAR_ESTADO_CHOFER = "UPDATE chofer SET estado_operativo = ? WHERE numero_de_licencia = ?";
    private final String ACTUALIZAR_SUCURSAL_ACTUAL = "UPDATE chofer SET sucursal_actual = ? WHERE numero_de_licencia = ?";
    private final String GET_CHOFER_ID = "SELECT * FROM chofer WHERE numero_de_licencia = ?";
    private final String GET_CHOFERES_SUCURSAL_BASE = "SELECT * FROM chofer WHERE sucursal_base = ?";
    private final String GET_CHOFERES_SUCURSAL_ACTUAL = "SELECT * FROM chofer WHERE sucursal_actual = ?";
    private final String TODOS_CHOFERES = "SELECT * FROM chofer";
    //SELECT dpi, nombre, LENGTH(foto) AS tamaño_foto, numero_licencia, fecha_vencimiento, numero_telefono, salario_por_viaje, estado_operativo, sucursal_base FROM chofer;
    
    public void agregarChofer(ChoferRequest request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement insert = connection.prepareStatement(AGREGAR_CHOFER);
            insert.setString(1, request.getNumeroLicencia());
            insert.setString(2, request.getNombre());
            
            insert.setString(3, request.getTipoLicencia().name());
            Date fecha = Date.valueOf(request.getFechaVencimiento());
            insert.setDate(4, fecha);
            insert.setString(5, request.getNumeroTelefono());
            insert.setDouble(6, request.getSalarioPorViaje());
            insert.setString(7, request.getSucursalBase());
            insert.setString(8, request.getSucursalBase());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar un chofer " + e.getMessage());
        }
    }
    
    public void editarInfoChofer(ChoferRequest request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement update = connection.prepareStatement(EDITAR_INFO_CHOFER);
            
            update.setString(1, request.getNombre());
            update.setString(2, request.getTipoLicencia().name());
            Date fecha = Date.valueOf(request.getFechaVencimiento());
            update.setDate(3, fecha);
            update.setString(4, request.getNumeroTelefono());
            update.setDouble(5, request.getSalarioPorViaje());
            update.setString(6, request.getSucursalBase());
            update.setString(7, request.getNumeroLicencia());
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al editar a un chofer " + e.getMessage());
        }
    }
    
    public void cambiarEstadoChofer(boolean estado, String dpiChofer) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement update = connection.prepareStatement(CAMBIAR_ESTADO_CHOFER);
            update.setBoolean(1, estado);
            update.setString(2, dpiChofer);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar el estado del chofer " + e.getMessage());
        }
    }
    
    public void actualizarSucursalActualChofer(String dpiChofer, String sucursalActual) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement update = connection.prepareStatement(ACTUALIZAR_SUCURSAL_ACTUAL);
            update.setString(1, sucursalActual);
            update.setString(2, dpiChofer);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al actualizar de sucursal a un chofer " + e.getMessage());
        }
    }
    
    public boolean existeChofer(String dpiChofer) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_CHOFER_ID);
            select.setString(1, dpiChofer);
            ResultSet rs = select.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar a chofer por dpi " + e.getMessage());
        }
    }
    
    public ChoferDB getChoferId(String dpiChofer) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_CHOFER_ID);
            select.setString(1, dpiChofer);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                return armarChofer(rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar a chofer por dpi " + e.getMessage());
        }
        return null;
    }
    
    public List<ChoferDB> choferesSucursalBase(String sucursalBase) throws AccesoALaDataException {
        List<ChoferDB> choferes = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_CHOFERES_SUCURSAL_BASE);
            select.setString(1, sucursalBase);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                choferes.add(armarChofer(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar todos los choferes de la sucursal " + sucursalBase + ": " + e.getMessage());
        }
        return choferes;
    }
    
    public List<ChoferDB> choferesSucursalActual(String sucursalActual) throws AccesoALaDataException {
        List<ChoferDB> choferes = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(GET_CHOFERES_SUCURSAL_ACTUAL);
            select.setString(1, sucursalActual);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                choferes.add(armarChofer(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar todos los choferes que se encuentran en la sucursal " + sucursalActual + ": " + e.getMessage());
        }
        return choferes;
    }
    
    public List<ChoferDB> todosLosChoferes() throws AccesoALaDataException {
        List<ChoferDB> choferes = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(TODOS_CHOFERES);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                choferes.add(armarChofer(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar todos los choferes " + e.getMessage());
        }
        return choferes;
    }
    
    private ChoferDB armarChofer(ResultSet rs) throws SQLException {
        return new ChoferDB(
                rs.getString("numero_de_licencia"), 
                rs.getString("nombre"), 
                rs.getBytes("foto"), 
                TipoLicencia.valueOf(rs.getString("tipo_de_licencia")),
                rs.getString("fecha_vencimiento"), 
                rs.getString("numero_telefono"), 
                rs.getDouble("salario_por_viaje"), 
                rs.getBoolean("estado_operativo"), 
                rs.getString("sucursal_base"), 
                rs.getString("sucursal_actual"));
    }
    
}
