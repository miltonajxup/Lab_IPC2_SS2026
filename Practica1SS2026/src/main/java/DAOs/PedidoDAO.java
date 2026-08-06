/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Modelos.Pedido;
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
public class PedidoDAO {
    
    private final String MESAS_OCUPADAS = """
                                          SELECT p.*, per.nombre AS nombre_mesero 
                                          FROM pedido AS p 
                                          JOIN personal AS per ON p.mesero = per.dpi 
                                          WHERE hora_liberacion IS NULL""";
    private final String AGREGAR_PEDIDO = "";
    private final String ACTUALIZAR_PEDIDO = "";
    
    public List<Pedido> geMesasOcupadas() {
        List<Pedido> pedidos = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(MESAS_OCUPADAS);
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                pedidos.add(armarPedido(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Ocurrio un problema al traer el pedido " + e.getMessage());
        }
        return pedidos;
    }
    
    public Pedido armarPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido(
                rs.getInt("numero_pedido"), 
                rs.getString("hora_ocupacion"), 
                rs.getString("hora_liberacion"), 
                rs.getBoolean("estado"), 
                rs.getDouble("pago_total"), 
                rs.getDouble("propina"), 
                rs.getString("mesero"), 
                rs.getString("nombre_mesero"),
                rs.getInt("mesa"));
        return pedido;
    }
    
}
