/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private TextField tfGameGenre;
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
        //
    }

    public void defaultComboValue() {
        ObservableList<Genre> genrefilterValue = FXCollections.observableArrayList(Genre.values());
        cbGameGenre.setItems(genrefilterValue);
        cbGameGenre.getSelectionModel().selectFirst();
        
        ObservableList<Integer> pegiValuefilter = FXCollections.observableArrayList(18,16,12,8,3);
        cbGamePegi.setItems(pegiValuefilter);
        cbGamePegi.getSelectionModel().selectFirst();
    }
}
