/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class CursoDB {
    
    private final int idCurso;
    private final String nombre;
    private final String descripcion;

    public CursoDB(int idCurso, String nombre, String descripcion) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }
    
}
