/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.Collection;
import transferObjects.User;

/**
 *
 * @author ibai Arriola
 */
public interface UserManager {
    /**
     * Este metodo devuelve una lista de los usarios que tengan ese login y esa contraseña
     * @param login
     * @param password
     * @return User
     * @throws Exception 
     */
    public Collection<User> checkLogin(String login, String password) throws Exception;
}
