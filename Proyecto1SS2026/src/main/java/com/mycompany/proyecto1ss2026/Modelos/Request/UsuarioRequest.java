/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Modelos.Request;

import com.mycompany.proyecto1ss2026.Constantes.RolUsuario;

/**
 *
 * @author milton
 */
public class UsuarioRequest {
    
    private final String dpi;
    private final String nombre;
    private final String nit;
    private final String telefono;
    private final String direccion;
    private double creditoDisponible;
    private boolean estado;
    private final RolUsuario rol;
    private String sucursal;

    public UsuarioRequest(String dpi, String nombre, String nit, String telefono, String direccion, RolUsuario rol) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.nit = nit;
        this.telefono = telefono;
        this.direccion = direccion;
        this.rol = rol;
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

    public RolUsuario getRol() {
        return rol;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setCreditoDisponible(double creditoDisponible) {
        this.creditoDisponible = creditoDisponible;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

}
