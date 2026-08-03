/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelos.Mesa;

/**
 *
 * @author milton
 */
public class Mesa {
    
    private final int numeroMesa;
    private final int capacidad;
    private final int estado;

    public Mesa(int numeroMesa, int capacidad, int estado) {
        this.numeroMesa = numeroMesa;
        this.capacidad = capacidad;
        this.estado = estado;
    }

    public int getNumeroMesa() {
        return numeroMesa;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getEstado() {
        return estado;
    }
    
}
