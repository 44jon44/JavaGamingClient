/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import businessLogic.PurchaseManager;
import factories.ClientManagerFactory;
import factories.PurchaseManagerFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.naming.OperationNotSupportedException;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import transferObjects.Client;
import transferObjects.Game;
import transferObjects.Purchase;
import businessLogic.ClientManager;
import java.text.SimpleDateFormat;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;

/**
 * FXML Controller class
 *
 * @author Alex Hurtado
 */
public class PurchaseController {

    private static final Logger LOG = Logger.getLogger(PurchaseController.class.getName());

    private final static String TYPE = "REST_WEB_CLIENT";

    private PurchaseManager purchasesManager;

    private ObservableList<Purchase> purchasesObservableList;

    private ClientManager clientsManager;

    private ObservableList<Client> clientsObservableList;
    
    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
    
    @FXML
    private Pane purchasePane;
    @FXML
    private TableView tvPurchases;
    @FXML
    private TableColumn<Purchase, String> tcPurchaseDate;
    @FXML
    private TableColumn<Purchase, String> tcClientName;
    @FXML
    private TableColumn<Purchase, String> tcGameName;
    @FXML
    private TableColumn<Purchase, String> tcGameGenre;
    @FXML
    private TableColumn<Purchase, Integer> tcGamePegi;
    @FXML
    private TableColumn<Purchase, Float> tcGamePrice;
    @FXML
    private Button btnModifyPurchase;
    @FXML
    private Button btnAddPurchase;
    @FXML
    private Button btnDeletePurchase;
    @FXML
    private Label lblError;
    @FXML
    private ComboBox<Client> cbClients;
    @FXML
    private DatePicker dpPurchaseDate;
    @FXML
    private ComboBox cbPrice;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnClearSeach;
    @FXML
    private HBox hbMenuAdm;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private Purchase selectedPurchase;

    public void setSelectedPurchase(Purchase selectedPurchase) {
        this.selectedPurchase = selectedPurchase;
    }
    
    public void initStage(Parent root) throws Exception{
        LOG.info("InitStage de la ventana purchase...");
        try{
            Scene purchaseScene = new Scene(root);
            stage.setScene(purchaseScene);
            loadViewState();
        }catch(Exception e){
            LOG.log(Level.SEVERE,"Se ha producido un error en el servidor",e);
            //Mostramos un error
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Error");
            errorAlert.setContentText("Error abriendo la ventana purchase");
            errorAlert.showAndWait();
        }
    }
    

