/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.resources;

import com.mycompany.proyecto2_ipc2_ss2026.DAOs.GradoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataExistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.InscripcionRequest;
import com.mycompany.proyecto2_ipc2_ss2026.Servicios.ServicioInscripcion;
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
@Path("inscripcion")
public class InscripcionController {
    
    private final ServicioInscripcion servicio;
    
    public InscripcionController() {
        servicio = new ServicioInscripcion();
    }
    
    @GET
    @Path("revisar-inscripcion/{idEstudiante}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response revisarInscripcion(@PathParam("idEstudiante") String dpiEstudiante) {
        try {
            return Response.ok(servicio.revisarInscripcion(dpiEstudiante)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("grupos-año/{pagina}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGruposAño(@PathParam("pagina") int pagina) {
        try {
            return Response.ok(servicio.getGruposDelAño(pagina)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("todos-los-grados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGradosNomales() {
        GradoDAO carreradao = new GradoDAO();
        try {
            return Response.ok(carreradao.getTodosLosGrados()).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Path("primer-ingreso")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inscripcionPrimerIngreso(InscripcionRequest inscripcion) {
        try {
            servicio.incribirPrimerIngreso(inscripcion);
            return Response.status(Response.Status.CREATED).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    /*
    "estudiante": {
        "dpi": "202610020", 
        "nombre": "Mario Gomez", 
        "rol": "ESTUDIANTE", 
        "numeroEncargado": "1234",
        "numeroEstudiante": "5678"
    }, 
    "grupoId": 10
    */
    
    @POST
    @Path("reingreso")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inscripcionRengreso(InscripcionRequest inscripcion) {
        try {
            servicio.inscripcionReingreso(inscripcion);
            return Response.status(Response.Status.CREATED).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DataExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }
    
}
