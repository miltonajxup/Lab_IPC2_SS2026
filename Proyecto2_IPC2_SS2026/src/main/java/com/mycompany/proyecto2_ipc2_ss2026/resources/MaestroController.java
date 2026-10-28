/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.resources;

import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.AsistenciaDiaRequest;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.NotaRequest;
import com.mycompany.proyecto2_ipc2_ss2026.Servicios.ServicioMaestro;
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
@Path("maestro")
public class MaestroController {
    
    private final ServicioMaestro servicio;
    
    public MaestroController() {
        servicio = new ServicioMaestro();
    }
    
    @GET
    @Path("cursos/{dpiMaestro}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarCursosMaestro(@PathParam("dpiMaestro") String dpiMaestro) {
        try {
            return Response.ok(servicio.buscarCursosMaestro(dpiMaestro)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("curso-asignado/{id}/{dpiMaestro}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarCursosMaestroPorId(@PathParam("id") int id, @PathParam("dpiMaestro") String dpiMaestro) {
        try {
            return Response.ok(servicio.buscarCursoMaestroPorId(id, dpiMaestro)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("estudiantes-curso/{idGrupo}/{idCurso}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarEstudiantesCurso(@PathParam("idGrupo") int idGrupo, @PathParam("idCurso") int idCurso) {
        try {
            return Response.ok(servicio.buscarEstudiantesCurso(idGrupo, idCurso)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("fechas-aisitencia/{idGrupo}/{idCurso}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarFechasAsistencia(@PathParam("idGrupo") int idGrupo, @PathParam("idCurso") int idCurso) {
        try {
            return Response.ok(servicio.getFechaTomadasAsistencia(idGrupo, idCurso)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("aisitencias/{idGrupo}/{idCurso}/{fecha}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarAsistenciasDeDia(@PathParam("idGrupo") int idGrupo, @PathParam("idCurso") int idCurso, @PathParam("fecha") String fecha) {
        try {
            return Response.ok(servicio.getAsistencias(idGrupo, idCurso, fecha)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @POST 
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response generarAsistencias(AsistenciaDiaRequest asistencia) {
        try {
            return Response.status(Response.Status.CREATED).entity(servicio.generarAsistencias(asistencia)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("asistencia/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response tomarAsistencia(@PathParam("id") int id) {
        try {
            servicio.tomarAsistencia(id);
            return Response.ok().build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("notas-curso/{idGrupo}/{idCurso}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarNotasCurso(@PathParam("idGrupo") int idGrupo, @PathParam("idCurso") int idCurso) {
        try {
            return Response.ok(servicio.getNotasCurso(idGrupo, idCurso)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("nota-unica/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarNotaPorId(@PathParam("id") int id) {
        try {
            return Response.ok(servicio.getNotaCurso(id)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("modificar-zona/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modificarZona(NotaRequest nota, @PathParam("id") int id) {
        try {
            return Response.ok(servicio.modificarZona(nota, id)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("modificar-examen-final/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modificarExamenFinal(NotaRequest nota, @PathParam("id") int id) {
        try {
            return Response.ok(servicio.modificarExamen(nota, id)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
}
