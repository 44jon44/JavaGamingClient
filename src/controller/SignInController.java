/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.EmployeeManager;
import businessLogic.PurchaseManager;
import factories.EmployeeManagerFactory;
import factories.PurchaseManagerFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.naming.OperationNotSupportedException;

/**
 * @author ibai arriola
 */
public class SignInController {

    
    private EmployeeManager employeesManager;
    private PurchaseManager puchasesManager;
    // un logger que nos informara mediante la terminal
    private static final Logger LOG = Logger.getLogger(SignInController.class.getName());
    //declaramos los componentes de la ventana  que manipularemos a continuacion
    private Stage signInStage;
    // textField  donde añadimos  el usuario
    @FXML
    private TextField tfUser;
    // textField  donde añadimos  la contraseña
    @FXML
    private PasswordField tfPassword;
    // button que inicia la sesion
    @FXML
    private Button btnSignIN;
    // un  label que visualiza los diferentes errores
    @FXML
    private AnchorPane panelSignIN;
    @FXML
    private Label lblError;
    //un hyperlink que llama a la ventana modal viewSingUP
    @FXML
    private Hyperlink hyperSignUP;

    //getter y setter del state SingIN
    public Stage getSignInStage() {
        return signInStage;
    }

    public void setSignInStage(Stage signInStage) {
        this.signInStage = signInStage;
    }

    public void initStage(Parent root) throws IOException {
        LOG.info("Init Stage de la VentanaSignIN");
        //Llamamos al metodo que se encarga del comportamiento del boton
        disableSignInBtn();
        //llamar al metodo de iniciar sesion cuando pulsas el boton
        btnSignIN.setOnAction(this::signIN);
        //llamar al metodo de  resgistrarse cuando pulsas el hyperEnlace
        hyperSignUP.setOnAction(this::signUp);
    }

    /**
     * El usuario podra iniciar la sesion
     *
     * @param event el evento de activacion del boton
     */
    @FXML
//    private void signIN(ActionEvent event) {
//        try {
//            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
//            FXMLLoader employee = new FXMLLoader(getClass().getResource("/view/employee.fxml"));
//            Parent root;
//            root = (Parent) employee.load();
//            panelSignIN.getScene().getWindow().hide();
//            //Creamos una nueva escena para la ventana SignIn
//            //cargamos el controlador de la ventana
//            EmployeeController controller = employee.getController();
//            controller.setStage(new Stage());
//            controller.setEmployeeManager(employeesManager);
//            controller.initStage(root);
//
//        } catch (IOException ex) {
//            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    private void signIN(ActionEvent event) {
        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader purchase = new FXMLLoader(getClass().getResource("/view/purchase.fxml"));
            Parent root;
            root = (Parent) purchase.load();
            panelSignIN.getScene().getWindow().hide();
            //Creamos una nueva escena para la ventana SignIn
            //cargamos el controlador de la ventana
            PurchaseController controller = purchase.getController();
            controller.setStage(new Stage());
            puchasesManager = PurchaseManagerFactory.createPurchaseManager("REST_WEB_CLIENT");
            controller.setPurchaseManager(puchasesManager);
            controller.initStage(root);

        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (OperationNotSupportedException ex)
        {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Abre una ventana modal de signUP que que el usuario se pueda registrar
     *
     * @param event el evento de activacion del enlace
     */
    @FXML
    private void signUp(ActionEvent event) {
        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader signUp = new FXMLLoader(getClass().getResource("/view/ViewSignUp.fxml"));
            Parent root;
            root = (Parent) signUp.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene UserViewScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage logout = new Stage();
            //definimos como modal la nueva ventana
            //logout.initModality(Modality.APPLICATION_MODAL);
            //añadimos la escena en el stage
            logout.setScene(UserViewScene);
            //por defecto no podra redimensionarse
            logout.setResizable(false);
            //cargamos el controlador de la ventana
            SignUpController controller = signUp.getController();
            controller.initStage(root);
            //mostramos la ventana modal mientras la actual se queda esperando
            logout.show();
            //cerramos la ventana
            panelSignIN.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * El metodo disableSigInBtn se encargara de comportamiento del boton el
     * boton por defecto estara desabilitado mientras no se informen los campos
     * TfUser y Tf Password
     */
    private void disableSignInBtn() {
        LOG.info("El boton esta desabilitado hasta informar los campos");
        btnSignIN.disableProperty().bind(tfUser.textProperty().isEmpty()
                .or(tfPassword.textProperty().isEmpty()
                ));
    }
}
