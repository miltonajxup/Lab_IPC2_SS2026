/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author milton
 */
public class PrestamoRequest {
    
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionCompletada;
    private String prestamista;
    private List<LibroPrestamoRequest> librosPrestamo;

    public LocalDate getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDate fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    public void setFechaDevolucionEsperada(LocalDate fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }

    public LocalDate getFechaDevolucionCompletada() {
        return fechaDevolucionCompletada;
    }

    public void setFechaDevolucionCompletada(LocalDate fechaDevolucionCompletada) {
        this.fechaDevolucionCompletada = fechaDevolucionCompletada;
    }

    public String getPrestamista() {
        return prestamista;
    }

    public void setPrestamista(String prestamista) {
        this.prestamista = prestamista;
    }

    public List<LibroPrestamoRequest> getLibrosPrestamo() {
        return librosPrestamo;
    }

    public void setLibrosPrestamo(List<LibroPrestamoRequest> librosPrestamo) {
        this.librosPrestamo = librosPrestamo;
    }

}
