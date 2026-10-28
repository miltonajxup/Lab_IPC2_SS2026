/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

import java.util.List;

/**
 *
 * @author milton
 */
public class SolvenciaEstudiante {
    
    private final String dpi;
    private final String nombre;
    private final String nombreGrado;
    private final String nombreNivel;
    private final String seccion;
    private final List<BoletaPagoDB> boletas;

    public SolvenciaEstudiante(String dpi, String nombre, String nombreGrado, String nombreNivel, String seccion, List<BoletaPagoDB> boletas) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.nombreGrado = nombreGrado;
        this.nombreNivel = nombreNivel;
        this.seccion = seccion;
        this.boletas = boletas;
    }

    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNombreGrado() {
        return nombreGrado;
    }

    public String getNombreNivel() {
        return nombreNivel;
    }

    public String getSeccion() {
        return seccion;
    }

    public List<BoletaPagoDB> getBoletas() {
        return boletas;
    }
    
}
