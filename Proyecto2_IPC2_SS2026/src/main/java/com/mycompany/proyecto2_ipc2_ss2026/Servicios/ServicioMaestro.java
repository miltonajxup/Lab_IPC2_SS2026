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
    
    public CursoMaestro buscarCursoMaestroPorId(int id, String idMaestro) throws AccesoALaDataException, DataInexistenteException {
        CursoMaestro curso = maestrodao.getCursosMaestroPorId(id, idMaestro);
        if (curso == null) {
            throw new DataInexistenteException("No se pudo encontrar el curso asignado al maestro");
        }
        return curso;
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
            String[] partes = fecha.split(",");
            if (partes.length == 3) {
                fecha = partes[0] + "-" + partes[1] + "-" + partes[2];
            } 
            date = LocalDate.parse(fecha);
        } catch (DateTimeParseException e) {
            throw new ValorInvalidoException("La fecha debe tener el formato yyyy-MM-dd y es: " + fecha);
        }
        if (!maestrodao.asistenciaTomadaDia(idGrupo, idCurso, date)) {
            throw new DataInexistenteException("No hay registros de asistencia para la fecha: " + fecha);
        }
        return maestrodao.getAsistenciasDelDia(idGrupo, idCurso, date);
    }
    
    public List<AsistenciaDB> generarAsistencias(AsistenciaDiaRequest asistencia) throws AccesoALaDataException, ValorInvalidoException {
        if (asistencia == null || asistencia.getFecha() == null) {
            throw new ValorInvalidoException("Falta incluir la fecha");
        }
        if (maestrodao.asistenciaTomadaDia(asistencia.getIdGrupoEstudiantes(), asistencia.getIdCurso(), asistencia.getFecha())) {
            return maestrodao.getAsistenciasDelDia(asistencia.getIdGrupoEstudiantes(), asistencia.getIdCurso(), asistencia.getFecha());
        }
        return maestrodao.generarAsistencias(asistencia);
    }
    
    public void tomarAsistencia(int id) throws AccesoALaDataException {
        maestrodao.tomarAsistencia(id);
    }
    
    public List<NotaDB> getNotasCurso(int idGrupo, int idCurso) throws AccesoALaDataException {
        return maestrodao.getNotasCurso(idGrupo, idCurso);
    }
    
    public NotaDB getNotaCurso(int id) throws AccesoALaDataException, DataInexistenteException {
        NotaDB nota = maestrodao.getNotaPorId(id);
        if (nota == null) {
            throw new DataInexistenteException("No se pudo encontrar la nota");
        }
        return nota;
    }
    
    public NotaDB modificarZona(NotaRequest nota, int id) throws AccesoALaDataException, ValorInvalidoException {
        if (nota.getZona() < Nota.MINIMO.getValor()) {
            throw new ValorInvalidoException("La nota minima permitida es: " + Nota.MINIMO.getValor());
        }
        if (nota.getZona() > Nota.MAXIMO_ZONA.getValor()) {
            throw new ValorInvalidoException("La nota maxima para la zona es: " + Nota.MAXIMO_ZONA.getValor());
        }
        return maestrodao.modificarZona(nota.getZona(), id);
    }
    
    public NotaDB modificarExamen(NotaRequest nota, int id) throws AccesoALaDataException, ValorInvalidoException {
        if (nota.getExamenFinal() < Nota.MINIMO.getValor()) {
            throw new ValorInvalidoException("La nota minima permitida es: " + Nota.MINIMO.getValor());
        }
        if (nota.getExamenFinal() > Nota.MAXIMO_FINAL.getValor()) {
            throw new ValorInvalidoException("La nota maxima para el examen final es: " + Nota.MAXIMO_FINAL.getValor());
        }
        return maestrodao.modificarExamen(nota.getExamenFinal(), id);
    }
    
}
