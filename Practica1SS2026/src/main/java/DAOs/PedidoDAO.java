/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Exceptions.AccesoALaDataException;
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
    private final String AGREGAR_PEDIDO = "INSERT INTO pedido (pago_total, mesero, mesa) VALUES (?,?,?)";
    private final String OBTENER_PEDIDO_MESERO = """
                                                 SELECT p.*, per.nombre AS nombre_mesero 
                                                 FROM pedido AS p 
                                                 JOIN personal AS per ON p.mesero = per.dpi 
                                                 WHERE hora_liberacion IS NULL AND p.mesero = ?""";
    private final String ACTUALIZAR_PEDIDO = "UPDATE pedido SET hora_liberacion = NOW(), estado = TRUE, propina = ? WHERE numero_pedido = ?";
    
    public List<Pedido> getMesasOcupadas() throws AccesoALaDataException {
        List<Pedido> pedidos = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(MESAS_OCUPADAS);
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                pedidos.add(armarPedido(resultSet));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Ocurrio un problema al traer los pedido " + e.getMessage());
        }
        return pedidos;
    }
    
    public void agregarPedido(double pagoTotal, String mesero, int mesa) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement insert = connection.prepareStatement(AGREGAR_PEDIDO);
            insert.setDouble(1, pagoTotal);
            insert.setString(2, mesero);
            insert.setInt(3, mesa);
            insert.execute();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Ocurrio un problema al traer el pedido " + e.getMessage());
        }
    }
    
    public Pedido obtenerPedidoMesero(String dpi) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(OBTENER_PEDIDO_MESERO);
            select.setString(1, dpi);
            ResultSet resultSet = select.executeQuery();
            if (resultSet.next()) {
                return armarPedido(resultSet);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar el pedido del mesero " + dpi + " : " + e.getMessage());
        }
        return null;
    }
    
    public void actualizarPedido(int numeroPedido, double propina) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement update = connection.prepareStatement(ACTUALIZAR_PEDIDO);
            update.setDouble(1, propina);
            update.setInt(2, numeroPedido);
            update.execute();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al actualizar pedido " + e.getMessage());
        }
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
