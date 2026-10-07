/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.resources.Controllers;

import com.mycompany.proyecto2_ipc2_ss2026.DAOs.SuperAdministradorDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataExistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.AñoLectivoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.AñoLectivoRequest;
import com.mycompany.proyecto2_ipc2_ss2026.Servicios.ServicioSuperAdmin;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author milton
 */
@Path("super-admin")
public class SuperAdminController {
    
    private final SuperAdministradorDAO superdao;
    private final ServicioSuperAdmin servicio;
    
    public SuperAdminController() {
        superdao = new SuperAdministradorDAO();
        servicio = new ServicioSuperAdmin();
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAñosLectivos() {
        try {
            List<AñoLectivoDB> años = superdao.getAñosLectivos();
            return Response.ok(años).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarAñoLectivo(AñoLectivoRequest añoLectivo) {
        try {
            List<AñoLectivoDB> años = servicio.crearAñoLectivo(añoLectivo);
            return Response.status(Response.Status.CREATED).entity(años).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DataExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("modificar/{año}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modificarAñoLectivo(@PathParam("año") int año, AñoLectivoRequest añoLectivo) {
        try {
            AñoLectivoDB modificado = servicio.modificarAñoLectivo(añoLectivo, año);
            return Response.ok(modificado).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("activar/{año}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response activarAñoLectivo(@PathParam("año") int año) {
        try {
            List<AñoLectivoDB> años = servicio.activarAñoLectivo(año);
            return Response.ok(años).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }
    
}
