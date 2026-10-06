/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class CarreraDB {
    
    private final String codigo;
    private final String nombre;
    private final boolean estado;
    private final int gradoId;

    public CarreraDB(String codigo, String nombre, boolean estado, int gradoId) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = estado;
        this.gradoId = gradoId;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    public int getGradoId() {
        return gradoId;
    }
    
}
