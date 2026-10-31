/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author milton
 */
public class PrestamoAtrasado extends PrestamoDB {
    
    private final int diasAtraso;
    private final double montoMulta;

    public PrestamoAtrasado(
            int id, LocalDate fechaPrestamo, LocalDate fechaDevolucionEsperada, 
            String fechaDevolucionCompletada, String dpiPrestamista, String nombrePrestamista, 
            boolean devuelto, List<LibroPrestamoDB> libros, int diasAtraso, double montoMulta) {
        super(id, fechaPrestamo, fechaDevolucionEsperada, fechaDevolucionCompletada, dpiPrestamista, nombrePrestamista, devuelto, libros);
        this.diasAtraso = diasAtraso;
        this.montoMulta = montoMulta;
    }

    public int getDiasAtraso() {
        return diasAtraso;
    }

    public double getMontoMulta() {
        return montoMulta;
    }
    
}
