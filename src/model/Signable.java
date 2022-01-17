/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import transferObjects.User;
import exception.ConnectionNotAvailableException;
import exception.LoginExistException;
import exception.LoginNotFoundException;
import exception.PasswordNotFoundException;


/**
 * Interfaz que contiene la firma de los metodos necesarios.
 *
 * @author ibai , jon , alex , markel
 */
public interface Signable {

    /**
     * Metodo para que un usuario inicie sesion.
     *
     * @param user un objeto User que consta de los campos "login" y "password" que se envía desde el cliente
     * @return Un objeto User que consta de los campos "id", "login", "fullname", "email" y "password"
     * @throws LoginNotFoundException
     * @throws PasswordNotFoundException
     * @throws ConnectionNotAvailableException
     * @throws Exception
     */
    public   User signIn(User user) throws LoginNotFoundException, PasswordNotFoundException, ConnectionNotAvailableException, Exception;

    /**
     * Metodo para que se de alta un Usuario
     *
     * @param user un usuario que se envía desde la aplicación cliente que consta de los campos "id", "login", "fullname", "email" y "password"
     * @return Un booleano que indica si se ha realizado bien el alta del usuario
     * @throws LoginExistException
     * @throws ConnectionNotAvailableException
     * @throws Exception
     */
    public boolean signUp(User user) throws LoginExistException, ConnectionNotAvailableException, Exception;
}