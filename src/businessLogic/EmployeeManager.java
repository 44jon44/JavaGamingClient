/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.Collection;
import transferObjects.Employee;

/**
 *  Interfaz de logica con los metos de gestion de empleados
 * @author Jon Mayo
 */
public interface EmployeeManager {

    

    /**
     * Este metodo crea un empleado nuevo.
     *
     * @param emp es el empleado añadir.
     * @throws java.lang.Exception
     *
     */
    public void createEmployee(Employee emp) throws Exception;

    /**
     * Este metodo actualiza los datos de un Employee existente.
     *
     * @param emp el Employee a actualizar.
     * @throws java.lang.Exception
     *
     */
    public void updateEmployee(Employee emp) throws Exception;

    /**
     * Este metodo borra los datos de un empleado existente
     *
     * @param emp el empleado a borrar.
     * @throws java.lang.Exception
     *
     */
    public void deleteEmployee(Employee emp) throws Exception;

    /**
     * Este metod comprueba que si el login de un empleado existe
     * @param login
     * @throws java.lang.Exception
     */
    public void isLoginExisting(String login) throws Exception;
    /**
     * Este metodo busca empleados por nombre
     * @param name
     * @return 
     * @throws Exception 
     */
    public Collection<Employee> employeesByName(String name) throws Exception;
    /**
     * Este metodo busca empleados por salario
     * @param salary
     * @return 
     * @throws Exception 
     */
    public Collection<Employee> employeesBySalary(Float salary) throws Exception;
    
    /**
     * Este recoje todos los empleados
     *
     * @return Collection con todos los empleados
     * @throws java.lang.Exception
     */
    public Collection<Employee> getAllEmployees() throws Exception;

}