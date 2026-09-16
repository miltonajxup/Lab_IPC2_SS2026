/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Servicios;

import com.mycompany.proyecto1ss2026.Constantes.Estado;
import com.mycompany.proyecto1ss2026.Constantes.Limite;
import com.mycompany.proyecto1ss2026.Constantes.RolUsuario;
import com.mycompany.proyecto1ss2026.DAOs.SucursalDAO;
import com.mycompany.proyecto1ss2026.DAOs.UsuarioDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorExistenteException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInexistenteException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.Usuario;
import com.mycompany.proyecto1ss2026.Respuesta.Respuesta;

/**
 *
 * @author milton
 */
public class ServicioUsuario {
    
    private final int MINIMO_CREDITOS = 1;
    private final int MINIMO_ADMINISTRADORES = 1;
    private final UsuarioDAO usuariodao;
    private final SucursalDAO sucursaldao;

    public ServicioUsuario() {
        usuariodao = new UsuarioDAO();
        sucursaldao = new SucursalDAO();
    }
    
    public Respuesta agregarUsuario(String dpi, String nombre, String nit, String telefono, String direccion) throws ValorInvalidoException, AccesoALaDataException, ValorExistenteException {
        if (valoresInvalidos(dpi, nombre, nit, telefono, direccion)) {
            return null;
        }
        Usuario usuario = filtrosCreacionUsuario(dpi, nombre, nit, telefono, direccion, RolUsuario.CLIENTE.name());
        usuariodao.agregarUsuario(usuario);
        return new Respuesta(true, "El usuario " + usuario.getNombre() + " ha sido creado con exito");
    }
    
    public Respuesta agregarUsuarioPorAdmin(String dpi, String nombre, String nit, String telefono, String direccion, String textoRol, String sucursal) 
            throws ValorInvalidoException, AccesoALaDataException, ValorExistenteException, ValorInexistenteException {
        if (valoresInvalidos(dpi, nombre, nit, telefono, direccion, textoRol)) {
            return null;
        }
        Usuario usuario = filtrosCreacionUsuario(dpi, nombre, nit, telefono, direccion, textoRol);
        necesitaSucursal(usuario, sucursal);
        usuariodao.agregarUsuario(usuario);
        return new Respuesta(true, "El usuario " + usuario.getNombre() + " ha sido creado con exito");
    }
    
    private Usuario filtrosCreacionUsuario(String dpi, String nombre, String nit, String telefono, String direccion, String textoRol) throws AccesoALaDataException, ValorExistenteException, ValorInvalidoException {
        Usuario usuario = armarUsuario(dpi, nombre, nit, telefono, direccion, textoRol);
        if (usuariodao.existeDpiUsuario(usuario.getDpi())) {
            throw new ValorExistenteException("Ya se encuentra registrado un usuario con el dpi " + usuario.getDpi());
        }
        return usuario;
    }
    
    public String modificarUsuario(String dpi, String nombre, String nit, String telefono, String direccion) throws ValorInvalidoException, AccesoALaDataException, ValorExistenteException, ValorInexistenteException {
        if (valoresInvalidos(dpi, nombre, nit, telefono, direccion)) {
            return null;
        }
        Usuario usuario = usuarioParaModificar(dpi, nombre, nit, telefono, direccion, RolUsuario.CLIENTE.name());
        usuariodao.editarUsuario(usuario);
        return "El usuario " + usuario.getNombre() + " con dpi " + usuario.getDpi()+ " ha sido modificado con exito";
    }
    
    public String modificacionPorAdmin(String dpi, String nombre, String nit, String telefono, String direccion, String textoRol, String sucursal) throws ValorInvalidoException, AccesoALaDataException, ValorExistenteException, ValorInexistenteException {
        if (valoresInvalidos(dpi, nombre, nit, telefono, direccion, textoRol)) {
            return null;
        }
        Usuario usuario = usuarioParaModificar(dpi, nombre, nit, telefono, direccion, textoRol);
        necesitaSucursal(usuario, sucursal);
        usuariodao.editarUsuario(usuario);
        return "El usuario " + usuario.getNombre() + " con dpi " + usuario.getDpi()+ " ha sido modificado con exito";
    }
    
    private Usuario usuarioParaModificar(String dpi, String nombre, String nit, String telefono, String direccion, String textoRol) throws ValorInvalidoException, AccesoALaDataException, ValorInexistenteException {
        Usuario usuario = armarUsuario(dpi, nombre, nit, telefono, direccion, textoRol);
        existenciaUsuario(dpi);
        return usuario;
    }
    
    public void modificarEstadoUsuario(String dpiUsuario, String textoEstado) throws AccesoALaDataException, ValorInexistenteException, ValorInvalidoException {
        if (dpiUsuario == null || textoEstado == null || dpiUsuario.isEmpty() || textoEstado.isEmpty()) {
            return;
        }
        if (!textoEstado.equalsIgnoreCase(Estado.FALSE.name()) && !textoEstado.equalsIgnoreCase(Estado.TRUE.name())) {
            throw new ValorInvalidoException("El valor " + textoEstado + " del estado es invalido");
        }
        existenciaUsuario(dpiUsuario);
        boolean estado = textoEstado.equalsIgnoreCase(Estado.TRUE.name());
        if (!estado) {
            int usuariosActivos = usuariodao.getCantidadAdministradoresActivos();
            if (usuariosActivos <= MINIMO_ADMINISTRADORES) {
                throw new ValorInvalidoException("No se pueden dejar menos de " + MINIMO_ADMINISTRADORES + " administrador activo");
            }
        }
        usuariodao.modificarEstado(estado, dpiUsuario);
    }
    
