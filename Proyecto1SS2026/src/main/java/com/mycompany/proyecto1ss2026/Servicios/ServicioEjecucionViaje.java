/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Servicios;

import com.mycompany.proyecto1ss2026.Constantes.Estado;
import com.mycompany.proyecto1ss2026.DAOs.BusDAO;
import com.mycompany.proyecto1ss2026.DAOs.ChoferDAO;
import com.mycompany.proyecto1ss2026.DAOs.DepreciacionDAO;
import com.mycompany.proyecto1ss2026.DAOs.EjecucionViajeDAO;
import com.mycompany.proyecto1ss2026.DAOs.ViajeDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInexistenteException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.BusDB;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ChoferDB;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.DepreciacionDB;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ViajeDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.ConclusionViaje;
import com.mycompany.proyecto1ss2026.Modelos.Request.DepreciacionBus;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 *
 * @author milton
 */
public class ServicioEjecucionViaje {
    
    private final double MINIMO_GASTO = 1;
    private final double BONO_VIAJE_PRIVADO = 1.15;
    private final EjecucionViajeDAO ejecuciondao;
    private final ViajeDAO viajedao;
    private final BusDAO busdao;
    private final ChoferDAO choferdao;
    private final DepreciacionDAO depreciaciondao;
    
    public ServicioEjecucionViaje() {
        ejecuciondao = new EjecucionViajeDAO();
        viajedao = new ViajeDAO();
        busdao = new BusDAO();
        choferdao = new ChoferDAO();
        depreciaciondao = new DepreciacionDAO();
    }
    
    public void generarRegistroEjecucion(String idViaje, String textoIniciar) throws AccesoALaDataException, ValorInexistenteException {
        if (idViaje == null || textoIniciar == null 
                || idViaje.isEmpty() || textoIniciar.isEmpty() || !textoIniciar.equalsIgnoreCase(Estado.TRUE.name())) {
            return;
        }
        
        ViajeDB viaje = existeViaje(idViaje);
        if (viaje.isComenzado()) {
            return;
        }
        BusDB bus = busdao.getBusId(viaje.getBus());
        ejecuciondao.agregarViajeEjecucion(bus.getKilometraje(), viaje.getId());
    }
    
    public void actualizarRegistroEjecucion(String textoKilometroLlegada, String textoGastoCombustible, String idViaje, 
            String textoFechaRegistro) throws AccesoALaDataException, ValorInvalidoException, ValorInexistenteException {
        
        if (textoKilometroLlegada == null || textoGastoCombustible == null || idViaje == null || textoFechaRegistro == null 
                || textoKilometroLlegada.isEmpty() || textoGastoCombustible.isEmpty() || idViaje.isEmpty() || textoFechaRegistro.isEmpty()) {
            return;
        }
        
        int kilometroLlegada;
        double gastoCombustible;
        try {
            kilometroLlegada = Integer.parseInt(textoKilometroLlegada);
        } catch (NumberFormatException e) {
            throw new ValorInvalidoException("El valor " + textoKilometroLlegada + " no es un valor valido para Kilometro de Llegada");
        }
        try {
            gastoCombustible = Double.parseDouble(textoGastoCombustible);
        } catch (NumberFormatException e) {
            throw new ValorInvalidoException("El valor " + textoGastoCombustible + " no es un valor validgo para el gasto de combustible");
        }
        
        ViajeDB viaje = existeViaje(idViaje);
        BusDB bus = busdao.getBusId(viaje.getBus());
        if (kilometroLlegada < bus.getKilometraje()) {
            throw new ValorInvalidoException("El kilometraje actual no puede ser menor al kilometraje de salida ( " + bus.getKilometraje() + " km )");
        }
        if (gastoCombustible < MINIMO_GASTO) {
            throw new ValorInvalidoException("El gasto del combustible no puede ser menor a " + MINIMO_GASTO);
        }
        
        ChoferDB chofer = choferdao.getChoferId(viaje.getChofer());
        DepreciacionBus depreciacion = armarDepreciacionRequest(textoFechaRegistro, kilometroLlegada, bus);
        double saldoChofer = chofer.getSaldoDisponible();
        boolean esViajePublico = viajedao.esViajePublico(viaje.getId());
        if (esViajePublico) {
            saldoChofer += chofer.getSalarioPorViaje();
        } else {
            saldoChofer += (chofer.getSalarioPorViaje() * BONO_VIAJE_PRIVADO);
        }
        
        ConclusionViaje conclusion = new ConclusionViaje(kilometroLlegada, gastoCombustible, idViaje, bus.getNumeroPlaca(), chofer.getNumeroLicencia(), saldoChofer, viaje.getSucursalDestino());
        ejecuciondao.modificarViajeEjecucion(conclusion, depreciacion);
        
    }
    
    private DepreciacionBus armarDepreciacionRequest(String textoFechaRegistro, int kilometrajeLlegada, BusDB bus) throws AccesoALaDataException, ValorInvalidoException {
        LocalDate fechaRegistro;
        try {
            fechaRegistro = LocalDate.parse(textoFechaRegistro);
        } catch (DateTimeParseException e) {
            throw new ValorInvalidoException("El valor " + textoFechaRegistro + " no es valido para la fecha");
        }
        DepreciacionDB depreciacion = depreciaciondao.ultimoValorDepreciacion();
        int kilometrosRecorridos = kilometrajeLlegada - bus.getKilometraje();
        double montoDepreciado = depreciacion.getMontoDepreciacion() * kilometrosRecorridos;
        return new DepreciacionBus(fechaRegistro, kilometrosRecorridos, depreciacion.getIdDepreciacion(), montoDepreciado, bus.getNumeroPlaca());
    }
    
    private ViajeDB existeViaje(String idViaje) throws AccesoALaDataException, ValorInexistenteException {
        ViajeDB viaje = viajedao.getViajesSinTerminarId(idViaje);
        if (viaje == null) {
            throw new ValorInexistenteException("No se pudo encontrar el viaje con el id " + idViaje);
        }
        return viaje;
    }
    
}
