/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs.Bibliotecario;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.MultaDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.MultaRequest;
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
public class MultaDAO {
    
    private final String MULTA_POR_ID = "SELECT * FROM multa WHERE id = ?";
    private final String AGREGAR_MULTA = "INSERT INTO multa (monto, prestamo_id) VALUES (?,?)";
    private final String PAGAR_MULTA = "UPDATE multa SET estado = TRUE, fecha_pago = CURDATE() WHERE id = ?";
    private final String GET_MULTAS_SIN_PAGAR = "SELECT * FROM multa WHERE estado = FALSE LIMIT ?, ?";
    private final String GET_MULTA_POR_PRESTAMO = "SELECT * FROM multa WHERE prestamo_id = ?";
    
    public boolean existeMulta(int id) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(MULTA_POR_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al revisar la existencia de una multa: " + e.getMessage());
        }
    }
    
    public MultaDB multaPorId(int id) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(MULTA_POR_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return armarMulta(rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al revisar la existencia de una multa: " + e.getMessage());
        }
        return null;
    }
    
    public void agregarMulta(MultaRequest multa) throws AccesoALaDataException{
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(AGREGAR_MULTA)) {
            ps.setDouble(1, multa.getMonto());
            ps.setInt(2, multa.getIdPrestamo());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar una multa: " + e.getMessage());
        }
    }
    
    public void pagarMulta(int idMulta) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(PAGAR_MULTA)) {
            ps.setInt(1, idMulta);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al pagar una multa: " + e.getMessage());
        }
    }
    
    public List<MultaDB> getMultasSinPagar(int numeroPagina) throws AccesoALaDataException {
        int paginacion = (numeroPagina - 1) * LimitePagina.LIMITE;
        List<MultaDB> multas = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_MULTAS_SIN_PAGAR)) {
            ps.setInt(1, paginacion);
            ps.setInt(2, LimitePagina.LIMITE);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    multas.add(armarMulta(rs));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar las multas sin pagar en la pagina " + numeroPagina + ": " + e.getMessage());
        }
        return multas;
    }
    
    public List<MultaDB> getMultPorPrestamo(int idPrestamo) throws AccesoALaDataException {
        List<MultaDB> multas = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_MULTA_POR_PRESTAMO)) {
            ps.setInt(1, idPrestamo);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    multas.add(armarMulta(rs));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar las multas relacionadas a un prestamo: " + e.getMessage());
        }
        return multas;
    }
    
    private MultaDB armarMulta(ResultSet rs) throws SQLException {
        return new MultaDB(
                rs.getInt("id"), 
                rs.getDouble("monto"), 
                rs.getBoolean("estado"), 
                rs.getDate("fecha_pago").toLocalDate(), 
                rs.getInt("prestamo_id"));
    }
    
}
