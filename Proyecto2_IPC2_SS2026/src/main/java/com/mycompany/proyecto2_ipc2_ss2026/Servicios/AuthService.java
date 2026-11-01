/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.RolUsuario;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador.EmpleadoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Estudiante.EstudianteDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.UsuarioDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.EmpleadoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.RespuestaLog;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.UsuarioCompleto;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.UsuarioDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.LoginRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Set;

/**
 *
 * @author milton
 */
public class AuthService {
    
    private final String MENSAJE_INGRESO = "No se pudo Acceder";
    
    public RespuestaLog ingresar(LoginRequest usuario) throws AccesoALaDataException {
        UsuarioDAO usuariodao = new UsuarioDAO();
        EstudianteDAO estudiantedao = new EstudianteDAO();
        EmpleadoDAO empleadodao = new EmpleadoDAO();
        if (verificarValores(usuario)) {
            return new RespuestaLog(false, MENSAJE_INGRESO);
        }
        
        UsuarioCompleto usuarioCompleto = usuariodao.usuarioCompletoPorNombre(usuario.getNombreUsuario());
        
        if (usuarioCompleto == null) {
            return new RespuestaLog(false, MENSAJE_INGRESO);
        }
        
        if (usuarioCompleto.getContraseña() == null) {
            return new RespuestaLog(false, "El usuario no ha sido activado");
        }
        
        String contraseñaEntrante = Base64.getEncoder().encodeToString(usuario.getContrasenia().getBytes(StandardCharsets.UTF_8));
        
        if (!usuarioCompleto.getContraseña().equals(contraseñaEntrante) ) {
            return new RespuestaLog(false, MENSAJE_INGRESO);
        }
        
        Set<RolUsuario> roles = agregarRoles(usuarioCompleto);
        UsuarioDB usuarioLogeado;
        
        if (usuarioCompleto.getRol() == RolUsuario.ESTUDIANTE) {
            usuarioLogeado = estudiantedao.getEstudiantePorID(usuarioCompleto.getDpi());
        } else {
            EmpleadoDB empleado = empleadodao.getEmpleadoSinContrato(usuarioCompleto.getDpi());
            if (empleado.isEstado()) {
                usuarioLogeado = empleado;
            } else {
                return new RespuestaLog(false, "No se pudo acceder porque el usuario ha sido bloqueado");
            }
        }
        usuarioLogeado.setRoles(roles);
        
        JwtTokenService tokenService = new JwtTokenService();
        String token = tokenService.generar(usuarioLogeado);
        return new RespuestaLog(true, token);
    }
    
    private boolean verificarValores(LoginRequest usuario) {
        return usuario == null || usuario.getContrasenia() == null || usuario.getNombreUsuario()== null || usuario.getNombreUsuario().isEmpty();
    }
    
    private Set<RolUsuario> agregarRoles(UsuarioCompleto usuario) {
        if (usuario.getRol() == RolUsuario.SUPER_ADMINISTRADOR) {
            return Set.of(RolUsuario.SUPER_ADMINISTRADOR, RolUsuario.ADMINISTRADOR, RolUsuario.BIBLIOTECARIO, 
                    RolUsuario.ESTUDIANTE, RolUsuario.MAESTRO, RolUsuario.SECRETARIA);
        }
        return Set.of(usuario.getRol());
    }
    
}
