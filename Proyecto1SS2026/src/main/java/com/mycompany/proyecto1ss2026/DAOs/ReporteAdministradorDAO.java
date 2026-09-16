/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.Request.ReporteGanancia;
import com.mycompany.proyecto1ss2026.Modelos.Request.ReporteRutaDemandada;
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
public class ReporteAdministradorDAO {
    //'2026-9-2'
    //'2026-9-25'
    //JOIN viaje AS via ON bol.viaje = via.id JOIN viaje_publico AS viap ON via.id = viap.id_viaje JOIN horario_ruta AS hor ON viap.horario = hor.id JOIN ruta AS rut ON hor.ruta = rut.id 
    private final String VENTAS_SUCURSALES = 
            """
            SELECT      rut.id AS ruta_id,     COUNT(*) AS boletos_vendidos, rut.precio_boleto, 
            rut.distancia_aproximada,  suc_o.nombre,  suc_o.ciudad, COUNT(*) * rut.precio_boleto AS venta_total  
            FROM boleto_viaje AS bol 
            JOIN viaje AS via   ON bol.viaje = via.id 
            JOIN viaje_publico AS viap   ON via.id = viap.id_viaje 
            JOIN horario_ruta AS hor  ON viap.horario = hor.id 
            JOIN ruta AS rut  ON hor.ruta = rut.id 
            JOIN sucursal AS suc_o  ON rut.sucursal_origen = suc_o.codigo_sucursal 
            WHERE bol.fecha >= ? AND bol.fecha < ? 
            GROUP BY rut.id ORDER BY suc_o.nombre""";
    private final String NOMBRES_SUCURSALES = "SELECT nombre FROM sucursal";
    private final String RUTAS_MAS_DEMANDADAS = 
            """
            SELECT rut.id AS ruta_id, COUNT(*) AS boletos_vendidos, rut.precio_boleto, rut.distancia_aproximada, 
            suc_o.nombre AS sucursal_origen, suc_o.ciudad AS ciudad_origen, 
            suc_d.nombre AS sucursal_destino, suc_d.ciudad AS ciudad_destino 
            FROM boleto_viaje AS bol JOIN viaje AS via ON bol.viaje = via.id 
            JOIN viaje_publico AS viap ON via.id = viap.id_viaje 
            JOIN horario_ruta AS hor ON viap.horario = hor.id 
            JOIN ruta AS rut ON hor.ruta = rut.id 
            JOIN sucursal AS suc_o ON rut.sucursal_origen = suc_o.codigo_sucursal 
            JOIN sucursal AS suc_d ON rut.sucursal_destino = suc_d.codigo_sucursal 
            WHERE bol.fecha >= ? AND bol.fecha < ? 
            GROUP BY rut.id ORDER BY boletos_vendidos DESC""";
    private final String GASTOS_COMBUSTIBLE_VIAJE_PUBLICO = 
            """
            SELECT SUM(viaej.gasto_combustible) AS gasto_combustible 
            FROM viaje_ejecucion AS viaej 
            JOIN viaje AS via ON viaej.viaje_id = via.id 
            JOIN viaje_publico AS viap ON via.id = viap.id_viaje 
            WHERE viap.fecha_salida >= ? AND viap.fecha_salida < ?""";
    private final String GASTOS_COMBUSTIBLE_SUCURSAL_VIAJE_PUBLICO = 
            """
            SELECT SUM(viaej.gasto_combustible) AS gasto_combustible, suc.nombre, suc.ciudad 
            FROM viaje_ejecucion AS viaej JOIN viaje AS via ON viaej.viaje_id = via.id 
            JOIN viaje_publico AS viap ON via.id = viap.id_viaje 
            JOIN bus ON via.bus = bus.numero_placa 
            JOIN sucursal AS suc ON bus.sucursal_base = suc.codigo_sucursal 
            WHERE viap.fecha_salida >= ? AND viap.fecha_salida < ? 
            GROUP BY suc.codigo_sucursal ORDER BY suc.codigo_sucursal""";
    private final String GASTO_TALLER = 
            """
            SELECT SUM(monto_mano_obra) AS monto_mano_obra, SUM(monto_repuestos) AS monto_repuestos 
            FROM gasto_taller WHERE fecha_mantenimiento >= ? AND fecha_mantenimiento < ?""";
    private final String GASTO_TALLER_SUCURSALES = 
            """
            SELECT SUM(gas.monto_mano_obra) AS monto_mano_obra, SUM(gas.monto_repuestos) AS monto_repuestos, suc.nombre 
            FROM gasto_taller AS gas 
            JOIN bus ON gas.bus = bus.numero_placa 
            JOIN sucursal AS suc ON bus.sucursal_base = suc.codigo_sucursal 
            WHERE gas.fecha_mantenimiento >= ? AND gas.fecha_mantenimiento < ? 
            GROUP BY suc.codigo_sucursal ORDER BY suc.codigo_sucursal""";
    private final String DEPRECIACION_ACUMULADA = 
            """
            SELECT SUM(monto_depreciado) AS monto_depreciado FROM depreciacion_bus WHERE fecha_registro >= ? AND fecha_registro < ?""";
    private final String DEPRECIACION_ACUMULADA_SUCURSALES = 
            """
            SELECT SUM(dep.monto_depreciado) AS monto_depreciado, suc.nombre, suc.ciudad 
            FROM depreciacion_bus AS dep 
            JOIN bus ON dep.bus = bus.numero_placa 
            JOIN sucursal AS suc ON bus.sucursal_base = suc.codigo_sucursal 
            WHERE dep.fecha_registro >=  ? AND dep.fecha_registro < ? 
            GROUP BY suc.codigo_sucursal ORDER BY suc.codigo_sucursal""";
    
