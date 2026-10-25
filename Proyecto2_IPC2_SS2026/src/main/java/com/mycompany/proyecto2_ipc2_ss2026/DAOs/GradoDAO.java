/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.GradoDB;
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
public class GradoDAO {
    
    private final String EXISTE_GRADO = "SELECT * FROM grado WHERE id = ?";
    private final String GRADOS_CARRERA = "SELECT gra.*, niv.nombre AS nombre_nivel FROM grado AS gra JOIN nivel_academico AS niv ON gra.nivel_id = niv.id WHERE nivel_id = 4";
    private final String GRADOS_NORMALES = "SELECT gra.*, niv.nombre AS nombre_nivel FROM grado AS gra JOIN nivel_academico AS niv ON gra.nivel_id = niv.id WHERE nivel_id != 4";
    private final String TODOS_LOS_GRADOS = "SELECT gra.*, niv.nombre AS nombre_nivel FROM grado AS gra JOIN nivel_academico AS niv ON gra.nivel_id = niv.id ";
    
    public boolean existeGrado(int idGrado) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(EXISTE_GRADO)) {
            ps.setInt(1, idGrado);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al revisar si una carrera existe: " + e.getMessage());
        }
    }
    
    public List<GradoDB> getGradosCarrera() throws AccesoALaDataException {
        List<GradoDB> grados = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GRADOS_CARRERA)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                grados.add(armarGrado(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los grados para carreras: " + e.getMessage());
        }
        return grados;
    }
    
    public List<GradoDB>  getGradosNormales() throws AccesoALaDataException {
        List<GradoDB> guardados = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement psGrados = connection.prepareStatement(GRADOS_NORMALES)) {
            ResultSet rsGrados = psGrados.executeQuery();
            while (rsGrados.next()) {
                guardados.add(armarGrado(rsGrados));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los grados guardados: " + e.getMessage());
        }
        return guardados;
    }
    
    public List<GradoDB> getTodosLosGrados() throws AccesoALaDataException {
        List<GradoDB> grados = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(TODOS_LOS_GRADOS)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                grados.add(armarGrado(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar todos los grados: " + e.getMessage());
        }
        return grados;
    }
    
    //Hay un metodo igual en CarreraDAO
    private GradoDB armarGrado(ResultSet rs) throws SQLException {
        return new GradoDB(
                rs.getInt("id"), 
                rs.getString("nombre"), 
                rs.getDouble("costo_inscripcion"), 
                rs.getDouble("costo_colegiatura"), 
                rs.getInt("nivel_id"), 
                rs.getString("nombre_nivel"));
    }
    
}
