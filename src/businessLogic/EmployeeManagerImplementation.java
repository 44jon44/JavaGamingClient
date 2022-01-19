/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.GenericType;
import rest.EmployeeRESTful;
import transferObjects.Employee;

public class EmployeeManagerImplementation implements EmployeeManager {

    private EmployeeRESTful webClient;
    private static final Logger LOGGER=Logger.getLogger("javaGaming");
    public EmployeeManagerImplementation() {
        webClient = new EmployeeRESTful();
    }

    @Override
    public Collection<Employee> getAllEmployees() throws Exception {
        List<Employee> employees =null;
        try{
            LOGGER.info("UsersManager: Buscando los empleados .");
            //Ask webClient for all users' data.
            employees = webClient.findAll(new GenericType<List<Employee>>() {});
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Excepcon buscando todos los empleados, {0}",
                    ex.getMessage());
           // throw new BusinessLogicException("Error finding all users:\n"+ex.getMessage());
        }
        return employees;   
    }

    @Override
    public void createEmployee(Employee emp) throws Exception {
        
        try{
            LOGGER.log(Level.INFO,"EmployeeManager: Creating employee {0}.",emp.getLogin());
            //Send user data to web client for creation. 
            webClient.create(emp);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception creating user, {0}",
                    ex.getMessage());
            //throw new BusinessLogicException("Error creating user:\n"+ex.getMessage());
        }
    }
    

    @Override
    public void updateEmployee(Employee emp) throws Exception {
        try{
            LOGGER.log(Level.INFO,"EmployeeManager: Updating employee {0}.",emp.getLogin());
            webClient.update(emp);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception updating user, {0}",
                    ex.getMessage());
            //throw new BusinessLogicException("Error updating user:\n"+ex.getMessage());
        }
    }
    

    @Override
    public void deleteEmployee(Employee emp) throws Exception {
                try{
            LOGGER.log(Level.INFO,"UsersManager: Deleting user {0}.",emp.getLogin());
            webClient.remove(emp.getLogin());
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception deleting user, {0}",
                    ex.getMessage());
            //throw new BusinessLogicException("Error deleting user:\n"+ex.getMessage());
        }
    }

    @Override
    public void isLoginExisting(String login) throws Exception {
        /*try{
            if(this.webClient.find(Employee.class, login)!=null)
                //throw new LoginExistsException("Ya existe un usuario con ese login");
       /* }catch(NotFoundException ex){
            //If there is a NotFoundException 404,that is,
            //the login does not exist, we catch the exception and do nothing. 
        }catch(ClientErrorException ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception checking login exixtence, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding user:\n"+ex.getMessage());
        }catch(Exception ex){}*/
    }

    @Override
    public void employeesByName(String name) throws Exception {
        
        try{
            LOGGER.log(Level.INFO,"EmployeeManager: buscando empleados con el nombre: {0}.",name);
            webClient.employeesByName(Employee.class, name);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception deleting user, {0}",
                    ex.getMessage());
            //throw new BusinessLogicException("Error deleting user:\n"+ex.getMessage());
        }
    }
        
    

    @Override
    public void employeesBySalary(Float salary) throws Exception {

        try{
            LOGGER.log(Level.INFO,"EmployeeManager: buscando empleados con el salario: {0}.",salary);
            webClient.employeeBySalary(Employee.class, salary);
        }catch(Exception ex){
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception deleting user, {0}",
                    ex.getMessage());
            //throw new BusinessLogicException("Error deleting user:\n"+ex.getMessage());
        }
    }

}
