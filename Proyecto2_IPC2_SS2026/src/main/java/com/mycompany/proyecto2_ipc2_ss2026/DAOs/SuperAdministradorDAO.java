/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.AñoLectivoRequest;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author milton
 */
public class SuperAdministradorDAO {
    
    
    private final String MODIFICAR_SALARIO = "UPDATE empleado SET salario = ? WHERE dpi = ?";
    private final String CREAR_NUEVO_AÑO_LECTIVO = "INSERT INTO ciclo_escolar (anio, fecha_inicio, fecha_fin)";
    private final String MODIFICAR_AÑO_LECTIVO = "UPDATE ciclo_escolar SET fecha_inicio = ?, fecha_fin WHERE anio = ?";
    private final String DESACTIVAR_CICLOS_ACTIVOS = "UPDATE ciclo_escolar SET estado = FALSE WHERE estado = TRUE";
    private final String ACTIVAR_CICLO_ESCOLAR_ACTUAL = "UPDATE ciclo_escolar SET estado = TRUE WHERE id = ?";
    
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
    
    public void crearAñoLectivo(AñoLectivoRequest request) throws AccesoALaDataException {
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
    }
    
    public void modificarAñoLectivo(AñoLectivoRequest añoLectivo) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(MODIFICAR_AÑO_LECTIVO);
            Date dateInicio = Date.valueOf(añoLectivo.getFechaInicio());
            Date dateFin = Date.valueOf(añoLectivo.getFechaFin());
            ps.setDate(1, dateInicio);
            ps.setDate(2, dateFin);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar un año lectivo: " + e.getMessage());
        }
    }
    
    public void modificarEstadoAñoLectivo(int codigo) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps1 = connection.prepareStatement(DESACTIVAR_CICLOS_ACTIVOS);
            ps1.executeUpdate();
            
            PreparedStatement ps2 = connection.prepareStatement(ACTIVAR_CICLO_ESCOLAR_ACTUAL);
            ps2.setInt(1, codigo);
            ps2.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al activar un año lectivo: " + e.getMessage());
        }
    }
    
}
