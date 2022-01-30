/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exception.BusinessLogicException;
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
    public Collection<Game> getAllGames() throws Exception, BusinessLogicException;

    public Collection<Game> getAllGamesbyGenre(String genre) throws Exception, BusinessLogicException;

    public Collection<Game> getAllGamesbyPegi(Integer pegi) throws Exception, BusinessLogicException;

    /**
     * Este metodo comprueba si existe el nombre del juego y en el caso de
     * existir lanza la exception
     *
     * @param name el nombre del objeto Game
     * @return 
     * @throws exception.GameExistExpception si exise el juego
     * @throws exception.BusinessLogicException
     */
    public Collection<Game> isNameExisting(String name) throws GameExistExpception, BusinessLogicException;

    /**
     * Este metodo crea un juego nuevo.
     *
     * @param game
     * @throws BusinessLogicException If there is any error while processing.
     */
    public void createGame(Game game) throws Exception, BusinessLogicException;

    /**
     * Este método actualiza los datos de un UserBean existente para el usuario.
     *
     * @param game
     * @throws BusinessLogicException Si hay algún error durante el proceso.
     */
    public void updateGame(Game game) throws Exception, BusinessLogicException;

    /**
     * Este método elimina los datos de un usuario existente.
     *
     * @param idgame
     * @throws BusinessLogicException Si hay algún error durante el proceso.
     */
    public void deleteGame(Integer idgame) throws Exception, BusinessLogicException;

}
