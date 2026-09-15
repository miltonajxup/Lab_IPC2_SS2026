/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.Request;

/**
 *
 * @author milton
 */
public class Boleto {
    
    private String usuario;
    private int viaje;
    private int asiento;
    private String fecha;

    public Boleto(String usuario, int viaje, int asiento, String fecha) {
        this.usuario = usuario;
        this.viaje = viaje;
        this.asiento = asiento;
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getViaje() {
        return viaje;
    }

    public int getAsiento() {
        return asiento;
    }

    public String getFecha() {
        return fecha;
    }
    
}
