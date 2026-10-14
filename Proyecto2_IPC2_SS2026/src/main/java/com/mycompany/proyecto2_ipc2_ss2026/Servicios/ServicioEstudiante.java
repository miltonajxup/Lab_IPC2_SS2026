/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimiteTamaño;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Estudiante.EstudianteDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.UsuarioDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.BoletaPagoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.InformacionMedicaDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.MultaDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.NotaDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.PagoBoletaDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.PrestamoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.InformacionMedicaRequest;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioEstudiante {
    
    private final EstudianteDAO estudiantedao;
    private final UsuarioDAO usuariodao;
    
    public ServicioEstudiante() {
        estudiantedao = new EstudianteDAO();
        usuariodao = new UsuarioDAO();
    }
    
    public List<NotaDB> buscarNotasEstudiante(String idEstudiante) throws AccesoALaDataException, DataInexistenteException {
        revisarExistencia(idEstudiante);
        
        return estudiantedao.getNotas(idEstudiante);
    }
    
    public InformacionMedicaDB getInformacionMedica(String idEstudiante) throws AccesoALaDataException, DataInexistenteException {
        revisarExistencia(idEstudiante);
        return estudiantedao.informacionPorEstudiante(idEstudiante);
    }
    
    public InformacionMedicaDB modificarInformacionMedica(InformacionMedicaRequest info, String idEstudiante) throws AccesoALaDataException, ValorInvalidoException, DataInexistenteException {
        if (info == null || info.getCondicionMedica() == null || info.getAlergias() == null || info.getMedicamentos() == null || idEstudiante == null) {
            throw new ValorInvalidoException("Faltan agregar valores para la informacion medica");
        }
        if (info.getCondicionMedica().length() > LimiteTamaño.DESCRIPCION.getLimite()) {
            throw new ValorInvalidoException("La descripcion medica no puede tener mas de " + LimiteTamaño.DESCRIPCION.getLimite() + " caracteres");
        }
        if (info.getAlergias().length() > LimiteTamaño.DESCRIPCION.getLimite()) {
            throw new ValorInvalidoException("La descripcion de las alergias no puede tener mas de " + LimiteTamaño.DESCRIPCION.getLimite() + " caracteres");
        }
        if (info.getMedicamentos().length() > LimiteTamaño.DESCRIPCION.getLimite()) {
            throw new ValorInvalidoException("La descripcion de los medicamentos no puede tener mas de " + LimiteTamaño.DESCRIPCION.getLimite() + " caracteres");
        }
        revisarExistencia(idEstudiante);
        return estudiantedao.modificarInformacionMedica(info, idEstudiante);
    }
    
    public List<PrestamoDB> buscarPrestamosPendientes(String idEstudiante) throws AccesoALaDataException, DataInexistenteException {
        revisarExistencia(idEstudiante);
        return estudiantedao.getPrestamosPendientes(idEstudiante);
    }
    
    public List<MultaDB> buscarMultas(String idEstudiante) throws AccesoALaDataException, DataInexistenteException {
        revisarExistencia(idEstudiante);
        return estudiantedao.getMultasPendientes(idEstudiante);
    }
    
    public List<BoletaPagoDB> buscarBoletasDelAño(String idEstudiante) throws AccesoALaDataException, DataInexistenteException {
        revisarExistencia(idEstudiante);
        return estudiantedao.getBoletasDelAño(idEstudiante);
    }
    
    public List<PagoBoletaDB> buscarPagosRealizados(String idEstudiante) throws AccesoALaDataException, DataInexistenteException {
        revisarExistencia(idEstudiante);
        return estudiantedao.getPagoRealizados(idEstudiante);
    }
    
    private void revisarExistencia(String idEstudiante) throws AccesoALaDataException, DataInexistenteException {
        if (!usuariodao.existeUsuarioPorDpi(idEstudiante)) {
            throw new DataInexistenteException("No se pudo encontrar el usuario con el ID: " + idEstudiante);
        }
    }
    
}
