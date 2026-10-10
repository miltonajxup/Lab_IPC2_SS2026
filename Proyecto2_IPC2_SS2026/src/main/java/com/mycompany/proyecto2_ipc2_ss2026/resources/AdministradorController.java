/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.resources;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.Mes;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.CursoMaestroRequest;
import com.mycompany.proyecto2_ipc2_ss2026.Servicios.ServicioAdministrador;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author milton
 */
@Path("administrador")
public class AdministradorController {
    
    private final ServicioAdministrador servicio;
    
    public AdministradorController() {
        servicio = new ServicioAdministrador();
    }
    
    @GET
    @Path("boletas-generadas/{mes}/{pagina}") 
    @Produces(MediaType.APPLICATION_JSON) 
    public Response getBoletasGeneradas(@PathParam("mes") Mes mes, @PathParam("pagina") int pagina) {
        try {
            return Response.ok(servicio.getBoletasGeneradas(mes, pagina)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("pagos-generados/{mes}/{pagina}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPagosGenerados(@PathParam("mes") Mes mes, @PathParam("pagina") int pagina) {
        try {
            return Response.ok(servicio.getPagosRealizados(mes, pagina)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Path("generar-boletas/{mes}")
    public Response generarBoletasDePago(@PathParam("mes") Mes mes) {
        try {
            return Response.status(Response.Status.CREATED).entity(servicio.generarBoletasDePago(mes)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Path("generar-pagos/{mes}")
    public Response generarPagos(@PathParam("mes") Mes mes) {
        try {
            return Response.status(Response.Status.CREATED).entity(servicio.pagarEmpleados(mes)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Path("asignar-curso-maestro")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response asignarMaestroCurso(CursoMaestroRequest cursoMaestro) {
        try {
            return Response.status(Response.Status.CREATED).entity(servicio.asignarCursoMaestro(cursoMaestro)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
}
