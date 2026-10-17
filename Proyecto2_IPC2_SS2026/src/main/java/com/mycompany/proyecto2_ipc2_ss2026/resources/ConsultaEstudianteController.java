/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.resources;

import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Secretaria.ConsultaEstudianteDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
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
@Path("consulta-estudiante")
public class ConsultaEstudianteController {
    
    private final ConsultaEstudianteDAO consultadao;
    
    public ConsultaEstudianteController() {
        consultadao = new ConsultaEstudianteDAO();
    }
    
    @GET
    @Path("grado-seccion/{idGrado}/{seccion}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response estudiantesGradoSeccion(@PathParam("idGrado") int idGrado, @PathParam("seccion") String seccion) {
        try {
            return Response.ok(consultadao.getEstudiantesGradoSeccion(idGrado, seccion)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("carrera-seccion/{idCarrera}/{seccion}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response estudiantesCarreraSeccion(@PathParam("idCarrera") String idCarrera, @PathParam("seccion") String seccion) {
        try {
            return Response.ok(consultadao.getEstudiantesCarreraSeccion(idCarrera, seccion)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
    @GET
    @Path("por-nombre/{nombre}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response estudiantesPorNombre(@PathParam("nombre") String nombre) {
        try {
            return Response.ok(consultadao.getEstudiantePorNombre(nombre)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
    
}
