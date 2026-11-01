/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimiteTamaño;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.LoginDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.UsuarioDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataExistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.RespuestaLog;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.UsuarioCompleto;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.UsuarioRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioLogin {
    
    private final String MENSAJE_INGRESO = "No se pudo Acceder";
    
    private final LoginDAO logindao;
    private final UsuarioDAO usuariodao;
    
    public ServicioLogin() {
        logindao = new LoginDAO();
        usuariodao = new UsuarioDAO();
    }
    
    public RespuestaLog colocarContraseña(UsuarioRequest usuario) throws AccesoALaDataException, ValorInvalidoException, DataExistenteException {
        if (verificarValores(usuario) || usuario.getDpi() == null) {
            return new RespuestaLog(false, MENSAJE_INGRESO);
        }
        UsuarioCompleto usuarioCompleto = usuariodao.usuarioCompletoPorNombre(usuario.getNombre());
        if (usuarioCompleto == null) {
            return new RespuestaLog(false, MENSAJE_INGRESO);
        }
        if (!usuarioCompleto.getDpi().equals(usuario.getDpi())) {
            return new RespuestaLog(false, MENSAJE_INGRESO);
        }
        if (usuarioCompleto.getContraseña() != null) {
            throw new DataExistenteException("Esta opcion solo esta disponible para cuentas que aun no tengan contraseña");
        }
        return modificarContraseña(usuario, usuarioCompleto.getDpi());
    }
    
    public RespuestaLog modificarContraseña(UsuarioRequest usuario, String idUsuario) throws AccesoALaDataException, ValorInvalidoException {
        if (usuario.getContrasenia() == null) {
            throw new ValorInvalidoException("No se ha agregado la contraseña");
        }
        
        if (usuario.getContrasenia().length() < LimiteTamaño.MINIMO_CONTRASEÑA.getLimite()) {
            throw new ValorInvalidoException("La contraseña tiene que tener un minimo de " + LimiteTamaño.MINIMO_CONTRASEÑA.getLimite() + " caracteres");
        }
        
        String contraseñaCodificada = Base64.getEncoder().encodeToString(usuario.getContrasenia().getBytes(StandardCharsets.UTF_8));
        logindao.modificarContraseña(contraseñaCodificada, idUsuario);
        
        return new RespuestaLog(true, "La contraseña se ha establecido de forma exitosa");
    }
    
    public RespuestaLog accesoRecuperarContraseña(UsuarioRequest usuario) throws AccesoALaDataException {
        if (verificarValores(usuario)) {
            return new RespuestaLog(false, MENSAJE_INGRESO);
        }
        
        UsuarioCompleto usuariodb = usuariodao.usuarioCompletoPorNombre(usuario.getNombre());
        if (usuariodb == null) {
            return new RespuestaLog(false, MENSAJE_INGRESO);
        }
        if (!usuariodb.getNombre().equals(usuario.getNombre())) {
            return new RespuestaLog(false, MENSAJE_INGRESO);
        }
        
        List<String> contraseñas = logindao.getContraseñasGuardas(usuario.getDpi());
        
        String contraseñaCodificada = Base64.getEncoder().encodeToString(usuario.getContrasenia().getBytes(StandardCharsets.UTF_8));
        
        for (String contraseña : contraseñas) {
            if (contraseñaCodificada.equals(contraseña)) {
                return new RespuestaLog(true, "Se encontró una contraseña que coincide");
            }
        }
        return new RespuestaLog(false, MENSAJE_INGRESO);
    }
    
    private boolean verificarValores(UsuarioRequest usuario) {
        return usuario == null || usuario.getContrasenia() == null || usuario.getNombre() == null || usuario.getNombre().isEmpty();
    }
    
}
