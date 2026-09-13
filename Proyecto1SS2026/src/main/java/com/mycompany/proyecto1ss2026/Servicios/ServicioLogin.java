/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.Servicios;

import com.mycompany.proyecto1ss2026.Constantes.Limite;
import com.mycompany.proyecto1ss2026.DAOs.LoginDAO;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Exeptions.LoginException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB;

/**
 *
 * @author milton
 */
public class ServicioLogin {
    
    private final LoginDAO logindao;
    
    public ServicioLogin() {
        logindao = new LoginDAO();
    }
    
    public UsuarioDB loogearUsuario(String idUsuario, String nombre) throws AccesoALaDataException, LoginException {
        if (idUsuario.length() > Limite.DPI.getTamañoLimite()) {
            terminarLogin();
        }
        if (nombre.length() > Limite.NOMBRE.getTamañoLimite()) {
            terminarLogin();
        }
        UsuarioDB usuario = logindao.existeCredencial(idUsuario, nombre);
        if (usuario == null) {
            terminarLogin();
        }
        return usuario;
    }
    
    private void terminarLogin() throws LoginException {
        throw new LoginException("No se pudo completar el Login");
    }
    
}
