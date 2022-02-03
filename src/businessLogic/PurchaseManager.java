/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import java.util.Collection;
import javax.ws.rs.ClientErrorException;
import transferObjects.Purchase;

/**
 *
 * @author Alex Hurtado
 */
public interface PurchaseManager {

    public String countREST() throws ClientErrorException;

    public Purchase findPurchaseById(String idClient, String idGame) throws Exception;

    public Collection<Purchase> findPurchasesByPurchaseDate(String purchaseDate) throws Exception;

    public Collection<Purchase> findPurchasesByPrice(String price) throws Exception;

    public void edit(Object requestEntity, String id) throws Exception;

    public Purchase find(String id) throws Exception;

    public Collection<Purchase> findRange(String from, String to) throws Exception;

    public void create(Object requestEntity) throws Exception;

    public Collection<Purchase> getAllPurchasess() throws Exception;

    public void remove(String id) throws Exception;

    public Collection<Purchase> findPurchasesByClientId(String idClient) throws Exception;

    public void deletePurchase(String idClient, String idGame) throws Exception;
    
    public void close();
    
}
