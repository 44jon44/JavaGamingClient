package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class HbMenuAdmController{

    private static final Logger LOG = Logger.getLogger(HbMenuAdmController.class.getName());

    @FXML
    private HBox hbMenuAdm;
    @FXML
    private MenuBar mbLeft;
    @FXML
    private Menu menuOptions;
    @FXML
    private MenuItem miManageUsers;
    @FXML
    private MenuItem miManageGames;
    @FXML
    private MenuItem miManagePurchases;
    @FXML
    private MenuItem miChangePasswd;
    @FXML
    private MenuItem miPrint;
    @FXML
    private MenuBar mbRight;
    @FXML
    private Menu mbExit;
    @FXML
    private Menu mbSignOut;
    
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    public void initStage(Parent root) throws IOException {
        LOG.info("Init Stage del menú hbMenuAdm");
        //llamar al método que abre la ventana de empleado
        miManageUsers.setOnAction(this::employeesClicked);
        //llamar al método que abre la ventana de juegos
        miManageGames.setOnAction(this::gamesClicked);
        //llamar al método que abre la ventana de compras
        miManagePurchases.setOnAction(this::purchasesClicked);
        //llamar al método que abre la ventana del formulario
        miPrint.setOnAction(this::printClicked);
        //llamar al método que abre la ventana para cambiar la contraseña
        miChangePasswd.setOnAction(this::passwordClicked);
    }
    
    @FXML
    private void employeesClicked(ActionEvent event) {
        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader employee = new FXMLLoader(getClass().getResource("/view/employee.fxml"));
            Parent root;
            root = (Parent) employee.load();
             //Creamos una nueva escena para la ventana SignIn
            Scene employeeScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage employeeStage = (Stage)hbMenuAdm.getScene().getWindow();
            //añadimos la escena en el stage
            employeeStage.setScene(employeeScene);
        } catch (IOException ex) {
            Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void gamesClicked(ActionEvent event) {
        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader game = new FXMLLoader(getClass().getResource("/view/game.fxml"));
            Parent root;
            root = (Parent) game.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene gameScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage gameStage = (Stage)hbMenuAdm.getScene().getWindow();
            //añadimos la escena en el stage
            gameStage.setScene(gameScene);
        } catch (IOException ex) {
            Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void purchasesClicked(ActionEvent event) {
        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader purchase = new FXMLLoader(getClass().getResource("/view/purchase.fxml"));
            Parent root;
            root = (Parent) purchase.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene PurchaseScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage purchaseStage = (Stage)hbMenuAdm.getScene().getWindow();
            //añadimos la escena en el stage
            purchaseStage.setScene(PurchaseScene);
        } catch (IOException ex) {
            Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void printClicked(ActionEvent event) {
        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader game = new FXMLLoader(getClass().getResource("/view/game.fxml"));
            Parent root;
            root = (Parent) game.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene GameScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage gameStage = new Stage();
            //definimos como modal la nueva ventana
            gameStage.initModality(Modality.NONE);
            //añadimos la escena en el stage
            gameStage.setScene(GameScene);
            //por defecto no podra redimensionarse
            gameStage.setResizable(false);
            gameStage.show();

        } catch (IOException ex) {
            Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void passwordClicked(ActionEvent event) {
        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader game = new FXMLLoader(getClass().getResource("/view/game.fxml"));
            Parent root;
            root = (Parent) game.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene GameScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage gameStage = new Stage();
            //definimos como modal la nueva ventana
            gameStage.initModality(Modality.NONE);
            //añadimos la escena en el stage
            gameStage.setScene(GameScene);
            //por defecto no podra redimensionarse
            gameStage.setResizable(false);
            gameStage.show();

        } catch (IOException ex) {
            Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void exitClicked(ActionEvent event) {
        System.out.println("Pajin");
    }

    private void logOutClicked(ActionEvent event) {
        System.out.println("Pajin");
    }

    private void closePreviousStage(Stage stage) {
        stage.getScene().getWindow().hide();
    }
}
