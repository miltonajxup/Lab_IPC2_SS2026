/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.Request;

import com.mycompany.proyecto1ss2026.Constantes.EstadoViajePrivado;

/**
 *
 * @author milton
 */
public class ViajePrivadoRequest {
    
    private String chofer;
    private String bus;
    private int cantidadPasajeros;
    private String origen;
    private String destino;
    private int distanciaAProximada;
    private String horaSalida;
    private String horaLlegada;
    private String fechaSalida;
    private String fechaLlegada;
    private double costo;
    private String usuarioSolicitante;
    private EstadoViajePrivado estadoViaje;

    public String getChofer() {
        return chofer;
    }

    public void setChofer(String chofer) {
        this.chofer = chofer;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(int cantidadPasajeros) {
        this.cantidadPasajeros = cantidadPasajeros;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public int getDistanciaAProximada() {
        return distanciaAProximada;
    }

    public void setDistanciaAProximada(int distanciaAProximada) {
        this.distanciaAProximada = distanciaAProximada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(String horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(String fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(String fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    public void setUsuarioSolicitante(String usuarioSolicitante) {
        this.usuarioSolicitante = usuarioSolicitante;
    }

    public EstadoViajePrivado getEstadoViaje() {
        return estadoViaje;
    }

    public void setEstadoViaje(EstadoViajePrivado estadoViaje) {
        this.estadoViaje = estadoViaje;
    }

}
