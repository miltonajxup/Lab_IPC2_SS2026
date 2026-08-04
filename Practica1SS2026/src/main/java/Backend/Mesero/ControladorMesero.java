/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend.Mesero;

import DAOs.MesaDAO;
import DAOs.PedidoDAO;
import DAOs.PersonalDAO;
import Frontent.OpcionSMMesero;
import Frontent.PlantillaMesa;
import Frontent.ServicioMesero;
import Frontent.SubMenuMeseros;
import Modelos.Mesa;
import Modelos.Pedido;
import Modelos.Personal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author milton
 */
public class ControladorMesero {
    
    private final PersonalDAO personaldao;
    private List<Personal> meseros;
    private List<PlantillaMesa> plantillasMesa;
    private final MesaDAO mesadao;
    private List<Mesa> mesas;
    private final PedidoDAO pedidodao;
    private List<Pedido> pedidos;
    private final ServicioMesero servicioMesero;
    private final SubMenuMeseros subMenuMeseros;

    public ControladorMesero(ServicioMesero servicioMesero, SubMenuMeseros subMenuMeseros) {
        this.personaldao = new PersonalDAO();
        this.mesadao = new MesaDAO();
        this.mesas = mesadao.getMesas();
        this.pedidodao = new PedidoDAO();
        this.servicioMesero = servicioMesero;
        this.subMenuMeseros = subMenuMeseros;
    }
    
    public void traerCambiosMesa() {
        mesas = mesadao.getMesas();
        pedidos = pedidodao.geMesasOcupadas();
        colocarMesas(true);
    }
    
    public void colocarMesas(boolean mostrar) {
        if (mostrar) {
            String mesero;
            String estado;
            servicioMesero.setCuadricula(mesas.size() / 2 + 1);
            pedidos = pedidodao.geMesasOcupadas();
            plantillasMesa = new ArrayList<>();
            for (int i = 0; i < mesas.size(); i++) {
                Mesa actual = mesas.get(i);
                Pedido pedido = mesaOcupada(actual.getNumeroMesa());
                if (pedido != null) {
                    mesero = pedido.getNombreMesero();
                    estado = "OCUPADO";
                } else {
                    mesero = "Ninguno";
                    estado = "LIBRE";
                }
                PlantillaMesa plantillaMesa = new PlantillaMesa(actual, mesero, estado);
                plantillaMesa.habilitarBoton(false);
                plantillasMesa.add(plantillaMesa);
                servicioMesero.agregarMesa(plantillaMesa);
            }
        } else {
            subMenuMeseros.mostrar(mostrar);
        }
    }
    
    private Pedido mesaOcupada(int numeroMesa) {
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido actual = pedidos.get(i);
            if (actual.getNumeroMesa() == numeroMesa) {
                return actual;
            }
        }
        return null;
    }
    
    public void colocarMeseros(boolean mostrar) {
        if (mostrar) {
            subMenuMeseros.mostrar(mostrar);
            meseros = personaldao.getMeseros();
            subMenuMeseros.setCuadricula(meseros.size());
            for (int i = 0; i < meseros.size(); i++) {
                OpcionSMMesero opcion = new OpcionSMMesero(this, subMenuMeseros, meseros.get(i));
                subMenuMeseros.agregarMesero(opcion);
            }
        }
    }
    
    public void elegirMesero(Personal mesero) {
        subMenuMeseros.mostrar(false);
        Pedido pedido = meseroOcupado(mesero.getDpi());
        for (int i = 0; i < mesas.size(); i++) {
            PlantillaMesa plantilla = plantillasMesa.get(i);
            if (pedido == null) {
                plantilla.habilitarBoton(true);
            } else if (pedido.getNumeroMesa() == plantilla.getNumeroMesa()) {
                plantilla.habilitarBoton(true);
            } else {
                plantilla.habilitarBoton(false);
            }
        }
    }
    
    private Pedido meseroOcupado(String dpi) {
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido actual = pedidos.get(i);
            if (actual.getMesero().equals(dpi)) {
                return actual;
            }
        }
        return null;
    }
    
    public void ocultarMesas() {
        servicioMesero.mostrar(false);
    }
    
}
