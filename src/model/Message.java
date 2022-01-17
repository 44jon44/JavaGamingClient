/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import transferObjects.User;
import java.io.Serializable;


/**
 * esta clase  sirve para enviar informacion entre el cliente-servidor.
 * @author ibai , jon , alex , markel
 */
public class Message implements Serializable {

    /**
     * @param User = Un objeto Usuario
     */
    private User user;
    /**
     * @param Order = Un objeto orden
     */
    private Order order;

    /**
     *  Constructor definido de la clase message con 2 paremetros
     * @param user
     * @param order
     */
    public Message(User user, Order order) {
        this.user = user;
        this.order = order;
    }
    /**
     * Constructor sin definir de la clase message.
     */
    public Message() {};
   /**
    * @return devuelve el objeto usuario
    */
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

   /**
    * @return devuelve el objeto Order
    */
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    
}

