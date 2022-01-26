/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.GameManager;
import exception.GameExistExpception;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

    private GameManager gameManager;
    private boolean tfNameIsValid = false;
    private boolean tfPriceIsValid = false;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the controller class.
     */
    public void initStage(Parent root) throws Exception {
        LOG.info("init stage del controlador de juegos");
        Scene GameScene = new Scene(root);
        stage.setScene(GameScene);

        //ComboBox
        defaultComboValue();
        //Textfield
        tfGameName.requestFocus();
        tfGameName.textProperty().addListener(this::tfGameNameTextChanged);
        tfGameName.focusedProperty().addListener(this::tfGameNameTextFocus);
        tfGamePrice.textProperty().addListener(this::tfGamePriceTextChanged);
        tfGamePrice.focusedProperty().addListener(this::tfGamePriceTextFocus);
        //Botones y hyperlinks
        hlBack.setOnAction(this::backtoGameTable);
        btnAdd.setOnAction(this::addGame);
        btnModify.setOnAction(this::modifyGame);
        //datePicker
        dpReleaseDate.setValue(LocalDate.now());
        dpReleaseDate.setEditable(false);
    }

    public void defaultComboValue() {
        ObservableList<Genre> genrefilterValue = FXCollections.observableArrayList(Genre.values());
        cbGameGenre.setItems(genrefilterValue);
        cbGameGenre.getSelectionModel().selectFirst();
        cbGameGenre.setEditable(false);

        ObservableList<Integer> pegiValuefilter = FXCollections.observableArrayList(18, 16, 12, 8, 3);
        cbGamePegi.setItems(pegiValuefilter);
        cbGamePegi.getSelectionModel().selectFirst();
        cbGamePegi.setEditable(false);
    }

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

    private void tfGameNameTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (newValue.length() != oldValue.length()) {
            lblErrorGameName.setText("");
            tfGameName.setStyle("-fx-text-inner-color: black;");
        }
    }

    private void tfGameNameTextFocus(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        LOG.info("Dentro de tfNameFocusChanged");
        if (oldValue) {//foco perdido 
           // tfNameIsValid = validateTfUser(tfGameName.getText());
            if (!tfNameIsValid) {
                tfGameName.setStyle("-fx-text-inner-color: red;");
                showlblErrorNameMessages("");
            }
        } else if (newValue) {//foco ganado
        }
    }

    private void tfGamePriceTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (newValue.length() != oldValue.length()) {
            lblErrorGamePrice.setText("");
            tfGamePrice.setStyle("-fx-text-inner-color: black;");
        }
    }

    private void tfGamePriceTextFocus(ObservableValue observable, Boolean oldValue, Boolean newValue) {

    }

    @FXML
    private void addGame(ActionEvent event)  {
        try {
            gameManager.isNameExisting(tfGameName.getText().trim());
            Game game = new Game();
            game.setName(tfGameName.getText().trim());
            game.setGenre(cbGameGenre.getSelectionModel().getSelectedItem().toString());
            game.setPegi((Integer) cbGamePegi.getSelectionModel().getSelectedItem());
            game.setRelaseData(Date.from(dpReleaseDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            game.setPrice(Float.valueOf(tfGamePrice.getText()));
            
            gameManager.createGame(game);

        } catch (GameExistExpception ex) {
            Logger.getLogger(GameFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GameFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void modifyGame(ActionEvent event) {

    }
    
    
    private void showlblErrorNameMessages(String name) {
        if (name.trim().length() == 0)
        {
            lblErrorGameName.setText("Campo obligatorio");
        } else
        {
            lblErrorGamePrice.setText("Nombre de juego invalido");
        }
    }
}
