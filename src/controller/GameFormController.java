/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.GameManager;
import businessLogic.GameManagerImplementation;
import exception.GameExistExpception;
import factories.GameManagerFactory;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import transferObjects.Game;
import transferObjects.Genre;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class GameFormController {

    private static final Logger LOG = Logger.getLogger(GameFormController.class.getName());

    @FXML
    private ComboBox cbGamePegi;
    @FXML
    private DatePicker dpReleaseDate;
    @FXML
    private ComboBox<Genre> cbGameGenre;
    @FXML
    private TextField tfGameName;
    @FXML
    private TextField tfGamePrice;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnModify;
    @FXML
    private Label lblErrorGamePrice;
    @FXML
    private Hyperlink hlBack;
    @FXML
    private Label lblErrorGameName;
    @FXML

    Stage stage;
    private Game gameModify;

    private GameManager gameManager;
    private boolean tfNameIsValid = false;
    private boolean tfPriceIsValid = false;
    private boolean tfnameExist = false;

    Game game;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the controller class.
     *
     * @param root
     * @throws java.lang.Exception
     */
    public void initStage(Parent root) throws Exception {
        try {
            LOG.info("init stage del controlador de juegos");
            Scene GameScene = new Scene(root);
            stage.setScene(GameScene);
            gameManager = new GameManagerImplementation();
            //ComboBox
            btnModify.setDisable(true);
            defaultComboValue();
            //Textfield
            tfGameName.requestFocus();
            tfGameName.textProperty().addListener(this::tfGameNameTextChanged);
            tfGameName.focusedProperty().addListener(this::tfGameNameTextFocus);
            tfGamePrice.textProperty().addListener(this::tfGamePriceTextFocus);
            tfGamePrice.focusedProperty().addListener(this::tfGamePriceTextChanged);
            //Botones y hyperlinks
            hlBack.setOnAction(this::backtoGameTable);
            btnAdd.setOnAction(this::addGame);
            btnModify.setOnAction(this::modifyGame);
            //datePicker
            dpReleaseDate.setValue(LocalDate.now());
            dpReleaseDate.setEditable(false);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Fallo de servidor, intentelo mas tarde");
            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    /**
     * carga los combos por defecto
     */
    public void defaultComboValue() {
        //combo de genero 
        ObservableList<Genre> genrefilterValue = FXCollections.observableArrayList(Genre.values());
        cbGameGenre.setItems(genrefilterValue);
        cbGameGenre.getSelectionModel().selectFirst();
        cbGameGenre.setEditable(false);

        //combo de Pegi
        ObservableList<Integer> pegiValuefilter = FXCollections.observableArrayList(18, 16, 12, 8, 3);
        cbGamePegi.setItems(pegiValuefilter);
        cbGamePegi.getSelectionModel().selectFirst();
        cbGamePegi.setEditable(false);
    }

    /**
     * volvemos a la ventana anterior
     *
     * @param event
     */
    public void backtoGameTable(ActionEvent event) {
        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader game = new FXMLLoader(getClass().getResource("/view/game.fxml"));
            Parent root;
            root = (Parent) game.load();
            GameController controller = game.getController();
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * si se cambia la longitud del texto se quita el label de error y el color
     * se vuelve normal
     *
     * @param observable el observable
     * @param oldValue el valor anterior del filtro
     * @param newValue el valor acctualizado del filtro
     */
    private void tfGameNameTextChanged(ObservableValue observable, String oldValue, String newValue) {
        //Si cambia longitud
        if (newValue.length() != oldValue.length()) {
            lblErrorGameName.setText("");
            tfGameName.setStyle("-fx-text-inner-color: black;");
        }
    }

    /**
     * valida que este campo este escrito correctamente validando la longitud ,
     * o posibles errores a la hora de escribir
     *
     * @param observable el observable
     * @param oldValue el valor anterior del filtro
     * @param newValue el valor acctualizado del filtro
     */
    private void tfGameNameTextFocus(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        LOG.info("Dentro de tfNameFocusChanged");
        if (oldValue) {//foco perdido 
            if (tfGameName.getText().length() > 50 || tfGameName.getText().length() < 3) {
                lblErrorGameName.setText("debe tener (3-50) caracteres");
            } else {
                tfNameIsValid = validateTfName(tfGameName.getText());
                if (!tfNameIsValid) {
                    lblErrorGameName.setText("Nombre de juego invalido");
                }
            }

        } else if (newValue) {//foco ganado
        }
    }

    /**
     * si se cambia la longitud del texto se quita el label de error y el color
     * se vuelve normal
     *
     * @param observable el observable
     * @param oldValue el valor anterior del filtro
     * @param newValue el valor acctualizado del filtro
     */
    private void tfGamePriceTextFocus(ObservableValue observable, String oldValue, String newValue) {
        //Si cambia longitud
        if (newValue.length() != oldValue.length()) {
            lblErrorGamePrice.setText("");
            tfGamePrice.setStyle("-fx-text-inner-color: black;");
        }
    }

    /**
     * valida que este campo este escrito correctamente validando la longitud ,
     * o posibles errores a la hora de escribir
     *
     * @param observable el observable
     * @param oldValue el valor anterior del filtro
     * @param newValue el valor acctualizado del filtro
     */
    private void tfGamePriceTextChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        LOG.info("Dentro de tfPriceFocusChanged");
        if (oldValue) {//foco perdido 
            if (tfGamePrice.getText().length() > 50) {
                lblErrorGamePrice.setText("mas de 50 caracteres");
            } else {
                tfPriceIsValid = validateTfPrice(tfGamePrice.getText());
                if (!tfPriceIsValid) {
                    tfGamePrice.setStyle("-fx-text-inner-color: red;");
                    showlblErrorPegiMessages(0);
                }
            }
        } else if (newValue) {//foco ganado
        }
    }

    /**
     * valida que los campos sean correctos y que no exista una entrada en la
     * base de datos con un mismo nombre y Añade un juego a la base de datos
     *
     * @param event
     */
    @FXML
    private void addGame(ActionEvent event) {
        try {
            //Comprobar que los campos  sean validos antes de añadir.
            if (!lblErrorGameName.getText().isEmpty()
                    || !lblErrorGamePrice.getText().isEmpty()
                    || tfGameName.getText().equals("") || tfGamePrice.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                LOG.info("el juego ya existe");
                alert.setTitle("Error");
                alert.setContentText("No se han rellenado los campos correctamente");
                Optional<ButtonType> result = alert.showAndWait();
                //si se rellena correctamente el formulario
            } else {
                //comprobamos si el juego añadido existe o no
                List<Game> games;
                games = (List<Game>) GameManagerFactory.createGameManager("REST_WEB_CLIENT").isNameExisting(tfGameName.getText().trim());
                //si existe
                if (!games.isEmpty()) {
                    LOG.info("el juego ya existe");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setContentText("ese juego ya existe");
                    Optional<ButtonType> result = alert.showAndWait();
                    //si no existe y esta todo valido creamos el juego.
                } else {
                    game = new Game();
                    game.setName(tfGameName.getText().trim());
                    game.setGenre(cbGameGenre.getSelectionModel().getSelectedItem().toString());
                    game.setPegi((Integer) cbGamePegi.getSelectionModel().getSelectedItem());
                    game.setRelaseData(Date.from(dpReleaseDate.getValue()
                            .atStartOfDay().atZone(ZoneId.systemDefault())
                            .toInstant()));
                    game.setPrice(Float.valueOf(tfGamePrice.getText()));
                    GameManagerFactory.createGameManager("REST_WEB_CLIENT").createGame(game);
                    LOG.info("juego creado existosamente");
                    cleanTextFields();
                    createGameAlert();
                }
            }
        } catch (GameExistExpception ex) {
            Logger.getLogger(GameFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GameFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * valida que los campos sean correctos y que no exista una entrada en la
     * base de datos con un mismo nombre y Modifica un juego a la base de datos
     *
     * @param event
     */
    private void modifyGame(ActionEvent event) {
        try {
            //Comprobar que los campos  sean validos antes de añadir.
            if (!lblErrorGameName.getText().isEmpty()
                    || !lblErrorGamePrice.getText().isEmpty()
                    || tfGameName.getText().equals("") || tfGamePrice.getText().equals("")) {
                LOG.info("no ha rellenado los campos correctamente");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setContentText("No se han rellenado los campos correctamente");
                Optional<ButtonType> result = alert.showAndWait();
                //si se rellena correctamente el formulario
            } else {
                List<Game> games;
                games = (List<Game>) GameManagerFactory.createGameManager("REST_WEB_CLIENT").isNameExisting(tfGameName.getText().trim());
                /*
                comprobamos si el juego añadido existe o no y tenemos en cuenta 
                si no cambia el campo de Nombre
                 */
                if (!games.isEmpty() && !games.get(0).getName().equalsIgnoreCase(gameModify.getName())) {
                    LOG.info("el juego ya existe");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error");
                    alert.setContentText("ese juego ya existe");
                    Optional<ButtonType> result = alert.showAndWait();
                    //si no existe y esta todo valido modificamos el juego.
                } else {
                    //modificamos el juego
                    gameModify.setName(tfGameName.getText().trim());
                    gameModify.setGenre(cbGameGenre.getSelectionModel().getSelectedItem().toString());
                    gameModify.setPegi((Integer) cbGamePegi.getSelectionModel().getSelectedItem());
                    gameModify.setRelaseData(Date.from(dpReleaseDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                    gameModify.setPrice(Float.valueOf(tfGamePrice.getText()));
                    GameManagerFactory.createGameManager("REST_WEB_CLIENT")
                            .updateGame(gameModify);
                    LOG.info("juego modificado existosamente");
                    modifyAlert();
                }
            }
        } catch (GameExistExpception ex) {
            Logger.getLogger(GameFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GameFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * dependiendo de la longitud del campo lanza un error o otro
     *
     * @param pegi
     */
    private void showlblErrorPegiMessages(float pegi) {
        //si cambia la longitud del campo price
        if (tfGamePrice.getText().isEmpty()) {
            lblErrorGamePrice.setText("Campo obligatorio");
        } else {
            lblErrorGamePrice.setText("introduzca un número,puede ser decimal");
        }
    }

    /**
     * Validamos que el nombre este bien escrito
     *
     * @param text el nombre del juego
     * @return un boleano
     */
    private boolean validateTfName(String text) {
        //valida que sea alfanumerico  y tenga espacios
        return Pattern.matches("^[A-Za-z0-9? ,_-]+$", text);
    }

    /**
     * Validamos que el precio este bien escrito
     *
     * @param price
     * @return boolean
     */
    private boolean validateTfPrice(String price) {
        //valida el campo precios que no pueda meter letras.
        return Pattern.matches("^(?:([0-9]{1,3}))((?:[.])(?:[0-9]{1,2}))?", price);
    }

    /**
     * Limpiamos los campos del formulario
     */
    private void cleanTextFields() {
        //limpiamos el campo
        tfGameName.setText("");
        //limpiamosel campo price
        tfGamePrice.setText("");
    }

    /**
     * Cuando hemos pulsado modificar en la ventana anterior toma los datos del
     * del juego seleccionado y deshabilita el boton de añadir
     *
     * @param g el Objeto juego a modificar
     */
    void modifyGameData(Game g) {
        //boton add se deshabilita
        btnAdd.setDisable(true);
        //boton Modify se habilita
        btnModify.setDisable(false);
        //recuperamos los datos del juego en nuestro formulario
        tfGameName.setText(g.getName());
        cbGameGenre.getSelectionModel().select(Genre.valueOf(g.getGenre()));
        cbGamePegi.getSelectionModel().select(g.getPegi());
        dpReleaseDate.setValue(g.getRelaseData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        tfGamePrice.setText(g.getPrice().toString());
        gameModify = g;
    }

    /**
     * esta alerta informa al cliente que ha modificado un el juego y lo lleva a
     * la ventana game
     *
     */
    private void modifyAlert() {
        //informamos que el juego fue modificado antes de enviarle de nueva a la
        //ventana anterior.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modificar");
        alert.setContentText("Se ha modificado correctamente el Juego");
        Optional<ButtonType> result = alert.showAndWait();
        //devolvemos al usuario de nuevo a a la ventana game.fxml
        try {
            FXMLLoader gameForm = new FXMLLoader(getClass().getResource("/view/game.fxml"));
            Parent root;
            root = (Parent) gameForm.load();
            GameController controller = gameForm.getController();
            controller.setStage(stage);
            controller.initStage(root);
        } catch (Exception ex) {
            Logger.getLogger(GameFormController.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    /**
     * esta alerta informa al cliente que ha creado un juego
     */
    private void createGameAlert() {
        Alert alert2 = new Alert(Alert.AlertType.INFORMATION, "juego creado existosamente");
        alert2.show();
    }

}
