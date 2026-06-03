/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Model.Comprobante;

/**
 *
 * @author kinve
 */
public interface IComprobante {
    
    public boolean insert(Comprobante c);

    public Comprobante SearchById(int id);

    public boolean updateEstado(Comprobante c);

}
