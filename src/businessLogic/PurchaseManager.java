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

    public Purchase findPurchaseById(String idClient, String idGame) throws ClientErrorException;

    public Collection<Purchase> findPurchasesByPurchaseDate(String purchaseDate) throws ClientErrorException;

    public Collection<Purchase> findPurchasesByPrice(String price) throws ClientErrorException;

    public void edit(Object requestEntity, String id) throws ClientErrorException;

    public Purchase find(String id) throws ClientErrorException;

    public Collection<Purchase> findRange(String from, String to) throws ClientErrorException;

    public void create(Object requestEntity) throws ClientErrorException;

    public Collection<Purchase> getAllPurchasess() throws ClientErrorException;

    public void remove(String id) throws ClientErrorException;

    public Purchase findPurchasesByClientId(String idClient) throws ClientErrorException;

    public void close();
    
}
