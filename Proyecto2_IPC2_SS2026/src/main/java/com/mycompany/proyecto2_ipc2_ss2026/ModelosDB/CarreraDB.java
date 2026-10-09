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
public class CarreraDB {
    
    private final String codigo;
    private final String nombre;
    private final boolean estado;
    private final List<GradoDB> grados;

    public CarreraDB(String codigo, String nombre, boolean estado, List<GradoDB> grados) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.estado = estado;
        this.grados = grados;
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

    public List<GradoDB> getGrados() {
        return grados;
    }
    
}
