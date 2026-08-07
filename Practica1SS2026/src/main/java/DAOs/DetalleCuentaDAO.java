/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Exceptions.AccesoALaDataException;
import Modelos.DetalleCuenta;
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
public class DetalleCuentaDAO {
    
    private final String DETALLES_DE_CUENTA = """
                                              SELECT det.*, pro.nombre AS nombre_producto 
                                              FROM detalle_cuenta AS det JOIN producto_menu AS pro ON det.producto = pro.codigo 
                                              WHERE pedido = ?""";
    private final String AGREGAR_DETALLE_DE_CUENTA = "INSERT INTO detalle_cuenta (producto,precio,unidades,sub_total,pedido) VALUES (?,?,?,?,?)";
    
    public List<DetalleCuenta> getDetallesCuenta(int numeroPedido) throws AccesoALaDataException {
        List<DetalleCuenta> detalles = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement insert = connection.prepareStatement(DETALLES_DE_CUENTA);
            insert.setInt(1, numeroPedido);
            ResultSet resultSet = insert.executeQuery();
            while (resultSet.next()) {
                detalles.add(armarDetalle(resultSet));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al traer los detalles de la cuenta " + numeroPedido + " : " + e.getMessage());
        }
        return detalles;
    }
    
    public void agregarDetalles(int idProducto, double precio, int unidades, double subTotal, int idPedido) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement insert = connection.prepareStatement(AGREGAR_DETALLE_DE_CUENTA);
            insert.setInt(1, idProducto);
            insert.setDouble(2, precio);
            insert.setInt(3, unidades);
            insert.setDouble(4, subTotal);
            insert.setInt(5, idPedido);
            insert.execute();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al insertar un nuevo detalle de cuenta");
        }
    }
    
    private DetalleCuenta armarDetalle(ResultSet rs) throws SQLException {
        return new DetalleCuenta(
                rs.getInt("id"), 
                rs.getInt("producto"), 
                rs.getString("nombre_producto"), 
                rs.getDouble("precio"), 
                rs.getInt("unidades"), 
                rs.getDouble("sub_total"), 
                rs.getInt("pedido"));
    }
    
}
