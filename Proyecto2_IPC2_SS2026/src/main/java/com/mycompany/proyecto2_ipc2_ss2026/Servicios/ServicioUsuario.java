/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.DAOs.UsuarioDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.UsuarioDB;

/**
 *
 * @author milton
 */
public class ServicioUsuario {
    
    public UsuarioDB buscarUsuario(String referencia) throws AccesoALaDataException, DataInexistenteException {
        UsuarioDAO usuariodao = new UsuarioDAO();
        UsuarioDB usuario = usuariodao.busquedaUsuario(referencia);
        if (usuario == null) {
            throw new DataInexistenteException("No se pudo encontrar al usuario \ncon la referencia: " + referencia);
        }
        return usuario;
    }
    
}
