/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Constantes;

/**
 *
 * @author milton
 */
public enum Limite {
    DPI(13), 
    NOMBRE(50), 
    NIT(13), 
    TELEFONO(10), 
    DIRECCION(75), 
    NUMERO_LICENCIA(13), 
    NUMERO_PLACA(10), 
    MARCA(30), 
    MODELO(30), 
    
    ;
    
    private final int tamañoLimite;

    private Limite (int tamañoLimite) {
        this.tamañoLimite = tamañoLimite;
    }
    
    public int getTamañoLimite() {
        return tamañoLimite;
    }
    
}

