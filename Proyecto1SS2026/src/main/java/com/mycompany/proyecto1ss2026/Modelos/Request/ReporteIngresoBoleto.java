/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.Request;

/**
 *
 * @author milton
 */
public class ReporteIngresoBoleto {
    
    private final int viajeId;
    private final String licencia;
    private final String chofer;
    private final String bus;
    private final int rutaId;
    private final int distancia;
    private final double precioBoleto;
    private final String sucursalOrigen;
    private final String sucursalDestino;
    private final String fechaSalida;
    private final int boletosVendidos;
    private final double ingresoTotal;

    public ReporteIngresoBoleto(int viajeId, String licencia, String chofer, String bus, int rutaId, int distancia, double precioBoleto, String sucursalOrigen, String sucursalDestino, String fechaSalida, int boletosVendidos, double ingresoTotal) {
        this.viajeId = viajeId;
        this.licencia = licencia;
        this.chofer = chofer;
        this.bus = bus;
        this.rutaId = rutaId;
        this.distancia = distancia;
        this.precioBoleto = precioBoleto;
        this.sucursalOrigen = sucursalOrigen;
        this.sucursalDestino = sucursalDestino;
        this.fechaSalida = fechaSalida;
        this.boletosVendidos = boletosVendidos;
        this.ingresoTotal = ingresoTotal;
    }

    public int getViajeId() {
        return viajeId;
    }

    public String getLicencia() {
        return licencia;
    }

    public String getChofer() {
        return chofer;
    }

    public String getBus() {
        return bus;
    }

    public int getRutaId() {
        return rutaId;
    }

    public int getDistancia() {
        return distancia;
    }

    public double getPrecioBoleto() {
        return precioBoleto;
    }

    public String getSucursalOrigen() {
        return sucursalOrigen;
    }

    public String getSucursalDestino() {
        return sucursalDestino;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public int getBoletosVendidos() {
        return boletosVendidos;
    }

    public double getIngresoTotal() {
        return ingresoTotal;
    }
    
}
