/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Exceptions.AccesoALaDataException;
import Modelos.Rol;
import com.mycompany.practica1ss2026.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author milton
 */
public class RolDAO {
    
    private final String GET_ROLES = "SELECT * FROM rol";
    
    public List<Rol> getRoles() throws AccesoALaDataException {
        List<Rol> roles = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(GET_ROLES);
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                roles.add(new Rol(resultSet.getInt("id"), resultSet.getString("rol")));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los roles " + e.getMessage());
        }
        return roles;
    }
    
}
