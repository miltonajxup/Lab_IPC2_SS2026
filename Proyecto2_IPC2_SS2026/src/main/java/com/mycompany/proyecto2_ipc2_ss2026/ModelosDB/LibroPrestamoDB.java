/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class LibroPrestamoDB {
    
    private final String titulo;
    private final String codigo;
    private final int cantidad;
    
    public LibroPrestamoDB(String titulo, String codigo, int cantidad) {
        this.titulo = titulo;
        this.codigo = codigo;
        this.cantidad = cantidad;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getCantidad() {
        return cantidad;
    }
    
}
