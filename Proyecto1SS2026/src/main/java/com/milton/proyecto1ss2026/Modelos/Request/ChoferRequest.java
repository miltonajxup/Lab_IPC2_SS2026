/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.Request;

/**
 *
 * @author milton
 */
public class ChoferRequest {
    
    private String dpi;
    private String nombre;
    private byte[] foto;
    private String numeroLicencia;
    private String tipoLicencia;
    private String fechaVencimiento;
    private String numeroTelefono;
    private double salarioPorViaje;
    private boolean estadoOperativo;
    private String sucursalBase;
    private String sucursalActual;

    public String getDpi() {
        return dpi;
    }

    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getNumeroLicencia() {
        return numeroLicencia;
    }

    public void setNumeroLicencia(String numeroLicencia) {
        this.numeroLicencia = numeroLicencia;
    }

    public String getTipoLicencia() {
        return tipoLicencia;
    }

    public void setTipoLicencia(String tipoLicencia) {
        this.tipoLicencia = tipoLicencia;
    }

    public String getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getNumeroTelefono() {
        return numeroTelefono;
    }

    public void setNumeroTelefono(String numeroTelefono) {
        this.numeroTelefono = numeroTelefono;
    }

    public double getSalarioPorViaje() {
        return salarioPorViaje;
    }

    public void setSalarioPorViaje(double salarioPorViaje) {
        this.salarioPorViaje = salarioPorViaje;
    }

    public boolean isEstadoOperativo() {
        return estadoOperativo;
    }

    public void setEstadoOperativo(boolean estadoOperativo) {
        this.estadoOperativo = estadoOperativo;
    }

    public String getSucursalBase() {
        return sucursalBase;
    }

    public void setSucursalBase(String sucursalBase) {
        this.sucursalBase = sucursalBase;
    }

    public String getSucursalActual() {
        return sucursalActual;
    }

    public void setSucursalActual(String sucursalActual) {
        this.sucursalActual = sucursalActual;
    }
    
}
