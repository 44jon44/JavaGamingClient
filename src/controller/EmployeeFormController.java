/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.EmployeeManager;
import exception.BusinessLogicException;
import factories.EmployeeManagerFactory;
import factories.UserManagerFactory;
import java.awt.AWTEventMulticaster;
import java.io.IOException;
import static java.lang.Float.parseFloat;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.naming.OperationNotSupportedException;
import model.UserPrivilege;
import model.UserStatus;
import transferObjects.Employee;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class EmployeeFormController implements Initializable {

    ZoneId defaultZoneId = ZoneId.systemDefault();
    private EmployeeManager employeesManager;

    private Employee employeeModify;

    //booleanos que indican si los campos son válidos tras las comprobaciones oportunas
    private boolean tfNameIsValid = false;
    private boolean tfEmailIsValid = false;
    private boolean tfLoginIsValid = false;
    private boolean dpHiringDateIsValid = false;
    private boolean tfSalaryIsValid = false;
    private boolean exists = false;
    boolean loginChanged = false;
    //Logger del controlador de la ventana "ViewSignIn"
    private static final Logger LOG = Logger.getLogger(EmployeeFormController.class.getName());
    /**
     * Create user data button.
     */
    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;
    @FXML
    private Label lblErrorName;
    @FXML
    private Label lblErrorEmail;
    @FXML
    private Label lblErrorLogin;
    @FXML
    private Label lblErrorHiringDate;
    @FXML
    private Label lblErrorSalary;
    @FXML
    private Hyperlink hpReturn;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfLogin;
    @FXML
    private DatePicker dpHiringDate;
    @FXML
    private TextField tfSalary;
    @FXML
    private Pane employeeFormPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    void initStage(Parent root) {
        lblErrorName.setText("");
        lblErrorEmail.setText("");
        lblErrorLogin.setText("");
        lblErrorHiringDate.setText("");
        lblErrorSalary.setText("");

        tfName.focusedProperty().addListener(this::tfNameFocusChanged);
        tfName.textProperty().addListener(this::tfNameTextChanged);
        tfEmail.focusedProperty().addListener(this::tfEmailFocusChanged);
        tfEmail.textProperty().addListener(this::tfEmailTextChanged);
        tfLogin.focusedProperty().addListener(this::tfLoginFocusChanged);
        tfLogin.textProperty().addListener(this::tfLoginTextChanged);
        tfSalary.focusedProperty().addListener(this::tfSalaryFocusChanged);
        tfSalary.textProperty().addListener(this::tfSalaryTextChanged);

        dpHiringDate.focusedProperty().addListener(this::dpHiringDateFocusChanged);

        btnDelete.setOnAction(this::clean);

        hpReturn.setOnAction(this::hpClicked);

        dpHiringDate.setEditable(false);

        tfName.requestFocus();

    }

    void initStageAdd() {
        tfSalary.setText("1000");
        btnSave.setOnAction(this::save);
    }

    void initStageModify() {
        btnSave.setOnAction(this::modify);

    }

    @FXML
    private void modify(ActionEvent event) {
        boolean exist = false;
        try {
            if (tfLogin.getText().length() != 0 && loginChanged) {

                UserManagerFactory.createUserManager("REST_WEB_CLIENT").checkLoginExists(tfLogin.getText());
            }
        } catch (BusinessLogicException ex) {
            exist = true;
            System.out.println("Existe Pajin");
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "El login ya existe",
                    ButtonType.OK);
            alert.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(EmployeeFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!exist) {
            try {

                employeeModify.setFullName(tfName.getText().trim());
                employeeModify.setLogin(tfLogin.getText().trim());
                employeeModify.setEmail(tfEmail.getText().trim());
                employeeModify.setHiringDate(Date.from(dpHiringDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                employeeModify.setSalary(tfSalary.getText().trim());

                EmployeeManagerFactory.createEmployeeManager("REST_WEB_CLIENT").updateEmployee(employeeModify);
            } catch (OperationNotSupportedException ex) {
                Logger.getLogger(EmployeeFormController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(EmployeeFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @FXML
    private void tfNameFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {

        LOG.info("Dentro de tfUser FocusChanged");
        if (oldValue) {//foco perdido 
            tfNameIsValid = validateTfName(tfName.getText());
            if (!tfNameIsValid) {
                showlblErrorNameMessages(tfName.getText());
            }
            if (tfName.getText().trim().length() > 255) {
                lblErrorName.setText("Longitud maxima de 255 caracteres");
            }

        } else if (newValue) {//foco ganado

        }
    }

    @FXML
    private void tfEmailFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {

        LOG.info("Dentro de tfEmail FocusChanged");
        if (oldValue) {//foco perdido 
            tfEmailIsValid = validateTfEmail(tfEmail.getText());
            if (!tfEmailIsValid) {
                tfEmail.setStyle("-fx-text-inner-color: red;");
                showlblErrorEmailMessages(tfEmail.getText());
            }
            if (tfEmail.getText().trim().length() > 255) {
                lblErrorEmail.setText("Longitud maxima de 255 caracteres");
            }
        } else if (newValue) {//foco ganado

        }
    }

    @FXML
    private void dpHiringDateFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {

        LOG.info("Dentro de dpHiringDate FocusChanged");
        if (oldValue) {//foco perdido 
            if (dpHiringDate.getValue() == null) {
                lblErrorHiringDate.setText("Campo obligatorio");
            } else {
                dpHiringDate.setStyle("-fx-text-inner-color: black;");
            }
        } else if (newValue) {//foco ganado

        }
    }

    @FXML
    private void tfLoginFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {

        LOG.info("Dentro de tfLoginFocusChanged");
        if (oldValue) {//foco perdido 
            tfLoginIsValid = validateTfLogin(tfLogin.getText());
            if (!tfLoginIsValid) {
                tfLogin.setStyle("-fx-text-inner-color: red;");
                showlblErrorLoginMessages(tfLogin.getText());
            }
        } else if (newValue) {//foco ganado

        }
    }

    @FXML
    private void tfSalaryFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {

        LOG.info("Dentro de tfUserFocusChanged");
        if (oldValue) {//foco perdido 
            if (!tfSalary.getText().equalsIgnoreCase("")) {

                tfSalaryIsValid = validateTfFSalary(tfSalary.getText());
                if (!tfSalaryIsValid) {
                    tfSalary.setStyle("-fx-text-inner-color: red;");
                    showlblErrorSalaryMessages(tfSalary.getText());
                }

            } else {
                tfSalary.setStyle("-fx-text-inner-color: red;");
                lblErrorSalary.setText("Campo obligatorio");
            }
        } else if (newValue) {//foco ganado

        }
    }

    @FXML
    private void tfNameTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (!newValue.equalsIgnoreCase(oldValue)) {
            lblErrorName.setText("");
            tfName.setStyle("-fx-text-inner-color: black;");
        }
    }

    @FXML
    private void tfEmailTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (!newValue.equalsIgnoreCase(oldValue)) {
            lblErrorEmail.setText("");
            tfEmail.setStyle("-fx-text-inner-color: black;");
        }
    }

    @FXML
    private void tfLoginTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (!newValue.equalsIgnoreCase(oldValue)) {
            lblErrorLogin.setText("");
            tfLogin.setStyle("-fx-text-inner-color: black;");
            loginChanged = true;
        }
    }

    @FXML
    private void tfSalaryTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (!newValue.equalsIgnoreCase(oldValue)) {
            lblErrorSalary.setText("");
            tfSalary.setStyle("-fx-text-inner-color: black;");
        }
    }

    @FXML
    private void save(ActionEvent event) {

        try {
            if (tfLogin.getText().length() != 0) {
                System.out.println("Dentro");
                UserManagerFactory.createUserManager("REST_WEB_CLIENT").checkLoginExists(tfLogin.getText());

            }
        } catch (BusinessLogicException ex) {
            exists = true;
            Alert alert = new Alert(Alert.AlertType.INFORMATION,
                    "El login ya existe",
                    ButtonType.OK);
            alert.showAndWait();
        } catch (Exception ex) {
            Logger.getLogger(EmployeeFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(exists);
        validateFields();
        if (true) {

            try {

                LocalDate localDate = dpHiringDate.getValue();
                Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
                System.out.println(date);

                Employee emp = new Employee();

                emp.setFullName(tfName.getText());
                emp.setEmail(tfEmail.getText());
                emp.setLogin(tfLogin.getText());
                emp.setHiringDate(date);
                emp.setPassword("56127fecb4c2c943ead237281290f7634513551a30a6c07af0e9c03668e7fb93");
                emp.setPrivilege(UserPrivilege.EMPLOYEE);
                emp.setStatus(UserStatus.ENABLED);
                emp.setSalary(tfSalary.getText());
                //Employee emp = new Employee(date, tfSalary.getText(), tfLogin.getText(), tfEmail.getText(), tfName.getText(), "56127fecb4c2c943ead237281290f7634513551a30a6c07af0e9c03668e7fb93");
                emp.setPrivilege(UserPrivilege.EMPLOYEE);
                emp.setStatus(UserStatus.ENABLED);

                EmployeeManagerFactory.createEmployeeManager("REST_WEB_CLIENT").createEmployee(emp);

            } catch (Exception ex) {
                Logger.getLogger(EmployeeFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private boolean fieldsInformed() {

        if (!tfName.getText().equalsIgnoreCase("")
                && !tfEmail.getText().equalsIgnoreCase("")
                && !tfLogin.getText().equalsIgnoreCase("")
                && dpHiringDate.getValue() != null
                && tfSalary.getText().equalsIgnoreCase("")) {
            return true;
        } else {
            showFieldErrors();
            return false;
        }
    }

    private boolean oneFieldInformed() {

        return !tfName.getText().equalsIgnoreCase("")
                || !tfEmail.getText().equalsIgnoreCase("")
                || !tfLogin.getText().equalsIgnoreCase("")
                || dpHiringDate.getValue() != null
                || !tfSalary.getText().equalsIgnoreCase("");
    }

    private boolean validFields() {
        return tfNameIsValid && tfEmailIsValid && tfLoginIsValid && tfSalaryIsValid;
    }

    private boolean validateTfName(String user) {
        return Pattern.matches("[a-zA-ZáéíóúÁÉÍÓÚ]{2,}[\\s[a-zA-ZáéíóúÁÉÍÓÚ]{2,}]*", user);
    }

    private boolean validateTfEmail(String email) {
        return Pattern.matches("\\b[a-zA-Z0-9_+-]+(?:.[a-zA-Z0-9_+-]+)*@(?:[a-zA-Z0-9-]+.)+[a-zA-Z]{2,6}\\b", email);
    }

    private boolean validateTfLogin(String login) {
        return Pattern.matches("[a-zA-Z_][a-zA-Z0-9_]{1,254}", login);
    }

    private boolean validateTfFSalary(String salary) {
        return Pattern.matches("^(?:([0-9]{4,6}))((?:[.])(?:[0-9]{1,2}))?", salary);
    }

    private void validateFields() {
        tfEmailIsValid = validateTfEmail(tfEmail.getText());
        tfNameIsValid = validateTfName(tfName.getText());
        tfLoginIsValid = validateTfLogin(tfLogin.getText());
        tfSalaryIsValid = validateTfFSalary(tfSalary.getText());
        if (dpHiringDate.getValue() != null) {
            dpHiringDateIsValid = true;
        }

    }

    private void showFieldErrors() {
        if (!tfNameIsValid) {
            showlblErrorNameMessages(tfName.getText());
        }
        if (!tfEmailIsValid) {
            showlblErrorEmailMessages(tfEmail.getText());
        }
        if (!tfLoginIsValid) {
            showlblErrorLoginMessages(tfLogin.getText());
        }
        if (!tfSalaryIsValid) {
            showlblErrorSalaryMessages(tfSalary.getText());
        }

    }

    private void showlblErrorNameMessages(String name) {
        switch (name.trim().length()) {
            case 0:
                lblErrorName.setText("Campo obligatorio");
                break;
            case 1:
                lblErrorName.setText("Longitud minima de 2 caracteres");
                break;
            default:
                lblErrorName.setText("El nombre solo puede contener\n letras y espacios");
                break;
        }
    }

    /**
     * Este metodo muestra los posibles errores de el campo tfEmail en
     * lblErrorEmail
     *
     * @param email
     */
    private void showlblErrorEmailMessages(String email) {
        if (email.trim().length() == 0) {
            lblErrorEmail.setText("Campo obligatorio");
        } else {
            lblErrorEmail.setText("Email invalido");
        }
        if (email.trim().length() > 255) {
            lblErrorEmail.setText("Longitud maxima de 255 caracteres");
        }
    }

    /**
     * Este metodo muestra los posibles errores de el campo tfLogin en
     * lblErrorLogin
     *
     * @param login
     */
    private void showlblErrorLoginMessages(String login) {

        char c = 0;

        if (login.trim().length() >= 1) {
            c = login.charAt(0);
        }
        if (login.contains(" ")) {
            lblErrorLogin.setText("No puede contener espacios");
        } else if (login.trim().length() == 0) {
            lblErrorLogin.setText("Campo obligatorio");
        } else if (login.trim().length() == 1) {
            lblErrorLogin.setText("Longitud mínima 2");
        } else if (Character.isDigit(c)) {
            lblErrorLogin.setText("El login no debe de empezar  \n por un numero");
        } else if (login.length() > 255) {
            lblErrorLogin.setText("El login no debe superar \n los 255 digitos");
        } else {
            lblErrorLogin.setText("login inválido solo puede contener \n "
                    + "numeros y letras");
        }
    }

    /**
     * Este metodo muestra los posibles errores de el campo tfSalary en
     * lblErrorSalary
     *
     * @param salary
     */
    private void showlblErrorSalaryMessages(String salary) {
        boolean validDecimal = true;

        try {
            float val = Float.parseFloat(salary.replace(',', '.'));
        } catch (NumberFormatException e) {
            lblErrorSalary.setText("El formato correcto \n es el de numero decimal");
            validDecimal = false;
        }
        if (validDecimal) {
            if (Float.parseFloat(salary) < 1000) {
                lblErrorSalary.setText("El salario debe de ser \n mayor o igual a 1000");
            }
            if (Float.parseFloat(salary) > 1000000) {
                lblErrorSalary.setText("El salario debe de ser \n menor que 1000000");
            }
        }

    }

    /**
     * Este metodo carga los datos del empleado a modificar
     *
     * @param emp
     */
    public void empToModify(Employee emp) {

        tfName.setText(emp.getFullName());
        tfEmail.setText(emp.getEmail());
        tfLogin.setText(emp.getLogin());
        dpHiringDate.setValue(emp.getHiringDate().toInstant()
                .atZone(ZoneId.systemDefault()).toLocalDate());
        tfSalary.setText(emp.getSalary().toString());

        employeeModify = emp;

    }

    /**
     * Este metodo vacia todos los campos si se confirma en el alert
     *
     * @param event
     */
    @FXML
    private void clean(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Se vaciarán los campos\n"
                + "Esta operación no se puede deshacer.",
                ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            tfName.setText("");
            tfEmail.setText("");
            tfLogin.setText("");
            tfSalary.setText("");
            dpHiringDate.setValue(null);
        } else {
            alert.close();
        }
    }

    /**
     * Este metodo controla las distintas situcianes que pueden suceder al
     * pulsar el hiperlink hpReturn
     *
     * @param event
     */
    @FXML
    private void hpClicked(ActionEvent event) {
        if (oneFieldInformed()) {
            //Informar que se descartaran los cambios
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Se descartarán los cambios\n"
                    + "Esta operación no se puede deshacer.",
                    ButtonType.OK, ButtonType.CANCEL);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
                    FXMLLoader employee = new FXMLLoader(getClass().getResource("/view/employee.fxml"));
                    Parent root;
                    root = (Parent) employee.load();
                    employeeFormPane.getScene().getWindow().hide();
                    //Creamos una nueva escena para la ventana SignIn
                    //cargamos el controlador de la ventana
                    EmployeeController controller = employee.getController();
                    controller.setStage(new Stage());
                    controller.setEmployeeManager(employeesManager);
                    controller.initStage(root);
                } catch (IOException ex) {
                    Logger.getLogger(EmployeeFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                alert.close();
            }
        } else {
            try {
                //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
                FXMLLoader employee = new FXMLLoader(getClass().getResource("/view/employee.fxml"));
                Parent root;
                root = (Parent) employee.load();
                employeeFormPane.getScene().getWindow().hide();
                //Creamos una nueva escena para la ventana SignIn
                //cargamos el controlador de la ventana
                EmployeeController controller = employee.getController();
                controller.setStage(new Stage());
                controller.setEmployeeManager(employeesManager);
                controller.initStage(root);
            } catch (IOException ex) {
                Logger.getLogger(EmployeeFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
