/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Servicios;

import com.mycompany.proyecto1ss2026.DAOs.UsuarioDAO;
import com.mycompany.proyecto1ss2026.DAOs.ViajeDAO;
import com.mycompany.proyecto1ss2026.DAOs.ViajePrivadoDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException;
import com.mycompany.proyecto1ss2026.Modelos.Request.PropuestaViaje;
import com.mycompany.proyecto1ss2026.Modelos.Request.ViajePrivado;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

/**
 *
 * @author milton
 */
public class ServicioViajePrivado {
    
    private final int MINIMO_PASAJEROS = 1;
    private final int MINIMO_DISTANCIA = 1;
    private static final double MINIMO_COSTO = 100.00;
    private final ViajeDAO viajedao;
    private final ViajePrivadoDAO viajePrivDao;
    private final UsuarioDAO usuariodao;

    public ServicioViajePrivado() {
        viajedao = new ViajeDAO();
        viajePrivDao = new ViajePrivadoDAO();
        usuariodao = new UsuarioDAO();
    }
    
    public String aceptarPropuestaViajePrivado(String chofer, String bus, String sucursal, String idPropuesta) throws AccesoALaDataException, ValorInvalidoException {
        if (chofer == null || bus == null || idPropuesta == null || sucursal == null
                || chofer.isEmpty() || bus.isEmpty() || idPropuesta.isEmpty() || sucursal.isEmpty()) {
            return null;
        }
        PropuestaViaje propuesta = viajePrivDao.buscarPropuestaPorId(idPropuesta);
        if (propuesta == null) {
            throw new ValorInvalidoException("No se pudo encontrar la propuesta de viaje con id: " + idPropuesta);
        }
        ViajePrivado viaje = new ViajePrivado(propuesta, chofer, bus, sucursal);
        viajedao.agregarViajePrivado(viaje, propuesta.getIdPropuesta());
        return "Se ha aceptado el viaje";
    }
    
    public String agregarPropuestaViajePrivado(
            String textoCantidadPasajeros, String origen, String destino, String textoDistancia, String textoHoraSalida, 
            String textoHoraLlegada, String textoFechaSalida, String textoCosto, String usuarioSolicitante) throws AccesoALaDataException, ValorInvalidoException {
        
        if (textoCantidadPasajeros == null || origen == null || destino == null || textoDistancia == null || textoHoraSalida == null
                || textoHoraLlegada == null || textoFechaSalida == null || textoCosto == null || usuarioSolicitante == null 
                || textoCantidadPasajeros.isEmpty() || origen.isEmpty() || destino.isEmpty() || textoDistancia.isEmpty() || textoHoraSalida.isEmpty() 
                || textoHoraLlegada.isEmpty() || textoFechaSalida.isEmpty() || textoCosto.isEmpty() || usuarioSolicitante.isEmpty()) {
            return null;
        }
        if (!usuariodao.existeDpiUsuario(usuarioSolicitante)) {
            throw new ValorInvalidoException("No se pudo encotrar el usuario con dpi " + usuarioSolicitante);
        }
        int cantidadPasjeros = revisarValorEntero(textoCantidadPasajeros, MINIMO_PASAJEROS, "pasajeros");
        int distanciaAproximada = revisarValorEntero(textoDistancia, MINIMO_DISTANCIA, "distancia");
        LocalTime horaSalida = revisalHora(textoHoraSalida);
        LocalTime horaLlegada = revisalHora(textoHoraLlegada);
        if (horaSalida.isAfter(horaLlegada)) {
            throw new ValorInvalidoException("La hora de salida no puede ser despues que la de llegada");
        }
        revisarFecha(textoFechaSalida);
        double costo = revisarValorDecimal(textoCosto, MINIMO_COSTO, "costo");
        PropuestaViaje propuesta = new PropuestaViaje(cantidadPasjeros, origen, destino, distanciaAproximada, textoHoraSalida, textoHoraLlegada, textoFechaSalida, costo, usuarioSolicitante);
        viajePrivDao.agregarPropuestaViaje(propuesta);
        return "Se ha mandado la Propuesta del viaje";
    }
    
    private int revisarValorEntero(String textoValor, int minimo, String mensajeError) throws ValorInvalidoException {
        int valor;
        try {
            valor = Integer.parseInt(textoValor);
        } catch (NumberFormatException e) {
            throw new ValorInvalidoException("El valor " + textoValor + " no es valido para " + mensajeError);
        }
        if (valor < minimo) {
            throw new ValorInvalidoException("El valor de " + mensajeError + " no puede se menor a " + minimo);
        }
        return valor;
    }
    
    private double revisarValorDecimal(String textoValor, double minimo, String mensajeError) throws ValorInvalidoException {
        double valor;
        try {
            valor = Integer.parseInt(textoValor);
        } catch (NumberFormatException e) {
            throw new ValorInvalidoException("El valor " + textoValor + " no es valido para " + mensajeError);
        }
        if (valor < minimo) {
            throw new ValorInvalidoException("El valor de " + mensajeError + " no puede se menor a " + minimo);
        }
        return valor;
    }
    
    private LocalTime revisalHora(String textoHora) throws ValorInvalidoException {
        LocalTime hora;
        try {
            hora = LocalTime.parse(textoHora);
        } catch (DateTimeParseException e) {
            throw new ValorInvalidoException("El valor " + textoHora + " no es valido para una hora");
        }
        return hora;
    }
    
    private LocalDate revisarFecha(String textoFecha) throws ValorInvalidoException {
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(textoFecha);
        } catch (DateTimeParseException e) {
            throw new ValorInvalidoException("El valor " + textoFecha + " no es valido para una fecha");
        }
        return fecha;
    }
    
}
