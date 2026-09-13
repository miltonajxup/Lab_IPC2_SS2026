/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Servicios;

import com.mycompany.proyecto1ss2026.Constantes.Estado;
import com.mycompany.proyecto1ss2026.Constantes.Limite;
import com.mycompany.proyecto1ss2026.DAOs.BusDAO;
import com.mycompany.proyecto1ss2026.DAOs.SucursalDAO;
import com.mycompany.proyecto1ss2026.DAOs.ViajeDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorExistenteException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInexistenteException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.BusDB;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ViajeDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.BusRequest;
import com.mycompany.proyecto1ss2026.Respuesta.Respuesta;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioBus {
    
    private final int MINIMO_PASAJEROS = 1;
    private final int MINIMO_KILOMETRAJE = 0;
    private final BusDAO busdao;
    private final SucursalDAO sucursaldao;
    private final ViajeDAO viajedao;
    
    public ServicioBus() {
        busdao = new BusDAO();
        sucursaldao = new SucursalDAO();
        viajedao = new ViajeDAO();
    }
    
    public String agregarBus(String numeroPlaca, String marca, String modelo, String textoFechaFabricacion, String textoCantidadPasajeros, 
            String textoKilometraje, String sucursalBase) throws AccesoALaDataException, ValorExistenteException, ValorInvalidoException, ValorInexistenteException {
        if (numeroPlaca == null || marca == null || modelo == null || textoFechaFabricacion == null || textoCantidadPasajeros == null 
                || textoKilometraje == null || sucursalBase == null || numeroPlaca.isEmpty() || marca.isEmpty() || modelo.isEmpty() 
                || textoFechaFabricacion.isEmpty() || textoCantidadPasajeros.isEmpty() || textoKilometraje.isEmpty() || sucursalBase.isEmpty()) {
            return null;
        }
        BusRequest bus = filtrarBus(numeroPlaca, marca, modelo, textoFechaFabricacion, textoCantidadPasajeros, textoKilometraje, sucursalBase);
        if (busdao.existeBus(numeroPlaca)) {
            throw new ValorExistenteException("Ya existe registrado un bus con el numero de placa " + numeroPlaca);
        }
        busdao.agregarBus(bus);
        return "El bus " + numeroPlaca + " ha sido añadido con exito";
    }
    
    public String modificarBus(String numeroPlaca, String marca, String modelo, String textoFechaFabricacion, String textoCantidadPasajeros, 
            String textoKilometraje, String sucursalBase) throws AccesoALaDataException, ValorInexistenteException, ValorInvalidoException {
        if (numeroPlaca == null || marca == null || modelo == null || textoFechaFabricacion == null || textoCantidadPasajeros == null 
                || textoKilometraje == null || sucursalBase == null || numeroPlaca.isEmpty() || marca.isEmpty() || modelo.isEmpty() 
                || textoFechaFabricacion.isEmpty() || textoCantidadPasajeros.isEmpty() || textoKilometraje.isEmpty() || sucursalBase.isEmpty()) {
            return null;
        }
        BusRequest bus = filtrarBus(numeroPlaca, marca, modelo, textoFechaFabricacion, textoCantidadPasajeros, textoKilometraje, sucursalBase);
        existeBus(numeroPlaca);
        busdao.modificarBus(bus);
        return "El Bus " + numeroPlaca + " ha sigo modificado correctamente";
    }
    
    public Respuesta modificarEstado(String numeroPlaca, String textoEstado) throws AccesoALaDataException, ValorInvalidoException, ValorInexistenteException {
        if (numeroPlaca == null || textoEstado == null || numeroPlaca.isEmpty() || textoEstado.isEmpty()) {
            return null;
        }
        existeBus(numeroPlaca);
        if (!textoEstado.equalsIgnoreCase(Estado.FALSE.name()) && !textoEstado.equalsIgnoreCase(Estado.TRUE.name())) {
            throw new ValorInvalidoException("El valor " + textoEstado + " no es un valor validao para el estado");
        }
        
        boolean estado = textoEstado.equalsIgnoreCase(Estado.TRUE.name());
        
        if (!estado) {
            List<ViajeDB> viajesSinTerminar = viajedao.getViajesSinTerminar();
            for (ViajeDB viaje : viajesSinTerminar) {
                if (viaje.getBus().equals(numeroPlaca)) {
                    return new Respuesta(false, "No se puede desactivar este bus porque tiene un viaje pendiente ( id viaje: " + viaje.getId() + " )");
                }
            }
        }
            
        busdao.modificarEstadoBus(estado, numeroPlaca);
        return new Respuesta(true, "Se ha cambiado el estado del bus");
    }
    
    public BusDB getBus(String numeroPlaca) throws AccesoALaDataException, ValorInexistenteException {
        existeBus(numeroPlaca);
        return busdao.getBusId(numeroPlaca);
    }
    
    public List<BusDB> getBusesSucursalBase(String sucursalBase) throws AccesoALaDataException, ValorInexistenteException {
        existeSucursal(sucursalBase);
        return busdao.getBusSucursal(sucursalBase);
    }
    
    public List<BusDB> getBusesSucursalActual(String sucursalActual) throws AccesoALaDataException, ValorInexistenteException {
        existeSucursal(sucursalActual);
        return busdao.getBusSucursalActual(sucursalActual);
    }
    
    private void existeBus(String numeroPlaca) throws AccesoALaDataException, ValorInexistenteException {
        if (!busdao.existeBus(numeroPlaca)) {
            throw new ValorInexistenteException("No se pudo encontrar el bus con la placa " + numeroPlaca);
        }
    }
    
    private void existeSucursal(String sucursal) throws AccesoALaDataException, ValorInexistenteException {
        if (!sucursaldao.existeSucursal(sucursal)) {
            throw new ValorInexistenteException("No se pudo encontrar la sucursal " + sucursal);
        }
    }
    
    private BusRequest filtrarBus(String numeroPlaca, String marca, String modelo, String textoFechaFabricacion, String textoCantidadPasajeros, 
            String textoKilometraje, String sucursalBase) throws AccesoALaDataException, ValorInexistenteException, ValorInvalidoException {
        revisarTamaños(numeroPlaca, marca, modelo);
        LocalDate fechaFabricacion;
        int cantidadPasajeros;
        int kilometraje;
        try {
            fechaFabricacion = LocalDate.parse(textoFechaFabricacion);
        } catch (DateTimeParseException e) {
            throw new ValorInvalidoException("El valor " + textoFechaFabricacion + " no es válido para la fecha");
        }
        try {
            cantidadPasajeros = Integer.parseInt(textoCantidadPasajeros);
        } catch (NumberFormatException e) {
            throw new ValorInvalidoException("El valor " + textoCantidadPasajeros + " no es un valor valido para la cantidad de pasajeros");
        }
        try {
            kilometraje = Integer.parseInt(textoKilometraje);
        } catch (NumberFormatException e) {
            throw new ValorInvalidoException("El valor " + textoKilometraje + " no es un valor válido para el kilomentraje");
        }
        
        revisarLimite("La Cantidad de Pasajeros ", cantidadPasajeros, MINIMO_PASAJEROS);
        revisarLimite("El Kilometraje ", kilometraje, MINIMO_KILOMETRAJE);
        
        existeSucursal(sucursalBase);
        
        return new BusRequest(numeroPlaca, marca, modelo, fechaFabricacion, cantidadPasajeros, kilometraje, sucursalBase);
    }
    
    private void revisarTamaños(String numeroPlaca, String marca, String modelo) throws ValorInvalidoException {
        if (numeroPlaca.length() > Limite.NUMERO_PLACA.getTamañoLimite()) {
            throw new ValorInvalidoException("En Numero de Placa no puede tener mas de " + Limite.NUMERO_PLACA.getTamañoLimite() + " caracteres");
        }
        if (marca.length() > Limite.MARCA.getTamañoLimite()) {
            throw new ValorInvalidoException("El nombre de la Marca no puede tener mas de " + Limite.MARCA.getTamañoLimite() + " caracteres");
        }
        if (modelo.length() > Limite.MODELO.getTamañoLimite()) {
            throw new ValorInvalidoException("El codigo de Modelo no puede tener mas de " + Limite.MODELO.getTamañoLimite() + " caracteres");
        }
    }
    
    private void revisarLimite(String valor, int cantidad, int limite) throws ValorInvalidoException {
        if (cantidad < limite) {
            throw new ValorInvalidoException(valor + " no puede ser menor a " + limite);
        }
    }
    
}
