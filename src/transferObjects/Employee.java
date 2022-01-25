/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferObjects;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entidad Empleado que extiende de la entidad User
 *
 * @author jon
 */
@XmlRootElement(name = "employee")
public class Employee extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Fecha en la que el empleado fue contratado
     */
    private Date hiringDate;

    /**
     * Salario que recibe el empleado
     */
    private Float salary;

    /**
     * Método que devuelve la fecha en la que fue contratado el empleado
     *
     * @return hiringDate
     */
    public Date getHiringDate() {
        return hiringDate;
    }

    /**
     * Metodo que establece la fecha de de cntratacion del empleado
     *
     * @param hiringDate
     */
    public void setHiringDate(Date hiringDate) {
        this.hiringDate = hiringDate;
    }

    /**
     * Método que devuelve el salario que recibe el empleado
     *
     * @return hiringDate
     */
    public Float getSalary() {
        return salary;
    }

    /**
     * Metodo que establece el salario que recibe el empleado
     *
     * @param salary
     */
    public void setSalary(Float salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" + super.toString() + "hiringDate=" + hiringDate + ", salary=" + salary + '}';
    }
}
