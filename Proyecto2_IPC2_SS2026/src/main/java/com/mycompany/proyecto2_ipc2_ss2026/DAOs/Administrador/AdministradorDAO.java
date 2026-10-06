/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.CicloEscolar;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.ColegiaturaEstudiante;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.CurriculoRequest;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.CursoMaestroRequest;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.GrupoAcademicoRequest;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.TipoPago;
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
public class AdministradorDAO {
    
    //SELECT ins.estudiante, grad.costo_colegiatura FROM boleta_pago AS bol RIGHT JOIN inscripcion AS ins ON bol.estudiante_dpi = ins.estudiante 
    //JOIN grupo_ciclo_escolar AS grup ON ins.grupo_id = grup.id JOIN grado AS grad ON grad.id = grup.grado_id WHERE bol.id IS NULL
    private final String CREAR_GRUPO_ACADEMICO = "INSERT INTO grupo_academico (ciclo_escolar_id, grado_id, carrera_id, seccion_id) VALUES (?,?,?,?)";
    private final String CREAR_CURRICULO_PARA_GRADO = "INSERT INTO curriculo (curso_id, grado_id) VALUES (?,?)";
    private final String CREAR_CURRICULO_PARA_CARRERA = "INSERT INTO curriculo (curso_id, carrera_id) VALUES (?,?)";
    private final String GET_ESTUDIANTES_INSCRITOS = 
            """
            SELECT ins.*, grad.costo_colegiatura FROM inscripcion AS ins 
            JOIN grupo_ciclo_escolar AS grup ON ins.grupo_id = grup.id 
            JOIN grado AS grad ON grup.grado_id = grad.id 
            WHERE ins.estado = TRUE AND grup.ciclo_escolar_id = ?""";
    private final String BOLETAS_GENERADAS = "SELECT * FROM boleta_pago WHERE ciclo_escolar_id = ? AND estudiante_dpi = ? AND mes_pago = ? AND tipo_pago = ?";
    private final String GENERAR_BOLETAS_PAGO = "INSERT INTO boleta_pago (monto, tipo_pago, mes_pago, ciclo_escolar_id, estudiante_dpi) VALUES (?,?,?,?,?,?)";
    private final String ASIGNAR_MAESTRO_CURSO = "INSERT INTO curso_maestro (maestro_id, curso_id, ciclo_escolar_id) VALUES (?,?,?)";
    
    private final CicloEscolar ciclo;
    
    public AdministradorDAO() {
        ciclo = new CicloEscolar();
    }
    
    public void crearGrupoAcademico(GrupoAcademicoRequest request) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(CREAR_GRUPO_ACADEMICO);
            ps.setInt(1, request.getAñoLectivo());
            ps.setInt(2, request.getGrado());
            ps.setInt(3, request.getCarrera());
            ps.setInt(4, request.getSeccion());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al crear un grupo academico " + e.getMessage());
        }
    }
    
    public void crearCurriculoParaGrado(CurriculoRequest request) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(CREAR_CURRICULO_PARA_GRADO);
            ps.setInt(1, request.getCursoId());
            ps.setInt(2, request.getGradoId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al crear un curriculo para un grado " + e.getMessage());
        }
    }
    
    public void crearCurriculo(CurriculoRequest request) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(CREAR_CURRICULO_PARA_CARRERA);
            ps.setInt(1, request.getCursoId());
            ps.setString(2, request.getCarreraId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al crear un curriculo para carrera");
        }
    }
    
    public void generarBoletasDePago(int mesBoleta) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            int año = ciclo.getAñoActivo();
            List<ColegiaturaEstudiante> estudiantesInscritos = new ArrayList<>();
            PreparedStatement psInscritos = connection.prepareStatement(GET_ESTUDIANTES_INSCRITOS);
            psInscritos.setInt(1, año);
            ResultSet rsInscritos = psInscritos.executeQuery();
            while (rsInscritos.next()) {
                estudiantesInscritos.add(new ColegiaturaEstudiante(
                        rsInscritos.getString("estudiante"), 
                        rsInscritos.getDouble("costo_colegiatura")));
            }
            
            PreparedStatement psGenerar = connection.prepareStatement(GENERAR_BOLETAS_PAGO);
            PreparedStatement psBoleta = connection.prepareStatement(BOLETAS_GENERADAS);
            for (ColegiaturaEstudiante estudiante : estudiantesInscritos) {
                psBoleta.setInt(1, año);
                psBoleta.setString(2, estudiante.getDpiEstudiante());
                psBoleta.setInt(3, mesBoleta);
                psBoleta.setString(4, TipoPago.COLEGIATURA.name());
                ResultSet rsBoleta = psBoleta.executeQuery();
                if (!rsBoleta.next()) {
                    //monto, tipo_pago, mes_pago, ciclo_escolar_id, estudiante_dpi
                    psGenerar.setDouble(1, estudiante.getCostoColegiatura());
                    psGenerar.setString(2, TipoPago.COLEGIATURA.name());
                    psGenerar.setInt(3, mesBoleta);
                    psGenerar.setInt(4, año);
                    psGenerar.setString(5, estudiante.getDpiEstudiante());
                }
            }
            
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al generar las boletas de pago: " + e.getMessage());
        }
    }
    
    public void asignarMaestroCurso(CursoMaestroRequest request) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(ASIGNAR_MAESTRO_CURSO);
            ps.setString(1, request.getMaestro());
            ps.setInt(2, request.getCurso());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al asignar a un maestro a un curso " + e.getMessage());
        }
    }
    
}
