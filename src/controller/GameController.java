/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import controller.EmployeeFormController;
import controller.GameFormController;
import controller.HbMenuAdmController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Alex Hurtado
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
        Scene GameScene = new Scene(root);   
        stage.setScene(GameScene);
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
            //Creamos una nueva escena para la ventana SignIn
            Scene GameScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage gameStage = (Stage)hbMenuAdm.getScene().getWindow();
            //definimos como modal la nueva ventana

            //añadimos la escena en el stage
            gameStage.setScene(GameScene);
            //por defecto no podra redimensionarse


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
