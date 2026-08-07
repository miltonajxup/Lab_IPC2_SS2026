/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author milton
 */
public class DetalleCuenta {
    
    private final int id;
    private final int producto;
    private final String nombreProducto;
    private final double precio;
    private final int unidades;
    private final double subTotal;
    private final int pedido;

    public DetalleCuenta(int id, int producto, String nombreProducto, double precio, int unidades, double subTotal, int pedido) {
        this.id = id;
        this.producto = producto;
        this.nombreProducto = nombreProducto;
        this.precio = precio;
        this.unidades = unidades;
        this.subTotal = subTotal;
        this.pedido = pedido;
    }

    public int getId() {
        return id;
    }

    public int getProducto() {
        return producto;
    }
    
    public String getNombreProducto() {
        return nombreProducto;
    }

    public double getPrecio() {
        return precio;
    }

    public int getUnidades() {
        return unidades;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public int getPedido() {
        return pedido;
    }
    
}
