/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.DataBase;

import com.mycompany.proyecto1ss2026.Constantes.TipoLicencia;

/**
 *
 * @author milton
 */
public class ChoferDB {
    
    private final String nombre;
    private final byte[] foto;
    private final String numeroLicencia;
    private final TipoLicencia tipoLicencia;
    private final String fechaVencimiento;
    private final String numeroTelefono;
    private final double salarioPorViaje;
    private final boolean estadoOperativo;
    private final String sucursalBase;
    private final String sucursalActual;
    private final double saldoDisponible;
    private int viajesCompletados;

    public ChoferDB(String numeroLicencia, String nombre, byte[] foto, TipoLicencia tipoLicencia, String fechaVencimiento, String numeroTelefono, double salarioPorViaje, boolean estadoOperativo, String sucursalBase, String sucursalActual, double saldoDisponible) {
        this.numeroLicencia = numeroLicencia;
        this.nombre = nombre;
        this.foto = foto;
        this.tipoLicencia = tipoLicencia;
        this.fechaVencimiento = fechaVencimiento;
        this.numeroTelefono = numeroTelefono;
        this.salarioPorViaje = salarioPorViaje;
        this.estadoOperativo = estadoOperativo;
        this.sucursalBase = sucursalBase;
        this.sucursalActual = sucursalActual;
        this.saldoDisponible = saldoDisponible;
    }

    public String getNombre() {
        return nombre;
    }

    public byte[] getFoto() {
        return foto;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public TipoLicencia getTipoLicencia() {
        return tipoLicencia;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public double getSalarioPorViaje() {
        return salarioPorViaje;
    }

    public boolean isEstadoOperativo() {
        return estadoOperativo;
    }

    public String getSucursalBase() {
        return sucursalBase;
    }

    public String getSucursalActual() {
        return sucursalActual;
    }

    public double getSaldoDisponible() {
        return saldoDisponible;
    }

    public int getViajesCompletados() {
        return viajesCompletados;
    }

    public void setViajesCompletados(int viajesCompletados) {
        this.viajesCompletados = viajesCompletados;
    }

}
