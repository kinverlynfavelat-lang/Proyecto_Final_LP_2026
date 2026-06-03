/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Model.DetallePedido;
import java.util.List;

/**
 *
 * @author kinve
 */
public interface IDetallePedido {

    public boolean insert(DetallePedido detalle);

    public List<DetallePedido> listaByPedido(int idPedido);
}
