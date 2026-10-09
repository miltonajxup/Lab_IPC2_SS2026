/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimiteTamaño;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.RolUsuario;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador.EmpleadoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.UsuarioDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataExistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.EmpleadoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.UsuarioRequest;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioEmpleado {
    
    private final UsuarioDAO usuariodao;
    private final EmpleadoDAO empleadodao;
    
    public ServicioEmpleado() {
        usuariodao = new UsuarioDAO();
        empleadodao = new EmpleadoDAO();
    }
    
    public String crearEmpleado(UsuarioRequest usuario) throws AccesoALaDataException, ValorInvalidoException, DataExistenteException {
        if (usuario.getDpi() == null ||  usuario.getNombre() == null || usuario.getRol() == null || 
                usuario.getFechaContratacion() == null || usuario.getDpi().isEmpty() || usuario.getNombre().isEmpty()) {
            throw new ValorInvalidoException("No se pudo crear el usuario de empleado por falta de valores de creacion");
        }
        revisarTamaños(usuario);
        if (usuariodao.existeUsuarioPorDpi(usuario.getDpi())) {
            throw new DataExistenteException("Ya existe un usuario con el DPI " + usuario.getDpi() + " registrado en el sistema");
        }
        if (usuariodao.existeUsuarioPorNombre(usuario.getNombre())) {
            throw new DataExistenteException("Ya existe un usuario con el Nombre " + usuario.getNombre() + " registrado en el sistema");
        }
        empleadodao.agregarEmpleado(usuario);
        return "Se ha creado y contratado al usuario " + usuario.getNombre() + " con exito";
    }
    
    public String contratarEmpleado(UsuarioRequest usuario) throws AccesoALaDataException, DataInexistenteException, DataExistenteException {
        if (!usuariodao.existeUsuarioPorDpi(usuario.getDpi())) {
            throw new DataInexistenteException("No se pudo encontrar a un empleado con el dpi " + usuario.getDpi() + " en el sistema");
        }
        if (empleadodao.existeContrato(usuario.getDpi())) {
            throw new DataExistenteException("El usuario " + usuario.getNombre() + " ya tiene un contrato para el año vigente");
        }
        empleadodao.contratarUsurario(usuario);
        return "Se ha re contratado al usuario " + usuario.getNombre();
    }
    
    public List<EmpleadoDB> buscarEmpleadosPorRol(String rol, int pagina) throws AccesoALaDataException, ValorInvalidoException {
        try {
            RolUsuario.valueOf(rol);
        } catch (IllegalArgumentException e) {
            throw new ValorInvalidoException("El valor " + rol + " no es un Rol de Usuario valido");
        }
        if (pagina < LimitePagina.PAGINA_MINIMA) {
            throw new ValorInvalidoException("El valor de la pagina no puede ser menor que " + LimitePagina.PAGINA_MINIMA);
        }
        return empleadodao.getEmpleadosPorRol(rol, pagina);
    }
    
    public EmpleadoDB buscarEmpleadoPorDpi(String dpiEmpleado) throws AccesoALaDataException, DataInexistenteException {
        if (!usuariodao.existeUsuarioPorDpi(dpiEmpleado)) {
            throw new DataInexistenteException("No existe un empleado con el dpi " + dpiEmpleado);
        }
        return empleadodao.getEmpleadoPorID(dpiEmpleado);
    }
    
    private void revisarTamaños(UsuarioRequest usuario) throws ValorInvalidoException {
        if (usuario.getNombre().length() > LimiteTamaño.NOMBRE.getLimite()) {
            throw new ValorInvalidoException("El nombre no puede tener mas de " + LimiteTamaño.NOMBRE.getLimite() + " caracteres");
        }
    }
    
}
