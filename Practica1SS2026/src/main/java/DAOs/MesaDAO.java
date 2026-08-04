/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Modelos.Mesa;
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
public class MesaDAO {
    
    private final String todasMesas = "SELECT * FROM mesa";
    private final String actualizarMesa = "UPDATE mesa SET estado = %d, mesero = %s WHERE numero_mesa = %d";
    
    public List<Mesa> getMesas() {
        
        List<Mesa> mesas = new ArrayList<>();
        
        try {
            Connection connection = DBConnection.getConnection();
            Statement select = connection.createStatement();
            ResultSet resultSet = select.executeQuery(todasMesas);
            while (resultSet.next()) {
                mesas.add(armarMesa(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar Mesas " + e.getMessage());
        }
        return mesas;
    }
    
    public void actualizarMesa(int estado, String mesero, int numeroMesa) {
        
        String update = String.format(actualizarMesa, estado, mesero, numeroMesa);
        
        try {
            Connection connection = DBConnection.getConnection();
            Statement updateStatement = connection.createStatement();
            
            updateStatement.executeUpdate(update);
        } catch (SQLException e) {
            System.out.println("Error al actualizar mesa " + e.getMessage());
        }
    }
    
    private Mesa armarMesa(ResultSet rs) throws SQLException {
        Mesa mesa = new Mesa(rs.getInt("numero_mesa"), rs.getInt("capacidad"), rs.getBoolean("estado"));
        return mesa;
    }
    
}
