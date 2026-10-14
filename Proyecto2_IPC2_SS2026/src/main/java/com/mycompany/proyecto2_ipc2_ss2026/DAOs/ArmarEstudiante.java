/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.EstadosEstudiante;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.RolUsuario;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.EstudianteDB;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author milton
 */
public class ArmarEstudiante {
    
    public EstudianteDB estudiante(ResultSet rs) throws SQLException {
        return new EstudianteDB(
                rs.getString("dpi_estudiante"), 
                rs.getString("nombre"), 
                RolUsuario.valueOf(rs.getString("rol")), 
                rs.getString("numero_encargado"), 
                rs.getString("numero_estudiante"), 
                EstadosEstudiante.valueOf(rs.getString("estado")));
    }
    
}
