package businessLogic;

import exception.BusinessLogicException;
import exception.LoginExistException;
import java.util.Collection;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import javax.xml.bind.DatatypeConverter;

import rest.UserRESTful;
import security.RSACipherClient;
import transferObjects.Employee;

import transferObjects.User;

/**
 *
 * @author jonma
 */
public class UserManagerImplementation implements UserManager {

    private UserRESTful webClient;
    private static final Logger LOGGER = Logger.getLogger("javaGaming");

    /**
     *
     * @param login
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public Collection<User> checkLogin(String login, String password) throws Exception {
        byte[] passBytes = password.getBytes();
        List<User> users;
        String cyphered = RSACipherClient.encrypt(passBytes);
        System.out.println(cyphered);
        try {
            LOGGER.log(Level.INFO, "EmployeeManager: buscando usuario con el login: {0}.", login);
            users = webClient.checkLogin(new GenericType<List<User>>() {
            }, login, cyphered);
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserManager: Exception finding employee by fullName, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error Signing In:\n" + ex.getMessage());
        }

        return users;
    }

    @Override
    public Collection<User> checkLoginExists(String login) throws Exception {
        List<User> users;

        try {
            LOGGER.log(Level.INFO, "EmployeeManager: buscando usuario con el login: {0}.", login);

            users = webClient.findUserByLogin(new GenericType<List<User>>() {
            }, login);

            if (users != null) {
                System.out.println("Encontrado");
                throw new LoginExistException();
            }
        } catch (ClientErrorException ex) {
            LOGGER.log(Level.SEVERE,
                    "UserManager: Exception finding employee by fullName, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error Signing In:\n" + ex.getMessage());
        }

        return users;
    }

    public UserManagerImplementation() {
        webClient = new UserRESTful();
    }
}
