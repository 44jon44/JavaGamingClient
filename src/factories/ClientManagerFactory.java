/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import businessLogic.ClientManager;
import businessLogic.ClientManagerImplementation;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author Alex Hurtado
 */
public class ClientManagerFactory {
    public static final String REST_WEB_CLIENT_TYPE = "REST_WEB_CLIENT";

    public static ClientManager createClientManager(String type) throws OperationNotSupportedException {
        //The object to be returned
        ClientManager clientManager = null;
        //Evaluate type parameter
        switch (type) {
            //If rest web client type is asked for, use DwellingManagerImplementation.
            case REST_WEB_CLIENT_TYPE:
                //If rest web client type is asked for, use DwellingManagerImplementation
                clientManager = new ClientManagerImplementation();
                break;
            default:
                throw new OperationNotSupportedException("Users manager type not supported.");
        }

        return clientManager;
    }
}
