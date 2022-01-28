package exception;


/**
 *Excepcion que se señala que el usuario ya existe en la base de datos
* @author Ibai,jon,markel,alex
 */
public class GameExistExpception extends Exception {

    /**
     * Crea una instancia de <code>LoginExistException</code> sin mensaje de
     * detalle.
     */
    public GameExistExpception() {
        super("ese juego ya existe");
    }
}
