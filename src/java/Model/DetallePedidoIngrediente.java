/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author kinve
 */
public class DetallePedidoIngrediente {

    private int idDetIngr;
    private int cantidad;

    private Ingrediente ingrediente;
    private DetallePedido detallePedido;

    public DetallePedidoIngrediente() {
    }

    public DetallePedidoIngrediente(int idDetIngr, int cantidad, Ingrediente ingrediente, DetallePedido detallePedido) {
        this.idDetIngr = idDetIngr;
        this.cantidad = cantidad;
        this.ingrediente = ingrediente;
        this.detallePedido = detallePedido;
    }

    public int getIdDetIngr() {
        return idDetIngr;
    }

    public void setIdDetIngr(int idDetIngr) {
        this.idDetIngr = idDetIngr;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public DetallePedido getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(DetallePedido detallePedido) {
        this.detallePedido = detallePedido;
    }
    
    
}
