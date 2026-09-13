/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Servicios;

import com.mycompany.proyecto1ss2026.DAOs.HorarioDAO;
import com.mycompany.proyecto1ss2026.DAOs.RutaDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorExistenteException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInexistenteException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.HorarioDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.HorarioRequest;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioHorario {
    
    private final HorarioDAO horadao;
    private final RutaDAO rutadao;
    
    public ServicioHorario() {
        horadao = new HorarioDAO();
        rutadao = new RutaDAO();
    }
    
    public String agregarHorario(String textoHoraSalida, String textoHoraLlegada, String ruta) throws AccesoALaDataException, ValorExistenteException, ValorInvalidoException {
        HorarioRequest horario = filtarValoresHorario(textoHoraSalida, textoHoraLlegada, ruta);
        if (horadao.existeHorario(horario)) {
            throw new ValorExistenteException("Ya existe un horario con las horas de " + textoHoraSalida + " a " + textoHoraLlegada + " para la ruta " + ruta);
        }
        horadao.agregarHorario(horario);
        return "El horario de " + textoHoraSalida + " a " + textoHoraLlegada + " para la ruta " + ruta + " se ha agregado con exito";
    }   
    
    public List<HorarioDB> getHorariosRuta(String idRuta) throws AccesoALaDataException, ValorInexistenteException, ValorInvalidoException {
        if (idRuta == null || idRuta.isEmpty()) { 
            return null;
        }
        revisarExistenciaRuta(idRuta);
        return horadao.getHorariosRuta(idRuta);
    }
    
    public List<HorarioDB> getHorariosSucursales(String sucursalOrigen, String sucursalDestino) throws AccesoALaDataException, ValorInexistenteException, ValorInvalidoException {
        if (sucursalOrigen == null || sucursalDestino == null || sucursalOrigen.isEmpty() || sucursalDestino.isEmpty()) {
            return null;
        }
        return horadao.getHorariosSucursales(sucursalOrigen, sucursalDestino);
    }
    
    public void revisarExistenciaRuta(String idRuta) throws AccesoALaDataException, ValorInvalidoException {
        if (!rutadao.existeRuta(idRuta)) {
            throw new ValorInvalidoException("No se pudo encontrar la sucursal " + idRuta);
        }
    }
    
    public HorarioRequest filtarValoresHorario(String textoHoraSalida, String textoHoraLlegada, String ruta) throws AccesoALaDataException, ValorExistenteException, ValorInvalidoException {
        LocalTime horaSalida;
        LocalTime horaLlegada;
        try {
            horaSalida = LocalTime.parse(textoHoraSalida);
        } catch (DateTimeParseException e) {
            throw new ValorInvalidoException("El valor " + textoHoraSalida + " no es un valor valido para una hora");
        }
        try {
            horaLlegada = LocalTime.parse(textoHoraLlegada);
        } catch (DateTimeParseException e) {
            throw new ValorInvalidoException("El valor " + textoHoraLlegada + " no es un valor valido para una hora");
        }
        if (horaSalida.equals(horaLlegada)) {
            throw new ValorInvalidoException("La hora de llegada no puede ser la misma hora de salida");
        }
        if (horaSalida.isAfter(horaLlegada)) {
            throw new ValorInvalidoException("La hora de llegada no puede ser antes que la hora de salida");
        }
        revisarExistenciaRuta(ruta);
        return new HorarioRequest(horaSalida, horaLlegada, ruta);
    }
    
}
