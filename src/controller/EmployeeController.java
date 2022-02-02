/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.EmployeeManager;
import factories.EmployeeManagerFactory;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.naming.OperationNotSupportedException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
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
    private Button btnReport;
    @FXML
    private TextField tfValue;
    @FXML
    private Label lblError;
    private final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
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
    private TableColumn<Employee, String> tcHiringDate;
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

    /**
     * initStage en caso de que se acceda mediante el menu
     *
     * @param root
     */
    public void initStage1(Parent root) {
        try {
            
            btnAdd.setOnAction(this::create);
            btnDelete.setOnAction(this::delete);
            btnModify.setOnAction(this::modify);
            btnFind.setOnAction(this::find);
            btnReport.setOnAction(this::print);
            //lblError se inicializa vacio
            lblError.setText("");
            //Digitos maximos para tfValue
            tfValue.requestFocus();
            tfValue.textProperty().addListener(this::tfValueTextChanged);
            //Se deshabilitan los botones btnDelete y bntModify
            btnDelete.setDisable(true);
            btnModify.setDisable(true);

            tcSalary.setStyle("-fx-alignment: CENTER-RIGHT;");
            tcName.setStyle("-fx-alignment: CENTER;");
            tcEmail.setStyle("-fx-alignment: CENTER;");
            tcLogin.setStyle("-fx-alignment: CENTER;");
            tcHiringDate.setStyle("-fx-alignment: CENTER;");

            tblEmployees.getSelectionModel().clearSelection();
            tblEmployees.refresh();

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
            cmbFilter.getSelectionModel().selectFirst();

            tcName.setCellValueFactory(
                    new PropertyValueFactory<>("fullName"));
            tcEmail.setCellValueFactory(
                    new PropertyValueFactory<>("email"));
            tcHiringDate.setCellValueFactory(cellData
                    -> new SimpleStringProperty(dateFormatter.format(cellData.getValue().getHiringDate())));
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

    /**
     * Metodo initStage en caso de que se acceda desde signIn
     *
     * @param root
     */
    public void initStage(Parent root) {
        try {
            Scene EmployeeScene = new Scene(root);

            //definimos como modal la nueva ventana
            stage.initModality(Modality.NONE);
            //anadimos la escena en el stage
            stage.setScene(EmployeeScene);
            //por defecto no podra redimensionarse
            stage.setResizable(false);
            tfValue.requestFocus();

            
            // menuController.setStage(stage);
            btnAdd.setOnAction(this::create);
            btnDelete.setOnAction(this::delete);
            btnModify.setOnAction(this::modify);
            btnFind.setOnAction(this::find);
            btnReport.setOnAction(this::print);
            //lblError se inicializa vacio
            lblError.setText("");
            //Digitos maximos para tfValue
            tfValue.requestFocus();
            tfValue.textProperty().addListener(this::tfValueTextChanged);
            //Se deshabilitan los botones btnDelete y bntModify
            btnDelete.setDisable(true);
            btnModify.setDisable(true);

            tcSalary.setStyle("-fx-alignment: CENTER-RIGHT;");
            tcName.setStyle("-fx-alignment: CENTER;");
            tcEmail.setStyle("-fx-alignment: CENTER;");
            tcLogin.setStyle("-fx-alignment: CENTER;");
            tcHiringDate.setStyle("-fx-alignment: CENTER;");

            tblEmployees.getSelectionModel().clearSelection();
            tblEmployees.refresh();

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
            cmbFilter.getSelectionModel().selectFirst();

            tcName.setCellValueFactory(
                    new PropertyValueFactory<>("fullName"));
            tcEmail.setCellValueFactory(
                    new PropertyValueFactory<>("email"));
            tcHiringDate.setCellValueFactory(cellData
                    -> new SimpleStringProperty(dateFormatter.format(cellData.getValue().getHiringDate())));
            tcSalary.setCellValueFactory(
                    new PropertyValueFactory<>("salary"));
            tcLogin.setCellValueFactory(
                    new PropertyValueFactory<>("login"));

            tblEmployees.getSelectionModel().selectedItemProperty().addListener(this::handleUsersTableSelectionChanged);

            stage.showAndWait();
        } catch (OperationNotSupportedException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metodo que abre la ventana employeeForm sin cargar datos
     *
     * @param event
     */
    private void create(ActionEvent event) {

        try {
            //getResource tienes que anadir la ruta de la ventana que quieres iniciar.
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
            controller.initStageAdd();
            controller = employeeForm.getController();
            controller.initStage(root);

            employeeFormStage.show();
            employeePane.getScene().getWindow().hide();
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Ha saltado este error");
            LOG.log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Metodo que abre la ventana employeeForm cargando datos
     *
     * @param event
     */
    @FXML
    private void modify(ActionEvent event) {
        try {
            Employee emp = ((Employee) tblEmployees.getSelectionModel().getSelectedItem());

            //getResource tienes que aÃ±adir la ruta de la ventana que quieres iniciar.
            FXMLLoader employeeForm = new FXMLLoader(getClass().getResource("/view/employeeForm.fxml"));
            Parent root = (Parent) employeeForm.load();
            //Creamos una nueva escena para la ventana SignIn
            Scene employeeFormScene = new Scene(root);
            //creamos un nuevo escenario para la nueva ventana
            Stage employeeFormStage = new Stage();

            //definimos como modal la nueva ventana
            employeeFormStage.initModality(Modality.NONE);
            //anadimos la escena en el stage
            employeeFormStage.setScene(employeeFormScene);
            //por defecto no podra redimensionarse
            employeeFormStage.setResizable(false);
            //cargamos el controlador de la ventana
            EmployeeFormController controller = (EmployeeFormController) employeeForm.getController();
            controller.initStage(root);
            controller.initStageModify();
            controller.empToModify(emp);
            controller = employeeForm.getController();
            controller.initStage(root);

            employeeFormStage.show();
            employeePane.getScene().getWindow().hide();
        } catch (IOException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este metodo borra el usuario seleccionado
     *
     * @param event
     */
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

                tblEmployees.getItems().remove(emp);
                tblEmployees.refresh();
                btnDelete.setDisable(true);
                btnModify.setDisable(true);

                tblEmployees.getSelectionModel().clearSelection();
                tblEmployees.refresh();
                
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION,
                    "¿Borrar la fila seleccionada?\n"
                    + "Esta operación no se puede deshacer.",
                    ButtonType.OK, ButtonType.CANCEL);
            }

        } catch (Exception ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este metodo busca empleados por metodo de filtrado y valor
     *
     * @param event
     */
    @FXML
    private void find(ActionEvent event) {
        boolean validFloat;
        lblError.setText("");
        if (tfValue.getText().equalsIgnoreCase("") && cmbFilter.getValue() != null) {
            if (!cmbFilter.getValue().toString().equals("Mostrar todos los empleados")) {
                lblError.setText("Debes de introducir un valor en campo value");
            }
        }

        if (cmbFilter.getValue().toString().equals("Salario")/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
                && !tfValue.getText().equalsIgnoreCase("")) {

            Pattern p = Pattern.compile("^(?:([0-9]{1,5}))((?:[.])(?:[0-9]{1,2}))?");
            Matcher m = p.matcher(tfValue.getText().trim());
            validFloat = m.matches();

            try {
                if (validFloat) {
                    ObservableList<Employee> empsS = FXCollections.observableArrayList(employeesManager.employeesBySalary(tfValue.getText()));
                    if (empsS.isEmpty()) {
                        lblError.setText("No se han encontrado empleados");
                    }
                    tblEmployees.setItems(empsS);
                } else {
                    lblError.setText("El valor del campo value no es valido. Introduce un numero entre 0 y 99999. Ej: 123.23"
                    );
                }
            } catch (Exception ex) {
                Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (cmbFilter.getValue().toString().equals("Nombre")/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 
                && !tfValue.getText().equalsIgnoreCase("")) {
            try {
                ObservableList<Employee> empsN = FXCollections.observableArrayList(EmployeeManagerFactory.createEmployeeManager("REST_WEB_CLIENT").employeesByName(tfValue.getText()));
                if (empsN.isEmpty()) {
                    lblError.setText("No se han encontrado empleados:");
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

        tfValue.setText("");
    }

    /**
     * Este metodo controla la seleccion de filas de la tabla. En caso de
     * seleccionar una, se habilitaran los botones btnDelete y btnModify
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
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

    /**
     * Este metodo carga la interfaz de logica EmployeeManager
     *
     * @param employeesManager
     */
    public void setEmployeeManager(EmployeeManager employeesManager) {
        this.employeesManager = employeesManager;
    }

    /**
     * Este metodo controla la longitud maxima(255) del tfValue
     *
     * @param observable
     * @param oldValue
     * @param newValue
     */
    private void tfValueTextChanged(ObservableValue observable, String oldValue, String newValue) {

        if (newValue.length() != oldValue.length()
                && newValue.length() > 255) {
            lblError.setText("La longitud maxima es de 255");
            tfValue.setText(oldValue);
        }
        if (newValue.length() != oldValue.length()
                && newValue.length() < 255
                && lblError.getText().equalsIgnoreCase("La longitud maxima es de 255")) {
            lblError.setText("");
        }
    }

    @FXML
    private void print(ActionEvent event) {
        try {
            LOG.info("Se va a imprimir un informe");
            JasperReport report
                    = JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/report/EmployeeReport.jrxml"));

            JRBeanCollectionDataSource dataItems
                    = new JRBeanCollectionDataSource((Collection<Employee>) this.tblEmployees.getItems());

            Map<String, Object> parameters = new HashMap<>();

            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);

            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
