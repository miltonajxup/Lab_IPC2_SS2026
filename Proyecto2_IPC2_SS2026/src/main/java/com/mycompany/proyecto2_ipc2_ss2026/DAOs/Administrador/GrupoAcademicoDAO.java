/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.CurriculoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.GrupoAcademicoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.CurriculoRequest;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.GrupoAcademicoRequest;
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
public class GrupoAcademicoDAO {
    
    private final String GRUPO_ACADEMICO_POR_GRADO = "SELECT * FROM grupo_ciclo_escolar WHERE ciclo_escolar_id = ? AND grado_id = ? AND seccion_id = ?";
    private final String GRUPO_ACADEMICO_POR_CARRERA = "SELECT * FROM grupo_ciclo_escolar WHERE ciclo_escolar_id = ? AND grado_id = ? AND carrera_id = ? AND seccion_id = ?";
    private final String CREAR_GRUPO_ACADEMICO_GRADO = "INSERT INTO grupo_ciclo_escolar (ciclo_escolar_id, grado_id, seccion_id) VALUES (?,?,?)";
    private final String CREAR_GRUPO_ACADEMICO_CARRERA = "INSERT INTO grupo_ciclo_escolar (ciclo_escolar_id, grado_id, carrera_id, seccion_id) VALUES (?,?,?,?)";
    private final String GET_CRUPOS_POR_AÑO = 
            """
            SELECT grup.*, gra.nombre AS nombre_grado, niv.nombre AS nivel_academico, carr.nombre AS nombre_carrera 
            FROM grupo_ciclo_escolar AS grup 
                JOIN grado AS gra ON grup.grado_id = gra.id 
                LEFT JOIN carrera AS carr ON grup.carrera_id = carr.codigo 
                JOIN nivel_academico AS niv ON gra.nivel_id = niv.id 
            WHERE grup.ciclo_escolar_id = ? ORDER BY grup.grado_id LIMIT ?, ?""";
    
    private final String FORMATO_GET_CURRICULOS = 
            """
            SELECT 
                curr.id, cu.nombre AS nombre_curso, 
                gra.nombre AS nombre_grado, 
                niv.nombre AS nombre_nivel, 
                carr.nombre AS nombre_carrera 
            FROM curriculo AS curr 
            JOIN curso AS cu ON curr.curso_id = cu.id 
            JOIN grado AS gra ON curr.grado_id = gra.id 
            JOIN nivel_academico AS niv ON gra.nivel_id = niv.id 
            LEFT JOIN carrera AS carr ON curr.carrera_id = carr.codigo """;
    private final String GET_CURRICULOS_GRADO = FORMATO_GET_CURRICULOS + " WHERE gra.id = ?";
    private final String GET_CURRICULOS_CARRERA = FORMATO_GET_CURRICULOS + " WHERE carr.codigo = ?";
    
    private final String EXISTE_CURRICULO_GRADO = "SELECT * FROM curriculo WHERE curso_id = ? AND grado_id = ?";
    private final String EXISTE_CURRICULO_CARRERA = "SELECT * FROM curriculo WHERE curso_id = ? AND grado_id = ? AND carrera_id = ?";
    private final String CURRICULO_CON_CURSOS = "SELECT curr.* FROM curriculo AS curr JOIN curso_estudiante AS cures ON curr.id = cures.curso_curriculo WHERE curr.id = ?";
    private final String CREAR_CURRICULO_PARA_GRADO = "INSERT INTO curriculo (curso_id, grado_id) VALUES (?,?)";
    private final String CREAR_CURRICULO_PARA_CARRERA = "INSERT INTO curriculo (curso_id, grado_id, carrera_id) VALUES (?,?,?)";
    private final String ELIMINAR_CURRICULO = "DELETE FROM curriculo WHERE id = ?";
    
