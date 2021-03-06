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
    
    /**
     * Crea un objeto PurchaseManagerImplementation. Construye un cliente web para
     * acceder a un servicio RESTful que proporciona lógica de negocio en una
     * aplicación servidor.
     */
    public PurchaseManagerImplementation() {
        webClient = new PurchaseRESTful();
    }
    
    @Override
    public String countREST() throws ClientErrorException {
        return webClient.countREST();
    }

    @Override
    public Collection<Purchase> getAllPurchasess() throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Collection<Purchase> purchases = webClient.findAll(
        new GenericType<Collection<Purchase>>(){});
        //purchases.stream().forEach(p -> p.setClient(webClient2.findClientById(Client.class, String.valueOf(p.getIdPurchase().getIdClient()))));
        return purchases;
    }
    
    @Override
    public Purchase findPurchaseById(String idClient, String idGame) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        Purchase purchase;
        purchase = webClient.findPurchaseById(Purchase.class, idClient, idGame);
        return purchase;
    }

    @Override
    public Collection<Purchase> findPurchasesByPurchaseDate(String purchaseDate) throws Exception {
        Collection<Purchase> purchases = webClient.findPurchasesByPurchaseDate(new GenericType<Collection<Purchase>>(){}, purchaseDate);
        return purchases;
    }

    @Override
    public Collection<Purchase> findPurchasesByPrice(String price) throws Exception {
        Collection<Purchase> purchases = webClient.findPurchasesByPrice(new GenericType<Collection<Purchase>>(){}, price);
        return purchases;
    }

    @Override
    public void edit(Object requestEntity, String id) throws Exception {
        webClient.edit(requestEntity, id);
    }

    @Override
    public Purchase find(String id) throws Exception {
        return webClient.find(Purchase.class, id);
    }

    @Override
    public Collection<Purchase> findRange(String from, String to) throws Exception {
        Collection<Purchase> purchases = webClient.findRange(new GenericType<Collection<Purchase>>(){}, from, to);
        return purchases;
    }

    @Override
    public void create(Object requestEntity) throws Exception {
        webClient.create(requestEntity);
    }

    @Override
    public void remove(String id) throws Exception {
        webClient.remove(id);
    }

    @Override
    public Collection<Purchase> findPurchasesByClientId(String idClient) throws Exception {
        return webClient.findPurchasesByClientId(new GenericType<Collection<Purchase>>(){},idClient);
    }

    @Override
    public void close() {
        webClient.close();
    }

    @Override
    public void deletePurchase(String idClient, String idGame) throws Exception {
        webClient.deletePurchase(idClient, idGame);
    }
}
