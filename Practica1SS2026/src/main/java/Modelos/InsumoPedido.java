/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author milton
 */
public class InsumoPedido {
    
    private final String nombre;
    private final int codigo;
    private double cantidad;

    public InsumoPedido(String nombre, int codigo) {
        this.nombre = nombre;
        this.codigo = codigo;
    }
    
    public String getNombre() {
        return nombre;
    }

    public int getCodigo() {
        return codigo;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }
    
}
