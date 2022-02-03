/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import transferObjects.Client;
import transferObjects.Purchase;
import businessLogic.ClientManager;
import businessLogic.GameManager;
import businessLogic.PurchaseManager;
import factories.ClientManagerFactory;
import factories.GameManagerFactory;
import factories.PurchaseManagerFactory;
import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javax.naming.OperationNotSupportedException;

/**
 * FXML Controller class
 *
 * @author ibai Arriola
 */
public class PurchaseFormController {

    private static final Logger LOG = Logger.getLogger(PurchaseFormController.class.getName());

    private final static String TYPE = "REST_WEB_CLIENT";

    private ClientManager clientsManager;

    private ObservableList<Client> clientsObservableList;

    private GameManager gamesManager;

    private PurchaseManager purchasesManager;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnDelete;
    @FXML
    private Hyperlink hlBack;
    @FXML
    private DatePicker dpPurchaseDate;
    @FXML
    private TextField tfGamePrice;
    @FXML
    private ComboBox cbGameName;
    @FXML
    private ComboBox cbGameGenre;
    @FXML
    private ComboBox cbGamePegi;
    @FXML
    private ComboBox<Client> cbClient;
    @FXML
    private Label lblErrorClient;
    @FXML
    private Label lblErrorGame;
    @FXML
    private Pane purchaseFormPane;
    @FXML
    private HBox hbMenuAdm;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Purchase purchaseToModify;

    public void setPurchaseToModify(Purchase purchaseToModify) {
        this.purchaseToModify = purchaseToModify;
    }

    public void initStage(Parent root) {
        try {
            Scene purchaseFormScene = new Scene(root);
            stage.setScene(purchaseFormScene);
            //Entity managers
            purchasesManager = PurchaseManagerFactory.createPurchaseManager(TYPE);
            clientsManager = ClientManagerFactory.createClientManager(TYPE);
            gamesManager = GameManagerFactory.createGameManager(TYPE);
            //hiperlink
            //hlBack.setDisable(true);
            hlBack.setOnAction(this::back);
            //labels error
            lblErrorClient.setText("");
            lblErrorGame.setText("");
            //botones
            btnDelete.setOnAction(this::hadleDelete);
            btnSave.setOnAction(this::handleSave);
            //si es una alta
            if (purchaseToModify != null) {
                cbClient.setValue(purchaseToModify.getClient());
                cbClient.setDisable(true);
                cbGameName.setValue(purchaseToModify.getGame().getName());
                cbGameName.setDisable(true);
                cbGameGenre.setValue(purchaseToModify.getGame().getGenre());
                cbGameGenre.setDisable(true);
                cbGameGenre.setStyle("-fx-opacity: 1.0");
                cbGamePegi.setValue(purchaseToModify.getGame().getPegi());
                cbGamePegi.setDisable(true);
                cbGamePegi.setStyle("-fx-opacity: 1.0");
                tfGamePrice.setText(purchaseToModify.getGame().getPrice().toString());
                tfGamePrice.setDisable(true);
                tfGamePrice.setStyle("-fx-opacity: 0.5");
                dpPurchaseDate.setEditable(false);
            }
        } catch (OperationNotSupportedException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

    }

    public void back(ActionEvent event) {

        //Informar que se descartaran los cambios
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Se descartarán los cambios\n"
                + "Esta operación no se puede deshacer.",
                ButtonType.OK, ButtonType.CANCEL);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {

            try {
                //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
                FXMLLoader purchase = new FXMLLoader(getClass().getResource("/view/purchase.fxml"));
                Parent root;
                root = (Parent) purchase.load();
                PurchaseController controller = purchase.getController();
                controller.setStage(stage);
                controller.initStage(root);
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        } else {
            alert.close();
        }

    }

    private void loadCbClients() {
        try {
            Collection clients;
            clients = clientsManager.findAllClients();
            clientsObservableList = FXCollections.observableArrayList(clients);
            cbClient.setItems(clientsObservableList);
            LOG.log(Level.INFO, "Clientes cargados: {0}", clientsObservableList.size());
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void hadleDelete(ActionEvent event) {

    }

    @FXML
    private void handleSave(ActionEvent event) {

    }
}
