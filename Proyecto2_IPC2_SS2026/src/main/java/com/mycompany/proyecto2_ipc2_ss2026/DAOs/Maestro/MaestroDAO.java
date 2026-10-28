/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs.Maestro;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.CicloEscolar;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.AsistenciaDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.CursoMaestro;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.EstudianteCurso;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.NotaDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.AsistenciaDiaRequest;
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
public class MaestroDAO {
    
    private final String CONSULTAR_CURSOS_MAESTRO = 
            """
            SELECT 
                curmae.id AS id_curso_maestro, 
                cur.id AS id_curso, 
                cur.nombre AS nombre_curso, 
                grup.id AS id_grupo, 
                gra.nombre AS nombre_grado, 
                niv.nombre AS nombre_nivel, 
                grup.seccion_id, 
                grup.ciclo_escolar_id 
            FROM curso_maestro AS curmae 
            JOIN curso AS cur ON curmae.curso_id = cur.id 
            JOIN grupo_ciclo_escolar AS grup ON curmae.grupo_id = grup.id 
            JOIN grado AS gra ON grup.grado_id = gra.id 
            JOIN nivel_academico AS niv ON gra.nivel_id = niv.id 
            WHERE grup.ciclo_escolar_id = ? AND curmae.maestro_id = ? """;
    private final String GET_CURSO_MAESTRO_POR_ID = 
            """
            SELECT 
                curmae.id AS id_curso_maestro, 
                cur.id AS id_curso, 
                cur.nombre AS nombre_curso, 
                grup.id AS id_grupo, 
                gra.nombre AS nombre_grado, 
                niv.nombre AS nombre_nivel, 
                grup.seccion_id, 
                grup.ciclo_escolar_id 
            FROM curso_maestro AS curmae 
            JOIN curso AS cur ON curmae.curso_id = cur.id 
            JOIN grupo_ciclo_escolar AS grup ON curmae.grupo_id = grup.id 
            JOIN grado AS gra ON grup.grado_id = gra.id 
            JOIN nivel_academico AS niv ON gra.nivel_id = niv.id 
            WHERE curmae.id = ? AND curmae.maestro_id = ?""";
    private final String ESTUDIANTES_CURSO_MAESTRO = 
            """
            SELECT usu.dpi, usu.nombre, cures.id AS id_curso_estudiante 
            FROM curso_estudiante AS cures 
            JOIN inscripcion AS ins ON cures.inscripcion_id = ins.id 
            JOIN grupo_ciclo_escolar AS grup ON ins.grupo_id = grup.id 
            JOIN usuario AS usu ON cures.estudiante_id = usu.dpi 
            WHERE grup.id = ? AND cures.curso_id = ? ORDER BY usu.dpi ASC""";
    
    private final String ASISTENCIA_TOMADA_DIA = 
            """
            SELECT asi.fecha FROM asistencia AS asi 
            JOIN curso_estudiante AS cures ON asi.curso_estudiante_id = cures.id 
            JOIN inscripcion AS ins ON cures.inscripcion_id = ins.id 
            JOIN grupo_ciclo_escolar AS grup ON ins.grupo_id = grup.id 
            WHERE grup.id = ? AND cures.curso_id = ? AND asi.fecha = ? GROUP BY asi.fecha""";
    private final String DIAS_TOMADOS_ASISTENCIA = 
            """
            SELECT asi.fecha FROM asistencia AS asi 
            JOIN curso_estudiante AS cures ON asi.curso_estudiante_id = cures.id 
            JOIN inscripcion AS ins ON cures.inscripcion_id = ins.id 
            JOIN grupo_ciclo_escolar AS grup ON ins.grupo_id = grup.id 
            WHERE grup.id = ? AND cures.curso_id = ? GROUP BY asi.fecha""";
    private final String EXISTE_ASISENCIA = "SELECT * FROM asistencia WHERE curso_estudiante_id = ? AND fecha = ?";
    private final String GENERAR_ASISTENCIAS = "INSERT asistencia (curso_estudiante_id, fecha) VALUES (?,?)";
    private final String GET_ASISTENCIAS_DEL_DIA = 
            """
            SELECT asi.*, usu.nombre FROM asistencia AS asi 
            JOIN curso_estudiante AS cures ON asi.curso_estudiante_id = cures.id 
            JOIN inscripcion AS ins ON cures.inscripcion_id = ins.id 
            JOIN grupo_ciclo_escolar AS grup ON ins.grupo_id = grup.id 
            JOIN usuario AS usu ON ins.estudiante = usu.dpi 
            WHERE grup.id = ? AND curso_id = ? AND asi.fecha = ?""";
    private final String TOMAR_ASISTENCIA = "UPDATE asistencia SET estado = ? WHERE id = ?";
    private final String GET_REGISTRO_ASISTENCIA = "SELECT * FROM asistencia WHERE id = ?";
    private final String FORMATO_NOTAS = 
            """
            SELECT usu.nombre AS nombre_estudiante, notas.* FROM notas 
            JOIN curso_estudiante AS cures ON notas.curso_estudiante_id = cures.id 
            JOIN usuario AS usu ON cures.estudiante_id = usu.dpi 
            JOIN inscripcion AS ins ON cures.inscripcion_id = ins.id 
            JOIN grupo_ciclo_escolar AS grup ON ins.grupo_id = grup.id """;
    private final String GET_NOTAS_CURSO = FORMATO_NOTAS + " WHERE  grup.id = ? AND cures.curso_id = ?";
    private final String GET_NOTA_POR_ID = FORMATO_NOTAS + " WHERE notas.id = ?";
    private final String MODIFICAR_ZONA = "UPDATE notas SET zona = ? WHERE id = ?";
    private final String MODIFICAR_FINAL = "UPDATE notas SET examen_final = ? WHERE id = ?";
    
