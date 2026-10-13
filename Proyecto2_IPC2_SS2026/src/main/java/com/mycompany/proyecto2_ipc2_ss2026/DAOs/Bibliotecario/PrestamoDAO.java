/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs.Bibliotecario;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.LibroDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.PrestamoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.LibroPrestamoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.LibroPrestamoRequest;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.PrestamoRequest;
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
public class PrestamoDAO {
    
    private final String GENERAR_PRESTAMO = "INSERT INTO prestamo_biblioteca (fecha_prestamo, fecha_devolucion_esperada, prestamista) VALUES (?,?,?)";
    private final String GET_ULTIMO_ID = "SELECT * FROM prestamo_biblioteca ORDER BY id DESC LIMIT 1";
    private final String AGREGAR_LIBRO_PRESTAMO = "INSERT INTO libro_prestamo (id_prestamo, codigo_libro, cantidad) VALUES (?,?,?)";
    private final String CONCLUIR_PRESTAMO = "UPDATE prestamo_biblioteca SET fecha_devolucion_completada = ? WHERE id = ?";
    private final String GET_PRESTAMO_POR_ID = "SELECT * FROM prestamo_biblioteca WHERE id = ?";
    private final String GET_PRESTAMO_POR_NOMBRE = 
            """
            SELECT pres.* 
            FROM prestamo_biblioteca AS pres 
            JOIN usuario AS usu ON pres.prestamista = usu.dpi 
            WHERE fecha_devolucion_completada IS NULL AND usu.nombre LIKE CONCAT(?, '%')""";
    private final String CONSULTAR_PRESTAMOS_SIN_TERMINAR = "SELECT * FROM prestamo_biblioteca WHERE fecha_devolucion_completada IS NULL LIMIT ?, ?";
    private final String GET_TODOS_LOS_REGISTROS = "SELECT * FROM prestamo_biblioteca ORDER BY id DESC LIMIT ?, ?";
    private final String GET_LIBROS_PRESTAMO = "SELECT * FROM libro_prestamo WHERE id_prestamo = ?";
    
    private final LibroDAO librodao;
    
    public PrestamoDAO() {
        librodao = new LibroDAO();
    }
    
