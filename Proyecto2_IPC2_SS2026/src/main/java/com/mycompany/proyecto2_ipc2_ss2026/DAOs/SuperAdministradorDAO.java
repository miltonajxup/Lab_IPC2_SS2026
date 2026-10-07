/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.AñoLectivoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.AñoLectivoRequest;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.GradoRequest;
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
public class SuperAdministradorDAO {
    
    private final String GET_AÑOS_LECTIVOS = "SELECT * FROM ciclo_escolar";
    private final String GET_AÑO_POR_ID = "SELECT * FROM ciclo_escolar WHERE anio = ?";
    private final String CREAR_NUEVO_AÑO_LECTIVO = "INSERT INTO ciclo_escolar (anio, fecha_inicio, fecha_fin) VALUES (?,?,?)";
    private final String MODIFICAR_AÑO_LECTIVO = "UPDATE ciclo_escolar SET fecha_inicio = ?, fecha_fin = ? WHERE anio = ?";
    private final String DESACTIVAR_CICLOS_ACTIVOS = "UPDATE ciclo_escolar SET estado = FALSE WHERE estado = TRUE";
    private final String ACTIVAR_CICLO_ESCOLAR_ACTUAL = "UPDATE ciclo_escolar SET estado = TRUE WHERE anio = ?";
    private final String MODIFICAR_SALARIO = "UPDATE empleado SET salario = ? WHERE dpi = ?";
    private final String MODIFICAR_COSTO_COLEGIATURA = "UPDATE grado SET costo_colegiatura = ? WHERE id = ?";
    private final String MODIFICAR_COSTO_INSCRIPCION = "UPDATE grado SET costo_inscripcion = ? WHERE id = ?";
    
    public List<AñoLectivoDB> getAñosLectivos() throws AccesoALaDataException {
        List<AñoLectivoDB> años = new ArrayList<>();
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_AÑOS_LECTIVOS);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                años.add(armarAñoLectivo(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los años lectivos: " + e.getMessage());
        }
        return años;
    }
    
    public AñoLectivoDB getAñoPorId(int año) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_AÑO_POR_ID);
            ps.setInt(1, año);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return armarAñoLectivo(rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Erro al buscar un año lectivo por su id: " + e.getMessage());
        }
        return null;
    }
    
    private AñoLectivoDB armarAñoLectivo(ResultSet rs) throws SQLException {
        return new AñoLectivoDB(rs.getInt("anio"), rs.getDate("fecha_inicio").toLocalDate(), rs.getDate("fecha_fin").toLocalDate(), rs.getBoolean("estado"));
    }
    
    public boolean existeAñoLectivo(int año) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_AÑO_POR_ID);
            ps.setInt(1, año);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar la existencia de un año: " + e.getMessage());
        }
    }
    
    public List<AñoLectivoDB> crearAñoLectivo(AñoLectivoRequest request) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(CREAR_NUEVO_AÑO_LECTIVO);
            ps.setInt(1, request.getAño());
            Date dateInicio = Date.valueOf(request.getFechaInicio());
            Date dateFin = Date.valueOf(request.getFechaFin());
            ps.setDate(2, dateInicio);
            ps.setDate(3, dateFin);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar un nuevo año lectivo: " + e.getMessage());
        }
        
        return getAñosLectivos();
    }   
    
    public AñoLectivoDB modificarAñoLectivo(AñoLectivoRequest añoLectivo, int año) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(MODIFICAR_AÑO_LECTIVO);
            Date dateInicio = Date.valueOf(añoLectivo.getFechaInicio());
            Date dateFin = Date.valueOf(añoLectivo.getFechaFin());
            ps.setDate(1, dateInicio);
            ps.setDate(2, dateFin);
            ps.setInt(3, año);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar un año lectivo: " + e.getMessage());
        }
        return getAñoPorId(año);
    }
    
    public List<AñoLectivoDB> modificarEstadoAñoLectivo(int año) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps1 = connection.prepareStatement(DESACTIVAR_CICLOS_ACTIVOS);
            ps1.executeUpdate();
            
            PreparedStatement ps2 = connection.prepareStatement(ACTIVAR_CICLO_ESCOLAR_ACTUAL);
            ps2.setInt(1, año);
            ps2.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al activar un año lectivo: " + e.getMessage());
        }
        return getAñosLectivos();
    }
    
    public void modificarSalario(double salarioActual, String dpiUsuario) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(MODIFICAR_SALARIO);
            ps.setDouble(1, salarioActual);
            ps.setString(2, dpiUsuario);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar el salario de un usario " + e.getMessage());
        }
    }
    
    public void modificarCostoColegiatura(GradoRequest grado, int idGrado) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(MODIFICAR_COSTO_COLEGIATURA);
            ps.setDouble(1, grado.getCostoColegiatura());
            ps.setInt(2, idGrado);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar el costo de una colegiatura: " + e.getMessage());
        }
    }
    
    public void modificarCostoInscripcion(GradoRequest grado, int idGrado) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(MODIFICAR_COSTO_INSCRIPCION);
            ps.setDouble(1, grado.getCostoInscripcion());
            ps.setInt(1, idGrado);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar el costo de inscripcion: " + e.getMessage());
        }
    }
    
}
