package controller;

import businessLogic.EmployeeManager;
import businessLogic.UserManager;
import factories.UserManagerFactory;
import factories.GameManagerFactory;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.naming.OperationNotSupportedException;
import exception.*;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class HbMenuAdmController {

    private static final Logger LOG = Logger.getLogger(HbMenuAdmController.class.getName());
    /**
     * Interfaz de metos para la clase Employee
     */
    private EmployeeManager employeesManager;
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
    private MenuItem logOut;
    @FXML
    private MenuItem closeApp;
    @FXML
    private TableView tblEmployees;

    private Stage stage;

    private UserManager usersManager;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage(Parent root) throws IOException {
        LOG.info("Init Stage del menú hbMenuAdm");
        try {
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
            //metodo que cierr la aplicacion
            closeApp.setOnAction(this::closeAppClicked);
            //metodo que cierra sesion
            logOut.setOnAction(this::logOutClicked);
        } catch (Exception ex) {
            Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Error abriendo el MenuHbMenuAdmController");
            errorAlert.showAndWait();
        }
    }

    @FXML
    private void employeesClicked(ActionEvent event) {
        LOG.info("Abriendo la ventana Employee...");
        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader employee = new FXMLLoader(getClass().getResource("/view/employee.fxml"));
            Parent root;
            root = (Parent) employee.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene employeeScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage employeeStage = (Stage) hbMenuAdm.getScene().getWindow();
            //añadimos la escena en el stage
            EmployeeController controller = employee.getController();
            controller.initStage1(root);
            controller.setEmployeeManager(employeesManager);
            employeeStage.setScene(employeeScene);

        } catch (IOException ex) {
            Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Error abriendo la ventana employee");
            errorAlert.showAndWait();
        }
    }

    @FXML
    private void gamesClicked(ActionEvent event) {
        LOG.info("Abriendo la venta Game");
        try {
            FXMLLoader game = new FXMLLoader(getClass().getResource("/view/game.fxml"));
            Parent root;
            root = (Parent) game.load();
            //creamos un nuevo escenario para la nueva ventana
            stage = (Stage) hbMenuAdm.getScene().getWindow();
            //controlador de la vista game
            GameController controller = game.getController();
            
            controller.setGameManager(GameManagerFactory.createGameManager("REST_WEB_CLIENT"));
            
            //establecemos el stage
            controller.setStage(stage);
            //lanzamos el initStage
            controller.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void purchasesClicked(ActionEvent event) {
        LOG.info("Abriendo la venta Purchase...");
        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader purchase = new FXMLLoader(getClass().getResource("/view/purchase.fxml"));
            Parent root;
            root = (Parent) purchase.load();
            stage = (Stage) hbMenuAdm.getScene().getWindow();
//            //Creamos una nueva escena para la ventana SignIn
//            Scene PurchaseScene = new Scene(root);
//            Stage purchaseStage = (Stage) hbMenuAdm.getScene().getWindow();
//            //creamos un nuevo escenario para la nueva ventana
            PurchaseController controller = purchase.getController();
            controller.setStage(stage);
            //añadimos la escena en el stage
            controller.initStage(root);
            //purchaseStage.setScene(PurchaseScene);

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
//        Alert errorAlert = new Alert(AlertType.ERROR);
//        errorAlert.setHeaderText("Error");
//        errorAlert.setContentText("No se ha implementado correctamente");
//        errorAlert.showAndWait();
    }

    @FXML
    private void printClicked(ActionEvent event) {

    }

    @FXML
    private void passwordClicked(ActionEvent event) {
        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader signIn = new FXMLLoader(getClass().getResource("/view/ViewSignIn.fxml"));
            Parent root;
            root = (Parent) signIn.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene SignInScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage signInStage = new Stage();
            //definimos como modal la nueva ventana
            signInStage.initModality(Modality.NONE);
            //añadimos la escena en el stage
            signInStage.setScene(SignInScene);
            //por defecto no podra redimensionarse
            signInStage.setResizable(false);
            signInStage.show();

        } catch (IOException ex) {
            Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void logOutClicked(ActionEvent event) {
        LOG.info("Cerrando sesion...");
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "¿Esta seguro de cerrar sesion?",
                    ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.out.println("Entra");
                try {
                    FXMLLoader signIn = new FXMLLoader(getClass().getResource("/view/ViewSignIn.fxml"));
                    Parent root;
                    root = (Parent) signIn.load();
                    //Creamos una nueva escena para la ventana SignIn
                    Scene employeeScene = new Scene(root);
                    //creamos un nuevo escenario para la nueva ventana
                    Stage employeeStage = (Stage) hbMenuAdm.getScene().getWindow();
                    SignInController controller = signIn.getController();
                    controller.setUsersManager(UserManagerFactory.createUserManager("REST_WEB_CLIENT"));
                    //añadimos la escena en el stage
                    employeeStage.setScene(employeeScene);
                } catch (IOException ex) {
                    Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (OperationNotSupportedException ex) {
                    Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        } catch (Exception ex) {
            Alert errorAlert = new Alert(AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Error cerrando sesion");
            errorAlert.showAndWait();
        }
    }

    @FXML
    private void closeAppClicked(ActionEvent event) {
        LOG.info("Cerrando la aplicacion...");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Esta seguro de salir de la aplicacion?",
                ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            //Cierra la aplicacion
            System.exit(0);
        }
    }
}
