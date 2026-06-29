/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author kinve
 */
public class Ingrediente {
    
    private int idIngrediente;
    private String nombre;
    private double precioExtra;

    public Ingrediente() {
    }

    public Ingrediente(int idIngrediente, String nombre, double precioExtra) {
        this.idIngrediente = idIngrediente;
        this.nombre = nombre;
        this.precioExtra = precioExtra;
    }

    public int getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(int idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecioExtra() {
        return precioExtra;
    }

    public void setPrecioExtra(double precioExtra) {
        this.precioExtra = precioExtra;
    }
    
    

}
