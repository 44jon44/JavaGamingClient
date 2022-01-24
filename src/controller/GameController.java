/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ibai Arriola
 */
public class GameController{

    private static final Logger LOG = Logger.getLogger(GameController.class.getName());
    
    @FXML
    private TableView<?> tvGames;
    @FXML
    private TableColumn<?, ?> tcGameName;
    @FXML
    private TableColumn<?, ?> tcGameGenre;
    @FXML
    private TableColumn<?, ?> tcGamePegi;
    @FXML
    private TableColumn<?, ?> tcGameReleaseDate;
    @FXML
    private TableColumn<?, ?> tcGamePrice;
    @FXML
    private ComboBox<?> cbSearchBy;
    @FXML
    private ComboBox<?> cbSearchValue;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnAddGame;
    @FXML
    private Button btnModifyGame;
    @FXML
    private Button btnDeleteGame;
    @FXML
    private Label lblGameError;
    @FXML
    private HBox hbMenuAdm;
    
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void initStage(Parent root) {
        Scene gameScene = new Scene(root);   
        stage.setScene(gameScene);
       // menuController.setStage(stage);
        btnAddGame.setOnAction(this::createGame);
        btnDeleteGame.setOnAction(this::deleteGame);
        btnModifyGame.setOnAction(this::modifyGame);
        //lblError se inicializa vacio
        lblGameError.setText("");
        //Se deshabilitan los botones btnDelete y bntModify
        btnDeleteGame.setDisable(true);
        btnModifyGame.setDisable(true);
    }
    
    private void createGame(ActionEvent event) {
       try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader gameForm = new FXMLLoader(getClass().getResource("/view/gameForm.fxml"));
            Parent root;
            root = (Parent) gameForm.load();
            GameFormController controller = gameForm.getController();
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     private void modifyGame(ActionEvent event) {
         LOG.info("No implementado");
    }
     
     private void deleteGame(ActionEvent event) {
         LOG.info("No implementado");
    }
}
