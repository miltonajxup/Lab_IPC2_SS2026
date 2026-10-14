/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs.Estudiante;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.ArmarBoletaPago;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.ArmarEstudiante;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Bibliotecario.MultaDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Bibliotecario.PrestamoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.CicloEscolar;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.BoletaPagoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.EstudianteDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.InformacionMedicaDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.LibroPrestamoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.MultaDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.NotaDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.PagoBoletaDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.PrestamoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.InformacionMedicaRequest;
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
public class EstudianteDAO {
    
    private final String GET_ESTUDIANTE_POR_DPI = 
            """
            SELECT usu.*, est.* FROM usuario AS usu 
            JOIN estudiante AS est ON usu.dpi = est.dpi_estudiante 
            WHERE usu.dpi = ?""";
    private final String NOTAS_ESTUDIANTE = 
            """
            SELECT cur.nombre AS nombre_curso, notas.* FROM notas 
            JOIN curso_estudiante AS cures ON notas.curso_estudiante_id = cures.id 
            JOIN curso AS cur ON cures.curso_id = cur.id 
            JOIN inscripcion AS ins ON cures.inscripcion_id = ins.id 
            JOIN grupo_ciclo_escolar AS grup ON ins.grupo_id = grup.id 
            WHERE cures.estudiante_id = ?""";
    private final String MODIFICAR_INFORMACION_MEDICA = "UPDATE informacion_medica SET condicion_medica = ?, alergias = ?, medicamentos = ? WHERE dpi_estudiante = ?";
    private final String GET_INFORMACION_POR_ESTUDIANTE = "SELECT * FROM informacion_medica WHERE dpi_estudiante = ?";
    private final String PRESTAMOS_PENDIENTES = "SELECT * FROM prestamo_biblioteca WHERE fecha_devolucion_completada IS NULL AND prestamista = ?";
    private final String MULTAS_PENDIENTES = 
            """
            SELECT mul.* FROM multa AS mul 
            JOIN prestamo_biblioteca AS pres ON mul.prestamo_id = pres.id 
            WHERE mul.estado = FALSE AND pres.prestamista = ?""";
    private final String GET_BOLETAS_DE_PAGO = 
            """
            SELECT bol.*, usu.nombre AS nombre_estudiante 
            FROM boleta_pago AS bol 
            JOIN usuario AS usu ON bol.estudiante_dpi = usu.dpi 
            WHERE ciclo_escolar_id = ? AND estudiante_dpi = ?""";
    private final String GET_FACTURAS_DE_PAGOS = 
            """
            SELECT pago.id AS id_pago, bol.*, usuEs.nombre AS nombre_estudiante, pago.fecha_pago, usu.nombre AS nombre_secretaria 
            FROM boleta_pago AS bol JOIN pago ON bol.id = pago.boleta_id 
            JOIN usuario AS usuEs ON bol.estudiante_dpi = usuEs.dpi 
            JOIN usuario AS usu ON pago.secretaria_dpi = usu.dpi 
            WHERE bol.estudiante_dpi = ?""";
    
