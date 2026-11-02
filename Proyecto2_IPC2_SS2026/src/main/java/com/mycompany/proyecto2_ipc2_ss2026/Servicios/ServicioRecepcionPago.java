/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.proyecto2_ipc2_ss2026.Servicios;

import com.mycompany.proyecto2_ipc2_ss2026.Constantes.LimitePagina;
import com.mycompany.proyecto2_ipc2_ss2026.Constantes.RolUsuario;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.Secretaria.RecepcionPagoDAO;
import com.mycompany.proyecto2_ipc2_ss2026.DAOs.UsuarioDAO;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.AccesoALaDataException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataExistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.DataInexistenteException;
import com.mycompany.proyecto2_ipc2_ss2026.Exceptions.ValorInvalidoException;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.BoletaPagoDB;
import com.mycompany.proyecto2_ipc2_ss2026.ModelosDB.UsuarioDB;
import java.util.List;

/**
 *
 * @author milton
 */
public class ServicioRecepcionPago {
    
    private final RecepcionPagoDAO recepciondao;
    private final UsuarioDAO usuariodao;
    
    public ServicioRecepcionPago() {
        recepciondao = new RecepcionPagoDAO();
        usuariodao = new UsuarioDAO();
    }
    
    public List<BoletaPagoDB> buscarBoletas(int pagina, String idEstudiante) throws AccesoALaDataException, ValorInvalidoException {
        if (pagina < LimitePagina.PAGINA_MINIMA) {
            throw new ValorInvalidoException("El numero de pagina no puede ser menor a " + LimitePagina.PAGINA_MINIMA);
        }
        return recepciondao.getBoletasPendientesEstudiante(pagina, idEstudiante);
    }
    
    public BoletaPagoDB realizarPago(int idBoleta, String idSecretaria) throws AccesoALaDataException, ValorInvalidoException, DataInexistenteException, DataExistenteException {
        if (idSecretaria == null) {
            throw new ValorInvalidoException("No se ha incluido el ID de la secretaria");
        }
        UsuarioDB secretaria = usuariodao.usuarioPorId(idSecretaria);
        if (secretaria == null) {
            throw new DataInexistenteException("No se pudo encontrar al usuario que efectua el pago");
        }
        if (secretaria.getRol() != RolUsuario.SECRETARIA && secretaria.getRol() != RolUsuario.SUPER_ADMINISTRADOR) {
            throw new ValorInvalidoException("El usuario que esta recibiendo el pago no esta autorizado para recibir el pago");
        }
        if (recepciondao.estaPagada(idBoleta)) {
            throw new DataExistenteException("La boleta selecionada ya se encuentra pagada");
        }
        return recepciondao.realizarPago(idBoleta, idSecretaria);
    }
    
}
