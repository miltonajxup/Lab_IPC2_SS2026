/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author milton
 */
public class Pago {
    
    private final int codigoNomina;
    private final String fechaEmision;
    private final double montoAPagar;
    private final boolean estado;
    private final String empleado;
    private final String tipo;
    private String nombreEmpleado;

    public Pago(int codigoNomina, String fechaEmision, double montoAPagar, boolean estado, String empleado, String tipo) {
        this.codigoNomina = codigoNomina;
        this.fechaEmision = fechaEmision;
        this.montoAPagar = montoAPagar;
        this.estado = estado;
        this.empleado = empleado;
        this.tipo = tipo;
    }

    public int getCodigoNomina() {
        return codigoNomina;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public double getMontoAPagar() {
        return montoAPagar;
    }

    public boolean isEstado() {
        return estado;
    }

    public String getEmpleado() {
        return empleado;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }
    
}
