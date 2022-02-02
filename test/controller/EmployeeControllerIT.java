/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import javafx.stage.Stage;
import model.UserStatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.BeforeClass;
import org.junit.Ignore;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isCancelButton;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;
import transferObjects.Employee;

/**
 *
 * @author jonma
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeControllerIT extends ApplicationTest {

    private TextField tfUser;
    private PasswordField tfPassword;
    private Button btnButton;
    private ComboBox cmbFilter;
    private TextField tfValue;
    private TableView tblEmployees;
    private Button btnFind;
    private Button btnAdd;
    private Button btnModify;
    private Button btnDelete;
    private Button btnSignIN;
    private Hyperlink hpReturn;
    private Pane employeePane;
    private Label lblError;
    private DatePicker dpHiringDate;
    private final TableView table = lookup("#tblEmployees").queryTableView();

    ZoneId defaultZoneId = ZoneId.systemDefault();

    //private final DatePicker dpHiringDate = lookup("#dpHiringDate").query();
    private static final String OVERSIZED_TEXT
            = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

    public EmployeeControllerIT() {
    }

    @BeforeClass
    public static void inicio() throws TimeoutException {
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(clientapp.ClientApplication.class);
    }

    @Test
    public void testA_initialInteraction() {
        clickOn("#tfUser");
        write("1");
        clickOn("#tfPassword");
        write("1");
        clickOn("#btnSignIN");
        verifyThat("#employeePane", isVisible());

    }

    @Test
    //@Ignore
    public void testB_initialState() {

        //verifyThat("#tfValue",  (TextField t) -> t.isFocused());
        verifyThat("#lblError", LabeledMatchers.hasText(""));
        verifyThat("#btnAdd", isEnabled());
        verifyThat("#btnModify", isDisabled());
        verifyThat("#btnDelete", isDisabled());

    }

    @Test
    //@Ignore
    public void testC_createModifyDeleteEnabledChange() {
        tblEmployees = lookup("#tblEmployees").queryTableView();
        assertNotEquals("Table has no data: Cannot test.",
                tblEmployees.getItems().size(), 0);
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        verifyThat("#btnDelete", isEnabled());
        verifyThat("#btnModify", isEnabled());
    }

    @Test
    @Ignore
    public void testD_cancelarBorrado() {
        int rowCount = table.getItems().size();
        assertNotEquals("La tabla no tiene Datos: no se puede testear.",
                rowCount, 0);
        clickOn("#btnDelete");
        verifyThat("¿Borrar la fila seleccionada?\n"
                + "Esta operación no se puede deshacer.",
                isVisible());
        clickOn(isCancelButton());
        assertEquals(rowCount, table.getItems().size());
    }

    @Test
    @Ignore
    public void testE_Borrado() {
        int rowCount = table.getItems().size();
        assertNotEquals("La tabla no tiene Datos: no se puede testear.",
                rowCount, 0);
        clickOn("#btnDelete");
        verifyThat("¿Borrar la fila seleccionada?\n"
                + "Esta operación no se puede deshacer.",
                isVisible());
        clickOn(isDefaultButton());
        assertEquals("The row has not been deleted!!!",
                rowCount - 1, table.getItems().size());

    }

    @Test
    @Ignore
    public void testF_usuarioExisteAlCrear() {
        int rowCount = table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        //get an existing login from table data
        String login = ((Employee) table.getItems().get(0)).getLogin();
        clickOn("#btnAdd");
        verifyThat("#employeeFormPane", isVisible());
        clickOn("#tfName");
        write("validName");

        clickOn("#tfEmail");
        write("valid@valid.com");

        clickOn("#tfLogin");
        write(login);
        clickOn("#dpHiringDate");
        dpHiringDate = (DatePicker) lookup("#dpHiringDate").query();
        dpHiringDate.setValue(LocalDate.now());

        clickOn("#btnSave");
        verifyThat("El login ya existe",
                isVisible());
        clickOn(isDefaultButton());
        assertEquals("A row has been added!!!", rowCount, table.getItems().size());

        clickOn("#hpReturn");
        clickOn(isDefaultButton());

    }

    @Test
    @Ignore
    public void testG_createUser() {
        //get row count
        int rowCount = table.getItems().size();
        //get an existing login from random generator
        String login = "username" + new Random().nextInt();
        //write that login on text field

    }

    @Test
    @Ignore
    public void testH_usuarioExisteAlModificar() {
        int rowCount = table.getItems().size();
        assertNotEquals("Table has no data: Cannot test.",
                rowCount, 0);
        Node row = lookup(".table-row-cell").nth(0).query();
        clickOn(row);
        //get an existing login from table data

        String login1 = ((Employee) table.getItems().get(1)).getLogin();
        clickOn("#btnModify");
        verifyThat("#employeeFormPane", isVisible());

        clickOn("#tfName");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        write("validName");

        clickOn("#tfEmail");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        write("newValid@newValid.com");

        clickOn("#tfLogin");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        write(login1);

        clickOn("#btnSave");
        verifyThat("El login ya existe",
                isVisible());
        clickOn(isDefaultButton());
        assertEquals("A row has been added!!!", rowCount, table.getItems().size());

        clickOn("#hpReturn");
        clickOn(isDefaultButton());

    }

    @Test
    @Ignore
    public void testK_modifyUser() {
        dpHiringDate = (DatePicker) lookup("#dpHiringDate").query();
        //get row count
        int rowCount = table.getItems().size();
        assertNotEquals("La tabla no tiene datos. Nose puede testear",
                rowCount, 0);
        //look for 1st row in table view and click it
        Node row = lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ", row);
        clickOn(row);
        //get selected item and index from table
        Employee prevEmp = (Employee) table.getSelectionModel()
                .getSelectedItem();
        int selectedIndex = table.getSelectionModel().getSelectedIndex();
        //Modify user data

        clickOn("#btnModify");

        Employee modEm = new Employee();
        TextField tfName = lookup("#tfName").query();
        TextField tfLogin = lookup("#tfLogin").query();
        TextField tfEmail = lookup("#tfEmail").query();
        TextField tfSalary = lookup("#tfSalary").query();
        dpHiringDate = (DatePicker) lookup("#dpHiringDate").query();

        tfLogin.setText(prevEmp.getLogin() + new Random().nextInt());

        modEm.setFullName(tfName.getText());
        modEm.setEmail(tfEmail.getText());
        modEm.setLogin(tfLogin.getText());
        modEm.setSalary(tfSalary.getText());

        LocalDate localDate = dpHiringDate.getValue();
        Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());
        modEm.setHiringDate(date);

        clickOn("#hpReturn");
        clickOn(isDefaultButton());
        table.refresh();
        Employee emp2 = (Employee) table.getItems().get(selectedIndex);

        assertEquals("The user has not been modified!!!",
                modEm.getFullName(),
                emp2.getFullName());
        assertEquals("The user has not been modified!!!",
                modEm.getEmail(),
                emp2.getEmail());
        assertEquals("The user has not been modified!!!",
                modEm.getLogin(),
                emp2.getLogin());
        assertEquals("The user has not been modified!!!",
                modEm.getHiringDate(),
                emp2.getHiringDate());
        assertEquals("The user has not been modified!!!",
                modEm.getSalary(),
                emp2.getSalary());

    }

    @Test
    @Ignore
    public void testL_MaximaLongitud() {
        clickOn("#tfValue");
        write(OVERSIZED_TEXT);
        verifyThat("La longitud maxima es de 255", isVisible());
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#btnAdd");
        TextField tfName = lookup("#tfName").query();
        TextField tfLogin = lookup("#tfLogin").query();
        TextField tfEmail = lookup("#tfEmail").query();
        TextField tfSalary = lookup("#tfSalary").query();

        clickOn("#tfName");
        write(OVERSIZED_TEXT);
        clickOn("#tfEmail");
        verifyThat("#lblErrorName", LabeledMatchers.hasText("Longitud maxima de 255 caracteres"));
        clickOn("#tfName");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#tfEmail");
        write(OVERSIZED_TEXT);
        clickOn("#tfName");
        verifyThat("#lblErrorEmail", LabeledMatchers.hasText("Longitud maxima de 255 caracteres"));
        clickOn("#tfEmail");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#tfLogin");
        write(OVERSIZED_TEXT);
        clickOn("#tfEmail");
        verifyThat("#lblErrorLogin", LabeledMatchers.hasText("El login no debe superar \n los 255 digitos"));
        clickOn("#tfLogin");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#tfSalary");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        write("999");
        clickOn("#tfEmail");
        verifyThat("#lblErrorSalary", LabeledMatchers.hasText("El salario debe de ser \n mayor o igual a 1000"));
        clickOn("#tfSalary");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        write("100000000");
        clickOn("#tfEmail");
        verifyThat("#lblErrorSalary", LabeledMatchers.hasText("El salario debe de ser \n menor que 1000000"));

        clickOn("#hpReturn");
        clickOn(isDefaultButton());
    }

    @Test
    @Ignore
    public void testM_ErroresName() {
        clickOn("#btnAdd");
        clickOn("#tfName");
        write("");
        clickOn("#tfEmail");
        verifyThat("#lblErrorName", LabeledMatchers.hasText("Campo obligatorio"));
        clickOn("#tfName");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#tfName");
        write("q");
        clickOn("#tfEmail");
        verifyThat("#lblErrorName", LabeledMatchers.hasText("Longitud minima de 2 caracteres"));
        clickOn("#tfName");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#tfName");
        write("jon123");
        clickOn("#tfEmail");
        verifyThat("#lblErrorName", LabeledMatchers.hasText("El nombre solo puede contener\n letras y espacios"));
        clickOn("#tfName");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#tfName");
        write("jon$$$");
        clickOn("#tfEmail");
        verifyThat("#lblErrorName", LabeledMatchers.hasText("El nombre solo puede contener\n letras y espacios"));
        clickOn("#tfName");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#tfName");
        write("jon.mayo");
        clickOn("#tfEmail");
        verifyThat("#lblErrorName", LabeledMatchers.hasText("El nombre solo puede contener\n letras y espacios"));
        clickOn("#tfName");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#hpReturn");
        clickOn(isDefaultButton());
    }

    @Test
    @Ignore
    public void testN_ErroresEmail() {
        clickOn("#btnAdd");

        clickOn("#tfEmail");
        write("");
        clickOn("#tfName");
        verifyThat("#lblErrorEmail", LabeledMatchers.hasText("Campo obligatorio"));
        clickOn("#tfEmail");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#tfEmail");
        write("jon@@gmail.com");
        clickOn("#tfName");
        verifyThat("#lblErrorEmail", LabeledMatchers.hasText("Email invalido"));
        clickOn("#tfEmail");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#tfEmail");
        write("jon@gmail.c");
        clickOn("#tfName");
        verifyThat("#lblErrorEmail", LabeledMatchers.hasText("Email invalido"));
        clickOn("#tfEmail");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#tfEmail");
        write("jongmail.com");
        clickOn("#tfName");
        verifyThat("#lblErrorEmail", LabeledMatchers.hasText("Email invalido"));
        clickOn("#tfEmail");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#hpReturn");
        clickOn(isDefaultButton());
    }

    @Test
    @Ignore
    public void testO_ErroresLogin() {
        clickOn("#btnAdd");

        clickOn("#tfLogin");
        write("");
        clickOn("#tfEmail");
        verifyThat("#lblErrorLogin", LabeledMatchers.hasText("Campo obligatorio"));
        clickOn("#tfLogin");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#tfLogin");
        write("jon mayo");
        clickOn("#tfEmail");
        verifyThat("#lblErrorLogin", LabeledMatchers.hasText("No puede contener espacios"));
        clickOn("#tfLogin");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#tfLogin");
        write("q");
        clickOn("#tfEmail");
        verifyThat("#lblErrorLogin", LabeledMatchers.hasText("Longitud mínima 2"));
        clickOn("#tfLogin");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#tfLogin");
        write("12jonmayo");
        clickOn("#tfEmail");
        verifyThat("#lblErrorLogin", LabeledMatchers.hasText("El login no debe de empezar  \n por un numero"));
        clickOn("#tfLogin");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#tfLogin");
        write("jon$@");
        clickOn("#tfEmail");
        verifyThat("#lblErrorLogin", LabeledMatchers.hasText("login inválido solo puede contener \n "
                + "numeros y letras"));
        clickOn("#tfLogin");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);

        clickOn("#hpReturn");
        clickOn(isDefaultButton());
    }

    @Test
    @Ignore
    public void testP_ErroresHiringDate() {
        clickOn("#btnAdd");

        clickOn("#dpHiringDate");
        clickOn("#tfEmail");
        verifyThat("#lblErrorHiringDate", LabeledMatchers.hasText("Campo obligatorio"));

        clickOn("#hpReturn");
        clickOn(isDefaultButton());

    }

    @Test
    //@Ignore
    public void testR_ErroresSalary() {
        clickOn("#btnAdd");

        clickOn("#tfSalary");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        write("aaaa");
        clickOn("#tfEmail");
        verifyThat("#lblErrorSalary", LabeledMatchers.hasText("El formato correcto \n es el de numero decimal"));
        clickOn("#tfSalary");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        
        write("");
        clickOn("#tfEmail");
        verifyThat("#lblErrorSalary", LabeledMatchers.hasText("Campo obligatorio"));
        clickOn("#tfSalary");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        
        write("1233..32");
        clickOn("#tfEmail");
        verifyThat("#lblErrorSalary", LabeledMatchers.hasText("El formato correcto \n es el de numero decimal"));
        clickOn("#tfSalary");
        push(KeyCode.CONTROL, KeyCode.A);
        eraseText(1);
        clickOn("#hpReturn");
        
    }
}
