/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimiteTamaño;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador.CarreraDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.GradoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataExistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.CarreraDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.GradoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.CarreraRequest;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioCarrera {
    
    private final CarreraDAO carreradao;
    private final GradoDAO gradodao;
    
    public ServicioCarrera() {
        carreradao = new CarreraDAO();
        gradodao = new GradoDAO();
    }
    
    public List<CarreraDB> crearCarrera(CarreraRequest carrera) throws AccesoALaDataException, ValorInvalidoException, DataExistenteException {
        if (carrera == null || carrera.getCodigo() == null || carrera.getNombre() == null || 
                carrera.getCodigo().isEmpty() || carrera.getNombre().isEmpty()) {
            throw new ValorInvalidoException("Faltan valores para crear una carrera nueva");
        }
        if (carrera.getCodigo().length() > LimiteTamaño.CODIGO.getLimite()) {
            throw new ValorInvalidoException("El codigo no puede tener mas de " + LimiteTamaño.CODIGO.getLimite() + " caracteres");
        }
        revisarTamaño(carrera.getNombre());
        if (carreradao.existeCodigoCarrera(carrera.getCodigo())) {
            throw new DataExistenteException("El codigo de la carrera ya esta sientdo utilizado");
        }
        if (carreradao.existeNombreCarrera(carrera.getNombre()) ) {
            throw new DataExistenteException("Ya existe registrado una carrera llamada " + carrera.getNombre());
        }
        
        revisarCodigosGrado(carrera.getGradosId());
        
        return carreradao.crearCarrera(carrera);
    }
    
    public CarreraDB modificarCarrera(CarreraRequest carrera, String codigoCarrera) throws AccesoALaDataException, ValorInvalidoException, DataInexistenteException, DataExistenteException {
        if (carrera == null || codigoCarrera == null || carrera.getNombre() == null || 
                codigoCarrera.isEmpty() || carrera.getNombre().isEmpty()) {
            throw new ValorInvalidoException("Faltan valores para modificar una carrera");
        }
        revisarTamaño(carrera.getNombre());
        CarreraDB carreradb = carreradao.getCarreraPorId(codigoCarrera);
        if (carreradb == null) {
            throw new DataInexistenteException("No se pudo encontrar la carrera con el codigo " + codigoCarrera);
        }
        if (!carreradb.getCodigo().equals(codigoCarrera)) {
            if (!carreradao.existeNombreCarrera(carrera.getNombre())) {
                throw new DataExistenteException("Ya existe registrado una carrera llamada " + carrera.getNombre());
            }
        }
        return carreradao.modificarCarrera(carrera, codigoCarrera);
    }
    
    public CarreraDB modificarEstadoCarrera(CarreraRequest carrera, String codigoCarrera) throws AccesoALaDataException, DataInexistenteException, ValorInvalidoException {
        if (carrera == null || codigoCarrera == null) {
            throw new ValorInvalidoException("Hacen falta valores para modificar la carrera");
        }
        if (carreradao.getCarreraPorId(codigoCarrera) == null) {
            throw new DataInexistenteException("No se pudo encontrar la carrera con el codigo " + codigoCarrera);
        }
        return carreradao.modificarEstadoCarrera(carrera.isEstado(), codigoCarrera);
    }
    
    public List<CarreraDB> buscarCarrerasActivas(int pagina) throws AccesoALaDataException, ValorInvalidoException {
        if (pagina < LimitePagina.PAGINA_MINIMA) {
            throw new ValorInvalidoException("La pagina minima para buscar es: " + LimitePagina.PAGINA_MINIMA);
        }
        return carreradao.getCarrerasActivas(pagina);
    }
    
    public CarreraDB buscarCarreraPorId(String codigoCarrera) throws AccesoALaDataException, DataInexistenteException {
        CarreraDB carreradb = carreradao.getCarreraPorId(codigoCarrera);
        if (carreradb == null) {
            throw new DataInexistenteException("No se pudo encontrar la carrera con el codigo: " + codigoCarrera);
        }
        return carreradb;
    }
    
    public List<CarreraDB> buscarCarrerasPorGrado(int idGrado) throws AccesoALaDataException, DataInexistenteException {
        List<CarreraDB> carreras = carreradao.getCarrerasPorGrado(idGrado);
        if (carreras.isEmpty()) {
            throw new DataInexistenteException("No se pudieron encontrar Carreras para este Grado");
        }
        return carreras;
    }
    
    private void revisarCodigosGrado(List<Integer> codigos) throws AccesoALaDataException, ValorInvalidoException {
        List<GradoDB> gradosDisponibles = gradodao.getGradosCarrera();
        if (codigos == null || codigos.isEmpty()) {
            throw new ValorInvalidoException("Debe seleccionar al menos un grado." );
        }
        boolean valido;
        for (Integer codigo : codigos) {
            valido = false;
            for (GradoDB grado : gradosDisponibles) {
                if (grado.getId() == codigo) {
                    valido = true;
                }
            }
            if (!valido) {
                throw new ValorInvalidoException("El grado que con codigo " + codigo + " no es valido para asignarle una carrera");
            }
        }
        int direrencia = gradosDisponibles.size() - codigos.size();
        if (direrencia > 0) {
            revisarGradosValidos(codigos, gradosDisponibles, direrencia);
        }
    }
    
    private void revisarGradosValidos(List<Integer> codigos, List<GradoDB> grados, int elementosMenos) throws ValorInvalidoException {
        boolean valido;
        for (Integer codigo : codigos) {
            valido = false;
            for (int i = 0; i < grados.size() - elementosMenos; i++) {
                if (grados.get(i).getId() == codigo) {
                    valido = true;
                }
            }
            if (!valido) {
                throw new ValorInvalidoException("Los grados deben seguir un orden y comenzar con cuarto grado");
            }
        }
    }
    
    private void revisarTamaño(String nombre) throws ValorInvalidoException {
        if (nombre.length() > LimiteTamaño.NOMBRE.getLimite()) {
            throw new ValorInvalidoException("El nombre no puede tener mas de " + LimiteTamaño.NOMBRE.getLimite() + " caracteres");
        }
    }
    
}
