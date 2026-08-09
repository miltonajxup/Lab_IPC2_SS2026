/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Exceptions.AccesoALaDataException;
import Modelos.GastoInsumo;
import Modelos.ProductoVendido;
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
public class ReporteDAO {
    
    private final String GASTOS_INSUMO = "SELECT SUM(cantidad) AS gasto_insumo, insumo AS codigo_insumo FROM gasto_insumo GROUP BY insumo";
    private final String GASTOS_INSUMO_FECHA = """
                                               SELECT SUM(cantidad) AS gasto_insumo, insumo AS codigo_insumo 
                                               FROM gasto_insumo WHERE fecha_compra BETWEEN ? AND ? GROUP BY insumo""";
    private final String TOTAL_VENTAS = "SELECT SUM(pago_total) AS total_ventas FROM pedido WHERE estado = TRUE";
    private final String TOTAL_VENTAS_FECHA = """
                                              SELECT COALESCE(SUM(pago_total), 0) AS total_ventas 
                                              FROM pedido WHERE estado = TRUE AND hora_liberacion BETWEEN ? AND ?""";
    private final String PAGOS_REALIZADOS = "SELECT SUM(monto_a_pagar) AS pagos_realizados FROM pago WHERE estado = TRUE";
    private final String PAGOS_REALIZADOS_FECHA = """
                                                  SELECT COALESCE(SUM(monto_a_pagar), 0) AS pagos_realizados 
                                                  FROM pago WHERE estado = TRUE AND fecha_emision BETWEEN ? AND ?""";
    private final String RANKING_PRODUCTO_VENDIDO = """
                                                    SELECT COUNT(det.producto) AS veces_vendida, pro.codigo AS codigo_producto, pro.nombre AS nombre_producto 
                                                    FROM detalle_cuenta AS det 
                                                    JOIN pedido AS pe ON det.pedido = pe.numero_pedido 
                                                    JOIN producto_menu AS pro ON pro.codigo = det.producto 
                                                    WHERE pe.estado = TRUE GROUP BY det.producto ORDER BY veces_vendida DESC""";
    
    public List<GastoInsumo> getGastosInsumos() throws AccesoALaDataException {
        List<GastoInsumo> gastosInsumo = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(GASTOS_INSUMO);
            ResultSet resulSet = select.executeQuery();
            getListaGastosInsumo(resulSet, gastosInsumo);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los gastos de insumos " + e.getMessage());
        }
        return gastosInsumo;
    }
    
    public List<GastoInsumo> getGastosInsumosEntreFechas(String fechaInicial, String fechaFinal) throws AccesoALaDataException {
        List<GastoInsumo> gastosInsumo = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(GASTOS_INSUMO_FECHA);
            select.setString(1, fechaInicial);
            select.setString(2, fechaFinal);
            ResultSet resulSet = select.executeQuery();
            getListaGastosInsumo(resulSet, gastosInsumo);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los gastos de insumos por fechas " + e.getMessage());
        }
        return gastosInsumo;
    }
    
    private void getListaGastosInsumo(ResultSet rs, List<GastoInsumo> gastosInsumo) throws AccesoALaDataException, SQLException {
        while (rs.next()) {
            gastosInsumo.add(armarGastoInsumo(rs));
        }
    }
    
    private GastoInsumo armarGastoInsumo(ResultSet rs) throws SQLException {
        return new GastoInsumo(rs.getInt("codigo_insumo"), rs.getDouble("gasto_insumo"));
    }
    
    public double getTotalVentas() throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(TOTAL_VENTAS);
            ResultSet rs = select.executeQuery();
            return getCantidadTotalVentas(rs);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar el total de ventas por fechas " + e.getMessage());
        }
    }
    
    public double getTotalVentasFecha(String fechaInicial, String fechaFinal) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(TOTAL_VENTAS_FECHA);
            select.setString(1, fechaInicial);
            select.setString(2, fechaFinal);
            ResultSet rs = select.executeQuery();
            return getCantidadTotalVentas(rs);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar el total de ventas " + e.getMessage());
        }
    }
    
    private double getCantidadTotalVentas(ResultSet rs) throws AccesoALaDataException, SQLException {
        if (rs.next()) {
            return rs.getDouble("total_ventas");
        }
        throw new AccesoALaDataException("Error al buscar el total de ventas");
    }
    
    public double getTotalPagos() throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(PAGOS_REALIZADOS);
            ResultSet rs = select.executeQuery();
            return getCantidadTotalPagos(rs);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar el total de pagos " + e.getMessage());
        }
    }
    
    public double getTotalPagosFecha(String fechaInicial, String fechaFinal) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(PAGOS_REALIZADOS_FECHA);
            select.setString(1, fechaInicial);
            select.setString(2, fechaFinal);
            ResultSet rs = select.executeQuery();
            return getCantidadTotalPagos(rs);
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar el total de pagos por fechas " + e.getMessage());
        }
    }
    
    private double getCantidadTotalPagos(ResultSet rs) throws AccesoALaDataException, SQLException {
        if (rs.next()) {
            return rs.getDouble("pagos_realizados");
        }
        throw new AccesoALaDataException("Error al buscar los pagos realizados");
    }
    
    public List<ProductoVendido> getRankingProductos() throws AccesoALaDataException {
        List<ProductoVendido> productosRanking = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(RANKING_PRODUCTO_VENDIDO);
            ResultSet rs = select.executeQuery();
            while (rs.next()) {
                productosRanking.add(armarProducto(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar el ranking de productos " + e.getMessage());
        }
        return productosRanking;
    }
    
    private ProductoVendido armarProducto(ResultSet rs) throws SQLException {
        return new ProductoVendido(rs.getInt("veces_vendida"), rs.getInt("codigo_producto"), rs.getString("nombre_producto"));
    }
    
}
