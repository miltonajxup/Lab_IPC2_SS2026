/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.Request;

/**
 *
 * @author milton
 */
public class ReporteRutaDemandada {
    
    private final int rutaId;
    private final int boletosVendidos;
    private final double precioBoleto;
    private final int distancia;
    private final String sucursalOrigen;
    private final String ciudadOrigen;
    private final String sucursalDestino;
    private final String ciudadDestino;

    public ReporteRutaDemandada(int rutaId, int boletosVendidos, double precioBoleto, int distancia, String sucursalOrigen, String ciudadOrigen, String sucursalDestino, String ciudadDestino) {
        this.rutaId = rutaId;
        this.boletosVendidos = boletosVendidos;
        this.precioBoleto = precioBoleto;
        this.distancia = distancia;
        this.sucursalOrigen = sucursalOrigen;
        this.ciudadOrigen = ciudadOrigen;
        this.sucursalDestino = sucursalDestino;
        this.ciudadDestino = ciudadDestino;
    }

    public int getRutaId() {
        return rutaId;
    }

    public int getBoletosVendidos() {
        return boletosVendidos;
    }

    public double getPrecioBoleto() {
        return precioBoleto;
    }

    public int getDistancia() {
        return distancia;
    }

    public String getSucursalOrigen() {
        return sucursalOrigen;
    }

    public String getCiudadOrigen() {
        return ciudadOrigen;
    }

    public String getSucursalDestino() {
        return sucursalDestino;
    }

    public String getCiudadDestino() {
        return ciudadDestino;
    }

}
