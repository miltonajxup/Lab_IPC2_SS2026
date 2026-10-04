/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Constantes;

/**
 *
 * @author milton
 */
public enum MesPago {
    
    ENERO(1), 
    FEBRERO(2), 
    MARZO(3), 
    ABRIL(4), 
    MAYO(5), 
    JUNIO(6), 
    JULIO(7), 
    AGOSTO(8), 
    SEPTIEMBRE(9), 
    OCTUBRE(10), 
    NOVIEMBRE(11), 
    DICIEMBRE(12);
    
    private final int numeroMes;
    
    private MesPago(int numeroMes) {
        this.numeroMes = numeroMes;
    }
    
    public int getNumeroMes() {
        return numeroMes;
    }
    
}
