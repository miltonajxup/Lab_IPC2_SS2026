/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs.Secretaria;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.ArmarBoletaPago;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.BoletaPagoDB;
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
public class RecepcionPagoDAO {
    
    private final String BOLETAS_POR_ESTUDIANTE = "SELECT bol.*, usu.nombre AS nombre_estudiante FROM boleta_pago AS bol JOIN usuario AS usu ON bol.estudiante_dpi = usu.dpi WHERE bol.estudiante_dpi = ?";
    private final String BOLETA_PAGO_POR_ID = "SELECT bol.*, usu.nombre AS nombre_estudiante FROM boleta_pago AS bol JOIN usuario AS usu ON bol.estudiante_dpi = usu.dpi WHERE bol.id = ?";
    private final String PAGAR_BOLETA_PAGO = "UPDATE boleta_pago SET estado = TRUE WHERE id = ?";
    private final String AGREGAR_PAGO = "INSERT INTO pago (boleta_id, secretaria_dpi) VALUES (?,?)";
    
    private final ArmarBoletaPago armar;
    
    public RecepcionPagoDAO() {
        armar = new ArmarBoletaPago();
    }
    
    public List<BoletaPagoDB> getBoletasPendientesEstudiante(String idEstudiante) throws AccesoALaDataException {
        List<BoletaPagoDB> boletas = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(BOLETAS_POR_ESTUDIANTE)) {
            ps.setString(1, idEstudiante);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    boletas.add(armar.armar(rs));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar las boletas de pago de un estudiante: " + e.getMessage());
        }
        return boletas;
    }
    
    public boolean estaPagada(int id) throws AccesoALaDataException, DataInexistenteException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(BOLETA_PAGO_POR_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("estado");
                } else {
                    throw new DataInexistenteException("La boleta de pago no existe");
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al revisar la existencia de una boleta de pago: " + e.getMessage());
        }
    }
    
    public BoletaPagoDB realizarPago(int idBoleta, String idSecretaria) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection()) {
            
            connection.setAutoCommit(false);
            try {
                
                try (PreparedStatement ps = connection.prepareStatement(PAGAR_BOLETA_PAGO)) {
                    ps.setInt(1, idBoleta);
                    ps.executeUpdate();
                }
                
                try (PreparedStatement ps = connection.prepareStatement(AGREGAR_PAGO)) {
                    ps.setInt(1, idBoleta);
                    ps.setString(2, idSecretaria);
                    ps.executeUpdate();
                }
                
                connection.commit();
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new AccesoALaDataException("Error al hacer un rollback al realizar un pago de boleta: " + ex.getMessage() + " \nOrigen: " + e.getMessage());
                }
                throw new AccesoALaDataException("Error al realizar un pago de una boleta de pago: \n" + e.getMessage());
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new AccesoALaDataException("Error al devolver el auto commit al realizar un pago: " + e.getMessage());
                }
            }
            
        } catch (SQLException e) {
            throw new AccesoALaDataException("Ocurrio un error de conexion al realizar un pago de boleta: " + e.getMessage());
        }
        return getBoletaPorId(idBoleta);
    }
    
    private BoletaPagoDB getBoletaPorId(int id) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(BOLETA_PAGO_POR_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return armar.armar(rs);
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar una boleta por ID: " + e.getMessage());
        }
        return null;
    }
    
}
