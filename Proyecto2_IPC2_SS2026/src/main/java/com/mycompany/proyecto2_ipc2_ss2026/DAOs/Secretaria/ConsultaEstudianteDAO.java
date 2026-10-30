/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs.Secretaria;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.ArmarEstudiante;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.EstudianteDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.ExpedienteEstudiante;
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
public class ConsultaEstudianteDAO {
    
    private final String GET_ESTUDIANTES_POR_GRADO_SECCION = 
            """
            SELECT usu.*, est.* FROM usuario AS usu 
            JOIN estudiante AS est ON usu.dpi = est.dpi_estudiante 
            JOIN inscripcion AS ins ON usu.dpi = ins.estudiante 
            JOIN grupo_ciclo_escolar AS grup ON ins.grupo_id = grup.id 
            WHERE grup.grado_id = ? AND grup.seccion_id = ?""";
    private final String GET_ESTUDIANTES_POR_CARRERA_SECCION = 
            """
            SELECT usu.*, est.* FROM usuario AS usu 
            JOIN estudiante AS est ON usu.dpi = est.dpi_estudiante 
            JOIN inscripcion AS ins ON usu.dpi = ins.estudiante 
            JOIN grupo_ciclo_escolar AS grup ON ins.grupo_id = grup.id 
            WHERE grup.carrera_id = ? AND grup.seccion_id = ?""";
    private final String GET_ESTUDIANTE_POR_NOMBRE = 
            """
            SELECT usu.*, est.* FROM usuario AS usu 
            JOIN estudiante AS est ON usu.dpi = est.dpi_estudiante 
            WHERE usu.nombre LIKE CONCAT(?, '%')""";
    private final String EXPEDIENTE_ESTUDIANTE = 
            """
            SELECT usu.*, est.estado, gra.nombre AS nombre_grado, niv.nombre AS nivel_academico 
            FROM inscripcion AS ins 
            JOIN grupo_ciclo_escolar AS grup ON ins.grupo_id = grup.id 
            JOIN usuario AS usu ON ins.estudiante = usu.dpi 
            JOIN grado AS gra ON grup.grado_id = gra.id 
            JOIN nivel_academico AS niv ON gra.nivel_id = niv.id 
            JOIN estudiante AS est ON usu.dpi = est.dpi_estudiante 
            WHERE ins.estudiante = ? ORDER BY ins.id DESC""";
    
    private final ArmarEstudiante armar;
    
    public ConsultaEstudianteDAO() {
        armar = new ArmarEstudiante();
    }
    
    public List<EstudianteDB> getEstudiantesGradoSeccion(int idGrado, String seccion) throws AccesoALaDataException {
        List<EstudianteDB> estudiantes = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_ESTUDIANTES_POR_GRADO_SECCION)) {
            ps.setInt(1, idGrado);
            ps.setString(2, seccion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                estudiantes.add(armar.estudiante(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los estudiantes del grado " + idGrado + " y seccion " + seccion + ": " + e.getMessage());
        }
        return estudiantes;
    }
    
    public List<EstudianteDB> getEstudiantesCarreraSeccion(String carrera, String seccion) throws AccesoALaDataException {
        List<EstudianteDB> estudiantes = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_ESTUDIANTES_POR_CARRERA_SECCION)) {
            ps.setString(1, carrera);
            ps.setString(2, seccion);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                estudiantes.add(armar.estudiante(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los estudiantes de la carrera " + carrera + " y seccion " + seccion + ": " + e.getMessage());
        }
        return estudiantes;
    }
    
    public List<EstudianteDB> getEstudiantePorNombre(String nombre) throws AccesoALaDataException {
        List<EstudianteDB> estudiantes = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_ESTUDIANTE_POR_NOMBRE)) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    estudiantes.add(armar.estudiante(rs));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar estudiantes por nombre: " + e.getMessage());
        }
        return estudiantes;
    }
    
    public ExpedienteEstudiante getExpediente(String idEstudiante) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(EXPEDIENTE_ESTUDIANTE)) {
            ps.setString(1, idEstudiante);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new ExpedienteEstudiante(
                        rs.getString("dpi"), 
                        rs.getString("nombre"), 
                        rs.getString("rol"), 
                        rs.getString("estado"), 
                        rs.getString("nombre_grado") + "  " + rs.getString("nivel_academico"));
            }
        } catch (Exception e) {
            throw new AccesoALaDataException("Error al buscar el expediente de un estudiante: " + e.getMessage());
        }
        return null;
    }
    
}
