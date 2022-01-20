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
public class HbMenuAdmController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void initStage(Parent root) {

    }
    
    private Stage stageToClose=null;

    public void windowClose() {
        stageToClose.close();
    }
    public void chargeController(){
    
    }

    @FXML
    private void employeesClicked(ActionEvent event) {
        try {
            if(stageToClose!=null){
                System.out.println("Pajin");
                windowClose();
            }
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader employee = new FXMLLoader(getClass().getResource("/view/employee.fxml"));
            Parent root;
            root = (Parent) employee.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene EmployeeScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage employeeStage = new Stage();
            
            //definimos como modal la nueva ventana
            employeeStage.initModality(Modality.WINDOW_MODAL);
            //añadimos la escena en el stage
            employeeStage.setScene(EmployeeScene);
            //por defecto no podra redimensionarse
            employeeStage.setResizable(false);
            employeeStage.show();
            stageToClose = employeeStage;
        } catch (IOException ex) {
            Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
        };
    }

    @FXML
    private void gamesClicked(ActionEvent event) {
        try {
            if(stageToClose!=null){
                System.out.println("Pajin");
                windowClose();
            }
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader game = new FXMLLoader(getClass().getResource("/view/game.fxml"));
            Parent root;
            root = (Parent) game.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene GameScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage gameStage = new Stage();
            stageToClose = gameStage;
            //definimos como modal la nueva ventana
            gameStage.initModality(Modality.WINDOW_MODAL);
            //añadimos la escena en el stage
            gameStage.setScene(GameScene);
            //por defecto no podra redimensionarse
            gameStage.setResizable(false);
            gameStage.show();

        } catch (IOException ex) {
            Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
        };
    }

    @FXML
    private void purchasesClicked(ActionEvent event) {
        try {
            if(stageToClose!=null){
                System.out.println("Pajin");
                windowClose();
            }
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader purchase = new FXMLLoader(getClass().getResource("/view/purchase.fxml"));
            Parent root;
            root = (Parent) purchase.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene PurchaseScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage purchaseStage = new Stage();
            stageToClose = purchaseStage;
            //definimos como modal la nueva ventana
            purchaseStage.initModality(Modality.WINDOW_MODAL);
            //añadimos la escena en el stage
            purchaseStage.setScene(PurchaseScene);
            //por defecto no podra redimensionarse
            purchaseStage.setResizable(false);
            purchaseStage.show();

        } catch (IOException ex) {
            Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
        };
    }

    @FXML
    private void printClicked(ActionEvent event) {
        try {
            if(stageToClose!=null){
                windowClose();
            }
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader game = new FXMLLoader(getClass().getResource("/view/game.fxml"));
            Parent root;
            root = (Parent) game.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene GameScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage gameStage = new Stage();
            stageToClose = gameStage;
            //definimos como modal la nueva ventana
            gameStage.initModality(Modality.NONE);
            //añadimos la escena en el stage
            gameStage.setScene(GameScene);
            //por defecto no podra redimensionarse
            gameStage.setResizable(false);
            gameStage.show();

        } catch (IOException ex) {
            Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
        };
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
        };
    }

    @FXML
    private void exitClicked(ActionEvent event) {
        System.out.println("Pajin");
    }

    private void logOutClicked(ActionEvent event) {
        System.out.println("Pajin");
    }

}
