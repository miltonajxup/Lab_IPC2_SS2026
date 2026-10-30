/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class IngresoDB {
    
    private final String tipoPago;
    private final double totalIngreso;

    public IngresoDB(String tipoPago, double totalIngreso) {
        this.tipoPago = tipoPago;
        this.totalIngreso = totalIngreso;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public double getTotalIngreso() {
        return totalIngreso;
    }
    
}