    public void generarPrestamo(PrestamoRequest prestamo) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection()) {
            connection.setAutoCommit(false); 
            
            try {
                try (PreparedStatement ps = connection.prepareStatement(GENERAR_PRESTAMO)) {
                    Date datePrestamo = Date.valueOf(prestamo.getFechaPrestamo());
                    Date dateDevolucion = Date.valueOf(prestamo.getFechaDevolucionEsperada());
                    ps.setDate(1, datePrestamo);
                    ps.setDate(2, dateDevolucion);
                    ps.setString(3, prestamo.getPrestamista());
                    ps.executeUpdate();
                }

                try (PreparedStatement psId = connection.prepareStatement(GET_ULTIMO_ID)) {
                    try (ResultSet rs = psId.executeQuery()) {
                        if (rs.next()) {
                            int idPrestamo = rs.getInt("id");
                            modificarLibrosPrestamo(connection, idPrestamo, prestamo);
                        } else {
                            throw new AccesoALaDataException("No se pudo encontrar el ID del Prestamo");
                        }
                    }
                }

                connection.commit();
            } catch (SQLException | AccesoALaDataException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new AccesoALaDataException("Error al hacer rollback de un prestamo: " + ex.getMessage() + ". \nError original: " + e.getMessage());
                }
                throw new AccesoALaDataException("Error al generar un prestamo de libro: " + e.getMessage());
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new AccesoALaDataException("Error al devolver el auto commit al generar al generar un prestamo " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error de conexion al Generar un Prestamo de Libro: " + e.getMessage());
        }
    }
    
    private void modificarLibrosPrestamo(Connection connection, int idPrestamo, PrestamoRequest prestamo) throws AccesoALaDataException {
        try {
            PreparedStatement ps = connection.prepareStatement(AGREGAR_LIBRO_PRESTAMO);
            for (LibroPrestamoRequest libro : prestamo.getLibrosPrestamo()) {
                ps.setInt(1, idPrestamo);
                ps.setString(2, libro.getCodigo());
                ps.setInt(3, libro.getCantidad());
                ps.executeUpdate();
                
                LibroDB librodb = librodao.getLibroPorId(libro.getCodigo());
                int cantidadActual = librodb.getCantidadDisponible() - libro.getCantidad();
                librodao.modificarCantidadLibros(connection, cantidadActual, librodb.getCodigo());
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al generar un registro de libro_prestamo: " + e.getMessage());
        }
    }
    
    public void concluirPrestamo(PrestamoRequest prestamo, int idPrestamo) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection()) {
            connection.setAutoCommit(false);

            try {
                try (PreparedStatement ps = connection.prepareStatement(CONCLUIR_PRESTAMO)) {
                    Date dateFinal = Date.valueOf(prestamo.getFechaDevolucionCompletada());
                    ps.setDate(1, dateFinal);
                    ps.setInt(2, idPrestamo);
                    ps.executeUpdate();
                }

                List<LibroPrestamoDB> libros = getLibrosPrestamo(connection, idPrestamo);
                devolverLibros(connection, libros);

                connection.commit();
            } catch (SQLException | AccesoALaDataException e) {
                try {
                    connection.rollback();
                } catch (SQLException ex) {
                    throw new AccesoALaDataException("Error al hacer rollback de una devolucion de libro: " + ex.getMessage() + ". \nOrigen: " + e.getMessage());
                }
                throw new AccesoALaDataException("Error al concluir un prestamo: " + e.getMessage());
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    throw new AccesoALaDataException("Error al devolver el auto commit al devolver un libro " + e.getMessage());
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error de conexion al concluir un prestamo: " + e.getMessage());
        }
    }
    
    private void devolverLibros(Connection connection, List<LibroPrestamoDB> libros) throws AccesoALaDataException, SQLException {
        for (LibroPrestamoDB libro : libros) {
            LibroDB actual = librodao.getLibroPorId(libro.getCodigo());
            int cantidadActual = actual.getCantidadDisponible() + libro.getCantidad();
            librodao.modificarCantidadLibros(connection, cantidadActual, libro.getCodigo());
        }
    }
    
    public boolean existePrestamo(int id) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_PRESTAMO_POR_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al revisar la existencia de un prestamo: " + e.getMessage());
        }
    }
    
    public PrestamoDB prestamoPorId(int id) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_PRESTAMO_POR_ID)) {
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    List<LibroPrestamoDB> libros = getLibrosPrestamo(connection, id);
                    return armarPrestamo(libros, rs);
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al revisar la existencia de un prestamo: " + e.getMessage());
        }
        return null;
    }
    
    public List<PrestamoDB> getPrestamoPorNombre(String nombrePrestamista) throws AccesoALaDataException {
        List<PrestamoDB> prestamos = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_PRESTAMO_POR_NOMBRE)) {
            ps.setString(1, nombrePrestamista);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    List<LibroPrestamoDB> libros = getLibrosPrestamo(connection, rs.getInt("id"));
                    prestamos.add(armarPrestamo(libros, rs));
                }
            }
        } catch (SQLException | AccesoALaDataException e) {
            throw new AccesoALaDataException("Error al buscar un prestamo por el prestamista: " + e.getMessage());
        }
        return prestamos;
    }
    
    public List<PrestamoDB> getPrestamosSinTerminar(int numeroPagina) throws AccesoALaDataException {
        int inicioPaginacion = (numeroPagina - 1) * LimitePagina.LIMITE;
        List<PrestamoDB> prestamos = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(CONSULTAR_PRESTAMOS_SIN_TERMINAR)) {
            ps.setInt(1, inicioPaginacion);
            ps.setInt(2, LimitePagina.LIMITE);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    List<LibroPrestamoDB> libros = getLibrosPrestamo(connection, rs.getInt("id"));
                    prestamos.add(armarPrestamo(libros, rs));
                }
            }
        } catch (SQLException | AccesoALaDataException e) {
            throw new AccesoALaDataException("Error al consultar los prestamos sin terminar: " + e.getMessage());
        }
        return prestamos;
    }
    
    public List<PrestamoDB> getTodosLosRegistros(int numeroPagina) throws AccesoALaDataException {
        int inicioPaginacion = (numeroPagina - 1) * LimitePagina.LIMITE;
        List<PrestamoDB> prestamos = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_TODOS_LOS_REGISTROS)) {
            ps.setInt(1, inicioPaginacion);
            ps.setInt(2, LimitePagina.LIMITE);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    List<LibroPrestamoDB> libros = getLibrosPrestamo(connection, rs.getInt("id"));
                    prestamos.add(armarPrestamo(libros, rs));
                }
            }
        } catch (SQLException | AccesoALaDataException e) {
            throw new AccesoALaDataException("Error al consultar todos los prestamos: " + e.getMessage());
        }
        return prestamos;
    }
    
    private PrestamoDB armarPrestamo(List<LibroPrestamoDB> libros, ResultSet rs) throws SQLException, AccesoALaDataException {
        String fechaCompletada = rs.getString("fecha_devolucion_completada");
        
        boolean estado = fechaCompletada != null;
        
        return new PrestamoDB(
                rs.getInt("id"), 
                rs.getDate("fecha_prestamo").toLocalDate(), 
                rs.getDate("fecha_devolucion_esperada").toLocalDate(), 
                fechaCompletada, 
                rs.getString("prestamista"), 
                estado, 
                libros);
    }
    
    private List<LibroPrestamoDB> getLibrosPrestamo(Connection connection, int idPrestamo) throws AccesoALaDataException {
        List<LibroPrestamoDB> libros = new ArrayList<>();
        
        try {
            PreparedStatement ps = connection.prepareStatement(GET_LIBROS_PRESTAMO);
            ps.setInt(1, idPrestamo);
            ResultSet rsPres = ps.executeQuery();
            while (rsPres.next()) {
                libros.add(new LibroPrestamoDB( 
                                rsPres.getString("codigo_libro"),
                                rsPres.getInt("cantidad"))
                );
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los detalles de un prestamo: " + e.getMessage());
        }
        return libros;
    }
    
}
