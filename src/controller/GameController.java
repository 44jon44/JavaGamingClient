package controller;

import businessLogic.GameManager;
import businessLogic.GameManagerImplementation;
import controller.GameFormController;
import controller.HbMenuAdmController;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import transferObjects.Game;
import transferObjects.Genre;

/**
 * FXML Controller class
 *
 * @author Alex Hurtado
 */
public class GameController {

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

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) throws Exception {
        LOG.info("init stage del controlador de juegos");
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
        disableBtn();
        //COMBOBOX DE LOS FILTRADOS
        ObservableList<String> filter = FXCollections.observableArrayList("GENERO", "PEGI");
        cbSearchBy.setItems(filter);
        defaultComboValue();
        cbSearchBy.getSelectionModel().selectedItemProperty().addListener(this::selectedFilter);
        btnSearch.setOnAction(this::searchOnTable);
        //COLUMNAS DE LA TABLA GAME
        tcGameName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tcGameGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        tcGamePegi.setCellValueFactory(new PropertyValueFactory<>("pegi"));
        tcGamePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        tcGameReleaseDate.setCellValueFactory(new PropertyValueFactory<>("relaseData"));
        gameManager = new GameManagerImplementation();
        //loadGamesOnTable();
        stage.show();
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
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private void modifyGame(ActionEvent event) {
        LOG.info("No implementado");
    }

    private void deleteGame(ActionEvent event) {
        LOG.info("No implementado");
    }

    public void loadGamesOnTable() throws Exception {
        Collection games;
        games = gameManager.getAllGames();
        gameObservableList = FXCollections.observableArrayList(games);
        tvGames.setItems(gameObservableList);
        LOG.info("juegos cargados" + games);

    }

    @FXML
    public void selectedFilter(ObservableValue ov, String oldValue, String newValue) {
        if (newValue != null) {
            String searchFilter = cbSearchBy.getValue();

            if (searchFilter.equalsIgnoreCase("GENERO")) {
                ObservableList<Genre> genrefilterValue = FXCollections.observableArrayList(Genre.values());
                cbSearchValue.setItems(genrefilterValue);
                cbSearchValue.getSelectionModel().selectFirst();
            } else {
                ObservableList<Integer> pegiValuefilter = FXCollections.observableArrayList(3, 8, 12, 16, 18);
                cbSearchValue.setItems(pegiValuefilter);
                cbSearchValue.getSelectionModel().selectFirst();
            }
        }
    }

    public void defaultComboValue() {
        cbSearchBy.getSelectionModel().selectFirst();
        ObservableList<Genre> genrefilterValue = FXCollections.observableArrayList(Genre.values());
        cbSearchValue.setItems(genrefilterValue);
        cbSearchValue.getSelectionModel().selectFirst();
    }

    @FXML
    public void searchOnTable(ActionEvent event) {
        String searchFilter = (String) cbSearchBy.getValue();
        String genre;
        Integer pegi;
        Collection<Game> games = null;
        try {
            if (searchFilter.equalsIgnoreCase("GENERO")) {
                genre = cbSearchValue.getValue().toString();
                games = gameManager.getAllGamesbyGenre(genre);
                loadGamesOnTable();

            } else {
                pegi = (Integer) cbSearchValue.getValue();
                games = gameManager.getAllGamesbyPegi(pegi);
                loadGamesOnTable();
            }
        } catch (Exception ex) {
            LOG.info("error al  filtrar la busqueda de juegos al pulsar btbSearh");
            Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void disableBtn() {
        LOG.info("El boton esta desabilitado hasta selecionar los campos de la tabla");
        btnModifyGame.disableProperty().bind(tvGames.selectionModelProperty().isNotNull());
        btnDeleteGame.disableProperty().bind(tvGames.selectionModelProperty().isNotNull());
    }
}
