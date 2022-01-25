package transferObjects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import javafx.scene.control.DatePicker;

/**
 * Java bean de la entidad Game.
 *
 * @author ibai Arriola
 */
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id del juego Y primary key
     */
    private Integer idGame;
    /**
     * Nombre del juego
     */
    private String name;
    /**
     * Genero del juego
     */
    private String genre;
    /**
     * Edad recomendado para jugar
     */
    private Integer pegi;
    /**
     * Fecha de salida
     */
    private Date relaseData;
    /**
     * Precio del juego
     */
    private Float price;

    private Set<Employee> employees;
    //lista de juegos comprados
    private Set<Purchase> purchases;

    //Getter y Setter  de la clase Game
    public Integer getIdGame() {
        return idGame;
    }

    public void setIdGame(Integer idGame) {
        this.idGame = idGame;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getPegi() {
        return pegi;
    }

    public void setPegi(Integer pegi) {
        this.pegi = pegi;
    }

    public Date getRelaseData() {
        return relaseData;
    }

    public void setRelaseData(Date relaseData) {
        this.relaseData = relaseData;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGame != null ? idGame.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Game)) {
            return false;
        }
        Game other = (Game) object;
        if ((this.idGame == null && other.idGame != null) || (this.idGame != null && !this.idGame.equals(other.idGame))) {
            return false;
        }
        return true;
    }

    //mostrar los diferentes juegos 
    @Override
    public String toString() {
        return "Game{" + "idGame=" + idGame + ", name=" + name + ", genre=" + genre + ", pegi=" + pegi + ", relaseData=" + relaseData + ", price=" + price + '}';
    }
}
