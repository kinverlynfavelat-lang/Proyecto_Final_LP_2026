/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Enums.EstadoPedido;
import Enums.MetodoPago;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author kinve
 */
public class Pedido {

    private int idPedido;
    private String codigo;
    private LocalDateTime fecha;
    private EstadoPedido estadoPedido;
    private MetodoPago metodoPago;

    private String nombreCliente;
    private String dni;
    private String telefono;
    private String direccionEntrega;

    private double total;

    private Usuario usuario;
    private List<Carrito> detallePedido;
    private List<DetallePedido> detalles;

    public Pedido() {
    }

    public Pedido(int idPedido, String codigo, LocalDateTime fecha, EstadoPedido estadoPedido, MetodoPago metodoPago, String nombreCliente, String dni, String telefono, String direccionEntrega, double total, Usuario usuario, List<Carrito> detallePedido, List<DetallePedido> detalles) {
        this.idPedido = idPedido;
        this.codigo = codigo;
        this.fecha = fecha;
        this.estadoPedido = estadoPedido;
        this.metodoPago = metodoPago;
        this.nombreCliente = nombreCliente;
        this.dni = dni;
        this.telefono = telefono;
        this.direccionEntrega = direccionEntrega;
        this.total = total;
        this.usuario = usuario;
        this.detallePedido = detallePedido;
        this.detalles = detalles;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public EstadoPedido getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(EstadoPedido estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Carrito> getDetallePedido() {
        return detallePedido;
    }

    public void setDetallePedido(List<Carrito> detallePedido) {
        this.detallePedido = detallePedido;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    

}
