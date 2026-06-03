/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Model.Pedido;
import java.util.List;

/**
 *
 * @author kinve
 */
public interface IPedido {

    public int generarPedido(Pedido pedido);

    public List<Pedido> lista();

    public Pedido SearchById(int id);

    public boolean updateEstado(Pedido p);

}
