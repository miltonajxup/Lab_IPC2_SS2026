/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author milton
 */
public class Personal {
    
    private final String dpi;
    private final String nombre;
    private final double salario;
    private final String fechaContratacion;
    private final boolean estado;
    private final String rol;
    private final String jornada;

    public Personal(String dpi, String nombre, double salario, String fechaContratacion, boolean estado, String rol, String jornada) {
        this.dpi = dpi;
        this.nombre = nombre;
        this.salario = salario;
        this.fechaContratacion = fechaContratacion;
        this.estado = estado;
        this.rol = rol;
        this.jornada = jornada;
    }

    public String getDpi() {
        return dpi;
    }

    public String getNombre() {
        return nombre;
    }

    public double getSalario() {
        return salario;
    }

    public String getFechaContratacion() {
        return fechaContratacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public String getRol() {
        return rol;
    }

    public String getJornada() {
        return jornada;
    }
    
    
    
}