    public ReporteGanancia getTotalGanancia(LocalDate fechaInicial, LocalDate fechaFinal) throws AccesoALaDataException {
        List<ReporteGanancia> ganancias = getGanancias(fechaInicial, fechaFinal);
        double totalVentas = 0;
        double totalGastoCombustible = 0;
        double totalGastoTallerMano = 0;
        double totalGastoTallerRepuestos = 0;
        double totalDepreciacion = 0;
        for (ReporteGanancia ganancia : ganancias) {
            totalVentas += ganancia.getVentas();
            totalGastoCombustible += ganancia.getGastoCombustible();
            totalGastoTallerMano += ganancia.getGastoTallerMano();
            totalGastoTallerRepuestos += ganancia.getGastoTallerRepuestos();
            totalDepreciacion += ganancia.getDepreciacion();
        }
        return new ReporteGanancia(totalVentas, totalGastoCombustible, totalGastoTallerMano, totalGastoTallerRepuestos, totalDepreciacion);
    }
    
    public List<ReporteGanancia> getGanancias(LocalDate fechaInicial, LocalDate fechaFinal) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        List<ReporteGanancia> ganancias = getGastosOperativosSucursal(fechaInicial, fechaFinal);
        try {
            PreparedStatement ps = connection.prepareStatement(VENTAS_SUCURSALES);
            Date dateInicial = Date.valueOf(fechaInicial);
            Date dateFinal = Date.valueOf(fechaFinal);
            ps.setDate(1, dateInicial);
            ps.setDate(2, dateFinal);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                for (ReporteGanancia ganancia : ganancias) {
                    if (ganancia.getSucursal().equals(rs.getString("nombre"))) {
                        ganancia.setVentas(rs.getDouble("venta_total"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al obtener los reportes de ganancias: " + e.getMessage());
        }
        return ganancias;
    }
    
    public List<ReporteRutaDemandada> getRerporteRuta(LocalDate fechaInicial, LocalDate fechaFinal) throws AccesoALaDataException {
        List<ReporteRutaDemandada> rutas = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(RUTAS_MAS_DEMANDADAS);
            Date dateInicial = Date.valueOf(fechaInicial);
            Date dateFinal = Date.valueOf(fechaFinal);
            ps.setDate(1, dateInicial);
            ps.setDate(2, dateFinal);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rutas.add( new ReporteRutaDemandada(
                        rs.getInt("ruta_id"), 
                        rs.getInt("boletos_vendidos"), 
                        rs.getDouble("precio_boleto"), 
                        rs.getInt("distancia_aproximada"), 
                        rs.getString("sucursal_origen"), 
                        rs.getString("ciudad_origen"), 
                        rs.getString("sucursal_destino"), 
                        rs.getString("ciudad_destino") ) );
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al traer el reporte de las rutas mas demandadas " + e.getMessage());
        }
        return rutas;
    }
    
    public List<ReporteGanancia> getGastosOperativosSucursal(LocalDate fechaInicial, LocalDate fechaFinal) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        List<ReporteGanancia> gastos = new ArrayList<>();
        Date dateInicial = Date.valueOf(fechaInicial);
        Date dateFinal = Date.valueOf(fechaFinal);
        try {
            PreparedStatement ps = connection.prepareStatement(NOMBRES_SUCURSALES);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                gastos.add(new ReporteGanancia(rs.getString("nombre")));
            }
            
            PreparedStatement combustible = connection.prepareStatement(GASTOS_COMBUSTIBLE_SUCURSAL_VIAJE_PUBLICO);
            combustible.setDate(1, dateInicial);
            combustible.setDate(2, dateFinal);
            ResultSet rsCombustible = combustible.executeQuery();
            while (rsCombustible.next()) {
                for (ReporteGanancia gasto : gastos) {
                    if (gasto.getSucursal().equals(rsCombustible.getString("nombre"))) {
                        gasto.setGastoCombustible(rsCombustible.getDouble("gasto_combustible"));
                    }
                }
            }
            
            PreparedStatement taller = connection.prepareStatement(GASTO_TALLER_SUCURSALES);
            taller.setDate(1, dateInicial);
            taller.setDate(2, dateFinal);
            ResultSet rsTaller = taller.executeQuery();
            while (rsTaller.next()) {
                for (ReporteGanancia gasto : gastos) {
                    if (gasto.getSucursal().equals(rsTaller.getString("nombre"))) {
                        gasto.setGastoTallerMano(rsTaller.getDouble("monto_mano_obra"));
                        gasto.setGastoTallerRepuestos(rsTaller.getDouble("monto_repuestos"));
                    }
                }
            }
            
            PreparedStatement depreciacion = connection.prepareStatement(DEPRECIACION_ACUMULADA_SUCURSALES);
            depreciacion.setDate(1, dateInicial);
            depreciacion.setDate(2, dateFinal);
            ResultSet rsDepreciacion = depreciacion.executeQuery();
            while (rsDepreciacion.next()) {
                for (ReporteGanancia gasto : gastos) {
                    if (gasto.getSucursal().equals(rsDepreciacion.getString("nombre"))) {
                        gasto.setDepreciacion(rsDepreciacion.getDouble("monto_depreciado"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los gastos operativos de sucursales " + e.getMessage());
        }
        return gastos;
    }
    
    public ReporteGanancia getGastosOperativos(LocalDate fechaInicial, LocalDate fechaFinal) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            ReporteGanancia gasto = new ReporteGanancia();
            Date dateInicial = Date.valueOf(fechaInicial);
            Date dateFinal = Date.valueOf(fechaFinal);
            
            PreparedStatement combustible = connection.prepareStatement(GASTOS_COMBUSTIBLE_VIAJE_PUBLICO);
            combustible.setDate(1, dateInicial);
            combustible.setDate(2, dateFinal);
            ResultSet rsCombustible = combustible.executeQuery();
            if (rsCombustible.next()) {
                gasto.setGastoCombustible(rsCombustible.getDouble("gasto_combustible"));
            }
            
            PreparedStatement taller = connection.prepareStatement(GASTO_TALLER);
            taller.setDate(1, dateInicial);
            taller.setDate(2, dateFinal);
            ResultSet rsTaller = taller.executeQuery();
            if (rsTaller.next()) {
                gasto.setGastoTallerMano(rsTaller.getDouble("monto_mano_obra"));
                gasto.setGastoTallerRepuestos(rsTaller.getDouble("monto_repuestos"));
            }
            
            PreparedStatement depreciacion = connection.prepareStatement(DEPRECIACION_ACUMULADA);
            depreciacion.setDate(1, dateInicial);
            depreciacion.setDate(2, dateFinal);
            ResultSet rsDepreciacion = depreciacion.executeQuery();
            if (rsDepreciacion.next()) {
                gasto.setDepreciacion(rsDepreciacion.getDouble("monto_depreciado"));
            }
            return gasto;
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los gastos operativos generales " + e.getMessage());
        }
    }
    
}
