/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.ModelosDB;

/**
 *
 * @author milton
 */
public class InformacionMedicaDB {
    
    private final String condicionMedica;
    private final String alergias;
    private final String medicamentos;

    public InformacionMedicaDB(String condicionMedica, String alergias, String medicamentos) {
        this.condicionMedica = condicionMedica;
        this.alergias = alergias;
        this.medicamentos = medicamentos;
    }

    public String getCondicionMedica() {
        return condicionMedica;
    }

    public String getAlergias() {
        return alergias;
    }

    public String getMedicamentos() {
        return medicamentos;
    }
    
}
