/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.CarreraDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.GradoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.CarreraRequest;
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
public class CarreraDAO {
    
    private final String GRADOS_DISPONIBLES = "SELECT gra.*, niv.nombre AS nombre_nivel FROM grado AS gra JOIN nivel_academico AS niv ON gra.nivel_id = niv.id WHERE nivel_id = 4";
    private final String EXISTE_CARRERA = "SELECT * FROM carrera WHERE nombre = ?";
    private final String CREAR_CARRERA = "INSERT INTO carrera (codigo, nombre) VALUES (?,?)";
    private final String AGREGAR_GRADO_CARRERA = "INSERT INTO grado_carrera (carrera_id, grado_id) VALUES (?,?)";
    private final String MODIFICAR_CARRERA = "UPDATE carrera SET nombre = ? WHERE codigo = ?";
    private final String MODIFICAR_ESTADO_CARRERA = "UPDATE carrera SET estado = ? WHERE codigo = ?";
    private final String GET_CARRERAS = "SELECT * FROM carrera LIMIT ?, ?";
    private final String GET_CARRERA_POR_ID = "SELECT * FROM carrera WHERE codigo = ?";
    private final String GET_GRADOS_CARRERA = 
            """
            SELECT gra.*, niv.nombre AS nombre_nivel FROM grado AS gra 
            JOIN nivel_academico AS niv ON gra.nivel_id = niv.id 
            JOIN grado_carrera AS grca ON gra.id = grca.grado_id 
            JOIN carrera AS carr ON grca.carrera_id = carr.codigo 
            WHERE carr.codigo = ?""";
    
    public List<GradoDB> getGradosDisponibles() throws AccesoALaDataException {
        List<GradoDB> grados = new ArrayList<>();
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GRADOS_DISPONIBLES);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                grados.add(armarGrado(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los grados para carreras: " + e.getMessage());
        }
        return grados;
    }
    
    public boolean existeNombreCarrera(String nombre) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(EXISTE_CARRERA);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al revisar si existe una carrera " + e.getMessage());
        }
    }
    
    public List<CarreraDB> crearCarrera(CarreraRequest carrera) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            connection.setAutoCommit(false);
            
            PreparedStatement ps = connection.prepareStatement(CREAR_CARRERA);
            ps.setString(1, carrera.getCodigo());
            ps.setString(2, carrera.getNombre());
            ps.executeUpdate();
            
            PreparedStatement psGrado = connection.prepareStatement(AGREGAR_GRADO_CARRERA);
            for (Integer codigo : carrera.getGrados()) {
                psGrado.setString(1, carrera.getCodigo());
                psGrado.setInt(2, codigo);
                psGrado.executeUpdate();
            }
            
            connection.commit();
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new AccesoALaDataException("Error al hacer el rollback al fallar creando una carrera: " + ex.getMessage() + " \nOrigen: " + e.getMessage());
            }
            throw new AccesoALaDataException("Error al agregar una carrera " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new AccesoALaDataException("Error al devolver el auto commit al fallar creando una carrera: " + e.getMessage());
            }
        }
        return getCarreras(LimitePagina.PAGINA_MINIMA); 
    }
    
    public CarreraDB modificarCarrera(CarreraRequest carrera, String codigoCarrera) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(MODIFICAR_CARRERA);
            ps.setString(1, carrera.getNombre());
            ps.setString(2, codigoCarrera);
            ps.executeUpdate();
            
            return getCarreraPorId(codigoCarrera);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar una carrera: " + e.getMessage());
        }
    }
    
    public CarreraDB modificarEstadoCarrera(boolean estado, String codigo) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(MODIFICAR_ESTADO_CARRERA);
            ps.setBoolean(1, estado);
            ps.setString(2, codigo);
            ps.executeUpdate();
            
            return getCarreraPorId(codigo);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar el estado de una carrera " + e.getMessage());
        }
    }
    
    public List<CarreraDB> getCarreras(int pagina) throws AccesoALaDataException {
        List<CarreraDB> carreras = new ArrayList<>();
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            int paginacion = (pagina - 1) * LimitePagina.LIMITE;
            PreparedStatement ps = connection.prepareStatement(GET_CARRERAS);
            ps.setInt(1, paginacion);
            ps.setInt(2, LimitePagina.LIMITE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                carreras.add(armarCarrera(connection, rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar las carreras " + e.getMessage());
        }
        return carreras;
    }
    
    public CarreraDB getCarreraPorId(String codigo) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_CARRERA_POR_ID);
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return armarCarrera(connection, rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar una carrera por id: " + e.getMessage());
        }
        return null;
    }
    
    private GradoDB armarGrado(ResultSet rs) throws SQLException {
        return new GradoDB(
                rs.getInt("id"), 
                rs.getString("nombre"), 
                rs.getDouble("costo_inscripcion"), 
                rs.getDouble("costo_colegiatura"), 
                rs.getInt("nivel_id"), 
                rs.getString("nombre_nivel"));
    }
    
    private CarreraDB armarCarrera(Connection connection, ResultSet rs) throws SQLException {
        List<GradoDB> grados = new ArrayList<>();
        String codigoCarrera = rs.getString("codigo");
        PreparedStatement ps = connection.prepareStatement(GET_GRADOS_CARRERA);
        ps.setString(1, codigoCarrera);
        ResultSet rsGrado = ps.executeQuery();
        while (rsGrado.next()) {
            grados.add(armarGrado(rsGrado));
        }
        
        return new CarreraDB(
                codigoCarrera, 
                rs.getString("nombre"), 
                rs.getBoolean("estado"), 
                grados);
    }
    
}
