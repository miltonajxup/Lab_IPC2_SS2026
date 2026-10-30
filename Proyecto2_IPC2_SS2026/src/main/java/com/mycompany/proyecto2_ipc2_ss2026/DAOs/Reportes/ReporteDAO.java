/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs.Reportes;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.IngresoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.PagoBoletaDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.PromedioCursoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.SolvenciaEstudiante;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author milton
 */
public class ReporteDAO {
    
    private final String ESTUDIANTES_GRUPO = 
            """
            SELECT usu.dpi, usu.nombre 
            FROM inscripcion AS ins 
            JOIN grupo_ciclo_escolar AS grup ON ins.grupo_id = grup.id 
            JOIN usuario AS usu ON ins.estudiante = usu.dpi 
            WHERE grup.id = ? LIMIT ?, ?""";
    private final String BOLETAS_ESTUDIANTE = 
            """
            SELECT * FROM boleta_pago WHERE estudiante_dpi = ?""";
    private final String PROMEDIO_ESTUDIANTES = 
            """
            SELECT 
                cur.nombre AS nombre_curso, 
                AVG(zona) AS promedio_zona, 
                AVG(examen_final) AS promedio_examen_final, 
                AVG(zona + examen_final) AS promedio_total 
            FROM curriculo AS curr 
            JOIN curso AS cur ON curr.curso_id = cur.id 
            JOIN grado AS gra ON curr.grado_id = gra.id 
            JOIN grupo_ciclo_escolar AS grup ON gra.id = grup.grado_id 
            JOIN inscripcion AS ins ON grup.id = ins.grupo_id 
            JOIN curso_estudiante AS cures ON cur.id = cures.curso_id 
            JOIN notas ON cures.id = notas.curso_estudiante_id 
            WHERE grup.id = ? GROUP BY cur.id ORDER BY promedio_total ASC""";
    private final String HISTORIAL_PAGOS_ESTUDIANTE = 
            """
            SELECT bol.*, pago.id AS id_pago, pago.fecha_pago, usu.nombre AS nombre_estudiante, usuSec.nombre AS nombre_secretaria 
            FROM boleta_pago AS bol 
            LEFT JOIN pago ON bol.id = pago.boleta_id 
            JOIN usuario AS usu ON bol.estudiante_dpi = usu.dpi 
            LEFT JOIN usuario AS usuSec ON pago.secretaria_dpi = usuSec.dpi 
            WHERE bol.estudiante_dpi = ? """;
    private final String HISTORIAL_PAGOS_ESTUDIANTE_SIN_FECHA = HISTORIAL_PAGOS_ESTUDIANTE + "LIMIT ?, ?";
    private final String HISTORIAL_PAGOS_ESTUDIANTE_CON_FECHA = HISTORIAL_PAGOS_ESTUDIANTE + " AND pago.fecha_pago BETWEEN ? AND ? LIMIT ?, ?";
    private final String PAGOS_REALIZADOS = 
            """
            SELECT bol.tipo_pago, SUM(bol.monto) AS total_ingreso 
            FROM pago 
            JOIN boleta_pago AS bol ON pago.boleta_id = bol.id  """;
    private final String PAGOS_REALIZADOS_SIN_FECHAS = PAGOS_REALIZADOS + " GROUP BY bol.tipo_pago ";
    private final String PAGOS_REALIZADOS_CON_FECHAS = PAGOS_REALIZADOS + " WHERE pago.fecha_pago BETWEEN ? AND ? GROUP BY bol.tipo_pago ";
    private final String PAGOS_MULTA = "SELECT COALESCE(SUM(monto), 0) AS total_ingreso FROM multa WHERE estado = TRUE ";
    private final String PAGOS_MULTA_FECHAS = PAGOS_MULTA + " AND fecha_pago BETWEEN ? AND ?";
    
