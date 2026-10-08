/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs;

import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.CursoDB;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author milton
 */
public class ArmarCurso {
    
    public CursoDB curso(ResultSet rs) throws SQLException {
        return new CursoDB(
                rs.getInt("id"), 
                rs.getString("nombre"), 
                rs.getString("descripcion"));
    }
    
}
