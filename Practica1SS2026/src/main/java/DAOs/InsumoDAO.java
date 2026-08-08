/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Exceptions.AccesoALaDataException;
import Modelos.Insumo;
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
public class InsumoDAO {
    
    private final String TODOS_INSUMOS = "SELECT ins.*, uni.unidad AS nombre_unidad FROM insumo AS ins JOIN unidad_medida AS uni ON ins.unidad = uni.id;";
    private final String ACTUALIZAR_CANTIDAD_INSUMO = "UPDATE insumo SET cantidad_stock = ? WHERE codigo = ?";
    
    public List<Insumo> getTodosInsumos() throws AccesoALaDataException {
        List<Insumo> insumos = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(TODOS_INSUMOS);
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                insumos.add(armarInsumo(resultSet));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al traer insumos " + e.getMessage());
        }
        return insumos;
    }
    
    public void actualizarInsumo(double cantidadStock, int codigo) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        actualizarInsumo(connection, cantidadStock, codigo);
    }
    
    public void actualizarInsumo(Connection connection, double cantidadStock, int codigo) throws AccesoALaDataException {
        try {
            PreparedStatement update = connection.prepareStatement(ACTUALIZAR_CANTIDAD_INSUMO);
            update.setDouble(1, cantidadStock);
            update.setInt(2, codigo);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al actualizar insumo " + e.getMessage());
        }
    }
    
    private Insumo armarInsumo(ResultSet rs) throws SQLException {
        Insumo insumo = new Insumo(
                rs.getInt("codigo"), 
                rs.getString("nombre"), 
                rs.getInt("cantidad_stock"), 
                rs.getInt("stock_minimo"), 
                rs.getDouble("costo"), 
                rs.getString("nombre_unidad"));
        return insumo;
    }
    
}
