/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import exception.BusinessLogicException;
import exception.LoginExistException;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.GenericType;
import rest.EmployeeRESTful;
import transferObjects.Employee;

/**
 * Esta clase implementa la interfaz de la capa logica EmployeeManager
 *
 * @author 2dam
 */
public class EmployeeManagerImplementation implements EmployeeManager {

    private EmployeeRESTful webClient;
    private static final Logger LOGGER = Logger.getLogger("javaGaming");

    /**
     * Crea un objeto EmployeeMAnagerImplementation.Crea un cliente web para
     * acceder al servicio RESTful
     */
    public EmployeeManagerImplementation() {
        webClient = new EmployeeRESTful();
    }

    /**
     * Este metodo crea un nuevoEmployee. Se hace medianteuna peticion POST al
     * servicio RESTful
     *
     * @param emp el Employee a añadir
     * @throws BusinessLogicException si algun error
     */
    @Override
    public void createEmployee(Employee emp) throws Exception {

        try {
            LOGGER.log(Level.INFO, "EmployeeManager: Creating employee {0}.", emp.getLogin());
            //Send user data to web client for creation. 
            webClient.create(emp);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception creating user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error creating user:\n" + ex.getMessage());
        }
    }

    /**
     * Este metodo actualiza los datos de un empleado
     *
     * @param emp el EMployee a actualizar
     * @throws BusinessLogicException si algun error
     */
    @Override
    public void updateEmployee(Employee emp) throws Exception {
        try {
            LOGGER.log(Level.INFO, "EmployeeManager: Updating employee {0}.", emp.getLogin());
            
            webClient.edit(emp, String.valueOf(emp.getIdUser()));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception updating user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error updating user:\n" + ex.getMessage());
        }
    }

    /**
     * Este metodo elimina los datos de un Employee
     *
     * @param emp el Employee a eliminar
     * @throws Exception
     */
    @Override
    public void deleteEmployee(Employee emp) throws Exception {
        try {
            LOGGER.log(Level.INFO, "UsersManager: Deleting user {0}.", emp.getLogin());
            webClient.remove(String.valueOf(emp.getIdUser()));
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception deleting user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error deleting user:\n" + ex.getMessage());
        }
    }

    /**
     * Este metodo comprueba si un Empleado ya existe
     *
     * @param login Parametro del Employee
     * @throws LoginExistException se lanza en el caso de que el login ya exista
     */
    @Override
    public void isLoginExisting(String login) throws Exception {
        try {
            if (this.webClient.find(Employee.class, login) != null) {
                throw new LoginExistException();
            }
        } catch (NotFoundException ex) {
            //If there is a NotFoundException 404,that is,
            //the login does not exist, we catch the exception and do nothing. 
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception checking login exixtence, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding user:\n" + ex.getMessage());
        }
    }

    /**
     *
     * @param salary
     * @throws Exception
     */
    @Override
    public Collection<Employee> employeesBySalary(String salary) throws Exception {
        List<Employee> employees = null;
        try {
            LOGGER.log(Level.INFO, "EmployeeManager: buscando empleados con el salario: {0}.", salary);
            employees = webClient.employeeBySalary(new GenericType<List<Employee>>() {
            }, salary);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Exception deleting user, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding user by salary:\n" + ex.getMessage());
        }
        return employees;
    }

    @Override
    public Collection<Employee> employeesByName(String fullName) throws Exception {
        List<Employee> employees = null;
        try {
            LOGGER.log(Level.INFO, "EmployeeManager: buscando empleados con el nombre: {0}.", fullName);
            employees=webClient.employeesByName(new GenericType<List<Employee>>() {
            }, fullName);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "EMployeeManager: Exception finding employee by fullName, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error deleting user:\n" + ex.getMessage());
        }
        return employees;
    }

    /**
     * Este metodo devuelve una Collection de Employee, conteniendo todos los
     * datos
     *
     * @return Collection con con todos los empleados
     * @throws BusinessLogicException si algun error
     */
    @Override
    public Collection<Employee> getAllEmployees() throws Exception {
        List<Employee> employees = null;
        try {
            LOGGER.info("UsersManager: Buscando los empleados .");
            //Ask webClient for all users' data.
            employees = webClient.findAll(new GenericType<List<Employee>>() {
            });
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE,
                    "UsersManager: Excepcon buscando todos los empleados, {0}",
                    ex.getMessage());
            throw new BusinessLogicException("Error finding all users:\n" + ex.getMessage());
        }
        return employees;
    }

}
