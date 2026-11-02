/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.resources;

import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataExistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.Servicios.ServicioRecepcionPago;
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
@Path("recepcion-pago")
public class RecepcionPagoController {
    
    @GET
    @Path("{pagina}/{idEstudiante}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBoletasEstudiante(@PathParam("pagina") int pagina, @PathParam("idEstudiante") String idEstudiante) {
        ServicioRecepcionPago servicio = new ServicioRecepcionPago();
        try {
            return Response.ok(servicio.buscarBoletas(pagina, idEstudiante)).build();
        } catch (AccesoALaDataException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        } catch (ValorInvalidoException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }
    
    @PUT
    @Path("{idPago}/{idSecretaria}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response pagarBoleta(@PathParam("idPago") int idPago, @PathParam("idSecretaria") String idSecretaria) {
        ServicioRecepcionPago servicio = new ServicioRecepcionPago();
        try {
            return Response.ok(servicio.realizarPago(idPago, idSecretaria)).build();
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
    
}
