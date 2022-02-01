
package transferObjects;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * entidad cliente  que es extiende de la entidad user
 * @author Alex Hurtado
 */
@XmlRootElement(name="client")
public class Client extends User implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Fecha en la que se ha dado de alta el cliente.
     */
    private Date signUpDate;

    /**
     * lista de compras reliazada por el cliente
     */
    private Set<Purchase> purchases;

    
    //getters y setters de la entidad cliente//
    public Date getSignUpDate() {
        return signUpDate;
    }

    public void setSignUpDate(Date signUpDate) {
        this.signUpDate = signUpDate;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }

/**
 * @return retorna los datos de un cliente
 */
    @Override
    public String toString() {
        return "Client{" + super.toString() + "signUpDate=" + signUpDate + '}';
    }
}
