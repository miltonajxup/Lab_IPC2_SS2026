/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs;

import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.GradoDB;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author milton
 */
public class ArmarGrado {
    
    public GradoDB armarGrado(ResultSet rs) throws SQLException {
        return new GradoDB(
                rs.getInt("id"), 
                rs.getString("nombre"), 
                rs.getDouble("costo_inscripcion"), 
                rs.getDouble("costo_colegiatura"), 
                rs.getInt("nivel_id"), 
                rs.getString("nombre_nivel"), 
                rs.getBoolean("estado"));
    }
    
}
