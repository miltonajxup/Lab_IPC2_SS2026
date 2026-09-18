/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Controlador;

import com.mycompany.proyecto1ss2026.DAOs.BusDAO;
import com.mycompany.proyecto1ss2026.DAOs.ChoferDAO;
import com.mycompany.proyecto1ss2026.DAOs.ViajePrivadoDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.BusDB;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ChoferDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.PropuestaViaje;
import com.mycompany.proyecto1ss2026.Servicios.ServicioViajePrivado;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author milton
 */
@WebServlet(name = "ViajePrivadoController", urlPatterns = {"/mvc/viaje/viaje-privado-servlet"})
public class ViajePrivadoController extends HttpServlet {

    private final ViajePrivadoDAO viajedao;
    private final ServicioViajePrivado servicio;
    private final ChoferDAO choferdao;
    private final BusDAO busdao;

    public ViajePrivadoController() {
        viajedao = new ViajePrivadoDAO();
        servicio = new ServicioViajePrivado();
        choferdao = new ChoferDAO();
        busdao = new BusDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String propuestas = request.getParameter("todo-propuestas");
        RequestDispatcher dispatcher;
        
        if (propuestas != null && propuestas.equals("todoPropuestas")) {
            try {
                setListas(request);
                
                String idPropuesta = request.getParameter("id-propuesta");
                String idSucursal = request.getParameter("id-sucursal");
                if (idPropuesta != null && idSucursal != null) {
                    PropuestaViaje propuesta = viajedao.getPropuestaPorId(idPropuesta);
                    request.setAttribute("propuesta", propuesta);
                    
                    List<ChoferDB> choferes = choferdao.todosLosChoferes();
                    List<BusDB> buses = busdao.getBusSucursalActual(idSucursal);
                    request.setAttribute("choferes", choferes);
                    request.setAttribute("buses", buses);
                }
            } catch (AccesoALaDataException e) {
                request.setAttribute("error", e.getMessage());
            }
            dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/viaje/propuesta-viaje.jsp");
            dispatcher.forward(request, response);
        }
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String parametro = request.getParameter("parametro-viaje-privado");
        RequestDispatcher dispatcher;
        
        if (parametro != null && parametro.equals("crearPropuesta")) {
            try {
                String mensajeCreacion = crearPropuesta(request);
                request.setAttribute("mensajeCreacion", mensajeCreacion);
                
            } catch (AccesoALaDataException | ValorInvalidoException e) {
                request.setAttribute("error", e.getMessage());
            }
            dispatcher = getServletContext().getRequestDispatcher("/mvc/usuario/viaje/crear-viaje-privado.jsp");
            dispatcher.forward(request, response);
        }
        
        if (parametro != null && parametro.equals("aceptarPropuesta")) {
            try {
                setListas(request);
                String mensaje = aceptarPropuesta(request);
                request.setAttribute("mensaje", mensaje);
                setListas(request);
            } catch (AccesoALaDataException | ValorInvalidoException e) {
                request.setAttribute("error", e.getMessage());
            }
            dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/viaje/propuesta-viaje.jsp");
            dispatcher.forward(request, response);
        }
        
    }
    
    private String crearPropuesta(HttpServletRequest request) throws AccesoALaDataException, ValorInvalidoException {
        String textoCantidadPasajeros = request.getParameter("cantidad-pasajeros");
        String origen = request.getParameter("origen");
        String destino = request.getParameter("destino");
        String textoDistancia = request.getParameter("distancia");
        String textoHoraSalida = request.getParameter("hora-salida");
        String textoHoraLlegada = request.getParameter("hora-llegada");
        String textoFechaSalida = request.getParameter("fecha-salida");
        String textoCosto = request.getParameter("costo");
        String usuarioSolicitante = request.getParameter("usuario");
        return servicio.agregarPropuestaViajePrivado(
                textoCantidadPasajeros, origen, destino, textoDistancia, 
                textoHoraSalida, textoHoraLlegada, textoFechaSalida, textoCosto, usuarioSolicitante);
    }
    
    private String aceptarPropuesta(HttpServletRequest request) throws AccesoALaDataException, ValorInvalidoException {
        String chofer = request.getParameter("chofer");
        String bus = request.getParameter("bus");
        String sucursal = request.getParameter("sucursal");
        String idPropuesta = request.getParameter("id-propuesta");
        return servicio.aceptarPropuestaViajePrivado(chofer, bus, sucursal, idPropuesta);
    }
    
    private void setListas(HttpServletRequest request) throws AccesoALaDataException  {
        List<PropuestaViaje> listaPropuestas = viajedao.getPropuestas();
        request.setAttribute("listaPropuestas", listaPropuestas);
    }

}
