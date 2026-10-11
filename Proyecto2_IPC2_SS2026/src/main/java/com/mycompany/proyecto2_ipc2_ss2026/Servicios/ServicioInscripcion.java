/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimiteTamaño;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Administrador.GrupoAcademicoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.CicloEscolar;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Secretaria.InscripcionDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.UsuarioDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataExistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.GrupoAcademicoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.InscripcionDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.InscripcionRequest;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosRequest.UsuarioRequest;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioInscripcion {
    
    private final InscripcionDAO inscripciondao;
    private final UsuarioDAO usuariodao;
    
    public ServicioInscripcion() {
        inscripciondao = new InscripcionDAO();
        usuariodao = new UsuarioDAO();
    }
    
    public InscripcionDB revisarInscripcion(String dpiEstudiante) throws AccesoALaDataException, DataInexistenteException {
        InscripcionDB inscripcion = inscripciondao.revisarInscripcion(dpiEstudiante);
        if (inscripcion == null) {
            throw new DataInexistenteException("El estudiante no se encuentra inscrito");
        }
        return inscripcion;
    }
    
    public List<GrupoAcademicoDB> getGruposDelAño(int pagina) throws AccesoALaDataException {
        GrupoAcademicoDAO grupodao = new GrupoAcademicoDAO();
        CicloEscolar ciclo = new CicloEscolar();
        int año = ciclo.getAñoActivo();
        return grupodao.getGruposPorAño(año, pagina);
    }
    
    public String incribirPrimerIngreso(InscripcionRequest inscripcion) throws AccesoALaDataException, ValorInvalidoException, DataExistenteException, DataInexistenteException {
        UsuarioRequest usuario = inscripcion.getEstudiante();
        if (inscripcion == null || usuario == null) {
            throw new ValorInvalidoException("Hacen falta valores para la inscripcion");
        }
        if (usuario.getDpi() == null || usuario.getNombre() == null || usuario.getRol() == null || 
                usuario.getNumeroEstudiante() == null || usuario.getNumeroEncargado() == null || 
                usuario.getDpi().isEmpty() || usuario.getNombre().isEmpty() ||
                usuario.getNumeroEstudiante().isEmpty() || usuario.getNumeroEncargado().isEmpty()) {
            throw new ValorInvalidoException("Hacen falta valores del usuario para la inscripcion");
        }
        
        revisarTamaños(usuario);
        if (usuariodao.existeUsuarioPorDpi(usuario.getDpi())) {
            throw new DataExistenteException("Ya existe un usuario registrado con el DPI " + usuario.getDpi());
        }
        if (usuariodao.existeUsuarioPorNombre(usuario.getNombre())) {
            throw new DataExistenteException("Ya existe un usuario registrado con el nombre " + usuario.getNombre());
        }
        if (!inscripciondao.existeGrupoCicloEscolar(inscripcion.getGrupoId())) {
            throw new DataInexistenteException("No se pudo encontrar el grupo Academico");
        }
        if (inscripciondao.estaInscrito(usuario.getDpi(), inscripcion.getGrupoId())) {
            throw new DataExistenteException("El estudiante ya se encuentra inscrito");
        }
        inscripciondao.inscribirEstudiantePrimerIngreso(inscripcion);
        return "Se ha inscrito al estudiante " + usuario.getNombre() + " correctamente";
    }
    
    public String inscripcionReingreso(InscripcionRequest inscripcion) throws AccesoALaDataException, ValorInvalidoException, DataInexistenteException, DataExistenteException {
        if (inscripcion == null || inscripcion.getDpiEstudiante() == null || inscripcion.getDpiEstudiante().isEmpty()) {
            throw new ValorInvalidoException("Hacen falta valores para la inscripcion");
        }
        UsuarioRequest usuario = inscripcion.getEstudiante();
        revisarTamaños(usuario);
        if (!usuariodao.existeUsuarioPorDpi(usuario.getDpi())) {
            throw new DataInexistenteException("No se pudo encontrar el usuario para su reinscripcion");
        }
        if (!inscripciondao.existeGrupoCicloEscolar(inscripcion.getGrupoId())) {
            throw new DataInexistenteException("No se pudo encontrar el grupo Academico");
        }
        if (inscripciondao.estaInscrito(inscripcion.getDpiEstudiante(), inscripcion.getGrupoId())) {
            throw new DataExistenteException("El estudiante ya se encuentra inscrito");
        }
        inscripciondao.inscribirEstudianteReingreso(inscripcion);
        return "Se ha reinscrito al estudiante de forma exitosa";
    }
    
    private void revisarTamaños(UsuarioRequest usuario) throws AccesoALaDataException, ValorInvalidoException {
        if (usuario.getDpi().length() > LimiteTamaño.DPI.getLimite()) {
            throw new ValorInvalidoException("El dpi/cui no puede tener mas de " + LimiteTamaño.DPI.getLimite() + " caracteres");
        }
        if (usuario.getNombre().length() > LimiteTamaño.NOMBRE.getLimite()) {
            throw new ValorInvalidoException("El nombre no puede tener mas de " + LimiteTamaño.NOMBRE.getLimite() + " caracteres");
        }
    }
    
}
