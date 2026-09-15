/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Controlador;

import com.mycompany.proyecto1ss2026.Constantes.RolUsuario;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.LoginException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB;
import com.mycompany.proyecto1ss2026.Servicios.ServicioLogin;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @author milton
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login/log-servlet"})
public class LoginController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServicioLogin servicio = new ServicioLogin();
        String idUsuario = request.getParameter("id-usuario");
        String nombre = request.getParameter("nombre");
        RequestDispatcher dispatcher;
        try {
            UsuarioDB usuario = servicio.loogearUsuario(idUsuario, nombre);
            if (usuario != null) {
                request.getSession().setAttribute("usuarioLogeado", usuario);
                if (usuario.getRol() == RolUsuario.CLIENTE) {
                    dispatcher = getServletContext().getRequestDispatcher("/mvc/usuario/menu-usuario.jsp");
                } else {
                    dispatcher = getServletContext().getRequestDispatcher("/mvc/administrador/menu-administrador.jsp");
                }
            } else {
                request.getSession().setAttribute("choferLogeado", servicio.getChofer());
                dispatcher = getServletContext().getRequestDispatcher("/mvc/chofer/menu-chofer.jsp");
            }
        } catch (AccesoALaDataException | LoginException e) {
            request.setAttribute("error", e.getMessage());
            dispatcher = getServletContext().getRequestDispatcher("/");
        }
        dispatcher.forward(request, response);
    }
    
}
