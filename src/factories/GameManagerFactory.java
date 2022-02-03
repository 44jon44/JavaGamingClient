/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import businessLogic.GameManager;
import businessLogic.GameManagerImplementation;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author jonma
 */
public class GameManagerFactory {
      public static final String REST_WEB_CLIENT_TYPE = "REST_WEB_CLIENT";

    public static GameManager createGameManager(String type) throws OperationNotSupportedException {
        //The object to be returned
        GameManager gameManager = null;
        //Evaluate type parameter
        switch (type) {
            //If rest web client type is asked for, use DwellingManagerImplementation.
            case REST_WEB_CLIENT_TYPE:
                //If rest web client type is asked for, use DwellingManagerImplementation
                gameManager = new GameManagerImplementation();
                break;
            default:
                throw new OperationNotSupportedException("Users manager type not supported.");
        }

        return gameManager;
    }
}