    public boolean existeGrupoGrado(GrupoAcademicoRequest grupo) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GRUPO_ACADEMICO_POR_GRADO);
            ps.setInt(1, grupo.getAñoLectivo());
            ps.setInt(2, grupo.getGrado());
            ps.setString(3, grupo.getSeccion());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al revisar la existencia de un grupo academico por grado: " + e.getMessage());
        }
    }
    
    public boolean existeGrupoCarrera(GrupoAcademicoRequest grupo) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GRUPO_ACADEMICO_POR_CARRERA);
            ps.setInt(1, grupo.getAñoLectivo());
            ps.setInt(2, grupo.getGrado());
            ps.setString(3, grupo.getCarrera());
            ps.setString(4, grupo.getSeccion());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al revisar la existencia de un grupo academico por carrera: " + e.getMessage());
        }
    }
    
    public List<GrupoAcademicoDB> crearGrupoAcademicoGrado(GrupoAcademicoRequest request) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(CREAR_GRUPO_ACADEMICO_GRADO);
            ps.setInt(1, request.getAñoLectivo());
            ps.setInt(2, request.getGrado());
            ps.setString(3, request.getSeccion());
            ps.executeUpdate();
            
            return getGruposPorAño(request.getAñoLectivo(), LimitePagina.PAGINA_MINIMA);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al crear un grupo academico para un grado: " + e.getMessage());
        }
    }
    
    public List<GrupoAcademicoDB> crearGrupoAcademicoCarrera(GrupoAcademicoRequest request) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(CREAR_GRUPO_ACADEMICO_CARRERA);
            ps.setInt(1, request.getAñoLectivo());
            ps.setInt(2, request.getGrado());
            ps.setString(3, request.getCarrera());
            ps.setString(4, request.getSeccion());
            ps.executeUpdate();
            
            return getGruposPorAño(request.getAñoLectivo(), LimitePagina.PAGINA_MINIMA);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al crear un grupo academico para una carrera" + e.getMessage());
        }
    }
    
    public List<GrupoAcademicoDB> getGruposPorAño(int año, int pagina) throws AccesoALaDataException {
        List<GrupoAcademicoDB> grupos = new ArrayList<>();
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            int paginacion = (pagina - 1) * LimitePagina.LIMITE;
            PreparedStatement ps = connection.prepareStatement(GET_CRUPOS_POR_AÑO);
            ps.setInt(1, año);
            ps.setInt(2, paginacion);
            ps.setInt(3, LimitePagina.LIMITE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                grupos.add(armarGrupoAcademico(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los grupos academicos por año: " + e.getMessage());
        }
        return grupos;
    }
    
    private GrupoAcademicoDB armarGrupoAcademico(ResultSet rs) throws SQLException {
        return new GrupoAcademicoDB(
                rs.getInt("id"), 
                rs.getInt("ciclo_escolar_id"), 
                rs.getInt("grado_id"), 
                rs.getString("nombre_grado"), 
                rs.getString("nivel_academico"), 
                rs.getString("carrera_id"), 
                rs.getString("nombre_carrera"), 
                rs.getString("seccion_id"));
    }
    
    public List<CurriculoDB> getCurriculosGrado(CurriculoRequest curriculo) throws AccesoALaDataException {
        List<CurriculoDB> curriculos = new ArrayList<>();
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_CURRICULOS_GRADO);
            ps.setInt(1, curriculo.getGradoId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                curriculos.add(armarCurriculo(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los cursos de un grado: " + e.getMessage());
        }
        return curriculos;
    }
    
    public List<CurriculoDB> getCurriculosCarrera(CurriculoRequest curriculo) throws AccesoALaDataException {
        List<CurriculoDB> curriculos = new ArrayList<>();
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_CURRICULOS_CARRERA);
            ps.setString(1, curriculo.getCarreraId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                curriculos.add(armarCurriculo(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los cursos de una carrera: " + e.getMessage());
        }
        return curriculos;
    }
    
    private CurriculoDB armarCurriculo(ResultSet rs) throws SQLException {
        return new CurriculoDB(
                rs.getInt("id"), 
                rs.getString("nombre_curso"), 
                rs.getString("nombre_grado"), 
                rs.getString("nombre_nivel"), 
                rs.getString("nombre_carrera"));
    }
    
    public boolean existeCurriculoGrado(CurriculoRequest curriculo) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(EXISTE_CURRICULO_GRADO);
            ps.setInt(1, curriculo.getCursoId());
            ps.setInt(2, curriculo.getGradoId());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar la existencia de un curriculo de grado: " + e.getMessage());
        }
    }
    
    public boolean existeCurriculoCarrera(CurriculoRequest curriculo) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(EXISTE_CURRICULO_CARRERA);
            ps.setInt(1, curriculo.getCursoId());
            ps.setInt(2, curriculo.getGradoId());
            ps.setString(3, curriculo.getCarreraId());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar la existencia de un curriculo de carrera: " + e.getMessage());
        }
    }
    
    public List<CurriculoDB> crearCurriculoParaGrado(CurriculoRequest request) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(CREAR_CURRICULO_PARA_GRADO);
            ps.setInt(1, request.getCursoId());
            ps.setInt(2, request.getGradoId());
            ps.executeUpdate();
            
            return getCurriculosGrado(request);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al crear un curriculo para un grado " + e.getMessage());
        }
    }
    
    public List<CurriculoDB> crearCurriculoParaCarrera(CurriculoRequest request) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(CREAR_CURRICULO_PARA_CARRERA);
            ps.setInt(1, request.getCursoId());
            ps.setInt(2, request.getGradoId());
            ps.setString(3, request.getCarreraId());
            ps.executeUpdate();
            
            return getCurriculosCarrera(request);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al crear un curriculo para carrera: " + e.getMessage());
        }
    }
    
    public boolean curriculoUtilizado(int idCurriculo) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(CURRICULO_CON_CURSOS);
            ps.setInt(1, idCurriculo);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar un curriculo con cursos de estudiante: " + e.getMessage());
        }
    }
    
    public void eliminarCurriculo(int idCurriculo) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(ELIMINAR_CURRICULO);
            ps.setInt(1, idCurriculo);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al eliminar un curriculo: " + e.getMessage());
        }
    }
    
}
