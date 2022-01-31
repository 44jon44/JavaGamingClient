package factories;

import businessLogic.EmployeeManager;
import businessLogic.EmployeeManagerImplementation;
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
        //The object to be returned
        UserManager userManager = null;
        //Evaluate type parameter
        switch (type) {
            //If rest web client type is asked for, use DwellingManagerImplementation.
            case REST_WEB_CLIENT_TYPE:
                //If rest web client type is asked for, use DwellingManagerImplementation
                userManager = new UserManagerImplementation();
                break;
            default:
                throw new OperationNotSupportedException("Users manager type not supported.");
        }

        return userManager;
    }
}
