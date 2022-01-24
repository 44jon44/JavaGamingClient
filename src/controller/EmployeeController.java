/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.EmployeeManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import transferObjects.Employee;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class EmployeeController {

    private static final Logger LOG = Logger.getLogger(EmployeeController.class.getName());

    private EmployeeManager employeesManager;

    List<String> list = new ArrayList<String>();
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

    /**
     * User's login data table column.
     */
    @FXML
    private TableColumn tcName;
    /**
     * User's login data table column.
     */
    @FXML
    private TableColumn tcLogin;
    /**
     * User's login data table column.
     */
    @FXML
    private TableColumn tcEmail;
    /**
     * User's login data table column.
     */
    @FXML
    private TableColumn tcHiringDate;
    /**
     * User's login data table column.
     */
    @FXML
    private TableColumn tcSalary;

    @FXML
    private ComboBox cmbFilter;

    @FXML
    private TableView tblEmployees;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void initStage1(Parent root) {
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
        //Cargamos los metodos de filtrado en la combo box
        ObservableList<String> observableList = FXCollections.observableList(list);
        observableList.add("Nombre");
        observableList.add("Salario");
        cmbFilter.setItems(observableList);
        cmbFilter.focusedProperty().addListener(this::filterFocusChanged);

        tblEmployees.getSelectionModel().selectedItemProperty().addListener(this::handleUsersTableSelectionChanged);

    }

    public void initStage(Parent root) {
        Scene EmployeeScene = new Scene(root);

        //definimos como modal la nueva ventana
        stage.initModality(Modality.NONE);
        //aÃ±adimos la escena en el stage
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

        //Cargamos los metodos de filtrado en la combo box
        ObservableList<String> observableList = FXCollections.observableList(list);
        observableList.add("Nombre");
        observableList.add("Salario");
        cmbFilter.setItems(observableList);
        cmbFilter.focusedProperty().addListener(this::filterFocusChanged);

        stage.showAndWait();
    }

    private void create(ActionEvent event) {

        try {
            //getResource tienes que aÃ±adir la ruta de la ventana que quieres iniciar.
            FXMLLoader employeeForm = new FXMLLoader(getClass().getResource("/view/employeeForm.fxml"));
            Parent root = (Parent) employeeForm.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene employeeFormScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage employeeFormStage = new Stage();

            //definimos como modal la nueva ventana
            employeeFormStage.initModality(Modality.NONE);
            //aÃ±adimos la escena en el stage
            employeeFormStage.setScene(employeeFormScene);
            //por defecto no podra redimensionarse
            employeeFormStage.setResizable(false);
            //cargamos el controlador de la ventana
            EmployeeFormController controller = (EmployeeFormController) employeeForm.getController();
            controller.initStage(root);
            controller = employeeForm.getController();
            controller.initStage(root);
            controller.initStageAdd(root);
            employeeFormStage.show();
            employeePane.getScene().getWindow().hide();
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Ha saltado este error");
            LOG.log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void modify(ActionEvent event) {

    }

    @FXML
    private void delete(ActionEvent event) {
        try {
            Employee emp;
            emp = ((Employee) tblEmployees.getSelectionModel().getSelectedItem());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "¿Borrar la fila seleccionada?\n"
                    + "Esta operación no se puede deshacer.",
                    ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                employeesManager.deleteEmployee(emp);
            }
            tblEmployees.getItems().remove(emp);
            tblEmployees.refresh();
            btnDelete.setDisable(true);
            btnModify.setDisable(true);

            tblEmployees.getSelectionModel().clearSelection();
            tblEmployees.refresh();
        } catch (Exception ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void find(ActionEvent event) {

        if (tfValue.getText().equalsIgnoreCase("")) {
            lblError.setText("Debes de introducir un valor en campo value");
        }
        if (cmbFilter.getSelectionModel().isEmpty()) {
            lblError.setText("Debes de seleccionar un filtro de busqueda");
        }
        if (cmbFilter.getValue().equals("Nombre")   ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                && !cmbFilter.getSelectionModel().isEmpty()
                && tfValue.getText().equalsIgnoreCase("")) {
            try {
                System.out.println("Pajin");
                employeesManager.employeesByName(tfValue.getText());

            } catch (Exception ex) {
                Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else  {
            System.out.println("NoPajin");
        }
        {

        }
    }

    @FXML
    private void valueFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {

        if (oldValue) {//foco perdido 

        }
    }

    @FXML
    private void filterFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {

        if (oldValue) {//foco perdido 

        }
    }

    @FXML
    private void handleUsersTableSelectionChanged(ObservableValue observable,
            Object oldValue,
            Object newValue) {
        //If there is a row selected, move row data to corresponding fields in the
        //window and enable create, modify and delete buttons
        if (newValue != null) {

            btnDelete.setDisable(false);
            btnModify.setDisable(false);
        } else {
            btnDelete.setDisable(true);
            btnModify.setDisable(true);
        }

    }
    public void setEmployeeManager(EmployeeManager employeesManager) {
        this.employeesManager = employeesManager;
    }

}