    public List<SolvenciaEstudiante> getSolvencias(int idGrupo, int pagina) throws AccesoALaDataException {
        List<SolvenciaEstudiante> solvencias = new ArrayList<>();
        int paginacion = (pagina - 1) * LimitePagina.LIMITE;
        
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement psG = connection.prepareStatement(ESTUDIANTES_GRUPO)) {
            psG.setInt(1, idGrupo);
            psG.setInt(2, paginacion);
            psG.setInt(3, LimitePagina.LIMITE);
            try (ResultSet rsG = psG.executeQuery()) {
                while (rsG.next()) {
                    String idEstudiante = rsG.getString("dpi");
                    String nombre = rsG.getString("nombre");
                    solvencias.add(armarSolvencia(connection, idEstudiante, nombre));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar la solvencia de estudiantes: " + e.getMessage());
        }
        return solvencias;
    }
    
    private SolvenciaEstudiante armarSolvencia(Connection connection, String idEstudiante, String nombre) throws SQLException {
        int contadorPendiente = 0;
        int contadorPagado = 0;
        try (PreparedStatement ps = connection.prepareCall(BOLETAS_ESTUDIANTE)) {
            ps.setString(1, idEstudiante);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getBoolean("estado")) {
                    contadorPagado++;
                } else {
                    contadorPendiente++;
                }
            }
        }
        String estado;
        if (contadorPendiente > 0) {
            estado = "MOROSO";
        } else {
            estado = "SOLVENTE";
        }
        return new SolvenciaEstudiante(
                idEstudiante, 
                nombre, 
                contadorPagado + contadorPendiente, 
                contadorPagado, 
                contadorPendiente, 
                estado);
    }
    
    public List<PromedioCursoDB> getPromedioCursos(int idGrupo) throws AccesoALaDataException {
        List<PromedioCursoDB> cursos = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(PROMEDIO_ESTUDIANTES)) {
            ps.setInt(1, idGrupo);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cursos.add(
                            new PromedioCursoDB(
                                    rs.getString("nombre_curso"), 
                                    rs.getDouble("promedio_zona"), 
                                    rs.getDouble("promedio_examen_final"), 
                                    rs.getDouble("promedio_total")));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar el promedio de cursos: " + e.getMessage());
        }
        return cursos;
    }
    
    public List<PagoBoletaDB> getPagosPorEstudianteSinFecha(String idUsuario, int pagina) throws AccesoALaDataException {
        List<PagoBoletaDB> pagos = new ArrayList<>();
        int paginacion = (pagina - 1) * LimitePagina.LIMITE;
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(HISTORIAL_PAGOS_ESTUDIANTE_SIN_FECHA)) {
            ps.setString(1, idUsuario);
            ps.setInt(2, paginacion);
            ps.setInt(3, LimitePagina.LIMITE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pagos.add(armarPagoBoleta(rs));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar todos los pagos de un estudiante: " + e.getMessage());
        }
        return pagos;
    }
    
    public List<PagoBoletaDB> getPagosPorEstudianteConFecha(
            String idUsuario, LocalDate fechaInicio, LocalDate fechaFin, int pagina) throws AccesoALaDataException {
        
        
        List<PagoBoletaDB> pagos = new ArrayList<>();
        
        Date inicio = Date.valueOf(fechaInicio);
        Date fin = Date.valueOf(fechaFin);
        int paginacion = (pagina - 1) * LimitePagina.LIMITE;
        
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(HISTORIAL_PAGOS_ESTUDIANTE_CON_FECHA)) {
            ps.setString(1, idUsuario);
            ps.setDate(2, inicio);
            ps.setDate(3, fin);
            ps.setInt(4, paginacion);
            ps.setInt(5, LimitePagina.LIMITE);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pagos.add(armarPagoBoleta(rs));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los pagos por estudiante: " + e.getMessage());
        }
        return pagos;
    }
    
    public List<IngresoDB> getIngresosSinFechas() throws AccesoALaDataException {
        List<IngresoDB> ingresos = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(PAGOS_REALIZADOS_SIN_FECHAS); 
                PreparedStatement psM = connection.prepareStatement(PAGOS_MULTA)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ingresos.add(new IngresoDB(rs.getString("tipo_pago"), rs.getDouble("total_ingreso")));
                }
            }
            try (ResultSet rs = psM.executeQuery()) {
                if (rs.next()) {
                    ingresos.add(new IngresoDB("MULTA_BIBLIOTECA", rs.getDouble("total_ingreso")));
                }
            }
            ingresos.add(new IngresoDB("ACTIVIDAD", 0));
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar todos ingresos: " + e.getMessage());
        }
        return ingresos;
    }
    
    public List<IngresoDB> getIngresosConFechas(LocalDate fechaInicio, LocalDate fechaFin) throws AccesoALaDataException {
        List<IngresoDB> ingresos = new ArrayList<>();
        Date inicio = Date.valueOf(fechaInicio);
        Date fin = Date.valueOf(fechaFin);
        
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(PAGOS_REALIZADOS_CON_FECHAS); 
                PreparedStatement psM = connection.prepareStatement(PAGOS_MULTA_FECHAS)) {
            ps.setDate(1, inicio);
            ps.setDate(2, fin);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ingresos.add(new IngresoDB(rs.getString("tipo_pago"), rs.getDouble("total_ingreso")));
                }
            }
            psM.setDate(1, inicio);
            psM.setDate(2, fin);
            try (ResultSet rs = psM.executeQuery()) {
                if (rs.next()) {
                    ingresos.add(new IngresoDB("MULTA_BIBLIOTECA", rs.getDouble("total_ingreso")));
                }
            }
            ingresos.add(new IngresoDB("ACTIVIDAD", 0));
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar todos ingresos: " + e.getMessage());
        }
        return ingresos;
    }
    
    
    private PagoBoletaDB armarPagoBoleta(ResultSet rs) throws SQLException {
        LocalDate fecha = null; 
        if (rs.getDate("fecha_pago") != null) {
            fecha = rs.getDate("fecha_pago").toLocalDate();
        }
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
                fecha, 
                rs.getString("nombre_secretaria"));
    }
    
    
}
