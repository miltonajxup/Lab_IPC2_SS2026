/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.BusDB;
import com.mycompany.proyecto1ss2026.Modelos.DataBase.ChoferDB;
import com.mycompany.proyecto1ss2026.Modelos.Request.ReporteDepreciacionBus;
import com.mycompany.proyecto1ss2026.Modelos.Request.ReporteIngresoBoleto;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author milton
 */
public class ReporteDeSucursalDAO {
    
    private final String VIAJES_COMPLETADOS_BUS = 
            """
            SELECT via.bus, COUNT(*) AS viajes_completados 
            FROM viaje_ejecucion AS viaej 
            JOIN viaje AS via ON viaej.viaje_id = via.id 
            WHERE viaej.hora_llegada IS NOT NULL AND via.bus = ? GROUP BY via.bus""";
    private final String VIAJES_COMPLETADOS_CHOFER = 
            """
            SELECT via.chofer, COUNT(*) AS viajes_completados 
            FROM viaje_ejecucion AS viaej 
            JOIN viaje AS via ON viaej.viaje_id = via.id 
            WHERE viaej.hora_llegada IS NOT NULL AND via.chofer = ? GROUP BY via.chofer""";
    private final String INGRESOS_BOLETO = 
            """
            SELECT 
                via.id AS viaje_id, 
                via.chofer AS licencia, 
                cho.nombre, 
                via.bus, 
                rut.id AS ruta_id, 
                rut.distancia_aproximada, 
                rut.precio_boleto, 
                rut.sucursal_origen, 
                rut.sucursal_destino, 
                viap.fecha_salida, 
                COUNT(*) AS boletos_vendidos, 
                COUNT(*) * precio_boleto AS ingreso_total 
            FROM boleto_viaje AS bol 
            JOIN viaje AS via ON bol.viaje = via.id 
            JOIN viaje_publico AS viap ON via.id = viap.id_viaje 
            JOIN horario_ruta AS hor ON viap.horario = hor.id 
            JOIN ruta AS rut ON hor.ruta = rut.id 
            JOIN chofer AS cho ON via.chofer = cho.numero_de_licencia 
            WHERE rut.sucursal_origen = ? AND bol.fecha >= ? AND bol.fecha < ? GROUP BY bol.viaje""";
    
    private final String DEPRECIACION_BUSES = 
            """
            SELECT 
                bus.numero_placa, 
                SUM(dep.monto_depreciado) AS monto_depreciacion, 
                SUM(kilometros_recorridos) AS kilometros_recorridos 
            FROM depreciacion_bus AS dep 
            JOIN bus ON dep.bus = bus.numero_placa 
            WHERE bus.sucursal_base = ? GROUP BY dep.bus""";
    
    private final BusDAO busdao;
    private final ChoferDAO choferdao;
    
    public ReporteDeSucursalDAO() {
        busdao = new BusDAO();
        choferdao = new ChoferDAO();
    }
    
    public List<BusDB> getReporteBuses(String sucursal) throws AccesoALaDataException {
        List<BusDB> buses = busdao.getBusSucursal(sucursal);
        Connection connection = DBConnection.getConnection();
        try {
            for (BusDB bus : buses) {
                PreparedStatement ps = connection.prepareStatement(VIAJES_COMPLETADOS_BUS);
                ps.setString(1, bus.getNumeroPlaca());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    bus.setViajesCompletados(rs.getInt("viajes_completados"));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar reporte de buses " + e.getMessage());
        }
        return buses;
    }
    
    public List<ChoferDB> getReporteChofer(String sucursal) throws AccesoALaDataException {
        List<ChoferDB> choferes = choferdao.choferesSucursalBase(sucursal);
        Connection connection = DBConnection.getConnection();
        try {
            for (ChoferDB chofer : choferes) {
                PreparedStatement ps = connection.prepareStatement(VIAJES_COMPLETADOS_CHOFER);
                ps.setString(1, chofer.getNumeroLicencia());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    chofer.setViajesCompletados(rs.getInt("viajes_completados"));
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar reporte de buses " + e.getMessage());
        }
        return choferes;
    }
    
    public List<ReporteIngresoBoleto> getReporteBoletos(String sucursal, LocalDate fechaInicial, LocalDate fechaFinal) throws AccesoALaDataException {
        List<ReporteIngresoBoleto> reportes = new ArrayList<>();
        try {
            Date dateInicial = Date.valueOf(fechaInicial);
            Date dateFinal = Date.valueOf(fechaFinal);
            Connection connection = DBConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(INGRESOS_BOLETO);
            ps.setString(1, sucursal);
            ps.setDate(2, dateInicial);
            ps.setDate(3, dateFinal);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reportes.add( new ReporteIngresoBoleto(
                        rs.getInt("viaje_id"), 
                        rs.getString("licencia"), 
                        rs.getString("nombre"), 
                        rs.getString("bus"), 
                        rs.getInt("ruta_id"), 
                        rs.getInt("distancia_aproximada"), 
                        rs.getDouble("precio_boleto"), 
                        rs.getString("sucursal_origen"), 
                        rs.getString("sucursal_destino"), 
                        rs.getString("fecha_salida"), 
                        rs.getInt("boletos_vendidos"), 
                        rs.getDouble("ingreso_total")) );
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar el reporte de ingresos boletos " + e.getMessage());
        }
        return reportes;
    }
    
    public List<ReporteDepreciacionBus> getReporteDepreciacionBus(String sucursal) throws AccesoALaDataException {
        List<ReporteDepreciacionBus> reportes = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(DEPRECIACION_BUSES);
            ps.setString(1, sucursal);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reportes.add(new ReporteDepreciacionBus(
                        rs.getString("numero_placa"), 
                        rs.getDouble("monto_depreciacion"), 
                        rs.getInt("kilometros_recorridos")) );
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar el reporte de ingresos boletos " + e.getMessage());
        }
        return reportes;
    }
    
}
