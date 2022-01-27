/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package factories;

import businessLogic.EmployeeManager;
import businessLogic.EmployeeManagerImplementation;
import javax.naming.OperationNotSupportedException;

/**
 *
 * @author jonma
 */
public class EmployeeManagerFactory {
    public static final String REST_WEB_CLIENT_TYPE = "REST_WEB_CLIENT";

    public static EmployeeManager createEmployeeManager(String type) throws OperationNotSupportedException {
        //The object to be returned
        EmployeeManager employeeManager = null;
        //Evaluate type parameter
        switch (type) {
            //If rest web client type is asked for, use DwellingManagerImplementation.
            case REST_WEB_CLIENT_TYPE:
                //If rest web client type is asked for, use DwellingManagerImplementation
                employeeManager = new EmployeeManagerImplementation();
                break;
            default:
                throw new OperationNotSupportedException("Users manager type not supported.");
        }

        return employeeManager;
    }
}
