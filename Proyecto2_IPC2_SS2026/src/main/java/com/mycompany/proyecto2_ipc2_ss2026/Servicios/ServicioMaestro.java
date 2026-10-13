/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.Nota;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.RolUsuario;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador.EmpleadoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Maestro.MaestroDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.AsistenciaDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.CursoMaestro;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.EmpleadoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.EstudianteCurso;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.NotaDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.AsistenciaDiaRequest;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.AsistenciaRequest;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.NotaRequest;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioMaestro {
    
    private final MaestroDAO maestrodao;
    private final EmpleadoDAO usuariodao;
    
    public ServicioMaestro() {
        maestrodao = new MaestroDAO();
        usuariodao = new EmpleadoDAO();
    }
    
    public List<CursoMaestro> buscarCursosMaestro(String dpiMaestro) throws AccesoALaDataException, DataInexistenteException, ValorInvalidoException {
        EmpleadoDB usuario = usuariodao.getEmpleadoPorID(dpiMaestro);
        if (usuario == null) {
            throw new DataInexistenteException("No se pudo encontrar el maestro para buscar sus cursos");
        }
        if (usuario.getRol() != RolUsuario.MAESTRO) {
            throw new ValorInvalidoException("El empleado elegido no es un " + RolUsuario.MAESTRO);
        }
        List<CursoMaestro> cursos = maestrodao.getCursosMaestro(dpiMaestro);
        if (cursos.isEmpty()) {
            throw new DataInexistenteException("El meastro no tiene cursos asigados");
        }
        return cursos;
    }
    
    public List<EstudianteCurso> buscarEstudiantesCurso(int idGrupo, int curso) throws AccesoALaDataException {
        return maestrodao.getEstudiantesCurso(idGrupo, curso);
    }
    
    public List<LocalDate> getFechaTomadasAsistencia(int grupo, int idCurso) throws AccesoALaDataException {
        return maestrodao.fechasTomadasAsitencia(grupo, idCurso);
    }
    
    public List<AsistenciaDB> getAsistencias(int idGrupo, int idCurso, String fecha) throws AccesoALaDataException, ValorInvalidoException, DataInexistenteException {
        LocalDate date;
        try {
            date = LocalDate.parse(fecha);
        } catch (DateTimeParseException e) {
            throw new ValorInvalidoException("La fecha debe tener el formato yyyy-MM-dd");
        }
        if (!maestrodao.asistenciaTomadaDia(idGrupo, idCurso, date)) {
            throw new DataInexistenteException("No hay registros de asistencia para la fecha: " + fecha);
        }
        return maestrodao.getAsistenciasDelDia(idGrupo, idCurso, date);
    }
    
    public List<AsistenciaDB> generarAsistencias(AsistenciaDiaRequest asistencia) throws AccesoALaDataException {
        if (maestrodao.asistenciaTomadaDia(asistencia.getIdGrupoEstudiantes(), asistencia.getIdCurso(), asistencia.getFecha())) {
            return maestrodao.getAsistenciasDelDia(asistencia.getIdGrupoEstudiantes(), asistencia.getIdCurso(), asistencia.getFecha());
        }
        return maestrodao.generarAsistencias(asistencia);
    }
    
    public AsistenciaDB tomarAsistencia(AsistenciaRequest asistencia, int id) throws AccesoALaDataException {
        return maestrodao.tomarAsistencia(asistencia, id);
    }
    
    public List<NotaDB> getNotasCurso(int idGrupo, int idCurso) throws AccesoALaDataException {
        return maestrodao.getNotasCurso(idGrupo, idCurso);
    }
    
    public NotaDB modificarZona(NotaRequest nota, int id) throws AccesoALaDataException, ValorInvalidoException {
        if (nota.getNotaZona() < Nota.MINIMO.getValor()) {
            throw new ValorInvalidoException("La nota minima permitida es: " + Nota.MINIMO.getValor());
        }
        if (nota.getNotaZona() > Nota.MAXIMO_ZONA.getValor()) {
            throw new ValorInvalidoException("La nota maxima para la zona es: " + Nota.MAXIMO_ZONA);
        }
        return maestrodao.modificarZona(nota.getNotaZona(), id);
    }
    
    public NotaDB modificarExamen(NotaRequest nota, int id) throws AccesoALaDataException, ValorInvalidoException {
        if (nota.getExamenFinal() < Nota.MINIMO.getValor()) {
            throw new ValorInvalidoException("La nota minima permitida es: " + Nota.MINIMO);
        }
        if (nota.getExamenFinal() > Nota.MAXIMO_FINAL.getValor()) {
            throw new ValorInvalidoException("La nota maxima para el examen final es: " + Nota.MAXIMO_FINAL.getValor());
        }
        return maestrodao.modificarExamen(nota.getExamenFinal(), id);
    }
    
}
