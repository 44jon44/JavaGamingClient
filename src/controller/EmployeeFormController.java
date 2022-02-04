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
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.UserPrivilege;
import model.UserStatus;
import transferObjects.Employee;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class EmployeeFormController {

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
    //booleano que indica si el campo Login ha cambiado
    boolean loginChanged = false;
    //Logger del controlador de la ventana "ViewSignIn"
    private static final Logger LOG = Logger.getLogger(EmployeeFormController.class.getName());
    /**
     * Boton crear empleado
     */
    @FXML
    private Button btnSave;
    /**
     * Boton borrar empleado
     */
    @FXML
    private Button btnDelete;
    /**
     * label de error de nombre
     */
    @FXML
    private Label lblErrorName;
    /**
     * label de error de email
     */
    @FXML
    private Label lblErrorEmail;
    /**
     * label de error de login
     */
    @FXML
    private Label lblErrorLogin;
    /**
     * label de error de fechaContratacion
     */
    @FXML
    private Label lblErrorHiringDate;
    /**
     * label de error de salario
     */
    @FXML
    private Label lblErrorSalary;
    /**
     * hiperlink que vuelve a la ventana employee.fxml
     */
    @FXML
    private Hyperlink hpReturn;
    /**
     * textField del campo nombre
     */
    @FXML
    private TextField tfName;
    /**
     * textField del campo email
     */
    @FXML
    private TextField tfEmail;
    /**
     * textField del campo login
     */
    @FXML
    private TextField tfLogin;
    /**
     * textField del campo fechaContratacion
     */
    @FXML
    private DatePicker dpHiringDate;
    /**
     * textField del campo salario
     */
    @FXML
    private TextField tfSalary;
    /**
     * Pane de la escena e
     */
    @FXML
    private Pane employeeFormPane;
    private String prevLogin;

    /**
     * Metodo para inicializar el stage EmployeeFormController Stage
     *
     * @param root El objeto padre representado el nodo raiz de la vista grafica
     */
    void initStage(Parent root) {
        //Inicializamos los label de error sin texto
        lblErrorName.setText("");
        lblErrorEmail.setText("");
        lblErrorLogin.setText("");
        lblErrorHiringDate.setText("");
        lblErrorSalary.setText("");
        //Metodos de control del cambio de texto y cambio de foco
        tfName.focusedProperty().addListener(this::tfNameFocusChanged);
        tfName.textProperty().addListener(this::tfNameTextChanged);
        tfEmail.focusedProperty().addListener(this::tfEmailFocusChanged);
        tfEmail.textProperty().addListener(this::tfEmailTextChanged);
        tfLogin.focusedProperty().addListener(this::tfLoginFocusChanged);
        tfLogin.textProperty().addListener(this::tfLoginTextChanged);
        tfSalary.focusedProperty().addListener(this::tfSalaryFocusChanged);
        tfSalary.textProperty().addListener(this::tfSalaryTextChanged);
        dpHiringDate.focusedProperty().addListener(this::dpHiringDateFocusChanged);
        //Controla el ActionEvent del boton que borra los datos
        btnDelete.setOnAction(this::clean);
        //Controla el ActionEvent del boton que vuelve a la ventana employee
        hpReturn.setOnAction(this::hpClicked);
        //No se permite introducir texto a mano en el datePicker
        dpHiringDate.setEditable(false);

        tfName.requestFocus();

    }

    /**
     * Inicializa la ventana cuando anteriormente se ha seleccionado anadir
     */
    void initStageAdd() {
        tfSalary.setText("1000");
        btnSave.setOnAction(this::save);
    }

    /**
     * Inicializa la ventana cuando anteriormente se ha seleccionado anadir
     */
    void initStageModify() {
        btnSave.setOnAction(this::modify);

    }

    /**
     * Controlador del evento accion del boton modificar. Valida los datos y los
     * envia a la capa de logica
     *
     * @param event El objecto ActionEvent del evento
     */
    @FXML
    private void modify(ActionEvent event) {
        LOG.info("Modificando...");
        boolean exist = false;
        try {
            //Recuperamos el empleado por id
            try {
                //En caso de que el tfLogin no sea null se busca si existe
                if (tfLogin.getText().length() != 0) {
                    System.out.println("Dentro");
                    if(!tfLogin.getText().equals(employeeModify.getLogin())){
                        UserManagerFactory.createUserManager("REST_WEB_CLIENT").checkLoginExists(tfLogin.getText());
                    }else{
                        employeeModify.setLogin(tfLogin.getText());
                    }
                    
                }
            } catch (BusinessLogicException ex) {
                //Si se encuentra se cambia el valor de exist a false y se muestra 
                //un alert de informacion
                exist = true;

                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "El login ya existe",
                        ButtonType.OK);
                alert.showAndWait();
            } catch (Exception ex) {
                Logger.getLogger(EmployeeFormController.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Se comprueba que no exista y los campos sean validos
            validateFields();
            if (!exist && validFields()) {

                try {
                    //Se crea el empleadoAModificar
                    employeeModify.setFullName(tfName.getText().trim());
                    employeeModify.setLogin(tfLogin.getText().trim());
                    employeeModify.setEmail(tfEmail.getText().trim());
                    employeeModify.setHiringDate(Date.from(dpHiringDate.getValue().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
                    employeeModify.setSalary(tfSalary.getText().trim());
                    //Se envia al empleado a modificar a la capa de logica
                    EmployeeManagerFactory.createEmployeeManager("REST_WEB_CLIENT").updateEmployee(employeeModify);
                    //Se le informa al usuario de que se ha creado corrrectamente
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            "El usuario se ha modificado correctamente",
                            ButtonType.OK);
                    alert.showAndWait();

                } catch (Exception ex) {
                    Logger.getLogger(EmployeeFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!exists && !validFields()) {
                //En caso de que no exisata y haya algun campo incorrecto,
                //se informa al usuario
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Algun campo no es correcto",
                        ButtonType.OK);
                alert.showAndWait();
            }

        } catch (Exception ex) {
            Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
            //En caso de error se informa al usuario
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Error abriendo modificando empleado");
            errorAlert.showAndWait();
        }
    }

    /**
     * Metodo que controla cuando cambia de foco el tfName
     *
     * @param observable
     * @param oldValue foco perdido
     * @param newValue foco ganado
     */
    @FXML
    private void tfNameFocusChanged(ObservableValue observable, Boolean oldValue, Boolean newValue) {

        LOG.info("Dentro de tfName FocusChanged");
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

    /**
     * Metodo que controla cuando cambia de foco el tfEmail
     *
     * @param observable
     * @param oldValue foco perdido
     * @param newValue foco ganado
     */
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

    /**
     * Metodo que controla cuando cambia de foco el dpHiringDate
     *
     * @param observable
     * @param oldValue foco perdido
     * @param newValue foco ganado
     */
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

    /**
     * Metodo que controla cuando cambia de foco el tfLogin
     *
     * @param observable
     * @param oldValue foco perdido
     * @param newValue foco ganado
     */
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

    /**
     * Metodo que controla cuando cambia de foco el tfSalary
     *
     * @param observable
     * @param oldValue foco perdido
     * @param newValue foco ganado
     */
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

    /**
     * metodo que controla cuando el tfName ha sufrido un cambio de texto
     *
     * @param observable
     * @param oldValue valor antiguo
     * @param newValue
     */
    @FXML
    private void tfNameTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (!newValue.equalsIgnoreCase(oldValue)) {
            lblErrorName.setText("");
            tfName.setStyle("-fx-text-inner-color: black;");
        }
    }

    /**
     * metodo que controla cuando el tfEmail ha sufrido un cambio de texto
     *
     * @param observable
     * @param oldValue valor antiguo
     * @param newValue
     */
    @FXML
    private void tfEmailTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (!newValue.equalsIgnoreCase(oldValue)) {
            lblErrorEmail.setText("");
            tfEmail.setStyle("-fx-text-inner-color: black;");
        }
    }

    /**
     * metodo que controla cuando el tfLogin ha sufrido un cambio de texto
     *
     * @param observable
     * @param oldValue valor antiguo
     * @param newValue
     */
    @FXML
    private void tfLoginTextChanged(ObservableValue observable, String oldValue, String newValue) {
        int i = 0;
        if (!newValue.equalsIgnoreCase(oldValue)) {
            lblErrorLogin.setText("");
            tfLogin.setStyle("-fx-text-inner-color: black;");
            LOG.info("Login textChanged");
            if (i == 1) {
                loginChanged = true;
            }
            i++;
        }

    }

    /**
     * metodo que controla cuando el tfSalary ha sufrido un cambio de texto
     *
     * @param observable
     * @param oldValue valor antiguo
     * @param newValue
     */
    @FXML
    private void tfSalaryTextChanged(ObservableValue observable, String oldValue, String newValue) {
        if (!newValue.equalsIgnoreCase(oldValue)) {
            lblErrorSalary.setText("");
            tfSalary.setStyle("-fx-text-inner-color: black;");
        }
    }

    /**
     * Controlador del evento accion del boton crear. Valida los datos y los
     * envia a la capa de logica
     *
     * @param event El objecto ActionEvent del evento
     */
    @FXML
    private void save(ActionEvent event) {
        LOG.info("Modificando...");
        try {
            try {
                //Se compueba que el tfLogin este informado
                if (tfLogin.getText().length() != 0) {
                    //Se buscan ususarios que tenga ese login
                    UserManagerFactory.createUserManager("REST_WEB_CLIENT").checkLoginExists(tfLogin.getText());
                }
            } catch (BusinessLogicException ex) {
                //Si se encuentran users con ese login, se muestra
                //una alert de informacion
                exists = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "El login ya existe",
                        ButtonType.OK);
                alert.showAndWait();
            } catch (Exception ex) {
                Logger.getLogger(EmployeeFormController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Se validan los campos
            validateFields();

            if (!exists && validFields()) {
                //En caso de que no exista el Login y los campos sean validos
                try {
                    //Se recoge la fecha de Hoy
                    LocalDate localDate = dpHiringDate.getValue();
                    Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
                    //Se crea el objeto empleado que se va a anadir
                    Employee emp = new Employee();
                    //Se le inroducen los datos del empleado mediante setters
                    emp.setIdUser(null);
                    emp.setFullName(tfName.getText());
                    emp.setEmail(tfEmail.getText());
                    emp.setLogin(tfLogin.getText());
                    emp.setHiringDate(date);
                    emp.setPassword("b4c2ae29e1d128a493449c85c1efc0cb0721b6a248c77c25223c457b0fa25e59");
                    emp.setPrivilege(UserPrivilege.EMPLOYEE);
                    emp.setStatus(UserStatus.ENABLED);
                    emp.setSalary(tfSalary.getText());
                    emp.setPrivilege(UserPrivilege.EMPLOYEE);
                    emp.setStatus(UserStatus.ENABLED);
                    //Se envia a la capa de logica para que cree un empleado
                    EmployeeManagerFactory.createEmployeeManager("REST_WEB_CLIENT").createEmployee(emp);
                    //Se informa al usuario de que el usuario se ha creado correctamente
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,
                            "El usuario se ha añadido correctamente",
                            ButtonType.OK);
                    alert.showAndWait();

                } catch (Exception ex) {
                    Logger.getLogger(EmployeeFormController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (!exists && !validFields()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        "Algun campo no es correcto",
                        ButtonType.OK);
                alert.showAndWait();
            }
        } catch (Exception ex) {
            Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Error añadiendo empleado");
            errorAlert.showAndWait();
        }
    }

    /**
     * Metodo que devuelve un booleano, en funcion de si los campos estan
     * informados o en caso contrario devuelve false y muestra los errores
     * mediante showFieldErrors()
     *
     * @return boolean
     */
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

    /**
     * Metodo que devuelve true si hay un campo informado, en el caso contrario
     * devuelve false
     *
     * @return boolean
     */
    private boolean oneFieldInformed() {

        return !tfName.getText().equalsIgnoreCase("")
                || !tfEmail.getText().equalsIgnoreCase("")
                || !tfLogin.getText().equalsIgnoreCase("")
                || dpHiringDate.getValue() != null
                || !tfSalary.getText().equalsIgnoreCase("");
    }

    /**
     * Metodo que devuelve un booleano. True si todos los campos son validos y
     * false si hay alguno que no lo sea
     *
     * @return boolean
     */
    private boolean validFields() {
        return tfNameIsValid && tfEmailIsValid && tfLoginIsValid && tfSalaryIsValid;
    }

    /**
     * Metodo que devuelve un booleano. True si tfName cumple el pattern
     *
     */
    private boolean validateTfName(String user) {
        return Pattern.matches("[a-zA-ZáéíóúÁÉÍÓÚ]{2,}[\\s[a-zA-ZáéíóúÁÉÍÓÚ]{2,}]*", user);
    }

    /**
     * Metodo que devuelve un booleano. True si tfEmail cumple el pattern
     *
     */
    private boolean validateTfEmail(String email) {
        return Pattern.matches("\\b[a-zA-Z0-9_+-]+(?:.[a-zA-Z0-9_+-]+)*@(?:[a-zA-Z0-9-]+.)+[a-zA-Z]{2,6}\\b", email);
    }

    /**
     * Metodo que devuelve un booleano. True si tfLogin cumple el pattern
     *
     */
    private boolean validateTfLogin(String login) {
        return Pattern.matches("[a-zA-Z_][a-zA-Z0-9_]{1,254}", login);
    }

    /**
     * Metodo que devuelve un booleano. True si tfSalary cumple el pattern
     *
     */
    private boolean validateTfFSalary(String salary) {
        return Pattern.matches("^(?:([0-9]{4,6}))((?:[.])(?:[0-9]{1,2}))?", salary);
    }

    /**
     * Metodo que llama a todos los metos isValid para validar si los campos son
     * correctos
     */
    private void validateFields() {
        tfEmailIsValid = validateTfEmail(tfEmail.getText());
        tfNameIsValid = validateTfName(tfName.getText());
        tfLoginIsValid = validateTfLogin(tfLogin.getText());
        tfSalaryIsValid = validateTfFSalary(tfSalary.getText());
        if (dpHiringDate.getValue() != null) {
            dpHiringDateIsValid = true;
        }

    }

    /**
     * Metodo que llama a todos los metos showlblError que mostraran errores si
     * los campos no son correctos
     */
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
        if (dpHiringDate.getValue() == null) {
            lblErrorHiringDate.setText("Campo obligatorio");
        } else {
            lblErrorHiringDate.setText("");
        }

    }

    /**
     * Este metodo muestra los posibles errores de el campo tfName en
     * lblErrorName
     *
     * @param name
     */
    private void showlblErrorNameMessages(String name) {
        //Se comprueba cual es el error del campo tfName en funcion de la
        //longitud del campo
        switch (name.trim().length()) {
            case 0:
                //longitud=0
                lblErrorName.setText("Campo obligatorio");
                break;
            case 1:
                //longitud=0
                lblErrorName.setText("Longitud minima de 2 caracteres");
                break;
            default:
                //Tiene caracteres no vlaidos
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
        try {
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
        } catch (Exception ex) {
            Logger.getLogger(HbMenuAdmController.class.getName()).log(Level.SEVERE, null, ex);
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Error volviendo a la ventana employee");
            errorAlert.showAndWait();
        }
    }
}
