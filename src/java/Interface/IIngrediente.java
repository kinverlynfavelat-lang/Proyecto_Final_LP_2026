/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Model.Ingrediente;
import java.util.List;

/**
 *
 * @author kinve
 */
public interface IIngrediente {
    
    List<Ingrediente> listar();

    boolean insertar(Ingrediente ingrediente);

    Ingrediente buscarPorId(int idIngrediente);

}
