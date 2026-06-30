/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Enums.EstadoProducto;
import Model.Producto;
import java.util.List;

/**
 *
 * @author kinve
 */
public interface IProducto {

    List<Producto> listar();

    boolean insertar(Producto producto);

    boolean actualizar(Producto producto);

    Producto buscarPorId(int idProducto);

    boolean cambiarEstado(int idProducto, EstadoProducto estado);
    
    List<Producto> listarActivos();
}
