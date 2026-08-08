/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAOs;

import Exceptions.AccesoALaDataException;
import Modelos.Pago;
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
public class PagoDAO {
    
    private final String GET_PAGOS = """
                                     SELECT pago.*, ti.tipo AS nombre_tipo 
                                     FROM pago JOIN tipo_pago AS ti ON pago.tipo = ti.id""";
    private final String GET_PAGOS_ENTRE_FECHAS = """
                                          SELECT pago.*, ti.tipo AS nombre_tipo 
                                          FROM pago JOIN tipo_pago AS ti ON pago.tipo = ti.id 
                                          WHERE fecha_emision BETWEEN ? AND ?""";
    private final String GET_PAGOS_EN_UNA_FECHA = """
                                                  SELECT pago.*, ti.tipo AS nombre_tipo, per.nombre  
                                                  FROM pago JOIN tipo_pago AS ti ON pago.tipo = ti.id 
                                                  JOIN personal AS per ON per.dpi = pago.empleado 
                                                  WHERE pago.estado = FALSE AND fecha_emision = ?""";
    private final String GET_PAGO_EMPLEADO_FECHA = """
                                                   SELECT pago.*, ti.tipo AS nombre_tipo 
                                                   FROM pago JOIN tipo_pago AS ti ON pago.tipo = ti.id 
                                                   WHERE fecha_emision = ? AND empleado = ?""";
    private final String AGREGAR_PAGO = "INSERT INTO pago (fecha_emision, monto_a_pagar, empleado, tipo) VALUES (?,?,?,?)";
    private final String ACTUALIZAR_PAGO = "UPDATE pago SET estado = TRUE WHERE fecha_emision = ? AND empleado = ?";
    
    public List<Pago> getTodosPagos() throws AccesoALaDataException {
        List<Pago> pagos = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(GET_PAGOS);
            
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                pagos.add(armarPago(resultSet));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al cargar todos los pagos " + e.getMessage());
        }
        return pagos;
    }
    
    public List<Pago> getPagosFecha(String fechaInicial, String fechaFinal) throws AccesoALaDataException {
        List<Pago> pagos = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(GET_PAGOS_ENTRE_FECHAS);
            select.setString(1, fechaInicial);
            select.setString(2, fechaFinal);
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                pagos.add(armarPago(resultSet));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al cargar pagos entre fechas" + e.getMessage());
        }
        return pagos;
    }
    
    public List<Pago> getPagosEnUnaFecha(String fecha) throws AccesoALaDataException {
        List<Pago> pagos = new ArrayList<>();
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(GET_PAGOS_EN_UNA_FECHA);
            select.setString(1, fecha);
            ResultSet resultSet = select.executeQuery();
            while (resultSet.next()) {
                Pago pago = armarPago(resultSet);
                pagos.add(pago);
                agregarNombre(resultSet, pago);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al cargar pagos por fecha " + e.getMessage());
        }
        return pagos;
    }
    
    public Pago getPagoEmpleadoFecha(String fecha, String dpiEmpleado) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement select = connection.prepareStatement(GET_PAGO_EMPLEADO_FECHA);
            select.setString(1, fecha);
            select.setString(2, dpiEmpleado);
            
            ResultSet resultSet = select.executeQuery();
            if (resultSet.next()) {
                return armarPago(resultSet);
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar pago por nombre y fecha " + e.getMessage());
        }
        return null;
    }
    
    public void agregarRegistroPago(String fechaEmision, double montoAPagar, String empleado, int tipoDePago) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement insert = connection.prepareStatement(AGREGAR_PAGO);
            insert.setString(1, fechaEmision);
            insert.setDouble(2, montoAPagar);
            insert.setString(3, empleado);
            insert.setInt(4, tipoDePago);
            insert.execute();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al registrar un pago " + e.getMessage());
        }
    }
    
    public void actualizarPago(String fecha, String dpiEmpleado) throws AccesoALaDataException {
        try {
            Connection connection = DBConnection.getConnection();
            PreparedStatement update = connection.prepareStatement(ACTUALIZAR_PAGO);
            update.setString(1, fecha);
            update.setString(2, dpiEmpleado);
            update.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al actualizar pago " + e.getMessage());
        }
    }
    
    private Pago armarPago(ResultSet rs) throws SQLException {
        return new Pago(rs.getInt("codigo_nomina"), 
                rs.getString("fecha_emision"), 
                rs.getDouble("monto_a_pagar"), 
                rs.getBoolean("estado"), 
                rs.getString("empleado"), 
                rs.getString("nombre_tipo"));
    }
    
    private void agregarNombre(ResultSet rs, Pago pago) throws SQLException {
        String nombre = rs.getString("nombre");
        pago.setNombreEmpleado(nombre);
    }
    
}
