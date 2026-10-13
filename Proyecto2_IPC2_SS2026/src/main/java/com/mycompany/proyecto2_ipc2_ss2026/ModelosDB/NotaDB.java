/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class NotaDB {
    
    private final String nombreIdentificador;
    private final int idNota;
    private final int zona;
    private final int examenFinal;
    private final int idCursoEstudiante;
    private int notaFinal;

    public NotaDB(String nombreIdentificador, int idNota, int zona, int examenFinal, int idCursoEstudiante) {
        this.nombreIdentificador = nombreIdentificador;
        this.idNota = idNota;
        this.zona = zona;
        this.examenFinal = examenFinal;
        this.idCursoEstudiante = idCursoEstudiante;
    }

    public String getNombreIdentificador() {
        return nombreIdentificador;
    }

    public int getIdNota() {
        return idNota;
    }

    public int getZona() {
        return zona;
    }

    public int getExamenFinal() {
        return examenFinal;
    }

    public int getIdCursoEstudiante() {
        return idCursoEstudiante;
    }
    
    public int getNotaFinal() {
        return zona + examenFinal;
    }
    
}
