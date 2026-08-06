/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Modelos.Insumo;
import Modelos.ProductoMenu;
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
public class ProductoDAO {
    
    private final String NOMBRES_PRODUCTOS = """
                                             SELECT pro.*, cat.categoria AS nombre_categoria 
                                             FROM producto_menu AS pro JOIN categoria_producto AS cat ON pro.categoria = cat.id""";
    private final String INSUMOS_PRODUCTO = """
                                            SELECT pro.codigo AS codigo_producto, pro.nombre AS nombre_producto, 
                                            ins.codigo AS codigo_insumo, ins.nombre AS nombre_insumo, rec.cantidad_utilizada 
                                            FROM producto_menu AS pro 
                                            JOIN receta AS rec ON pro.codigo = rec.producto_id 
                                            JOIN insumo AS ins ON rec.insumo_id = ins.codigo WHERE pro.codigo = ?""";
    
    public List<ProductoMenu> getProductos() {
        List<ProductoMenu> productos = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(NOMBRES_PRODUCTOS);
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                ProductoMenu producto = armarProducto(resultSet);
                List<Insumo> insumos = getInsumosProducto(producto.getCodigo());
                producto.setInsumos(insumos);
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.out.println("Error al traer los productos del menu " + e.getMessage());
        }
        return productos;
    }
    
    private List<Insumo> getInsumosProducto(int idProducto) {
        List<Insumo> listaInsumos = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(INSUMOS_PRODUCTO);
            select.setInt(1, idProducto);
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                listaInsumos.add(armarInsumo(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error al traer los productos del menu " + e.getMessage());
        }
        return listaInsumos;
    }
    
    public ProductoMenu armarProducto(ResultSet rs) throws SQLException {
        ProductoMenu producto = new ProductoMenu(rs.getInt("codigo"), rs.getString("nombre"), rs.getDouble("precio"), rs.getString("nombre_categoria"));
        return producto;
    }
    
    private Insumo armarInsumo(ResultSet rs) throws SQLException {
        Insumo insumo = new Insumo(rs.getInt("codigo_insumo"), rs.getString("nombre_insumo"), rs.getInt("cantidad_utilizada"));
        return insumo;
    }
}
