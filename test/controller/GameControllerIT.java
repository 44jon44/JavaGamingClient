/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.GameManager;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.BeforeClass;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import org.testfx.matcher.base.NodeMatchers;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import org.testfx.matcher.control.LabeledMatchers;
import transferObjects.Game;
import transferObjects.Genre;

/**
 *
 * @author ibai Arriola
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GameControllerIT extends ApplicationTest {

    //atributo para probar  el rango maximo de los atributos
    private static final String OVERSIZED_TEXT = "1111111111111"
            + "11111111111111111111111111111111111"
            + "111111111111111111111111111111111111111111111111";

    private static final Logger LOG = Logger.getLogger(GameController.class.getName());

    private GameManager gameManager;
    private ObservableList<Game> gameObservableList;

    private TableView<Game> tvGames;

    private TableColumn tcGameName;

    private TableColumn tcGameGenre;

    private TableColumn tcGamePegi;

    private TableColumn tcGameReleaseDate;

    private TableColumn tcGamePrice;

    private ComboBox<String> cbSearchBy;

    private ComboBox cbSearchValue;

    private Button btnSearch;

    private Button btnAddGame;

    private Button btnModifyGame;

    private Button btnDeleteGame;

    private Label lblGameError;

    private HBox hbMenuAdm;

    private Button btnSignIN;

    private TextField tfUser;
    // textField  donde añadimos  la contraseña

    private PasswordField tfPassword;

    private MenuItem miManageGames;

    //ATRIBUTOS DEL CONTROLADOR GAME FORM 
    private ComboBox cbGamePegi;

    private DatePicker dpReleaseDate;

    private ComboBox<Genre> cbGameGenre;

    private TextField tfGameName;

    private TextField tfGamePrice;

    private Button btnAdd;

    private Button btnReport;

    private Button btnModify;

    private Label lblErrorGamePrice;

    private Hyperlink hlBack;

    private Label lblErrorGameName;
    private Pane gameFormPane;
    private final TableView table = lookup("#tvGames").queryTableView();

    @BeforeClass
    public static void inicio() throws TimeoutException {
        //start JavaFX application to be tested    
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(clientapp.ClientApplication.class);
    }

    /**
     * Este test prueba que la ventana se inicie correctamente con los y
     * verifica que los botones que están habilitado y deshabilitados.
     */
    @Test
    public void testA_initialInteraction() {
        clickOn("#tfUser");
        write("1");
        clickOn("#tfPassword");
        write("1");
        clickOn("#btnSignIN");
        clickOn("Opciones");
        clickOn("Gestionar juegos");
        verifyThat("#btnAddGame", isEnabled());
        verifyThat("#btnReport", isEnabled());
        verifyThat("#btnSearch", isEnabled());
        verifyThat("#btnModifyGame", isDisabled());
        verifyThat("#btnDeleteGame", isDisabled());
        System.out.println("controller.GameControllerIT."
                + "testA_initialInteraction()");

        cbGameGenre = lookup("#cbGameGenre").queryComboBox();
        cbGamePegi = lookup("#cbGamePegi").queryComboBox();

    }

    /**
     * Este test prueba que se añada un objeto Game Verificando el Alert que
     * obtiene el Usuario al crear un Objeto.
     */
    @Test
    public void testB_CreateGame() {

        int rowCount = table.getItems().size();
        int genRandom = 0;

        clickOn("#btnAddGame");
        verifyThat("#gameFormPane", isVisible());
        String game = "GAME" + new Random().nextInt();
        clickOn("#tfGameName");
        write(game);
        clickOn("#tfGamePrice");
        write("80");
        clickOn("#btnAdd");
        verifyThat("juego creado existosamente",
                NodeMatchers.isVisible());
        clickOn(isDefaultButton());
        clickOn("#hlBack");
        table.refresh();
        
        int rowCount2 = table.getItems().size();
        assertEquals("The row has not been added!!!", rowCount + 1, rowCount2);
        //look for user in table data model
        List<Game> games = table.getItems();
        assertEquals("The game has not been added!!!",
                games.stream().filter(u -> u.getName().equals(game)).count(), 1);
    }

    /**
     * Este test comprueba que los botones borrar y modificar se habiliten
     * solamente cuando se seleccione una columna en la tabla.
     */
    //@Test
    public void testC_VerifyEnableButtons() {
        Node value = lookup(".table-row-cell").nth(0).query();
        clickOn(value);
        verifyThat("#btnModifyGame", isEnabled());
        verifyThat("#btnDeleteGame", isEnabled());

    }

    /**
     * este test prueba cuando creas un juego que ya existe.
     */
    // @Test
    public void testD_CreateGameExist() {
        int rowCount = table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        clickOn("#btnAddGame");
        verifyThat("#gameFormPane", isVisible());
        clickOn("#tfGameName");
        write("aaa");
        clickOn("#tfGamePrice");
        write("80");
        clickOn("#btnAdd");
        verifyThat("ese juego ya existe",
                NodeMatchers.isVisible());
        clickOn(isDefaultButton());
        clickOn("#hlBack");
        assertEquals("A row has been added!!!", rowCount, table.getItems().size());
    }

    /**
     * Este test comprueba que al borrar un juego y en la confirmación le das
     * cancelar no borre el juego.
     */
    //  @Test
    public void testE_CalcelDelate() {
        int rowCount = table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        Node value = lookup(".table-row-cell").nth(0).query();
        clickOn(value);
        clickOn("#btnDeleteGame");
        clickOn("Cancelar");
        verifyThat("operación cancelada", NodeMatchers.isVisible());
        clickOn(isDefaultButton());
        assertEquals("A row has been added!!!", rowCount, table.getItems().size());
    }

    /**
     * Este test comprueba cuando le das al boton de añadir y tengas algun campo
     * mal salte el Alert que informe del error.
     */
    // @Test
    public void testF_GameFormError() {
        clickOn("#btnAddGame");
        verifyThat("#gameFormPane", isVisible());
        clickOn("#tfGameName");
        write("Game *");
        clickOn("#tfGamePrice");
        verifyThat("#lblErrorGameName", LabeledMatchers.
                hasText("Nombre de juego invalido"));
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        write("AA");
        clickOn("#tfGameName");
        verifyThat("#lblErrorGamePrice", LabeledMatchers.
                hasText("introduzca un número,puede ser decimal"));
        clickOn("#hlBack");
    }

    /**
     * Este test comprueba que al cambiar el foco de algun campo que sea
     * obligatorio lo indice.
     */
    // @Test
    public void testG_GameNecesaryFields() {
        clickOn("#btnAddGame");
        verifyThat("#gameFormPane", isVisible());
        clickOn("#tfGameName");
        write("");
        clickOn("#tfGamePrice");
        verifyThat("#lblErrorGameName", LabeledMatchers.
                hasText("debe tener (3-50) caracteres"));
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        write("");
        clickOn("#tfGameName");
        verifyThat("#lblErrorGamePrice", LabeledMatchers.
                hasText("Campo obligatorio"));
        eraseText(1);
        clickOn("#hlBack");
    }

    /**
     * Este test comprueba que no se pueda añadir una longitud mas grande a un
     * atributo que en la base de datos
     */
    // @Test
    public void testH_MaxLengh() {
        clickOn("#btnAddGame");
        verifyThat("#gameFormPane", isVisible());
        clickOn("#tfGameName");
        write(OVERSIZED_TEXT);
        clickOn("#tfGamePrice");
        verifyThat("#lblErrorGameName", LabeledMatchers.
                hasText("debe tener (3-50) caracteres"));
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        write(OVERSIZED_TEXT);
        clickOn("#cbGamePegi");
        verifyThat("#lblErrorGamePrice", LabeledMatchers.
                hasText("mas de 50 caracteres"));
        clickOn("#hlBack");
    }

    /**
     * Comprueba que el se modifique correctamente un objeto Game
     */
    // @Test
    public void testI_Modify() {
        int rowCount = table.getItems().size();
        assertNotEquals("La tabla no tiene datos. Nose puede testear",
                rowCount, 0);
        Node value = lookup(".table-row-cell").nth(0).query();
        clickOn(value);
        clickOn("#btnModifyGame");

        Game modifyGame = new Game();
        TextField tfName = lookup("#tfGameName").query();
        modifyGame.setName(tfName.getText());
        doubleClickOn("#tfGameName");
        eraseText(1);
        String game = "GAME" + new Random().nextInt();
        write(game);

        clickOn("#btnModify");
        verifyThat("Se ha modificado correctamente el Juego",
                NodeMatchers.isVisible());
        clickOn(isDefaultButton());

        Game game2 = (Game) table.getItems().get(rowCount - 1);
        assertNotEquals(modifyGame.getName(), game2.getName());

    }

    // @Test
    public void testJ_ModifyExist() {
        int rowCount = table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        Node value = lookup(".table-row-cell").nth(0).query();
        clickOn(value);
        clickOn("#btnModifyGame");
        doubleClickOn("#tfGameName");
        eraseText(1);
        write("aaa");
        clickOn("#btnModify");
        verifyThat("ese juego ya existe",
                NodeMatchers.isVisible());
        clickOn(isDefaultButton());
        clickOn("#hlBack");
        assertEquals("A row has been added!!!", rowCount, table.getItems().size());
    }

    /**
     *
     *
     *
     * Este test comprueba cuando se borra un juego.
     */
    //@Test
    public void testK_DeleteGame() {
        tvGames = lookup("#tvGames").queryTableView();
        int rowCount = tvGames.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        //look for 1st row in table view and click it
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        Node value = lookup(".table-row-cell").nth(0).query();
        clickOn(value);
        doubleClickOn("#btnDeleteGame");
        clickOn(isDefaultButton());
        assertEquals("The row has not been deleted!!!",
                rowCount - 1, tvGames.getItems().size());
    }

    /**
     *
     *
     * Este test comprueba que si damos un filtro que no encuentra sale un
     * error.
     */
    //@Test
    public void testN_ProbarFiltro1() {
        int rowCount = table.getItems().size();
        clickOn("#cbSearchBy");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#cbSearchValue");
        type(KeyCode.ENTER);
        clickOn("#btnSearch");
        verifyThat("#lblGameError", LabeledMatchers.
                hasText("No hay juegos disponibles del pegi seleccionado"));

        assertEquals("se han encontrado juegos de ese filtro!!!", 0, table.getItems().size());
    }

    /**
     *
     *
     * Este test comprueba que si damos un filtro que no encuentra sale un
     * error.
     */
    //  @Test
    public void testM_ProbarFiltro2() {
        int rowCount = table.getItems().size();
        clickOn("#cbSearchBy");
        type(KeyCode.UP);
        type(KeyCode.ENTER);
        clickOn("#cbSearchValue");
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);
        clickOn("#btnSearch");
        verifyThat("#lblGameError", LabeledMatchers.
                hasText("No hay juegos disponibles del genero seleccionado"));

        assertEquals("se han encontrado juegos de ese filtro!!!", 0, table.getItems().size());

    }

}
