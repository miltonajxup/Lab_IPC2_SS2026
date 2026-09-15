/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.Request;

/**
 *
 * @author milton
 */
public class ConclusionViaje {
    
    private final int kilometrajeLlegada; 
    private final double gastoCombustible; 
    private final String idViaje;  
    private final String numeroPlaca; 
    private final String numeroLicencia; 
    private final double saldoChofer; 
    private final String destino;

    public ConclusionViaje(int kilometrajeLlegada, double gastoCombustible, String idViaje, String numeroPlaca, String numeroLicencia, double saldoChofer, String destino) {
        this.kilometrajeLlegada = kilometrajeLlegada;
        this.gastoCombustible = gastoCombustible;
        this.idViaje = idViaje;
        this.numeroPlaca = numeroPlaca;
        this.numeroLicencia = numeroLicencia;
        this.saldoChofer = saldoChofer;
        this.destino = destino;
    }

    public int getKilometrajeLlegada() {
        return kilometrajeLlegada;
    }

    public double getGastoCombustible() {
        return gastoCombustible;
    }

    public String getIdViaje() {
        return idViaje;
    }

    public String getNumeroPlaca() {
        return numeroPlaca;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public double getSaldoChofer() {
        return saldoChofer;
    }

    public String getDestino() {
        return destino;
    }
    
}
