
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class HbMenuAdmController implements Initializable {

    @FXML
    private HBox hbMenuAdm;
    @FXML
    private MenuBar mbLeft;
    @FXML
    private Menu menuOptions;
    @FXML
    private MenuItem miManageUsers;
    @FXML
    private MenuItem miManageGames;
    @FXML
    private MenuItem miManagePurchases;
    @FXML
    private MenuItem miChangePasswd;
    @FXML
    private MenuBar mbRight;
    @FXML
    private Menu mbExit;
    @FXML
    private Menu mbSignOut;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
