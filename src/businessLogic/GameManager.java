/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package businessLogic;

import java.util.Collection;
import transferObjects.Game;

/**
 *
 * @author ibai Arriola
 */
public interface GameManager {
    /**
     * Este método devuelve una colección de {@link UserBean}, que contiene todos los datos de los usuarios.
     * @return Collection La colección con todos los datos {@link UserBean} de los usuarios. 
     * @throws BusinessLogicException Si hay algún error durante el proceso.
     */
    public Collection<Game > getAllGames() throws Exception;
        
    /**
     * Este metodo crea un juego nuevo.
     * @param Game el objeto que va ser añadido.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public void createGame(Game game) throws Exception;
    /**
     * This method updates data for an existing UserBean data for user. 
     * @param user The UserBean object to be updated.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public void updateGame(Game game) throws Exception;
    /**
     * This method deletes data for an existing user. 
     * @param user The UserBean object to be deleted.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public void deleteGame(Game game) throws Exception;
    /**
     * This method checks if a user's login already exists, throwing an Exception 
     * if that's the case.
     * @param login The login value to be checked.
     * @throws LoginExistsException The Exception thrown in case login already exists
     */  
}
