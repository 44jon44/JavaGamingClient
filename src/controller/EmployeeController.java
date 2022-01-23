/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class EmployeeController {

    private static final Logger LOG = Logger.getLogger(EmployeeController.class.getName());

    @FXML
    private Pane employeePane;

    /**
     * Create user data button.
     */
    @FXML
    private Button btnAdd;
    /**
     * Modify user data button.
     */
    @FXML
    private Button btnModify;
    /**
     * Delete user data button.
     */
    @FXML
    private Button btnDelete;
    /**
     * Delete user data button.
     */
    @FXML
    private Button btnFind;
    @FXML
    private TextField tfValue;
    @FXML
    private Label lblError;

    @FXML
    private ComboBox cmbFilter;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage1(Parent root) {
        

        //definimos como modal la nueva ventana
        

        

        // menuController.setStage(stage);
        btnAdd.setOnAction(this::create);
        btnDelete.setOnAction(this::delete);
        btnModify.setOnAction(this::modify);
        btnFind.setOnAction(this::find);
        //lblError se inicializa vacio
        lblError.setText("");
        //Se deshabilitan los botones btnDelete y bntModify
        btnDelete.setDisable(true);
        btnModify.setDisable(true);

        btnFind.focusedProperty().addListener(this::valueFocusChanged);
        
    }
      public void initStage(Parent root) {
        Scene EmployeeScene = new Scene(root);

        //definimos como modal la nueva ventana
        stage.initModality(Modality.NONE);
        //añadimos la escena en el stage
        stage.setScene(EmployeeScene);
        //por defecto no podra redimensionarse
        stage.setResizable(false);

        // menuController.setStage(stage);
        btnAdd.setOnAction(this::create);
        btnDelete.setOnAction(this::delete);
        btnModify.setOnAction(this::modify);
        btnFind.setOnAction(this::find);
        //lblError se inicializa vacio
        lblError.setText("");
        //Se deshabilitan los botones btnDelete y bntModify
        btnDelete.setDisable(true);
        btnModify.setDisable(true);

        btnFind.focusedProperty().addListener(this::valueFocusChanged);
        stage.showAndWait();
    }

    private void create(ActionEvent event) {

        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader employeeForm = new FXMLLoader(getClass().getResource("/view/employeeForm.fxml"));
            Parent root = (Parent) employeeForm.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene employeeFormScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage employeeFormStage = new Stage();

            //definimos como modal la nueva ventana
            employeeFormStage.initModality(Modality.NONE);
            //añadimos la escena en el stage
            employeeFormStage.setScene(employeeFormScene);
            //por defecto no podra redimensionarse
            employeeFormStage.setResizable(false);
            //cargamos el controlador de la ventana
            EmployeeFormController controller = (EmployeeFormController) employeeForm.getController();
            controller.initStage(root);
            controller = employeeForm.getController();
            controller.initStageAdd(root);
            employeeFormStage.show();
            employeePane.getScene().getWindow().hide();
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Ha saltado este error");
            LOG.log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void delete(ActionEvent event) {

    }

    @FXML
    private void modify(ActionEvent event) {

    }

    @FXML
    private void find(ActionEvent event) {

        if (tfValue.getText().equalsIgnoreCase("")) {
            lblError.setText("Debes de introducir un valor en campo value");
        }
    }

    @FXML
    private void valueFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {
        //lblErrorPassword.setTextFill(Paint.valueOf("RED"));
        if (oldValue) {//foco perdido 
            lblError.setText("");
        }
    }

}
