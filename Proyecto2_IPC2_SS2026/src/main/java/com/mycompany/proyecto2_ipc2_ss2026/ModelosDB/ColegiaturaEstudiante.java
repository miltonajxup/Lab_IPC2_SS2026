/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class ColegiaturaEstudiante {
    
    private final String dpiEstudiante;
    private final double costoColegiatura;

    public ColegiaturaEstudiante(String dpiEstudiante, double costoColegiatura) {
        this.dpiEstudiante = dpiEstudiante;
        this.costoColegiatura = costoColegiatura;
    }

    public String getDpiEstudiante() {
        return dpiEstudiante;
    }

    public double getCostoColegiatura() {
        return costoColegiatura;
    }
    
}
