/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Controlador;

import com.mycompany.proyecto1ss2026.DAOs.RutaDAO;
import com.mycompany.proyecto1ss2026.DAOs.UsuarioDAO;
import com.mycompany.proyecto1ss2026.DAOs.ViajeDAO;
import com.mycompany.proyecto1ss2026.DAOs.ViajePublicoDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorExistenteException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInexistenteException;
import com.mycompany.proyecto1ss2026.Exeptions.ValorInvalidoException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.RutaDB;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ViajeDB;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ViajePublicoDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.Asiento;
import com.mycompany.proyecto1ss2026.Respuesta.Respuesta;
import com.mycompany.proyecto1ss2026.Servicios.ServicioUsuario;
import com.mycompany.proyecto1ss2026.Servicios.ServicioViaje;
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
@WebServlet(name = "UsuarioController", urlPatterns = {"/mvc/usuario/usuario-servlet"})
public class UsuarioController extends HttpServlet {
    
    private final ViajeDAO viajedao;
    private final ViajePublicoDAO viajepdao;
    private final UsuarioDAO usuariodao;
    private final ServicioUsuario servicioUsuario;
    
    public UsuarioController() {
        viajedao = new ViajeDAO();
        viajepdao = new ViajePublicoDAO();
        usuariodao = new UsuarioDAO();
        servicioUsuario = new ServicioUsuario();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String parametroRutas = request.getParameter("rutas");
        
        RequestDispatcher dispatcher;
        
        if (parametroRutas != null && parametroRutas.equals("todos")) {
            try {
                String idViaje = setIdViaje(request);

                setRutasEnCompraBoleto(request, response);
                setAsientosEnCompraBoleto(request, idViaje);

                dispatcher = getServletContext().getRequestDispatcher("/mvc/usuario/viaje/comprar-boleto.jsp");
                dispatcher.forward(request, response);
            } catch (AccesoALaDataException e) {
                request.setAttribute("error", e.getMessage());
                dispatcher = getServletContext().getRequestDispatcher("/mvc/usuario/viaje/comprar-boleto.jsp");
                dispatcher.forward(request, response);
            }
        }
        
        String parametroUsuarios = request.getParameter("modificar-usuario");
        
        if (parametroUsuarios != null && parametroUsuarios.equals("todos")) {
            try {
                String dpiUsuario = request.getParameter("dpi-usuario");
                String textoEstado = request.getParameter("estado");
                setValoresModificarUsuario(request, dpiUsuario);
                servicioUsuario.modificarEstadoUsuario(dpiUsuario, textoEstado);
                setValoresModificarUsuario(request, dpiUsuario);
                dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/usuario/modificar-usuario.jsp");
                dispatcher.forward(request, response);
            } catch (AccesoALaDataException | ValorInexistenteException | ValorInvalidoException e) {
                request.setAttribute("error", e.getMessage());
                dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/usuario/modificar-usuario.jsp");
                dispatcher.forward(request, response);
            }        
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServicioViaje servicioViaje = new ServicioViaje();
        String parametroCompraBoleto = request.getParameter("compra-boleto");
        RequestDispatcher dispatcher;
        if (parametroCompraBoleto != null && parametroCompraBoleto.equals("compraBoleto")) {
            try {
                setRutasEnCompraBoleto(request, response);
                setIdRuta(request);
                String idViaje = setIdViaje(request);
                String dpi = request.getParameter("dpi");
                String textoNumeroAsiento = request.getParameter("asiento");
                Respuesta respuesta = servicioViaje.comparaBoletoViajePublico(dpi, idViaje, textoNumeroAsiento);
                
                UsuarioDB usuario = usuariodao.getUsuarioPorDpi(dpi);
                request.getSession().setAttribute("usuarioLogeado", usuario);
                setAsientosEnCompraBoleto(request, idViaje);
                request.setAttribute("respuesta", respuesta);
                
                dispatcher = getServletContext().getRequestDispatcher("/mvc/usuario/viaje/comprar-boleto.jsp");
                dispatcher.forward(request, response);
            } catch (AccesoALaDataException | ValorInexistenteException | ValorInvalidoException e) {
                request.setAttribute("error", e.getMessage());
                dispatcher = getServletContext().getRequestDispatcher("/mvc/usuario/viaje/comprar-boleto.jsp");
                dispatcher.forward(request, response);
            }
        }
        
        String parametroUsuarios = request.getParameter("modificar-usuario");
        if (parametroUsuarios != null && parametroUsuarios.equals("todos")) {
            try {
                String dpiUsuario = request.getParameter("dpi-usuario");
                setValoresModificarUsuario(request, dpiUsuario);
                modificarUsuario(request, dpiUsuario);
                setValoresModificarUsuario(request, dpiUsuario);
                
                dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/usuario/modificar-usuario.jsp");
                dispatcher.forward(request, response);
            } catch (AccesoALaDataException | ValorExistenteException | ValorInexistenteException | ValorInvalidoException e) {
                request.setAttribute("error", e.getMessage());
                dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/usuario/modificar-usuario.jsp");
                dispatcher.forward(request, response);
            }
        }
    }
    
    private String setIdRuta(HttpServletRequest request) {
        String idRuta = request.getParameter("id-ruta");
        if (idRuta != null) {
            request.setAttribute("idRuta", idRuta);
        }
        return idRuta;
    }
    
    private String setIdViaje(HttpServletRequest request) {
        String idViaje = request.getParameter("id-viaje");
        if (idViaje != null) {
            request.setAttribute("idViaje", idViaje);
        }
        return idViaje;
    }
    
    private void setRutasEnCompraBoleto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RutaDAO rutadao = new RutaDAO();
        String idRuta = setIdRuta(request);
        try {
            List<RutaDB> rutas = rutadao.getTodasLasRutas();
            request.setAttribute("rutas", rutas);
            if (idRuta != null) {
                List<ViajePublicoDB> viajesDisponibles = viajepdao.getViajesPublicosOrigenDestino(idRuta);
                request.setAttribute("viajesDisponibles", viajesDisponibles);
            }
        } catch (AccesoALaDataException e) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/mvc/usuario/menu-usuario.jsp");
            request.setAttribute("error", e.getMessage());
            dispatcher.forward(request, response);
        }
    }
    
    private void setAsientosEnCompraBoleto(HttpServletRequest request, String idViaje) throws ServletException, IOException, AccesoALaDataException {
        if (idViaje != null) {
            ViajeDB viaje = viajedao.getViajePorId(idViaje);
            List<Asiento> asientos = viajepdao.getAsientosBus(viaje.getBus(), viaje.getId());
            request.setAttribute("asientos", asientos);
        }
    }
    
    private void setValoresModificarUsuario(HttpServletRequest request, String dpiUsuario) throws AccesoALaDataException, IOException, ServletException {
        if (dpiUsuario != null) {
            UsuarioDB usuario = usuariodao.getUsuarioPorDpi(dpiUsuario);
            request.setAttribute("usuarioElegido", usuario);
        }

        List<UsuarioDB> administradores = usuariodao.getAdiministradores();
        List<UsuarioDB> adminsSucursales = usuariodao.getAdministradoresSucursales();
        List<UsuarioDB> clientes = usuariodao.getClientes();
        request.setAttribute("administradores", administradores);
        request.setAttribute("adminsSucursales", adminsSucursales);
        request.setAttribute("clientes", clientes);
    }
    
    private void modificarUsuario(HttpServletRequest request, String dpiUsuario) throws AccesoALaDataException, ValorExistenteException, ValorInexistenteException, ValorInvalidoException {
        String nombre = request.getParameter("nombre");
        String nit = request.getParameter("nit");
        String telefono = request.getParameter("telefono");
        String direccion = request.getParameter("direccion");
        String textoRol = request.getParameter("rol");
        String sucursal = request.getParameter("sucursal");
        servicioUsuario.modificacionPorAdmin(dpiUsuario, nombre, nit, telefono, direccion, textoRol, sucursal);
    }

}
