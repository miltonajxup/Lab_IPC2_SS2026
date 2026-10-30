/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class PromedioCursoDB {
    
    private final String nombreCurso;
    private final double promedioZona;
    private final double promedioExamen;
    private final double promedioTotal;

    public PromedioCursoDB(String nombreCurso, double promedioZona, double promedioExamen, double promedioTotal) {
        this.nombreCurso = nombreCurso;
        this.promedioZona = promedioZona;
        this.promedioExamen = promedioExamen;
        this.promedioTotal = promedioTotal;
    }

    public String getNombreCurso() {
        return nombreCurso;
    }

    public double getPromedioZona() {
        return promedioZona;
    }

    public double getPromedioExamen() {
        return promedioExamen;
    }

    public double getPromedioTotal() {
        return promedioTotal;
    }
    
}
