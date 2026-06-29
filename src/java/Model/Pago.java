/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Enums.EstadoPago;
import Enums.MetodoPago;
import java.time.LocalDateTime;

/**
 *
 * @author kinve
 */
public class Pago {

    private int idPago;
    private MetodoPago metodo;
    private String comprobante;
    private EstadoPago estadoPago;
    private LocalDateTime fechaPago;
    private Pedido pedido;

    public Pago() {
    }

    public Pago(int idPago, MetodoPago metodo, String comprobante, EstadoPago estadoPago, LocalDateTime fechaPago, Pedido pedido) {
        this.idPago = idPago;
        this.metodo = metodo;
        this.comprobante = comprobante;
        this.estadoPago = estadoPago;
        this.fechaPago = fechaPago;
        this.pedido = pedido;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public MetodoPago getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoPago metodo) {
        this.metodo = metodo;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public EstadoPago getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(EstadoPago estadoPago) {
        this.estadoPago = estadoPago;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    
}