    public EstudianteDB getEstudiantePorID(String dpiEstudiante) throws AccesoALaDataException {
        ArmarEstudiante armarEst = new ArmarEstudiante();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_ESTUDIANTE_POR_DPI)) {
            ps.setString(1, dpiEstudiante);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return armarEst.estudiante(rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar un usuario por dpi " + e.getMessage());
        }
        return null;
    }
    
    public List<NotaDB> getNotas(String idEstudiante) throws AccesoALaDataException {
        List<NotaDB> notas = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(NOTAS_ESTUDIANTE)) {
            ps.setString(1, idEstudiante);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    notas.add(armarNota(rs));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar las notas de un estudiante: " + e.getMessage());
        }
        return notas;
    }
    
    public InformacionMedicaDB modificarInformacionMedica(InformacionMedicaRequest info, String idEstudiante) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(MODIFICAR_INFORMACION_MEDICA)) {
            ps.setString(1, info.getCondicionMedica());
            ps.setString(2, info.getAlergias());
            ps.setString(3, info.getMedicamentos());
            ps.setString(4, idEstudiante);
            ps.executeUpdate();
            
            return informacionPorEstudiante(idEstudiante);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar la informacion de medica: " + e.getMessage());
        }
    }
    
    public InformacionMedicaDB informacionPorEstudiante(String idEstudiante) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection(); 
                PreparedStatement ps = connection.prepareStatement(GET_INFORMACION_POR_ESTUDIANTE)) {
            ps.setString(1, idEstudiante);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new InformacionMedicaDB(rs.getString("condicion_medica"), rs.getString("alergias"), rs.getString("medicamentos"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar la informacion medica de un estudiante: " + e.getMessage());
        }
    }
    
    public List<PrestamoDB> getPrestamosPendientes(String idEstudiante) throws AccesoALaDataException {
        List<PrestamoDB> prestamos = new ArrayList<>();
        PrestamoDAO prestamodao = new PrestamoDAO();
        
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(PRESTAMOS_PENDIENTES)) {
            ps.setString(1, idEstudiante);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    List<LibroPrestamoDB> libros = prestamodao.getLibrosPrestamo(connection, rs.getInt("id"));
                    prestamos.add(prestamodao.armarPrestamo(libros, rs));
                }
            }
        } catch (SQLException | AccesoALaDataException e) {
            throw new AccesoALaDataException("Error al buscar los prestamos pendientes de devolver: " + e.getMessage());
        }
        return prestamos;
    }
    
    public List<MultaDB> getMultasPendientes(String idEstudiante) throws AccesoALaDataException {
        List<MultaDB> multas = new ArrayList<>();
        MultaDAO multadao = new MultaDAO();
        
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(MULTAS_PENDIENTES)) {
            ps.setString(1, idEstudiante);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    multas.add(multadao.armarMulta(rs));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar las multas pendientes de pagar: " + e.getMessage());
        }
        return multas;
    }
    
    public List<BoletaPagoDB> getBoletasDelAño(String idEstudiante) throws AccesoALaDataException {
        List<BoletaPagoDB> boletas = new ArrayList<>();
        CicloEscolar ciclo = new CicloEscolar();
        ArmarBoletaPago armarBoleta = new ArmarBoletaPago();
        int año = ciclo.getAñoActivo();
        
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_BOLETAS_DE_PAGO)) {
            ps.setInt(1, año);
            ps.setString(2, idEstudiante);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    boletas.add(armarBoleta.armar(rs));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar las boletas de pago de un estudiante: " + e.getMessage());
        }
        return boletas;
    }
    
    public List<PagoBoletaDB> getPagoRealizados(String idEstudiante) throws AccesoALaDataException {
        List<PagoBoletaDB> pagos = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_FACTURAS_DE_PAGOS)) {
            ps.setString(1, idEstudiante);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pagos.add(armarPagoBoleta(rs));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los pagos realizados de un estudiante: " + e.getMessage());
        }
        return pagos;
    }
    
    private NotaDB armarNota(ResultSet rs) throws SQLException {
        return new NotaDB(
                rs.getString("nombre_curso"), 
                rs.getInt("id"), 
                rs.getInt("zona"), 
                rs.getInt("examen_final"), 
                rs.getInt("curso_estudiante_id"));
    }
    
    private PagoBoletaDB armarPagoBoleta(ResultSet rs) throws SQLException {
        return new PagoBoletaDB(
                rs.getInt("id_pago"), 
                rs.getInt("id"), 
                rs.getDouble("monto"), 
                rs.getBoolean("estado"), 
                rs.getString("tipo_pago"), 
                rs.getString("mes_pago"), 
                rs.getInt("ciclo_escolar_id"), 
                rs.getString("estudiante_dpi"), 
                rs.getString("nombre_estudiante"), 
                rs.getDate("fecha_pago").toLocalDate(), 
                rs.getString("nombre_secretaria"));
    }
    
}
