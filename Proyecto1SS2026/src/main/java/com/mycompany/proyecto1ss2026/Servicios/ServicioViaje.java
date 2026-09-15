/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Servicios;

import com.mycompany.proyecto1ss2026.DAOs.BusDAO;
import com.mycompany.proyecto1ss2026.DAOs.ChoferDAO;
import com.mycompany.proyecto1ss2026.DAOs.HorarioDAO;
import com.mycompany.proyecto1ss2026.DAOs.UsuarioDAO;
import com.mycompany.proyecto1ss2026.DAOs.ViajeDAO;
import com.mycompany.proyecto1ss2026.DAOs.ViajePublicoDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInexistenteException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.BusDB;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ViajeDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.Asiento;
import com.mycompany.proyecto1ss2026.Modelos.Request.ViajePublicoRequest;
import com.mycompany.proyecto1ss2026.Respuesta.Respuesta;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioViaje {
    
    private final ViajeDAO viajedao;
    private final ViajePublicoDAO viaPublicodao;
    private final ChoferDAO choferdao;
    private final BusDAO busdao;
    private final HorarioDAO horariodao;
    private final UsuarioDAO usuariodao;
    
    public ServicioViaje() {
        viajedao = new ViajeDAO();
        viaPublicodao = new ViajePublicoDAO();
        choferdao = new ChoferDAO();
        busdao = new BusDAO();
        horariodao = new HorarioDAO();
        usuariodao = new UsuarioDAO();
    }
    
    public Respuesta agregarViajePublico(String chofer, String bus, String textoFecha, String horario) throws AccesoALaDataException, ValorInexistenteException, ValorInvalidoException {
        if (chofer == null || bus == null || textoFecha == null || horario == null 
                    || chofer.isEmpty() || bus.isEmpty() || textoFecha.isEmpty() && horario.isEmpty()) {
            return new Respuesta(false, "El formaulario esta vacio");
        }
        revisarExistenciaChofer(chofer);
        revisarExistenciaBus(bus);
        LocalDate fecha = revisarFecha(textoFecha);
        revisarExistenciaHorario(horario);
        ViajePublicoRequest viaje = new ViajePublicoRequest(chofer, bus, fecha, horario);
        viajedao.agregarViajePublico(viaje);
        return new Respuesta(true, "El viaje se ha generado con exito");
    }
    
    public Respuesta comparaBoletoViajePublico(String dpi, String idViaje, String textoNumeroAsientto) throws AccesoALaDataException, ValorInexistenteException, ValorInvalidoException {
        if (dpi == null || idViaje == null || textoNumeroAsientto == null 
                || dpi.isEmpty() || idViaje.isEmpty() || textoNumeroAsientto.isEmpty()) {
            return null;
        }
        int numeroAsiento;
        try {
            numeroAsiento = Integer.parseInt(textoNumeroAsientto);
        } catch (NumberFormatException e) {
            throw new ValorInvalidoException("El valor " + textoNumeroAsientto + " no es valido para el asiento");
        }
        ViajeDB viaje = viajedao.getViajePorId(idViaje);
        if (viaje == null) {
            throw new ValorInexistenteException("No se pudo encontrar el viaje con id: " + idViaje);
        }
        BusDB bus = busdao.getBusId(viaje.getBus());
        if (numeroAsiento > bus.getCapacidadPasajeros()) {
            throw new ValorInexistenteException("El asiento con numero " + numeroAsiento + " no existe dentro del bus " + bus.getNumeroPlaca());
        }
        List<Asiento> asientos = viaPublicodao.getAsientosBus(viaje.getBus(), viaje.getId());
        for (Asiento asiento : asientos) {
            if (asiento.getNumAsiento() == numeroAsiento && asiento.isOcupado()) {
                throw new ValorInvalidoException("El Asiento " + numeroAsiento + " ya se encuentra ocupado");
            }
        }
        UsuarioDB usuario = usuariodao.getUsuarioPorDpi(dpi);
        if (usuario == null) {
            throw new ValorInexistenteException("El usuario con dpi " + dpi + " no existe");
        }
        double precioBoleto = viajedao.getPrecioViaje(viaje.getId());
        if (usuario.getCreditoDisponible() < precioBoleto) {
            return new Respuesta(false, "No se cuenta con el saldo suficiente <br>para comprar un boleto <br>Precio Boleto: " + precioBoleto + "Q");
        }
        double saldoActual = usuario.getCreditoDisponible() - precioBoleto;
        viaPublicodao.agregarCompraBoleto(dpi, viaje.getId(), numeroAsiento, saldoActual);
        return new Respuesta(true, "Se ha comprado un boleto");
    }
    
    public ViajeDB getViajeSinTerminarPorId(String idViaje) throws AccesoALaDataException {
        if (idViaje == null || idViaje.isEmpty()) {
            return null;
        }
        return viajedao.getViajesSinTerminarId(idViaje);
    }
    
    public List<ViajeDB> getViajesSinTerminarSucursal(String sucursal) throws AccesoALaDataException {
        return viajedao.getViajesSinTerminarSucursalOrigen(sucursal);
    }
    
    public List<ViajeDB> getViajesSinTerminarChofer(String numeroLicencia) throws AccesoALaDataException {
        return viajedao.getViajesSinTerminarChofer(numeroLicencia);
    }
    
    private void revisarExistenciaChofer(String chofer) throws AccesoALaDataException, ValorInexistenteException {
        if (!choferdao.existeChofer(chofer)) {
            throw new ValorInexistenteException("No se pudo encotrar al chofer " + chofer);
        }
    }
    
    private void revisarExistenciaBus(String bus) throws AccesoALaDataException, ValorInexistenteException {
        if (!busdao.existeBus(bus)) {
            throw new ValorInexistenteException("No se pudo encontrar el bus con el numero de placa " + bus);
        }
    }
    
    private void revisarExistenciaHorario(String horario) throws AccesoALaDataException, ValorInexistenteException {
        if (!horariodao.existeHorario(horario)) {
            throw new ValorInexistenteException("No se pudo encontrar el horario con id " + horario);
        }
    }
    
    private LocalDate revisarFecha(String textoFecha) throws ValorInvalidoException {
        LocalDate fecha;
        try {
            fecha = LocalDate.parse(textoFecha);
        } catch (DateTimeParseException e) {
            throw new ValorInvalidoException("El valor " + textoFecha + " no es un valor valido para la fecha");
        }
        return fecha;
    }
    
}
