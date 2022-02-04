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
import exception.*;

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
        LOG.info("InitStage de la ventana EmployeeController...");
        try {
            //controladores de los eventos ActionEvent de los botones
            btnAdd.setOnAction(this::create);
            btnDelete.setOnAction(this::delete);
            btnModify.setOnAction(this::modify);
            btnFind.setOnAction(this::find);
            btnReport.setOnAction(this::print);
            //lblError se inicializa vacio
            lblError.setText("");
            //Se enfoca el tfValue
            tfValue.requestFocus();
            //Metodo que controla cuando el texto de tfValue cambia
            tfValue.textProperty().addListener(this::tfValueTextChanged);
            //Se deshabilitan los botones btnDelete y bntModify
            btnDelete.setDisable(true);
            btnModify.setDisable(true);
            //Se alinean los valores de la tabla
            tcSalary.setStyle("-fx-alignment: CENTER-RIGHT;");
            tcName.setStyle("-fx-alignment: CENTER;");
            tcEmail.setStyle("-fx-alignment: CENTER;");
            tcLogin.setStyle("-fx-alignment: CENTER;");
            tcHiringDate.setStyle("-fx-alignment: CENTER;");
            //Se deselecciona fila de la tabla en caso de que haya alguna 
            //selecionada
            tblEmployees.getSelectionModel().clearSelection();
            tblEmployees.refresh();
            //Se cargan datos en la tabla
            ObservableList<Employee> emps = FXCollections.observableArrayList(EmployeeManagerFactory.createEmployeeManager("REST_WEB_CLIENT").getAllEmployees());
            if (emps.isEmpty()) {
                //En caso de que no se encuentre nada
                lblError.setText("No se han encontrado empleados");
            }
            //Se cargan los datos en la tabla
            tblEmployees.setItems(emps);

            //Cargamos los metodos de filtrado en la combo box
            ObservableList<String> observableList = FXCollections.observableList(list);
            observableList.add("Nombre");
            observableList.add("Salario");
            observableList.add("Mostrar todos los empleados");
            //Se cargan las opciones en la comboBox
            cmbFilter.setItems(observableList);
            cmbFilter.getSelectionModel().selectFirst();
            //Se cargan las propiedades de las columnas
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
            //Se controla cuando se cambia la seleccion de una fila
            tblEmployees.getSelectionModel().selectedItemProperty().addListener(this::handleUsersTableSelectionChanged);

        } catch (Exception ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            //Mostramos un error
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Error abriendo la ventana employee");
            errorAlert.showAndWait();
        }

    }

    /**
     * Metodo initStage en caso de que se acceda desde signIn
     *
     * @param root
     */
    public void initStage(Parent root) {
        LOG.info("InitStage de la ventana EmployeeController...");
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
            //Se enfoca en el tfValue
            tfValue.requestFocus();
            tfValue.textProperty().addListener(this::tfValueTextChanged);
            //Se deshabilitan los botones btnDelete y bntModify
            btnDelete.setDisable(true);
            btnModify.setDisable(true);
            //Se alinean los valores de la tabla
            tcSalary.setStyle("-fx-alignment: CENTER-RIGHT;");
            tcName.setStyle("-fx-alignment: CENTER;");
            tcEmail.setStyle("-fx-alignment: CENTER;");
            tcLogin.setStyle("-fx-alignment: CENTER;");
            tcHiringDate.setStyle("-fx-alignment: CENTER;");
            //Se deselecciona fila de la tabla en caso de que haya alguna 
            //selecionada
            tblEmployees.getSelectionModel().clearSelection();
            //Se refresca la tabla
            tblEmployees.refresh();
            //Se buscan empleados en la capa de logica
            ObservableList<Employee> emps = FXCollections.observableArrayList(EmployeeManagerFactory.createEmployeeManager("REST_WEB_CLIENT").getAllEmployees());
            if (emps.isEmpty()) {
                //Si no se encuentra ningun empleado
                lblError.setText("No se han encontrado empleados");
            }
            //Se cargan los elementos en la tabla
            tblEmployees.setItems(emps);

            //Cargamos los metodos de filtrado en la combo box
            ObservableList<String> observableList = FXCollections.observableList(list);
            observableList.add("Nombre");
            observableList.add("Salario");
            observableList.add("Mostrar todos los empleados");
            //Se cargan las opciones en la comboBox
            cmbFilter.setItems(observableList);
            //Se selecciona la primera opcion
            cmbFilter.getSelectionModel().selectFirst();
            //Se cargan los PropertyValueFactory de las columnas de la tabla
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
            //Controla cuando se cambia la seleccion de fila de la tabla
            tblEmployees.getSelectionModel().selectedItemProperty().addListener(this::handleUsersTableSelectionChanged);
            //Se muestra el stage 
            stage.showAndWait();
        } catch (Exception ex) {
            //En caso de error al abrir la ventan employee
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Error abriendo la ventana employee");
            errorAlert.showAndWait();
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
        } catch (Exception ex) {
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

            //getResource tienes que anadir la ruta de la ventana que quieres iniciar.
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
        LOG.info("Borrando...");
        try {
            Employee emp;
            //se recoge el Employee de la tabla en una variable
            emp = ((Employee) tblEmployees.getSelectionModel().getSelectedItem());
            //Se pide la confirmacion de borrado al usuario mediante un alert
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "¿Borrar la fila seleccionada?\n"
                    + "Esta operación no se puede deshacer.",
                    ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                //En caso de confirmacion, se envia el Employee a la capa de logica
                EmployeeManagerFactory.createEmployeeManager("REST_WEB_CLIENT").deleteEmployee(emp);
                //Se elimina el Employee de la tabala
                tblEmployees.getItems().remove(emp);
                tblEmployees.refresh();
                //Los btnDelete y btnModify se deshabilitan
                btnDelete.setDisable(true);
                btnModify.setDisable(true);
                //Se quita la seleccion de la tabla
                tblEmployees.getSelectionModel().clearSelection();
                tblEmployees.refresh();

                
            }

        } catch (Exception ex) {
            //En caso de error se informa al usurio
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Error borrando empleado");
            errorAlert.showAndWait();
        }
    }

    /**
     * Este metodo busca empleados por metodo de filtrado y valor Opciones de
     * filtrado: salario, nombre y mostrar todos los empleados
     *
     * @param event
     */
    @FXML
    private void find(ActionEvent event) {
        LOG.info("Buscando...");
        boolean validFloat;
        try {
            //Al label error se le cambia el texto a vacio
            lblError.setText("");
            if (tfValue.getText().equalsIgnoreCase("") && cmbFilter.getValue() != null) {
                if (!cmbFilter.getValue().toString().equals("Mostrar todos los empleados")) {
                    //En caso de que no se haya seleccionado Mostrar todos los empleados 
                    //y el tfValue no tenga texto
                    lblError.setText("Debes de introducir un valor en campo value");
                }
            }

            if (cmbFilter.getValue().toString().equals("Salario")
                    && !tfValue.getText().equalsIgnoreCase("")) {
                //En caso de que no se haya seleccionado Salario
                //y el tfValue este informado
                Pattern p = Pattern.compile("^(?:([0-9]{1,5}))((?:[.])(?:[0-9]{1,2}))?");
                Matcher m = p.matcher(tfValue.getText().trim());
                validFloat = m.matches();

                if (validFloat) {
                    //En caso de que el salario sea valido
                    ObservableList<Employee> empsS = FXCollections.observableArrayList(employeesManager.employeesBySalary(tfValue.getText()));
                    if (empsS.size()==0) {
                        //Si no se ha encontrado ninguno se informa al usuario 
                        lblError.setText("No se han encontrado empleados");
                    }
                    //Si hay resultados, se cargan los datos en la tabla
                    tblEmployees.setItems(empsS);
                    
                } else {
                    //Si no es un campo valido, se informa al usuario
                    lblError.setText("El valor del campo value no es valido. Introduce un numero entre 0 y 99999. Ej: 123.23"
                    );
                }

            }

            if (cmbFilter.getValue().toString().equals("Nombre")
                    && !tfValue.getText().equalsIgnoreCase("")) {
                //Se buscan los empleados por nombre en caso de que se haya seleccionado nombre
                // y el campo tfValue este informado
                ObservableList<Employee> empsN = FXCollections.observableArrayList(EmployeeManagerFactory.createEmployeeManager("REST_WEB_CLIENT").employeesByName(tfValue.getText()));
                if (empsN.isEmpty()) {
                    //Si no se han encontrados empleados se informa al usuario
                    lblError.setText("No se han encontrado empleados:");
                }
                //Se cargan los datos en la tabla
                tblEmployees.setItems(empsN);

            }
            if (cmbFilter.getValue().toString().equals("Mostrar todos los empleados")
                    && tfValue.getText().equalsIgnoreCase("")) {
                 //Se buscan todos los empleados  en caso de que se haya 
                 //seleccionado mostar todos los empleados
                // y el campo tfValue  no este informado
                ObservableList<Employee> emps = FXCollections.observableArrayList(EmployeeManagerFactory.createEmployeeManager("REST_WEB_CLIENT").getAllEmployees());
                if (emps.isEmpty()) {
                    //en caso de que nos e hayan encontrado empleados
                    //se informa al usuario
                    lblError.setText("No se han encontrado empleados");
                }
                 //Se cargan los datos en la tabla
                tblEmployees.setItems(emps);
               
            }
            if (cmbFilter.getValue().toString().equals("Mostrar todos los empleados")
                    && !tfValue.getText().equalsIgnoreCase("")) {
                //En caso de que se haya 
                 //seleccionado mostar todos los empleados
                // y el campo tfValue este informado
                //se informara al usuario
                lblError.setText("El campo value debe de estar vacio");
            }

            tfValue.setText("");

            
        } catch (Exception ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);

            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Error buscando empleado");
            errorAlert.showAndWait();
        }
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
        //Si se selecciona una fila, se habiltan btnModify y btnDelete 
        if (newValue != null) {

            btnDelete.setDisable(false);
            btnModify.setDisable(false);
        } //Si se deselecciona una fila, se deshabiltan btnModify y btnDelete
        else {
            btnDelete.setDisable(true);
            btnModify.setDisable(true);
        }

    }

    /**
     * Este metodo carga la interfaz de logica EmployeesManager
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

    /**
     * Controlador del ActionEvent del boton print. Muestra un Jframe que
     * contiene un informe. Esto permite imprimir el informe
     *
     * @param event El objeto de activacion del ActionEvent
     */
    @FXML
    private void print(ActionEvent event) {
        try {
            LOG.info("Se va a imprimir un informe");
            JasperReport report
                    = JasperCompileManager.compileReport(getClass()
                            .getResourceAsStream("/report/EmployeeReport.jrxml"));
            //Datos para el informe: una coleccion de empleados basado en una
            //implementacion de JRDataSource 

            JRBeanCollectionDataSource dataItems
                    = new JRBeanCollectionDataSource((Collection<Employee>) this.tblEmployees.getItems());
            //mapa que se pasara al informe
            Map<String, Object> parameters = new HashMap<>();
            //Llena el informe con datos 
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, dataItems);
            //Crea la venta del informe. El valor false hace que la ventana
            //anterior no se cierre
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            jasperViewer.setVisible(true);
        } catch (JRException ex) {
            Logger.getLogger(EmployeeController.class.getName()).log(Level.SEVERE, null, ex);
            //Si hay un error se informa al usuario
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Error generando el informe");
            errorAlert.showAndWait();
        }
    }
}
