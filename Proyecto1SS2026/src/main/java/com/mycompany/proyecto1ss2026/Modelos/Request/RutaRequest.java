/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.Request;

/**
 *
 * @author milton
 */
public class RutaRequest {
    
    private String id;
    private int distanciaAproximada;
    private double precioBoleto;
    private String sucursalRegistro;
    private String sucursalOrigen;
    private String sucursalDestino;

    public RutaRequest(String id, int distanciaAproximada, double precioBoleto) {
        this.id = id;
        this.distanciaAproximada = distanciaAproximada;
        this.precioBoleto = precioBoleto;
    }

    public RutaRequest(int distanciaAproximada, double precioBoleto, String sucursalRegistro, String sucursalOrigen, String sucursalDestino) {
        this.distanciaAproximada = distanciaAproximada;
        this.precioBoleto = precioBoleto;
        this.sucursalRegistro = sucursalRegistro;
        this.sucursalOrigen = sucursalOrigen;
        this.sucursalDestino = sucursalDestino;
    }

    public String getId() {
        return id;
    }

    public int getDistanciaAproximada() {
        return distanciaAproximada;
    }

    public void setDistanciaAproximada(int distanciaAproximada) {
        this.distanciaAproximada = distanciaAproximada;
    }

    public double getPrecioBoleto() {
        return precioBoleto;
    }

    public void setPrecioBoleto(double precioBoleto) {
        this.precioBoleto = precioBoleto;
    }

    public String getSucursalRegistro() {
        return sucursalRegistro;
    }

    public void setSucursalRegistro(String sucursalRegistro) {
        this.sucursalRegistro = sucursalRegistro;
    }

    public String getSucursalOrigen() {
        return sucursalOrigen;
    }

    public void setSucursalOrigen(String sucursalOrigen) {
        this.sucursalOrigen = sucursalOrigen;
    }

    public String getSucursalDestino() {
        return sucursalDestino;
    }

    public void setSucursalDestino(String sucursalDestino) {
        this.sucursalDestino = sucursalDestino;
    }

}
