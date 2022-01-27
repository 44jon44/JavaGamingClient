/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.EmployeeManager;
import factories.EmployeeManagerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.naming.OperationNotSupportedException;
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
        try {
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
            ObservableList<Employee> emps = FXCollections.observableArrayList(EmployeeManagerFactory.createEmployeeManager("REST_WEB_CLIENT").getAllEmployees());
            if (emps.isEmpty()) {
                lblError.setText("No se han encontrado empleados");
            }
            tblEmployees.setItems(emps);
            
            tfValue.requestFocus();

            //Cargamos los metodos de filtrado en la combo box
            ObservableList<String> observableList = FXCollections.observableList(list);
            observableList.add("Nombre");
            observableList.add("Salario");
            observableList.add("Mostrar todos los empleados");
            cmbFilter.setItems(observableList);
            cmbFilter.focusedProperty().addListener(this::filterFocusChanged);

            tcName.setCellValueFactory(
                    new PropertyValueFactory<>("fullName"));
            tcEmail.setCellValueFactory(
                    new PropertyValueFactory<>("email"));
            tcHiringDate.setCellValueFactory(
                    new PropertyValueFactory<>("hiringDate"));
            tcSalary.setCellValueFactory(
                    new PropertyValueFactory<>("salary"));
            tcLogin.setCellValueFactory(
                    new PropertyValueFactory<>("login"));

            tblEmployees.getSelectionModel().selectedItemProperty().addListener(this::handleUsersTableSelectionChanged);

        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void initStage(Parent root) {
        try {
            Scene employeeScene = new Scene(root);  
            //añadimos la escena en el stage
            stage.setScene(employeeScene);
            //definimos como modal la nueva ventana
            if(stage.getModality() != Modality.NONE)
                stage.initModality(Modality.NONE);
            //por defecto no podra redimensionarse
            if(stage.isResizable())
                stage.setResizable(false);
            
            tfValue.requestFocus();

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

            ObservableList<Employee> emps = FXCollections.observableArrayList(EmployeeManagerFactory.createEmployeeManager("REST_WEB_CLIENT").getAllEmployees());
            if (emps.isEmpty()) {
                lblError.setText("No se han encontrado empleados");
            }
            tblEmployees.setItems(emps);

            //Cargamos los metodos de filtrado en la combo box
            ObservableList<String> observableList = FXCollections.observableList(list);
            observableList.add("Nombre");
            observableList.add("Salario");
            observableList.add("Mostrar todos los empleados");
            cmbFilter.setItems(observableList);
            cmbFilter.focusedProperty().addListener(this::filterFocusChanged);

            tcName.setCellValueFactory(
                    new PropertyValueFactory<>("fullName"));
            tcEmail.setCellValueFactory(
                    new PropertyValueFactory<>("email"));
            tcHiringDate.setCellValueFactory(
                    new PropertyValueFactory<>("hiringDate"));
            tcSalary.setCellValueFactory(
                    new PropertyValueFactory<>("salary"));
            tcLogin.setCellValueFactory(
                    new PropertyValueFactory<>("login"));

            tblEmployees.getSelectionModel().selectedItemProperty().addListener(this::handleUsersTableSelectionChanged);

            if(!stage.isShowing())
                stage.showAndWait();
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void create(ActionEvent event) {

        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader employeeForm = new FXMLLoader(getClass().getResource("/view/employeeForm.fxml"));
            Parent root = (Parent) employeeForm.load();
            //controlador de la ventana
            EmployeeFormController controller = employeeForm.getController();
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Ha saltado este error");
            LOG.log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void modify(ActionEvent event) {
        try {
            Employee emp = ((Employee) tblEmployees.getSelectionModel().getSelectedItem());
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader employeeForm = new FXMLLoader(getClass().getResource("/view/employeeForm.fxml"));
            Parent root = (Parent) employeeForm.load();
            //cargamos el controlador de la ventana
            EmployeeFormController controller =  employeeForm.getController();
            controller.setStage(stage);
            controller.empToModify(emp);
            controller.initStage(root);
        } catch (IOException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                EmployeeManagerFactory.createEmployeeManager("REST_WEB_CLIENT").deleteEmployee(emp);
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
        boolean validFloat = true;
        lblError.setText("");
        if (tfValue.getText().equalsIgnoreCase("") && cmbFilter.getValue() != null) {
            if (!cmbFilter.getValue().toString().equals("Mostrar todos los empleados")) {
                lblError.setText("Debes de introducir un valor en campo value");
            }
        }
        if (cmbFilter.getSelectionModel().isEmpty() && cmbFilter.getValue() == null) {
            lblError.setText("Debes de seleccionar un filtro de busqueda");
        } else {
            if (cmbFilter.getValue().toString().equals("Salario")/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
                    && !tfValue.getText().equalsIgnoreCase("")) {

                try {
                    Float.parseFloat(tfValue.getText());
                } catch (NumberFormatException ex) {
                    validFloat = false;
                    lblError.setText("El campo value no es valido. Ejemplo correcto: 432.43");

                }
                try {
                    if (validFloat) {
                        ObservableList<Employee> empsS = FXCollections.observableArrayList(EmployeeManagerFactory.createEmployeeManager("REST_WEB_CLIENT").employeesBySalary(Float.parseFloat(tfValue.getText())));
                        if (empsS.isEmpty()) {
                            lblError.setText("No se han encontrado empleados");
                        }
                        tblEmployees.setItems(empsS);
                    }
                } catch (OperationNotSupportedException ex) {
                    Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            if (cmbFilter.getValue().toString().equals("Nombre")/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
                    && !tfValue.getText().equalsIgnoreCase("")) {
                try {
                    ObservableList<Employee> empsN = FXCollections.observableArrayList(EmployeeManagerFactory.createEmployeeManager("REST_WEB_CLIENT").employeesByName(tfValue.getText()));
                    if (empsN.isEmpty()) {
                        lblError.setText("No se han encontrado empleados");
                    }
                    tblEmployees.setItems(empsN);

                } catch (Exception ex) {
                    Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            if (cmbFilter.getValue().toString().equals("Mostrar todos los empleados")/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
                    && tfValue.getText().equalsIgnoreCase("")) {
                try {
                    ObservableList<Employee> emps = FXCollections.observableArrayList(EmployeeManagerFactory.createEmployeeManager("REST_WEB_CLIENT").getAllEmployees());
                    if (emps.isEmpty()) {

                        lblError.setText("No se han encontrado empleados");
                    }
                    tblEmployees.setItems(emps);
                } catch (Exception ex) {
                    Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (cmbFilter.getValue().toString().equals("Mostrar todos los empleados")
                    && !tfValue.getText().equalsIgnoreCase("")) {
                lblError.setText("El campo value debe de estar vacio");
            }
        }
        tfValue.setText("");
    }

    @FXML
    private void valueFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {

        if (oldValue) {//foco perdido 

        }
    }

    @FXML
    private void filterFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {

        if (newValue != null) {//foco perdido 
            tfValue.setText("");
        } else {

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