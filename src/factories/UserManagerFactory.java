package factories;

import businessLogic.UserManager;
import businessLogic.UserManagerImplementation;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author jonma
 */
public class UserManagerFactory {
    public static final String REST_WEB_CLIENT_TYPE = "REST_WEB_CLIENT";

    public static UserManager createUserManager(String type) throws OperationNotSupportedException {
        //El objeto a retornar
        UserManager userManager = null;
        //Evalua el parametro recibido 
        switch (type) {
            
            case REST_WEB_CLIENT_TYPE:
                //Si es un cliente Rest, llama a UserManager
                userManager = new UserManagerImplementation();
                break;
            default:
                throw new OperationNotSupportedException("Users manager type not supported.");
        }

        return userManager;
    }
}
