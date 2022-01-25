/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package transferObjects;

import java.io.Serializable;
import java.security.Timestamp;
import javax.xml.bind.annotation.XmlRootElement;
import model.UserPrivilege;
import model.UserStatus;

/**
 * Java Bean User
 *
 * @author ibai , jon , alex , markel
 */
@XmlRootElement(name="user")
public class User implements Serializable {

    private int id;
    private String login;
    private String email;
    private String name;
    private UserStatus status;
    private UserPrivilege privilege;
    private String password;
    private Timestamp lastPasswordChange;

    /**
     * Constructor vacio de la clase User
     */
    public User() {
    }
    
    /**
     * Constructor de la clase User que recibe 2 parámetros
     * @param login login del usuario
     * @param password  contraseña del usuario
     */
    public User(String login, String password){
        this.login = login;
        this.password = password;
    }
    
    /**
     * Constructor de la clase User que recibe 4 parámetros
     * @param login login del usuario
     * @param email email del usuario
     * @param name  nombre del usuario
     * @param password  contraseña del usuario
     */
    public User(String login,String email, String name, String password){
        this.login = login;
        this.email = email;
        this.name = name;
        this.password = password;
    }
    
    //getters y setters de la clase User
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public UserPrivilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(UserPrivilege privilege) {
        this.privilege = privilege;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getLastPasswordChange() {
        return lastPasswordChange;
    }

    public void setLastPasswordChange(Timestamp lastPasswordChange) {
        this.lastPasswordChange = lastPasswordChange;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", login=" + login + ", email=" + email + ", name=" + name + ", status=" + status + ", privilege=" + privilege + ", password=" + password + ", lastPasswordChange=" + lastPasswordChange + '}';
    }
}