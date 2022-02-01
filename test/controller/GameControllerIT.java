/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.GameManager;
import clientapp.ClientApplication;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import javafx.stage.Stage;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isCancelButton;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import transferObjects.Game;

/**
 *
 * @author ibai Arriola
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameControllerIT extends ApplicationTest {

    private static final String OVERSIZED_TEXT = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

    private static final Logger LOG = Logger.getLogger(GameController.class.getName());

    private GameManager gameManager;
    private ObservableList<Game> gameObservableList;
    @FXML
    private TableView<Game> tvGames;
    @FXML
    private TableColumn tcGameName;
    @FXML
    private TableColumn tcGameGenre;
    @FXML
    private TableColumn tcGamePegi;
    @FXML
    private TableColumn tcGameReleaseDate;
    @FXML
    private TableColumn tcGamePrice;
    @FXML
    private ComboBox<String> cbSearchBy;
    @FXML
    private ComboBox cbSearchValue;
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
    @FXML
    private Button btnSignIN;

    @FXML
    private TextField tfUser;
    // textField  donde añadimos  la contraseña
    @FXML
    private PasswordField tfPassword;
    @FXML
    private MenuItem miManageGames;
    // button que inicia la sesion

    @Override
    public void start(Stage stage) throws Exception {
        //start JavaFX application to be tested    
        new clientapp.ClientApplication().start(stage); 
    } 
     @Test
    public void testA_initialInteraction(){
        clickOn("#tfUser");
        write("1");
        clickOn("#tfPassword");
        write("1");
        clickOn("#btnSignIN");
        clickOn("Opciones");
        clickOn("Gestionar juegos");
    }

}
