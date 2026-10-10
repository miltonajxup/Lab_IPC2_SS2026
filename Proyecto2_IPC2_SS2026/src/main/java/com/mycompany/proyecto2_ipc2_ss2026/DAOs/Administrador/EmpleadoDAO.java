/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.RolUsuario;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.CicloEscolar;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.UsuarioDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.EmpleadoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.UsuarioRequest;
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
public class EmpleadoDAO {
    
    private final String AGREGAR_EMPLEADO = "INSERT INTO empleado (dpi_empleado) VALUES (?)";
    private final String EXISTE_CONTRATO = "SELECT * FROM contrato_empleado WHERE empleado_dpi = ? AND ciclo_escolar_id = ?";
    private final String CREAR_CONTRATO_EMPLEADO = "INSERT INTO contrato_empleado (empleado_dpi, ciclo_escolar_id, salario) VALUES (?,?,?)";
    private final String GET_EMPLEADO_POR_DPI = 
            """
            SELECT usu.*, emp.*, con.salario, con.fecha_contratacion 
            FROM usuario AS usu 
            JOIN empleado AS emp ON usu.dpi = emp.dpi_empleado 
            JOIN contrato_empleado AS con ON emp.dpi_empleado = con.empleado_dpi 
            WHERE con.ciclo_escolar_id = ? AND usu.dpi = ? """;
    private final String GET_EMPLEADOS_POR_ROL = 
            """
            SELECT usu.*, emp.*, con.salario, con.fecha_contratacion 
            FROM usuario AS usu 
            JOIN empleado AS emp ON usu.dpi = emp.dpi_empleado 
            JOIN contrato_empleado AS con ON emp.dpi_empleado = con.empleado_dpi 
            WHERE con.ciclo_escolar_id = ? AND usu.rol = ? LIMIT ?, ?""";
    
    private final UsuarioDAO usuariodao;
    private final CicloEscolar ciclo;
    
    public EmpleadoDAO() {
        usuariodao = new UsuarioDAO();
        ciclo = new CicloEscolar();
    }
    
    public void agregarEmpleado(UsuarioRequest usuario) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            connection.setAutoCommit(false);
            
            usuariodao.agregarUsuario(connection, usuario);
            
            PreparedStatement ps = connection.prepareStatement(AGREGAR_EMPLEADO);
            ps.setString(1, usuario.getDpi());
            ps.executeUpdate();
            
            contratarUsuario(connection, usuario);
            
            connection.commit();
        } catch (SQLException | AccesoALaDataException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new AccesoALaDataException("Error al realizar un rollback al agregar un usuario de tipo " + usuario.getRol().name() + ": " + ex.getMessage() 
                        + "\nError Inicial " + e.getMessage());
            }
            throw new AccesoALaDataException("Error al agregar un usuario de tipo " + usuario.getRol().name() + ": " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException ex) {
                throw new AccesoALaDataException("Error al restarurar el autocommit de la creacion de un usuario " + ex.getMessage());
            }
        }
    }
    
    public boolean existeContrato(String dpiEmpleado) throws AccesoALaDataException {
        int añoActivo = ciclo.getAñoActivo();
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(EXISTE_CONTRATO);
            ps.setString(1, dpiEmpleado);
            ps.setInt(2, añoActivo);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Erroa al revisar la existencia de un contrato: " + e.getMessage());
        }
    }
    
    public void contratarUsurario(UsuarioRequest usuario) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        contratarUsuario(connection, usuario);
    }
    
    private void contratarUsuario(Connection connection, UsuarioRequest usuario) throws AccesoALaDataException {
        int añoActivo = ciclo.getAñoActivo();
        try {
            PreparedStatement ps = connection.prepareStatement(CREAR_CONTRATO_EMPLEADO);
            ps.setString(1, usuario.getDpi());
            ps.setInt(2, añoActivo);
            ps.setDouble(3, usuario.getSalario());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al generan un contrato " + e.getMessage());
        }
    }
    
    public EmpleadoDB getEmpleadoPorID(String dpiEmpleado) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        int año = ciclo.getAñoActivo();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_EMPLEADO_POR_DPI);
            ps.setInt(1, año);
            ps.setString(2, dpiEmpleado);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return armarEmpleado(rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar a un empleado por id " + e.getMessage());
        }
        return null;
    }
    
    public List<EmpleadoDB> getEmpleadosPorRol(String rol, int numeroPagina) throws AccesoALaDataException {
        List<EmpleadoDB> empleados = new ArrayList<>();
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        int año = ciclo.getAñoActivo();
        int paginacion = (numeroPagina - 1) * LimitePagina.LIMITE;
        try {
            PreparedStatement ps = connection.prepareStatement(GET_EMPLEADOS_POR_ROL);
            ps.setInt(1, año);
            ps.setString(2, rol);
            ps.setInt(3, paginacion);
            ps.setInt(4, LimitePagina.LIMITE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                empleados.add(armarEmpleado(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar a los empleados del rol " + rol + ": " + e.getMessage());
        }
        return empleados;
    }
    
    private EmpleadoDB armarEmpleado(ResultSet rs) throws SQLException {
        return new EmpleadoDB(
                rs.getString("dpi_empleado"), 
                rs.getString("nombre"), 
                RolUsuario.valueOf(rs.getString("rol")), 
                rs.getDouble("salario"), 
                rs.getString("fecha_contratacion"));
    }
    
}
