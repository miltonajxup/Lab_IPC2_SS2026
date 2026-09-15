/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Servicios;

import com.mycompany.proyecto1ss2026.DAOs.LoginDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.LoginException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ChoferDB;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB;

/**
 *
 * @author milton
 */
public class ServicioLogin {
    
    private final LoginDAO logindao;
    private ChoferDB chofer;
    
    public ServicioLogin() {
        logindao = new LoginDAO();
    }

    public ChoferDB getChofer() {
        return chofer;
    }
    
    public UsuarioDB loogearUsuario(String idUsuario, String nombre) throws AccesoALaDataException, LoginException {
        UsuarioDB usuario = logindao.existeCredencial(idUsuario, nombre);
        if (usuario != null) {
            if (!usuario.isEstado()) {
                throw new LoginException("No es posible acceder porque se ha bloquado al usuario");
            }
            return usuario;
        }
        chofer = logindao.existeCredencialChofer(idUsuario, nombre);
        if (chofer != null) {
            if (!chofer.isEstadoOperativo()) {
                throw new LoginException("No es posible acceder porque se ha bloquado al usuario");
            }
            return null;
        }
        throw new LoginException("No se pudo completar el Login");
    }
    
}
