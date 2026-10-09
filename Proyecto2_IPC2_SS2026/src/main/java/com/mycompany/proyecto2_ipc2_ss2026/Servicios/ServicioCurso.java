/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimiteTamaño;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador.CursoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataExistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.CursoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.CursoRequest;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioCurso {
    
    private final CursoDAO cursodao;
    
    public ServicioCurso() {
        cursodao = new CursoDAO();
    }
    
    public List<CursoDB> getCursosPaginados(int pagina) throws AccesoALaDataException, ValorInvalidoException {
        if (pagina < LimitePagina.PAGINA_MINIMA) {
            throw new ValorInvalidoException("La paginaa minima que se puede visualizar es " + LimitePagina.PAGINA_MINIMA);
        }
        return cursodao.getTodosLosCursos(pagina);
    }
    
    public List<CursoDB> crearCurso(CursoRequest curso) throws AccesoALaDataException, ValorInvalidoException, DataExistenteException {
        if (curso.getNombre() == null || curso.getNombre().isEmpty()) {
            throw new ValorInvalidoException("Falta especificar el nombre del curso");
        }
        revisarTamaños(curso);
        if (cursodao.existeCurso(curso.getNombre())) {
            throw new DataExistenteException("Ya existe un curso llamado " + curso.getNombre() + " en el sistema");
        }
        return cursodao.crearCurso(curso);
    }
    
    public CursoDB modificarCurso(CursoRequest curso, int idCurso) throws AccesoALaDataException, ValorInvalidoException, DataExistenteException, DataInexistenteException {
        if (curso.getNombre() == null || curso.getNombre().isEmpty()) {
            throw new ValorInvalidoException("Falta especificar el nombre del curso");
        }
        revisarTamaños(curso);
        CursoDB cursodb = cursodao.getCursoId(idCurso);
        if (cursodb == null) {
            throw new DataInexistenteException("No se pudo encontrar el curso para modificar");
        }
        if (!cursodb.getNombre().equals(curso.getNombre())) {
            if (cursodao.existeCurso(curso.getNombre())) {
                throw new DataExistenteException("Ya existe un curso llamado " + curso.getNombre() + " en el sistema");
            }
        }
        
        return cursodao.modificarCurso(curso, idCurso);
    }
    
    private void revisarTamaños(CursoRequest curso) throws ValorInvalidoException {
        if (curso.getNombre().length() > LimiteTamaño.NOMBRE.getLimite()) {
            throw new ValorInvalidoException("El nombre no puede tener mas de " + LimiteTamaño.NOMBRE.getLimite() + " caracteres");
        }
        if (curso.getDescripcion().length() > LimiteTamaño.DESCRIPCION.getLimite()) {
            throw new ValorInvalidoException("La descripcion no puede tener mas de " + LimiteTamaño.DESCRIPCION.getLimite() + " caracteres");
        }
    }
    
}
