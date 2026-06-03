/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author kinve
 */
public class Pedido {

    private int id_pedido;
    private String codigo;
    private Persona persona;
    private double total;
    private EstadoPedido estadoPedido;
    private Timestamp fecha;
    private MetodoPago metodoPago;
    private List<DetallePedido> detallePedido;

    public Pedido() {
    }

    public Pedido(int id_pedido, String codigo, Persona persona, Double total, EstadoPedido estadoPedido, Timestamp fecha, MetodoPago metodoPago, List<DetallePedido> detallePedido) {
        this.id_pedido = id_pedido;
        this.codigo = codigo;
        this.persona = persona;
        this.total = total;
        this.estadoPedido = estadoPedido;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.detallePedido = detallePedido;
    }

    public int getId_pedido() {
        return id_pedido;
    }

    public void setId_pedido(int id_pedido) {
        this.id_pedido = id_pedido;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public List<DetallePedido> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(List<DetallePedido> detallePedido) {
        this.detallePedido = detallePedido;
    }
    

    
    
}
