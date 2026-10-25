/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class CurriculoDB {
    
    private final int id;
    private final int cursoId;
    private final String curso;
    private final String grado;
    private final String nivel;
    private final String carrera;

    public CurriculoDB(int id, int idCurso, String curso, String grado, String nivel, String carrera) {
        this.id = id;
        this.cursoId = idCurso;
        this.curso = curso;
        this.grado = grado;
        this.nivel = nivel;
        this.carrera = carrera;
    }

    public int getId() {
        return id;
    }
    
    public int getCursoId() {
        return cursoId;
    }

    public String getCurso() {
        return curso;
    }

    public String getGrado() {
        return grado;
    }

    public String getNivel() {
        return nivel;
    }

    public String getCarrera() {
        return carrera;
    }
    
}
