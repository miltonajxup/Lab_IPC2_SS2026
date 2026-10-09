/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest;

import java.util.List;

/**
 *
 * @author milton
 */
public class CarreraRequest {
    
    private String codigo;
    private String nombre;
    private boolean estado;
    private List<Integer> grados;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Integer> getGrados() {
        return grados;
    }

    public void setGrados(List<Integer> grados) {
        this.grados = grados;
    }
    
}
