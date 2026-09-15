/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Servicios;

import com.mycompany.proyecto1ss2026.Constantes.Estado;
import com.mycompany.proyecto1ss2026.Constantes.Limite;
import com.mycompany.proyecto1ss2026.Constantes.TipoLicencia;
import com.mycompany.proyecto1ss2026.DAOs.ChoferDAO;
import com.mycompany.proyecto1ss2026.DAOs.SucursalDAO;
import com.mycompany.proyecto1ss2026.DAOs.ViajeDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorExistenteException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInexistenteException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ChoferDB;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ViajeDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.Chofer;
import com.mycompany.proyecto1ss2026.Respuesta.Respuesta;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioChofer {
    
    private final ChoferDAO choferdao;
    private final SucursalDAO sucursaldao;
    private final ViajeDAO viajedao;

    public ServicioChofer() {
        choferdao = new ChoferDAO();
        sucursaldao = new SucursalDAO();
        viajedao = new ViajeDAO();
    }
    
    public String agregarChofer(String nombre, /*InputStream imagen, */String numeroLicencia, 
            String textoTipoLicencia, String fechaVencimiento, String numeroTelefono, String textoSalario, 
            String sucursal) throws ValorInvalidoException, AccesoALaDataException, ValorExistenteException {
        if (nombre == null || numeroLicencia == null || textoTipoLicencia == null || fechaVencimiento == null 
                    || numeroTelefono == null || textoSalario == null || sucursal == null || nombre.isEmpty() 
                    || numeroLicencia.isEmpty() || textoTipoLicencia.isEmpty() || fechaVencimiento.isEmpty() 
                    || numeroTelefono.isEmpty() || textoSalario.isEmpty() || sucursal.isEmpty()) {
            return null;
        }
        Chofer chofer = armarChofer(nombre, numeroLicencia, textoTipoLicencia, fechaVencimiento, numeroTelefono, textoSalario, sucursal);
        if (choferdao.existeChofer(chofer.getNumeroLicencia())) {
            throw new ValorExistenteException("Ya existe registrado un chofer con el numero de licencia: " + chofer.getNumeroLicencia());
        }
        choferdao.agregarChofer(chofer);
        return "Se ha agregado correctamente el chofer " + chofer.getNombre() + " con el numero de licencia: " + chofer.getNumeroLicencia();
    }
    
    public String editarChofer(String dpi, String nombre, /*InputStream imagen, */String numeroLicencia, 
            String textoTipoLicencia, String fechaVencimiento, String numeroTelefono, String textoSalario, 
            String sucursal) throws AccesoALaDataException, ValorInvalidoException, ValorInexistenteException {
        if (nombre == null || numeroLicencia == null || textoTipoLicencia == null || fechaVencimiento == null 
                    || numeroTelefono == null || textoSalario == null || sucursal == null || nombre.isEmpty() 
                    || numeroLicencia.isEmpty() || textoTipoLicencia.isEmpty() || fechaVencimiento.isEmpty() 
                    || numeroTelefono.isEmpty() || textoSalario.isEmpty() || sucursal.isEmpty()) {
            return null;
        }
        Chofer chofer = choferParaModificar(nombre, numeroLicencia, textoTipoLicencia, fechaVencimiento, numeroTelefono, textoSalario, sucursal);
        existeChofer(dpi);
        choferdao.editarInfoChofer(chofer);
        return "Se ha actualizado el chofer de forma correcta";
    }
    
    public void actualizarLocalizacionChofer(String dpiChofer, String sucursalDestino) throws AccesoALaDataException, ValorInvalidoException, ValorInexistenteException {
        existeChofer(dpiChofer);
        existeSucursal(sucursalDestino);
        choferdao.actualizarSucursalActualChofer(dpiChofer, sucursalDestino);
    }
    
    private void existeChofer(String numeroLicencia) throws AccesoALaDataException, ValorInexistenteException {
        if (!choferdao.existeChofer(numeroLicencia)) {
            throw new ValorInexistenteException("No se pudo encontrar al chofer con el Numero de Licencia " + numeroLicencia);
        }
    }
    
    private void existeSucursal(String sucursal) throws AccesoALaDataException, ValorInexistenteException {
        if (!sucursaldao.existeSucursal(sucursal)) {
            throw new ValorInexistenteException("No se pudo encontrar la sucursal " + sucursal);
        }
    }
    
    public Respuesta modificarEstadoChofer(String numeroLicencia, String textoEstado) throws AccesoALaDataException, ValorInvalidoException, ValorInexistenteException {
        if (textoEstado == null || textoEstado.isEmpty() || numeroLicencia == null || numeroLicencia.isEmpty()) {
            return null;
        }
        existeChofer(numeroLicencia);
        if (!textoEstado.equalsIgnoreCase(Estado.FALSE.name()) && !textoEstado.equalsIgnoreCase(Estado.TRUE.name())) {
            throw new ValorInvalidoException("El valor " + textoEstado + " no es valido para el estado");
        }
        boolean estado = textoEstado.equalsIgnoreCase(Estado.TRUE.name());
        
        if (!estado) {
            List<ViajeDB> viajesSinTerminar = viajedao.getViajesSinTerminar();
            for (ViajeDB viaje : viajesSinTerminar) {
                if (viaje.getChofer().equals(numeroLicencia)) {
                    return new Respuesta(false, "No se pude desactivar el chofer porque tiene un viaje sin terminar");
                }
            }
        }
        
        choferdao.cambiarEstadoChofer(estado, numeroLicencia);
        return new Respuesta(true, "Se ha cambiado el estado del chofer");
    }
    
    public Chofer choferParaModificar(String nombre, /*InputStream imagen, */String numeroLicencia, String textoTipoLicencia, String fechaVencimiento, String numeroTelefono, String textoSalario, String sucursal) throws AccesoALaDataException, ValorInexistenteException, ValorInvalidoException {
        existeChofer(numeroLicencia);
        return armarChofer(nombre, numeroLicencia, textoTipoLicencia, fechaVencimiento, numeroTelefono, textoSalario, sucursal);
    }
    
    public List<ChoferDB> getChoferesSucursalBase(String sucursal) throws AccesoALaDataException, ValorInexistenteException {
        if (sucursal == null || sucursal.isEmpty()) {
            throw new ValorInexistenteException("No fue encontrado el nombre de la sucursal");
        }
        if (!sucursaldao.existeSucursal(sucursal)) {
            throw new ValorInexistenteException("La sucursal " + sucursal + " no ha sido encontrada");
        }
        return choferdao.choferesSucursalBase(sucursal);
    }
    
    public List<ChoferDB> getChoferesSucursalActual(String sucursal) throws AccesoALaDataException, ValorInexistenteException {
        if (sucursal == null || sucursal.isEmpty()) {
            throw new ValorInexistenteException("No fue encontrado el nombre de la sucursal");
        }
        if (!sucursaldao.existeSucursal(sucursal)) {
            throw new ValorInexistenteException("La sucursal " + sucursal + " no ha sido encontrada");
        }
        return choferdao.choferesSucursalActual(sucursal);
    }
    
    private Chofer armarChofer(String nombre, /*InputStream imagen, */String numeroLicencia, 
            String textoTipoLicencia, String textofechaVencimiento, String numeroTelefono, 
            String textoSalario, String sucursal) throws ValorInvalidoException {
        validarTamaños(nombre, numeroLicencia, numeroTelefono);
        TipoLicencia tipoLicencia;
        LocalDate fechaVencimiento;
        double salario;
        try {
            tipoLicencia = TipoLicencia.valueOf(textoTipoLicencia);
            fechaVencimiento = LocalDate.parse(textofechaVencimiento);
            salario = Double.parseDouble(textoSalario);
        } catch (IllegalArgumentException | DateTimeParseException e) {
            throw new ValorInvalidoException("Se intentan agregar valores invalidos a un Chofer " + e.getMessage());
        }
        
        if (salario < 1) {
            throw new ValorInvalidoException("El valor del salario debe ser de al menos Q1");
        }
        
        return new Chofer(nombre, numeroLicencia, tipoLicencia, fechaVencimiento, numeroTelefono, salario, sucursal);
    }
    
    private void validarTamaños(String nombre, String numeroLicencia, String numeroTelefono) throws ValorInvalidoException {
        if (nombre.length() > Limite.NOMBRE.getTamañoLimite()) {
            throw new ValorInvalidoException("El Nombre no puede tener mas de " + Limite.NOMBRE.getTamañoLimite() + " caracteres");
        }
        if (numeroLicencia.length() > Limite.NUMERO_LICENCIA.getTamañoLimite()) {
            throw new ValorInvalidoException("El Numero de Licencia no puede tener mas de " + Limite.NUMERO_LICENCIA.getTamañoLimite() + " caracteres");
        }
        if (numeroTelefono.length() > Limite.TELEFONO.getTamañoLimite()) {
            throw new ValorInvalidoException("El telefono no puede tener mas de " + Limite.TELEFONO.getTamañoLimite() + " caracteres");
        }
    }
    
}
