/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos;

/**
 *
 * @author milton
 */
public class GastoInsumo {
    
    private final int codigo;
    private final double unidadesInsumo;

    public GastoInsumo(int codigo, double unidadesInsumo) {
        this.codigo = codigo;
        this.unidadesInsumo = unidadesInsumo;
    }

    public int getCodigo() {
        return codigo;
    }

    public double getUnidadesInsumo() {
        return unidadesInsumo;
    }
    
}
