package transferObjects;

import java.io.Serializable;
import java.util.Date;

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

    //mostrar los diferentes juegos 
    @Override
    public String toString() {
        return "Game{" + "idGame=" + idGame + ", name=" + name + ", genre=" + genre + ", pegi=" + pegi + ", relaseData=" + relaseData + ", price=" + price + '}';
    }

}