    public String recargarCreditos(String dpi, String textoCredito) throws AccesoALaDataException, ValorInexistenteException, ValorInvalidoException {
        if (dpi == null || textoCredito == null || dpi.isEmpty() || textoCredito.isEmpty()) {
            return null;
        }
        existenciaUsuario(dpi);
        double credito;
        try {
            credito = Double.parseDouble(textoCredito);
        } catch (NumberFormatException e) {
            throw new ValorInvalidoException("El valor " + textoCredito + " no es valido para credito");
        }
        if (credito < MINIMO_CREDITOS) {
            throw new ValorInexistenteException("El credito que se agrega tiene que ser al menos " + MINIMO_CREDITOS + "Q");
        }
        UsuarioDB usuariodb = usuariodao.getUsuarioPorDpi(dpi);
        double creditoActual = usuariodb.getCreditoDisponible() + credito;
        usuariodao.agregarCreditos(creditoActual, dpi);
        return "Se ha recargado " + credito + " de saldo ahora se cuenta con " + creditoActual + " de saldo";
    }
    
    private void existenciaUsuario(String dpiUsuario) throws AccesoALaDataException, ValorInexistenteException {
        if (!usuariodao.existeDpiUsuario(dpiUsuario)) {
            throw new ValorInexistenteException("El usuario con dpi: " + dpiUsuario + " no existe registrado en la aplicacion");
        }
    }
    
    private Usuario armarUsuario(String dpi, String nombre, String nit, String telefono, String direccion, String textoRol) throws ValorInvalidoException {
        RolUsuario rol;
        try {
            rol = RolUsuario.valueOf(textoRol);
        } catch (IllegalArgumentException e) {
            throw new ValorInvalidoException(textoRol + " no es un valor valido par rol");
        }
        Usuario usuario = new Usuario(dpi, nombre, nit, telefono, direccion, rol);
        validarTamaños(usuario);
        return usuario;
    }
    
    private void validarTamaños(Usuario usuario) throws ValorInvalidoException {
        if (usuario.getDpi().length() > Limite.DPI.getTamañoLimite()) {
            throw new ValorInvalidoException("El dpi no puede tener mas de " + Limite.DPI.getTamañoLimite() + " caracteres");
        }
        if (usuario.getNombre().length() > Limite.NOMBRE.getTamañoLimite()) {
            throw new ValorInvalidoException("El nombre no puede tener mas de " + Limite.NOMBRE.getTamañoLimite() + " caracteres");
        }
        if (usuario.getNit().length() > Limite.NIT.getTamañoLimite()) {
            throw new ValorInvalidoException("El nit no puede tener mas de " + Limite.NIT.getTamañoLimite() + " caracteres");
        }
        if (usuario.getTelefono().length() > Limite.TELEFONO.getTamañoLimite()) {
            throw new ValorInvalidoException("El telefono no puede tener mas de " + Limite.TELEFONO.getTamañoLimite() + " caracteres");
        }
        if (usuario.getDireccion().length() > Limite.DIRECCION.getTamañoLimite()) {
            throw new ValorInvalidoException("La direccion no puede tener mas de " + Limite.DIRECCION.getTamañoLimite() + " caracteres");
        }
    }
    
    private void necesitaSucursal(Usuario usuario, String sucursal) throws AccesoALaDataException, ValorInvalidoException, ValorInexistenteException {
        if (usuario.getRol() == RolUsuario.ADMINISTRADOR_SUCURSAL) {
            if (sucursal == null || sucursal.isEmpty()) {
                throw new ValorInvalidoException("Al ser un ADMINISTRADOR DE SUCURSAL Falta especificar a que sucursal pertenece");
            }
            if (!sucursaldao.existeSucursal(sucursal)) {
                throw new ValorInexistenteException("No se pudo encontrar la sucursal " + sucursal);
            }
            usuario.setSucursal(sucursal);
        }
    }
    
    private boolean valoresInvalidos(String dpi, String nombre, String nit, String telefono, String direccion) {
        return dpi == null || nombre == null || nit == null || telefono == null || direccion == null 
                || dpi.isEmpty() || nombre.isEmpty() || nit.isEmpty() || telefono.isEmpty() || direccion.isEmpty();
    }
    
    private boolean valoresInvalidos(String dpi, String nombre, String nit, String telefono, String direccion, String textoRol) {
        return dpi == null || nombre == null || nit == null || telefono == null || direccion == null || textoRol == null  
                || dpi.isEmpty() || nombre.isEmpty() || nit.isEmpty() || telefono.isEmpty() || direccion.isEmpty() || textoRol.isEmpty();
    }
}
