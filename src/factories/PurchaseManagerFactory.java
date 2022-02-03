/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import businessLogic.PurchaseManager;
import businessLogic.PurchaseManagerImplementation;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author Alex Hurtado
 */
public class PurchaseManagerFactory {
    public static final String REST_WEB_CLIENT_TYPE = "REST_WEB_CLIENT";

    public static PurchaseManager createPurchaseManager(String type) throws OperationNotSupportedException {
        //The object to be returned
        PurchaseManager purchaseManager = null;
        //Evaluate type parameter
        switch (type) {
            //If rest web client type is asked for, use DwellingManagerImplementation.
            case REST_WEB_CLIENT_TYPE:
                //If rest web client type is asked for, use DwellingManagerImplementation
                purchaseManager = new PurchaseManagerImplementation();
                break;
            default:
                throw new OperationNotSupportedException("Users manager type not supported.");
        }
        return purchaseManager;
    }
}
