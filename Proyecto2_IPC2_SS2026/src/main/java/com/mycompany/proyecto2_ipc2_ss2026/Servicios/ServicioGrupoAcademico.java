/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador.CarreraDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador.GrupoAcademicoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.CicloEscolar;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataExistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.CurriculoDB;
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
    private final CarreraDAO carreradao;
    private final CicloEscolar ciclo;
    
    public ServicioGrupoAcademico() {
        grupodao = new GrupoAcademicoDAO();
        carreradao = new CarreraDAO();
        ciclo = new CicloEscolar();
    }
    
    public List<GrupoAcademicoDB> crearGrupoAcademicoGrado(GrupoAcademicoRequest grupo) throws AccesoALaDataException, ValorInvalidoException, DataExistenteException {
        revisarAño(grupo.getAñoLectivo());
        if (faltanValores(grupo)) {
            throw new ValorInvalidoException("Faltan valores para crear un grupo adademico de grado");
        }
        if (grupodao.existeGrupoGrado(grupo)) {
            throw new DataExistenteException("Ya existe un grupo academico de grado con estos valores registrado en el sistema");
        }
        List<GradoDB> gradosCarrera = carreradao.getGradosDisponibles();
        for (GradoDB grado : gradosCarrera) {
            if (grado.getId() == grupo.getGrado()) {
                throw new ValorInvalidoException("No se puede crear el grupo porque el grado seleccionado pertenece al nivel de carrera");
            }
        }
        return grupodao.crearGrupoAcademicoGrado(grupo);
    }
    
    public List<GrupoAcademicoDB> crearGrupoAcademicoCarrera(GrupoAcademicoRequest grupo) throws AccesoALaDataException, ValorInvalidoException, DataExistenteException {
        revisarAño(grupo.getAñoLectivo());
        if (faltanValores(grupo) || grupo.getCarrera() == null || grupo.getCarrera().isEmpty()) {
            throw new ValorInvalidoException("Faltan valores para crear un grupo academico de una carrera");
        }
        if (grupodao.existeGrupoCarrera(grupo)) {
            throw new DataExistenteException("Ya existe un grupo academico de carrera con estos valores registrados en el sistema");
        }
        return grupodao.crearGrupoAcademicoCarrera(grupo);
    }
    
    public List<GrupoAcademicoDB> getGruposPorAño(int año, int pagina) throws AccesoALaDataException, ValorInvalidoException {
        if (pagina < LimitePagina.PAGINA_MINIMA) {
            throw new ValorInvalidoException("La pagina minima para buscar es: " + LimitePagina.PAGINA_MINIMA);
        }
        return grupodao.getGruposPorAño(año, pagina);
    }
    
    public List<CurriculoDB> getCursosGradoCarrera(CurriculoRequest curriculo) throws AccesoALaDataException {
        if (curriculo.getCarreraId() != null) {
            return grupodao.getCurriculosCarrera(curriculo);
        } else {
            return grupodao.getCurriculosGrado(curriculo);
        }
    }
    
    public List<CurriculoDB> agregarCursoCurriculoGrado(CurriculoRequest curriculo) throws AccesoALaDataException, DataExistenteException {
        if (grupodao.existeCurriculoGrado(curriculo)) {
            throw new DataExistenteException("Ya se encuentra registrado el curso para el grado");
        }
        return grupodao.crearCurriculoParaGrado(curriculo);
    }
    
    public List<CurriculoDB> agregarCursoCurriculoCarrera(CurriculoRequest curriculo) throws AccesoALaDataException, DataExistenteException {
        if (grupodao.existeCurriculoCarrera(curriculo)) {
            throw new DataExistenteException("Ya se encuentra registrado el curso para el nivel de carrera");
        }
        return grupodao.crearCurriculoParaCarrera(curriculo);
    }
    
    public void eliminarCurriculo(int idCurriculo) throws AccesoALaDataException, ValorInvalidoException {
        if (grupodao.curriculoUtilizado(idCurriculo)) {
            throw new ValorInvalidoException("No se puede eliminar el curso porque esta siendo usado por otros registros");
        }
        grupodao.eliminarCurriculo(idCurriculo);
    }
    
    private void revisarAño(int añoGrupo) throws AccesoALaDataException, ValorInvalidoException {
        int añoActual = ciclo.getAñoActivo();
        if (añoActual < añoGrupo) {
            throw new ValorInvalidoException("No se puede crear un Grupo Academico para un año anterior al que esta activo ( " + añoActual + " )");
        }
    }
    
    private boolean faltanValores(GrupoAcademicoRequest grupo) {
        return grupo.getAñoLectivo() == 0 || grupo.getGrado() == 0;
    }
    
}
