/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.EmployeeManager;

import businessLogic.UserManager;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.naming.OperationNotSupportedException;
import transferObjects.User;
import exception.*;

/**
 * @author ibai arriola
 */
public class SignInController {

    private EmployeeManager employeesManager;
    private UserManager usersManager;
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
        setUsersManager(usersManager);
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
            ObservableList<User> users = FXCollections.observableArrayList(usersManager.checkLogin(tfUser.getText().trim(), tfPassword.getText().trim()));
            if (!users.isEmpty()) {
                System.out.println(users.size());

                //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
                FXMLLoader employee = new FXMLLoader(getClass().getResource("/view/employee.fxml"));
                Parent root;
                root = (Parent) employee.load();
                panelSignIN.getScene().getWindow().hide();
                //Creamos una nueva escena para la ventana SignIn
                //cargamos el controlador de la ventana
                EmployeeController controller = employee.getController();
                controller.setStage(new Stage());
                controller.setEmployeeManager(employeesManager);
                controller.initStage(root);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Usuario o contraseña incorrectos",
                        ButtonType.OK);
                alert.showAndWait();
            }

        } catch (IOException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SignInController.class.getName()).log(Level.SEVERE, null, ex);
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Error iniciando sesion");
            errorAlert.showAndWait();
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

    public void setEmployeesManager(EmployeeManager employeesManager) {
        this.employeesManager = employeesManager;
    }

    public void setUsersManager(UserManager usersManager) {
        this.usersManager = usersManager;
    }

}
