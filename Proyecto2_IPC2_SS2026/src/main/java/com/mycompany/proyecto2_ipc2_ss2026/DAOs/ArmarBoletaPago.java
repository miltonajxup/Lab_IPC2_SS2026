/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs;

import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.BoletaPagoDB;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author milton
 */
public class ArmarBoletaPago {
    
    public BoletaPagoDB armar(ResultSet rs) throws SQLException {
        return new BoletaPagoDB(
                rs.getInt("id"), 
                rs.getDouble("monto"), 
                rs.getBoolean("estado"), 
                rs.getString("tipo_pago"), 
                rs.getString("mes_pago"), 
                rs.getInt("ciclo_escolar_id"), 
                rs.getString("estudiante_dpi"), 
                rs.getString("nombre_estudiante"));
    }
    
}
