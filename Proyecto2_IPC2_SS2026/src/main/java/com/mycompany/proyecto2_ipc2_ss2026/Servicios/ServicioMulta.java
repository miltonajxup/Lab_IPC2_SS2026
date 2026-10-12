/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Bibliotecario.MultaDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Bibliotecario.PrestamoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataExistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.MultaDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.MultaRequest;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioMulta {
    
    private final MultaDAO multadao;
    private final PrestamoDAO prestamodao;
    
    public ServicioMulta() {
        multadao = new MultaDAO();
        prestamodao = new PrestamoDAO();
    }
    
    public List<MultaDB> getMultasPendientes(int pagina) throws AccesoALaDataException, ValorInvalidoException {
        if (pagina < LimitePagina.PAGINA_MINIMA) {
            throw new ValorInvalidoException("La pagina minima disponible es: " + LimitePagina.PAGINA_MINIMA);
        }
        return multadao.getMultasSinPagar(pagina);
    }
    
    public List<MultaDB> getMultasPrestamo(int idPrestamo) throws AccesoALaDataException, DataInexistenteException {
        List<MultaDB> multas = multadao.getMultPorPrestamo(idPrestamo);
        if (multas.isEmpty()) {
            throw new DataInexistenteException("Este prestamo no tiene multas relacionadas");
        }
        return multas;
    }
    
    public String agregarMulta(MultaRequest multa) throws AccesoALaDataException, ValorInvalidoException, DataInexistenteException {
        if (multa == null || multa.getFechaPago() == null) {
            throw new ValorInvalidoException("Faltan datos para crear una multa");
        }
        if (!prestamodao.existePrestamo(multa.getIdPrestamo())) {
            throw new DataInexistenteException("No se pudo encontrar el prestamo al cual se le quiere agregar una multa");
        }
        if (multa.getMonto() < 1) {
            throw new ValorInvalidoException("El monto de la multa no puede ser al menos de Q1");
        }
        multadao.agregarMulta(multa);
        return "La multa ha sido generada con Exito";
    }
    
    public String pagarMulta(int idMulta) throws AccesoALaDataException, DataInexistenteException, DataExistenteException {
        MultaDB multa = multadao.multaPorId(idMulta);
        if (multa == null) {
            throw new DataInexistenteException("No se pudo encontrar la multa que se quiere pagar");
        }
        if (multa.isEstado()) {
            throw new DataExistenteException("Esta multa ya se encuentra pagada");
        }
        multadao.pagarMulta(idMulta);
        return "Se ha pagado al multa del prestamo con exito";
    }
    
}
