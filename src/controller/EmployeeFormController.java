/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import businessLogic.EmployeeManager;
import static java.lang.Float.parseFloat;
import java.net.URL;
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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import transferObjects.Employee;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class EmployeeFormController implements Initializable {

    ZoneId defaultZoneId = ZoneId.systemDefault();
    private EmployeeManager employeesManager;

    //booleanos que indican si los campos son válidos tras las comprobaciones oportunas
    private boolean tfNameIsValid = false;
    private boolean tfEmailIsValid = false;
    private boolean tfLoginIsValid = false;
    private boolean dpHirngDateIsValid = false;
    private boolean tfSalaryIsValid = false;
    //Logger del controlador de la ventana "ViewSignIn"
    private static final Logger LOG = Logger.getLogger(EmployeeFormController.class.getName());
    /**
     * Create user data button.
     */
    @FXML
    private Button btnSave;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void initStageAdd(Parent root) {
        btnSave.setOnAction(this::add);
    }

    public void initStageModify() {
        btnSave.setOnAction(this::modify);
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
        tfLogin.focusedProperty().addListener(this::tfEmailFocusChanged);
        tfLogin.textProperty().addListener(this::tfLoginTextChanged);
        tfSalary.focusedProperty().addListener(this::tfSalaryFocusChanged);
        tfSalary.textProperty().addListener(this::tfSalaryTextChanged);

        hpReturn.setOnAction(this::hpClicked);

    }

    @FXML
    private void tfNameFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {

        LOG.info("Dentro de tfUser FocusChanged");
        if (oldValue) {//foco perdido 
            tfNameIsValid = validateTfName(tfName.getText());
            if (!tfNameIsValid) {
                showlblErrorUserMessages(tfName.getText());
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
            tfSalaryIsValid = validateTfFSalary(tfSalary.getText());
            if (!tfSalaryIsValid) {
                tfSalary.setStyle("-fx-text-inner-color: red;");
                showlblErrorLoginMessages(tfSalary.getText());
            }
        } else if (newValue) {//foco ganado

        }
    }

    @FXML
    private void tfNameTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (newValue.length() != oldValue.length()) {
            lblErrorName.setText("");
            tfEmail.setStyle("-fx-text-inner-color: black;");
        }
    }

    @FXML
    private void tfEmailTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (newValue.length() != oldValue.length()) {
            lblErrorEmail.setText("");
            tfEmail.setStyle("-fx-text-inner-color: black;");
        }
    }

    @FXML
    private void tfLoginTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (newValue.length() != oldValue.length()) {
            lblErrorLogin.setText("");
            tfEmail.setStyle("-fx-text-inner-color: black;");
        }
    }

    @FXML
    private void tfSalaryTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (newValue.length() != oldValue.length()) {
            lblErrorSalary.setText("");
            tfEmail.setStyle("-fx-text-inner-color: black;");
        }
    }

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
                
            }else{
                alert.close();
            }
        } else {
            //Abrir ventana employee
        }
    }

    @FXML
    private void add(ActionEvent event) {

        try {
            employeesManager.isLoginExisting(tfName.getText());
        } catch (Exception ex) {
            Logger.getLogger(EmployeeFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (fieldsInformed()) {
            try {
                Employee emp = new Employee();

                emp.setName(tfName.getText());
                emp.setEmail(tfEmail.getText());
                emp.setLogin(tfLogin.getText());
                Date date = Date.from(dpHiringDate.getValue().atStartOfDay(defaultZoneId).toInstant());
                emp.setHiringDate(date);
                emp.setSalary(parseFloat(tfSalary.getText()));

                employeesManager.createEmployee(emp);

            } catch (Exception ex) {
                Logger.getLogger(EmployeeFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            //Abrir ventana employee
        }
    }

    @FXML
    private void modify(ActionEvent event) {
        if (fieldsInformed()) {
            //Informar que se descartaran los cambios
        } else {
            //Abrir ventana employee
        }
    }

    private boolean fieldsInformed() {

        if (!tfName.getText().equalsIgnoreCase("")
                && !tfEmail.getText().equalsIgnoreCase("")
                && !tfLogin.getText().equalsIgnoreCase("")
                && dpHiringDate.getValue() != null
                && !tfSalary.getText().equalsIgnoreCase("")) {
            return true;
        } else {
            return false;
        }
    }
    
    private boolean oneFieldInformed() {

        if (!tfName.getText().equalsIgnoreCase("")
                || !tfEmail.getText().equalsIgnoreCase("")
                || !tfLogin.getText().equalsIgnoreCase("")
                || dpHiringDate.getValue() != null
                || !tfSalary.getText().equalsIgnoreCase("")) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validFields() {
        return tfNameIsValid && tfEmailIsValid && tfLoginIsValid && dpHirngDateIsValid && tfSalaryIsValid;

    }

    private boolean validateTfName(String user) {
        return Pattern.matches("\\b[a-zA-Z][a-zA-Z0-9]+\\b", user);
    }

    private boolean validateTfEmail(String email) {
        return Pattern.matches("\\b[a-zA-Z0-9_+-]+(?:.[a-zA-Z0-9_+-]+)*@(?:[a-zA-Z0-9-]+.)+[a-zA-Z]{2,6}\\b", email);
    }

    private boolean validateTfLogin(String fullName) {
        return Pattern.matches("\\b\\p{L}+[\\p{L}\\p{Z}\\p{P}]{0,}", fullName);
    }

    private boolean validateTfFSalary(String fullName) {
        return Pattern.matches("[+-]?([0-9]*[.])?[0-9]+", fullName);
    }

    private boolean validateHiringDate() {
        return false;
    }

    private void showFieldErrors() {
        if (!tfNameIsValid) {
            showlblErrorUserMessages(tfName.getText());
        }
        if (!tfEmailIsValid) {
            showlblErrorEmailMessages(tfEmail.getText());
        }
        if (!tfLoginIsValid) {
            showlblErrorLoginMessages(tfLogin.getText());
        }
        if (!dpHirngDateIsValid) {
            //showlblErrorLoginMessages(tfLogin.getText());
        }
        if (!tfSalaryIsValid) {
            showlblErrorSalaryMessages(Float.parseFloat(tfSalary.getText()));
        }

    }

    private void showlblErrorUserMessages(String name) {
        tfName.setStyle("-fx-text-inner-color: red;");
        if (name.contains(" ")) {
            lblErrorName.setText("No puede contener espacios");
        } else if (name.trim().length() == 0) {
            lblErrorName.setText("Campo obligatorio");
        } else if (name.trim().length() == 1) {
            lblErrorName.setText("Longitud mínima 2");
        } else {
            lblErrorName.setText("Usuario inválido");
        }
    }

    private void showlblErrorEmailMessages(String email) {
        if (email.trim().length() == 0) {
            lblErrorEmail.setText("Campo obligatorio");
        } else {
            lblErrorEmail.setText("Email inválido");
        }
    }

    private void showlblErrorLoginMessages(String login) {
        if (login.trim().length() == 0) {
            lblErrorLogin.setText("Campo obligatorio");
        } else {
            lblErrorLogin.setText("Nombre inválido");
        }
    }

    private void showlblErrorSalaryMessages(Float salary) {
        if (salary < 1000) {
            lblErrorLogin.setText("El salario debe de ser mayor o igual a 1000");
        } else {
            lblErrorLogin.setText("Introduce un numero decimal");
        }
    }

    private void showlblErrorHiringDateMessages(Date hiringDate) {

    }
}
