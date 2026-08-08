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
    private final String TODOS_PERSONAL = """
                                        SELECT p.*, r.tipo AS nombre_rol, j.tipo AS nombre_jornada 
                                        FROM personal AS p JOIN rol AS r ON p.rol = r.id 
                                        JOIN jornada AS j ON p.jornada = j.id
                                        """;
    private final String TODOS_MESEROS = """
                                        SELECT p.*, r.tipo AS nombre_rol, j.tipo AS nombre_jornada 
                                        FROM personal AS p JOIN rol AS r ON p.rol = r.id 
                                        JOIN jornada AS j ON p.jornada = j.id
                                        WHERE r.tipo = 'MESERO'
                                        """;
    private final String GET_PERSONAL_POR_DPI = """
                                        SELECT p.*, r.tipo AS nombre_rol, j.tipo AS nombre_jornada 
                                        FROM personal AS p JOIN rol AS r ON p.rol = r.id 
                                        JOIN jornada AS j ON p.jornada = j.id WHERE p.dpi = ?""";
    private final String AGREGAR_PERSONAL = "INSERT INTO personal (dpi, nombre, salario, rol, jornada) VALUES (?,?,?,?,?)";
    private final String ACTUALIZAR_PERSONAL = "UPDATE personal SET estado = ? WHERE dpi = ?";
    
    public List<Personal> getPersonal() throws AccesoALaDataException {
        List<Personal> personal = new ArrayList<>();
        
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(TODOS_PERSONAL);
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
            PreparedStatement select = connection.prepareStatement(TODOS_MESEROS);
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                personal.add(armarPersonal(resultSet));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Ocurrio un erro al cargar al personal " + e.getMessage());
        }
        return personal;
    }
    
    public Personal getPersonalPorDpi(String dpi) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(GET_PERSONAL_POR_DPI);
            select.setString(1, dpi);
            ResultSet rs = select.executeQuery();
            if (rs.next()) {
                return armarPersonal(rs);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar empleado por DPI " + e.getMessage());
        }
        return null;
    }
    
    public void agregarPersonal(String dpi, String nombre, double salario, int rol, int jornada) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement insert = connection.prepareStatement(AGREGAR_PERSONAL);
            insert.setString(1, dpi);
            insert.setString(2, nombre);
            insert.setDouble(3, salario);
            insert.setInt(4, rol);
            insert.setInt(5, jornada);
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al intentar agregar un nuevo usuario " + e.getMessage());
        }
    }
    
    public void actulizarPersonal(boolean estado, String dpi) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement update = connection.prepareStatement(ACTUALIZAR_PERSONAL);
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
