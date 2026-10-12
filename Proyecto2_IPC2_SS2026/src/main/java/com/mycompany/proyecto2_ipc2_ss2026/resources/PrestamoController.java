/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.resources;

import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.PrestamoRequest;
import com.mycompany.proyecto2_ipc2_ss2026.Servicios.ServicioPrestamo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
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
@Path("prestamo")
public class PrestamoController {
    
    private final ServicioPrestamo servicio;
    
    public PrestamoController() {
        servicio = new ServicioPrestamo();
    }
    
    @GET
    @Path("buscar-por-nombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response prestamosPorNombre(@PathParam("nombre") String nombre) {
        try {
            return Response.ok(servicio.buscarPrestamoPorNombre(nombre)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("sin-terminar/{pagina}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response prestamosSinterminar(@PathParam("pagina") int pagina) {
        try {
            return Response.ok(servicio.prestamosSinTerminar(pagina)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("todos-los-registros/{pagina}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response todosLosRegistros(@PathParam("pagina") int pagina) {
        try {
            return Response.ok(servicio.todosLosRegistros(pagina)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response prestarLibro(PrestamoRequest prestamo) {
        try {
            return Response.status(Response.Status.CREATED).entity(servicio.prestarLibro(prestamo)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON) 
    public Response concluirPrestamo(PrestamoRequest prestamo, @PathParam("id") int id) {
        try {
            servicio.terminarPrestamo(prestamo, id);
            return Response.ok().build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
}
