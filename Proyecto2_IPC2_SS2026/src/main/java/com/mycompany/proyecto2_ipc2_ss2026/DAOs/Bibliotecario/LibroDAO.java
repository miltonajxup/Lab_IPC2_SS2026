/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs.Bibliotecario;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.LibroDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.LibroRequest;
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
public class LibroDAO {
    
    private final String LIBRO_POR_ID = "SELECT * FROM libro WHERE codigo_isbn = ?";
    private final String AGREGAR_LIBRO = "INSERT INTO libro (codigo_isbn, titulo, autor, editorial, cantidad_disponible) VALUES (?,?,?,?,?)";
    private final String MODIFICAR_LIBRO = "UPDATE libro SET titulo = ?, autor = ?, editorial = ? WHERE codigo_isbn = ?";
    private final String MODIFICAR_CANTIDAD_LIBROS = "UPDATE libro SET cantidad_disponible = ? WHERE codigo_isbn = ?";
    private final String GET_LIBROS_BIBLIOTECA = "SELECT * FROM libro ORDER BY titulo ASC LIMIT ?, ?";
    
    public boolean existeCodigoLibro(String codigo) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(LIBRO_POR_ID)) {
            ps.setString(1, codigo);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al ver la existencia del codigo de un libro: " + e.getMessage());
        }
    }
    
    public void agregarLibro(LibroRequest libro) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(AGREGAR_LIBRO)) {
            ps.setString(1, libro.getCodigo());
            ps.setString(2, libro.getTitulo());
            ps.setString(3, libro.getAutor());
            ps.setString(4, libro.getEditorial());
            ps.setInt(5, libro.getCantidadDisponible());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar un libro: " + e.getMessage());
        }
    }
    
    public LibroDB modificarLibro(LibroRequest libro, String codigo) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(MODIFICAR_LIBRO)) {
            ps.setString(1, libro.getTitulo());
            ps.setString(2, libro.getAutor());
            ps.setString(3, libro.getEditorial());
            ps.setString(4, codigo);
            ps.executeUpdate();
            
            return getLibroPorId(libro.getCodigo());
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar un libro: " + e.getMessage());
        }
    }
    
    public void modificarCantidadLibros(int cantidadLibros, String codigoLibro) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection()) {
            modificarCantidadLibros(connection, cantidadLibros, codigoLibro);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar la cantidad de libros: " + e.getMessage());
        }
    }
    
    public void modificarCantidadLibros(Connection connection, int cantidadLibros, String codigoLibro) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(MODIFICAR_CANTIDAD_LIBROS)) {
            ps.setInt(1, cantidadLibros);
            ps.setString(2, codigoLibro);
            ps.executeUpdate();
        }
    }
    
    public LibroDB getLibroPorId(String codigoLibro) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(LIBRO_POR_ID)) {
            ps.setString(1, codigoLibro);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return armarLibro(rs);
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar un libro por su ID: " + e.getMessage());
        }
        return null;
    }
    
    public List<LibroDB> getTodosLosLibros(int pagina) throws AccesoALaDataException {
        int paginacion = (pagina - 1) * LimitePagina.LIMITE;
        List<LibroDB> libros = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_LIBROS_BIBLIOTECA)) {
            ps.setInt(1, paginacion);
            ps.setInt(2, LimitePagina.LIMITE);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    libros.add(armarLibro(rs));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los libros de biblioteca: " + e.getMessage());
        }
        return libros;
    }
    
    private LibroDB armarLibro(ResultSet rs) throws SQLException {
        return new LibroDB(
                rs.getString("codigo_isbn"), 
                rs.getString("titulo"), 
                rs.getString("autor"), 
                rs.getString("editorial"), 
                rs.getInt("cantidad_disponible"));
    }
    
}
