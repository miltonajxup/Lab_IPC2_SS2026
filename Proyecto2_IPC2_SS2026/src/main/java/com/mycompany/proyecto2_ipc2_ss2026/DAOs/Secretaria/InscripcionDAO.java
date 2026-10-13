/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs.Secretaria;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.TipoPago;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.CicloEscolar;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.UsuarioDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.InscripcionDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.InscripcionRequest;
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
public class InscripcionDAO {
    
    private final String PAGO_INSCRIPCION = "SELECT estado FROM boleta_pago WHERE tipo_pago = ? AND estudiante_dpi = ?";
    private final String REVISAR_INSCRIPCION = "SELECT * FROM inscripcion WHERE estudiante = ?";
    private final String ESTUDIANTE_INSCRITO = "SELECT * FROM inscripcion WHERE estudiante = ? AND grupo_id = ?";
    private final String AGREGAR_ESTUDIANTE = "INSERT INTO estudiante (dpi_estudiante, numero_encargado, numero_estudiante) VALUES (?,?,?)";
    private final String GENERAR_INSCRIPCION = "INSERT INTO inscripcion (estudiante, grupo_id) VALUES (?,?)";
    private final String ULTIMA_INSCRIPCION = "SELECT * FROM inscripcion ORDER BY id DESC LIMIT 1";
    private final String GENERAR_BOLETA_PAGO = "INSERT INTO boleta_pago (monto, tipo_pago, ciclo_escolar_id, estudiante_dpi) VALUES (?,?,?,?)";
    private final String GET_COSTO_INSCRIPCION = "SELECT gra.costo_inscripcion FROM grado AS gra JOIN grupo_ciclo_escolar AS grup ON gra.id = grup.grado_id AND grup.id = ?";
    private final String GET_GRUPO_CICLO_ESCOLAR = "SELECT * FROM grupo_ciclo_escolar WHERE id = ?";
    private final String GET_CURRICULO_GRADO = "SELECT * FROM curriculo WHERE grado_id = ?";
    private final String GET_CURRICULO_CARRERA = "SELECT * FROM curriculo WHERE carrera_id = ?";
    private final String ASIGNAR_ESTUDIANTE_CURSO = "INSERT INTO curso_estudiante (estudiante_id, curso_id, inscripcion_id) VALUES (?,?,?)";
    
    private final UsuarioDAO usuariodao;
    private final CicloEscolar ciclo;
    
    public InscripcionDAO() {
        usuariodao = new UsuarioDAO();
        ciclo = new CicloEscolar();
    }
    
    public InscripcionDB revisarInscripcion(String estudiante) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            boolean pagoRealizado = false;
            PreparedStatement psPago = connection.prepareStatement(PAGO_INSCRIPCION);
            psPago.setString(1, TipoPago.INSCRIPCION.name());
            psPago.setString(2, estudiante);
            ResultSet rsPago = psPago.executeQuery();
            if (rsPago.next()) {
                pagoRealizado = rsPago.getBoolean("estado");
            }
            
