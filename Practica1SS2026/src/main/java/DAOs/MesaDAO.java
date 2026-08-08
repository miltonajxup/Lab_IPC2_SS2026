/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Exceptions.AccesoALaDataException;
import Modelos.Mesa;
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
public class MesaDAO {
    
    private final String todasMesas = "SELECT * FROM mesa";
    private final String actualizarMesa = "UPDATE mesa SET estado = ? WHERE numero_mesa = ?";
    
    public List<Mesa> getMesas() throws AccesoALaDataException {
        
        List<Mesa> mesas = new ArrayList<>();
        
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(todasMesas);
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                mesas.add(armarMesa(resultSet));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al cargar Mesas " + e.getMessage());
        }
        return mesas;
    }
    
    public void actualizarMesa(boolean estado, int numeroMesa) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        actualizarMesa(connection, estado, numeroMesa);
    }
    
    public void actualizarMesa(Connection connection, boolean estado, int numeroMesa) throws AccesoALaDataException {
        try {
            PreparedStatement update = connection.prepareStatement(actualizarMesa);
            update.setBoolean(1, estado);
            update.setInt(2, numeroMesa);
            
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al actualizar mesa " + e.getMessage());
        }
    }
    
    private Mesa armarMesa(ResultSet rs) throws SQLException {
        Mesa mesa = new Mesa(rs.getInt("numero_mesa"), rs.getInt("capacidad"), rs.getBoolean("estado"));
        return mesa;
    }
    
}
