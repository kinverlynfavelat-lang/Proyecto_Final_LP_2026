/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Enums.EstadoPago;
import Model.Pago;

/**
 *
 * @author kinve
 */
public interface IPago {

    boolean registrarPago(Pago pago);

    Pago buscarPorPedido(int idPedido);

    boolean validarPago(int idPago, EstadoPago estado);

}
