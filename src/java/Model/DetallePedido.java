/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.List;

/**
 *
 * @author kinve
 */
public class DetallePedido {

    private int idDetalleP;
    private int cantidad;
    private double precioUnitario;
    private double subTotal;

    private Pedido pedido;
    private Producto producto;

    public DetallePedido() {
    }

    public DetallePedido(int idDetalleP, int cantidad, double precioUnitario, double subTotal, Pedido pedido, Producto producto) {
        this.idDetalleP = idDetalleP;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subTotal = subTotal;
        this.pedido = pedido;
        this.producto = producto;
    }

    public int getIdDetalleP() {
        return idDetalleP;
    }

    public void setIdDetalleP(int idDetalleP) {
        this.idDetalleP = idDetalleP;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

   
    

}
