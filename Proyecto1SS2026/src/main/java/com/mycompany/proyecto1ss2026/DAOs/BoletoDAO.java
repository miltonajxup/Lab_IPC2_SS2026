/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto1ss2026.DAOs;

import com.mycompany.proyecto1ss2026.ConeccionBaseDatos.DBConnection;
import com.mycompany.proyecto1ss2026.Exeptions.AccesoALaDataException;
import com.mycompany.proyecto1ss2026.Modelos.Request.BoletoRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author milton
 */
public class BoletoDAO {
    
    
    private final String GENERAR_REGISTRO_BOLETO = "INSERT INTO boleto_viaje (usuario, viaje, asiento) VALUES (?,?,?)";
    
    public void generarRegistroBoleto(BoletoRequest request) throws AccesoALaDataException {
        Connection connection = DBConnection.getConnection();
        try {
            PreparedStatement insert = connection.prepareStatement(GENERAR_REGISTRO_BOLETO);
            insert.setString(1, request.getUsuario());
            insert.setInt(2, request.getViaje());
            insert.setInt(3, request.getAsiento());
            insert.executeUpdate();
        } catch (SQLException e) {
            throw new AccesoALaDataException("Error al gererar un boleto " + e.getMessage());
        }
    }
    
//    public void () throws AccesoALaDataException {
//        Connection connection = DBConnection.getConnection();
//        try {
//            PreparedStatement i = connection.prepareStatement();
//        } catch (SQLException e) {
//            throw new AccesoALaDataException("Error al " + e.getMessage());
//        }
//    }
    //sigo sin entender como funcionan los analizadores de lenguaje, mas especificamente un automata finito determinista, explicalo desde sus componentes formales, con un ejmplo basico y con uno complejo, el objetivo de organizarlo asi y ten en cuenta que mi intencion es pasarlo a codigo, pero si ves que es inconveniente mezclarlo ahora, de momento puedes dejarlo para despues
}
