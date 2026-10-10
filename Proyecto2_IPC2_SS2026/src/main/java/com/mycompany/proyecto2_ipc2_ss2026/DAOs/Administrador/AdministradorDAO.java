/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador;

import com.mycompany.proyecto2_ipc2_ss2026.Connection.DBConnectionSingleton;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.CicloEscolar;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.CursoMaestroRequest;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.TipoPago;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.ArmarBoletaPago;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.BoletaPagoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.PagoEmpleadoDB;
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
public class AdministradorDAO {
    
    //SELECT ins.estudiante, grad.costo_colegiatura FROM boleta_pago AS bol RIGHT JOIN inscripcion AS ins ON bol.estudiante_dpi = ins.estudiante 
    //JOIN grupo_ciclo_escolar AS grup ON ins.grupo_id = grup.id JOIN grado AS grad ON grad.id = grup.grado_id WHERE bol.id IS NULL
    private final String GET_ESTUDIANTES_INSCRITOS = 
            """
            SELECT ins.*, grad.costo_colegiatura FROM inscripcion AS ins 
            JOIN grupo_ciclo_escolar AS grup ON ins.grupo_id = grup.id 
            JOIN grado AS grad ON grup.grado_id = grad.id 
            WHERE ins.estado = TRUE AND grup.ciclo_escolar_id = ?""";
    private final String BOLETA_GENERADA = "SELECT * FROM boleta_pago WHERE ciclo_escolar_id = ? AND estudiante_dpi = ? AND mes_pago = ? AND tipo_pago = ?";
    private final String GENERAR_BOLETAS_PAGO = "INSERT INTO boleta_pago (monto, tipo_pago, mes_pago, ciclo_escolar_id, estudiante_dpi) VALUES (?,?,?,?,?)";
    private final String GET_BOLETAS_GENERERADAS = "SELECT bol.*, usu.nombre AS nombre_estudiante FROM boleta_pago AS bol JOIN usuario AS usu ON bol.estudiante_dpi = usu.dpi WHERE ciclo_escolar_id = ? AND mes_pago = ? LIMIT ?, ?";
    
    private final String GET_EMPLEADOS_CONTRATADOS = "SELECT * FROM contrato_empleado WHERE ciclo_escolar_id = ?";
    private final String PAGO_REALIZADO = "SELECT * FROM pago_empleado WHERE mes_pago = ? AND ciclo_escolar_id = ? AND empleado_pagado = ?";
    private final String PAGAR_MENSUALIDAD = "INSERT INTO pago_empleado (monto_pagado, mes_pago, ciclo_escolar_id, empleado_pagado) VALUES (?,?,?,?)";
    private final String GET_PAGOS_REALIZADOS = "SELECT pago.*, usu.nombre AS nombre_empleado FROM pago_empleado AS pago JOIN usuario AS usu ON pago.empleado_pagado = usu.dpi WHERE ciclo_escolar_id = ? AND mes_pago = ? LIMIT ?, ?";
    
    private final String CURSO_ASIGNADO = "SELECT * FROM curso_maestro WHERE ciclo_escolar_id = ? AND curso_id = ?";
    private final String ASIGNAR_MAESTRO_CURSO = "INSERT INTO curso_maestro (maestro_id, curso_id, ciclo_escolar_id) VALUES (?,?,?)";
    private final String CAMBIAR_MAESTRO_ASIGNADO = "UPDATE curso_maestro SET maestro_id = ? WHERE curso_id = ? AND ciclo_escolar_id = ?";
    
    private final CicloEscolar ciclo;
    private final ArmarBoletaPago boleta;
    
    public AdministradorDAO() {
        ciclo = new CicloEscolar();
        boleta = new ArmarBoletaPago();
    }
    
