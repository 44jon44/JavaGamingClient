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
 *
 * @author Alex Hurtado
 */
@XmlRootElement
public class Purchase implements Serializable {

    private static final long serialVersionUID = 1L;


    private IdPurchase idPurchase;
/**
 * Fecha en la que se produjo la compra
 */

    private Date purchaseDate;
/**
 * id del cliente que hizo la compra
 */
    private Client client;
/**
 * id del juego que se compro
 */
    
    private Game game;
   
   //Metodos getters y setters 
    public IdPurchase getIdPurchase() {
        return idPurchase;
    }

    public void setIdPurchase(IdPurchase idPurchase) {
        this.idPurchase = idPurchase;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPurchase != null ? idPurchase.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Purchase)) {
            return false;
        }
        Purchase other = (Purchase) object;
        if ((this.idPurchase == null && other.idPurchase != null) || (this.idPurchase != null && !this.idPurchase.equals(other.idPurchase))) {
            return false;
        }
        return true;
    }
/**
 * 
 * @return devuelve los datos 
 */
    @Override
    public String toString() {
        return "Purchase{" + "idPurchase=" + idPurchase + ", purchaseDate=" + purchaseDate + ", client=" + client + ", game=" + game + '}';
    }

}
