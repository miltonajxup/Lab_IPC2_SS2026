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
public class PrestamoDB {
    
    private final int id;
    private final LocalDate fechaPrestamo;
    private final LocalDate fechaDevolucionEsperada;
    private final String fechaDevolucionCompletada;
    private final String dpiPrestamista;
    private final boolean devuelto;
    private final List<LibroPrestamoDB> libros;

    public PrestamoDB(int id, LocalDate fechaPrestamo, LocalDate fechaDevolucionEsperada, String fechaDevolucionCompletada, String dpiPrestamista, boolean devuelto, List<LibroPrestamoDB> libros) {
        this.id = id;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.fechaDevolucionCompletada = fechaDevolucionCompletada;
        this.dpiPrestamista = dpiPrestamista;
        this.devuelto = devuelto;
        this.libros = libros;
    }

    public int getId() {
        return id;
    }

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public LocalDate getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    public String getFechaDevolucionCompletada() {
        return fechaDevolucionCompletada;
    }

    public String getDpiPrestamista() {
        return dpiPrestamista;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public List<LibroPrestamoDB> getLibros() {
        return libros;
    }
    
}
