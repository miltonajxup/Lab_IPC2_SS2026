/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.resources;

import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataExistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.UsuarioRequest;
import com.mycompany.proyecto2_ipc2_ss2026.Servicios.ServicioEmpleado;
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
@Path("empleado")
public class EmpleadoController {
    
    private final ServicioEmpleado servicio;
    
    public EmpleadoController() {
        servicio = new ServicioEmpleado();
    }
    
    @GET
    @Path("{dpi}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response empleadoPorDpi(@PathParam("dpi") String dpiEmpleado) {
        try {
            return Response.ok(servicio.buscarEmpleadoPorDpi(dpiEmpleado)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("empleados-rol/{rol}/{pagina}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response empleadosPorRol(@PathParam("rol") String rol, @PathParam("pagina") int pagina) {
        try {
            return Response.ok(servicio.buscarEmpleadosPorRol(rol, pagina)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Path("crear")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response crearYContratar(UsuarioRequest usuario) {
        try {
            String mensaje = servicio.crearEmpleado(usuario);
            return Response.status(Response.Status.CREATED).entity(mensaje).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Path("contratar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response contratar(UsuarioRequest usuario) {
        try {
            String mensaje = servicio.contratarEmpleado(usuario);
            return Response.status(Response.Status.CREATED).entity(mensaje).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (DataExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        }
    }
    
}
