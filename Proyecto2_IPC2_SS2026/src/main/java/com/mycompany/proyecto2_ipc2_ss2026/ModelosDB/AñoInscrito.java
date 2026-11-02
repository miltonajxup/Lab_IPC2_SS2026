/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class AñoInscrito {
    
    private final int anio;
    private final String carrera;
    private final String grado;

    public AñoInscrito(int anio, String carrera, String grado) {
        this.anio = anio;
        this.carrera = carrera;
        this.grado = grado;
    }

    public int getAnio() {
        return anio;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getGrado() {
        return grado;
    }
    
}
