/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Exceptions.AccesoALaDataException;
import Modelos.Personal;
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
public class PersonalDAO {
    
    //SELECT p.*, r.tipo AS nombre_rol, j.tipo AS nombre_jornada FROM personal AS p JOIN rol AS r ON p.rol = r.id JOIN jornada AS j ON p.jornada = j.id
    private final String todoPersonal = """
                                        SELECT p.*, r.tipo AS nombre_rol, j.tipo AS nombre_jornada 
                                        FROM personal AS p JOIN rol AS r ON p.rol = r.id 
                                        JOIN jornada AS j ON p.jornada = j.id
                                        """;
    private final String todosMeseros = """
                                        SELECT p.*, r.tipo AS nombre_rol, j.tipo AS nombre_jornada 
                                        FROM personal AS p JOIN rol AS r ON p.rol = r.id 
                                        JOIN jornada AS j ON p.jornada = j.id
                                        WHERE r.tipo = 'MESERO'
                                        """;
    private final String actualizarPersonal = "UPDATE personal SET estado = ? WHERE dpi = ?";
    
    public List<Personal> getPersonal() throws AccesoALaDataException {
        List<Personal> personal = new ArrayList<>();
        
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(todoPersonal);
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                personal.add(armarPersonal(resultSet));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Ocurrio un erro al cargar al personal " + e.getMessage());
        }
        return personal;
    }
    
    public List<Personal> getMeseros() throws AccesoALaDataException {
        List<Personal> personal = new ArrayList<>();
        
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(todosMeseros);
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                personal.add(armarPersonal(resultSet));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Ocurrio un erro al cargar al personal " + e.getMessage());
        }
        return personal;
    }
    
    public void actulizarPersonal(boolean estado, String dpi) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement update = connection.prepareStatement(actualizarPersonal);
            update.setBoolean(1, estado);
            update.setString(2, dpi);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al actulizar personal " + e.getMessage());
        }
    }
    
    private Personal armarPersonal(ResultSet rs) throws SQLException {
        Personal personal = new Personal(
                rs.getString("dpi"), 
                rs.getString("nombre"), 
                rs.getDouble("salario"), 
                rs.getString("fecha_contratacion"), 
                rs.getBoolean("estado"), 
                rs.getString("nombre_rol"), 
                rs.getString("nombre_jornada"));
        return personal;
    }
    
}
