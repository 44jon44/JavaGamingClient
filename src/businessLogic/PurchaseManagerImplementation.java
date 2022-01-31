/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.Collection;
import java.util.logging.Logger;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.GenericType;
import rest.ClientRESTful;
import rest.PurchaseRESTful;
import transferObjects.Client;
import transferObjects.Purchase;

/**
 *
 * @author Alex Hurtado
 */
public class PurchaseManagerImplementation implements PurchaseManager{

    //Logger de la implementación de la interfaz
    private static final Logger LOG = Logger.getLogger(PurchaseManagerImplementation.class.getName());
    private PurchaseRESTful webClient;
    private ClientRESTful webClient2;
    
    /**
     * Crea un objeto PurchaseManagerImplementation. Construye un cliente web para
     * acceder a un servicio RESTful que proporciona lógica de negocio en una
     * aplicación servidor.
     */
    public PurchaseManagerImplementation() {
        webClient = new PurchaseRESTful();
        webClient2 = new ClientRESTful();

    }
    
    @Override
    public String countREST() throws ClientErrorException {
        return webClient.countREST();
    }

    @Override
    public Collection<Purchase> getAllPurchasess() throws ClientErrorException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Collection<Purchase> purchases = webClient.findAll(
        new GenericType<Collection<Purchase>>(){});
        //purchases.stream().forEach(p -> p.setClient(webClient2.findClientById(Client.class, String.valueOf(p.getIdPurchase().getIdClient()))));
        return purchases;
    }
    
    @Override
    public Purchase findPurchaseById(String idClient, String idGame) throws ClientErrorException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Purchase purchase;
        purchase = webClient.findPurchaseById(Purchase.class, idClient, idGame);
        return purchase;
    }

    @Override
    public Collection<Purchase> findPurchasesByPurchaseDate(String purchaseDate) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Purchase> findPurchasesByPrice(String price) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void edit(Object requestEntity, String id) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Purchase find(String id) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<Purchase> findRange(String from, String to) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void create(Object requestEntity) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(String id) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Purchase findPurchasesByClientId(String idClient) throws ClientErrorException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void close() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
