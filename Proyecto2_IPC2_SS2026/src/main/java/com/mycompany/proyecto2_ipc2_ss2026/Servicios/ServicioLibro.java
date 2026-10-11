/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Bibliotecario.LibroDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataExistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.LibroDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.LibroRequest;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioLibro {
    
    private final LibroDAO librodao;
    
    public ServicioLibro() {
        librodao = new LibroDAO();
    }
    
    public LibroDB getLibroPorId(String codigo) throws AccesoALaDataException, DataInexistenteException {
        revisarExistenciaLibro(codigo);
        return librodao.getLibroPorId(codigo);
    }
    
    public List<LibroDB> getLibrosBiblioteca(int pagina) throws AccesoALaDataException, ValorInvalidoException {
        if (pagina < LimitePagina.PAGINA_MINIMA) {
            throw new ValorInvalidoException("La pagina minima que se puede buscar es " + LimitePagina.PAGINA_MINIMA);
        }
        return librodao.getTodosLosLibros(pagina);
    }
    
    public String crearLibro(LibroRequest libro) throws AccesoALaDataException, ValorInvalidoException, DataExistenteException {
        revisarDatos(libro, libro.getCodigo());
        
        if (librodao.existeCodigoLibro(libro.getCodigo())) {
            throw new DataExistenteException("Ya existe un libro con el codigo " + libro.getCodigo());
        }
        
        librodao.agregarLibro(libro);
        return "Se ha agregado el libro " + libro.getTitulo() + " de forma exitosa";
    }
    
    public LibroDB modificarLibro(LibroRequest libro, String codigo) throws AccesoALaDataException, ValorInvalidoException, DataInexistenteException {
        revisarDatos(libro, codigo);
        
        revisarExistenciaLibro(codigo);
        
        return librodao.modificarLibro(libro, codigo);
    }
    
    public void modificarCantidadLibros(LibroRequest libro, String codigo) throws AccesoALaDataException, ValorInvalidoException, DataInexistenteException {
        revisarExistenciaLibro(codigo);
        
        if (libro.getCantidadDisponible() < 0) {
            throw new ValorInvalidoException("La cantidad de libros disponibles no puede ser una cantidad negativa");
        }
        
        librodao.modificarCantidadLibros(libro.getCantidadDisponible(), codigo);
    }
    
    private void revisarDatos(LibroRequest libro, String codigo) throws ValorInvalidoException {
        if (libro == null || codigo == null || libro.getTitulo() == null || libro.getAutor() == null || libro.getEditorial() == null || 
                codigo.isEmpty() || libro.getTitulo().isEmpty() || libro.getAutor().isEmpty() || libro.getEditorial().isEmpty()) {
            throw new ValorInvalidoException("Hacen falta datos para registrar un libro nuevo");
        }
    }
    
    private void revisarExistenciaLibro(String codigo) throws AccesoALaDataException, DataInexistenteException {
        if (!librodao.existeCodigoLibro(codigo)) {
            throw new DataInexistenteException("No se pudo encontrar el libro");
        }
    }
    
}
