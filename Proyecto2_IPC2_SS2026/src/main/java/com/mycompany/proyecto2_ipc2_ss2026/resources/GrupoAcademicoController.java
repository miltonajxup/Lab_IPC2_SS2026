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
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.CurriculoRequest;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.GrupoAcademicoRequest;
import com.mycompany.proyecto2_ipc2_ss2026.Servicios.ServicioGrupoAcademico;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
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
@Path("grupo-academico")
public class GrupoAcademicoController {
    
    private final ServicioGrupoAcademico servicio;
    
    public GrupoAcademicoController() {
        servicio = new ServicioGrupoAcademico();
    }
    
    @GET
    @Path("grados")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGrados() {
        try {
            GradoDAO gradodao = new GradoDAO();
            return Response.ok(gradodao.getGradosNormales()).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("grupos-anio-actual/{pagina}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGruposDelAño(@PathParam("pagina") int pagina) {
        try {
            return Response.ok(servicio.getGruposDelAño(pagina)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("grupos-anio/{año}/{pagina}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGruposPorAño(@PathParam("año") int año, @PathParam("pagina") int pagina) {
        try {
            return Response.ok(servicio.getGruposPorAño(año, pagina)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("todos-los-grupos/{pagina}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getGruposPorAño(@PathParam("pagina") int pagina) {
        try {
            return Response.ok(servicio.getTodosLosGruposPorAño(pagina)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Path("crear-grupo-grado")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearGrupoAcademicoGrado(GrupoAcademicoRequest grupo) {
        try {
            return Response.status(Response.Status.CREATED).entity(servicio.crearGrupoAcademicoGrado(grupo)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Path("crear-grupo-carrera")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearGrupoAcademicoCarrera(GrupoAcademicoRequest grupo) {
        try {
            return Response.status(Response.Status.CREATED).entity(servicio.crearGrupoAcademicoCarrera(grupo)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataExistenteException e) {
           return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("cursos-curriculo-grado/{grado}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCursosCurriculoGrado(@PathParam("grado") int idGrado) {
        try {
            return Response.ok(servicio.getCursosCurriculoGrado(idGrado)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("cursos-curriculo-carrera/{carrera}/{grado}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getCursosCurriculoCarrera(@PathParam("carrera") String codigoCarrera, @PathParam("grado") int idGrado) {
        try {
            return Response.ok(servicio.getCursosCurriculoCarrera(codigoCarrera, idGrado)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Path("agregar-curriculo-grado")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregarCurriculoGrado(CurriculoRequest curriculo) {
        try {
            servicio.agregarCursoCurriculoGrado(curriculo);
            return Response.status(Response.Status.CREATED).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @POST
    @Path("agregar-curriculo-carrera")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response agregarCurriculoCarrera(CurriculoRequest curriculo) {
        try {
            servicio.agregarCursoCurriculoCarrera(curriculo);
            return Response.status(Response.Status.CREATED).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (DataExistenteException e) {
            return Response.status(Response.Status.CONFLICT).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @DELETE
    @Path("{id}")
    public Response eliminarCurriculo(@PathParam("id") int idCurriculo) {
        try {
            servicio.eliminarCurriculo(idCurriculo);
            return Response.ok().build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
}
