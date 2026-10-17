/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

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
    private final int idBoleta;
    private final String tipoPago;

    public SolvenciaEstudiante(String dpi, String nombre, String nombreGrado, String nombreNivel, String seccion, int idBoleta, String tipoPago) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.nombreGrado = nombreGrado;
        this.nombreNivel = nombreNivel;
        this.seccion = seccion;
        this.idBoleta = idBoleta;
        this.tipoPago = tipoPago;
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

    public int getIdBoleta() {
        return idBoleta;
    }

    public String getTipoPago() {
        return tipoPago;
    }
    
}
