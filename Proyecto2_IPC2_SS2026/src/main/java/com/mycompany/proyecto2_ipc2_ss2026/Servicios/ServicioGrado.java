/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.DAOs.GradoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.GradoDB;

/**
 *
 * @author milton
 */
public class ServicioGrado {
    
    public void modificarEstado(int id) throws AccesoALaDataException, ValorInvalidoException {
        GradoDAO gradodao  = new GradoDAO();
        GradoDB grado = gradodao.gradoPorId(id);
        if (grado == null) {
            throw new ValorInvalidoException("El grado no existe");
        }
        gradodao.modificarEstado(!grado.isEstado(), id);
    }
    
}
