/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import java.util.Random;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Ignore;
import static org.testfx.api.FxAssert.verifyThat;
import org.testfx.framework.junit.ApplicationTest;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.matcher.base.NodeMatchers.isVisible;
import static org.testfx.matcher.control.ButtonMatchers.isCancelButton;
import static org.testfx.matcher.control.ButtonMatchers.isDefaultButton;
import static org.testfx.matcher.control.ComboBoxMatchers.hasSelectedItem;
import org.testfx.matcher.control.LabeledMatchers;
import static org.testfx.matcher.control.TextInputControlMatchers.hasText;

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
    private final TableView table = lookup("#tblEmployees").queryTableView();

    private static final String OVERSIZED_TEXT
            = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
            + "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

    public EmployeeControllerIT() {
    }

    @Override
    public void start(Stage stage) throws Exception {
        //Se lanza la aplicacion a testear
        new clientapp.ClientApplication().start(stage);
        //Comprobacion de nodos
        tfUser = lookup("#tfUser").query();
        tfPassword = lookup("#tfPassword").query();
        hpReturn = lookup("#hpReturn").query();
        btnSignIN = lookup("#btnSignIN").query();
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
    public void testB_initialState() {
        Stage stage = (Stage) employeePane.getScene().getWindow();
        stage.hide();
        tfValue=lookup("#tfValue").query();
        btnDelete=lookup("#btnDelete").query();
        //verifyThat("#tfValue",  (TextField t) -> t.isFocused());
        verifyThat("#lblError", LabeledMatchers.hasText(""));
        verifyThat("#btnAdd", isEnabled());
        verifyThat("#btnModify", isDisabled());
        verifyThat("#btnDelete", isDisabled());

    }

    @Test
    //@Ignore
    public void testC_createModifyDeleteEnabledChange() {
        tblEmployees=lookup("#tblEmployees").queryTableView();
        assertNotEquals("Table has no data: Cannot test.",
                        tblEmployees.getItems().size(),0);
        Node row=lookup(".table-row-cell").nth(0).query();
        assertNotNull("Row is null: table has not that row. ",row);
        clickOn(row);
        sleep(50000);
        verifyThat("#btnDelete",isEnabled());
    }
}
