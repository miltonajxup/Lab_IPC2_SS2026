/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ChoferDB;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.UsuarioDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author milton
 */
public class LoginDAO {
    
    private final String BUSCAR_USUARIO = "SELECT * FROM usuario WHERE dpi = ? AND nombre = ?";
    private final String BUSCAR_CHOFER = "SELECT * FROM chofer WHERE numero_de_licencia = ? AND nombre = ?";
    private final UsuarioDAO usuariodao;
    private final ChoferDAO choferdao;
    
    public LoginDAO() {
        usuariodao = new UsuarioDAO();
        choferdao = new ChoferDAO();
    }
    
    public UsuarioDB existeCredencial(String idUsuario, String nombre) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement select = connection.prepareStatement(BUSCAR_USUARIO);
            select.setString(1, idUsuario);
            select.setString(2, nombre);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                return usuariodao.armarUsuario(rs, connection);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Ocurrio un error al buscar al usuario para login " + e.getMessage());
        }
        return null;
    }
    
    public ChoferDB existeCredencialChofer(String idChofer, String nombre) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement selectChofer = connection.prepareStatement(BUSCAR_CHOFER);
            selectChofer.setString(1, idChofer);
            selectChofer.setString(2, nombre);
            ResultSet rsChofer = selectChofer.executeQuery();
            if (rsChofer.next()) {
                return choferdao.armarChofer(rsChofer);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Ocurrio un errro al buscar a usuario para el login " + e.getMessage());
        }
        return null;
    } 
    
}
