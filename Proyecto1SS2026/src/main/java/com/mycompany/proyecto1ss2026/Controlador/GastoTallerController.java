/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Controlador;

import com.mycompany.proyecto1ss2026.DAOs.BusDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.BusDB;
import com.mycompany.proyecto1ss2026.Servicios.ServicioGastoTaller;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author milton
 */
@WebServlet(name = "GastoTallerController", urlPatterns = {"/mvc/administrador/taller/taller-servlet"})
public class GastoTallerController extends HttpServlet {

    private final BusDAO busdao;

    public GastoTallerController() {
        busdao = new BusDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        String idSucursal = req.getParameter("id-sucursal");
        if (idSucursal != null) {
            try {
                
                setBuses(idSucursal, req);
                
                String idBus = req.getParameter("id-bus");
                if (idBus != null) {
                    BusDB bus = busdao.getBusId(idBus);
                    req.setAttribute("bus", bus);
                }
                dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/taller/gastos-taller.jsp");
            } catch (AccesoALaDataException e) {
                req.setAttribute("error", e.getMessage());
                dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/taller/gastos-taller.jsp");
            }
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher;
        try {
            ServicioGastoTaller servicio = new ServicioGastoTaller();
            String montoManoDeObra = request.getParameter("monto-mano-obra");
            String montoRepuestos = request.getParameter("monto-repuestos");
            String fecha = request.getParameter("fecha");
            String idBus = request.getParameter("id-bus");
            String idSucursal = request.getParameter("id-sucursal");
            
            setBuses(idSucursal, request);
            
            String respuesta = servicio.agregarRegistroPago(montoManoDeObra, montoRepuestos, fecha, idBus);
            request.setAttribute("respuesta", respuesta);
            dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/taller/gastos-taller.jsp");
        } catch (AccesoALaDataException | ValorInvalidoException e) {
            request.setAttribute("error", e.getMessage());
            dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/taller/gastos-taller.jsp");
        }
        dispatcher.forward(request, response);
    }
    
    private void setBuses(String idSucursal, HttpServletRequest req) throws AccesoALaDataException {
        List<BusDB> buses = busdao.getBusSucursal(idSucursal);
        req.setAttribute("buses", buses);
    }
    
}