    public int generarBoletasDePago(String mesBoleta) throws AccesoALaDataException {
        int año = ciclo.getAñoActivo();
        int boletasGeneradas = 0;
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement psInscritos = connection.prepareStatement(GET_ESTUDIANTES_INSCRITOS);
            psInscritos.setInt(1, año);
            ResultSet rsInscritos = psInscritos.executeQuery();
            while (rsInscritos.next()) {
                String dpiEstudiante = rsInscritos.getString("estudiante");
                if (!boletaGenerada(connection, año, dpiEstudiante, mesBoleta)) {
                    boletasGeneradas++;
                    generarBoletaEstudiante(connection, rsInscritos.getDouble("costo_colegiatura"), mesBoleta, año, dpiEstudiante);
                }
            }
            
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al generar las boletas de pago: " + e.getMessage());
        }
        return boletasGeneradas;
    }
    
    private boolean boletaGenerada(Connection connection, int año, String dpiEstudiante, String mesBoleta) throws SQLException {
        PreparedStatement psBoleta = connection.prepareStatement(BOLETA_GENERADA);
        psBoleta.setInt(1, año);
        psBoleta.setString(2, dpiEstudiante);
        psBoleta.setString(3, mesBoleta);
        psBoleta.setString(4, TipoPago.COLEGIATURA.name());
        ResultSet rsBoleta = psBoleta.executeQuery();
        return rsBoleta.next();
    }
    
    private void generarBoletaEstudiante(Connection connection, double monto, String mesBoleta, int año, String dpiEstudiante) throws SQLException {
        PreparedStatement psGenerar = connection.prepareStatement(GENERAR_BOLETAS_PAGO);
        psGenerar.setDouble(1, monto);
        psGenerar.setString(2, TipoPago.COLEGIATURA.name());
        psGenerar.setString(3, mesBoleta);
        psGenerar.setInt(4, año);
        psGenerar.setString(5, dpiEstudiante);
        psGenerar.executeUpdate();
    }
    
    public List<BoletaPagoDB> getBoletasDePagoGeneradas(String mes, int pagina) throws AccesoALaDataException {
        List<BoletaPagoDB> boletas = new ArrayList<>();
        int año = ciclo.getAñoActivo();
        int paginacion = (pagina - 1) * LimitePagina.LIMITE;
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_BOLETAS_GENERERADAS);
            ps.setInt(1, año);
            ps.setString(2, mes);
            ps.setInt(3, paginacion);
            ps.setInt(4, LimitePagina.LIMITE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                boletas.add(boleta.armar(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar las boletas generadas " + e.getMessage());
        }
        return boletas;
    }
    
    public int pagarAEmpleados(String mesPago) throws AccesoALaDataException {
        int año = ciclo.getAñoActivo();
        int pagosRealizados = 0;
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement psEmpleados = connection.prepareStatement(GET_EMPLEADOS_CONTRATADOS);
            psEmpleados.setInt(1, año);
            ResultSet rsEmpleados = psEmpleados.executeQuery();
            while (rsEmpleados.next()) {
                String dpiEmpleado = rsEmpleados.getString("empleado_dpi");
                if (!pagoRealizado(connection, mesPago, año, dpiEmpleado)) {
                    pagosRealizados++;
                    realizarPagoEmpleado(connection, rsEmpleados.getDouble("salario"), mesPago, año, dpiEmpleado);
                }
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al generar los pagos de los empleados: " + e.getMessage());
        }
        return pagosRealizados;
    }
    
    private boolean pagoRealizado(Connection connection, String mesPago, int año, String dpiEmpleado) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(PAGO_REALIZADO);
        ps.setString(1, mesPago);
        ps.setInt(2, año);
        ps.setString(3, dpiEmpleado);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }
    
    private void realizarPagoEmpleado(Connection connection, double salario, String mesPago, int año, String dpiEmpleado) throws SQLException {
        PreparedStatement psPago = connection.prepareStatement(PAGAR_MENSUALIDAD);
        psPago.setDouble(1, salario);
        psPago.setString(2, mesPago);
        psPago.setInt(3, año);
        psPago.setString(4, dpiEmpleado);
        psPago.executeUpdate();
    }
    
    public List<PagoEmpleadoDB> getRegistrosPagos(String mes, int pagina) throws AccesoALaDataException {
        int año = ciclo.getAñoActivo();
        int paginacion = (pagina - 1) * LimitePagina.LIMITE;
        List<PagoEmpleadoDB> pagos = new ArrayList<>();
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        try {
            PreparedStatement ps = connection.prepareStatement(GET_PAGOS_REALIZADOS);
            ps.setInt(1, año);
            ps.setString(2, mes);
            ps.setInt(3, paginacion);
            ps.setInt(4, LimitePagina.LIMITE);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                pagos.add(armarPago(rs));
            }
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al buscar los pagos realizados a los empleados: " + e.getMessage());
        }
        return pagos;
    }
    
    private PagoEmpleadoDB armarPago(ResultSet rs) throws SQLException {
        return new PagoEmpleadoDB(
                rs.getInt("id"), 
                rs.getDate("fecha_pago").toLocalDate(), 
                rs.getDouble("monto_pagado"), 
                rs.getString("mes_pago"), 
                rs.getInt("ciclo_escolar_id"), 
                rs.getString("empleado_pagado"), 
                rs.getString("nombre_empleado"));
    }
    
    public boolean cursoTieneMaestro(int curso) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        int año = ciclo.getAñoActivo();
        try {
            PreparedStatement ps = connection.prepareStatement(CURSO_ASIGNADO);
            ps.setInt(1, año);
            ps.setInt(2, curso);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al revisar si un curso tiene asignado un maestro: " + e.getMessage());
        }
    }
            
    public void asignarMaestroCurso(CursoMaestroRequest request) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        int año = ciclo.getAñoActivo();
        try {
            PreparedStatement ps = connection.prepareStatement(ASIGNAR_MAESTRO_CURSO);
            ps.setString(1, request.getMaestro());
            ps.setInt(2, request.getCurso());
            ps.setInt(3, año);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al asignar a un maestro a un curso " + e.getMessage());
        }
    }
    
    public void modificarCursoMaestro(CursoMaestroRequest curso) throws AccesoALaDataException {
        Connection connection = DBConnectionSingleton.getInstancia().getConnection();
        int año = ciclo.getAñoActivo();
        try {
            PreparedStatement ps = connection.prepareStatement(CAMBIAR_MAESTRO_ASIGNADO);
            ps.setString(1, curso.getMaestro());
            ps.setInt(2, curso.getCurso());
            ps.setInt(3, año);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al modificar al maestro asignado a un curso: " + e.getMessage());
        }
    }
    
}
