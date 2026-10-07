/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.DAOs.SuperAdministradorDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataExistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.AñoLectivoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.AñoLectivoRequest;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioSuperAdmin {
    
    
    private final SuperAdministradorDAO superdao;
    
    public ServicioSuperAdmin() {
        superdao = new SuperAdministradorDAO();
    }
    
    public List<AñoLectivoDB> crearAñoLectivo(AñoLectivoRequest añoLectivo) throws AccesoALaDataException, ValorInvalidoException, DataExistenteException {
        if (añoLectivo == null || añoLectivo.getAño() == 0 || añoLectivo.getFechaInicio() == null || añoLectivo.getFechaFin() == null) {
            throw new ValorInvalidoException("Falta especificar los valores para la creacion de un nuevo año lectivo");
        }
        
        revisarFecha(añoLectivo.getFechaInicio(), añoLectivo.getFechaFin());
        
        if (superdao.existeAñoLectivo(añoLectivo.getAño())) {
            throw new DataExistenteException("El año que se quiere agregar ya existe en los registros del colegio");
        }
        return superdao.crearAñoLectivo(añoLectivo);
    }
    
    public AñoLectivoDB modificarAñoLectivo(AñoLectivoRequest añoLectivo, int año) throws AccesoALaDataException, ValorInvalidoException, DataInexistenteException {
        if (añoLectivo == null || añoLectivo.getFechaInicio() == null || añoLectivo.getFechaFin() == null) {
            throw new ValorInvalidoException("Falta especificar los valores para la creacion de un nuevo año lectivo");
        }
        
        revisarFecha(añoLectivo.getFechaInicio(), añoLectivo.getFechaFin());
        
        if (!superdao.existeAñoLectivo(año)) {
            throw new DataInexistenteException("El año que se quiere modificar no existe");
        }
        return superdao.modificarAñoLectivo(añoLectivo, año);
    }
    
    public List<AñoLectivoDB> activarAñoLectivo(int año) throws AccesoALaDataException, DataInexistenteException {
        if (!superdao.existeAñoLectivo(año)) {
            throw new DataInexistenteException("El año que se quiere activar no existe");
        }
        return superdao.modificarEstadoAñoLectivo(año);
    }
    
    private void revisarFecha(LocalDate fechaInicio, LocalDate fechaFin) throws ValorInvalidoException {
        if (fechaInicio.isAfter(fechaFin)) {
            throw new ValorInvalidoException("La fecha de inicio no puede ser posterior a la de finalizacion");
        }
    }
    
}
