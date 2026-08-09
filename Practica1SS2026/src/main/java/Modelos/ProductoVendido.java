/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author milton
 */
public class ProductoVendido {
    
    private final int vecesVendida;
    private final int codigoProducto;
    private final String nombreProducto;

    public ProductoVendido(int vecesVendida, int codigoProducto, String nombreProducto) {
        this.vecesVendida = vecesVendida;
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
    }

    public int getVecesVendida() {
        return vecesVendida;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }
    
}
