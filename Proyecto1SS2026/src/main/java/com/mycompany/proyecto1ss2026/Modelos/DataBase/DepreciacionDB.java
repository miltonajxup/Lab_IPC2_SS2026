/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.DataBase;

/**
 *
 * @author milton
 */
public class DepreciacionDB {
    
    private final int idDepreciacion;
    private final double montoDepreciacion;

    public DepreciacionDB(int idDepreciacion, double montoDepreciacion) {
        this.idDepreciacion = idDepreciacion;
        this.montoDepreciacion = montoDepreciacion;
    }

    public int getIdDepreciacion() {
        return idDepreciacion;
    }

    public double getMontoDepreciacion() {
        return montoDepreciacion;
    }
    
    
}
