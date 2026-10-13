/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class EstudianteCurso {
    
    private final String dpi;
    private final String nombre;
    private final int idCursoEstudiante;

    public EstudianteCurso(String dpi, String nombre, int idCursoEstudiante) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.idCursoEstudiante = idCursoEstudiante;
    }

    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public int getIdCursoEstudiante() {
        return idCursoEstudiante;
    }
    
}
