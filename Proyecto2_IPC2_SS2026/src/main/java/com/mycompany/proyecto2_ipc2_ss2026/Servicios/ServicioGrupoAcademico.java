/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador.CarreraDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador.GrupoAcademicoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.CicloEscolar;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.GradoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataExistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.CurriculoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.CursoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.GradoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.GrupoAcademicoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.CurriculoRequest;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.GrupoAcademicoRequest;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioGrupoAcademico {
    
    private final GrupoAcademicoDAO grupodao;
    private final GradoDAO gradodao;
    private final CarreraDAO carreradao;
    private final CicloEscolar ciclo;
    
    public ServicioGrupoAcademico() {
        grupodao = new GrupoAcademicoDAO();
        gradodao = new GradoDAO();
        carreradao = new CarreraDAO();
        ciclo = new CicloEscolar();
    }
    
    public List<GrupoAcademicoDB> crearGrupoAcademicoGrado(GrupoAcademicoRequest grupo) throws AccesoALaDataException, ValorInvalidoException, DataExistenteException {
        if (grupo == null || grupo.getSeccion() == null || grupo.getSeccion().isEmpty()) {
            throw new ValorInvalidoException("Hacen falta valores para el grupo academico");
        }
        
        revisarAño(grupo.getAñoLectivo()); 
        
        if (grupodao.existeGrupoGrado(grupo)) {
            throw new DataExistenteException("Ya existe un grupo academico de grado con estos valores registrado en el sistema");
        }
        
        List<GradoDB> grados = gradodao.getGradosNormales();
        revisarGrado(grados, grupo.getGrado());
        
        return grupodao.crearGrupoAcademicoGrado(grupo);
    }
    
    public List<GrupoAcademicoDB> crearGrupoAcademicoCarrera(GrupoAcademicoRequest grupo) throws AccesoALaDataException, ValorInvalidoException, DataExistenteException {
        if (grupo == null || grupo.getSeccion() == null || grupo.getCarrera() == null || 
                grupo.getSeccion().isEmpty() || grupo.getCarrera().isEmpty()) {
            throw new ValorInvalidoException("Hacen falta valores para crear un grupo Academico");
        }
        
        revisarAño(grupo.getAñoLectivo());
        
        if (grupodao.existeGrupoCarrera(grupo)) {
            throw new DataExistenteException("Ya existe un grupo academico de carrera con estos valores registrados en el sistema");
        }
        
        List<GradoDB> gradosCarrera = gradodao.getGradosCarrera();
        revisarGrado(gradosCarrera, grupo.getGrado());
        
        return grupodao.crearGrupoAcademicoCarrera(grupo);
    }
    
    public List<GrupoAcademicoDB> getGruposDelAño(int pagina) throws AccesoALaDataException, ValorInvalidoException {
        if (pagina < LimitePagina.PAGINA_MINIMA) {
            throw new ValorInvalidoException("La pagina minima para buscar es: " + LimitePagina.PAGINA_MINIMA);
        }
        return grupodao.getGruposDelAño(pagina);
    }
    
    public List<GrupoAcademicoDB> getGruposPorAño(int año, int pagina) throws AccesoALaDataException, ValorInvalidoException {
        if (pagina < LimitePagina.PAGINA_MINIMA) {
            throw new ValorInvalidoException("La pagina minima para buscar es: " + LimitePagina.PAGINA_MINIMA);
        }
        return grupodao.getGruposPorAño(año, pagina);
    }
    
    public List<CurriculoDB> getCursosCurriculoGrado(int idGrado) throws AccesoALaDataException, DataInexistenteException {
        if (!gradodao.existeGrado(idGrado)) {
            throw new DataInexistenteException("El grado seleccionado no se pudo encontrar");
        }
        return grupodao.getCurriculosGrado(idGrado);
    }
    
    public List<CurriculoDB> getCursosCurriculoCarrera(String idCarrera) throws AccesoALaDataException, DataInexistenteException {
        if (!carreradao.existeCodigoCarrera(idCarrera)) {
            throw new DataInexistenteException("El grado seleccionado no se pudo encontrar");
        }
        return grupodao.getCurriculosCarrera(idCarrera);
    }
    
    public void agregarCursoCurriculoGrado(CurriculoRequest curriculo) throws AccesoALaDataException, DataExistenteException, ValorInvalidoException {
        if (curriculo == null) {
            throw new ValorInvalidoException("No se ha mandado ningun valor para el Curriculo");
        }
        
        List<GradoDB> grados = gradodao.getGradosNormales();
        revisarGrado(grados, curriculo.getGradoId());
        
        if (grupodao.existeCurriculoGrado(curriculo)) {
            throw new DataExistenteException("Ya se encuentra registrado el curso para el grado");
        }
        grupodao.crearCurriculoParaGrado(curriculo);
    }
    
    public void agregarCursoCurriculoCarrera(CurriculoRequest curriculo) throws AccesoALaDataException, DataExistenteException, ValorInvalidoException {
        if (curriculo == null || curriculo.getCarreraId() == null || curriculo.getCarreraId().isEmpty()) {
            throw new ValorInvalidoException("Hacen falta valores para el curriculo");
        }
        if (grupodao.existeCurriculoCarrera(curriculo)) {
            throw new DataExistenteException("Ya se encuentra registrado el curso para el nivel de carrera");
        }
        
        List<GradoDB> grados = gradodao.getGradosCarrera();
        revisarGrado(grados, curriculo.getGradoId());
        
        grupodao.crearCurriculoParaCarrera(curriculo);
    }
    
    public void eliminarCurriculo(int idCurriculo) throws AccesoALaDataException, ValorInvalidoException {
        grupodao.eliminarCurriculo(idCurriculo);
    }
    
    private void revisarAño(int añoGrupo) throws AccesoALaDataException, ValorInvalidoException {
        int añoActual = ciclo.getAñoActivo();
        if (añoActual < añoGrupo) {
            throw new ValorInvalidoException("No se puede crear un Grupo Academico para un año anterior al que esta activo ( " + añoActual + " )");
        }
    }
    
    private void revisarGrado(List<GradoDB> grados, int gradoGrupo) throws ValorInvalidoException {
        for (GradoDB grado : grados) {
            if (grado.getId() == gradoGrupo) {
                return;
            }
        }
        throw new ValorInvalidoException("El grado seleccionado no es valido para usarse con esta opcion");
    }
    
}
