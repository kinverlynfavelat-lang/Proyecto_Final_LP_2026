/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Interface;

import Model.Usuario;

/**
 *
 * @author kinve
 */
public interface IUsuario {

    boolean registrar(Usuario usuario);

    Usuario iniciarSesion(String correo, String password);

}
