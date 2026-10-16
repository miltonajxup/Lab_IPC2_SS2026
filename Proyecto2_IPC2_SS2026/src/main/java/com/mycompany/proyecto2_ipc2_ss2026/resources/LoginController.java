/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.resources;

import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataExistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.RespuestaLog;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.UsuarioRequest;
import com.mycompany.proyecto2_ipc2_ss2026.Servicios.ServicioLogin;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author milton
 */
@Path("login")
public class LoginController {
    
    private final ServicioLogin servicio;
    
    public LoginController() {
        servicio = new ServicioLogin();
    }
    
    @POST
    @Path("ingresar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ingresarAlSistema(UsuarioRequest usuario) {
        try {
            RespuestaLog respuesta = servicio.ingresar(usuario);
            if (respuesta.isAcceso()) {
                return Response.ok(respuesta.getUsuario()).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(respuesta.getMensaje()).build();
            }
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Path("colocar-contrasenia")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response colocarContraseña(UsuarioRequest usuario) {
        try {
            RespuestaLog respuesta = servicio.colocarContraseña(usuario);
            if (respuesta.isAcceso()) {
                return Response.ok(respuesta.getMensaje()).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(respuesta.getMensaje()).build();
            }
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("modificar-contrasenia/{idUsuario}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modificarContraseña(UsuarioRequest usuario, @PathParam("idUsuario") String idUsuario) {
        try {
            RespuestaLog respuesta = servicio.modificarContraseña(usuario, idUsuario);
            if (respuesta.isAcceso()) {
                return Response.ok(respuesta.getMensaje()).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(respuesta.getMensaje()).build();
            }
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Path("recuperar-contrasenia")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response accesoRecuperarContraseña(UsuarioRequest usuario) {
        try {
            RespuestaLog respuesta = servicio.accesoRecuperarContraseña(usuario);
            if (respuesta.isAcceso()) {
                return Response.ok(respuesta.isAcceso()).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(respuesta.getMensaje()).build();
            }
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    
}
