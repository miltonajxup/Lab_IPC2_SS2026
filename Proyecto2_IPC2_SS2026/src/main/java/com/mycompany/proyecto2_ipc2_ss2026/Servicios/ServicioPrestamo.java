/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LibroPrestamo;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Bibliotecario.LibroDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Bibliotecario.PrestamoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.UsuarioDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.LibroDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.PrestamoAtrasado;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.PrestamoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.LibroPrestamoRequest;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.PrestamoRequest;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioPrestamo {
    
    private final PrestamoDAO prestamodao;
    private final LibroDAO librodao;
    private final UsuarioDAO usuariodao;
    
    public ServicioPrestamo() {
        prestamodao = new PrestamoDAO();
        librodao = new LibroDAO();
        usuariodao = new UsuarioDAO();
    }
    
    public List<PrestamoDB> buscarPrestamoPorNombre(String nombrePrestamista) throws AccesoALaDataException, DataInexistenteException {
        List<PrestamoDB> prestamos = prestamodao.getPrestamoPorNombre(nombrePrestamista);
        if (prestamos.isEmpty()) {
            throw new DataInexistenteException("No se encontraron coincidencias o el usuario no tiene prestamos pendientes");
        }
        return prestamos;
    }
    
    public List<PrestamoAtrasado> prestamosSinTerminar(int pagina) throws AccesoALaDataException, ValorInvalidoException {
        revisarPagina(pagina);
        
        return prestamodao.getPrestamosSinTerminar(pagina);
    }
    
    public List<PrestamoDB> todosLosRegistros(int pagina) throws AccesoALaDataException, ValorInvalidoException {
        revisarPagina(pagina);
        
        return prestamodao.getTodosLosRegistros(pagina);
    }
    
    public PrestamoDB getPrestamoPorId(int id) throws AccesoALaDataException, DataInexistenteException {
        PrestamoDB prestamo = prestamodao.prestamoPorId(id);
        if (prestamo == null) {
            throw new DataInexistenteException("No se pudo encontrar el prestamo");
        }
        return prestamo;
    }
    
    public String prestarLibro(PrestamoRequest prestamo) throws AccesoALaDataException, DataInexistenteException, ValorInvalidoException {
        if (prestamo.getLibrosPrestamo() == null || prestamo.getIdPrestamista() == null || prestamo.getFechaPrestamo() == null || 
               prestamo.getFechaDevolucionEsperada() == null || prestamo.getIdPrestamista().isEmpty()) {
            throw new ValorInvalidoException("Hacen falta valores del prestamo");
        }
        if (!usuariodao.existeUsuarioPorDpi(prestamo.getIdPrestamista())) {
            throw new DataInexistenteException("El usuario que solicita el prestamo no se encuentra registrado en el sistema");
        }
        if (prestamo.getFechaPrestamo().isAfter(prestamo.getFechaDevolucionEsperada())) {
            throw new ValorInvalidoException("La Fecha del prestamo no puede ser posterior a al de Devolucion Esperada");
        }
        LocalDate diaActual =  LocalDate.now();
        if (diaActual.isAfter(prestamo.getFechaPrestamo())) {
            throw new ValorInvalidoException("No se pueden presentar prestamos anteriores al dia de hoy " + diaActual);
        }
        for (LibroPrestamoRequest libro : prestamo.getLibrosPrestamo()) {
            LibroDB actual = librodao.getLibroPorId(libro.getCodigo());
            if (actual == null) {
                throw new DataInexistenteException("No se pudo encontrar el libro con codigo " + libro.getCodigo());
            }
            int cantidadRestante = actual.getCantidadDisponible() - LibroPrestamo.CANTIDAD;
            if (cantidadRestante < 0) {
                throw new ValorInvalidoException("No se puede prestar el libro " + actual.getTitulo() + " \nporque no hay unidades suficientes \nCantidad: "  + actual.getCantidadDisponible());
            }
        }
        prestamodao.generarPrestamo(prestamo);
        return "El prestamo solicitado se ha validado";
    }
    
    public void terminarPrestamo(int idPrestamo) throws AccesoALaDataException, DataInexistenteException, ValorInvalidoException {
        if (!prestamodao.existePrestamo(idPrestamo)) {
            throw new DataInexistenteException("El prestamo que se quiere concluir no existe");
        }
        prestamodao.concluirPrestamo(idPrestamo);
    }
    
    private void revisarPagina(int pagina) throws ValorInvalidoException {
        if (pagina < LimitePagina.PAGINA_MINIMA) {
            throw new ValorInvalidoException("La pagina minima que se permite es: " + LimitePagina.PAGINA_MINIMA);
        }
    }
    
}
