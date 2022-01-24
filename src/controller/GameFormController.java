/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
    private ComboBox cbGameGenre;
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
    Stage stage;

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
        
        tfGamePrice.textProperty().addListener(this::tfGamePriceTextChanged);
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
        

    }
    private void tfGamePriceTextChanged(ObservableValue observable, String oldValue, String newValue) {
          if (newValue != null) {
        //  if (tfGamePrice.get>=200) {
        } else {
              }

    }
    private void addGame(ActionEvent event){
        
    }
    private void modifyGame(ActionEvent event){
        
    }
}
