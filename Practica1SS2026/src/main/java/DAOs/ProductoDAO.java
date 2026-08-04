/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Modelos.Insumo;
import Modelos.ProductoMenu;
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
public class ProductoDAO {
    
    private final String NOMBRE_PRODUCTO = "SELECT * FROM producto_menu";
    private final String INSUMOS_PRODUCTO = """
                                            SELECT pro.codigo AS codigo_producto, pro.nombre AS nombre_producto, ins.nombre AS nombre_insumo, rec.cantidad_utilizada 
                                            FROM producto_menu AS pro 
                                            JOIN receta AS rec ON pro.codigo = rec.producto_id 
                                            JOIN insumo AS ins ON rec.insumo_id = ins.codigo WHERE pro.codigo = $d""";
    
    public List<ProductoMenu> getProductos() {
        List<ProductoMenu> productos = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            Statement selectStatement = connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery(NOMBRE_PRODUCTO);
            while (resultSet.next()) {
                ProductoMenu producto = armarProducto(resultSet);
                List<Insumo> insumos = getInsumosProducto(producto.getCodigo());
                producto.setInsumos(insumos);
            }
        } catch (SQLException e) {
            System.out.println("Error al traer los productos del menu " + e.getMessage());
        }
        return productos;
    }
    
    private List<Insumo> getInsumosProducto(int idProducto) {
        String insumos = String.format(INSUMOS_PRODUCTO, idProducto);
        List<Insumo> listaInsumos = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            Statement selectStatement = connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery(insumos);
            while (resultSet.next()) {
                listaInsumos.add(armarInsumo(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Error al traer los productos del menu " + e.getMessage());
        }
        return listaInsumos;
    }
    
    public ProductoMenu armarProducto(ResultSet rs) throws SQLException {
        ProductoMenu producto = new ProductoMenu(rs.getInt("cogigo_producto"), rs.getString("nombre_producto"));
        return producto;
    }
    
    private Insumo armarInsumo(ResultSet rs) throws SQLException {
        Insumo insumo = new Insumo(rs.getString("nombre_insumo"), rs.getInt("cantidad_utilizada"));
        return insumo;
    }
}
