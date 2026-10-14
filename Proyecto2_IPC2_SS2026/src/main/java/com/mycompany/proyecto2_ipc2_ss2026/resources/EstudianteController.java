/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.resources;

import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.InformacionMedicaRequest;
import com.mycompany.proyecto2_ipc2_ss2026.Servicios.ServicioEstudiante;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
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
@Path("estudiante")
public class EstudianteController {
    
    private final ServicioEstudiante servicio;
    
    public EstudianteController() {
        servicio = new ServicioEstudiante();
    }
    
    @GET
    @Path("notas/{idEstudiante}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarNotas(@PathParam("idEstudiante") String idEsudiante) {
        try {
            return Response.ok(servicio.buscarNotasEstudiante(idEsudiante)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("info-medica/{idEstudiante}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarInformacionMedica(@PathParam("idEstudiante") String idEsudiante) {
        try {
            return Response.ok(servicio.getInformacionMedica(idEsudiante)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("modificar-info-medica/{idEstudiante}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modificarInformacionMedica(InformacionMedicaRequest info, @PathParam("idEstudiante") String idEsudiante) {
        try {
            return Response.ok(servicio.modificarInformacionMedica(info, idEsudiante)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("prestamos-pendientes/{idEstudiante}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPrestamosPendientes(@PathParam("idEstudiante") String idEsudiante) {
        try {
            return Response.ok(servicio.buscarPrestamosPendientes(idEsudiante)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("multas/{idEstudiante}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarMultasSinPagar(@PathParam("idEstudiante") String idEsudiante) {
        try {
            return Response.ok(servicio.buscarMultas(idEsudiante)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("boletas-año/{idEstudiante}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarBoletasDelAño(@PathParam("idEstudiante") String idEsudiante) {
        try {
            return Response.ok(servicio.buscarBoletasDelAño(idEsudiante)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("pagos-realizados/{idEstudiante}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarBoletas(@PathParam("idEstudiante") String idEsudiante) {
        try {
            return Response.ok(servicio.buscarPagosRealizados(idEsudiante)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
}
