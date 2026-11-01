/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.resources;

import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.ErrorResponse;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.LoginResponse;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.RespuestaLog;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.LoginRequest;
import com.mycompany.proyecto2_ipc2_ss2026.Servicios.AuthService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author milton
 */
@Path("auth")
public class AuthController {
    
    @POST
    @Path("ingresar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ingresarAlSistema(LoginRequest usuario) {
        try {
            AuthService service = new AuthService();
            RespuestaLog respuesta = service.ingresar(usuario);
            if (respuesta.isAcceso()) {
                return Response.ok(new LoginResponse(respuesta.getMensaje())).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponse(respuesta.getMensaje())).build();
            }
        } catch (AccesoALaDataException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
}
