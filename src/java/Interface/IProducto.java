/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Model.EstadoProducto;
import Model.Producto;
import java.util.List;

/**
 *
 * @author kinve
 */
public interface IProducto {
    
     public List<Producto> lista();

    public boolean insert(Producto p);

    public boolean update(Producto p);

    public Producto SearchById(int id);

    public boolean delete(int id);
    
    public boolean updateEstado(int id, EstadoProducto estado);
}
