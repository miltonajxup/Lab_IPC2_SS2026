/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Modelos.Personal;
import com.mycompany.practica1ss2026.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    private final String actualizarPersonal = "UPDATE personal SET estado = $d WHERE dpi = $s";
    
    public List<Personal> getPersonal() {
        List<Personal> personal = new ArrayList<>();
        
        try {
            Connection connection = DBConnection.getConnection();
            Statement selectStatement = connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery(todoPersonal);
            while (resultSet.next()) {
                personal.add(armarPersonal(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Ocurrio un erro al cargar al personal " + e.getMessage());
        }
        return personal;
    }
    
    public List<Personal> getMeseros() {
        List<Personal> personal = new ArrayList<>();
        
        try {
            Connection connection = DBConnection.getConnection();
            Statement selectStatement = connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery(todosMeseros);
            while (resultSet.next()) {
                personal.add(armarPersonal(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Ocurrio un erro al cargar al personal " + e.getMessage());
        }
        return personal;
    }
    
    public void actulizarPersonal(int estado, String dpi) {
        String update = String.format(actualizarPersonal, estado, dpi);
        try {
            Connection connection = DBConnection.getConnection();
            Statement updateStatement = connection.createStatement();
            updateStatement.executeUpdate(update);
        } catch (SQLException e) {
            System.out.println("Error al actulizar personal " + e.getMessage());
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
