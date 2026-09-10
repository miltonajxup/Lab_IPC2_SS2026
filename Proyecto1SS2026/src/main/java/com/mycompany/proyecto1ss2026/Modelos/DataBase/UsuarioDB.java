/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.DataBase;

/**
 *
 * @author milton
 */
public class UsuarioDB {
    
    private final String dpi;
    private final String nombre;
    private final String nit;
    private final String telefono;
    private final String direccion;
    private final double creditoDisponible;
    private final boolean estado;
    private String sucursal;

    public UsuarioDB(String dpi, String nombre, String nit, String telefono, String direccion, double creditoDisponible, boolean estado) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.nit = nit;
        this.telefono = telefono;
        this.direccion = direccion;
        this.creditoDisponible = creditoDisponible;
        this.estado = estado;
    }

    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNit() {
        return nit;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public double getCreditoDisponible() {
        return creditoDisponible;
    }

    public boolean isEstado() {
        return estado;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

}
