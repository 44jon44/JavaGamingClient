package controller;

import businessLogic.GameManager;
import businessLogic.GameManagerImplementation;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import transferObjects.Game;
import transferObjects.Genre;

/**
 * FXML Controller class
 *
 * @author Ibai Arriola
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
    private TableColumn<Game, String> tcGameReleaseDate;
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
    private Button btnReport;
    @FXML
    private Label lblGameError;
    @FXML
    private HBox hbMenuAdm;

    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) {
        try {
            Scene gameScene = new Scene(root);
            stage.setScene(gameScene);
            btnAddGame.setOnAction(this::createGame);
            btnDeleteGame.setOnAction(this::deleteGame);
            btnModifyGame.setOnAction(this::modifyGame);
            btnReport.setOnAction(this::reportTable);
            //lblError se inicializa vacio
            lblGameError.setText("");
            //Se deshabilitan los botones btnDelete y bntModify
            btnDeleteGame.setDisable(true);
            btnModifyGame.setDisable(true);

            //COMBOBOX DE LOS FILTRADOS
            ObservableList<String> filter = FXCollections.
                    observableArrayList("GENERO", "PEGI");
            cbSearchBy.setItems(filter);
            defaultComboValue();
            cbSearchBy.getSelectionModel().selectedItemProperty().
                    addListener(this::selectedFilter);
            btnSearch.setOnAction(this::searchOnTable);
            //COLUMNAS DE LA TABLA GAME
            tcGameName.setCellValueFactory(new PropertyValueFactory<>("name"));
            tcGameGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
            tcGamePegi.setCellValueFactory(new PropertyValueFactory<>("pegi"));
            tcGamePrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            //formateamos las fechas para quitar la forma predeterminada
            tcGameReleaseDate.setCellValueFactory(cellData
                    -> new SimpleStringProperty(dateFormatter.format(cellData.getValue().getRelaseData())));
            gameManager = new GameManagerImplementation();
            loadGamesOnTable();
            //CENTRAMOS LOS DATOS DE LA TABLA
            tcGameName.setStyle("-fx-alignment: CENTER;");
            tcGameGenre.setStyle("-fx-alignment: CENTER;");
            tcGamePegi.setStyle("-fx-alignment: CENTER;");
            tcGamePrice.setStyle("-fx-alignment: CENTER;");
            tcGameReleaseDate.setStyle("-fx-alignment: CENTER;");
            /////////////////////////////////////////////////////
            tvGames.getSelectionModel().selectedItemProperty()
                    .addListener((ov, oldValue, newValue) -> {
                        if (newValue != null) {
                            btnDeleteGame.setDisable(false);
                            btnModifyGame.setDisable(false);
                        } else {
                            btnDeleteGame.setDisable(true);
                            btnModifyGame.setDisable(true);
                        }
                    });
        } catch (Exception ex) {
            LOG.log(Level.SEVERE,
                    "Error al iniciar la ventana Game",
                    ex.getMessage());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Fallo de servidor, intentelo mas tarde");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * En este metodo navegamos a la ventana GameForm.fxm
     *
     * @param event accionamos el evento del boton
     */
    private void createGame(ActionEvent event) {
        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader gameForm = new FXMLLoader(getClass()
                    .getResource("/view/gameForm.fxml"));
            Parent root;
            root = (Parent) gameForm.load();
            GameFormController controller = gameForm.getController();
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Fallo de servidor, intentelo mas tarde");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * vamos a la ventana de gameForm y pasamos los datos seleccionados
     *
     * @param event accionamos el evento del boton
     */
    private void modifyGame(ActionEvent event) {
        try {
            Game selectedGame = ((Game) tvGames.getSelectionModel()
                    .getSelectedItem());

            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader gameForm = new FXMLLoader(getClass()
                    .getResource("/view/gameForm.fxml"));
            Parent root;
            root = (Parent) gameForm.load();
            GameFormController controller = gameForm.getController();
            controller.setStage(stage);
            controller.initStage(root);
            controller.modifyGameData((Game) tvGames.getSelectionModel().getSelectedItem());

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * El boton para imprimir la tabla que tenemos mediante el jasper Report
     *
     * @param event accionamos el evento del boton
     */
    private void reportTable(ActionEvent event) {
        try {
            LOG.info("Beginning printing action...");
            JasperReport report
                    = JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/report/GameReport.jrxml"));

            JRBeanCollectionDataSource dataItems
                    = new JRBeanCollectionDataSource((Collection<Game>) this.tvGames.getItems());
            //Map of parameter to be passed to the report
            Map<String, Object> parameters = new HashMap<>();
            //Fill report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Create and show the report window. The second parameter false value makes 
            //report window not to close app.
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
            // jasperViewer.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        } catch (JRException ex) {
            //If there is an error show message and
            //log it.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Fallo al imprimir");
            Optional<ButtonType> result = alert.showAndWait();
            LOG.log(Level.SEVERE,
                    ": Error printing report: {0}",
                    ex.getMessage());
        }
    }

    /**
     * borramos el juego seleccionado de la tabla
     *
     * @param event accionamos el evento del boton
     */
    private void deleteGame(ActionEvent event) {
        try {
            LOG.info("Deleting user...");
            //Obtenemos los datos del juego seleccionado mediante el atributo selectedGame
            Game selectedGame = ((Game) tvGames.getSelectionModel()
                    .getSelectedItem());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            //Se añaden los textos a la ventana alert
            alert.setTitle("Borrar Juego");
            alert.setHeaderText("Confirmacion");
            alert.setContentText("¿Desea  borrar el juego?");
            //La ventana se hace no redimensionable
            alert.setResizable(false);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                //delete game from server side
                gameManager.deleteGame(selectedGame.getIdGame());
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION,
                        "juego borrado");
                alert2.show();
                tvGames.getItems().remove(selectedGame);
                tvGames.refresh();
            } else {
                Alert alert3 = new Alert(Alert.AlertType.INFORMATION,
                        "operación cancelada");
                alert3.show();
            }
        } catch (Exception e) {
            //If there is an error in the business logic tier show message and
            //log it.
            LOG.log(Level.SEVERE,
                    "UI GestionUsuariosController: Error deleting user: {0}",
                    e.getMessage());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Fallo de servidor, intentelo mas tarde");
            Optional<ButtonType> result = alert.showAndWait();
        }

    }

    /**
     *
     * En este metodo cargamos los datos de todos los juegos de la parte
     * servidor
     */
    public void loadGamesOnTable() throws Exception {
        Collection games;
        //Obtenemos la lista de juegos.
        games = gameManager.getAllGames();
        gameObservableList = FXCollections.observableArrayList(games);
        tvGames.setItems(gameObservableList);
        LOG.info("juegos cargados" + games);

    }

    /**
     * Este metodo sirve para seleccionar filtros de busqueda
     *
     * @param ov el observable
     * @param oldValue el valor anterior del filtro
     * @param newValue el valor acctualizado del filtro
     */
    @FXML
    public void selectedFilter(ObservableValue ov, String oldValue, String newValue) {
        if (newValue != null) {
            String searchFilter = cbSearchBy.getValue();
                //si filtramos por genero
            if (searchFilter.equalsIgnoreCase("GENERO")) {
                //cargamos el Enum de Genero
                ObservableList<Genre> genrefilterValue = FXCollections
                        .observableArrayList(Genre.values());
                cbSearchValue.setItems(genrefilterValue);
                cbSearchValue.getSelectionModel().selectFirst();
                //si filtramos por Pegi
            } else {
                ObservableList<Integer> pegiValuefilter = FXCollections
                        .observableArrayList(3, 8, 12, 16, 18);
                cbSearchValue.setItems(pegiValuefilter);
                cbSearchValue.getSelectionModel().selectFirst();
            }
        }
    }

    /**
     * Este metodo carga los valores predeterminados a las combo cuando
     * iniciamos la ventana game
     */
    public void defaultComboValue() {
        cbSearchBy.getSelectionModel().selectFirst();
        ObservableList<Genre> genrefilterValue = FXCollections
                .observableArrayList(Genre.values());
        cbSearchValue.setItems(genrefilterValue);
        //selecionamos el primero que en nuestro caso es Miedo.
        cbSearchValue.getSelectionModel().selectFirst();
    }

    /**
     * nos filtra la tabla con los valores de busqueda que tengamos en las combo
     *
     *@param event accionamos el evento del boton
     */
    @FXML
    public void searchOnTable(ActionEvent event) {
        String searchFilter = (String) cbSearchBy.getValue();
        String genre;
        Integer pegi;
        //lista de juegos donde cargaremos los juegos para la tabla
        Collection<Game> games = null;
        try {
            lblGameError.setText("");
            if (searchFilter.equalsIgnoreCase("GENERO")) {
                //obtenemos el genero selecionado en el cbSearchValue
                genre = cbSearchValue.getValue().toString();
                // cargamos nuestra lista con el filtro de busqueda
                games = gameManager.getAllGamesbyGenre(genre);
                if (games.isEmpty()) {
                    lblGameError.setText("No hay juegos disponibles del genero seleccionado");
                }
                //cargamos los 
                chargeFilters(games);
            } else {
                //obtenemos el pegi selecionado en el cbSearchValue
                pegi = (Integer) cbSearchValue.getValue();
                // cargamos nuestra lista con el filtro de busqueda
                games = gameManager.getAllGamesbyPegi(pegi);
                if (games.isEmpty()) {
                    lblGameError.setText("No hay juegos disponibles del pegi seleccionado");
                }
                //llamamos al metodo para que carge nuestra lista en la tabla
                chargeFilters(games);

            }
        } catch (Exception ex) {
            LOG.info("error al  filtrar la busqueda de juegos al pulsar btbSearh");
            LOG.log(Level.SEVERE, null, ex);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setContentText("Fallo de servidor, intentelo mas tarde");
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**
     * Este metodo se encarga de cargar los filtros del cliente en la tabla y
     * asi poder facilitarle la busqueda
     *
     * @param games lista de juegos filtrada obtenido en el metodo searchOnTable
     */
    public void chargeFilters(Collection<Game> games) {
        games = FXCollections.observableArrayList(games);
        tvGames.setItems((ObservableList<Game>) games);
    }

}
