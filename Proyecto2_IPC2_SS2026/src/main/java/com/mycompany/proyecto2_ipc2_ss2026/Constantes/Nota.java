/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Constantes;

/**
 *
 * @author milton
 */
public enum Nota { 
    MINIMO(0),
    MAXIMO_ZONA(70), 
    MAXIMO_FINAL(30);
    
    private final int valor;
    
    private Nota(int valor) {
        this.valor = valor;
    }
    
    public int getValor() {
        return valor;
    }
}
