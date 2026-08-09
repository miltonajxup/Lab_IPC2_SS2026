/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

/**
 *
 * @author milton
 */
public class ReporteDAO {
    
    private final String COSTO_INSUMOS = "SELECT SUM(cantidad) AS gasto_insumo, insumo AS codigo_insumo FROM gasto_insumo GROUP BY insumo";
    private final String TOTAL_VENTAS = "SELECT SUM(pago_total) FROM pedido WHERE estado = TRUE";
    private final String PAGOS_REALIZADOS = "SELECT SUM(monto_a_pagar) FROM pago WHERE estado = TRUE";
    
    
}
