/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;
/**
 *Excepcion que se señala que la contraseña no existe
 * @author Ibai,jon,markel,alex
 */
public class PasswordNotFoundException extends Exception {

 /**
     * Crea una instancia de <code>PasswordNotFoundException</code> sin mensaje
     * de detalle.
     */
    public PasswordNotFoundException() {
        super("La contraseña no es correcta");
    }
}