/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.ArmarCurso;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.CursoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.CursoRequest;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
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
public class CursoDAO {
    
    private final String CREAR_CURSO = "INSERT INTO curso (nombre, descripcion) VALUES (?,?)";
    private final String MODIFICAR_CURSO = "UPDATE curso SET nombre = ?, descripcion = ? WHERE id = ?";
    private final String GET_TODOS_CURSOS = "SELECT * FROM curso LIMIT ?, ?";
    
    public ArmarCurso armar;
    
    public CursoDAO() {
        armar = new ArmarCurso();
    }
    
    public void crearCurso(CursoRequest request) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(CREAR_CURSO);
            ps.setString(1, request.getNombre());
            ps.setString(2, request.getDescripcion());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al crear un curso " + e.getMessage());
        }
    }
    
    public void modificarCurso(CursoRequest request, int idCurso) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(MODIFICAR_CURSO);
            ps.setString(1, request.getNombre());
            ps.setString(2, request.getDescripcion());
            ps.setInt(3, idCurso);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar un curso " + e.getMessage());
        }
    }
    
    public List<CursoDB> getTodosLosCursos(int numeroPagina) throws AccesoALaDataException {
        List<CursoDB> cursos = new ArrayList<>();
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_TODOS_CURSOS);
            ps.setInt(1, (numeroPagina - 1) * LimitePagina.LIMITE);
            ps.setInt(2, LimitePagina.LIMITE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                cursos.add(armar.curso(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar todos los cursos " + e.getMessage());
        }
        return cursos;
    }
    
}
