/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.resources;

import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Secretaria.ConsultaEstudianteDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.ExpedienteEstudiante;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.NotaDB;
import com.mycompany.proyecto2_ipc2_ss2026.Servicios.ServicioEstudiante;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 *
 * @author milton
 */
@Path("secretaria")
public class SecretariaController {
    
    @GET
    @Path("expediente-estudiante/{idEstudiante}")
    public Response getExpediente(@PathParam("idEstudiante") String idEstudiante) {
        try {
            ServicioEstudiante servEst = new ServicioEstudiante();
            List<NotaDB> notas = servEst.buscarNotasEstudiante(idEstudiante);
            ConsultaEstudianteDAO consultadao = new ConsultaEstudianteDAO();
            ExpedienteEstudiante expediente = consultadao.getExpediente(idEstudiante);
            expediente.setNotas(notas);
            
            return Response.ok(expediente).build();
        } catch (AccesoALaDataException e) {
            System.out.println(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        } catch (DataInexistenteException e) {
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }
    }
    
}
