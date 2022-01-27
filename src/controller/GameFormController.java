/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.GameManager;
import businessLogic.GameManagerImplementation;
import exception.GameExistExpception;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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

    Game game;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the controller class.
     * @param root
     * @throws java.lang.Exception
     */
    public void initStage(Parent root) throws Exception {
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
            tfNameIsValid = validateTfName(tfGameName.getText());
            if (!tfNameIsValid) {
                tfGameName.setStyle("-fx-text-inner-color: red;");
                showlblErrorNameMessages("");
            }
        } else if (newValue) {//foco ganado
        }
    }

    private void tfGamePriceTextFocus(ObservableValue observable, String oldValue, String newValue) {
        if (newValue.length() != oldValue.length()) {
            lblErrorGamePrice.setText("");
            tfGamePrice.setStyle("-fx-text-inner-color: black;");
        }
    }

    private void tfGamePriceTextChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        LOG.info("Dentro de tfPriceFocusChanged");
        if (oldValue) {//foco perdido 
            tfPriceIsValid = validateTfPrice(Float.valueOf(tfGamePrice.getText()));
            if (!tfPriceIsValid) {
                tfGameName.setStyle("-fx-text-inner-color: red;");
                showlblErrorPegiMessages(0);
            }
        } else if (newValue) {//foco ganado
        }
    }

    @FXML
    private void addGame(ActionEvent event) {
        try {
            gameManager.isNameExisting(tfGameName.getText().trim());
            
            LOG.info("Estamos creando el juego");
            game = new Game();
            game.setName(tfGameName.getText().trim());
            game.setGenre(cbGameGenre.getSelectionModel().getSelectedItem().toString());
            game.setPegi((Integer) cbGamePegi.getSelectionModel().getSelectedItem());
            game.setRelaseData(Date.from(dpReleaseDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            game.setPrice(Float.valueOf(tfGamePrice.getText()));

            gameManager.createGame(game);
            
            LOG.info("juego creado existosamente");
            cleanTextFields();
            createGameAlert();

        } catch (GameExistExpception ex) {
            Logger.getLogger(GameFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GameFormController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void modifyGame(ActionEvent event) {
        try {
            gameModify.setName(tfGameName.getText().trim());
            gameModify.setGenre(cbGameGenre.getSelectionModel().getSelectedItem().toString());
            gameModify.setPegi((Integer) cbGamePegi.getSelectionModel().getSelectedItem());
            gameModify.setRelaseData(Date.from(dpReleaseDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
            gameModify.setPrice(Float.valueOf(tfGamePrice.getText()));
            gameManager.updateGame(gameModify);
            LOG.info("juego modificado existosamente");
            modifyAlert();
        } catch (GameExistExpception ex) {
            Logger.getLogger(GameFormController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GameFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void showlblErrorNameMessages(String name) {
        if (tfGameName.getText().isEmpty()) {
            lblErrorGameName.setText("Campo obligatorio");
        } else {
            lblErrorGamePrice.setText("Nombre de juego invalido");
        }
    }

    private void showlblErrorPegiMessages(float pegi) {
        if (tfGamePrice.getText().isEmpty()) {
            lblErrorGameName.setText("Campo obligatorio");
        } else {
            lblErrorGamePrice.setText("introduzca un número entre (20/500)");
        }
    }

    private boolean validateTfName(String text) {
        return Pattern.matches("\\b[a-zA-Z][a-zA-Z0-9]+\\b", text);
    }

    private boolean validateTfPrice(float price) {
        if (price > 500 && price < 20) {
            return false;
        } else {
            return true;
        }
    }

    private void cleanTextFields() {
        tfGameName.setText("");
        tfGamePrice.setText("");
    }

    void modifyGameData(Game g) {
        btnAdd.setDisable(true);
        btnModify.setDisable(false);
        tfGameName.setText(g.getName());
        cbGameGenre.getSelectionModel().select(Genre.valueOf(g.getGenre()));
        cbGamePegi.getSelectionModel().select(g.getPegi());
        dpReleaseDate.setValue(g.getRelaseData().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        tfGamePrice.setText(g.getPrice().toString());
        gameModify = g;
    }

    private void modifyAlert() {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modificar");
        alert.setContentText("Se ha modificado correctamente el Juego");
        Optional<ButtonType> result = alert.showAndWait();
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

    private void createGameAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("AGregar");
        alert.setContentText("Se ha agregado correctamente el Juego");
        Optional<ButtonType> result = alert.showAndWait();
    }
}
