/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Enums.EstadoPedido;
import Model.Pedido;
import java.util.List;

/**
 *
 * @author kinve
 */
public interface IPedido {

    int generarPedido(Pedido pedido);

    List<Pedido> listar();

    Pedido buscarPorId(int idPedido);

    boolean actualizarEstado(int idPedido, EstadoPedido estado);

    List<Pedido> historialCliente(int idUsuario);

}
