/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.resources;

import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.Servicios.ServicioReporte;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author milton
 */
@Path("reporte")
public class ReporteController {
    
    private final ServicioReporte servicio;
    
    public ReporteController() {
        servicio = new ServicioReporte();
    }
    
    @GET
    @Path("solvencia/{idGrupo}/{pagina}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response buscarSolvencias(@PathParam("idGrupo") int idGrupo, @PathParam("pagina") int pagina) {
        try {
            return Response.ok(servicio.buscarSolvencias(idGrupo, pagina)).build();
        } catch (AccesoALaDataException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("promedio/{idGrupo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPromedios(@PathParam("idGrupo") int idGrupo) {
        try {
            return Response.ok(servicio.buscarPromedioCursos(idGrupo)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("pagos-estudiante/{idEstudiante}/{pagina}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPagosEstudiante(@PathParam("idEstudiante") String idEstudiante, @PathParam("pagina") int pagina) {
        try {
            return Response.ok(servicio.buscarPagosPorEstudianteSinFecha(idEstudiante, pagina)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("pagos-estudiante-fechas/{idEstudiante}/{fechaInicio}/{fechaFin}/{pagina}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPagosEstudianteFechas(
            @PathParam("idEstudiante") String idEstudiante, @PathParam("fechaInicio") String textoFechaInicio, 
            @PathParam("fechaFin") String textoFechaFin, @PathParam("pagina") int pagina) {
        try {
            return Response.ok(servicio.buscarPagosPorEstudianteConFecha(idEstudiante, textoFechaInicio, textoFechaFin, pagina)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("ingresos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarIngresos() {
        try {
            return Response.ok(servicio.buscarIngresosSinFechas()).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("ingresos-fechas/{fechaInicio}/{fechaFin}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarIngresosPorFecha(
            @PathParam("fechaInicio") String textoFechaInicio, @PathParam("fechaFin") String textoFechaFin) {
        try {
            return Response.ok(servicio.buscarIngresosConFechas(textoFechaInicio, textoFechaFin)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
}
