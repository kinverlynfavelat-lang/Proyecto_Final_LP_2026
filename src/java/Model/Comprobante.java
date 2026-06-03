/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author kinve
 */
public class Comprobante {
    
    private int id_comprobante;
    private String archivo;
    private EstadoComprobante estado;
    private Pedido pedido;

    public Comprobante() {
    }

    public Comprobante(int id_comprobante, String archivo, EstadoComprobante estado, Pedido pedido) {
        this.id_comprobante = id_comprobante;
        this.archivo = archivo;
        this.estado = estado;
        this.pedido = pedido;
    }

    public int getId_comprobante() {
        return id_comprobante;
    }

    public void setId_comprobante(int id_comprobante) {
        this.id_comprobante = id_comprobante;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    public EstadoComprobante getEstado() {
        return estado;
    }

    public void setEstado(EstadoComprobante estado) {
        this.estado = estado;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    
    
    
}