            PreparedStatement ps = connection.prepareStatement(REVISAR_INSCRIPCION);
            ps.setString(1, estudiante);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return armarInscripcion(rs, pagoRealizado);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar un registro de inscripcion: " + e.getMessage());
        }
        return null;
    }
    
    private InscripcionDB armarInscripcion(ResultSet rs, boolean pagoRealizado) throws SQLException {
        return new InscripcionDB(rs.getInt("id"), rs.getString("estudiante"), rs.getInt("estado"), rs.getBoolean("estado"), pagoRealizado);
    }
    
    public boolean existeGrupoCicloEscolar(int idGrupo) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_GRUPO_CICLO_ESCOLAR);
            ps.setInt(1, idGrupo);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al revisar la existencia de un grupo escolar: " + e.getMessage());
        }
    }
    
    public boolean estaInscrito(String dpiEstudiante, int grupoId) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(ESTUDIANTE_INSCRITO);
            ps.setString(1, dpiEstudiante);
            ps.setInt(2, grupoId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al revisar si un estudiante se encuentra inscrito: " + e.getMessage());
        }
    }
    
    public void inscribirEstudiantePrimerIngreso(InscripcionRequest inscripcion) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            connection.setAutoCommit(false);
            
            agregarEstudiante(connection, inscripcion.getEstudiante());
            
            PreparedStatement ps = connection.prepareStatement(GENERAR_INSCRIPCION);
            ps.setString(1, inscripcion.getEstudiante().getDpi());
            ps.setInt(2, inscripcion.getGrupoId());
            ps.executeUpdate();
            
            PreparedStatement psId = connection.prepareStatement(ULTIMA_INSCRIPCION);
            ResultSet rsId = psId.executeQuery();
            if (rsId.next()) {
                generarBoletaInscripcion(connection, inscripcion.getGrupoId(), inscripcion.getEstudiante().getDpi());

                asignarCursosEstudiante(connection, inscripcion.getGrupoId(), inscripcion.getEstudiante().getDpi(), rsId.getInt("id"));
            } else {
                throw new AccesoALaDataException("No se pudo encontrar el ID de la inscripcion");
            }
            
            connection.setAutoCommit(false);
        } catch (SQLException | AccesoALaDataException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new AccesoALaDataException("Error al realizar el rollback al incribir a un estudiante: " + ex.getMessage() + "\nCon origen: " + e.getMessage());
            }
            throw new AccesoALaDataException("Error al inscribir a un estudiante: \n" + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new AccesoALaDataException("Error al regresar el auto commit al crear un estudiante " + e.getMessage());
            }
        }
    }
    
    public void inscribirEstudianteReingreso(InscripcionRequest inscripcion) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            connection.setAutoCommit(false);
            
            PreparedStatement ps = connection.prepareStatement(GENERAR_INSCRIPCION);
            ps.setString(1, inscripcion.getDpiEstudiante());
            ps.setInt(2, inscripcion.getGrupoId());
            ps.executeUpdate();
            
            PreparedStatement psId = connection.prepareStatement(ULTIMA_INSCRIPCION);
            ResultSet rs = psId.executeQuery();
            if (rs.next()) {
                generarBoletaInscripcion(connection, inscripcion.getGrupoId(), inscripcion.getDpiEstudiante());

                asignarCursosEstudiante(connection, inscripcion.getGrupoId(), inscripcion.getDpiEstudiante(), rs.getInt("id"));
            } else {
                throw new AccesoALaDataException("No se pude encontar el ID de la inscripcion: ");
            }
            
            connection.commit();
        } catch (SQLException | AccesoALaDataException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new AccesoALaDataException("Error al hacer un rollback en inscribir estudiante de reingreso: " + ex.getMessage() + "\n Con origen: " + e.getMessage());
            }
            throw new AccesoALaDataException("Error al incribir a un estudiante de reingreso: " + e.getMessage());
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                throw new AccesoALaDataException("Error al regresar el auto commit al inscribir de reingreso: " + e.getMessage());
            }
        }
    }
    
    private void agregarEstudiante(Connection connection, UsuarioRequest request) throws AccesoALaDataException {
        try {
            usuariodao.agregarUsuario(connection, request);

            PreparedStatement ps = connection.prepareStatement(AGREGAR_ESTUDIANTE);
            ps.setString(1, request.getDpi());
            ps.setString(2, request.getNumeroEncargado());
            ps.setString(3, request.getNumeroEstudiante());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al agregar un estudiante: " + e.getMessage());
        }
    }
    
    private void generarBoletaInscripcion(Connection connection, int grupoId, String dpiEstudiante) throws AccesoALaDataException {
        int año = ciclo.getAñoActivo();
        double monto = 0;
        try {
            PreparedStatement psMonto = connection.prepareStatement(GET_COSTO_INSCRIPCION);
            psMonto.setInt(1, grupoId);
            ResultSet rsMonto = psMonto.executeQuery();
            if (rsMonto.next()) {
                monto = rsMonto.getDouble("costo_inscripcion");
            } else  {
                throw new AccesoALaDataException("No se pudo enconctrar el valor de la inscripcion ");
            }
            
            PreparedStatement ps = connection.prepareStatement(GENERAR_BOLETA_PAGO);
            ps.setDouble(1, monto);
            ps.setString(2, TipoPago.INSCRIPCION.name());
            ps.setInt(3, año);
            ps.setString(4, dpiEstudiante);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al generar la boleta de inscripcion: " + e.getMessage());
        }
    }
    
    private void asignarCursosEstudiante(Connection connection, int grupoId, String dpiEstudiante, int idInscripcion) throws AccesoALaDataException {
        try {
            boolean esCarrera = false;
            int gradoId = 0;
            String carreraId = "";
            List<Integer> idCursosCurriculo;
            
            PreparedStatement psGrupo = connection.prepareStatement(GET_GRUPO_CICLO_ESCOLAR);
            psGrupo.setInt(1, grupoId);
            ResultSet rsGrupo = psGrupo.executeQuery();
            if (rsGrupo.next()) {
                carreraId = rsGrupo.getString("carrera_id");
                if (carreraId != null) {
                    esCarrera = true;
                } else {
                    gradoId = rsGrupo.getInt("grado_id");
                }
            }
            if (esCarrera) {
                PreparedStatement ps = connection.prepareStatement(GET_CURRICULO_CARRERA);
                ps.setString(1, carreraId);
                ResultSet rs = ps.executeQuery();
                idCursosCurriculo = getListaCursos(rs);
            } else {
                PreparedStatement ps = connection.prepareStatement(GET_CURRICULO_GRADO);
                ps.setInt(1, gradoId);
                ResultSet rs = ps.executeQuery();
                idCursosCurriculo = getListaCursos(rs);
            }
            //estudiante_id, curso_id, inscripcion_id
            for (Integer cursoCurriculo : idCursosCurriculo) {
                PreparedStatement ps = connection.prepareStatement(ASIGNAR_ESTUDIANTE_CURSO);
                ps.setString(1, dpiEstudiante);
                ps.setInt(2, cursoCurriculo);
                ps.setInt(3, idInscripcion);
                ps.executeUpdate();
            }
            
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al asignar los cursos del curriculo al estudiante: " + e.getMessage());
        }
    }
    
    private List<Integer> getListaCursos(ResultSet rs) throws SQLException {
        List<Integer> idCursos = new ArrayList<>();
        while (rs.next()) {
            idCursos.add(rs.getInt("curso_id"));
        }
        return idCursos;
    }
    
}