    public void handlePurchaseSelected(ObservableValue ov, Object oldValue, Object newValue) {
        if (newValue != null) {
            btnModifyPurchase.setDisable(false);
            btnDeletePurchase.setDisable(false);
            LOG.info(ov.toString());
        }else{
            btnModifyPurchase.setDisable(true);
            btnDeletePurchase.setDisable(true);
        }
    }
    @FXML
    private void handleAddPurchase(ActionEvent event) {
        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader purchaseForm = new FXMLLoader(getClass().getResource("/view/purchaseForm.fxml"));
            Parent root = (Parent) purchaseForm.load();
            //controlador de la ventana
            PurchaseFormController controller = purchaseForm.getController();
            controller.setStage(stage);
            controller.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Ha saltado este error");
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleModifyPurchase(ActionEvent event) {
        try {
            //getResource tienes que añadir la ruta de la ventana que quieres iniciar.
            FXMLLoader purchaseForm = new FXMLLoader(getClass().getResource("/view/purchaseForm.fxml"));
            Parent root = (Parent) purchaseForm.load();
            //controlador de la ventana
            PurchaseFormController controller = purchaseForm.getController();
            controller.setStage(stage);
            controller.setPurchaseToModify(selectedPurchase);
            controller.initStage(root);
        } catch (IOException ex) {
            LOG.log(Level.INFO, "Ha saltado este error");
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleClearSearch(ActionEvent event) {
        cbClients.valueProperty().set(null);
        cbPrice.valueProperty().set(null);
        dpPurchaseDate.getEditor().clear();
        loadPurchasesData();
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        LOG.log(Level.SEVERE, "Método 'BUSCAR' no implementado");
    }

    @FXML
    private void handleDeletePurchase(ActionEvent event) {
        //LOG.log(Level.SEVERE, "Método 'BORRAR COMPRA' no implementado");
            try{
                if(selectedPurchase != null){
                purchasesManager.deletePurchase(
                        String.valueOf(selectedPurchase.getClient().getIdUser()),String.valueOf(selectedPurchase.getGame().getIdGame()));
                        loadPurchasesData();
                LOG.info("Compra borrada");
            }
            }catch(Exception e){
                LOG.log(Level.SEVERE, "Error al borrar compra", e);
            }
    }

    public void setPurchaseManager(PurchaseManager puchasesManager) {
        this.purchasesManager = puchasesManager;
    }

    private void loadPurchasesData() {
        try
        {
            Collection purchases;
            purchases = purchasesManager.getAllPurchasess();
            if(purchases != null){
                purchasesObservableList = FXCollections.observableArrayList(purchases);
                tvPurchases.setItems(purchasesObservableList);
            }
            LOG.log(Level.INFO, "Juegos cargados: {0}", ((purchasesObservableList != null)?purchasesObservableList.size():0));
        } catch (Exception ex)
        {
            LOG.log(Level.SEVERE, "Se ha producido un error al cargar juegos.", ex);
        }
    }

    private void loadClientsData() {
        try
        {
            Collection clients;
            clients = clientsManager.findAllClients();
            if(clients !=  null){
                clientsObservableList = FXCollections.observableArrayList(clients);
                cbClients.setItems(clientsObservableList);
            }
            LOG.log(Level.INFO, "Clientes cargados: {0}", ((clientsObservableList != null)?clientsObservableList.size():0));
        } catch (Exception ex)
        {
            LOG.log(Level.SEVERE, "Se ha producido un error al cargar los clientes", ex);
        }
    }

    private void loadPricesData() {
        ObservableList<String> precios = FXCollections.observableArrayList("Menos de 30€", "30€ a 40€", "40€ a 50€", "50€ a 60€", "Más de 60€");
        cbPrice.setItems(precios);
    }

    private void loadViewState() {
        try {
            //botones
            btnAddPurchase.setOnAction(this::handleAddPurchase);
            btnModifyPurchase.setOnAction(this::handleModifyPurchase);
            btnDeletePurchase.setOnAction(this::handleDeletePurchase);
            btnModifyPurchase.setDisable(true);
            btnDeletePurchase.setDisable(true);
            btnSearch.setOnAction(this::handleSearch);
            btnClearSeach.setOnAction(this::handleClearSearch);
            //label error
            lblError.setText("");
            //DatePicker
            dpPurchaseDate.setEditable(false);
            dpPurchaseDate.setConverter(new StringConverter<LocalDate>() {
                String pattern = "yyyy-MM-dd";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
                
                {
                    dpPurchaseDate.setPromptText(pattern.toLowerCase());
                }
                
                @Override
                public String toString(LocalDate date) {
                    if (date != null) {
                        return dateFormatter.format(date);
                    } else {
                        return "";
                    }
                }
                
                @Override
                public LocalDate fromString(String string) {
                    if (string != null && !string.isEmpty()) {
                        return LocalDate.parse(string, dateFormatter);
                    } else {
                        return null;
                    }
                }
            });
            //ComboBox
            clientsManager = ClientManagerFactory.createClientManager(TYPE);
            loadClientsData();
            loadPricesData();
            //TableView
            //tcPurchaseDate.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));
            tcPurchaseDate.setCellValueFactory(cell -> new SimpleStringProperty(dateFormatter.format(cell.getValue().getPurchaseDate())));
            tcClientName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getClient().getFullName()));
            tcGameName.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGame().getName()));
            tcGameGenre.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getGame().getGenre()));
            tcGamePegi.setCellValueFactory(cell -> new SimpleIntegerProperty(cell.getValue().getGame().getPegi()).asObject());
            tcGamePrice.setCellValueFactory(cell -> new SimpleFloatProperty(cell.getValue().getGame().getPrice()).asObject());
            purchasesManager = PurchaseManagerFactory.createPurchaseManager(TYPE);
            //carga de datos en la tabla
            loadPurchasesData();
            //listener para cuando se selecciona una fila de la TableView
            tvPurchases.getSelectionModel().selectedItemProperty().addListener((ov, oldValue, newValue) -> {
                if (newValue != null) {
                    btnModifyPurchase.setDisable(false);
                    btnDeletePurchase.setDisable(false);
                    setSelectedPurchase((Purchase) tvPurchases.getSelectionModel().getSelectedItem());
                    
                } else {
                    btnModifyPurchase.setDisable(true);
                    btnDeletePurchase.setDisable(true);
                }
            });
        } catch (OperationNotSupportedException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
