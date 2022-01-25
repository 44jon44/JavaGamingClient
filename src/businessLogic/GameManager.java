/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exception.GameExistExpception;
import java.util.Collection;
import transferObjects.Game;

/**
 *
 * @author ibai Arriola
 */
public interface GameManager {

    /**
     * Este método devuelve una colección de {@link GameBean}, que contiene
     * todos los juegos.
     *
     * @return Collection La colección con todos los datos {@link GameBean} de
     * los juegos.
     * @throws BusinessLogicException Si hay algún error durante el proceso.
     */
    public Collection<Game> getAllGames() throws Exception;
    
    /**
     * Este metodo comprueba si existe el nombre del juego y en el caso de existir 
     * lanza la exception
     * @param name el nombre del objeto Game
     * @throws exception.GameExistExpception si exise el juego
     */
    public void isNameExisting(String name) throws GameExistExpception;

    /**
     * Este metodo crea un juego nuevo.
     *
     * @param Game el objeto que va ser añadido.
     * @throws BusinessLogicException If there is any error while processing.
     */
    public void createGame(Game game) throws Exception;

    /**
     * Este método actualiza los datos de un UserBean existente para el usuario.
     *
     * @param user El objeto UserBean a actualizar.
     * @throws BusinessLogicException Si hay algún error durante el proceso.
     */
    public void updateGame(Game game) throws Exception;

    /**
     * Este método elimina los datos de un usuario existente.
     *
     * @param user El objeto UserBean que se va a eliminar.
     * @throws BusinessLogicException Si hay algún error durante el proceso.
     */
    public void deleteGame(Game game) throws Exception;
}
