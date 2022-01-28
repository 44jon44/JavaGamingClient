package exception;



    
public class GameExistsException extends BusinessLogicException {

 public  GameExistsException() {
        super("El usuario ya existe");
    }
    
}
