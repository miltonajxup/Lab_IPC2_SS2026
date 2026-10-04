/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Constantes;

/**
 *
 * @author milton
 */
public enum LimiteTamaño {
    
    DPI(13), 
    NOMBRE(60), 
    CONTRASEÑA(50), 
    TELEFONO(10), 
    CODIGO(20),
    SECCION(1), 
    DESCRIPCION(300), 
    
    
    ;
    
    private final int limite;
    
    private LimiteTamaño(int limite) {
        this.limite = limite;
    }
    
    public int getLimite() {
        return limite;
    }
    
}
