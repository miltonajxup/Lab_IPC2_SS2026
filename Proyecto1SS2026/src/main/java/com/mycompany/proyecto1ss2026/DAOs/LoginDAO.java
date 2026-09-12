/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
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
    
    public LoginDAO() {
        usuariodao = new UsuarioDAO();
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
            PreparedStatement selectChofer = connection.prepareStatement(BUSCAR_CHOFER);
            selectChofer.setString(1, idUsuario);
            selectChofer.setString(2, nombre);
            ResultSet rsChofer = selectChofer.executeQuery();
            if (rsChofer.next()) {
                return usuariodao.armarUsuario(rsChofer);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Ocurrio un error al buscar al usuario para login " + e.getMessage());
        }
        return null;
    }
    
}
