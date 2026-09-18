/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.Request;


/**
 *
 * @author milton
 */
public class PropuestaViaje {
    
    private int idPropuesta;
    private final int cantidadPasajeros;
    private final String origen;
    private final String destino;
    private final int distanciaAProximada;
    private final String horaSalida;
    private final String horaLlegada;
    private final String fechaSalida;
    private final double costo;
    private final String usuarioSolicitante;
    private boolean estadoPropuesta;

    public PropuestaViaje(int idPropuesta, int cantidadPasajeros, String origen, String destino, int distanciaAProximada, String horaSalida, String horaLlegada, String fechaSalida, double costo, String usuarioSolicitante, boolean estadoPropuesta) {
        this.idPropuesta = idPropuesta;
        this.cantidadPasajeros = cantidadPasajeros;
        this.origen = origen;
        this.destino = destino;
        this.distanciaAProximada = distanciaAProximada;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.fechaSalida = fechaSalida;
        this.costo = costo;
        this.usuarioSolicitante = usuarioSolicitante;
        this.estadoPropuesta = estadoPropuesta;
    }

    public PropuestaViaje(int cantidadPasajeros, String origen, String destino, int distanciaAProximada, String horaSalida, String horaLlegada, String fechaSalida, double costo, String usuarioSolicitante) {
        this.cantidadPasajeros = cantidadPasajeros;
        this.origen = origen;
        this.destino = destino;
        this.distanciaAProximada = distanciaAProximada;
        this.horaSalida = horaSalida;
        this.horaLlegada = horaLlegada;
        this.fechaSalida = fechaSalida;
        this.costo = costo;
        this.usuarioSolicitante = usuarioSolicitante;
    }

    public int getIdPropuesta() {
        return idPropuesta;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public String getOrigen() {
        return origen;
    }

    public String getDestino() {
        return destino;
    }

    public int getDistanciaAProximada() {
        return distanciaAProximada;
    }

    public String getHoraSalida() {
        return horaSalida;
    }

    public String getHoraLlegada() {
        return horaLlegada;
    }

    public String getFechaSalida() {
        return fechaSalida;
    }

    public double getCosto() {
        return costo;
    }

    public String getUsuarioSolicitante() {
        return usuarioSolicitante;
    }

    public boolean isEstadoPropuesta() {
        return estadoPropuesta;
    }

}
