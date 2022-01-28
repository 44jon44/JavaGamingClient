package businessLogic;

import exception.GameExistExpception;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;
import rest.GameRESTful;
import transferObjects.Game;

/**
 *
 * @author ibai Arriola
 */
public class GameManagerImplementation implements GameManager {
//REST GAME web 

    private GameRESTful webClient;
    private static final Logger LOGGER = Logger.getLogger(GameManagerImplementation.class.getName());

    /**
     * Crea un objeto UsersManagerImplementation. Construye un cliente web para
     * acceder a un servicio RESTful que proporciona lógica de negocio en una
     * aplicación servidor.
     */
    public GameManagerImplementation() {
        webClient = new GameRESTful();
        
    }

    /**
     * Este método devuelve una Colección de {@link GameBean}, que contiene
     * todos los datos de los juegos.
     *
     * @return Collection La colección con todos los datos {@link GameBean} de
     * los juegos.
     * @throws BusinessLogicException Si hay algún error durante el proceso.
     */
    @Override
    public Collection<Game> getAllGames() throws Exception {
        Collection<Game> games = null;
        try {
            LOGGER.info("UsersManager: Finding all users from REST service (XML).");
            //Ask webClient for all users' data.
            games = webClient.findAll(new GenericType<Collection<Game>>() {
            });
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "GameManager: Exception finding all users, {0}",
                    ex.getMessage());
        }
        return games;
    }

    /**
     * Este método añade un nuevo Game creado. Esto se hace enviando una
     * petición POST a un servicio web RESTful.
     *
     * @param game El objeto Game a añadir.
     *
     */
    @Override
    public void createGame(Game game) throws Exception {
        try {
            LOGGER.log(Level.INFO, "UsersManager: Creating Game {0}.", game.getName());
            //Send user data to web client for creation. 
            webClient.create(game);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Excepcion al crear el usuario,{0}",
                    ex.getMessage());
        }

    }

    /**
     * Este método añade un Game recién creado. Esto se hace enviando una
     * solicitud POST a un servicio web RESTful.
     *
     * @param Game El objeto Game a añadir.
     *
     */
    @Override
    public void updateGame(Game game) throws Exception {
        try {
            LOGGER.log(Level.INFO, "UsersManager: Updating user {0}.", game.getIdGame());
            webClient.edit(game,game.getIdGame().toString());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception updating user, {0}",
                    ex.getMessage());
        }
    }

    /**
     * Este método elimina los datos de un juego existente. Esto se hace
     * enviando una solicitud DELETE a un servicio web RESTful.
     *
     * @param game El objeto UserGame que se va a eliminar.
     */
    @Override
    public void deleteGame(Integer idgame) throws Exception {
        try {
           
            webClient.remove(idgame);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception deleting user, {0}",
                    ex.getMessage());
        }
    }

    /**
     * Este método comprueba si el nombre de un juego ya existe, lanzando una
     * Excepción si es el caso.
     *
     * @throws GameExistExpception La excepción lanzada en caso de que el login
     * ya exista
     */
    @Override
    public void isNameExisting(String name) throws GameExistExpception {
        try {
            if (this.webClient.find(Game.class, name) != null) {
                throw new GameExistExpception();
            }
        } catch (NotFoundException ex) {
            //If there is a NotFoundException 404,that is,
            //the login does not exist, we catch the exception and do nothing. 
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception checking login exixtence, {0}",
                    ex.getMessage());
        }
    }

    /**
     * Este método devuelve una Colección de {@link Game}, que filtra la
     * busqueda de juegos por los diferentes generos.
     *
     * @return Collection, La colección con todos los datos {@link Game} de los
     * juegos filtrados por genero.
     */
    @Override
    public Collection<Game> getAllGamesbyGenre(String genre) throws Exception {
        List<Game> games = null;
        try {
            LOGGER.info("UsersManager: Finding all users from REST service (XML).");
            //Ask webClient for all users' data.
            games = webClient.findGamebyGenre(new GenericType<List<Game>>() {
            }, genre);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "GameManager: Exception finding all users, {0}",
                    ex.getMessage());
        }
        return games;
    }
/**
     * Este método devuelve una Colección de {@link Game}, que filtra la
     * busqueda de juegos por los diferentes pegis.
     *
     * @return Collection, La colección con todos los datos {@link Game} de los
     * juegos filtrados por genero.
     */
    @Override
    public Collection<Game> getAllGamesbyPegi(Integer pegi) throws Exception {
        List<Game> games = null;
        try {
            LOGGER.info("UsersManager: Finding all users from REST service (XML).");
            //Ask webClient for all users' data.
            games = webClient.findGamebyPegi(new GenericType<List<Game>>() {
            }, pegi);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "GameManager: Exception finding all users, {0}",
                    ex.getMessage());
        }
        return games;
    }
}