    public List<CursoMaestro> getCursosMaestro(String dpiMaestro) throws AccesoALaDataException {
        CicloEscolar ciclo = new CicloEscolar();
        int año = ciclo.getAñoActivo();
        List<CursoMaestro> cursos = new ArrayList<>();
        
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(CONSULTAR_CURSOS_MAESTRO)) {
            ps.setInt(1, año);
            ps.setString(2, dpiMaestro);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cursos.add(armarCursoMaestro(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los cursos de que tiene asinado un maestro");
        }
        return cursos;
    }
    
    public CursoMaestro getCursosMaestroPorId(int id, String idMaestro) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_CURSO_MAESTRO_POR_ID)) {
            ps.setInt(1, id);
            ps.setString(2, idMaestro);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return armarCursoMaestro(rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los cursos de que tiene asinado un maestro");
        }
        return null;
    }
    
    public List<EstudianteCurso> getEstudiantesCurso(int idGrupo, int curso) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection()) {
            return getEstudiantesCurso(connection, idGrupo, curso);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los estudiantes de un curso: " + e.getMessage());
        }
    }
    
    public List<EstudianteCurso> getEstudiantesCurso(Connection connection, int idGrupo, int curso) throws SQLException, AccesoALaDataException {
        List<EstudianteCurso> estudiantes = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(ESTUDIANTES_CURSO_MAESTRO)) {
            ps.setInt(1, idGrupo);
            ps.setInt(2, curso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                estudiantes.add(armarEstudiante(rs));
            }
        }
        return estudiantes;
    }
    
    
    public boolean asistenciaTomadaDia(int idGrupo, int idCurso, LocalDate fecha) throws AccesoALaDataException {
        Date date = Date.valueOf(fecha);
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(ASISTENCIA_TOMADA_DIA)) {
            ps.setInt(1, idGrupo);
            ps.setInt(2, idCurso);
            ps.setDate(3, date);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al revisar la existencia de fechas tomadas en un dia: " + e.getMessage());
        }
    }
    
    public List<LocalDate> fechasTomadasAsitencia(int idGrupo, int idCurso) throws AccesoALaDataException {
        List<LocalDate> fechas = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(DIAS_TOMADOS_ASISTENCIA)) {
            ps.setInt(1, idGrupo);
            ps.setInt(2, idCurso);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    fechas.add(rs.getDate("fecha").toLocalDate());
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar las fechas tomadas asistencia: " + e.getMessage());
        }
        return fechas;
    }
    
    public List<AsistenciaDB> generarAsistencias(AsistenciaDiaRequest asistencia) throws AccesoALaDataException {
        Date date = Date.valueOf(asistencia.getFecha());
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection()) {
            List<EstudianteCurso> estudiantes = getEstudiantesCurso(connection, asistencia.getIdGrupoEstudiantes(), asistencia.getIdCurso());
            for (EstudianteCurso estudiante : estudiantes) {
                
                if (!existeAsistencia(connection, estudiante.getIdCursoEstudiante(), date)) {
                    try (PreparedStatement ps = connection.prepareStatement(GENERAR_ASISTENCIAS)) {
                        ps.setInt(1, estudiante.getIdCursoEstudiante());
                        ps.setDate(2, date);
                        ps.executeUpdate();
                    }
                }
            }
            return getAsistenciasDelDia(asistencia.getIdGrupoEstudiantes(), asistencia.getIdCurso(), asistencia.getFecha());
        } catch (SQLException | AccesoALaDataException e) {
            throw new AccesoALaDataException("Error al generar las asistencias: " + e.getMessage());
        }
    }
    
    private boolean existeAsistencia(Connection connection, int cursoEstudiante, Date fecha) throws SQLException {
        try (PreparedStatement psExists = connection.prepareStatement(EXISTE_ASISENCIA)) {
            psExists.setInt(1, cursoEstudiante);
            psExists.setDate(2, fecha);
            ResultSet rsExists = psExists.executeQuery();
            return rsExists.next();
        }
    }
    
    public List<AsistenciaDB> getAsistenciasDelDia(int idGrupo, int idCurso, LocalDate fecha) throws AccesoALaDataException {
        List<AsistenciaDB> asistencias = new ArrayList<>();
        Date date = Date.valueOf(fecha);
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_ASISTENCIAS_DEL_DIA)) {
            ps.setInt(1, idGrupo);
            ps.setInt(2, idCurso);
            ps.setDate(3, date);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                asistencias.add(armarAsistencia(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar las asistencias del dia: " + e.getMessage());
        }
        return asistencias;
    }
    
    public void tomarAsistencia(int id) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement psGet = connection.prepareStatement(GET_REGISTRO_ASISTENCIA);
                PreparedStatement ps = connection.prepareStatement(TOMAR_ASISTENCIA)) {
            psGet.setInt(1, id);
            
            boolean estado;
            try (ResultSet rs = psGet.executeQuery()) {
                if (!rs.next()) {
                    throw new AccesoALaDataException("No se pudo enconctrar la asistencia con ID" + id);
                }
                estado = rs.getBoolean("estado");
            }
            
            ps.setBoolean(1, !estado);
            ps.setInt(2, id);
            ps.executeUpdate();
            
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al tomar la asistencia de un estudiante: " + e.getMessage());
        }
    }
    
    public List<NotaDB> getNotasCurso(int idGrupo, int idCurso) throws AccesoALaDataException {
        List<NotaDB> notas = new ArrayList<>();
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_NOTAS_CURSO)) {
            ps.setInt(1, idGrupo);
            ps.setInt(2, idCurso);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                notas.add(armarNota(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar las notas de un curso: " + e.getMessage());
        }
        return notas;
    }
    
    public NotaDB modificarZona(int notaZona , int id) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(MODIFICAR_ZONA)) {
            ps.setInt(1, notaZona);
            ps.setInt(2, id);
            ps.executeUpdate();
            
            return getNotaPorId(id);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar la zona de una nota: " + e.getMessage());
        }
    }
    
    public NotaDB modificarExamen(int notaExamen, int id) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(MODIFICAR_FINAL)) {
            ps.setInt(1, notaExamen);
            ps.setInt(2, id);
            ps.executeUpdate();
            
            return getNotaPorId(id);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar el examen final de una nota: " + e.getMessage());
        }
    }
    
    public NotaDB getNotaPorId(int id) throws AccesoALaDataException {
        try (Connection connection = DBConnectionSingleton.getInstancia().getConnection();
                PreparedStatement ps = connection.prepareStatement(GET_NOTA_POR_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return armarNota(rs);
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar una nota por ID: " + e.getMessage());
        }
        return null;
    }
    
    private EstudianteCurso armarEstudiante(ResultSet rs) throws SQLException {
        return new EstudianteCurso(rs.getString("dpi"), rs.getString("nombre"), rs.getInt("id_curso_estudiante"));
    }
    
    private CursoMaestro armarCursoMaestro(ResultSet rs) throws SQLException {
        return new CursoMaestro(
                rs.getInt("id_curso_maestro"), 
                rs.getInt("id_curso"), 
                rs.getString("nombre_curso"), 
                rs.getInt("id_grupo"), 
                rs.getString("nombre_grado"), 
                rs.getString("nombre_nivel"), 
                rs.getString("seccion_id"), 
                rs.getInt("ciclo_escolar_id"));
    }
    
    private AsistenciaDB armarAsistencia(ResultSet rs) throws SQLException {
        return new AsistenciaDB(
                rs.getInt("id"), 
                rs.getInt("curso_estudiante_id"), 
                rs.getBoolean("estado"), 
                rs.getDate("fecha").toLocalDate(), 
                rs.getString("nombre"));
    }
    
    private NotaDB armarNota(ResultSet rs) throws SQLException {
        return new NotaDB(
                rs.getString("nombre_estudiante"), 
                rs.getInt("id"), 
                rs.getInt("zona"), 
                rs.getInt("examen_final"), 
                rs.getInt("curso_estudiante_id"));
    }
    
}
